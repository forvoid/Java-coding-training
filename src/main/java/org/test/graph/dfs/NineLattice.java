package org.test.graph.dfs;


/**
 * 实现计算出九宫格的总的路径
 * 最短的组合为4
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
