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
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;

/*
 * 
 * @ClassName: DvinfoListFragment
 * @Description:上传设备列表
 * @author  Android��-HeMingli/ChenSir
 * @version 1.0
 * @date 2014-11-24 ����10:28:19
 *
 */
public class DvinfoListFragment extends Fragment {

	ListView listView = null;
	DvinfoListAdapter mDvinfoListAdapter = null;
	List<CookUtensilBean> mDeviceList = null;

	InfoDealIF info = null;
	String infoReceive = null;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			info = new InfoDealIF();
			JsonParse jsonParse = new JsonParse();
			infoReceive = info.inquire(MainActivity.interfaceId,
					Type.SELECT_MACHINE1,
					jsonParse.pagingJsonParse(0, 0, Type.SORT_HOMEAPP));
			if (infoReceive != null) {
				mDeviceList = ParseJson.parseCookUtensilList(infoReceive);
				if (mDeviceList != null && mDeviceList.size() != 0) {

					mDvinfoListAdapter = new DvinfoListAdapter(getActivity(),
							mDeviceList);
				}
			} else {
				Toast.makeText(activity.getApplicationContext(), "查询器具列表失败！",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(activity.getApplicationContext(), "数据解析失败！！",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = View.inflate(getActivity(), R.layout.dvinfolist, null);

		listView = (ListView) view.findViewById(R.id.dvinfolist_listview);
		if (mDvinfoListAdapter != null) {

			listView.setAdapter(mDvinfoListAdapter);

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {

						CookUtensilBean bean = mDeviceList.get(position);
						int machineId = bean.getMachineId();
						UploadCookRcdFragment uploadFragment = new UploadCookRcdFragment();

						Bundle bundle = new Bundle();
						bundle.putInt("machineId", machineId);
						uploadFragment.setArguments(bundle);

						getActivity().getFragmentManager().beginTransaction()
								.replace(R.id.xiaye_fragment, uploadFragment)
								.commit();
				}
			});
		}
		return view;
	}
}
