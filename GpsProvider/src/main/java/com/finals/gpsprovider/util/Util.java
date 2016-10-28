package com.finals.gpsprovider.util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;

import com.baidu.mapapi.model.LatLng;

public class Util {

	@TargetApi(19)
	public static boolean isGPSClose(Context context) {
		ContentResolver resolver = null;
		try {
			resolver = context.getContentResolver();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			int isGPSClose = Settings.Secure.LOCATION_MODE_OFF;
			try {
				isGPSClose = Settings.Secure.getInt(resolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (isGPSClose == Settings.Secure.LOCATION_MODE_OFF) {
				return true;
			}
		} else {
			try {
				String allProviderString = "";
				if (resolver != null) {
					allProviderString = Settings.Secure.getString(resolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
				}
				if (TextUtils.isEmpty(allProviderString)) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public static void OpenGps(Context context) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		try {
			context.startActivity(intent);
		} catch (Exception e) {

		}
	}

	public static void openSimulationSetting(Context context) {
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
		try {
			context.startActivity(intent);
		} catch (Exception e) {

		}
	}

	public static boolean isSimulationLocation(Context context) {
		int result = 0;
		ContentResolver resolver = null;
		try {
			resolver = context.getContentResolver();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (resolver != null) {
				result = Settings.Secure.getInt(resolver, Settings.Secure.ALLOW_MOCK_LOCATION, 0);
			}
			if (result != 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T getHolderView(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		View childView = null;
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		} else {
			childView = viewHolder.get(id);
			if (childView == null) {
				childView = view.findViewById(id);
				viewHolder.put(id, childView);
			}
		}
		return (T) childView;
	}

	static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

	public static LatLng bd_encrypt(double gg_lat, double gg_lon) {
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		LatLng result = new LatLng(bd_lat, bd_lon);
		return result;
	}

	public static LatLng bd_decrypt(double bd_lat, double bd_lon) {
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double gg_lon = z * Math.cos(theta);
		double gg_lat = z * Math.sin(theta);

		LatLng result = new LatLng(gg_lat, gg_lon);
		return result;
	}
}
