package com.folyd.tuan.test;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;
import android.test.AndroidTestCase;

import com.folyd.tuan.bean.City;
import com.folyd.tuan.bean.Goods;
import com.folyd.tuan.service.CityService;
import com.folyd.tuan.service.GoodsDetailService;
import com.folyd.tuan.service.GoodsService;

public class TuanTest extends AndroidTestCase {
	private Context mContext;
	private CityService mCityService;
	private GoodsService mGoodsService;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mContext = getContext();

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testgetCity() {
		System.out.println("Test------------------->");
		mCityService = new CityService(mContext);
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			list = mCityService.getCity();
			for (Object obj : list) {
				if (obj instanceof String) {
					String str = (String) obj;
					System.out.println(str);
				} else if (obj instanceof City) {
					City city = (City) obj;
					System.out.println(city);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testgetGoodsList() {
		System.out.println("Test------------------->");
		System.out.println(mContext);
		mGoodsService = new GoodsService();
		ArrayList<Goods> list = new ArrayList<Goods>();
		try {
			list = mGoodsService.getGoodsList(1, "0", "guang_zhou");
			for (Goods obj : list) {
				System.out.println(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public void testgetGoodsListCache() {
		System.out.println("Test------------------->");
		mGoodsService = new GoodsService();
		ArrayList<Goods> list = new ArrayList<Goods>();
		/*try {
			list = mGoodsService.getGoodsListCache();
			for (Goods obj : list) {
				System.out.println(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	public void testGetGoodsDetail() {
		String goodsId = "13a05bf625b966b78fa9e7a6f41d1a77";
		GoodsDetailService mGoodsDetailService = new GoodsDetailService();
		Goods goods = new Goods();
		try {
			goods = mGoodsDetailService.getGoodsDetail(goodsId);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(goods);
	}
	
	private void testFastJson() {
		mGoodsService = new GoodsService();
		ArrayList<Goods> list = new ArrayList<Goods>();
		try {
			list = mGoodsService.getGoodsList(1, "0", "guang_zhou");
			for (Goods obj : list) {
				System.out.println(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
