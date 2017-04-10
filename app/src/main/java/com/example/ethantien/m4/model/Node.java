package com.example.ethantien.m4.model;

/**
 * Created by ethantien on 4/9/17.
 * Node for the Graphing SparseArray to keep track of a month and its count
 * so it can average them at the end.
 */

public class Node {
    private Double value;
    private int count;

    public Node(Double val) {
        value = val;
        count = 1;
    }
    public Double getValue() {
        return value;
    }
    public int getCount() {
        return count;
    }
    public void addValue(Double num) {
        value += num;
    }
    public void incrementCount() {
        count++;
    }
    public String toString() {
        return "Node: {Value:" + value + ", Count:" + count + "}";
    }
}