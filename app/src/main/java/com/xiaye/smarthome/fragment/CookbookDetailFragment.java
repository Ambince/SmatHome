package com.xiaye.smarthome.fragment;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.CookingRecordBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;

/**
 * 
 * @ClassName: CookbookDetailFragment
 * @Description: 烹调记录详细信息Fragment
 * @author Android组-HeMingli/ChenSir
 * @version 1.0
 * @date 2014-11-24 10:22:16
 * 
 */
public class CookbookDetailFragment extends Fragment implements
		OnItemSelectedListener, OnClickListener {

	// 关联的菜谱名
	Spinner menuName_spn = null;
	// Spinner数据源
	List<String> MenuNameList = null;

	TextView totalTime_txt = null;
	TextView machineShapeCode_txt = null;
	TextView fileStoragePath_txt = null;

	EditText usenumber_edt = null;
	EditText remark_edt = null;

	Button mNodebtn = null;
	Button edit_btn = null;
	Button back_btn = null;
	Button save_btn = null;

	InfoDealIF info = null;
	JsonParse jsonParse = null;

	String menuId = null;
	String menuName_receive = null;
	String matchMenuName_spn = null;

	CookingRecordBean bean = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			bean = (CookingRecordBean) getArguments().getSerializable(
					"cookingRecordBean");
			menuId = bean.getMenuId();
			info = new InfoDealIF();
			Log.e("CookbookDetailFragment", "menuId = " + menuId);
			if (menuId == null) {
				// 查询所有菜谱名
				menuName_receive = info.inquire(MainActivity.interfaceId,
						Type.SELECT_MENU2, null);
				Toast.makeText(activity.getApplicationContext(),
						"请点击下拉列表选择所要关联的菜谱名", Toast.LENGTH_LONG).show();
			} else {
				// 根据菜谱ID查询菜谱名
				jsonParse = new JsonParse();
				menuName_receive = info.inquire(MainActivity.interfaceId,
						Type.EDITOR_MENU4,
						jsonParse.pagingJsonParse(0, 0, menuId));
				Log.e(menuName_receive, menuName_receive);
			}

			if (menuName_receive != null) {
				MenuNameList = ParseJson.parseMatchMenuName(menuName_receive);
			} else {
				Toast.makeText(activity, "未获取到数据!", Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = View.inflate(getActivity(), R.layout.cookbookdetail, null);
		initView(view);
		bindData();
		return view;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.cookbookdetail_back_btn) {
			// 返回
			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.xiaye_fragment, new CookingRecordFragment())
					.commit();

		} else if (id == R.id.cookbookdetail_save_btn) {
			// 保存
			try {
				String menuId_receive = info.inquire(MainActivity.interfaceId,
						Type.SELECT_MENU1,
						jsonParse.pagingJsonParse(0, 0, matchMenuName_spn));
				String menuId = null;
				menuId = ParseJson.parseMatchMenuId(menuId_receive);

				int useNum = Integer.parseInt(usenumber_edt.getText()
						.toString());
				String remark = remark_edt.getText().toString();

				// 数据库更新
				JSONObject jsonToDB = new JSONObject();
				jsonToDB.put("foodProcessingId", bean.getFoodProcessingId());
				jsonToDB.put("menuId", menuId);
				jsonToDB.put("usenumber", useNum);
				jsonToDB.put("remark", remark);
				int flag = info.control(MainActivity.interfaceId,
						Type.UPDATE_PROCESS, jsonToDB.toString().getBytes(),
						null);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (id == R.id.cookbookdetail_edit_btn) {
			// 编辑
			usenumber_edt.setEnabled(true);
			remark_edt.setEnabled(true);
			Toast.makeText(getActivity(), "编辑后请点击保存！", Toast.LENGTH_LONG)
					.show();
		} else {
			// 查看每个节点信息
			EachNodeInfoFragment eachNodeFragment = new EachNodeInfoFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("foodProcessingId", bean.getFoodProcessingId());
			eachNodeFragment.setArguments(bundle);
			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.xiaye_fragment, eachNodeFragment).commit();
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long arg3) {
		matchMenuName_spn = parent.getItemAtPosition(position).toString();
		if (matchMenuName_spn == null) {
			matchMenuName_spn = "";
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	/**
	 * 
	 * @Description: 初始化控件
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-29 上午11:54:43
	 * @param view
	 */
	public void initView(View view) {
		menuName_spn = (Spinner) view
				.findViewById(R.id.cookbookdetail_menuname_spn);
		totalTime_txt = (TextView) view
				.findViewById(R.id.cookbookdetail_cookrecordtime_txt);

		machineShapeCode_txt = (TextView) view
				.findViewById(R.id.cookbookdetail_machineShapeCode_txt);
		fileStoragePath_txt = (TextView) view
				.findViewById(R.id.cookbookdetail_path_txt);

		usenumber_edt = (EditText) view
				.findViewById(R.id.cookbookdetail_usenum_txt);
		remark_edt = (EditText) view
				.findViewById(R.id.cookbookdetail_remark_edt);

		mNodebtn = (Button) view
				.findViewById(R.id.cookbookdetail_everynodeinfo_btn);
		edit_btn = (Button) view.findViewById(R.id.cookbookdetail_edit_btn);
		back_btn = (Button) view.findViewById(R.id.cookbookdetail_back_btn);
		save_btn = (Button) view.findViewById(R.id.cookbookdetail_save_btn);

	}

	/**
	 * 
	 * @Description:绑定数据
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-29 下午12:12:03
	 */
	public void bindData() {
		if (MenuNameList != null) {

			menuName_spn.setAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item, MenuNameList));

			totalTime_txt.setText(String.valueOf(bean.getTimes()));
			machineShapeCode_txt.setText(bean.getMachineShapeCode());
			fileStoragePath_txt.setText(bean.getDatafilestoragepath());

			usenumber_edt.setText(String.valueOf(bean.getUsenumber()));
			remark_edt.setText(bean.getRemark());

			usenumber_edt.setEnabled(false);
			remark_edt.setEnabled(false);

			edit_btn.setOnClickListener(this);
			save_btn.setOnClickListener(this);
			back_btn.setOnClickListener(this);
		}
	}

}
