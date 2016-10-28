package com.example.fanXing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ly on 2016/10/20 13:00.
 */

public class ListGenerator<T extends List>
{
    private T[] fooArray;
    public T[] getfooArray(){
        return fooArray;
    }
    public void setFooArray(T[] fooArray){
        this.fooArray=fooArray;
    }
    public static void main(String[] args){
        ListGenerator<LinkedList>  foo1=new ListGenerator<LinkedList>();
        ListGenerator<ArrayList>   foo2=new ListGenerator<ArrayList>();
        LinkedList[] linkedLists=new LinkedList[10];
        foo1.setFooArray(linkedLists);
        ArrayList[] arrayLists=new ArrayList[10];
        foo2.setFooArray(arrayLists);
    }


}