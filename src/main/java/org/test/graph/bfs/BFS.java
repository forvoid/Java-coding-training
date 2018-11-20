package org.test.graph.bfs;

import org.test.graph.dfs.NineLattice;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 广度优先遍历
 *
 */
public class BFS {


    Queue<Integer> queue = new LinkedBlockingQueue();

    int[][] m = {
            {0,1,2},
            {3,4,5},
            {6,7,8}
    };

    int[][] conn = {
            {0,2,1},
            {3,5,4},
            {6,8,7},
            {0,6,3},
            {1,7,4},
            {2,8,5},
            {0,8,4},
            {2,6,4}
    };
    int[] used = new int[10];
    boolean isPath(int i,int j) {
        if (i>j) {
            int tmp = i;
            i = j;
            j = tmp;
        }
        for (int ii = 0 ;ii <8;ii++) {
            if (i == conn[ii][0] && j == conn[ii][1]) {
                // 满足特殊值
                if (used[conn[ii][2]] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    void bfs(int s,int n,int len) {
        used[s] = 1;
        if (n == len) {
            return;
        }
        for (int i = 0;i<9;i++) {
            if (used[i]==0) {
                queue.add(i);
            }
        }
        while (queue.isEmpty()) {
            bfs(queue.poll(),n,len+1);
        }
        used[s] = 0;
    }

    int count = 0,sum = 0;

    public void sum() {
        int tmp = 0;
        for (int i = 4; i <= 9; i++) {
            count = 0;
            bfs(0, i, 1);
            tmp += count * 4;


            for (int h = 0; h < 9; h++) {
                used[h] = 0;
            }
            count = 0;
            bfs(1, i, 1);
            tmp += count * 4;

            for (int h = 0; h < 9; h++) {
                used[h] = 0;
            }
            count = 0;
            bfs(4, i, 1);
            tmp += count;

            sum += tmp;
            System.out.println(i + "一共有路径方式：" + tmp);
            tmp = 0;
        }
        System.out.println("一共有解决方案: " + sum);
    }

    public static void main(String[] args) {
        NineLattice nineLattice = new NineLattice();
        nineLattice.sum();
    }
}
