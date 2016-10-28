package com.example.fanXing;

/**
 * Created by ly on 2016/10/20 12:00.
 */

public class Container<K,V>{
    private K key;
    private V value;

    public Container() {

    }
    public Container(K k,V v){
        key=k;
        value=v;
    }
    public K getKey(){
        return key;
    }
    public V getValue(){
        return value;
    }
    public void setKey(){
        this.key=key;
    }
    public void setValue(){
        this.value=value;
    }
}

