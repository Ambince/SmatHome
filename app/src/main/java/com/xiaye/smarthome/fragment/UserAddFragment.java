package com.xiaye.smarthome.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.JsonParse;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserAddFragment extends Fragment implements OnClickListener {

	EditText memberName_edt;
	EditText nativePlace_edt;
	EditText callName_edt;
	EditText taboos_edt;
	// EditText mHealthyState_edt;
	EditText mWorkType_edt;
	EditText mSpecifier_edt;
	EditText mhoby_edt;

	Button addMember_btn;

	InfoDealIF info;
	JsonParse jsonParse;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_add, null);
		initView(view);
		info = new InfoDealIF();
		return view;
	}

	public void initView(View view) {
		memberName_edt = (EditText) view.findViewById(R.id.useradd_mname);
		nativePlace_edt = (EditText) view.findViewById(R.id.useradd_native);
		callName_edt = (EditText) view.findViewById(R.id.useradd_callname);
		taboos_edt = (EditText) view.findViewById(R.id.useradd_taboos);
		mWorkType_edt = (EditText) view.findViewById(R.id.useradd_worktype);
		mSpecifier_edt = (EditText) view.findViewById(R.id.useradd_specifier);
		mhoby_edt = (EditText) view.findViewById(R.id.useradd_hoby);

		addMember_btn = (Button) view.findViewById(R.id.useradd_add_btn);
		addMember_btn.setOnClickListener(this);
	}

	public String dataInserted() {
		JSONObject jsonToDB = new JSONObject();
		try {
			jsonToDB.put("memberName", memberName_edt.getText().toString()
					.trim());
			jsonToDB.put("nativePlace", nativePlace_edt.getText().toString()
					.trim());
			jsonToDB.put("callName", callName_edt.getText().toString().trim());
			jsonToDB.put("taboos", taboos_edt.getText().toString().trim());
			jsonToDB.put("workType", mWorkType_edt.getText().toString().trim());
			jsonToDB.put("hoby", mhoby_edt.getText().toString().trim());
			jsonToDB.put("specifier", mSpecifier_edt.getText().toString()
					.trim());
			jsonToDB.put("healthyState", 1);// 健康状况默认为1
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return jsonToDB.toString();

	}

	@Override
	public void onClick(View view) {
		String input = dataInserted();
		int flag = info.control(MainActivity.interfaceId,
				Type.INSERT_MEMBER_INFORMATION, input.getBytes(), null);

		if (flag == 0) {
			Toast.makeText(getActivity(), "添加成功！", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getActivity(), "添加失败！", Toast.LENGTH_LONG).show();
		}

	}

}
