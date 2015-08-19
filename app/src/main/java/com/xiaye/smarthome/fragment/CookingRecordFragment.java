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
import com.xiaye.smarthome.adapter.CookingRecordListAdapter;
import com.xiaye.smarthome.bean.CookingRecordBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.ParseJson;

/*
 * 
 * @ClassName: CookingRecordFragment
 * @Description: 查看烹调记录列表
 * @author  Android��-HeMingli/ChenSir
 * @version 1.0
 * @date 2014-11-24 ����10:24:52
 *
 */
public class CookingRecordFragment extends Fragment {

	ListView mListView = null;
	List<CookingRecordBean> mCookRcdBeansList = null;
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
			if (infoReceive != null) {
				mCookRcdBeansList = ParseJson
						.parseCookingRecordbean(infoReceive);
				cRcdAdapter = new CookingRecordListAdapter(getActivity(),
						mCookRcdBeansList);
			} else {
				Toast.makeText(activity.getApplicationContext(),
						"查询烹调记录列表信息失败！", Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.cookingrecord, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mListView = (ListView) view.findViewById(R.id.cookingrecord_list);
		mListView.setAdapter(cRcdAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				CookbookDetailFragment fragment = new CookbookDetailFragment();
				CookingRecordBean bean = mCookRcdBeansList.get(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("cookingRecordBean", bean);
				fragment.setArguments(bundle);
				getActivity().getFragmentManager().beginTransaction()
						.replace(R.id.xiaye_fragment, fragment).commit();
			}
		});
	}
}
