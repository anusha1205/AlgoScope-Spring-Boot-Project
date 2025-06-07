package com.example.algoscope.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {

    private final Map<String, List<String>> adjList = new HashMap<>();

    public void addNode(String node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String from, String to) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.putIfAbsent(to, new ArrayList<>());
        adjList.get(from).add(to);
        adjList.get(to).add(from); // undirected
    }

    public List<String> getAllNodes() {
        return new ArrayList<>(adjList.keySet());
    }

    public List<String> getAllEdges() {
        List<String> edges = new ArrayList<>();
        for (String node : adjList.keySet()) {
            for (String neighbor : adjList.get(node)) {
                edges.add(node + " -> " + neighbor);
            }
        }
        return edges;
    }

    public List<String> bfs(String start) {
        List<String> visitedOrder = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            visitedOrder.add(current);
            for (String neighbor : adjList.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return visitedOrder;
    }

    public List<String> dfs(String start) {
        List<String> visitedOrder = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfsHelper(start, visited, visitedOrder);
        return visitedOrder;
    }

    private void dfsHelper(String node, Set<String> visited, List<String> order) {
        if (visited.contains(node)) return;
        visited.add(node);
        order.add(node);
        for (String neighbor : adjList.getOrDefault(node, Collections.emptyList())) {
            dfsHelper(neighbor, visited, order);
        }
    }
}
