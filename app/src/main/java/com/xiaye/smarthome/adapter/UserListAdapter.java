package com.xiaye.smarthome.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.UserBean;
import com.xiaye.smarthome.fragment.HealthSituationFragment;

/**
 * 
 * @ClassName: UserListAdapter
 * @Description: 用餐成员列表Adapter
 * @author Android组-ChengBin
 * @version 1.0
 * @date 2014-11-24 下午7:25:42
 * 
 */
public class UserListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<UserBean> users;
	private Holder holder = null;
	private Activity activity;
	public static HashMap<Integer, Boolean> isSelected;

	public UserListAdapter(Activity activity, List<UserBean> users) {
		this.activity = activity;
		this.users = users;
		layoutInflater = activity.getLayoutInflater();
		isSelected = new HashMap<Integer, Boolean>();
		initData();
	}

	private void initData() {
		for (int i = 0; i < users.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public int getCount() {
		return this.users.size();
	}

	@Override
	public Object getItem(int position) {
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int id = position;

//		LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		
		convertView = layoutInflater.inflate(R.layout.fragment_user_list_item,
				null);
		
		holder = new Holder();
		holder.cb_selected = (CheckBox) convertView
				.findViewById(R.id.cb_select_eating);
		holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		holder.tv_nick = (TextView) convertView.findViewById(R.id.tv_nick);
		holder.tv_health = (Button) convertView.findViewById(R.id.tv_health);
		holder.iv_delete = (ImageButton) convertView
				.findViewById(R.id.iv_delete);

		UserBean user = users.get(position);
		holder.tv_name.setText(user.getMemberName());
		holder.tv_nick.setText(user.getCallName());
		holder.tv_health.setText("健康状态");
		holder.cb_selected.setChecked(getIsSelected().get(position));
		holder.cb_selected
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						getIsSelected().put(id, isChecked);
					}
				});
		holder.tv_health.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activity.getFragmentManager()
						.beginTransaction()
						.replace(R.id.xiaye_fragment,
								new HealthSituationFragment()).commit();
			}
		});
		return convertView;
	}

	public class Holder {
		public CheckBox cb_selected;
		public TextView tv_name;
		public TextView tv_nick;
		public Button tv_health;
		public ImageButton iv_delete;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		UserListAdapter.isSelected = isSelected;
	}
}
