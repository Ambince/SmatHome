package com.xiaye.smarthome.fragment;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookingRcdStepBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;

public class EachNodeInfoFragment extends Fragment implements OnClickListener {

	int foodProcessingId = 0;

	TextView nodeNo_txt = null;
	TextView nodeTime_txt = null;
	EditText tipInfo_edt = null;

	Button nextNode_btn = null;
	Button priorNode_btn = null;
	Button back_btn = null;

	InfoDealIF info = null;
	JsonParse jsonParse = null;

	List<CookingRcdStepBean> cRcdStepBeans = null;
	int position = 0;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		foodProcessingId = getArguments().getInt("foodProcessingId");
		info = new InfoDealIF();
		jsonParse = new JsonParse();
		String receive = info.inquire(MainActivity.interfaceId,
				Type.SELECT_TIMING2,
				jsonParse.pagingJsonParse(0, 0, foodProcessingId));
		try {
			cRcdStepBeans = ParseJson.parseCookingRcdStepBean(receive);

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(activity, "未获取到信息！", Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = View.inflate(getActivity(), R.layout.everynodeinfo, null);
		initView(view);
		bindData();

		return view;

	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.everynodeinfo_next_btn) {
			// 下一个
			while (position < cRcdStepBeans.size()) {

				position++;

				nodeNo_txt.setText(String.valueOf(cRcdStepBeans.get(position)
						.getNodenumber()));
				nodeTime_txt.setText(String.valueOf(cRcdStepBeans.get(position)
						.getTimeOfNode()));
				tipInfo_edt.setText(cRcdStepBeans.get(position).getTips());
			}
		} else if (id == R.id.everynodeinfo_last_btn) {
			// 上一个
			if (position != 0) {
				position--;
				nodeNo_txt.setText(String.valueOf(cRcdStepBeans.get(position)
						.getNodenumber()));
				nodeTime_txt.setText(String.valueOf(cRcdStepBeans.get(position)
						.getTimeOfNode()));
				tipInfo_edt.setText(cRcdStepBeans.get(position).getTips());
			} else {
				Toast.makeText(getActivity(), "已经是第一个节点！", Toast.LENGTH_LONG)
						.show();
			}

			// } else if (id == R.id.everynodeinfo_save_btn) {
			// // 保存
			//
			//
			//
			// } else if (id == R.id.everynodeinfo_edit_btn) {
			// // 编辑
			// tipInfo_edt.setEnabled(true);
			// Toast.makeText(getActivity(), "编辑后请点击保存！", Toast.LENGTH_LONG)
			// .show();
		} else {
			// 返回
			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.xiaye_fragment, new CookbookDetailFragment())
					.commit();
		}
	}

	/**
	 * 
	 * @Description:初始化控件
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-29 下午2:55:12
	 * @param view
	 */
	public void initView(View view) {

		nodeNo_txt = (TextView) view
				.findViewById(R.id.everynodeinfo_nodenum_txt);
		nodeTime_txt = (TextView) view
				.findViewById(R.id.everynodeinfo_nodetime_txt);
		tipInfo_edt = (EditText) view.findViewById(R.id.everynodeinfo_tips_edt);

		nextNode_btn = (Button) view.findViewById(R.id.everynodeinfo_next_btn);
		priorNode_btn = (Button) view.findViewById(R.id.everynodeinfo_last_btn);
		back_btn = (Button) view.findViewById(R.id.everynodeinfo_back_btn);
	}

	/**
	 * 
	 * @Description:绑定数据(默认初始化第一个节点数据)
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-29 下午2:55:41
	 */
	public void bindData() {

		nodeNo_txt
				.setText(String.valueOf(cRcdStepBeans.get(0).getNodenumber()));
		nodeTime_txt.setText(String.valueOf(cRcdStepBeans.get(0)
				.getTimeOfNode()));
		tipInfo_edt.setText(cRcdStepBeans.get(0).getTips());

		tipInfo_edt.setEnabled(false);
	}

	/**
	 * 
	 * @Description:设置监听
	 *
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-29 下午3:50:19
	 */
	public void setListener() {
		nextNode_btn.setOnClickListener(this);
		priorNode_btn.setOnClickListener(this);
		back_btn.setOnClickListener(this);
	}

}
