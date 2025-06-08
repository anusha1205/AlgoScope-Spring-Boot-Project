package com.example.algoscope.controller;

import com.example.algoscope.dto.TreeNodeDTO;
import com.example.algoscope.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.swing.tree.TreeNode;

@RestController
@RequestMapping("/api/tree")
@CrossOrigin("*")
public class TreeController {

    @Autowired
    private TreeService treeService;

    @PostMapping("/reset")
    public void resetTree() {
        treeService.reset();
    }

    @PostMapping("/insert")
    public List<Integer> insert(@RequestParam int value) {
        treeService.insert(value);
        return treeService.inorderTraversal();
    }

    @GetMapping("/traverse/{type}")
    public List<Integer> traverse(@PathVariable String type) {
        return treeService.traverse(type);
    }

    @GetMapping("/structure")
    public TreeNodeDTO getStructure() {
        return treeService.getTreeStructure();
    }

}