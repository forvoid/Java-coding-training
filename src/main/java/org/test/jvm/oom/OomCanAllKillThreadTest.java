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
