package com.folyd.tuan.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.folyd.tuan.R;
import com.folyd.tuan.bean.Goods;
import com.folyd.tuan.cache.ImageCache;
import com.folyd.tuan.util.AsyncImageLoader;
import com.folyd.tuan.util.SpanUtil;

public class GoodsAdapter extends BaseAdapter implements OnScrollListener {
	private ArrayList<Goods> list;
	private Context mContext;
	private final int maxMemory = (int) Runtime.getRuntime().maxMemory();
	private final int cacheSize = maxMemory / 5;

	private ImageCache mImageCache;
	private final String TAG = "GoodsAdapter";

	private LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(
			cacheSize) {
		@Override
		protected int sizeOf(String key, Bitmap bitmap) {
			// replaced by getByteCount() in API 12
			return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
		}

		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			System.out.println("LruCache entryRemoved----------->");
			if (evicted && oldValue != null && !oldValue.isRecycled()) {
				oldValue.recycle();
				oldValue = null;
			}
		}

	};

	public GoodsAdapter(ArrayList<Goods> list, Context mContext) {
		super();
		this.list = list;
		this.mContext = mContext;
		mImageCache = new ImageCache(mContext, mLruCache);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			Log.i(TAG, "convertView == null");
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.today_tuan_list_item,
					null);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.old_price = (TextView) convertView
					.findViewById(R.id.old_price);
			holder.new_price = (TextView) convertView
					.findViewById(R.id.new_price);
			holder.sale_out = (TextView) convertView
					.findViewById(R.id.sale_out);
			holder.last_time = (TextView) convertView
					.findViewById(R.id.last_time);
			convertView.setTag(holder);
		} else {
			Log.i(TAG, "convertView != null");
			holder = (ViewHolder) convertView.getTag();
		}
		final Goods goodsItem = list.get(position);

		// holder.id = goodsItem.id;
		String highLightValue = "[" + goodsItem.sitename + "]";
		String textValue = highLightValue + goodsItem.partname;
		int end = highLightValue.length();
		SpannableString span = SpanUtil.highLight(textValue, "#DD0E0E", 0, end);
		holder.title.setText(span);

		loadBitmap(goodsItem.img, holder.image);

		String deleteValue = "￥" + goodsItem.value;
		SpannableString span2 = SpanUtil.deleteStyle(deleteValue, 0,
				deleteValue.length());
		holder.old_price.setText(span2);

		holder.new_price.setText("￥" + goodsItem.price);
		holder.sale_out.setText("已售出" + goodsItem.quantity_sold);
		String date = goodsItem.end_date.substring(5);
		holder.last_time.setText("截止日期：" + date);

		return convertView;
	}

	/**
	 * 
	 * @param urlStr
	 *            所需要加载的图片的url，以String形式传进来
	 * @param image
	 *            ImageView 控件
	 */
	private void loadBitmap(String urlStr, ImageView image) {
		Bitmap bitmap = mImageCache.getBitmapFromMemoryCache(urlStr);
		if (bitmap != null) {
			image.setImageBitmap(bitmap);
		} else {
			image.setImageResource(R.drawable.thum);
			new AsyncImageLoader(image, mImageCache).execute(urlStr);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}

	/**
	 * 这里设置ViewHolder 为static，也就是静态的，静态类只会在第一次加载时 会耗费比较长时间，但是后面就可以很好帮助加载，
	 * 同时保证了内存中只有一个ViewHolder，节省了内存的开销。 详情见：
	 * 
	 * @author Folyd
	 * 
	 */
	static class ViewHolder {
		// public String id;
		ImageView image;
		TextView title;
		TextView old_price;
		TextView new_price;
		TextView sale_out;
		TextView last_time;
	}
}
