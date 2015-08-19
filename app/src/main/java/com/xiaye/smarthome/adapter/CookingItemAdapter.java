package com.xiaye.smarthome.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookMenuBean;

public class CookingItemAdapter extends BaseAdapter {

	private List<CookMenuBean> datas;
	private LayoutInflater layoutInflater;

	public CookingItemAdapter(Context context, List<CookMenuBean> datas) {
		layoutInflater = LayoutInflater.from(context);
		this.datas = datas;

	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int pos) {
		return datas.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Holder holder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.fragment_cooking_item_item, null);
			holder = new Holder();

			holder.tv_id = (TextView) convertView.findViewById(R.id.item_id);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.item_name);
			holder.tv_attribute = (TextView) convertView
					.findViewById(R.id.item_attribute);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		CookMenuBean data = datas.get(position);
		Log.e("CookingItemAdapter", data+"");
		StringBuilder attribute = new StringBuilder();
		if (data.isMeat()) {
			attribute.append("荤菜");
		}else{
			attribute.append("素菜");
		}
		if (data.isDry()) {
			attribute.append(",干菜");
		}else{
			attribute.append(",湿菜");
		}
		int i = 0;
		holder.tv_id.setText((i++)+"");
		holder.tv_name.setText(data.getMenuName());
		Log.e("CookingItemAdapter", data.getMenuName());
		attribute.append(","+data.getColor());
		holder.tv_attribute.setText(attribute.toString());
		return convertView;
	}

	public class Holder {
		public TextView tv_id;
		public TextView tv_name;
		public TextView tv_attribute;
	}

}
