package org.test.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * jvm堆内存溢出后，其他线程是否可继续工作
 * <p>
 * 今天根据 微笑老师 的博文一起探究 JvmOOM 之后是否其他线程会继续工作
 * https://mp.weixin.qq.com/s/TXu6LOAN2i9oAOQaLf7saQ
 * 对内存溢出的分类：
 * * 堆溢出（“java.lang.OutOfMemoryError: Java heap space”）
 * 永久带溢出（“java.lang.OutOfMemoryError:Permgen space”）
 * 不能创建线程（“java.lang.OutOfMemoryError:Unable to create new native thread”）
 * 本文主要是分析堆溢出对应用带来的影响。
 *
 * @author taowenxiang
 * @date 2018/11/8
 * @since 1.0
 */
public class OomCanAllKillThreadTest {

    /**
     * 原理分析
     * 我们知道java对象基本上都是在堆上分配（有特殊情况下，不在我们讨论的范围内）。小对象都是直接在Eden区域中分配。如果此时内存不够，
     * 就会发生young gc，如果释放之后还是内存不够，此时jvm会进行full gc。如果发生full gc之后内存还是不够，
     * 此时就会抛出“java.lang.OutOfMemoryError: Java heap space”。大对象jvm会直接在old 区域中申请，但是和小对象分配的原理类似。
     *
     * 一般情况下，java对象内存分配跟线程无关（TLAB例外），能够申请成功至于当前只和当前heap空余空间有关。
     */
    /**
     * 总结
     * 发生OOM之后会不会影响其他线程正常工作需要具体的场景分析。但是就一般情况下，发生OOM的线程都会终结（除非代码写的太烂），
     * 该线程持有的对象占用的heap都会被gc了，释放内存。
     *
     * 因为发生OOM之前要进行gc，就算其他线程能够正常工作，也会因为频繁gc产生较大的影响。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1000);
        new Thread(new MyThread0(),"Mythread-0").start();
        new Thread(new MyThread1(),"Mythread-1").start();
    }

    /**
     * 线程0
     */
    static class MyThread0 implements Runnable {

        @Override
        public void run() {
            List<byte[]> bytesList = new ArrayList<>();
            while (true) {
                bytesList.add(new byte[1024]);
            }
        }
    }

    /**
     * 线程1
     */
    static class MyThread1 implements Runnable {

        @Override
        public void run() {
            List<byte[]> bytesList = new ArrayList<>();
            while (true) {
                try {
                    // 捕获了错误并继续执行
                    bytesList.add(new byte[1024]);
                    Thread.sleep(1000);
                    System.out.println("==========");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
