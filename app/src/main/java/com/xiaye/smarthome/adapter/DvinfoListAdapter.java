package com.xiaye.smarthome.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookUtensilBean;

public class DvinfoListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<CookUtensilBean> list;
	private Holder holder = null;

	public DvinfoListAdapter(Activity activity,
			List<CookUtensilBean> cList) {
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
			convertView = layoutInflater.inflate(R.layout.dvinfolistitem, null);
			holder = new Holder();
			holder.machineName = (TextView) convertView
					.findViewById(R.id.dvinfolistitem_dvname_txt);
			holder.mState = (TextView) convertView
					.findViewById(R.id.dvinfolistitem_state_txt);
			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.machineName.setText(list.get(position).getMachineName());
		int state = list.get(position).getMachineState();
		if (state == 1) {
			holder.mState.setText("在线");
		} else if (state == 0) {
			holder.mState.setText("离线");
		} else {
			holder.mState.setText("注销");
		}

		return convertView;
	}

	public class Holder {

		public TextView machineName;
		public TextView mState;

	}

}
