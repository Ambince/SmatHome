package com.xiaye.smarthome.fragment;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.jni.info.InfoDealIF.OutPut;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.DeviceInformationBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;

public class DeviceRegisterFragment extends Fragment implements OnClickListener {
	private String[] typeNames = {"插座及控制面板", "智能家电", "烹调器具", "安防设备", "其它"};
	InfoDealIF info = null;
	TextView dvTypeId_txt = null;
	TextView machineShapeCode_txt = null;

	EditText remark_edt = null;
	EditText dvName_edt = null;

	Button determine = null;
	Button cancel = null;

	DeviceInformationBean registerBean = null;
	int machineId = 0;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		info = new InfoDealIF();

		registerBean = (DeviceInformationBean) getArguments().getSerializable("sendRegisterInquirybean");
		Log.e("typeId", registerBean.getTypeId()+"");
		machineId = getArguments().getInt("machineId");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dv_register, null);
		initView(view);
		bindData();
		return view;

	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.dv_register_confirm_btn && registerBean != null) {
			JSONObject stoneObject = new JSONObject();
			try {
				stoneObject.put("machineId", machineId);
				stoneObject.put("machineName", dvName_edt.getText().toString());
				stoneObject.put("typeId", registerBean.getTypeId());
				stoneObject.put("remark", remark_edt.getText().toString());

				String sendRegist = stoneObject.toString();
				OutPut output = new OutPut();
				
				if (!(info.control(MainActivity.interfaceId,
						Type.UPDATE_MACHINE4, sendRegist.getBytes(), output) < 0)) {
					Toast.makeText(getActivity(), "信息补充成功！！", Toast.LENGTH_LONG)
							.show();

				} else {
					Toast.makeText(getActivity(), "信息补充失败！！请重试！",
							Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else {
			/*getActivity().getFragmentManager().beginTransaction().remove(this)
					.commit();*/
		}

	}

	/**
	 * 
	 * @Description:初始化控件
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-29 下午6:13:29
	 * @param view
	 */
	public void initView(View view) {

		dvTypeId_txt = (TextView) view.findViewById(R.id.register_dvtypeid_txt);
		machineShapeCode_txt = (TextView) view
				.findViewById(R.id.register_dvmcode_edt);

		remark_edt = (EditText) view.findViewById(R.id.register_dvremark_edt);
		dvName_edt = (EditText) view.findViewById(R.id.register_dvoname_edt);

		determine = (Button) view.findViewById(R.id.dv_register_confirm_btn);
		cancel = (Button) view.findViewById(R.id.dv_register_cancle_btn);

		determine.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	/**
	 * 
	 * @Description:绑定数据
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-29 下午6:14:03
	 */
	public void bindData() {
		if (registerBean != null) {
			String type =  typeNames[Integer.parseInt(registerBean.getTypeOfType())-1];
			dvTypeId_txt.setText(registerBean.getTypeName()+"("+type+")");
			machineShapeCode_txt.setText(registerBean.getDevice_id());
			remark_edt.setText(registerBean.getmNote());
			dvName_edt.setText(registerBean.getMachineName());
		} else {
			Toast.makeText(getActivity(), "未收到数据！", Toast.LENGTH_LONG).show();
		}
	}

}
