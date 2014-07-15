package com.folyd.tuan.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.json.JSONException;

import com.folyd.tuan.bean.Goods;
import com.folyd.tuan.service.GoodsService;
import com.folyd.tuan.util.StreamUtil;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * json数据缓存类
 * 
 * @author Folyd 2013.08.28
 */
public class JsonCache {
	private String TAG = "JsonCache";
	private Context context;
	private final long SAVE_TIME = 5 * 60 * 1000; // 设置json数据默认持久时间
	private File file = null;

	public JsonCache(Context context) {
		super();
		this.context = context;
		file = new File(getJsonCachePath(), "goods.json");
	}

	/**
	 * 将json数据存储到缓存中
	 * 
	 * @param jsonString
	 */
	public void addJsonToCache(final String jsonString) {
		new Runnable() {

			@Override
			public void run() {
				try {
					if (!file.exists()) {
						file.getParentFile().mkdirs();
						file.createNewFile();
					}

					OutputStream os = new FileOutputStream(file);
					file.setLastModified(System.currentTimeMillis());
					os.write(jsonString.getBytes());
					os.flush();
					os.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Log.e(TAG, "addJsonToCache FileNotFoundException");
					file.delete();
				} catch (IOException e) {
					e.printStackTrace();
					Log.e(TAG, "addJsonToCache IOException ");
					file.delete();
				}
			}
		}.run();
	}

	/**
	 * 从缓存的json中获取数据
	 * 
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<Goods> getGoodsListFromJsonCache() {
		System.out.println("getGoodsListFromJsonCache()  ----------------->");
		InputStream is = null;
		String jsonString = "";
		if (!file.exists()) {
			return null;
		}
		try {
			is = new FileInputStream(file);
			jsonString = new String(StreamUtil.readInputStream(is));
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "getGoodsListFromJsonCache IOException ");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return new GoodsService().parseJson(jsonString);
	}

	private String getJsonCachePath() {
		final String cachePath;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			cachePath = context.getExternalFilesDir(null).getPath();
		} else {
			cachePath = context.getFilesDir().getParent();
		}
		return cachePath;
	}

	public boolean canSave() {
		// 如果小于json默认持久时间，则不保存
		if (!new File(getJsonCachePath(), "goods.json").exists()) {
			return true;
		}
		if (System.currentTimeMillis() - file.lastModified() > SAVE_TIME) {
			return true;
		}

		return false;
	}

}
