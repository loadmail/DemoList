package com.example;

/**
 * Created by Administrator on 2016/5/5.
 */
public class Test {
    public static int count = 0;
private FaceListener listener;

    static class MyListener implements FaceListener {

        @Override
        public void setNub(int nub) {

        }
    }


    protected static final int num = 0;
    //构造
    public Test(){
        count ++;
        listener.setNub(count);
    }

    private void setListener(FaceListener listener) {
        this.listener = listener;
    }

    public static void main(String[] args) {
        MyListener listener = new MyListener();
        Test test = new Test();
        test.setListener(listener);
    }
}
