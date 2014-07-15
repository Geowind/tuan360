package com.folyd.tuan.ui;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.folyd.tuan.R;

public class MainActivity extends ActivityGroup {
	private Context mContext = this;
	private LinearLayout mLinearLayout;
	private ImageView today_flag, around_flag, my_flag, more_flag;
	private TextView today, around, my, more;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewByIds();
		setListenner();
		showContent(TodayTuanActivity.class, "todaytuan");
		today_flag.setVisibility(View.VISIBLE);
		today.setCompoundDrawablesWithIntrinsicBounds(0,
				R.drawable.today_press, 0, 0);
	}

	private void setListenner() {
		today.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Class<?> activity = TodayTuanActivity.class;
				String tag = "todaytuan";
				showContent(activity, tag);
				invisibleView();
				today.setCompoundDrawablesWithIntrinsicBounds(0,
						R.drawable.today_press, 0, 0);
				today_flag.setVisibility(View.VISIBLE);
			}

		});
		around.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Class<?> activity = TodayTuanActivity.class;
				String tag = "aroundtuan";
				showContent(activity, tag);
				invisibleView();
				around.setCompoundDrawablesWithIntrinsicBounds(0,
						R.drawable.around_press, 0, 0);
				around_flag.setVisibility(View.VISIBLE);

			}
		});
		my.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Class<?> activity = TodayTuanActivity.class;
				String tag = "mytuan";
				showContent(activity, tag);
				invisibleView();
				my.setCompoundDrawablesWithIntrinsicBounds(0,
						R.drawable.my_press, 0, 0);
				my_flag.setVisibility(View.VISIBLE);
			}
		});
		more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Class<?> activity = TodayTuanActivity.class;
				String tag = "moretuan";
				showContent(activity, tag);
				invisibleView();
				more.setCompoundDrawablesWithIntrinsicBounds(0,
						R.drawable.more_press, 0, 0);
				more_flag.setVisibility(View.VISIBLE);
			}
		});
	}

	protected void showContent(Class<?> activity, String tag) {
		mLinearLayout.removeAllViews();

		Intent intent = new Intent(mContext, activity);
		Window window = getLocalActivityManager().startActivity(tag, intent);
		View contentView = window.getDecorView();
		mLinearLayout.addView(contentView,
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
	}

	private void findViewByIds() {
		mLinearLayout = (LinearLayout) findViewById(R.id.main_content);

		today = (TextView) findViewById(R.id.today_tuan);
		around = (TextView) findViewById(R.id.around_tuan);
		my = (TextView) findViewById(R.id.my_tuan);
		more = (TextView) findViewById(R.id.more_tuan);

		today_flag = (ImageView) findViewById(R.id.today_curr_flag);
		around_flag = (ImageView) findViewById(R.id.arround_curr_flag);
		my_flag = (ImageView) findViewById(R.id.my_curr_flag);
		more_flag = (ImageView) findViewById(R.id.more_curr_flag);

	}

	private void invisibleView() {
		today_flag.setVisibility(View.INVISIBLE);
		around_flag.setVisibility(View.INVISIBLE);
		my_flag.setVisibility(View.INVISIBLE);
		more_flag.setVisibility(View.INVISIBLE);

		today.setCompoundDrawablesWithIntrinsicBounds(0,
				R.drawable.today_selector, 0, 0);
		around.setCompoundDrawablesWithIntrinsicBounds(0,
				R.drawable.around_selector, 0, 0);
		my.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.my_selector,
				0, 0);
		more.setCompoundDrawablesWithIntrinsicBounds(0,
				R.drawable.more_selector, 0, 0);
	}

}
