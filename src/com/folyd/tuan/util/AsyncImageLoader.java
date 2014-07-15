package com.folyd.tuan.util;

import java.io.IOException;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.folyd.tuan.cache.ImageCache;

/**
 * 图片异步加载类，包括磁盘缓存和从网上获取数据
 * 
 * @author Folyd
 * 
 */
public class AsyncImageLoader extends AsyncTask<String, Void, Bitmap> {
	private ImageView image;
	private ImageCache imageCache;
	private String TAG = "AsyncImageLoader";

	public AsyncImageLoader(ImageView image, ImageCache imageCache) {
		super();
		this.image = image;
		this.imageCache = imageCache;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = imageCache.getBitmapFromDiskCache(params[0]);
		if (bitmap != null) {
			imageCache.addBitmapToMemoryCache(params[0], bitmap);
			return bitmap;
		}

		try {
			bitmap = SimpleImageLoader.getBitmap(params[0]);
			System.out.println("SimpleImageLoader  getBitmap----->");
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "doInBackground SimpleImageLoader.getBitmap异常");
		}
		imageCache.addBitmapToMemoryCache(params[0], bitmap);
		imageCache.addBitmapToDiskCache(params[0], bitmap);
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		image.setImageBitmap(bitmap);
	}

}
