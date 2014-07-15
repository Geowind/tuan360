package com.folyd.tuan.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.folyd.tuan.R;
import com.folyd.tuan.util.LocationUtil;

public class WelcomeActivity extends Activity {
	private Context mContext = WelcomeActivity.this;
	private ImageView mImageView;
	private String city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mImageView = (ImageView) findViewById(R.id.splash_logo);
		AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);
		anim.setDuration(3000);
		Log.i("Log", "Animation");
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				city = LocationUtil.getCity(mContext);
				Log.i("Log", "onAnimationStart(Animation animation)");
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(WelcomeActivity.this,
						MainActivity.class);
				intent.putExtra("city", city);
				startActivity(intent);
				finish();
			}
		});
		mImageView.startAnimation(anim);

	}
}
