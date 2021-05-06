package com.example.demo.service;

import com.pugwoo.wooutils.json.JSON;

/**
 * @author 锟斤拷 <br/>
 * @date 2021/04/23 <br/>
 *
 */
public class QueueProblem {
    
    public static void main(String[] args) {
    
        long start = System.currentTimeMillis();
        long count = queueProblem(8);
        long end = System.currentTimeMillis();
        System.out.println(8 + " - " + count + "    - cost: " + (end - start));
        
        // for (int n = 1; n <= 16; n++) {
        //     long start = System.currentTimeMillis();
        //     long count = queueProblem(n);
        //     long end = System.currentTimeMillis();
        //     System.out.println(n + " - " + count + "    - cost: " + (end - start));
        // }
    }
    
    /**
     * 1 -         1    - cost: 0
     * 2 -         0    - cost: 0
     * 3 -         0    - cost: 0
     * 4 -         2    - cost: 0
     * 5 -        10    - cost: 0
     * 6 -         4    - cost: 0
     * 7 -        40    - cost: 0
     * 8 -        92    - cost: 1
     * 9 -       352    - cost: 1
     * 10 -      724    - cost: 5
     * 11 -     2680    - cost: 21
     * 12 -    14200    - cost: 110
     * 13 -    73712    - cost: 635
     * 14 -   365596    - cost: 3734
     * 15 -  2279184    - cost: 24233
     * 16 - 14772512    - cost: 173115
     */
    public static long queueProblem(int n) {
        if (n < 1) { return 0; }
        int[] record = new int[n];
        return solution(record, n,  0);
    }
    
    /**
     *
     * @param record 棋盘 下标代表行，数值代表列
     * @param n  N皇后 N行*N列
     * @param y  正在处理第y行
     * @return 多少种解法
     */
    private static long solution(int[] record, int n, int y) {
        if (y == n) {
            System.out.println(JSON.toJson(record));
            return 1;
        }
        long count = 0;
        // yx  x的值，第y行要摆在x列
        for (int yx = 0; yx < n; yx++) {
            if (valid(record, y, yx)) {
                record[y] = yx;
                count += solution(record, n, y + 1);
            }
        }
        return count;
    }
    
    /**
     * 校验是否符合要求，即皇后不同行 不同列 不在一斜线上
     *    前置条件 y 行放置进去之前，y行之前的数据是合法的
     *    不能同行的情况，通过结构规避掉了
     * @param arr 棋盘
     * @param y   正在处理第y行
     * @param yx  第y行想放进去的列数
     * @return 是否能够放进去
     */
    private static boolean valid(int[] arr, int y, int yx) {
        // i 第几行
        for (int i = 0; i < y; i++) {
            // 不能同列
            int ix = arr[i];
            if (ix == yx) { return false; }
            // 不能斜线
            int offset = y - i;
            if (ix + offset == yx || ix - offset == yx) {
                return false;
            }
        }
        return true;
    }
}


class QueueProblem2 {

    public static void main(String[] args) {
        for (int n = 1; n <= 20; n++) {
            long start = System.currentTimeMillis();
            long count = queueProblem(n);
            long end = System.currentTimeMillis();
            System.out.println(n + " - " + count + "    - cost: " + (end - start));
        }
    }

    /**
     * 1 -         1    - cost: 0       - cost: 0
     * 2 -         0    - cost: 0       - cost: 0
     * 3 -         0    - cost: 0       - cost: 0
     * 4 -         2    - cost: 0       - cost: 0
     * 5 -        10    - cost: 0       - cost: 0
     * 6 -         4    - cost: 0       - cost: 0
     * 7 -        40    - cost: 0       - cost: 0
     * 8 -        92    - cost: 1       - cost: 1
     * 9 -       352    - cost: 0       - cost: 1
     * 10 -      724    - cost: 0       - cost: 5
     * 11 -     2680    - cost: 2       - cost: 21
     * 12 -    14200    - cost: 7       - cost: 110
     * 13 -    73712    - cost: 35      - cost: 635
     * 14 -   365596    - cost: 203     - cost: 3734
     * 15 -  2279184    - cost: 1284    - cost: 24233
     * 16 - 14772512    - cost: 8397    - cost: 173115
     * 17 - 95815104    - cost: 58819
     */
    public static long queueProblem(int n) {
        if (n < 1 || n > 32) { return 0; }
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return solution(limit, 0, 0,  0);
    }

    /**
     *
     * @param limit         划定了问题的规模 该值也包含了n的信息
     *                      基准 数值左移后会在多出来的位上产生1，与该值与后变为0
     * @param colLim        列限制，位为1不能放皇后
     * @param leftDiaLimit  左斜线限制，位为1不能放皇后
     * @param rightDiaLimit 右斜线限制，位为1不能放皇后
     * @return 解法
     */
    private static long solution(final int limit,
                                 final int colLim,
                                 final int leftDiaLimit,
                                 final int rightDiaLimit) {
        if (colLim == limit) { return 1; }
        int pos = limit & ( ~(colLim | leftDiaLimit | rightDiaLimit));
        int mostRightOne;
        int count = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos = pos ^ mostRightOne;
            count += solution(
                    limit,
                    colLim | mostRightOne,
                    (leftDiaLimit | mostRightOne) << 1,
                    (rightDiaLimit | mostRightOne) >>> 1
            );
        }
        return count;
    }
}

