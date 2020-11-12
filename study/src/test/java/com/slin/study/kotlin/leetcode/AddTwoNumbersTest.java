package com.slin.study.kotlin.leetcode;

import org.junit.Test;

/**
 * author: slin
 * date: 2020/11/12
 * description:
 */
public class AddTwoNumbersTest {
    @Test
    public void addTwoNumbersTest() {
        int[] array1 = {9, 9, 9, 9, 9, 9, 9};
        int[] array2 = {9, 9, 9, 9};

        ListNode test1 = createListNode(array1);
        ListNode test2 = createListNode(array2);
        printListNode(test1);
        printListNode(test2);

        Solution solution = new Solution();
        ListNode result = solution.addTwoNumbers(test1, test2);

        printListNode(result);
    }

    private ListNode createListNode(int[] array) {
        ListNode result = new ListNode(array[0]);
        ListNode next = result;
        for (int j : array) {
            ListNode node = new ListNode(j);
            next.next = node;
            next = node;
        }
        return result;
    }

    private void printListNode(ListNode node) {
        ListNode printNode = node;
        while (printNode != null) {
            System.out.print(printNode.val + (printNode.next != null ? "->" : ""));
            printNode = printNode.next;
        }
        System.out.println();
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    static class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

            ListNode head = addVal(null, l1, l2);

            ListNode cur = head;
            ListNode next = cur.next;
            while (l1.next != null && l2.next != null) {
                ListNode l = addVal(next, l1.next, l2.next);
                cur.next = l;

                cur = l;
                next = l.next;

                l1 = l1.next;
                l2 = l2.next;
            }


            while (l1.next != null) {
                ListNode l = addVal(next, l1.next);
                cur.next = l;

                cur = l;
                next = l.next;

                l1 = l1.next;

            }

            while (l2.next != null) {
                ListNode l = addVal(next, l2.next);
                cur.next = l;

                cur = l;
                next = l.next;

                l2 = l2.next;
            }

            return head;
        }

        private ListNode addVal(ListNode carryNode, ListNode l1, ListNode l2) {
            int val = l1.val + l2.val;
            if (carryNode != null) {
                val += carryNode.val;
            }
            ListNode node = new ListNode(val);
            if (val >= 10) {
                node.next = new ListNode(1);
                node.val -= 10;
            }
            return node;
        }

        private ListNode addVal(ListNode carryNode, ListNode l1) {
            int val = l1.val;
            if (carryNode != null) {
                val += carryNode.val;
            }
            ListNode node = new ListNode(val);
            if (val >= 10) {
                node.next = new ListNode(1);
                node.val -= 10;
            }
            return node;
        }

    }


}
