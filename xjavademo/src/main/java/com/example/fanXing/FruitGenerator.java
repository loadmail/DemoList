package com.example.fanXing;

import java.util.Random;

/**
 * Created by ly on 2016/10/20 12:09.
 */

public class FruitGenerator implements Generator<String> {
    private String[] fruits = new String[]{"Apple", "Banana", "Pear"};

    @Override
    public String next() {
        Random rand = new Random();
        return fruits[rand.nextInt(3)];
    }
}
