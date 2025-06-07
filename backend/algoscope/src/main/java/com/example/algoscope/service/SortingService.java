package com.example.algoscope.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SortingService {

    public List<int[]> bubbleSort(int[] arr) {
        List<int[]> steps = new ArrayList<>();
        int[] a = arr.clone();

        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    steps.add(a.clone());
                }
            }
        }
        return steps;
    }

    public List<int[]> insertionSort(int[] arr) {
        List<int[]> steps = new ArrayList<>();
        int[] a = arr.clone();

        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j = j - 1;
                steps.add(a.clone());
            }
            a[j + 1] = key;
            steps.add(a.clone());
        }
        return steps;
    }

    public List<int[]> selectionSort(int[] arr) {
        List<int[]> steps = new ArrayList<>();
        int[] a = arr.clone();

        for (int i = 0; i < a.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = a[minIndex];
            a[minIndex] = a[i];
            a[i] = temp;
            steps.add(a.clone());
        }
        return steps;
    }

    public List<int[]> mergeSort(int[] arr) {
        List<int[]> steps = new ArrayList<>();
        int[] a = arr.clone();
        mergeSortHelper(a, 0, a.length - 1, steps);
        return steps;
    }

    private void mergeSortHelper(int[] a, int left, int right, List<int[]> steps) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortHelper(a, left, mid, steps);
            mergeSortHelper(a, mid + 1, right, steps);
            merge(a, left, mid, right, steps);
        }
    }

    private void merge(int[] a, int left, int mid, int right, List<int[]> steps) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (a[i] <= a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }

        while (i <= mid) temp[k++] = a[i++];
        while (j <= right) temp[k++] = a[j++];

        for (int l = 0; l < temp.length; l++) {
            a[left + l] = temp[l];
        }

        steps.add(a.clone()); // Record final merged step
    }

    public List<int[]> quickSort(int[] arr) {
        List<int[]> steps = new ArrayList<>();
        int[] a = arr.clone();
        quickSortHelper(a, 0, a.length - 1, steps);
        return steps;
    }

    private void quickSortHelper(int[] a, int low, int high, List<int[]> steps) {
        if (low < high) {
            int pi = partition(a, low, high, steps);
            quickSortHelper(a, low, pi - 1, steps);
            quickSortHelper(a, pi + 1, high, steps);
        }
    }

    private int partition(int[] a, int low, int high, List<int[]> steps) {
        int pivot = a[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (a[j] < pivot) {
                i++;
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                steps.add(a.clone());
            }
        }

        int temp = a[i + 1];
        a[i + 1] = a[high];
        a[high] = temp;
        steps.add(a.clone());
        return i + 1;
    }
}
