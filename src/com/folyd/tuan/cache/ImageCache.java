package com.folyd.tuan.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * 图片缓存类，处理图片内存缓存和磁盘缓存
 * 
 * @author Folyd 2013.08.23
 * 
 */
public class ImageCache {
	public Context context;
	private LruCache<String, Bitmap> lruCache;
	private String TAG = "ImageCache";
	@SuppressWarnings("unused")
	private static final String CACEHDIR = "cache";
	private static int MB = 1024 * 1024;
	private static final int CACHE_SIZE = 10;
	@SuppressWarnings("unused")
	private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = CACHE_SIZE;

	public ImageCache(Context context, LruCache<String, Bitmap> lruCache) {
		super();
		this.context = context;
		this.lruCache = lruCache;
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		System.out.println("addBitmapToMemoryCache--->");
		if (getBitmapFromMemoryCache(key) == null) {
			lruCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemoryCache(String key) {
		return lruCache.get(key);
	}

	public void addBitmapToDiskCache(String key, Bitmap bitmap) {
		if (bitmap == null) {
			return;
		}

		String fileName = convertUrlToFileName(key);
		String dir = getDiskCachePath();
		System.out.println("addBitmapToDiskCache ----getDiskCachePath:" + dir);
		File file = new File(dir + "/" + fileName);
		OutputStream out = null;
		try {
			file.createNewFile();
			out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "addBitmapToDiskCache file.createNewFile()时发生异常");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Bitmap getBitmapFromDiskCache(final String key) {
		String path = getDiskCachePath() + "/" + convertUrlToFileName(key);
		System.out.println("getBitmapFromDiskCache : " + path);
		File file = new File(path);
		if (file.exists()) {
			try {
				InputStream is = new FileInputStream(file);
				// 查看源码得知decodeFile()方法里面也是调用decodeStream()方法，所以这里直接调用decodeStream()方法要高效一些
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				// Bitmap bitmap = BitmapFactory.decodeFile(path);
				if (bitmap == null) {
					file.delete();
				} else {
					file.setLastModified(System.currentTimeMillis());
					return bitmap;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Log.e(TAG, "getBitmapFromDiskCache FileNotFoundException");
			}
		}
		return null;

	}

	/**
	 * 计算SDCard剩余的空间
	 * 
	 * @return 返回多少MB
	 */
	@SuppressWarnings("unused")
	private int freeSpaceOnSdCard() {
		StatFs statFs = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		int freeSd = statFs.getAvailableBlocks() * statFs.getBlockSize() / MB;
		return freeSd;
	}

	/**
	 * 将URL转成文件名
	 * 
	 * @param url
	 * @return
	 */
	private String convertUrlToFileName(String url) {
		String strs[] = url.split("/");
		return strs[strs.length - 1];
	}

	private String getDiskCachePath() {
		final String cachePath;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return cachePath;
	}
}
