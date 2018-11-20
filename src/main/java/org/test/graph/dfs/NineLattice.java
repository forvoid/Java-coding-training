package org.test.graph.dfs;


/**
 * 实现计算出九宫格的总的路径
 * {{0, 1, 2},
 *  {3, 4, 5},
 *  {6, 7, 8}}
 * 最短的组合为4
 *
 *
 * @param conn 表示当前的特殊值 满足 角标为0 1时判断特殊值是否为角标2，如果是的话就是需要特殊判断的对象
 * @param used 这个数组表示 临时存放当前的深度优先遍历的已经访问过的点
 */
public class NineLattice {

    int[][] m = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    int[][] conn = {
            {0, 2, 1},
            {0, 6, 3},
            {0, 8, 4},
            {2, 6, 4},
            {2, 8, 5},
            {1, 7, 4},
            {3, 5, 4},
            {6, 8, 7},
    };
    int[] used = new int[10];

    int count = 0, sum = 0;

    /**
     * 1、判断i j 大的放在后面，便于匹配是否满足特殊值
     * 2、循环 0 -8（这个是特殊数组的数量） ，如果 当前i值为数组ii0的值 并且 j值为数组ii1的值相同
     *         如果 ii2已经走过了 就表示不是特殊值
     *         如果 ii2没有走过 就表示是不能连接的
     * 4、否则其他的值，都是正确的路径
     * @param i 待匹配值
     * @param j 待匹配值
     * @return
     */
    boolean isPath(int i, int j) {
        if (i > j) {
            int tmp = i;
            i = j;
            j = tmp;
        }
        for (int ii = 0; ii < 8; ii++) {
            if (i == conn[ii][0] && j == conn[ii][1]) {
                if (used[conn[ii][2]] == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 深度优先算法
     * 1、使用数组作为是否进入的某一个节点的标记
     *      在进入前 改为1
     *      在退出前 改为0
     * 2、判断是否当前的深度是要求的深度
     *      如果满足深度的要求，就可以不继续往下进行了 退出当前方法
     *      如果不满足要求 就执行第三步
     * 3、循环0-8，
     *      判断 是否 循环的值已经走过了 && 判断 是否满足特殊的值
     *      如果该值没有走过，并且 满足特殊的值时 将执行 4、
     *      否值 退出
     * 4、设置 循环的i为 当前节点  n为指定深度， 当前深度len+1
     *
     * @param s 当前节点
     * @param n 指定深度
     * @param len 当前深度
     */
    void dfs(int s, int n, int len) {
        used[s] = 1;
        if (len == n) {
            count++;
        } else {
            for (int i = 0; i < 9; i++) {
                if (used[i] != 1 && isPath(s, i)) {
                    dfs(i, n, len + 1);
                    used[i] = 0;
                }
            }
        }
        used[s] = 0;
    }

    /**
     * 设置计算
     * 循环 i为4到9 表示最短路径为4
     * 通过分析可以发现
     *      0 有4个相似点
     *      1 有4个相似点
     *      4 有1个相似点
     * 然后 将 这几个点 为起点 得到的路径的数量 乘以相似点的数量 就是 当前i的所有的路径数量
     */
    public void sum() {
        int tmp = 0;
        for (int i = 4; i <= 9; i++) {
            count = 0;
            dfs(0, i, 1);
            tmp += count * 4;


            for (int h = 0; h < 9; h++) {
                used[h] = 0;
            }
            count = 0;
            dfs(1, i, 1);
            tmp += count * 4;

            for (int h = 0; h < 9; h++) {
                used[h] = 0;
            }
            count = 0;
            dfs(4,i,1);
            tmp += count;

            sum += tmp;
            System.out.println(i + "一共有路径方式："+ tmp);
            tmp = 0;
        }
        System.out.println("一共有解决方案: " + sum);
    }

    public static void main(String[] args) {
        NineLattice nineLattice = new NineLattice();
        nineLattice.sum();
    }

    private void swap(int i, int j) {
        int tmp = i;
        i = j;
        j = tmp;
    }
}
