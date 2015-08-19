package com.xiaye.smarthome.fragment;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.constant.UI_Constant;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.main.OnlineOperationActivity;
import com.xiaye.smarthome.main.SmartHomeApplication;
import com.xiaye.smarthome.util.ChangeByteAndInt;
import com.xiaye.smarthome.util.DataUtil;
import com.xiaye.smarthome.util.ParseJson;
import com.xiaye.smarthome.util.PluginBeanService;
import com.xiaye.smarthome.util.PollThread;
import com.xiaye.smarthome.util.ProtocolInfo;
import com.xiaye.smarthome.util.ShareData;
import com.xiaye.smarthome.util.SingleData;

/**
 * 
 * @类描述： 插件解析（与webView交互）
 * @项目名称：SmartHome
 * @包名： com.xiaye.smarthome.fragment
 * @类名称：CtrlFragment
 * @创建人：chengbin
 * @创建时间：2015-6-6下午3:20:42
 * @version v1.0
 * @mail OV5687@163.com
 */
public class CtrlFragment extends Fragment implements ProtocolInfo {

	public static final String TAG = "CtrlFragment";

	// 元素类型，0，底图；1，指示灯图片；2，电器图片，3，数码字图片；4，button；5，文本编辑框；6，直接反应
	public static final int TYPE_BOTPIC = 0;
	public static final int TYPE_LIGHT = 1;
	public static final int TYPE_DEVICE = 2;
	public static final int TYPE_CODE = 3;
	public static final int TYPE_BUTTON = 4;
	public static final int TYPE_TEXT = 5;

	// 所有控件的变量声明和最近的值
	private WebView webView;
	private LinearLayout progressBar;
	private LinearLayout containerView;

	private OprtHandler mHandler;
	private PollThread mPollThread;
	private Handler UIHandler = new Handler();

	private PluginBeanService pService;
	private InfoDealIF info;

	private Timer timer;
	private TimerTask task;
	public static byte[][] tipsData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pService = new PluginBeanService();
		info = new InfoDealIF();
		
		OnlineOperationActivity.machineID = getActivity().getIntent().getIntExtra("machineId", 0);
		
		// JSON测试
		byte[] d = { 0 };
		SingleData data = new SingleData(GETSTATEINFOTYPE, d.length, d, 0);
		ShareData.SetdataFromPlugin(data);

		// 设置设备虚拟地址
		pService.setCertainMachineID(SlideMenuFragment.machineShapeCode,
				OnlineOperationActivity.machineID, getActivity()
						.getApplicationContext());
		// 启动MainAcitivty与this 通信handler
		mHandler = new OprtHandler(getActivity());
		mPollThread = new PollThread(mHandler);
		new Thread(mPollThread).start();

		// 插件状态为打开
		int state = 1;
		pService.setCertainState(SlideMenuFragment.machineShapeCode, state, getActivity()
				.getApplicationContext());

		if (MainActivity.downloadFlag == 1) {
			// 发送执行烹调
			byte[] data1 = { 0x25 };// 数据为空
			ShareData.SetdataToPlugin(ProtocolInfo.SETCOOKREMINDTYPE,
					data1.length, data1, MainActivity.getCurrentTime());
			
			// 发送提示信息
			String tips = (String) SmartHomeApplication.appMap
					.get(UI_Constant.PLUGIN_HINTMSG_KEY);
			System.out.println("tips = " + tips );
			
			// 解析字符串，变为二维数组

			tipsData = ParseJson.parseCookingRcdStepBeanByteArrary(tips);
			System.out.println(" tipsData = " +  new String(tipsData[0]));
			
			// 复原downloadFlag
			MainActivity.downloadFlag = 0;
		}

		Log.i("界面向设备的保活", "OnlineOperationActivity");
		// 界面向设备的保活
		task = new TimerTask() {
			public void run() {
				info.control(MainActivity.interfaceId,
						Type.SYSTM_KEEPLIVE_TODEVICE, ChangeByteAndInt
								.intToBytes(OnlineOperationActivity.machineID),
						null);
			}
		};

		timer = new Timer();
		timer.schedule(task, 0, 5000);
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (containerView == null) {
			containerView = (LinearLayout) inflater.inflate(
					R.layout.fragment_ctrl, container, false);
			progressBar = (LinearLayout) containerView
					.findViewById(R.id.progress);
			webView = (WebView) containerView.findViewById(R.id.webView);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.getSettings().setAllowFileAccess(true);
			webView.getSettings().setDefaultTextEncodingName("UTF-8");
		}
		return containerView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		String pageTag = getArguments().getString(SlideMenuFragment.PAGE_TAG);
		webView.loadUrl("file://" + pageTag);
		webView.setWebViewClient(new WebViewClient());

