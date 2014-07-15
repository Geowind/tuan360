package com.folyd.tuan.util;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 百度定位帮助类
 * @author Folyd
 *
 */
public class LocationUtil {
     private static LocationClient mLocationClient;
     private static LocationClientOption options;
     private static BDLocationListener mBdLocationListener;
     
     private static String city;
     /**
      * 初始化定位器
      */
     private static void  initLocationClient(Context context){
    	 mLocationClient = new LocationClient(context);
    	 options = new LocationClientOption();
    	 
    	 options.setOpenGps(true);
    	 options.setScanSpan(1000);
    	 options.setAddrType("all");
    	 options.setCoorType("bd09ll");
    	 mLocationClient.setLocOption(options);
    	 
    	 mBdLocationListener = new BDLocationListener() {
 			
 			@Override
 			public void onReceivePoi(BDLocation loc) {
 				
 			}
 			
 			@Override
 			public void onReceiveLocation(BDLocation loc) {
 				city = loc.getCity();
 				//System.out.println("void onReceivePoi(BDLocation loc) ");
 				stopLocationClient();
 			}
 		};
    	 mLocationClient.registerLocationListener(mBdLocationListener);
    	 mLocationClient.start();
    	 mLocationClient.requestLocation();
     }
     /**
      * 获取所定位的城市
      * @return
      */
     public static String getCity(Context context){
    	 initLocationClient(context);
    	 
    	 Log.i("Log", "String getCity(Context context)  city: "+ city);
		return city;
     }
     /**
      * 关闭定位器
      */
     public static void stopLocationClient(){
    	 mLocationClient.unRegisterLocationListener(mBdLocationListener);
    	 mLocationClient.stop();
     }
     
}
