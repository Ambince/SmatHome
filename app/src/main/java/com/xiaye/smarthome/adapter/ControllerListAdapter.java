package com.xiaye.smarthome.adapter;

import java.util.List;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.ControllerBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ControllerListAdapter extends BaseAdapter {

	List<ControllerBean> controllerList;
	LayoutInflater inflater;

	public ControllerListAdapter(List<ControllerBean> controllerList,
			Context context) {
		inflater = LayoutInflater.from(context);
		this.controllerList = controllerList;
	}

	@Override
	public int getCount() {
		return controllerList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return controllerList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int postion, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.dialog_controller_item,
					null);
		}
		TextView id_txt = (TextView) convertView
				.findViewById(R.id.controller_id_txt);
		TextView name_txt = (TextView) convertView
				.findViewById(R.id.controller_name__txt);
		TextView state_txt = (TextView) convertView
				.findViewById(R.id.controller_state__txt);

		id_txt.setText(controllerList.get(postion).getControllerID() + "");
		name_txt.setText(controllerList.get(postion).getControllerName());
		if (controllerList.get(postion).getControllerState() == 1) {
			state_txt.setText("在线");
		} else {
			state_txt.setText("离线");
		}
		return convertView;
	}
}
