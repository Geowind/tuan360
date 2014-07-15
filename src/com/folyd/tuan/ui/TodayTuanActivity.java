package com.folyd.tuan.ui;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.folyd.tuan.R;
import com.folyd.tuan.app.App;
import com.folyd.tuan.bean.Goods;
import com.folyd.tuan.cache.JsonCache;
import com.folyd.tuan.service.GoodsService;
import com.folyd.tuan.ui.adapter.GoodsAdapter;
import com.folyd.tuan.util.DateFormatUtil;
import com.folyd.tuan.widget.PullToRefreshListView;
import com.folyd.tuan.widget.PullToRefreshListView.OnRefreshListener;

public class TodayTuanActivity extends ListActivity {
	private Context mContext = this;
	private App app;
	// private ListView mListView;
	private PullToRefreshListView mPullToRefreshListView;
	private Button cityList;
	private TextView spinner;
	private TextView more_tv;
	private ProgressBar progressbar;
	private View footer;

	private GoodsAdapter mGoodsAdapter;
	private GoodsService mGoodsService;
	private JsonCache mJsonCache;

	private ArrayList<Goods> array;
	private String goodsType = "0";
	private int currPage = 1;

	private String city = "guang_zhou";
	private String TAG = "TodayTuanActivity";
	public final int REQUEST_CODE = 1;
	private String[] catagorys = { "今日团购", "本地美食", "休闲娱乐", "旅游酒店", "网上购物",
			"美容保健", "其他" };
	private String[] mItemIds = { "0", "1", "2", "3", "4", "5", "6" };

