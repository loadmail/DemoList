package com.example.TestThreadPool.TestQueue;
public abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {
    private int priority;

    public PriorityRunnable(int priority) {
        if (priority < 0) {
            throw new IllegalArgumentException();
        }
        this.priority = priority;
    }

    @Override
    public int compareTo(PriorityRunnable another) {
        return another.getPriority() - priority;//不懂
    }



    public int getPriority() {
        return priority;
    }
}
