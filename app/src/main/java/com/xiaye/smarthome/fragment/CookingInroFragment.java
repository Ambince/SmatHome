package com.xiaye.smarthome.fragment;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookMenuBean;
import com.xiaye.smarthome.bean.CookingRecordBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.constant.UI_Constant;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;

/**
 * 
 * @ClassName: CookingInroFragment
 * @Description: 菜谱介绍
 * @author Android组-ChengBin/ChenSir
 * @version 1.0
 * @date 2014-11-26 上午10:14:11
 * 
 */
public class CookingInroFragment extends Fragment {

	public static String TAG = CookingInroFragment.class.getSimpleName();

	private TextView tv_cooking_name;
	private TextView tv_cooking_intro;
	private TextView tv_make_intro;
	private Spinner sp_people_number;
	private Button btn_confirm;
	private Button btn_back;
	private String cooking_item_name;
	private String cooking_name;

	CookMenuBean mBean = null;
	String menuId = null;
	List<CookingRecordBean> cRecordBeanList = null;
	List<String> useNumsList = null;

	CookingRecordBean selectedBean = null;

	InfoDealIF info = null;
	JsonParse jsonParse = null;
	String receive = null;

	String exctCookingFlag = null;
	int useNum = 0;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		exctCookingFlag = getArguments().getString(UI_Constant.FLAG);
		Log.e("CookingInroFragment", "exctCookingFlag = " + exctCookingFlag);
		mBean = (CookMenuBean) getArguments().getSerializable("cookMenuBean");
		menuId = mBean.getMenuId();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_cooking_intro, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initView(view);
		bindData();

