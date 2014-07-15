package com.folyd.tuan.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.folyd.tuan.R;
import com.folyd.tuan.bean.City;

public class CityAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Object> array;

	private String city_name;
	private String city_code;

	private static final String TAG = "CityAdapter";

	public CityAdapter(Context mContext, ArrayList<Object> array) {
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
		ViewHolder holder;
		City city;

		if (convertView == null) {
			Log.i(TAG, "convertView == null");
			convertView = View.inflate(mContext, R.layout.city_list_item, null);
			holder = new ViewHolder();
			holder.item_letter = (TextView) convertView
					.findViewById(R.id.city_list_item_letter);
			holder.item_city = (TextView) convertView
					.findViewById(R.id.city_list_item_city);
			convertView.setTag(holder);
		} else {
			Log.i(TAG, "convertView != null");
			holder = (ViewHolder) convertView.getTag();
		}

		Object obj = array.get(position);
		if (obj instanceof String) {
			holder.item_letter.setText(obj.toString());
			holder.item_letter.setVisibility(View.VISIBLE);
			holder.item_city.setVisibility(View.GONE);
		} else if (obj instanceof City) {
			city = (City) obj;
			holder.item_city.setText(city.name);
			holder.item_city.setVisibility(View.VISIBLE);
			holder.item_letter.setVisibility(View.GONE);
		}
		return convertView;
	}
    /**设置成静态类，优化ListView
     * @see GoodsAdapter.ViewHolder
     * @author Folyd
     *
     */
	static class ViewHolder {
		TextView item_letter;
		TextView item_city;
	}
}
