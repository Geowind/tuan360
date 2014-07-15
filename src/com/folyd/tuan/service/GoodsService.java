package com.folyd.tuan.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.folyd.tuan.bean.Goods;
import com.folyd.tuan.util.ApiUtil;
import com.folyd.tuan.util.HttpUtil;

/**
 * 商品业务类
 * 
 * @author Folyd
 * 
 */
public class GoodsService {

	public ArrayList<Goods> getGoodsList(int currPage, String goodsType,
			String city) throws IOException, JSONException {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("currPage", currPage + "");
		params.put("start", currPage * 10 + "");
		params.put("goodstype", goodsType);
		params.put("city", city);
		params.put("num", 10 + "");

		String json = HttpUtil.post(ApiUtil.RECOMMENDLIST_API, params, "UTF-8");

		return parseJson(json);

	}

	public ArrayList<Goods> parseJson(String json) {
		ArrayList<Goods> list = new ArrayList<Goods>();
		Log.i("GoodsService", "parseJson");
		try {
			JSONArray array = new JSONObject(json).getJSONArray("result");
			JSONObject jsonItem;
			Goods goods;
			int len = array.length();

			for (int i = 0; i < len; i++) {
				jsonItem = array.getJSONObject(i);
				goods = new Goods();
				goods.id = jsonItem.getString("id");
				goods.cid = jsonItem.getString("cid");
				goods.title = jsonItem.getString("title");
				goods.partname = jsonItem.getString("partname");
				goods.img = jsonItem.getString("img");
				goods.start_date = jsonItem.getString("start_date");
				goods.end_date = jsonItem.getString("end_date");
				goods.price = jsonItem.getString("price");
				goods.value = jsonItem.getString("value");
				goods.discount_amount = jsonItem.getString("discount_amount");
				goods.discount_percent = jsonItem.getString("discount_percent");
				goods.quantity_sold = jsonItem.getString("quantity_sold");
				goods.keywords = jsonItem.getString("keywords");
				goods.remain_time = jsonItem.getString("remain_time");
				goods.sitename = jsonItem.getString("sitename");
				goods.sitekey = jsonItem.getString("sitekey");
				goods.goods_url = jsonItem.getString("goods_url");
				goods.siteurl = jsonItem.getString("siteurl");
				goods.oauth = jsonItem.getString("oauth");

				list.add(goods);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public String toJsonString(List<Goods> list) {
		if (list == null) {
			return null;
		}
		JSONObject json = new JSONObject();
		JSONArray result = new JSONArray();
		String jsonString = null;
		int len = list.size();
		for (int i = 0; i < len; i++) {
			Goods goods = list.get(i);
			JSONObject jsonItem = new JSONObject();

			try {
				jsonItem.put("id", goods.id);
				jsonItem.put("cid", goods.cid);
				jsonItem.put("title", goods.title);
				jsonItem.put("partname", goods.partname);
				jsonItem.put("img", goods.img);
				jsonItem.put("start_date", goods.start_date);
				jsonItem.put("end_date", goods.end_date);
				jsonItem.put("price", goods.price);
				jsonItem.put("value", goods.value);
				jsonItem.put("discount_amount", goods.discount_amount);
				jsonItem.put("discount_percent", goods.discount_percent);
				jsonItem.put("quantity_sold", goods.quantity_sold);
				jsonItem.put("keywords", goods.keywords);
				jsonItem.put("remain_time", goods.remain_time);
				jsonItem.put("sitename", goods.sitename);
				jsonItem.put("sitekey", goods.sitekey);
				jsonItem.put("goods_url", goods.goods_url);
				jsonItem.put("siteurl", goods.siteurl);
				jsonItem.put("oauth", goods.oauth);

				result.put(i, jsonItem);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		try {
			json.put("result", result);
			jsonString = json.toString(4);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}
