package com.finals.gpsprovider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.finals.gpsprovider.base.BaseActivity;
import com.finals.gpsprovider.base.PoiSearchAdapter;
import com.finals.gpsprovider.util.InputMethodLayout;
import com.finals.gpsprovider.util.InputMethodLayout.onKeyboardsChangeListener;
import com.finals.gpsprovider.util.SimulateLocationManager;
import com.finals.gpsprovider.util.Util;
import com.finals.gpsprovider.view.FMapView;
import com.finals.gpsprovider.view.SimulationGPSDialog;

/**
 * 各种接口的的实现
 */
public class SettingActivity extends BaseActivity implements
		OnMapLoadedCallback, OnMapStatusChangeListener, OnGetGeoCoderResultListener, TextWatcher, OnItemClickListener,
	//软键盘监听
		onKeyboardsChangeListener, OnClickListener {

	GeoCoder mGeocoder;

	ReverseGeoCodeOption mReverseGeoCodeOption;

	LatLng currentLatLng;

	FMapView mFMapView;

	View locationTitleView;

	TextView locationTextView;
	TextView locationContentTextView;
	ListView mSearchListView;
	EditText mSearchEditText;
	TextView selectCityTextView;
	View startScanView;
	View stopScanView;
	View searchLoadingView;

	String cityName = "北京市";

	PoiSearchAdapter mSearchAdapter;

	InputMethodLayout mInputLayout;

	SimulateLocationManager mLocationManager;

	Animation searchAnimation;

	SimulationGPSDialog mGpsDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		InitView();
		InitData();
	}

	private void InitData() {
		mGeocoder = GeoCoder.newInstance();
		mGeocoder.setOnGetGeoCodeResultListener(this);

		mSearchAdapter = new PoiSearchAdapter(this);
		mSearchListView.setAdapter(mSearchAdapter);
		mSearchListView.setOnItemClickListener(this);

		searchAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		searchAnimation.setDuration(500);
		searchAnimation.setRepeatCount(-1);

		mLocationManager = new SimulateLocationManager(this);

		RefreshCity();
	}

	private void InitView() {
		mInputLayout = new InputMethodLayout(this);
		mInputLayout.setOnkeyboarddStateListener(this);

		mFMapView = (FMapView) findViewById(R.id.map_view);
		mFMapView.setOnMapLoadedCallback(this);
		mFMapView.setOnMapStatusChangeListener(this);

		locationTitleView = findViewById(R.id.location_title);
		locationTextView = (TextView) findViewById(R.id.content_title);
		locationContentTextView = (TextView) findViewById(R.id.content_content);

		mSearchEditText = (EditText) findViewById(R.id.search_edit);
		mSearchEditText.addTextChangedListener(this);

		mSearchListView = (ListView) findViewById(R.id.search_result_list);

		searchLoadingView = findViewById(R.id.search_loading);

		startScanView = findViewById(R.id.start_scare);
		startScanView.setOnClickListener(this);

		stopScanView = findViewById(R.id.stop_scare);
		stopScanView.setOnClickListener(this);

		selectCityTextView = (TextView) findViewById(R.id.select_city);
		selectCityTextView.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		if (mFMapView != null) {
			mFMapView.onPause();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mFMapView != null) {
			mFMapView.onResume();
		}
		if (Util.isGPSClose(this) && Util.isSimulationLocation(this)) {
			if (mGpsDialog != null) {
				mGpsDialog.dismiss();
			}
		} else{
			if (mGpsDialog != null && mGpsDialog.isShowing()) {
				mGpsDialog.InitData();
			}
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (mFMapView != null) {
			mFMapView.onDestroy();
		}
		if (mGeocoder != null) {
			mGeocoder.destroy();
			mGeocoder = null;
		}
		if (mGpsDialog != null && mGpsDialog.isShowing()) {
			mGpsDialog.dismiss();
		}
		mInputLayout.OnDestory();
		mSearchAdapter.onDestroy();
		mLocationManager.onDestroy();
		super.onDestroy();
	}

	/**
	 * setOnMapLoadedCallback
	 */
	@Override
	public void onMapLoaded() {

	}

	public void reverseGeoCode(LatLng mLatLng) {
		if (mGeocoder != null) {
			if (mReverseGeoCodeOption == null) {
				mReverseGeoCodeOption = new ReverseGeoCodeOption();
			}
			mReverseGeoCodeOption.location(mLatLng);
			mGeocoder.reverseGeoCode(mReverseGeoCodeOption);
		}
	}

	@Override
	public void onMapStatusChange(MapStatus status) {

	}

	@Override
	public void onMapStatusChangeFinish(MapStatus status) {
		if (status != null) {
			currentLatLng = status.target;
			reverseGeoCode(currentLatLng);
		}
	}

	@Override
	public void onMapStatusChangeStart(MapStatus status) {
		setLocationTitle(null, null);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	private void setLocationTitle(String title, String content) {
		if (TextUtils.isEmpty(title)) {
			locationTitleView.setVisibility(View.GONE);
		} else {
			locationTitleView.setVisibility(View.VISIBLE);
			locationTextView.setText(title);
			locationContentTextView.setText(content);
		}
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result != null) {
			if (result.error == SearchResult.ERRORNO.NO_ERROR) {
				mFMapView.setLocation(result.getLocation());
				setLocationTitle(result.getAddress(), "");
			}
		}
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result != null) {
			if (result.error == SearchResult.ERRORNO.NO_ERROR) {
				setLocationTitle(result.getAddress(), result.getAddressDetail().district);
			}
		}
	}

	@Override
	public void afterTextChanged(Editable editable) {
		if (TextUtils.isEmpty(editable.toString())) {
			mSearchListView.setVisibility(View.GONE);
		} else {
			mSearchListView.setVisibility(View.VISIBLE);
			mSearchAdapter.searchInCity(editable.toString(), cityName);
			startAnimation();
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		PoiInfo currentInfo = mSearchAdapter.getItem(position);
		currentLatLng = currentInfo.location;
		setLocationTitle(currentInfo.name, currentInfo.address);
		mFMapView.setLocation(currentLatLng);
		HideKeyboard();
	}

	/**
	 软键盘监听实现
	 * @param state
	 * @param keyboardHeight
	 * @param displayHeight
	 */
	@Override

	public void onKeyBoardStateChange(int state, int keyboardHeight, int displayHeight) {
		if (state == InputMethodLayout.KEYBOARD_STATE_SHOW) {
			mSearchListView.setVisibility(View.VISIBLE);
			// TODO: 2016/6/8 这个方法不懂 要这俩个高度啥用?
			ViewGroup.LayoutParams params = mSearchListView.getLayoutParams();
			int[] location = new int[2];
			mSearchListView.getLocationOnScreen(location);
			params.height = displayHeight - location[1];
			mSearchListView.setLayoutParams(params);
		} else if (state == InputMethodLayout.KEYBOARD_STATE_HIDE) {
			mSearchListView.setVisibility(View.GONE);
		}
	}

	/**
	 * 加载动画
	 */
	public void startAnimation() {
		if (searchLoadingView.getAnimation() != searchAnimation) {
			searchLoadingView.startAnimation(searchAnimation);
		}
	}

	/**
	 * 动画停止
	 */
	public void stopAnimation() {
		searchLoadingView.clearAnimation();
	}

	@Override
	public void onClick(View view) {
		if (view.equals(startScanView)) {
			if (!Util.isGPSClose(this) || !Util.isSimulationLocation(this)) {
				if (mGpsDialog == null) {
					mGpsDialog = new SimulationGPSDialog(this);
				}
				mGpsDialog.InitData();
				mGpsDialog.show();
				return;
			}
			if (currentLatLng != null) {
				mLocationManager.setLocation(currentLatLng);
				Toast.makeText(this, "模拟位置成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "请选择地址", Toast.LENGTH_SHORT).show();
			}
		} else if (view.equals(stopScanView)) {
			if (currentLatLng != null) {
				mLocationManager.StopLocation();
				Toast.makeText(this, "停止模拟位置成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "请选择地址", Toast.LENGTH_SHORT).show();
			}
		} else if (view.equals(selectCityTextView)) {
			Intent intent = new Intent(this, ContactsListViewActivity.class);
			startActivityForResult(intent, 1);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			if (data != null) {
				String cityString = data.getStringExtra("City");
				cityName = cityString;
				if (mGeocoder != null) {
					mGeocoder.geocode(new GeoCodeOption().city(cityName).address(cityName));
				}
				RefreshCity();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void RefreshCity() {
		if (!TextUtils.isEmpty(cityName)) {
			selectCityTextView.setText(cityName);
		}
	}
}
