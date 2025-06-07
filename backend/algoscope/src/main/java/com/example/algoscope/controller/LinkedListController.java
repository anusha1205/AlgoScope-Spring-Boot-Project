package com.example.algoscope.controller;

import com.example.algoscope.service.LinkedListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ll")
@CrossOrigin("*")
public class LinkedListController {

    @Autowired
    private LinkedListService linkedListService;

    @PostMapping("/insertHead")
    public List<Integer> insertAtHead(@RequestParam int value) {
        linkedListService.insertAtHead(value);
        return linkedListService.traverse();
    }

    @PostMapping("/insertTail")
    public List<Integer> insertAtTail(@RequestParam int value) {
        linkedListService.insertAtTail(value);
        return linkedListService.traverse();
    }

    @DeleteMapping("/delete/{index}")
    public List<Integer> deleteAtIndex(@PathVariable int index) {
        linkedListService.deleteAtIndex(index);
        return linkedListService.traverse();
    }

    @GetMapping("/traverse")
    public List<Integer> traverse() {
        return linkedListService.traverse();
    }
}