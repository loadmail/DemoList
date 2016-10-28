package com.finals.gpsprovider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.finals.gpsprovider.base.BaseActivity;

/**BaseActivity implements Runnable
 * handler.postDelayed(Runnable ,time);
 */
public class MainActivity extends BaseActivity implements Runnable {

	Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mHandler = new Handler(Looper.getMainLooper());
		mHandler.postDelayed(this, 2 * 1000);
	}

	@Override
	public void run() {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	protected void onDestroy() {
		mHandler.removeCallbacks(this);
		super.onDestroy();
	}

}
