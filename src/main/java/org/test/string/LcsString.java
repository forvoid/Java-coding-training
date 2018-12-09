package org.test.string;


import java.util.HashSet;
import java.util.Set;

/**
 * lcs 查找两个字符串的最长公共子序列
 */
public class LcsString {

    public static int lcs(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int c[][] = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0 || j == 0) {
                    c[i][j] = 0;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else {
                    c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);
                }
            }
        }
        return c[len1][len2];
    }

    public static void main(String[] args) {
        System.out.println(lcs3("bdcaba", "abcbdab"));
    }

    /**
     * 自己实现lcs 查找最长子序列算法
     * c[i][j] 中 i=0 j=0 -> c[i][j]=0;
     * str1 [i-1] == str2[j-1] -> c[i][j]= c[i-1][j-1] + 1;
     * 否则 c[i][j] = max(c[i-1][j],c[i][j-1])
     */
    public static int lcs2(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int[][] c = new int[len1 + 1][len2 + 1];
        Set<Character> lcsSet = new HashSet<>();
        for (int i = 0; i < len1 + 1; i++) {
            for (int j = 0; j < len2 + 1; j++) {
                if (i == 0 || j == 0) {
                    c[i][j] = 0;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    if (!lcsSet.contains(str1.charAt(i - 1))) {
                        System.out.println(str1.charAt(i - 1));
                        lcsSet.add(str1.charAt(i - 1));
                    }

                } else {
                    c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);
                }
            }
        }
        return c[len1][len2];
    }

    /**
     * 公共最长子字符串
     */
    public static int lcs3(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int result = 0;
        Set<Integer> end = new HashSet();
        int[][] c = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0 || j == 0) {
                    c[i][j] = 0;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    if (c[i][j] > result) {
                        result = c[i][j];
                        end = new HashSet();
                        end.add(i);
                    } else if (c[i][j] == result) {
                        System.out.println("当前 状态 c" + c[i][j] + " result" + result);
                        end.add(i);
                    }

                } else {
                    c[i][j] = 0;
                }
            }
        }
        for (Integer o : end) {
            System.out.println(str1.substring(o - result, o));
        }
        return result;
    }
}
