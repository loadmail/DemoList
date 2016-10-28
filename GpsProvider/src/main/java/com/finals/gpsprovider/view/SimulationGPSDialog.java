package com.finals.gpsprovider.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.finals.gpsprovider.R;
import com.finals.gpsprovider.base.BaseApplication;
import com.finals.gpsprovider.util.Util;

/**
 * 没有开启GPS
 * 
 * @author Administrator
 * 
 */
public class SimulationGPSDialog extends Dialog implements android.view.View.OnClickListener {

	Activity context;

	View closeGPS;
	View allowSumilation;

	View cancel;

	public SimulationGPSDialog(Activity context) {
		super(context, R.style.FDialog);
		this.context = context;
		setContentView(R.layout.dialog_gps);
		InitWindow();
		InitView();
	}

	private void InitView() {
		closeGPS = findViewById(R.id.close_gps);
		closeGPS.setOnClickListener(this);

		allowSumilation = findViewById(R.id.allow_simulation);
		allowSumilation.setOnClickListener(this);

		cancel = findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
	}

	public void InitData() {
		if (Util.isSimulationLocation(context)) {
			allowSumilation.setSelected(true);
		}
		if (Util.isGPSClose(context)) {
			closeGPS.setSelected(true);
		}
	}

	private void InitWindow() {
		android.view.WindowManager.LayoutParams params = getWindow().getAttributes();
		params.width = LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(params);
	}

	@Override
	public void onClick(View view) {
		if (view.equals(allowSumilation)) {
			if (!allowSumilation.isSelected()) {
				Util.openSimulationSetting(context);
			}
		} else if (view.equals(closeGPS)) {
			if (!closeGPS.isSelected()) {
				Util.OpenGps(context);
			}
		} else {
			this.dismiss();
		}
	}

}
