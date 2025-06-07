package com.example.algoscope.controller;

import com.example.algoscope.service.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sort")
@CrossOrigin("*")
public class SortController {

    @Autowired
    private SortingService sortingService;

    @PostMapping("/bubble")
    public List<int[]> bubbleSort(@RequestBody int[] input) {
        return sortingService.bubbleSort(input);
    }

    @PostMapping("/insertion")
    public List<int[]> insertionSort(@RequestBody int[] input) {
        return sortingService.insertionSort(input);
    }

    @PostMapping("/selection")
    public List<int[]> selectionSort(@RequestBody int[] input) {
        return sortingService.selectionSort(input);
    }

    @PostMapping("/merge")
    public List<int[]> mergeSort(@RequestBody int[] input) {
        return sortingService.mergeSort(input);
    }

    @PostMapping("/quick")
    public List<int[]> quickSort(@RequestBody int[] input) {
        return sortingService.quickSort(input);
    }
}