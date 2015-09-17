package com.xiaye.smarthome.adapter;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.jni.info.InfoDealIF.OutPut;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookUtensilBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.fragment.Dvinfofragment;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.ChangeByteAndInt;

public class CookUtensilListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater = null;
	private List<CookUtensilBean> list;
	private Holder holder = null;
	private Activity mactivity;
	private int[] useCount;

	public CookUtensilListAdapter(Activity activity, List<CookUtensilBean> cList, int ... useCount) {
		this.mactivity = activity;
		this.list = cList;
		layoutInflater = activity.getLayoutInflater();
		this.useCount = useCount;
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
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.cookingutensilitem,
					null);
			holder = new Holder();
			holder.cookname = (TextView) convertView
					.findViewById(R.id.cookingutensil_cookname_txt);
			holder.machinestate = (TextView) convertView
					.findViewById(R.id.cookingutensil_machinestate_txt);
			holder.look = (Button) convertView
					.findViewById(R.id.cookingutensil_look_btn);
			holder.logout = (Button) convertView
					.findViewById(R.id.cookingutensil_logout_btn);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.cookname.setText(list.get(position).getMachineName());

		int state = list.get(position).getMachineState();
		if (state == 1) {
			holder.machinestate.setText("在线");
		} else if (state == 0) {
			holder.machinestate.setText("离线");
		} else {
			holder.machinestate.setText("注销");
		}

		final int machineId = list.get(position).getMachineId();
		
		if (useCount != null && useCount.length != 0) {
			holder.look.setVisibility(View.GONE);
		}
		
		holder.look.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Dvinfofragment dvInfoFragment = new Dvinfofragment();
				Bundle bundle = new Bundle();
				bundle.putInt("machineId", machineId);
				if (useCount != null && useCount.length != 0) {
					bundle.putString("useNum", useCount[0]+"");
				}
				dvInfoFragment.setArguments(bundle);
				
				mactivity.getFragmentManager().beginTransaction()
						.replace(R.id.xiaye_fragment, dvInfoFragment). commitAllowingStateLoss();
			}
		});

		holder.logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				InfoDealIF info = new InfoDealIF();
				OutPut output = new OutPut();
				byte[] input = ChangeByteAndInt.intToBytes(machineId);

				info.control(MainActivity.interfaceId,
						Type.PROTO_FUN_UNREGISTER_DEVICE, input, output);
				byte[] flag = output.getOutput();

				if (flag[0] == 0) {
					Toast.makeText(mactivity, "注销成功！", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(mactivity, "注销失败！请重试！", Toast.LENGTH_LONG)
							.show();
				}
			}

		});

		return convertView;
	}

	public class Holder {

		public TextView cookname;
		public TextView machinestate;
		public Button look;
		public Button logout;

	}

}
