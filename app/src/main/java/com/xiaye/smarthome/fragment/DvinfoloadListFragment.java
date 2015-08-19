package com.xiaye.smarthome.fragment;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.DvinfoListAdapter;
import com.xiaye.smarthome.bean.CookUtensilBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.Connect2ByteArrays;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;

public class DvinfoloadListFragment extends Fragment {

	ListView listview = null;
	DvinfoListAdapter dvListAdapter = null;

	List<CookUtensilBean> machineList = null;// 设备信息列表
	String recieve;// 数据库查询获得数据

	String machineShapeCode = null;
	int record = 0;// 记录号
	String dataFilePath = null;// 路径

	InfoDealIF info;
	JsonParse jsonParse;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		machineShapeCode = getArguments().getString("machineShapeCode");
		record = getArguments().getInt("record");
		dataFilePath = getArguments().getString("dfPath");

		info = new InfoDealIF();
		/*
		 * 根据设备条形码查询设备列表
		 */
		recieve = info.inquire(MainActivity.interfaceId, Type.SELECT_MACHINE5,
				null);
		if (recieve != null) {

			machineList = ParseJson.parseDeviceInforbean(recieve);

			if (machineList != null && machineList.size() != 0) {
				dvListAdapter = new DvinfoListAdapter(activity, machineList);
			} else {
				Toast.makeText(activity.getApplicationContext(), "解析数据失败！",
						Toast.LENGTH_LONG).show();
			}

		} else {
			Toast.makeText(activity.getApplicationContext(), "查询器具列表信息失败！",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = View.inflate(getActivity(), R.layout.loaddvinfolist, null);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		listview = (ListView) view.findViewById(R.id.loaddvinfolist_listview);
		listview.setAdapter(dvListAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				CookUtensilBean dInfo = machineList.get(position);
				// 设备虚拟地址
				int machineId = dInfo.getMachineId();
				byte[] data = null;
				int length = 4;
				data = new byte[length + 5];
				data[0] = (byte) (0xff & machineId);
				data[1] = (byte) ((0xff00 & machineId) >> 8);
				data[2] = (byte) ((0xff0000 & machineId) >> 16);
				data[3] = (byte) ((0xff000000 & machineId) >> 24);
				data[length] = 0x30;// 文件类型
				data[length + 1] = (byte) (0xff & record);
				data[length + 2] = (byte) ((0xff00 & record) >> 8);
				data[length + 3] = (byte) ((0xff0000 & record) >> 16);
				data[length + 4] = (byte) ((0xff000000 & record) >> 24);

				byte[] data2 = dataFilePath.getBytes();
				byte[] data3 = Connect2ByteArrays.conn2ByteArrays(data, data2);
				if ((info.control(MainActivity.interfaceId,
						Type.PROTO_FILE_TODEVICE, data3, null)) == 0) {
					Toast.makeText(getActivity(), "请等待下载.....",
							Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getActivity(), "下载失败！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
