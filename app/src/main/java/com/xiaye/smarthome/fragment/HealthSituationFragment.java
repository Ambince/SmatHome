package com.xiaye.smarthome.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.HealthStatusAdapter;
import com.xiaye.smarthome.bean.Situation;

/**
 * 
 * @ClassName: HealthSituationFragment
 * @Description: 健康情况Fragment
 * @author  Android组-ChengBin
 * @version 1.0
 * @date 2014-11-27 下午6:30:41
 *
 */
public class HealthSituationFragment extends Fragment {
	
	private ListView lv_situation_list;
	private Button btn_back;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_health_situation, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		initView(view);
		
		bindData();
		
		setListener();
	
	}

	/**
	 * 
	 * @Description: 初始化控件
	 *
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-25 下午2:24:23
	 * @param view
	 */
	private void initView(View view) {
		lv_situation_list = (ListView) view.findViewById(R.id.lv_situation_list);
		btn_back = (Button) view.findViewById(R.id.btn_back);
	}

	private void bindData() {
		List<Situation> situations = loadSituationFromDB();
		lv_situation_list.setAdapter(new HealthStatusAdapter(getActivity(), situations));
	}

	private void setListener() {
		btn_back.setOnClickListener(backClickListener);
	}
	

	/************************从数据库加载数据，这里使用的模拟数据***************************/
	private List<Situation> loadSituationFromDB() {
		List<Situation> situations  = new ArrayList<Situation>();
		
		Situation s1 = new Situation("舒张压", "12", "mmHg", "60-90");
		Situation s2 = new Situation("收缩压", "22", "mmHg", "90-140");
		Situation s3 = new Situation("白蛋白", "222", "U/L", "5-50");
		Situation s4 = new Situation("丙氨酸氨基转移酶", "12", "mmHg", "60-90");
		Situation s5 = new Situation("舒张压", "12", "mmHg", "60-90");
		Situation s6 = new Situation("收缩压", "22", "mmHg", "90-140");
		Situation s7 = new Situation("白蛋白", "222", "U/L", "5-50");
		Situation s8 = new Situation("丙氨酸氨基转移酶", "12", "mmHg", "60-90");
		Situation s9 = new Situation("舒张压", "12", "mmHg", "60-90");
		Situation s10 = new Situation("收缩压", "22", "mmHg", "90-140");
		Situation s11 = new Situation("白蛋白", "222", "U/L", "5-50");
		Situation s12 = new Situation("丙氨酸氨基转移酶", "12", "mmHg", "60-90");
		
		situations.add(s1);
		situations.add(s2);
		situations.add(s3);
		situations.add(s4);
		situations.add(s5);
		situations.add(s6);
		situations.add(s7);
		situations.add(s8);
		situations.add(s9);
		situations.add(s10);
		situations.add(s11);
		situations.add(s12);
		
		return situations;
	}
	
	
	/***
	 * 返回事件监听
	 */
	OnClickListener backClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			getActivity().getFragmentManager().beginTransaction().
			replace(R.id.xiaye_fragment, new UserListFragment()).commit();
		}
	};
}
