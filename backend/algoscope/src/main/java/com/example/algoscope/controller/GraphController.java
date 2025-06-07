package com.example.algoscope.controller;

import com.example.algoscope.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/graph")
@CrossOrigin("*")
public class GraphController {

    @Autowired
    private GraphService graphService;

    @PostMapping("/addNode")
    public List<String> addNode(@RequestParam String node) {
        graphService.addNode(node);
        return graphService.getAllNodes();
    }

    @PostMapping("/addEdge")
    public List<String> addEdge(@RequestParam String from, @RequestParam String to) {
        graphService.addEdge(from, to);
        return graphService.getAllEdges();
    }

    @GetMapping("/bfs")
    public List<String> bfs(@RequestParam String start) {
        return graphService.bfs(start);
    }

    @GetMapping("/dfs")
    public List<String> dfs(@RequestParam String start) {
        return graphService.dfs(start);
    }
}