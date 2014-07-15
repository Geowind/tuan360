package com.folyd.tuan.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.folyd.tuan.bean.Goods;

public class DetailAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Goods> array;

	public DetailAdapter(Context mContext, ArrayList<Goods> array) {
		super();
		this.mContext = mContext;
		this.array = array;
	}

	@Override
	public int getCount() {
		return array.size();
	}

	@Override
	public Object getItem(int position) {
		return array.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

	class ViewHolder {
		TextView title;
		ImageView image;
		TextView sitename;
		TextView remain_time;
		TextView value;
		TextView price;
		TextView discount_percent;
		TextView quantity_sold;
		TextView merchant;
		TextView merchant_tel;
		TextView merchant_addr;
	}

}
