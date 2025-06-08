package com.example.algoscope.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {

    private final Map<String, Set<String>> graph = new HashMap<>();

    public void reset() {
        graph.clear();
    }

    public Set<String> addNode(String node) {
        graph.putIfAbsent(node, new HashSet<>());
        return graph.keySet();
    }

    public Set<String> addEdge(String from, String to) {
        graph.putIfAbsent(from, new HashSet<>());
        graph.putIfAbsent(to, new HashSet<>());
        graph.get(from).add(to);
        graph.get(to).add(from); // undirected
        return getEdges();
    }

    public Map<String, Set<String>> getStructure() {
        return graph;
    }

    public Set<String> getEdges() {
        Set<String> edges = new LinkedHashSet<>();
        for (String from : graph.keySet()) {
            for (String to : graph.get(from)) {
                String edge = from.compareTo(to) < 0 ? from + "-" + to : to + "-" + from;
                edges.add(edge);
            }
        }
        return edges;
    }

    public List<String> bfs(String start) {
        List<String> result = new ArrayList<>();
        if (!graph.containsKey(start)) return result;

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            result.add(curr);

            for (String neighbor : graph.get(curr)) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return result;
    }

    public List<String> dfs(String start) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfsHelper(start, visited, result);
        return result;
    }

    private void dfsHelper(String node, Set<String> visited, List<String> result) {
        if (!graph.containsKey(node) || visited.contains(node)) return;
        visited.add(node);
        result.add(node);
        for (String neighbor : graph.get(node)) {
            dfsHelper(neighbor, visited, result);
        }
    }
}
