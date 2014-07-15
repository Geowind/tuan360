package com.folyd.tuan.ui;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.folyd.tuan.R;
import com.folyd.tuan.app.App;
import com.folyd.tuan.bean.Goods;
import com.folyd.tuan.service.GoodsDetailService;
import com.folyd.tuan.util.DateFormatUtil;
import com.folyd.tuan.util.SimpleImageLoader;
import com.folyd.tuan.util.SpanUtil;

public class GoodsDetailActivity extends Activity {
	private App app;
	private Button back;
	private TextView share;
	private TextView look;

	private TextView detail;
	private ImageView image;
	private TextView remain_time;
	private TextView value;
	private TextView price;
	private TextView discount_percent;
	private TextView quantity_sold;
	private TextView merchant;
	private TextView merchant_tel;
	private TextView merchant_addr;

	private Goods mGoods;
	private GoodsDetailService mGoodsDetailService;
	private DetailListenner mListenner;

	private String goodsId;
	private boolean networkConnected = false;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_detail);
		app = (App) getApplication();
		networkConnected = app.isNetworkConnected();
		findViewByIds();
		setListenner();
		if (!networkConnected) {
			Toast.makeText(this, "网络连接异常，请检查网络连接...", Toast.LENGTH_LONG).show();
			return;
		}
		renderView();
	}

	private void renderView() {
		goodsId = getIntent().getStringExtra("goodsId");
		try {
			mGoodsDetailService = new GoodsDetailService();
			mGoods = mGoodsDetailService.getGoodsDetail(goodsId);

			long time = Long.parseLong(mGoods.remain_time);
			String date = DateFormatUtil.format(time);
			remain_time.setText("剩余时间：" + date);

			String sitename = "[" + mGoods.sitename + "]";
			String textValue = sitename + mGoods.partname;
			detail.setText(SpanUtil.highLight(textValue, "#DD0E0E", 0,
					sitename.length()));

			new Thread(new Runnable() {
				Drawable drawable;

				@Override
				public void run() {
					try {
						drawable = new BitmapDrawable(getResources(),
								SimpleImageLoader.getBitmap(mGoods.img));
					} catch (IOException e) {
						e.printStackTrace();
					}
					image.post(new Runnable() {

						@Override
						public void run() {
							// setImageDrawable(Drawalbe)
							// setBackground(Drawable) 两者的区别
							// image.setImageDrawable(drawable);
							image.setBackgroundDrawable(drawable);// Deprecated
																	// in API 16
							// image.setBackground(drawable); //Recommend ues
							// this method .since API 16
						}
					});

				}
			}).start();

			String deleteValue = "￥" + mGoods.value;
			value.setText(SpanUtil.deleteStyle(deleteValue, 0,
					deleteValue.length()));
			discount_percent.setText(mGoods.discount_percent);
			price.setText("￥" + mGoods.price);
			quantity_sold.setText("已售出：" + mGoods.quantity_sold);
			merchant.setText(mGoods.sitename);
			merchant_tel.setText(mGoods.merchant_tel);
			merchant_addr.setText(mGoods.merchant_addr);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void findViewByIds() {
		back = (Button) findViewById(R.id.back);
		share = (TextView) findViewById(R.id.share);
		look = (TextView) findViewById(R.id.detail_look);

		remain_time = (TextView) findViewById(R.id.detail_remain_time);
		detail = (TextView) findViewById(R.id.detail_detail);
		image = (ImageView) findViewById(R.id.detail_image);
		value = (TextView) findViewById(R.id.detail_value);
		discount_percent = (TextView) findViewById(R.id.detail_discount_percent);
		price = (TextView) findViewById(R.id.detail_price);
		quantity_sold = (TextView) findViewById(R.id.detail_quantity_sold);
		merchant = (TextView) findViewById(R.id.detail_merchant);
		merchant_tel = (TextView) findViewById(R.id.detail_merchant_tel);
		merchant_addr = (TextView) findViewById(R.id.detail_merchant_addr);
	}

	private void setListenner() {
		mListenner = new DetailListenner();

		back.setOnClickListener(mListenner);
		share.setOnClickListener(mListenner);
		look.setOnClickListener(mListenner);
		merchant_tel.setOnClickListener(mListenner);
	}

	class DetailListenner implements View.OnClickListener {
		private Uri uri;
		private Intent intent;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;
			case R.id.detail_merchant_tel:
				if (networkConnected) {
					uri = Uri.parse("tel:" + merchant_tel.getText().toString());
					intent = new Intent(Intent.ACTION_CALL, uri);
					startActivity(intent);
				}
				break;
			case R.id.detail_look:
				if (networkConnected) {
					intent = new Intent(GoodsDetailActivity.this,
							WebViewActivity.class);
					intent.putExtra("detail_url", mGoods.goods_url);
					startActivity(intent);
				}
				break;
			}
		}

	}
}
