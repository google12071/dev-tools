package com.learn.datastructure;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName SinglyLinkedList
 * @Description:单链表（为描述问题，简单直接存储整数）
 * @Author lfq
 * @Date 2020/2/15
 **/
public class SinglyLinkedList {
    /**
     * 链表表头
     */
    private Node head;

    /**
     * 链表表尾部
     */
    private Node tail;

    /**
     * 结点抽象
     */
    private static class Node {
        private int val;
        private Node next;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }


        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

    public void add(int val) {
        Node node = new Node(val);
        if (head == null) {
            head = node;
        } else {
            Node prev = tail;
            prev.next = node;
        }
        tail = node;
    }

    /**
     * 删除元素
     *
     * @param val
     */
    public void remove(int val) {
        /**
         * 从头开始定位
         */
        Node node = head;
        Node prev = null;
        while (node != null && (node.val != val)) {
            prev = node;
            node = node.next;
        }

        if (node == null) {
            return;
        }
        // 删除表尾
        if (node.equals(tail)) {
            tail = prev;
            if (prev != null) {
                prev.next = null;
            }
        }

        Node next = node.next;
        if (prev != null && next != null) {
            prev.next = next;
        } else if (prev != null) {
            prev.next = null;
        } else if (next != null) {
            head = next;
        } else {
            head = null;
        }
    }

    public void display() {
        Node current = head;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    /**
     * 移除未排序链表中的重复节点。保留最开始出现的节点。
     */
    public void removeDuplicateNodes() {
        if (head == null) {
            return;
        }
        Node prev = null;
        Node current = head;
        Set<Integer> nodeSet = new HashSet<>();
        while (current != null) {
            if (nodeSet.add(current.val)) {
                prev = current;
            } else {
                if (prev != null) {
                    prev.next = current.next;
                }
            }
            current = current.next;
        }
    }

    /**
     * 链表逆置
     * 1、链表为空或只有一个元素直接返回；
     * 2、设置两个前后相邻的指针p,q，使得p指向的节点为q指向的节点的后继；
     * 3、重复步骤2，直到q为空；
     * 4、调整链表头和链表尾；
     */
    public void reverse() {
        //链表为空，或只有一个元素直接返回
        if (head == null || head.next == null) {
            return;
        }

        Node p = head.next;
        Node q = head.next.next;
        Node t = null;
        while (q != null) {
            t = q.next;
            q.next = p;
            p = q;
            q = t;
        }
        /**
         * 表头和表尾部处理
         */
        Node tmp = head;
        head = tail;
        tmp.next.next = tmp;
        tmp.next = null;
    }


    public static void main(String[] args) {
        SinglyLinkedList linkedList = new SinglyLinkedList();
        linkedList.add(9);
        linkedList.add(10);
        linkedList.add(13);
        linkedList.add(8);
        linkedList.add(0);
        linkedList.add(5);
        linkedList.add(1);
        linkedList.reverse();
        linkedList.display();
    }
}
