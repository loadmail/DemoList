package com.finals.gpsprovider.util;

import java.util.Comparator;

import com.finals.gpsprovider.bean.CityItem;

public class PinyinComparator implements Comparator<CityItem> {

	public int compare(CityItem o1, CityItem o2) {
		return o1.getPinyin().compareTo(o2.getPinyin());
	}

}
