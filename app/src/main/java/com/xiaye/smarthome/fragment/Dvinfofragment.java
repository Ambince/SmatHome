package com.xiaye.smarthome.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.DeviceInformationBean;
import com.xiaye.smarthome.bean.DvinfoBean;
import com.xiaye.smarthome.bean.PluginBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.main.SmartHomeApplication;
import com.xiaye.smarthome.util.GetDataFromSharedPreference;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;
import com.xiaye.smarthome.util.PluginBeanService;
import com.xiaye.smarthome.util.SDCardUtils;

/*
 * 
 * @ClassName: Dvinfofragment
 * @Description: 设备详细信息
 * @author  Android��-hemingli/ChenSir
 * @version 1.0
 * @date 2014-11-24 ����10:27:17
 *
 */
public class Dvinfofragment extends Fragment implements OnClickListener {

	private String[] typeNames = { "插座及控制面板", "智能家电", "烹调器具", "安防设备", "其它" };
	private String[] registerWays = { "自动", "手动" };
	private ArrayList<DvinfoBean> allTypes = new ArrayList<DvinfoBean>();
	// UI
	TextView machineId_txt;
	TextView machineShapeCode_txt;
	TextView machineAddress_txt;
	TextView machinePort_txt;
	TextView terminal_programId_txt;
	TextView driverModuleId_txt;
	TextView interfaceModuleId_txt;
	Spinner registerWay_edt;
	EditText machineName_edt;
	Spinner typeId_edt;
	EditText remark_edt;

	Button dvSave_btn;
	Button dvEdit_btn;
	Button dvBack_btn;

	InfoDealIF info;

	JsonParse jsonParse;
	String dvInfoDetail_receive;
	DeviceInformationBean dvInfoDetailbean = null;

	int machineId = 0;

	Activity mActivity = null;

