package com.finals.gpsprovider;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.finals.gpsprovider.bean.CityItem;
import com.finals.gpsprovider.util.CityThread;
import com.finals.gpsprovider.util.Util;
import com.solo.widget.contactslistview.ContactsListAdapter;
import com.solo.widget.contactslistview.ContactsListView;

public class ContactsListViewActivity extends Activity implements OnItemClickListener,OnClickListener {

	private ArrayList<CityItem> COUNTRIES;

	EditText mSearchContext;

	ContactsListView mListView;

	StandardArrayAdapter arrayAdapter;

	ContactsListAdapter sectionAdapter;
	View back;

	CityThread mCityThread;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);

		mSearchContext = (EditText) findViewById(R.id.search_edit);
		mSearchContext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable sequence) {
				new StandardArrayAdapter(COUNTRIES).getFilter().filter(sequence.toString());
			}
		});
		mListView = (ContactsListView) findViewById(R.id.section_list_view);
		mListView.setOnItemClickListener(this);
		mListView.setFastScrollEnabled(true);
		mListView.setSpinnedShadowEnabled(true);

		COUNTRIES = new ArrayList<CityItem>();

		arrayAdapter = new StandardArrayAdapter(COUNTRIES);

		sectionAdapter = new ContactsListAdapter(getLayoutInflater(), arrayAdapter);
		mListView.setAdapter(sectionAdapter);
		mCityThread = new CityThread(arrayAdapter, COUNTRIES);
		mCityThread.start();
		back = findViewById(R.id.goback);
		back.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		mCityThread.StopThread();
		super.onDestroy();
	}

	private class StandardArrayAdapter extends BaseAdapter implements Filterable {

		private final ArrayList<CityItem> items;

		public StandardArrayAdapter(ArrayList<CityItem> args) {
			this.items = args;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null || convertView.getTag() == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.list_item_search, null);
			}
			CityItem item = items.get(position);
			TextView textId = (TextView) Util.getHolderView(convertView, R.id.content);
			if (textId != null) {
				textId.setText(String.valueOf(item.id));
			}
			TextView textTitle = (TextView) Util.getHolderView(convertView, R.id.title);
			if (textTitle != null) {
				textTitle.setText(item.name);
			}
			return convertView;
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Filter getFilter() {
			Filter listfilter = new MyFilter();
			return listfilter;
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}

	public class MyFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			constraint = mSearchContext.getText().toString();
			FilterResults result = new FilterResults();
			if (constraint != null && constraint.toString().length() > 0) {

				ArrayList<CityItem> filt = new ArrayList<CityItem>();
				ArrayList<CityItem> Items = new ArrayList<CityItem>();
				synchronized (this) {
					Items = COUNTRIES;
				}
				for (int i = 0; i < Items.size(); i++) {
					CityItem item = Items.get(i);
					if (item.getName().contains(constraint.toString())) {
						filt.add(item);
					}
				}

				result.count = filt.size();
				result.values = filt;
			} else {

				synchronized (this) {
					result.count = COUNTRIES.size();
					result.values = COUNTRIES;
				}
			}
			return result;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			@SuppressWarnings("unchecked")
			ArrayList<CityItem> filtered = (ArrayList<CityItem>) results.values;
			arrayAdapter = new StandardArrayAdapter(filtered);
			sectionAdapter = new ContactsListAdapter(getLayoutInflater(), arrayAdapter);
			mListView.setAdapter(sectionAdapter);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Object currentObject = mListView.getAdapter().getItem(position);
		if (currentObject instanceof CityItem) {
			String nameString = ((CityItem) currentObject).getName();
			Intent intent = new Intent();
			intent.putExtra("City", nameString);
			setResult(RESULT_OK, intent);
			this.finish();
		}
	}

	@Override
	public void onClick(View view) {
		if (view.equals(back)) {
			this.finish();
		}
	}
}