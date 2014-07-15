package com.folyd.tuan.service;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.folyd.tuan.bean.City;
import com.folyd.tuan.util.StreamUtil;

public class CityService {
	private Context context;

	public CityService(Context context) {
		super();
		this.context = context;
	}

	public ArrayList<Object> getCity() throws JSONException, Exception {
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		ArrayList<Object> list = new ArrayList<Object>();

		// String json = HttpUtil.get(ApiUtil.CITYLIST_API, "UTF-8");
		InputStream is = context.getAssets().open("city.txt");
		String json = new String(StreamUtil.readInputStream(is));
		JSONObject obj = new JSONObject(json).getJSONObject("result");
		JSONArray array;
		JSONObject jsonItem;
		City mCity;
		for (String item : letters) {
			if (obj.has(item)) {
				if (!list.contains(item)) {
					list.add(item);
				}
				array = obj.getJSONArray(item);
				for (int i = 0; i < array.length(); i++) {
					try {
						jsonItem = array.getJSONObject(i);
						mCity = new City();
						mCity.name = jsonItem.getString("name");
						mCity.code = jsonItem.getString("code");
						mCity.hotrank = jsonItem.getString("hotrank");

						list.add(mCity);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return list;
	}
}
