package com.example.stack;

import java.util.Arrays;

/**  轻松学算法  博客:irfen.me
 * Created by lizhichao on 16/11/23.
 */

public class Stack {
    private int[] array;
    public int size = 0;

    public Stack() {
        array = new int[size];
    }

    public Stack(int num) {
        if (num <= 0) {
            num = 10;
        }
        array = new int[num];
    }

    /**
     * 栈顶
     */
    public int peek() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("栈里已经空");
        }
        return array[size - 1];

    }

    /**
     * 压栈
     */
    public void push(int item) {
        if (size == array.length) {
            array = Arrays.copyOf(array, 2 * size);  //// TODO: 16/11/23 扩容
        }

        array[size++] = item;
    }

    /**
     * 出栈
     */
    public int pop() {
        int item = peek();
        size--;  //TODO: 16/11/23  直接使元素个数减一,不需要清除元素,下次入栈会覆盖旧元素
        return item;
    }

    /**
     大小
     * @return
     */
    public int size() {
        return size;
    }

    /**
     为空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     栈满
     * @return
     */
    public boolean isFull() {
        return size == array.length;
    }
}