	String machineShapeCode;
	DvinfoBean dvinfoBean;
	int dvTypeId = 0;
	int dvRegisterWay = 0;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mActivity = activity;
		info = new InfoDealIF();
		jsonParse = new JsonParse();
		// 获取参数
		machineId = getArguments().getInt("machineId");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.dvinfodetail, null);
		initView(view);

		// 查询数据库
		dvInfoDetail_receive = info.inquire(MainActivity.interfaceId,
				Type.SELECT_MACHINE7,
				jsonParse.pagingJsonParse(0, 0, machineId));
		String typesString = info.inquire(MainActivity.interfaceId,
				Type.SELECT_MACHINE_TYPE,
				jsonParse.pagingJsonParse(0, 0, machineId));
		try {
			if (dvInfoDetail_receive != null && typesString != null) {
				dvInfoDetailbean = ParseJson
						.parseDvInfoDetail(dvInfoDetail_receive);
				JSONArray array = new JSONArray(typesString);
				JSONObject jsonObject = array.getJSONObject(0);
				dvinfoBean = new DvinfoBean();
				dvinfoBean.setTypeId(jsonObject.getInt("typeId"));
				dvinfoBean.setTypeOfType(jsonObject.getInt("typeOfType"));
				dvinfoBean.setTypeName(jsonObject.getString("typeName"));

				// 所有类型
				String allType = info.inquire(MainActivity.interfaceId,
						Type.SELECT_TYPE3,
						jsonParse.pagingJsonParse(0, 0, machineId));
				JSONArray typeArray = new JSONArray(allType);
				for (int i = 0; i < typeArray.length(); i++) {
					JSONObject typeJson = typeArray.getJSONObject(i);
					DvinfoBean bean = new DvinfoBean();
					bean.setTypeId(typeJson.getInt("typeId"));
					bean.setTypeName(typeJson.getString("typeName"));
					bean.setTypeOfType(typeJson.getInt("typeOfType"));
					allTypes.add(bean);
				}
			} else {
				Toast.makeText(getActivity().getApplicationContext(),
						"查询器具详细信息失败！", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (dvInfoDetailbean != null) {
			setDataToView();
			uninstallPlugin(inflater, view);
		}
		return view;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.dvdetail_edit_btn) {
			// 编辑
			registerWay_edt.setEnabled(true);
			machineName_edt.setEnabled(true);
			dvSave_btn.setEnabled(true);
			// TODO 类别修改暂时不开放
			typeId_edt.setEnabled(false);
			remark_edt.setEnabled(true);

			// 类别id
			String[] typeIdInfos = new String[allTypes.size()];
			for (int i = 0; i < allTypes.size(); i++) {
				String typeInfo = allTypes.get(i).getTypeName() + "("
						+ typeNames[allTypes.get(i).getTypeOfType() - 1] + ")";
				typeIdInfos[i] = typeInfo;
			}
			typeId_edt.setAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item, typeIdInfos));
			dvTypeId = dvInfoDetailbean.getTypeId() - 1;
			typeId_edt.setSelection(dvTypeId);

			// 注册方式
			registerWay_edt.setAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item, registerWays));
			dvRegisterWay = dvInfoDetailbean.getRegisterWay() - 1;
			registerWay_edt.setSelection(dvRegisterWay);

			Toast.makeText(mActivity.getApplicationContext(), "编辑后请点击保存！",
					Toast.LENGTH_LONG).show();

		} else if (view.getId() == R.id.dvdetail_back_btn) {
			String count = getArguments().getString("useNum");
			CookUtensilFragment cFragment = new CookUtensilFragment();
			if (count != null) {
				SmartHomeApplication.exctCookingFlag = "cooking";
				Bundle bundle = new Bundle();
				bundle.putInt("useNum", Integer.parseInt(count));
				cFragment.setArguments(bundle);
			}
			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.xiaye_fragment, cFragment).commit();
		} else {
			// 保存
			int registerWay = registerWay_edt.getSelectedItemPosition() + 1;
			String machineName = machineName_edt.getText().toString();
			int typeId = allTypes.get(typeId_edt.getSelectedItemPosition())
					.getTypeId();

			String remark = remark_edt.getText().toString();
			// 转为JSON格式
			String jsonToDB = jsonParse.updateDeviceInfo(machineId,
					registerWay, machineName, typeId, remark);
			Log.e("保存", jsonToDB);

			int flag = info.control(MainActivity.interfaceId,
					Type.UPDATE_MACHINE2, jsonToDB.getBytes(), null);
			if (flag >= 0) {
				dvInfoDetail_receive = info.inquire(MainActivity.interfaceId,
						Type.SELECT_MACHINE7,
						jsonParse.pagingJsonParse(0, 0, machineId));
				Log.e("更新成功，查询数据", dvInfoDetail_receive);
				Toast.makeText(mActivity.getApplicationContext(), "更新成功！",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(mActivity.getApplicationContext(), "更新失败！",
						Toast.LENGTH_LONG).show();
			}
		}

	}

	/**
	 * 
	 * @Description:初始化控件
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-28 下午3:23:48
	 * @param view
	 */
	public void initView(View view) {

		machineId_txt = (TextView) view.findViewById(R.id.dvinfo_machineId_edt);
		machineShapeCode_txt = (TextView) view
				.findViewById(R.id.dvinfo_machineShapeCode_edt);
		machineAddress_txt = (TextView) view
				.findViewById(R.id.dvinfo_mAddress_edt);
		machinePort_txt = (TextView) view.findViewById(R.id.dvinfo_mPort_edt);
		terminal_programId_txt = (TextView) view
				.findViewById(R.id.dvinfo_mterminalPId_edt);
		driverModuleId_txt = (TextView) view
				.findViewById(R.id.dvinfo_mdriverMId_edt);
		interfaceModuleId_txt = (TextView) view
				.findViewById(R.id.dvinfo_mifModuleId_edt);

		registerWay_edt = (Spinner) view
				.findViewById(R.id.dvinfo_mregisterway_edt);

		machineName_edt = (EditText) view.findViewById(R.id.dvinfo_mName_edt);
		typeId_edt = (Spinner) view.findViewById(R.id.dvinfo_mtypeId_edt);
		remark_edt = (EditText) view.findViewById(R.id.dvinfo_mremark_edt);

		dvSave_btn = (Button) view.findViewById(R.id.dvdetail_save_btn);
		dvEdit_btn = (Button) view.findViewById(R.id.dvdetail_edit_btn);
		dvBack_btn = (Button) view.findViewById(R.id.dvdetail_back_btn);

		registerWay_edt.setEnabled(false);
		machineName_edt.setEnabled(false);
		typeId_edt.setEnabled(false);
		remark_edt.setEnabled(false);
		dvSave_btn.setEnabled(false);

		dvEdit_btn.setOnClickListener(this);
		dvSave_btn.setOnClickListener(this);
		dvBack_btn.setOnClickListener(this);
	}

	/**
	 * 
	 * @Description:绑定数据及监听
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-28 下午3:27:29
	 */
	public void setDataToView() {

		machineId_txt.setText(String.valueOf(dvInfoDetailbean
				.getDevice_Vaddrs()));
		machineShapeCode_txt.setText(dvInfoDetailbean.getDevice_id());
		machineAddress_txt.setText(String.valueOf(dvInfoDetailbean
				.getDevice_addrs()));
		machinePort_txt.setText(String.valueOf(dvInfoDetailbean
				.getMachinePort()));
		terminal_programId_txt.setText(dvInfoDetailbean.getTerminalPId());
		driverModuleId_txt.setText(dvInfoDetailbean.getDriverModuleId());
		interfaceModuleId_txt.setText(dvInfoDetailbean.getInterfaceModuleId());

		int registerWay = dvInfoDetailbean.getRegisterWay();
		String wayString = registerWays[registerWay - 1];
		registerWay_edt.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item,
				new String[] { wayString }));

		machineName_edt.setText(dvInfoDetailbean.getMachineName());

		String type = typeNames[dvinfoBean.getTypeOfType() - 1];
		String dvTypeId = dvinfoBean.getTypeName() + "(" + type + ")";
		Log.e("dvTypeId", dvTypeId);
		typeId_edt
				.setAdapter(new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_item,
						new String[] { dvTypeId }));

		remark_edt.setText(dvInfoDetailbean.getmNote());

	}

	/**
	 * 
	 * @Description: 检测是否安装该设备对应的插件包,若安装显示卸载按钮
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-28 下午3:31:42
	 * @param inflater
	 * @param view
	 */
	public void uninstallPlugin(LayoutInflater inflater, View view) {

		machineShapeCode = dvInfoDetailbean.getDevice_id();
		List<PluginBean> allList = PluginBeanService.getAllPluginBean(mActivity
				.getApplicationContext());
		if (allList != null) {
			for (int i = 0; i < allList.size(); i++) {
				final PluginBean pluginBean = allList.get(i);
				final String mSCode = pluginBean.getMatchMcSCode();
				if (mSCode.equals(machineShapeCode)) {
					final Button btn = (Button) view.findViewById(R.id.dyn_btn);
					btn.setBackgroundResource(R.drawable.btn);
					btn.setText("删除设备插件");
					btn.setVisibility(View.VISIBLE);
					btn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							// TODO:删除插件文件，并清除SP
							GetDataFromSharedPreference.removePreference(
									mSCode, getActivity());
							SDCardUtils util = new SDCardUtils();
							util.DeleteFolder(SmartHomeApplication.mHTMLPath
									+ machineShapeCode);
							Toast.makeText(
									getActivity().getApplicationContext(),
									"插件删除成功！", Toast.LENGTH_LONG).show();
							btn.setVisibility(View.GONE);
						}
					});
					break;
				} else {
					Log.e("DvInfoDetail", "未找到对应序列号");
				}
			}
		}
	}
}
