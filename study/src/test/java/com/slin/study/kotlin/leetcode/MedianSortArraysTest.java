package com.slin.study.kotlin.leetcode;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

//给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
//
// 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
//
//
//
// 示例 1：
//
// 输入：nums1 = [1,3], nums2 = [2]
//输出：2.00000
//解释：合并数组 = [1,2,3] ，中位数 2
//
//
// 示例 2：
//
// 输入：nums1 = [1,2], nums2 = [3,4]
//输出：2.50000
//解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
//
//
// 示例 3：
//
// 输入：nums1 = [0,0], nums2 = [0,0]
//输出：0.00000
//
//
// 示例 4：
//
// 输入：nums1 = [], nums2 = [1]
//输出：1.00000
//
//
// 示例 5：
//
// 输入：nums1 = [2], nums2 = []
//输出：2.00000
//
//
//
//
// 提示：
//
//
// nums1.length == m
// nums2.length == n
// 0 <= m <= 1000
// 0 <= n <= 1000
// 1 <= m + n <= 2000
// -106 <= nums1[i], nums2[i] <= 106
//
// Related Topics 数组 二分查找 分治算法
// 👍 3392 👎 0


/**
 * author: slin
 * date: 2021/1/26
 * description: 寻找两个正序数组的中位数
 */
public class MedianSortArraysTest {

    public static double findMedianSortedArrays2(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) {
            return findMedianSortedArrays2(B, A); // 保证 m <= n
        }
        int iMin = 0, iMax = m;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = (m + n + 1) / 2 - i;
            if (j != 0 && i != m && B[j - 1] > A[i]) { // i 需要增大
                iMin = i + 1;
            } else if (i != 0 && j != n && A[i - 1] > B[j]) { // i 需要减小
                iMax = i - 1;
            } else { // 达到要求，并且将边界条件列出来单独考虑
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = B[j - 1];
                } else if (j == 0) {
                    maxLeft = A[i - 1];
                } else {
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                } // 奇数的话不需要考虑右半部分

                int minRight = 0;
                if (i == m) {
                    minRight = B[j];
                } else if (j == n) {
                    minRight = A[i];
                } else {
                    minRight = Math.min(B[j], A[i]);
                }

                return (maxLeft + minRight) / 2.0; //如果是偶数的话返回结果
            }
        }
        return 0.0;
    }

    public static double findMedianSortedArrays(@NotNull int[] nums1, @NotNull int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            return getKthElement(nums1, nums2, midIndex + 1);
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            return (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
        }
    }

    /**
     * 从两个正向排序的数组中 找到 第k小的数
     * <p>
     * 1. 从两个数组中分别取 p1 = nums1[k/2-1] 和 p2 = nums2[k/2-1]比较（这里分别取k/2个数，共k个，保证较小的前k/2个数不会超过k个）
     * 2. 如果 p1 <= p2，那么说明 nums1 小于等于 p1 的有 k/2 个，将 nums1 前 k/2个裁剪掉，这样就排除掉了 k/2 个，再找k = k - k / 2 小的数
     * 3. 如果 p1 > p2，那么说明 nums2 小于等于 p2 的有 k/2个，将 nums2 前 k/2个裁剪掉，这样就排除掉了 k/2 个，再找 k = k - k / 2 小的数
     * <p>
     * 4. 如果 k == 1 了，那么取 nums1 和 nums2中较小的数
     * 5. 如果 nums1 全部排除了，那么取 nums2 往后 剩下的 k 个，或者说是 k - nums1.length个
     */
    public static int getKthElement(int[] nums1, int[] nums2, int k) {
        int index1 = 0, index2 = 0;


        while (true) {

            if (index1 >= nums1.length) {
                return nums2[index2 + k - 1];
            }

            if (index2 >= nums2.length) {
                return nums1[index1 + k - 1];
            }

            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }

            int half = k / 2;
            int newIndex1 = Math.min(index1 + half, nums1.length) - 1;
            int newIndex2 = Math.min(index2 + half, nums2.length) - 1;

            if (nums1[newIndex1] < nums2[newIndex2]) {
                k -= newIndex1 - index1 + 1;
                index1 = newIndex1 + 1;
            } else {
                k -= newIndex2 - index2 + 1;
                index2 = newIndex2 + 1;
            }

        }


    }

    @Test
    public void test() {
        int[] nums1 = {1, 1, 3, 3, 5, 7, 8, 9, 10, 23, 25, 29, 30};
        int[] nums2 = {1, 7};
        System.out.println(findMedianSortedArrays2(nums1, nums2));
    }


}
