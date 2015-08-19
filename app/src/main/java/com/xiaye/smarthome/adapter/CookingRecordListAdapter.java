package com.xiaye.smarthome.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookingRecordBean;

public class CookingRecordListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<CookingRecordBean> list;
	private Holder holder = null;

	public CookingRecordListAdapter(Activity activity,
			List<CookingRecordBean> cList) {
		this.list = cList;
		layoutInflater = activity.getLayoutInflater();
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.cookingrecorditem,
					null);
			holder = new Holder();
			holder.menu_name = (TextView) convertView
					.findViewById(R.id.menuname_txt);
			holder.use_num = (TextView) convertView
					.findViewById(R.id.usenumber_txt);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.menu_name.setText(list.get(position).getMenuName());
		holder.use_num.setText(String
				.valueOf(list.get(position).getUsenumber()));
		return convertView;
	}

	public class Holder {

		public TextView menu_name;
		public TextView use_num;

	}

}
