package com.slin.study.kotlin;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

/**
 * author: slin
 * date: 2020/11/10
 * description:
 */
public class LeetcodeTest {

    @Test
    public void test() {
        Solution solution = new Solution();
//        System.out.println(solution.lengthOfLongestSubstring("abcabcbb"));
//        System.out.println(solution.lengthOfLongestSubstring("bbbbb"));
//        System.out.println(solution.lengthOfLongestSubstring("pwwkew"));
//        System.out.println(solution.lengthOfLongestSubstring(""));
//        System.out.println(solution.lengthOfLongestSubstring("a"));
//        System.out.println(solution.lengthOfLongestSubstring("aa"));
//        System.out.println(solution.lengthOfLongestSubstring("ab"));
        System.out.println(Arrays.toString(solution.twoSum1(new int[]{1, 2, 2, 2, 5, 3, 9}, 7)));
        System.out.println(Arrays.toString(solution.twoSum1(new int[]{1, 2, 2, 2, 5, 3, 9}, 8)));
        System.out.println(Arrays.toString(solution.twoSum1(new int[]{1, 2, 2, 2, 5, 3, 9}, 4)));
    }

    static class Solution {
        /**
         * 无重复字符的最长子串
         *
         * @param s 字符串
         * @return 最长子串长度
         */
        public int lengthOfLongestSubstring(String s) {
            int ans = 0, n = s.length();
            HashMap<Character, Integer> map = new HashMap<>();
            for (int start = 0, end = 0; end < n; end++) {
                Character character = s.charAt(end);
                Integer value = map.get(character);
                if (value != null) {
                    start = Math.max(start, value);
                }
                map.put(character, end + 1);
                ans = Math.max(ans, end - start + 1);
            }

            return ans;

        }

        /**
         * 两数之和暴力破解，超时
         * O(n^2)   O(1)   超时
         */
        public int[] twoSum(int[] nums, int target) {
            int n = nums.length;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (nums[i] + nums[j] == target) {
                        return new int[]{i, j};
                    }
                }
            }
            return new int[]{0, 0};
        }


        /**
         * 两数之和，排序之后再用双指针法找到对应的数值，再找到所在位置
         * O(nlog(n))   O(n)    10ms
         */
        public int[] twoSum1(int[] nums, int target) {
            int[] ans = new int[2];
            int[] sortNums = new int[nums.length];
            System.arraycopy(nums, 0, sortNums, 0, nums.length);
            Arrays.sort(sortNums);
            int num1 = 0, num2 = 0;
            for (int i = 0, j = sortNums.length - 1; i < j; ) {
                if (sortNums[i] + sortNums[j] < target) {
                    i++;
                } else if (sortNums[i] + sortNums[j] > target) {
                    j--;
                } else {
                    num1 = sortNums[i];
                    num2 = sortNums[j];
                    break;
                }
            }

            for (int i = 0; i < nums.length; i++) {
                if (num1 == nums[i]) {
                    ans[0] = i;
                    break;
                }
            }

            for (int i = 0; i < nums.length; i++) {
                if (num2 == nums[i] && ans[0] != i) {
                    ans[1] = i;
                }
            }

            return ans;
        }

        /**
         * 两数之和，使用HashMap记录已经寻找过的数据的位置，后面如果能直接获取target-num1的数据，则找到了
         * O(n) O(n) 15ms（应该是因为HashMap插入和查找数据慢了一点）
         */
        public int[] twoSum2(int[] nums, int target) {
            int[] ans = new int[2];
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                int value = nums[i];
                if (map.containsKey(target - value)) {
                    ans[0] = map.get(target - value);
                    ans[1] = i;
                    return ans;
                } else {
                    map.put(value, i);
                }

            }
            return ans;
        }

    }

}
