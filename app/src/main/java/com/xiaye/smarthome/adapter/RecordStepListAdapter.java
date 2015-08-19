package com.xiaye.smarthome.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.RecordOnDvBean;

public class RecordStepListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<RecordOnDvBean> recordList = null;
	private Holder holder = null;

	public static HashMap<Integer, Boolean> isSelected;

	public RecordStepListAdapter(Activity activity,
			List<RecordOnDvBean> recordList) {
		this.recordList = recordList;
		layoutInflater = activity.getLayoutInflater();
		isSelected = new HashMap<Integer, Boolean>();
		initData();
	}

	private void initData() {
		for (int i = 0; i < recordList.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public int getCount() {
		return recordList.size();
	}

	@Override
	public Object getItem(int position) {
		return recordList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final int id = position;

		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.recordstepitem, null);
			holder = new Holder();
			holder.recordnum = (TextView) convertView
					.findViewById(R.id.recordstep_recordnum_txt);
			holder.recordState = (TextView) convertView
					.findViewById(R.id.recordstep_state_txt);
			holder.check = (CheckBox) convertView
					.findViewById(R.id.recordstep_checkbox);

			convertView.setTag(holder);

		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.recordnum.setText(recordList.get(position).getRecordNo() + "");

		if (recordList.get(position).isUploaded()) {
			holder.recordState.setText("已上传");
		} else {
			holder.recordState.setText("未上传");
		}

		holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				getIsSelected().put(id, isChecked);
			}
		});

		return convertView;
	}

	public class Holder {

		public TextView recordnum;
		public TextView recordState;
		public CheckBox check;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		UserListAdapter.isSelected = isSelected;
	}
}
