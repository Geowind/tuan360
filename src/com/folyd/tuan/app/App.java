package com.folyd.tuan.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @author Administrator
 * 
 */
public class App extends Application {
   public static String PULL_TO_REFRESH_LISTVIEW_LASTUPDATE_TIME;
	//网络状态
	private static final int NETWORK_CMWAP = 1;
	private static final int NETWORK_CMNET = 2;
	private static final int NETWORK_WWIFI = 3;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/**
	 * 检测是否已连接网络
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null && info.isConnectedOrConnecting()) {
			return true;
		} else {
			
			Log.e("App", "网络连接异常...");
			return false;
		}
	}

	/**
	 * 获取当前网络类型 0：没网络 1：CMWAP 2：CMNET 3：WIFI
	 * 
	 * @return
	 */
	public int getNetworkType() {
		int type = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info == null) {
			return type;
		}
		int nType = info.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = info.getExtraInfo();
			if (extraInfo.toLowerCase().equals("cmnet")) {
				type = NETWORK_CMNET;
			} else {
				type = NETWORK_CMWAP;
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			type = NETWORK_WWIFI;
		}
		return type;
	}
}