		webView.addJavascriptInterface(new JavaScriptInterface(), "android");

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);

				if (newProgress == 100) {
					progressBar.setVisibility(View.GONE);
					webView.setVisibility(View.VISIBLE);
				} else {
					progressBar.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	public final class OprtHandler extends Handler {

		@SuppressWarnings("unused")
		private final Activity mActivity;

		public OprtHandler(Activity activity) {
			mActivity = activity;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			// 插件接收到数据进行处理
			if (msg.obj != null && msg.obj instanceof SingleData) {

				SingleData pluginData = (SingleData) msg.obj; // 获取主程序发送的消息
				int type = msg.what; // 获得信息类型
				final byte[] ctrlData = pluginData.data; // 获取数据

				switch (type) {

				case GETCTRLBACKTYPE: // 执行命令回复（按钮点击回复）
					byte answer = ctrlData[0];
					if (answer == 0x26) {
						if (ctrlData[2] == 0x00) {
							Log.i(TAG, "执行命令成功");
						} else {
							Log.i(TAG, "执行命令失败");
						}
					}
					break;
				case GETSTATUSINFTYPE: // 自定义状态码
					/**
					 * 数据格式例：08 02 09 43 02 01 00 09 44 02 00 00 第1位：
					 * （08）固定头，表示为自定义状态码 第2位： （02）有多少块数据 第3位： （09）每块数据固定的头 第4位：
					 * （43）控件对应的命令码 第5位： （02）当前数据块中数据的长度 第6-7位： （01 00）当前数据块的数据
					 * 第8位： （09）转到第3位
					 */
					// TODO

					int i = 0;
					int dataCount = 0;
					byte blockCount = 0;
					byte flag = ctrlData[i++]; // 固定头, 对应08
					byte blockSize = ctrlData[i++]; // 数据块大小，对应02
					byte blockHeader = 0;

					if (flag == 0x8) {

						while (blockCount < blockSize) {
							blockHeader = ctrlData[i++]; // 每块数据固定的头，对应09
							if (blockHeader == 0x9) {

								byte code = ctrlData[i++]; // 获取控件命令码，对应43
								int dataLen = ctrlData[i++]; // 数组长度
								int[] data = new int[dataLen]; // 存放当前数据块的数据
								for (dataCount = 0; dataCount < dataLen; dataCount++) {
									data[dataCount] = ctrlData[i++];
								}

								parseData(code, data, dataLen);

							} else {
								Log.e(TAG, blockHeader + " 不属于自定义状态码");
							}

							blockCount++;
						}

					}
					break;

				case GETDEVICEBRKTYPE: // 获取到设备状态(0为在线， 1为下线)
					Log.e(TAG, "获取到设备状态");
					final byte status = ctrlData[0];
					// TODO
					// 传递状态参数并执行JS状态处理方法
					// 接收JSON数据并使用JS函数显示（对应的的页面怎么找？）
					UIHandler.post(new Runnable() {
						@Override
						public void run() {
							webView.loadUrl("javascript:stat('"
									+ GETDEVICEBRKTYPE + "', '" + status + "')");
						}
					});

					break;

				case SETCOOKREMINDTYPE: // 执行烹调
					Log.e(TAG, "收到执行烹调命令...");
					ShareData.ClearDataFromPlugin(SETCOOKREMINDTYPE);
					// 执行烹调
					byte data[] = { 0x25, 0 };
					SingleData singdata = new SingleData(SETQUERYCTRLTYPE,
							data.length, data, 0);
					// 发送数据至主程序
					ShareData.SetdataFromPlugin(singdata);
					break;

				case GETBASEINFTYPE: // JSON
					Log.e(TAG, "获得JSON格式数据");
					final String jsonResult = new String(ctrlData);

					// TODO
					// 接收JSON数据并使用JS函数显示（对应的的页面怎么找？）
					UIHandler.post(new Runnable() {
						@Override
						public void run() {
							webView.loadUrl("javascript:SetTextByJson('"
									+ jsonResult + "')");
						}
					});

					Log.e(TAG, jsonResult + "");
					break;
				default:
					break;
				}
			}
		}

		/**
		 * @param dataLen
		 *            *
		 * 
		 * @描述: 解析各个数据块的自定义状态码
		 * @方法名: parseData
		 * @param code
		 * @param data
		 * @返回类型 void
		 * @创建人 chengbin
		 * @创建时间 2015-6-6下午3:57:36
		 * @since
		 * @throws
		 */
		private void parseData(final byte code, final int[] data,
				final int dataLen) {
			// TODO
			// 1. 根据接收到的code在当前HTML页面中查找控件：
			// a. 如果控件数量为0，表示当前页面没有对应的控件，终止解释；
			// b. 如果控件数量为1，表示只有一个控件
			// c. 如果控件数量为多个（大于等于2），表示是一串有序控件。
			// data中的数据与各个序列控件对应（如第i个控件和data[i]对应）。

			// 2. 根据控件信息查询出当前控件的类型（是图片？按钮？文字）。

			UIHandler.post(new Runnable() {
				@Override
				public void run() {
					String dataString = Arrays.toString(data);
					webView.loadUrl("javascript:getCode('" + code + "', '"
							+ dataString + "')");
				}
			});

		}

	}

	/**
	 * 
	 * @类描述： 为JS提供的调用接口
	 * @类名称：JavaScriptInterface
	 * @创建人：chengbin
	 * @创建时间：2015-6-2下午7:00:11
	 * @version v1.0
	 * @mail OV5687@163.com
	 */
	final class JavaScriptInterface {

		/**
		 * 
		 * @描述: webView中的按钮点击回调
		 * 
		 * @方法名: click
		 * @param s
		 * @返回类型 void
		 * @创建人 chengbin
		 * @创建时间 2015-6-2下午10:34:13
		 */
		@JavascriptInterface
		public void click(String s) {
			if (s != null) {
				// 减小,4,4365,4732.5,472.5,0,0,1,0x21,1,0x01,0,0,0,0,0,1
				String[] datas = s.split(",");
				byte b = Byte.decode(datas[8]);
				byte data[] = { (byte) b, 0 };
				SingleData singdata = new SingleData(SETQUERYCTRLTYPE,
						data.length, data, 0);
				// 发送数据至主程序
				System.out.println("单击按钮被点击： 请求数据为："+s);
				ShareData.SetdataFromPlugin(singdata);
			}
		}

		@JavascriptInterface
		public int getOpOne(String data, String cmpOperData, String oper) {
			int dataInt = Integer.parseInt(data);
			int cmpInt = Integer.parseInt(cmpOperData);
			int operInt = Integer.parseInt(oper);

			return DataUtil.getResult(dataInt, cmpInt, operInt);
		}

		@JavascriptInterface
		public int getOpText(String data, String cmpOperData, String oper) {
			StringBuffer buffer = new StringBuffer(data);
			buffer.deleteCharAt(0);
			buffer.deleteCharAt(buffer.length() - 1);
			String[] bufferData = buffer.toString().split("\\s*,\\s*");

			int[] dataArray = new int[bufferData.length];
			for (int k = 0; k < bufferData.length; k++) {
				dataArray[k] = Integer.parseInt(bufferData[k]);
			}
			int cmpInt = Integer.parseInt(cmpOperData);
			int operInt = Integer.parseInt(oper);

			return DataUtil.getResult(dataArray, cmpInt, operInt);
		}

		// JSON
		@JavascriptInterface
		public String getOpTwo(String data, String oper, String value) {
			int operInt = Integer.parseInt(oper);
			int valueInt = Integer.parseInt(value);
			String[] datas = (String[]) DataUtil.getSecondResult(
					new Object[] { data }, operInt, 0);
			String result = datas[valueInt - 1].split("\\|")[1];
			return result;
		}

		@JavascriptInterface
		public String getOpTwoText(String data, String oper, String value,
				String code) {
			int operInt = Integer.parseInt(oper);
			int valueInt = Integer.parseInt(value);
			int codeInt = Integer.parseInt(code);
			String[] datas = (String[]) DataUtil.getSecondResult(
					new Object[] { data }, operInt, valueInt);
			
			String result = DataUtil.getStringByCode(datas, (byte) codeInt);
			return result;
		}

		// shine
		@JavascriptInterface
		public int getShine(String data, String oper, String value) {
			int dataInt = Integer.parseInt(data);
			int operInt = Integer.parseInt(oper);
			int valueInt = Integer.valueOf(value, 2);
			return (Integer) DataUtil.getSecondResult(new Object[] { dataInt },
					operInt, valueInt);
		}

		@JavascriptInterface
		public String getTipInfo(String index) {
			
			System.out.println("index="+ index);
			Log.e("INDEX", index + "====");
			if (index == null || tipsData == null)
				return null;
			
			int indexInt = Integer.parseInt(index);
			Log.e("tipsData", tipsData+"...");
			
			
			byte[] tip = tipsData[indexInt];
			String tipString = new String(tip);
			System.out.println(tipString);
			return tipString;
		}

		@JavascriptInterface
		public String getTemp(String value1, String value2) {
			int value1Int = Integer.parseInt(value1);
			int value2Int = Integer.parseInt(value2);
			int a = (value1Int & 0xff) << 8;
			int b = (value2Int & 0xff);
			int result = a | b;
			return result + "";
		}

		// 传入code和控件信息的第10位
		@JavascriptInterface
		public void onlongClick(String code, String flag) {
			byte codeInt = (byte) Integer.parseInt(code.replace("0x", ""), 16);
			byte flagInt = (byte) Integer.parseInt(flag.replace("0x", ""), 16);

			byte data[] = { codeInt, flagInt };
			System.out.println(codeInt + "  " + flagInt);
			// 构建发送给主程序的实体
			SingleData singdata = new SingleData(SETQUERYCTRLTYPE, data.length,
					data, 0);

			// 发送数据给主程序
			ShareData.SetdataFromPlugin(singdata);
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(mPollThread);
		mPollThread = null;
		mHandler = null;

		if (task != null) {
			task.cancel();
			task = null;
		}
		if (timer != null) {
			timer.cancel();
			timer.purge();
			timer = null;
		}
		Log.i(TAG, "onDestroy");
	}
}
