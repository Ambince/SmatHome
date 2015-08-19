package com.xiaye.smarthome.fragment;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.MaterialAdapter;
import com.xiaye.smarthome.bean.CookingRecordBean;
import com.xiaye.smarthome.bean.MaterialBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.constant.UI_Constant;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.main.SmartHomeApplication;
import com.xiaye.smarthome.util.Connect2ByteArrays;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;

/**
 * 
 * @ClassName: MaterialFragment
 * @Description: 原料表
 * @author Android组-ChengBin/ChenSir
 * @version 1.0
 * @date 2014-11-27 下午6:51:06
 * 
 */
public class MaterialFragment extends Fragment {

	private ListView materialListView;
	private Button btn_back;
	private Button btn_executeCooking;

	private String cooking_name;
	private String cooking_item_name;

	InfoDealIF info = new InfoDealIF();
	String receive = null;

	CookingRecordBean sBean = null;
	String exctCookingFlag = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		sBean = (CookingRecordBean) getArguments().getSerializable(
				"cRecordSelected");
		Log.e("Material", "sBean = " + sBean);
		exctCookingFlag = getArguments().getString(UI_Constant.FLAG);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_material, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		cooking_item_name = getArguments().getString(
				UI_Constant.COOKING_ITEM_NAME, null);
		cooking_name = getArguments().getString(UI_Constant.COOKING_NAME, null);
		initView(view);
		bindData();
		setListener();
	}

	/**
	 * 
	 * @Description: 初始化所有控件
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-26 下午4:46:44
	 * @param view
	 */
	private void initView(View view) {
		materialListView = (ListView) view.findViewById(R.id.lv_material_list);
		btn_back = (Button) view.findViewById(R.id.btn_back);
		btn_executeCooking = (Button) view
				.findViewById(R.id.btn_executecooking);
	}

	/**
	 * 
	 * @Description: 绑定数据到控件
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-26 下午4:47:03
	 */
	private void bindData() {
		List<MaterialBean> materials = listloadDataFromDB();
		if (materials != null && materials.size() != 0) {

			materialListView.setAdapter(new MaterialAdapter(getActivity(),
					materials));
		}
	}

	/**
	 * 
	 * @Description: 设置监听
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-27 下午6:49:56
	 */
	private void setListener() {
		btn_back.setOnClickListener(backClickListener);
		btn_executeCooking.setOnClickListener(cookClickListener);
	}

	OnClickListener backClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {

			CookingInroFragment fragment = new CookingInroFragment();
			Bundle bundle = new Bundle();

			bundle.putString(UI_Constant.COOKING_ITEM_NAME, cooking_item_name);
			bundle.putString(UI_Constant.COOKING_NAME, cooking_name);
			
			bundle.putInt("useNum", getArguments().getInt("useNum"));
			bundle.putString(UI_Constant.FLAG, exctCookingFlag);
			bundle.putString(UI_Constant.COOKING_SCHEMA, getArguments()
					.getString(UI_Constant.COOKING_SCHEMA));
			bundle.putString("cuisinesName",
					getArguments().getString("cuisinesName"));
			bundle.putString("machineShapeCode",
					getArguments().getString("machineShapeCode"));
			bundle.putString(UI_Constant.COOKING_URI,
					getArguments().getString(UI_Constant.COOKING_URI));
			bundle.putSerializable("cookMenuBean", getArguments()
					.getSerializable("cookMenuBean"));
			bundle.putString(getArguments().getString(
							UI_Constant.COOKING_URI, ""), getArguments()
							.getString(UI_Constant.COOKING_URI, ""));

			fragment.setArguments(bundle);
			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.xiaye_fragment, fragment).commit();
		}
	};

	OnClickListener cookClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			if ("cooking".equals(exctCookingFlag)) {
				/*
				 * 执行烹调
				 */
				MainActivity.downloadFlag = 1;
				// 设备虚拟地址
				int machineId = SmartHomeApplication.machineId;
				Log.e("MaterialFragment row:152", "machineId = " + machineId);
				// 记录号
				int record = sBean.getRecord();
				String dataFilePath = sBean.getDatafilestoragepath();
				Log.e("MaterialFragment", "dataFilePath = " + dataFilePath);
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

				Log.e("MaterialFragment", "data3 = " + data3);
				int flag = info.control(MainActivity.interfaceId,
						Type.PROTO_FILE_TODEVICE, data3, null);
				Log.e("materialFG", "flag = " + flag);
				if (flag == 0) {
					Toast.makeText(getActivity(), "正在下载烹调记录.....",
							Toast.LENGTH_SHORT).show();

					// 通过烹调记录ID查询记录节点表 提示信息和节点编号
					JsonParse jsonParse = new JsonParse();
					String tipString = info.inquire(
							MainActivity.interfaceId,
							Type.SELECT_TIMING1,
							jsonParse.pagingJsonParse(0, 0,
									sBean.getFoodProcessingId()));

					SmartHomeApplication.appMap.put(
							UI_Constant.PLUGIN_HINTMSG_KEY, tipString);

				} else {
					Toast.makeText(getActivity(), "无法下载！", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Toast.makeText(getActivity(), "请从选择“用餐成员”开始进行烹调！",
						Toast.LENGTH_LONG).show();
				// DvinfoloadFragment fg = new DvinfoloadFragment();
				// Bundle bundle = new Bundle();
				// String mCode = sBean.getMachineShapeCode();
				// int record = sBean.getRecord();
				// String dfPath = sBean.getDatafilestoragepath();
				// bundle.putString("machineShapeCode", mCode);
				// bundle.putString("dfPath", dfPath);
				// bundle.putInt("record", record);
				// fg.setArguments(bundle);
				// getActivity().getFragmentManager().beginTransaction()
				// .replace(R.id.xiaye_fragment, fg).commit();
			}
		}

	};

	/****
	 * 
	 * @Description: 从数据库加载数据
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-26 下午5:05:00
	 * @return
	 */
	private List<MaterialBean> listloadDataFromDB() {

		List<MaterialBean> materialList = null;
		info = new InfoDealIF();
		JsonParse jsonParse = new JsonParse();

		if (sBean != null) {

			Log.e("Material", "food ID = " + sBean.getFoodProcessingId());
			receive = info
					.inquire(
							MainActivity.interfaceId,
							Type.MENU_DETAIL3,
							jsonParse.pagingJsonParse(0, 0,
									sBean.getFoodProcessingId()));

			Log.e("Material", "receive = " + receive);
			if (receive != null) {
				try {
					materialList = ParseJson.parseMaterialBeanList(receive);
				} catch (Exception e) {
					Toast.makeText(getActivity(), "解析数据出错！", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}
			}
			return materialList;
		}
		return null;

	}

}
