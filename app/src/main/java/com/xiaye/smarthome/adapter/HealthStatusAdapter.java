package com.xiaye.smarthome.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.Situation;
import com.xiaye.smarthome.bean.UserBean;

/**
 * 
 * @ClassName: SituationAdapter
 * @Description: 用户健康状况Adapter
 * @author  Android组-ChengBin
 * @version 1.0
 * @date 2014-11-25 下午2:34:00
 *
 */
public class HealthStatusAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<Situation> situations;
	private Holder holder = null;
	
	public HealthStatusAdapter(Context context, List<Situation> situations) {
		this.situations = situations;
		layoutInflater = LayoutInflater.from(context);
	}
	
	
	@Override
	public int getCount() {
		return this.situations.size();
	}

	@Override
	public Object getItem(int position) {
		return situations.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.fragment_health_situation_item, null);
			holder = new Holder();
	
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_result = (TextView) convertView.findViewById(R.id.tv_result);
			holder.tv_unit = (TextView) convertView.findViewById(R.id.tv_unit);
			holder.tv_value = (TextView) convertView.findViewById(R.id.tv_value);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		Situation situation = situations.get(position);
		holder.tv_name.setText(situation.name);
		holder.tv_result.setText(situation.result);
		holder.tv_unit.setText(situation.unit);
		holder.tv_value.setText(situation.value);
		return convertView;
	}
	
	
	
	public class Holder {
		public TextView tv_name;
		public TextView tv_result;
		public TextView tv_unit;
		public TextView tv_value;
	}

}
