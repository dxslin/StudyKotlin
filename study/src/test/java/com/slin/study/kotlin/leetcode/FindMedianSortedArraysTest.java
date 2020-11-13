package com.slin.study.kotlin.leetcode;

import org.junit.Test;

/**
 * author: slin
 * date: 2020/11/12
 * description:
 */
public class FindMedianSortedArraysTest {


    @Test
    public void findMedianSortedArraysTest() {
        int[] test1 = {};
        int[] test2 = {1, 2, 5};


        Solution solution = new Solution();
        System.out.println(solution.findMedianSortedArrays(test1, test2));
    }

    class Solution {

//        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
//            int n = (int) Math.ceil((nums1.length + nums2.length) / 2);
//
//            int s1Start = 0;
//            int s2Start = 0;
//            int value1 = 0, value2 = 0;
//
//            for (int i = 0; i < n; i++) {
//                if(s1Start < nums1.length && s2Start < nums2.length){
//                    if (nums1[s1Start] < nums2[s2Start]) {
//                        value1 = nums1[s1Start];
//                        s1Start++;
//                    } else {
//                        value1 = nums2[s2Start];
//                        s2Start++;
//                    }
//                } else if(s1Start < nums1.length){
//
//                }
//
//            }
//
//            return (value1 + value2) / 2.0;
//        }


        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int start = 0;
            int end = nums1.length + nums2.length - 1;

            if (nums1.length == 0) {
                nums1 = nums2;
            }
            if (nums2.length == 0) {
                nums2 = nums1;
            }

            int s1Start = 0;
            int s2Start = 0;
            int s1End = nums1.length - 1;
            int s2End = nums2.length - 1;

            int value1 = 0, value2 = 0;

            while (start <= end) {

                if (nums1[s1Start] < nums2[s2Start]) {
                    value1 = nums1[s1Start];
                    s1Start++;
                } else {
                    value1 = nums2[s2Start];
                    s2Start++;
                }
                start++;

                if (nums1[s1End] > nums2[s2End]) {
                    value2 = nums1[s1End];
                    s1End--;
                } else {
                    value2 = nums2[s2End];
                    s2End--;
                }
                end--;
            }

            return (value1 + value2) / 2.0;
        }
    }

}