	private Handler mHandler = new Handler() {  
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				mGoodsAdapter = new GoodsAdapter(array, mContext);
				mPullToRefreshListView.setAdapter(mGoodsAdapter);
				break;
			case 2:
				Toast.makeText(app, "网络连接异常，请检查网络连接...", Toast.LENGTH_LONG)
						.show();
				break;
			case 3:
				ArrayList<Goods> list = null;
				if (app.isNetworkConnected()) {
					try {
						mGoodsService = new GoodsService();
						list = mGoodsService.getGoodsList(currPage, goodsType,
								city);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					mHandler.sendEmptyMessage(2);
				}
				if (list != null) {
					array.addAll(list);
					mGoodsAdapter = new GoodsAdapter(array, mContext);
					mPullToRefreshListView.setAdapter(mGoodsAdapter);
					long lastUpdateTime = System.currentTimeMillis();

					String lastUpdatedTime = "最后更新于："
							+ DateFormatUtil.getDateTime(lastUpdateTime);
					mPullToRefreshListView.setLastUpdated(lastUpdatedTime);
					App.PULL_TO_REFRESH_LISTVIEW_LASTUPDATE_TIME = lastUpdatedTime;
				}
				mPullToRefreshListView.onRefreshComplete();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_today_tuan);
		app = (App) getApplication();
		// mListView = getListView();
		mPullToRefreshListView = (PullToRefreshListView) getListView();
		mGoodsService = new GoodsService();
		mJsonCache = new JsonCache(mContext);
		findViewByIds();
		mPullToRefreshListView.addFooterView(footer);
		array = new ArrayList<Goods>();
		new Thread(new Runnable() {

			@Override
			public void run() {
				mGoodsService = new GoodsService();
				ArrayList<Goods> list = null;
				try {
					list = mJsonCache.getGoodsListFromJsonCache();
					if (list == null) {
						if (app.isNetworkConnected()) {
							list = mGoodsService.getGoodsList(currPage,
									goodsType, city);
						} else {
							mHandler.sendEmptyMessage(2);
						}
					}
					if (list != null) {
						array.addAll(list);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.e(TAG, "加载ListView数据时放生了异常...");
				}
				mHandler.sendEmptyMessage(1);
			}
		}).start();
		setListener();
	}

	private void setListener() {
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				mHandler.sendEmptyMessage(3);
			}
		});
		mPullToRefreshListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Goods goods = (Goods) parent
								.getItemAtPosition(position);
						Intent intent = new Intent(mContext,
								GoodsDetailActivity.class);
						intent.putExtra("goodsId", goods.id);
						startActivity(intent);
					}
				});
		mPullToRefreshListView.setOnScrollListener(new OnScrollListener() {
			private int lastItemIndex;// 当前ListView中最后一个Item的索引

			// 当ListView不在滚动，并且ListView的最后一项的索引等于adapter的项数减一时则自动加载（因为索引是从0开始的）
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 因为PullToRefreshListView中加了一个HeaderView
				// ，所以这里mGoodsAdapter.getCount()后不需要减一
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& lastItemIndex == mGoodsAdapter.getCount()) {
					Log.i(TAG, "onScrollStateChanged");
					currPage++;
					goodsType = currPage + "";
					progressbar.setVisibility(View.VISIBLE);
					more_tv.setText(R.string.more_tv);
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							if (app.isNetworkConnected()) {
								mGoodsService = new GoodsService();
								try {
									ArrayList<Goods> list = mGoodsService
											.getGoodsList(currPage, goodsType,
													city);
									array.addAll(list);
									mGoodsAdapter.notifyDataSetChanged();
								} catch (Exception e) {
									e.printStackTrace();
									Log.e(TAG, "加载另一页ListView数据时发生了异常...");
								}
							} else {
								mHandler.sendEmptyMessage(2);
							}
						}
					}, 1000);
				} else {
					progressbar.setVisibility(View.GONE);
					more_tv.setText("更多");
				}
			}

			// 这三个int类型的参数可以自行Log打印一下就知道是什么意思了
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// ListView 的FooterView也会算到visibleItemCount中去，所以要再减去一
				lastItemIndex = firstVisibleItem + visibleItemCount - 1 - 1;
			}
		});
		/*
		 * more.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { currPage++; goodsType =
		 * currPage + ""; more.setVisibility(View.GONE);
		 * progressbar.setVisibility(View.VISIBLE); mHandler.postDelayed(new
		 * Runnable() {
		 * 
		 * @Override public void run() { if (app.isNetworkConnected()) {
		 * mGoodsService = new GoodsService(); try { ArrayList<Goods> list =
		 * mGoodsService .getGoodsList(currPage, goodsType, city);
		 * array.addAll(list); mGoodsAdapter.notifyDataSetChanged(); } catch
		 * (Exception e) { e.printStackTrace(); Log.e(TAG,
		 * "加载另一页ListView数据时发生了异常..."); } } else { mHandler.sendEmptyMessage(2);
		 * } // Message msg = mHandler.obtainMessage(0); //
		 * mHandler.sendMessage(msg); more.setVisibility(View.VISIBLE);
		 * progressbar.setVisibility(View.GONE); } }, 1000);
		 * 
		 * } });
		 */
		spinner.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new Builder(mContext);
				builder.setSingleChoiceItems(catagorys, 0,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								spinner.setText(catagorys[which]);
								goodsType = mItemIds[which];
							}
						}).setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		cityList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, CityListActivity.class);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
	}

	private void findViewByIds() {
		cityList = (Button) findViewById(R.id.city);
		spinner = (TextView) findViewById(R.id.spinner);
		footer = getLayoutInflater().inflate(R.layout.today_tuan_list_footer,
				null);
		progressbar = (ProgressBar) footer
				.findViewById(R.id.today_tuan_progressbar);
		more_tv = (TextView) footer.findViewById(R.id.today_tuan_more_tv);
		// more = (Button) footer.findViewById(R.id.today_tuan_more);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i(TAG, "onActivityResult");
		if (requestCode == REQUEST_CODE
				&& resultCode == CityListActivity.RESULT_CODE) {

			cityList.setText(data.getStringExtra("city_name"));
			city = data.getStringExtra("city_code");
			if (app.isNetworkConnected()) {
				try {
					ArrayList<Goods> list = mGoodsService.getGoodsList(
							currPage, goodsType, city);
					array.clear();
					array.addAll(list);

				} catch (Exception e) {
					e.printStackTrace();
					Log.e(TAG, "onActivityResult 中加载数据时异常...");

				}
				mHandler.sendEmptyMessage(1);
			} else {
				array.clear();
				mHandler.sendEmptyMessage(1);
				mHandler.sendEmptyMessageDelayed(2, 100);
			}
		}
	}

	/*
	 * ArrayList<Goods> loadGoodsListFromNet() { final ArrayList<Goods> list;
	 * new Thread(new Runnable() {
	 * 
	 * @Override public void run() { try { list =
	 * mGoodsService.getGoodsList(currPage, goodsType, city); } catch
	 * (IOException e) { e.printStackTrace(); } catch (JSONException e) {
	 * e.printStackTrace(); } } }).start(); return list; }
	 */

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (array != null && mJsonCache.canSave()) {
			Log.i(TAG, "canSave");
			String jsonString = mGoodsService
					.toJsonString(array.subList(0, 10));
			if (jsonString != null) {
				mJsonCache.addJsonToCache(jsonString);
				Log.i(TAG, "Saved------------->");
			}
		}
	}

}
