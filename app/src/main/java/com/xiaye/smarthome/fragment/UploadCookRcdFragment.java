package com.xiaye.smarthome.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.jni.info.InfoDealIF.OutPut;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.RecordStepListAdapter;
import com.xiaye.smarthome.bean.RecordOnDvBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;

/**
 * 
 * @ClassName: UploadCookRcdFragment
 * @Description: 上传烹调记录
 * @author ChenSir
 * @version 1.0
 * @date 2014-12-5 下午2:54:27
 * 
 */
public class UploadCookRcdFragment extends Fragment implements OnClickListener {

	ListView listView = null;
	Button confirm_btn = null;
	Button back_btn = null;

	RecordStepListAdapter mRecordStepadapter = null;
	List<RecordOnDvBean> recordList = null;
	List<Integer> selectedList = null;

	InfoDealIF info;
	OutPut output;
	int machineId;

	HashMap<Integer, Boolean> isSelected = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		info = new InfoDealIF();
		output = new OutPut();
		machineId = getArguments().getInt("machineId");

		int length = 4;
		byte[] data = new byte[length + 1];
		data[0] = (byte) (0xff & machineId);
		data[1] = (byte) ((0xff00 & machineId) >> 8);
		data[2] = (byte) ((0xff0000 & machineId) >> 16);
		data[3] = (byte) ((0xff000000 & machineId) >> 24);
		data[length] = 0x04;// 查询选项

		if (info.control(MainActivity.interfaceId, Type.PROTO_FUN_DEVICE_QUERY,
				data, output) == 0) {
			recordList = new ArrayList<RecordOnDvBean>();
			byte[] drecord_rec = output.getOutput();
			// for (int i = 0; i < drecord_rec.length; i++) {
			// System.out.println(drecord_rec[i]);
			// }
			for (int i = 5; i < drecord_rec.length; i++) {
				RecordOnDvBean bean = null;
				int recordNo = (int) drecord_rec[i];
				boolean isUploaded = false;
				int temp = (int) drecord_rec[++i];
				if (temp == 1) {
					isUploaded = true;
				} else {
					isUploaded = false;
				}
				bean = new RecordOnDvBean(recordNo, isUploaded);
				recordList.add(bean);
			}
		} else {
			Toast.makeText(getActivity(), "查询记录失败！", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = View.inflate(getActivity(), R.layout.recordstep, null);

		return view;
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		confirm_btn = (Button) view.findViewById(R.id.recordstep_ok_btn);
		back_btn = (Button) view.findViewById(R.id.recordstep_back_btn);

		confirm_btn.setOnClickListener(this);
		back_btn.setOnClickListener(this);

		listView = (ListView) view.findViewById(R.id.recordstep_listview);
		if (recordList != null && recordList.size() != 0) {
			mRecordStepadapter = new RecordStepListAdapter(getActivity(),
					recordList);
			listView.setAdapter(mRecordStepadapter);
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.recordstep_ok_btn) {
			// 确定
			byte[] data = null;
			int length = 4;
			data = new byte[length + 5];
			data[0] = (byte) (0xff & machineId);
			data[1] = (byte) ((0xff00 & machineId) >> 8);
			data[2] = (byte) ((0xff0000 & machineId) >> 16);
			data[3] = (byte) ((0xff000000 & machineId) >> 24);
			data[length] = Type.COOKING_RECORD_TYPE;// 文件类型

			isSelected = RecordStepListAdapter.isSelected;

			Iterator iter = isSelected.entrySet().iterator();
			while (iter.hasNext()) {

				Map.Entry entry = (Map.Entry) iter.next();
				if ((Boolean) entry.getValue()) {
					int position = (Integer) entry.getKey();
					// 设备记录号（附加数据）
					int dr = recordList.get(position).getRecordNo();
					data[length + 1] = (byte) (0xff & dr);
					data[length + 2] = (byte) ((0xff00 & dr) >> 8);
					data[length + 3] = (byte) ((0xff0000 & dr) >> 16);
					data[length + 4] = (byte) ((0xff000000 & dr) >> 24);
					// 调用接口，上传记录
					if (info.control(MainActivity.interfaceId,
							Type.PROTO_FILE_DEVICEUP, data, null) == 0) {
						Toast.makeText(getActivity(), "正在上传..请等待....",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "操作失败！",
								Toast.LENGTH_LONG).show();
					}
				}
			}

		} else {
			// 返回按钮
			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.xiaye_fragment, new DvinfoListFragment())
					.commit();
		}
	}
}
