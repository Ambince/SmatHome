package com.xiaye.smarthome.fragment;

import java.util.List;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.CookingRecordListAdapter;
import com.xiaye.smarthome.bean.CookingRecordBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.ParseJson;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/*
 * 
 * @ClassName: DvinfoloadFragment
 * @Description: 下载烹调记录列表
 * @author  Android组-ChenSir/hemingli
 * @version 1.0
 * @date 2014-11-27 下午9:05:49
 *
 */

public class DvinfoloadFragment extends Fragment {

	ListView mListView = null;
	List<CookingRecordBean> mCookRcdBeans = null;
	CookingRecordListAdapter cRcdAdapter = null;

	int interfaceId;
	InfoDealIF info = null;
	String infoReceive = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			interfaceId = MainActivity.interfaceId;
			info = new InfoDealIF();
			infoReceive = info.inquire(interfaceId, Type.MENU_DETAIL4, null);
			Log.i("DvinfoloadFragment", infoReceive);
			if (infoReceive != null) {
				mCookRcdBeans = ParseJson.parseCookingRecordbean(infoReceive);
				cRcdAdapter = new CookingRecordListAdapter(getActivity(),
						mCookRcdBeans);
			} else {
				Toast.makeText(activity, "查询烹调记录列表信息失败！", Toast.LENGTH_LONG)
						.show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.loadcookingrecord,
				null);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mListView = (ListView) view.findViewById(R.id.loadcookingrecord_list);

		if (mCookRcdBeans != null) {

			mListView.setAdapter(cRcdAdapter);

			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {

					DvinfoloadListFragment fg = new DvinfoloadListFragment();

					Bundle bundle = new Bundle();
					bundle.putString("machineShapeCode",
							mCookRcdBeans.get(position).getMachineShapeCode());
					bundle.putInt("record", mCookRcdBeans.get(position)
							.getRecord());
					bundle.putString("dfPath", mCookRcdBeans.get(position)
							.getDatafilestoragepath());
					fg.setArguments(bundle);

					getActivity().getFragmentManager().beginTransaction()
							.replace(R.id.xiaye_fragment, fg).commit();
				}
			});
		}

	}
}
