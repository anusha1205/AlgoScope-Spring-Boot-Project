    package com.example.algoscope.controller;

    import com.example.algoscope.service.GraphService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Map;
    import java.util.Set;

    @RestController
    @RequestMapping("/api/graph")
    @CrossOrigin("*")
    public class GraphController {

        @Autowired
        private GraphService graphService;

        @PostMapping("/reset")
        public void reset() {
            graphService.reset();
        }

        @PostMapping("/addNode")
        public Set<String> addNode(@RequestParam String node) {
            return graphService.addNode(node);
        }

        @PostMapping("/addEdge")
        public Set<String> addEdge(@RequestParam String from, @RequestParam String to) {
            return graphService.addEdge(from, to);
        }

        @GetMapping("/structure")
        public Map<String, Set<String>> getStructure() {
            return graphService.getStructure();
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
