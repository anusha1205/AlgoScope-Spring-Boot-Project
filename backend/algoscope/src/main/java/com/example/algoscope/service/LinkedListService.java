package com.example.algoscope.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkedListService {

    private Node head;

    private static class Node {
        int val;
        Node next;
        Node(int val) { this.val = val; }
    }

    public void insertAtHead(int value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
    }

    public void insertAtTail(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
            return;
        }
        Node curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = newNode;
    }

    public void deleteAtIndex(int index) {
        if (head == null || index < 0) return;
        if (index == 0) {
            head = head.next;
            return;
        }
        Node curr = head;
        for (int i = 0; curr != null && i < index - 1; i++) {
            curr = curr.next;
        }
        if (curr == null || curr.next == null) return;
        curr.next = curr.next.next;
    }

    public List<Integer> traverse() {
        List<Integer> result = new ArrayList<>();
        Node curr = head;
        while (curr != null) {
            result.add(curr.val);
            curr = curr.next;
        }
        return result;
    }
}