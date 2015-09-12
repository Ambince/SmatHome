package com.xiaye.smarthome.main;


import com.xiaye.smarthome.R;
import com.xiaye.smarthome.fragment.SlideMenuFragment;
import com.xiaye.smarthome.slidemenu.SlidingMenu;
import com.xiaye.smarthome.util.MobilePhoneDeviceInfo;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

/**
 * 
 * @ClassName: OnlineOperationActivity
 * @Description: 电磁炉在线操作界面
 * @author ChenSir
 * @version 1.0
 * @date 2015-6-4 下午8:36:45
 * 
 */
public class OnlineOperationActivity  extends FragmentActivity {

	public static Context mainContext;
	public static int machineID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobilePhoneDeviceInfo.initDeviceInfo(this, this);

		// 设置当前窗口无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_onlineoper);

		// 设置侧滑
		setSlideMenu();

		// 设置句柄
		mainContext = getApplicationContext();
		
	}

	/**
	 * 
	 * @description 创建侧滑
	 * 
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-9-22下午8:23:03
	 * @param
	 * @return
	 */
	private void setSlideMenu() {
		SlidingMenu menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// 设置滑动阴影的图像资源
		// menu.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		menu.setBehindWidth(150);
		menu.setBackgroundResource(R.drawable.menu);
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		// 设置触摸屏幕的模式
		menu.attachToActivity(this, SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setMenu(R.layout.activity_left_frame);

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		SlideMenuFragment menuFragment = new SlideMenuFragment();
		Bundle bundle = getIntent().getExtras();
		menuFragment.setArguments(bundle);
		ft.replace(R.id.left_frame, menuFragment);
		ft.commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("PluginMain", "onDestroy");
	}
}
