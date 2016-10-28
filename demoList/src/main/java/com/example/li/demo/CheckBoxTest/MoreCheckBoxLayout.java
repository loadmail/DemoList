package com.example.li.demo.CheckBoxTest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.li.demo.R;


public class MoreCheckBoxLayout extends LinearLayout implements View.OnClickListener {

	String[] reasons;
	int Len = 2;
	Context context;
	CheckBox[] allCheckbox;
	EditText feedbackEditText;

	public MoreCheckBoxLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void setReason(String[] reasons, EditText feedbackEditText) {
		this.reasons = reasons;
		this.feedbackEditText = feedbackEditText;

		allCheckbox = new CheckBox[reasons.length];


		int count = reasons.length / 2 + (reasons.length % 2 > 0 ? 1 : 0);
		for (int i = 0; i < count; i++) {
			ViewGroup item = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.reason_item, null);
			for (int j = 0; j < Len; j++) {
				int current = i * Len + j;
				//rb_line1  "rb_line" + (j + 1)
				// TODO: 2016/6/13  	获取资源id 这里
				// int drawableId = mContext.getResources().getIdentifier("ic_launcher","drawable", mContext.getPackageName());
				CheckBox itemCheckBox = (CheckBox) item.findViewById(getResources().getIdentifier("rb_line" + (j + 1), "id", context.getPackageName()));
				if (current < reasons.length) {
					allCheckbox[current] = itemCheckBox;
					allCheckbox[current].setText(reasons[current]);
					allCheckbox[current].setOnClickListener(this);
				} else {
					itemCheckBox.setVisibility(View.INVISIBLE);
				}
			}
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.recharge_item_height));
			addView(item, params);
		}
	}

	@Override
	public void onClick(View view) {
		selectCurrent(view);
	}

	private void selectCurrent(View view) {
		if (allCheckbox != null) {
			for (int i = 0; i < allCheckbox.length; i++) {
				if (view == allCheckbox[i]) {
					allCheckbox[i].setChecked(true);
					feedbackEditText.setText("#" + reasons[i] + "#");
				} else {
					allCheckbox[i].setChecked(false);
				}
			}
		}
	}


	/**
	 * 得到选中的，没有选中返回-1
	 * 
	 * @return
	 */
	public int getCurrent() {
		int index = -1;
		if (allCheckbox != null) {
			for (int i = 0; i < allCheckbox.length; i++) {
				if (allCheckbox[i].isChecked()) {
					index = i;
					break;
				}
			}
		}
		return index;
	}
}
