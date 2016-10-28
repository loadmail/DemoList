package com.example.li.demo.RecyclerViewDemo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/1.
 */
public class DataModel {

  public static ArrayList<SampleModel> getData(int size) {
      ArrayList<SampleModel> list = new ArrayList<SampleModel>();
        for (int i = 0; i <size ; i++) {
          list.add(new SampleModel("新的列表"+i));
        }
        return list;
    }
}
