package com.finals.gpsprovider.base;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.finals.gpsprovider.R;
import com.finals.gpsprovider.SettingActivity;
import com.finals.gpsprovider.util.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PoiSearchAdapter extends BaseAdapter implements OnGetPoiSearchResultListener {

	Context context;

	PoiSearch mPoiSearch;

	PoiCitySearchOption mCitySearchOption;

	List<PoiInfo> mList;

	public PoiSearchAdapter(Context context) {
		this.context = context;
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);

		mList = new ArrayList<PoiInfo>();
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public PoiInfo getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		PoiInfo info = mList.get(position);
		if (view == null || view.getTag() == null) {
			view = LayoutInflater.from(context).inflate(R.layout.list_item_search, null);
		}
		TextView titleTextView = Util.getHolderView(view, R.id.title);
		titleTextView.setText(info.name);

		TextView contentTextView = Util.getHolderView(view, R.id.content);
		contentTextView.setText(info.address);

		return view;
	}

	public void onDestroy() {
		mPoiSearch.destroy();
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {

	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		// Finals 2016-4-16
		if (context instanceof SettingActivity) {
			((SettingActivity) context).stopAnimation();
		}
		if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
			List<PoiInfo> resultList = result.getAllPoi();
			if (resultList != null) {
				mList.clear();
				mList.addAll(resultList);
				notifyDataSetChanged();
			}
		}
	}

	public void searchInCity(String keywords, String city) {
		if (mCitySearchOption == null) {
			mCitySearchOption = new PoiCitySearchOption();
		}
		mCitySearchOption.city(city);
		mCitySearchOption.keyword(keywords);
		mPoiSearch.searchInCity(mCitySearchOption);
	}
}
