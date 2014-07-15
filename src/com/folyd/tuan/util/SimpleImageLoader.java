package com.folyd.tuan.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 简单的图片加载工具类，仅适用于单张图片加载。此类没有涉及到图片缓存。
 * @author Folyd
 *
 */
public class SimpleImageLoader {

	public static Bitmap getBitmap(String urlStr) throws IOException{
		Bitmap bitmap;
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setReadTimeout(5*1000);
		conn.setDoInput(true);
		conn.connect();
		InputStream is = conn.getInputStream();
		//byte[] image_byte = StreamUtil.readInputStream(is);
		bitmap = BitmapFactory.decodeStream(is);
		is.close();
		return bitmap;
	}
}
