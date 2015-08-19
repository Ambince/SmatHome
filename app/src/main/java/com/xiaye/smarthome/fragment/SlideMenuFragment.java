package com.xiaye.smarthome.fragment;

import java.io.File;
import java.io.FileFilter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.main.SmartHomeApplication;
import com.xiaye.smarthome.util.MobilePhoneDeviceInfo;
import com.xiaye.smarthome.util.SDCardUtils;


public class SlideMenuFragment extends Fragment {
	private static final String TAG = "MenuFragment";

	private LinearLayout view;
	public HashMap<String, String> hashMenu = new HashMap<String, String>();
	public OrderMenus orderMenus = new OrderMenus(hashMenu);
	public TreeMap<String, String> menus = new TreeMap<String, String>(orderMenus);
	 
	private String currentPage;
	public static final String PAGE_TAG = "PageTag";
	private int height = 0;
	public static String machineShapeCode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = (LinearLayout) inflater.inflate(R.layout.activity_left_menu, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		machineShapeCode = getActivity().getIntent().getStringExtra(
				"machineShapeCode");

		loadMenuByPages();
		showDefaultPage();
	}

	/**
	 * 
	 * @Description: 将指定文件中的html路径装载到{@link #menus}中
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-12-23 下午6:56:24
	 */
	private void loadMenuByPages() {
		boolean statu = SDCardUtils.getSDcardstatus();

		if (statu) {
			String path = SmartHomeApplication.mHTMLPath + machineShapeCode+"/";
			Log.e(TAG, path);
			File dir = new File(path);
			if (!dir.exists()) {
				Log.e(TAG, " html not exists . ");
			} else {
				HTMLFileFilter html = new HTMLFileFilter();
				File[] files = dir.listFiles(html);
				
				height = (int) (MobilePhoneDeviceInfo.height / files.length);
				
				for (File file : files) {
					Log.e(TAG, "file.getAbsolutePath()" + file.getAbsolutePath());
					hashMenu.put(file.getName(), file.getAbsolutePath());
				}
				
				menus.putAll(hashMenu);
			
				for (Entry<String, String> set : menus.entrySet()) {
					String file_name = set.getKey().split("_")[0];
					LinearLayout layoutMenu = newMenuButton(file_name, set.getValue());  
					view.addView(layoutMenu);
				}
				
			}
		} else {
			Log.e(TAG, " haven't path  or not readable. ");
		}
	}

	/**
	 * 
	 * @Description: 默认显示第一页
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-12-23 下午7:53:34
	 */
	private void showDefaultPage() {
		
		LinearLayout child = (LinearLayout)view.getChildAt(0);
		if (child == null) return;
		View btn = child.getChildAt(0);
		currentPage = btn.getTag().toString();
		
		// 初始化时显示控制界面
		Bundle bundle = new Bundle();
		bundle.putString(PAGE_TAG, currentPage);
		
		CtrlFragment fragment = new CtrlFragment();
		fragment.setArguments(bundle);
		
		getFragmentManager().beginTransaction()
				.replace(R.id.fl_detail, fragment).commit();

	}

	/**
	 * 
	 * @Description: 创建侧滑菜单
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-12-23 下午7:11:48
	 * @param componentInfo
	 * @param i
	 * @return
	 */
	private LinearLayout newMenuButton(String fileName,String path) {
		LinearLayout layoutMenu = new LinearLayout(getActivity());
		layoutMenu.setOrientation(LinearLayout.VERTICAL);
		layoutMenu.setGravity(Gravity.CENTER_VERTICAL);

		
		// 手机竖屏		
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,  height);
		layoutMenu.setLayoutParams(params);

		Button button = new Button(getActivity());
		button.setText(fileName);
		params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		button.setTag(path);
		button.setLayoutParams(params);
		button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_launcher, 0, 0, 0);
		button.setTextAppearance(getActivity(), R.style.MainTabRadioTheme);
		button.setBackgroundResource(R.drawable.fragm_tab_seletor);
		button.setPadding(0, 0, 15, 0);
		button.setOnClickListener(onclick);
		layoutMenu.addView(button);

		return layoutMenu;
	}

	/**
	 * 三个按钮的事件监听处理
	 */
	OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			// 获取被点击的页面TAG
			currentPage = v.getTag().toString();
			Bundle bundle = new Bundle();
			bundle.putString(PAGE_TAG, currentPage);
			
			// 替换Fragment
			CtrlFragment fragment = new CtrlFragment();
			fragment.setArguments(bundle);
			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.fl_detail, fragment).commit();
		}

	};

	
	
	public static final class HTMLFileFilter implements FileFilter {
		public boolean accept(File pathname) {
			if (pathname.getName().endsWith(".html")) {
				return true;
			}
			if (pathname.getName().endsWith(".htm")) {
				return true;
			}
			return false;
		}
	}
	
	class OrderMenus  implements Comparator<String> {  
		
		private HashMap<String,String> base;
		
		public OrderMenus(HashMap<String, String> base) {
		    this.base = base;
		}
		
		public int compare(String a, String b) {
			String aString = a.split("_")[1].split("\\.")[0];
			String bString = b.split("_")[1].split("\\.")[0];
			
			int aInt = Integer.parseInt(aString);
			int bInt = Integer.parseInt(bString);
			if (aInt >= bInt) {
				return 1;
			} 
		   return -1;
		}
	}  

}
