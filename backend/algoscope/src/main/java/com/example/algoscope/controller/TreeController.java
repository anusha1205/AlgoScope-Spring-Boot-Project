package com.example.algoscope.controller;

import com.example.algoscope.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tree")
@CrossOrigin("*")
public class TreeController {

    @Autowired
    private TreeService treeService;

    @PostMapping("/insert")
    public List<Integer> insert(@RequestParam int value) {
        treeService.insert(value);
        return treeService.inorderTraversal();
    }

    @GetMapping("/traverse/{type}")
    public List<Integer> traverse(@PathVariable String type) {
        return treeService.traverse(type);
    }
}