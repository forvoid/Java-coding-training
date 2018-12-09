package org.test.greedy;

/**
 * 除去 k 个数获取到最小的数
 * 删去k个数字后的最小值，使用贪心算法来处理
 *
 * @author taowenxiang
 * @date 2018-11-28
 * @since 1.0
 */
public class MinNumber {

    public static void main(String[] args) {


        System.out.println(removeKDigits("1593212", 3));


        System.out.println(removeKDigits("30200", 1));


        System.out.println(removeKDigits("10", 2));


        System.out.println(removeKDigits("541270936", 3));

    }

    /**
     *
     */
    public static String removeKDigits(String num, int k) {
        String numNew = num;
        for (int i = 0; i < k; i++) {
            boolean hasCut = false;
            for (int j = 0; j < numNew.length(); j++) {
                if (numNew.charAt(j) > numNew.charAt(j + 1)) {
                    numNew = numNew.substring(0, j) + numNew.substring(j + 1);
                    hasCut = true;
                    break;
                }
            }
            if (!hasCut&&numNew.length()!=0) {
                // 如果没有删除对应的参数，就删除最后一个参数
                numNew = numNew.substring(0, numNew.length() - 1);
            }
            // 清理整数左侧的数字0
            numNew = removeZero(numNew);
        }

        //判断是否整数为 0
        if (numNew.length() == 0) {
            return "0";
        }
        return numNew;
    }

    /**
     * 清理数字0
     */
    private static String removeZero(String num) {
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(0) == '0') {
                num = num.substring(1);
            } else {
                break;
            }
        }
        return num;
    }

}
