package com.xiaye.smarthome.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.UserListAdapter;
import com.xiaye.smarthome.bean.UserBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.ParseJson;

/**
 * 
 * @ClassName: UserListFragment
 * @Description: 成员列表
 * @author Android组-ChenSir/ChengBin
 * @version 1.0
 * @date 2014-11-24 下午7:00:46
 * 
 */
public class UserListFragment extends Fragment {

	private ListView lv_user_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_user_list, null);
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
	 * @Description: 初始化所有控件
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-24 下午7:46:22
	 * @param view
	 */
	private void initView(View view) {
		lv_user_list = (ListView) view.findViewById(R.id.lv_user_list);
	}

	/**
	 * 
	 * @Description: 绑定数据到控件
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-24 下午7:46:40
	 */
	private void bindData() {

		List<UserBean> users = loadUsersFromDB();
		
		if (users != null && users.size() != 0) {

			lv_user_list.setAdapter(new UserListAdapter(getActivity(), users));
		}
	}

	/**
	 * 
	 * @Description:(这里用一句话描述这个方法的作用)
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-11-24 下午8:36:34
	 */
	private void setListener() {

	}

	/**
	 * 
	 * @Description:从数据库加载数据
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-12-9 上午9:24:54
	 * @return
	 */
	private List<UserBean> loadUsersFromDB() {
		
		List<UserBean> users = new ArrayList<UserBean>();
		// 查询数据
		InfoDealIF info = new InfoDealIF();
		
		String data = info.inquire(MainActivity.interfaceId,
				Type.SELECT_MEMBER1, null);
		
		try {
			if (data != null) {
				users = ParseJson.parseUserBeanList(data);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}

		return users;
	}

}
