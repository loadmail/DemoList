package com.example.stack;

/**
 * Created by lizhichao on 16/11/23.
 */

public class StackTest {
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(11);
        stack.push(12);
        stack.peek();
        stack.pop();
        stack.isEmpty();
    }
}
