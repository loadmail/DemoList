package com.finals.gpsprovider.util;

import android.os.Handler;
import android.os.Looper;
import android.widget.BaseAdapter;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.finals.gpsprovider.bean.CityItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityThread extends Thread implements MKOfflineMapListener {

    BaseAdapter sectionAdapter;

    ArrayList<CityItem> COUNTRIES;

    MKOfflineMap offlineMap;

    List<CityItem> lists;

    boolean isRun = true;

    Handler mHandler;

    CharacterParser parser;

    public CityThread(BaseAdapter sectionAdapter, ArrayList<CityItem> COUNTRIES) {
        this.sectionAdapter = sectionAdapter;
        this.COUNTRIES = COUNTRIES;
        offlineMap = new MKOfflineMap();
        offlineMap.init(this);
        lists = new ArrayList<CityItem>();
        mHandler = new Handler(Looper.getMainLooper());
        parser = new CharacterParser();
    }

    @Override
    public void run() {
        isRun = true;
        ArrayList<MKOLSearchRecord> resultsArrayList;
        try {
            resultsArrayList = offlineMap.getOfflineCityList();
        } catch (Exception e) {
            resultsArrayList = new ArrayList<MKOLSearchRecord>();
        }
        AddLists(resultsArrayList, lists);
        if (!isRun) {
            return;
        }
        Collections.sort(lists, new PinyinComparator());
        if (!isRun) {
            return;
        }
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                COUNTRIES.addAll(lists);
                sectionAdapter.notifyDataSetChanged();
            }
        });
    }

    private void AddLists(ArrayList<MKOLSearchRecord> resultsArrayList, List<CityItem> lists2) {
        for (int i = 0; i < resultsArrayList.size(); i++) {
            if (!isRun) {
                return;
            }
            MKOLSearchRecord itemMkolSearchRecord = resultsArrayList.get(i);

            MKOLSearchRecord cuRecord = resultsArrayList.get(i);
            CityItem item = new CityItem(cuRecord.cityName, cuRecord.cityID);
            item.setPinyin(parser.getSelling(item.getName()));
            if (cuRecord.cityType == 2) {
                lists.add(item);
            }
            if (itemMkolSearchRecord.childCities != null && itemMkolSearchRecord.childCities.size() > 0) {
                AddLists(itemMkolSearchRecord.childCities, lists2);
            }
        }
    }

    public void StopThread() {
        isRun = false;
        offlineMap.destroy();
    }

    @Override
    public void onGetOfflineMapState(int arg0, int arg1) {

    }

}
