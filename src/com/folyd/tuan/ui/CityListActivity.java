package com.folyd.tuan.ui;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.folyd.tuan.R;
import com.folyd.tuan.bean.City;
import com.folyd.tuan.service.CityService;
import com.folyd.tuan.ui.adapter.CityAdapter;
import com.folyd.tuan.widget.SlideBar;
import com.folyd.tuan.widget.SlideBar.OnTouchLetterChangeListener;

public class CityListActivity extends ListActivity {
	private Context mContext = this;
	private Button back;
	private ListView mListView;
	private SlideBar mSlideBar;
	private TextView float_letter;

	private ArrayList<Object> array;
	private CityService mCityService;
	private CityAdapter mCityAdapter;

	private String city_name;
	private String city_code;

	public static final int RESULT_CODE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_citylist);
		back = (Button) findViewById(R.id.city_list_back);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mListView = getListView();
		mCityService = new CityService(mContext);
		array = new ArrayList<Object>();
		try {
			array = mCityService.getCity();
			mCityAdapter = new CityAdapter(mContext, array);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mListView.setAdapter(mCityAdapter);
		mSlideBar = (SlideBar) findViewById(R.id.slideBar);
		float_letter = (TextView) findViewById(R.id.float_letter);
		setListenner();

	}

	private void setListenner() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("onItemClick------------>");
				Object obj = parent.getItemAtPosition(position);
				if (obj instanceof String) {
					return;
				} else if (obj instanceof City) {
					City city = (City) obj;
					city_name = city.name;
					city_code = city.code;
					Intent intent = new Intent(mContext,
							TodayTuanActivity.class);
					intent.putExtra("city_name", city_name);
					intent.putExtra("city_code", city_code);
					setResult(RESULT_CODE, intent);
					finish();
				}
			}
		});
		mSlideBar
				.setOnTouchLetterChangeListener(new OnTouchLetterChangeListener() {

					@Override
					public void onTouchLetterChange(boolean isTouched, String s) {

						float_letter.setText(s);
						if (isTouched) {
							float_letter.setVisibility(View.VISIBLE);
						} else {
							float_letter.postDelayed(new Runnable() {

								@Override
								public void run() {
									float_letter.setVisibility(View.GONE);
								}
							}, 100);
						}
						int position = array.indexOf(s);
						mListView.setSelection(position);
					}
				});

	}
}
