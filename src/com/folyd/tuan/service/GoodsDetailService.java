package com.folyd.tuan.service;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.folyd.tuan.bean.Goods;
import com.folyd.tuan.util.ApiUtil;
import com.folyd.tuan.util.HttpUtil;

public class GoodsDetailService {

	public Goods getGoodsDetail(String goodsId) throws IOException,
			JSONException {
		Goods goods = new Goods();

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", goodsId);

		String json = HttpUtil.post(ApiUtil.DETAILINFO_API, params, "UTF-8");
		System.out.println(json);
		JSONObject obj = new JSONObject(json).getJSONObject("result");
		goods.id = obj.getString("id");
		goods.cid = obj.getString("cid");
		goods.title = obj.getString("title");
		goods.partname = obj.getString("partname");
		goods.img = obj.getString("img");
		goods.start_date = obj.getString("start_date");
		goods.end_date = obj.getString("end_date");
		goods.price = obj.getString("price");
		goods.value = obj.getString("value");
		goods.discount_amount = obj.getString("discount_amount");
		goods.discount_percent = obj.getString("discount_percent");
		goods.quantity_sold = obj.getString("quantity_sold");
		goods.keywords = obj.getString("keywords");
		goods.remain_time = obj.getString("remain_time");
		goods.sitename = obj.getString("sitename");
		goods.sitekey = obj.getString("sitekey");
		goods.oauth = obj.getString("oauth");
		goods.siteurl = obj.getString("siteurl");
		goods.goods_url = obj.getString("goods_url");
		goods.detail_url = obj.getString("detail_url");
		goods.imgarray = obj.getString("imgarray");
		goods.comment_num = obj.getString("comment_num");
		goods.comment_url = obj.getString("comment_url");
		goods.special_tip = obj.getString("special_tip");
		goods.merchant = obj.getString("merchant");
		goods.merchant_tel = obj.getString("merchant_tel");
		goods.merchant_addr = obj.getString("merchant_addr");

		return goods;
	}

}