		setListener();
	}

	private void initView(View view) {
		tv_cooking_name = (TextView) view.findViewById(R.id.tv_cooking_name);
		tv_cooking_intro = (TextView) view.findViewById(R.id.tv_cooking_intro);
		tv_make_intro = (TextView) view.findViewById(R.id.tv_make_intro);
		sp_people_number = (Spinner) view.findViewById(R.id.sp_people_number);
		btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
		btn_back = (Button) view.findViewById(R.id.btn_back);
	}

	private void bindData() {

		// 设置菜谱名称
		cooking_item_name = getArguments().getString(
				UI_Constant.COOKING_ITEM_NAME);
		cooking_name = getArguments().getString(UI_Constant.COOKING_NAME, null);
		tv_cooking_name.setText(mBean.getMenuName());

		// 获取菜谱介绍
		String cooking_intro = loadCookingIntro();
		tv_cooking_intro.setText(cooking_intro);

		// 获取制作方法
		String cooking_make = loadCookingMake();
		tv_make_intro.setText(cooking_make);

		info = new InfoDealIF();
		jsonParse = new JsonParse();
		try {

			// 根据flag 确定Spinner的绑定数据
			useNumsList = new ArrayList<String>();
			if (exctCookingFlag != null && "cooking".equals(exctCookingFlag)) {
				// 执行烹调流程
				useNum = getArguments().getInt("useNum");
				useNumsList.add(useNum + "");
				receive = info.inquire(MainActivity.interfaceId,
						Type.SELECT_PROCESS4,
						jsonParse.twoConditionsQuery(0, 0, menuId, useNum));
				Log.e("CookingInroFragment", "receive = " + receive);
				if (receive != null) {
					cRecordBeanList = ParseJson
							.parseCookingRecordBean2(receive);
					Log.i("CookingInroFg r132", cRecordBeanList.size()
							+ "");

				}

			} else {

				receive = info.inquire(MainActivity.interfaceId,
						Type.MENU_DETAIL2,
						jsonParse.pagingJsonParse(0, 0, menuId));
				if (receive == null) {
					Toast.makeText(getActivity(), "未获取到适用人数数据！",
							Toast.LENGTH_SHORT).show();
				} else {
					cRecordBeanList = ParseJson.parseCookingRecordBean(receive);
					for (CookingRecordBean bean : cRecordBeanList) {
						String temp = bean.getUsenumber() + "";
						useNumsList.add(temp);
					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		// 为spinner绑定数据
		if (useNumsList != null && useNumsList.size() != 0) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_spinner_item,
					useNumsList);
			sp_people_number.setAdapter(adapter);
		}
	}

	/**
	 * 
	 * @Description: 为控件设置监听
	 * 
	 * @author ChengBin/ChenSir
	 * @version 1.0
	 * @date 2014-11-26 下午3:16:11
	 */
	private void setListener() {
		btn_confirm.setOnClickListener(confirmClickListener);
		btn_back.setOnClickListener(backClickListener);
	}

	OnClickListener confirmClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			MaterialFragment materialFragment = new MaterialFragment();
			Bundle bundle = new Bundle();
			bundle.putString(UI_Constant.FLAG, exctCookingFlag);
			bundle.putString(UI_Constant.COOKING_ITEM_NAME, cooking_item_name);
			bundle.putString(UI_Constant.COOKING_NAME, cooking_name);
			bundle.putString(UI_Constant.COOKING_URI, getArguments().getString(UI_Constant.COOKING_URI, ""));
			
			if (cRecordBeanList != null) {
				bundle.putSerializable("cookMenuBean", mBean);
				bundle.putSerializable("cRecordSelected", cRecordBeanList
						.get(sp_people_number.getSelectedItemPosition()));
				if (exctCookingFlag != null
						&& "cooking".equals(exctCookingFlag)) {
					bundle.putInt("useNum", useNum);
					bundle.putString("cuisinesName", getArguments().getString("cuisinesName", ""));
					bundle.putString(UI_Constant.FLAG, exctCookingFlag);
					bundle.putString("machineShapeCode", getArguments().getString("machineShapeCode", ""));
					Log.e("ttttttt", bundle.getString("machineShapeCode", ""));
					Log.i("CookingInroFg row 194", "exctCookingFlag = "
							+ exctCookingFlag);
				}
				materialFragment.setArguments(bundle);
				getFragmentManager().beginTransaction().addToBackStack(TAG);
				getActivity().getFragmentManager().beginTransaction()
						.replace(R.id.xiaye_fragment, materialFragment)
						.commit();

			} else {
				Toast.makeText(getActivity(), "适用人数为空！无法获取原料制作记录！",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	OnClickListener backClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
//			CookingItemFragment cookingFragment = new CookingItemFragment();
//			Bundle bundle = new Bundle();
//
//			bundle.putString(UI_Constant.COOKING_NAME, cooking_name);
//			bundle.putString(UI_Constant.COOKING_SCHEMA, getArguments()
//					.getString(UI_Constant.COOKING_SCHEMA));
//			if (exctCookingFlag != null && "cooking".equals(exctCookingFlag)) {
//				bundle.putString(UI_Constant.FLAG, exctCookingFlag);
//				bundle.putInt("useNum", useNum);
//				bundle.putString("cuisinesName",
//						getArguments().getString("cuisinesName"));
//				bundle.putString("machineShapeCode",
//						getArguments().getString("machineShapeCode"));
//			}
//
//			Log.e("uri...", getArguments().getString(UI_Constant.COOKING_URI));
//			bundle.putString(UI_Constant.COOKING_URI,
//					getArguments().getString(UI_Constant.COOKING_URI));
//
//			cookingFragment.setArguments(bundle);
//			getActivity().getFragmentManager().beginTransaction()
//					.replace(R.id.xiaye_fragment, cookingFragment).commit();
			getFragmentManager().popBackStackImmediate();
		}
	};

	/***
	 * 
	 * @Description: 加载菜谱介绍
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-26 下午2:49:22
	 * @return
	 */
	public String loadCookingIntro() {
		if (mBean != null) {

			return mBean.getSummarize();
		}
		return null;
	}

	/**
	 * 
	 * @Description: 加载菜谱制作方法
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-26 下午2:58:58
	 * @return
	 */
	public String loadCookingMake() {

		if (mBean != null) {

			return mBean.getIntroduceMakeMethod();
		}
		return null;
	}

}
