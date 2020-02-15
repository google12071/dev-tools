package com.learn.datastructure;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName SinglyLinkedList
 * @Description:单链表
 * @Author lfq
 * @Date 2020/2/15
 **/
public class SinglyLinkedList<T> {

    private int size = 0;
    /**
     * 链表表头
     */
    private Node<T> head;

    /**
     * 链表表尾部
     */
    private Node<T> tail;

    /**
     * 结点抽象
     *
     * @param <T>
     */
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public void add(T data) {
        add(new Node<>(data));
    }

    /**
     * 后插法
     *
     * @param node
     */
    private void add(Node<T> node) {
        if (head == null) {
            head = node;
        } else {
            Node<T> prev = tail;
            prev.next = node;
        }
        tail = node;
        size++;
    }

    /**
     * 删除元素
     * @param data
     */
    public void remove(T data) {
        /**
         * 从头开始定位
         */
        Node<T> node = head;
        Node<T> prev = null;
        while (node != null && (!node.data.equals(data))) {
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

        Node<T> next = node.next;
        if (prev != null && next != null) {
            prev.next = next;
        } else if (prev != null) {
            prev.next = null;
        } else if (next != null) {
            head = next;
        } else {
            head = null;
        }
        size--;
    }

    public void display() {
        Node<T> current = head;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    public int size() {
        return size;
    }

    /**
     *移除未排序链表中的重复节点。保留最开始出现的节点。
     */
    public void removeDuplicateNodes(){
        if (head == null) {
            return;
        }
        Node<T> prev = null;
        Node<T> current = head;
        Set<T> nodeSet = new HashSet<>();
        while (current != null) {
            if (nodeSet.add(current.data)) {
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

        Node<T> p = head.next;
        Node<T> q = head.next.next;
        Node<T> t = null;
        while (q != null) {
            t = q.next;
            q.next = p;
            p = q;
            q = t;
        }
        /**
         * 表头和表尾部处理
         */
        Node<T> tmp = head;
        head = tail;
        tmp.next.next = tmp;
        tmp.next = null;
    }


    public static void main(String[]args){
        SinglyLinkedList<Integer> linkedList = new SinglyLinkedList<>();
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
