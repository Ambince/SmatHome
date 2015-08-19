package com.xiaye.smarthome.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.MaterialBean;

/**
 * 
 * @ClassName: MaterialAdapter
 * @Description: 菜谱加工原料Adapter
 * @author Android组-ChengBin
 * @version 1.0
 * @date 2014-11-26 下午4:51:58
 * 
 */
public class MaterialAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<MaterialBean> materials;
	private Holder holder = null;

	public MaterialAdapter(Context context, List<MaterialBean> materials) {
		this.materials = materials;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return this.materials.size();
	}

	@Override
	public Object getItem(int position) {
		return materials.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.fragment_material_item, null);
			holder = new Holder();

			holder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_material_name);
			holder.tv_kinds = (TextView) convertView
					.findViewById(R.id.tv_material_kinds);
			holder.tv_number = (TextView) convertView
					.findViewById(R.id.tv_material_number);
			holder.tv_make = (TextView) convertView
					.findViewById(R.id.tv_material_make);
			holder.tv_no = (TextView) convertView
					.findViewById(R.id.tv_material_no);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		MaterialBean material = materials.get(position);
		holder.tv_name.setText(material.getMaterialName());

		// TODO
		int typeId = material.getTypeId();
		if (typeId == 1) {
			holder.tv_kinds.setText( "原料");
		}else if (typeId == 2) {
			holder.tv_kinds.setText( "辅料");
		}else{
			holder.tv_kinds.setText( "配料");
		}
		
		holder.tv_number.setText(material.getMaterialNumber());
		holder.tv_make.setText(material.getMaterialProcessingMethod());
		holder.tv_no.setText(material.getMaterialProcessingNumber() + "");
		
		return convertView;
	}

	public class Holder {
		public TextView tv_name;
		public TextView tv_kinds;
		public TextView tv_number;
		public TextView tv_make;
		public TextView tv_no;
	}

}
