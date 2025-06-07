package com.example.algoscope.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreeService {

    private TreeNode root;

    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode node, int value) {
        if (node == null)
            return new TreeNode(value);
        if (value < node.val)
            node.left = insertRec(node.left, value);
        else
            node.right = insertRec(node.right, value);
        return node;
    }

    public List<Integer> traverse(String type) {
        switch (type.toLowerCase()) {
            case "inorder":
                return inorderTraversal();
            case "preorder":
                return preorderTraversal();
            case "postorder":
                return postorderTraversal();
            default:
                return new ArrayList<>();
        }
    }

    public List<Integer> inorderTraversal() {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    public List<Integer> preorderTraversal() {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    public List<Integer> postorderTraversal() {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    private void inorder(TreeNode node, List<Integer> res) {
        if (node == null)
            return;
        inorder(node.left, res);
        res.add(node.val);
        inorder(node.right, res);
    }

    private void preorder(TreeNode node, List<Integer> res) {
        if (node == null)
            return;
        res.add(node.val);
        preorder(node.left, res);
        preorder(node.right, res);
    }

    private void postorder(TreeNode node, List<Integer> res) {
        if (node == null)
            return;
        postorder(node.left, res);
        postorder(node.right, res);
        res.add(node.val);
    }
}