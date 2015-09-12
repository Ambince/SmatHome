package com.xiaye.smarthome.main;

import android.app.*;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jni.info.Flag;
import com.jni.info.FlagThread;
import com.jni.info.InfoDealIF;
import com.jni.info.InfoDealIF.CallMsg;
import com.jni.info.InfoDealIF.OutPut;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.ControllerListAdapter;
import com.xiaye.smarthome.adapter.UserListAdapter;
import com.xiaye.smarthome.bean.ControllerBean;
import com.xiaye.smarthome.bean.CookUtensilBean;
import com.xiaye.smarthome.bean.DeviceInformationBean;
import com.xiaye.smarthome.bean.PluginBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.constant.UI_Constant;
import com.xiaye.smarthome.fragment.*;
import com.xiaye.smarthome.util.ChangeByteAndInt;
import com.xiaye.smarthome.util.Connect2ByteArrays;
import com.xiaye.smarthome.util.GetDataFromSharedPreference;
import com.xiaye.smarthome.util.GetSubStringUtil;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.MessageFlagThread;
import com.xiaye.smarthome.util.MobilePhoneDeviceInfo;
import com.xiaye.smarthome.util.ParseJson;
import com.xiaye.smarthome.util.PluginBeanService;
import com.xiaye.smarthome.util.ProtocolInfo;
import com.xiaye.smarthome.util.ShareData;
import com.xiaye.smarthome.util.UpdateUIThread;
import com.xiaye.smarthome.util.ZipUtils;
import com.xiaye.smarthome.view.CategoryView;
import com.xiaye.smarthome.view.CategoryView.OnClickCategoryListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author Android组-ChenSir/ChengBin
 * @version 1.0
 * @ClassName: MainActivity
 * @Description: 主界面
 * @date 2014-11-23 下午2:43:55
 */
public class MainActivity extends Activity {

    // 快捷按钮控件下标
    public int shortCutIndex = 0;

    public static final String TAG = "MainActivity";
    // 界面ID
    public static int interfaceId;
    // MainActivity句柄
    public static Context mainContext;

    /*
     * 下载文件成功后流程区分标志 1 代表 执行烹调过程 0 代表 编制菜谱
     */
    public static int downloadFlag = 0;

    // 动态库相关
    int res; // 接收调C函数的返回值
    static CallMsg callmsg = null;
    public static OutPut output = null;

    InfoDealIF info = null;

    // 业务操作
    JsonParse jsonParse;
    PluginBeanService plugService;
    String initInfoJson = null;

    // 总控列表UI
    List<ControllerBean> controllerList;
    View controllerListlayout;
    ListView controllerListView;
    ControllerListAdapter controllerAdapter;

    UpdateUIThread mUpdateUIThread;

    JNIHandler mJNIHandler = null; // 总控消息处理
    FlagThread flagThread = null;
    Plugin_MainHandler mPlugin_mainHandler = null; // 主程序和插件消息处理
    public static byte[] deviceinfoByteArray = null;
    MessageFlagThread flThread;
    UpdateUIHandler mUIHandler = null; // 更新UI

    // UI声明
    private CategoryView view_category; // 右侧Category
    public static CategoryView header_category; // 头部Category
    private String[] category_data; // 右侧Category数据
    private String category3_name;
    private TextView tv_status; // 状态栏
    private List<String> list;
    private boolean setFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobilePhoneDeviceInfo.initDeviceInfo(this, this);
        setContentView(R.layout.activity_main);
        mainContext = this;
        shortCutIndex = 0;

        controllerList = new ArrayList<ControllerBean>();
        LayoutInflater inflater = getLayoutInflater();
        controllerListlayout = inflater.inflate(
                R.layout.dialog_controller_list,
                (ViewGroup) findViewById(R.id.dialog_list));

        controllerListView = (ListView) controllerListlayout
                .findViewById(R.id.controller_list);

//        setDataToSharePreference();//Test Code

        // 初始化控件
        initViews();
        // 绑定数据
        bindData();
        // 初始化业务
        initService();
        // 监听事件
        setListener();
    }

    /**
     * @Description:初始化业务
     * @author ChenSir
     * @version 1.0
     * @date 2014-11-26 下午2:02:58
     */
    public void initService() {

        callmsg = new CallMsg(new Flag());
        output = new InfoDealIF.OutPut();
        info = new InfoDealIF();// JNI本地方法接口
        jsonParse = new JsonParse();
        plugService = new PluginBeanService();

        // interfaceId = GetDataFromSharedPreference.getInterfaceID(
        // Type.INTERFACEID_KEY, getApplicationContext());
        // 获得字符串
        initInfoJson = getIntent().getStringExtra("initInfoJson");
        Log.i(TAG, initInfoJson);
        // 总控初始化
        res = info.init(initInfoJson);

        // 界面注册
        res = info.registe("wolf", 0, Type.IF_USER_M, callmsg);
        if (res == 0 || res == 1) {
            if (res == 0) {
                tv_status.setText("与总控已连接");
            } else if (res == 1) {
                tv_status.setText("等待远程界面到总控注册");
            }
            // 启动总控消息线程
            mJNIHandler = new JNIHandler();
            flagThread = new FlagThread(CallMsg.fl, mJNIHandler);
            new Thread(flagThread).start();

            // 启动总控、插件通信线程
            mPlugin_mainHandler = new Plugin_MainHandler();
            flThread = new MessageFlagThread(mPlugin_mainHandler);
            new Thread(flThread).start();

            // 更新UI线程
            mUIHandler = new UpdateUIHandler();
            mUpdateUIThread = new UpdateUIThread(mUIHandler);
            new Thread(mUpdateUIThread).start();

        } else {
            Toast.makeText(getApplicationContext(), "界面注册失败！",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @Description: 初始化所有控件
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-23 下午2:45:14
     */
    private void initViews() {
        view_category = (CategoryView) this.findViewById(R.id.view_category);
        header_category = (CategoryView) this
                .findViewById(R.id.header_category);
        // footer_category = (CategoryView) this
        // .findViewById(R.id.footer_category);
        tv_status = (TextView) this.findViewById(R.id.tv_status);
    }

    /**
     * @Description: 绑定数据到控件
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-23 下午2:44:57
     */
    private void bindData() {

        this.category_data = this.getResources().getStringArray(
                R.array.category_0);

        view_category.addCategory(category_data, UI_Constant.CATEGORY_VIEW_1,
                UI_Constant.CATEGORY_VERTICAL);

        replaceFragmentWith(CoverFragment.class);
    }

    /**
     * @Description: 为控件设置监听
     * @author ChengBin/ChenSir
     * @version 1.0
     * @date 2014-11-23 下午2:51:16
     */
    private void setListener() {
        view_category.setOnClickCategoryListener(viewCategoryListener);
        header_category.setOnClickCategoryListener(viewCategoryListener);
    }

    /*********************
     * 监听处理
     *********************/
    OnClickCategoryListener viewCategoryListener = new OnClickCategoryListener() {
        @Override
        public void click(RadioGroup group, int checkedId) {

//            clearBackStack();

            replaceFragmentWith(CoverFragment.class);
            // 点击了那一组菜单（一级、二级、三级、头部、尾部）
            String categoryName = (String) group.getTag();

            // 获取被点击的按钮tag属性值
            RadioButton btn = (RadioButton) group.findViewById(checkedId);
            String btnTag = btn.getTag().toString(); // 获取按钮tag

            if (UI_Constant.CATEGORY_VIEW_1.equals(categoryName)) { // 如果用户点击了一级菜单
                // 如果点击了一级菜单，首先移除第二、三级菜单，然后根据用户选择画出第二级菜单
                view_category.delCategoryByName(UI_Constant.CATEGORY_VIEW_2);
                view_category.delCategoryByName(UI_Constant.CATEGORY_VIEW_3);
                header_category.delCategoryByName(UI_Constant.CATEGORY_HEADER);
                List<String> list = getCategoryById(R.array.category_1, btnTag);
                view_category.addCategory(
                        list.toArray(new String[list.size()]),
                        UI_Constant.CATEGORY_VIEW_2,
                        UI_Constant.CATEGORY_VERTICAL);
            } else if (UI_Constant.CATEGORY_VIEW_2.equals(categoryName)) { // 如果用户点击了二级菜单
                // 如果点击了二级菜单，首先移除第三级菜单，然后根据用户选择画出第三级菜单
                view_category.delCategoryByName(UI_Constant.CATEGORY_VIEW_3);
                header_category.delCategoryByName(UI_Constant.CATEGORY_HEADER);
                List<String> list = getCategoryById(R.array.category_2, btnTag);

                if (list != null && list.size() != 0) {
                    view_category.addCategory(
                            list.toArray(new String[list.size()]),
                            UI_Constant.CATEGORY_VIEW_3,
                            UI_Constant.CATEGORY_VERTICAL);
                } else {
                    CategoryHandleEvents(btnTag);
                }
            } else if (UI_Constant.CATEGORY_VIEW_3.equals(categoryName)) { // 如果用户点击了三级菜单
                // 删除header菜单
                header_category.delCategoryByName(UI_Constant.CATEGORY_HEADER);

                // 判断是否有header
                list = getCategoryById(R.array.header, btnTag);
                if (list != null && list.size() != 0) {
                    header_category.addCategory(
                            list.toArray(new String[list.size()]),
                            UI_Constant.CATEGORY_HEADER,
                            UI_Constant.CATEGORY_HORIZONTAL);
                }

                category3_name = btn.getText().toString();
                CategoryHandleEvents(btnTag);
            } else if (categoryName.startsWith(UI_Constant.CATEGORY_HEADER)) { // 如果用户点击了头部菜单

                String header_name = btn.getText().toString();
                headerHandleEvents(header_name);

            } else if (categoryName.startsWith(UI_Constant.CATEGORY_FOOTER)) { // 如果用户点击了尾部菜单

            } else { // 其他

            }
        }

    };

    /**
     * @param array_id
     * @param btnTag
     * @return
     * @Description: 根据数组ID和tag获取菜单列表
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-23 下午9:34:18
     */
    private List<String> getCategoryById(int array_id, String btnTag) {
        String[] loops = MainActivity.this.getResources().getStringArray(
                array_id);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < loops.length; i++) {
            Uri uri = Uri.parse(loops[i]);
            String[] tags = uri.getScheme().split(UI_Constant.HEADER_TOEKN, 2);
            String tag = null;
            if (tags.length == 1) {
                tag = tags[0];
            } else {
                tag = tags[1];
            }
            if (tag.startsWith(btnTag)) {
                list.add(loops[i]);
            }
        }
        return list;
    }

    /**
     * @Description:header_category控件 按钮监听事件
     * @author ChenSir
     * @version 1.0
     * @date 2014-11-24 下午7:19:01
     */
    public void CategoryHandleEvents(String btnTag) {

        if (btnTag.equals(UI_Constant.USER_LIST)) {
            // 成员列表
            replaceFragmentWith(UserListFragment.class);
        } else if (btnTag.equals(UI_Constant.USER_ADD)) {
            // 添加用户
            replaceFragmentWith(UserAddFragment.class);

        } else if (btnTag.equals(UI_Constant.COOKING_MACHINE)) {
            // 烹调器具
            replaceFragmentWith(CookUtensilFragment.class);
        } else if (btnTag.equals(UI_Constant.COOKING_NATIVE)
                || btnTag.equals(UI_Constant.COOKING_STORE)
                || btnTag.equals(UI_Constant.COOKING_ONLINE)
                || btnTag.equals(UI_Constant.COOKING_USEFUL)
                || btnTag.equals(UI_Constant.COOKING_EDIT)) {
            // 烹调菜谱
            String cooking_name = null;
            if (header_category != null && header_category.getChildCount() != 0) {
                View view = header_category.getChildAt(0);
                RadioGroup group = (RadioGroup) view
                        .findViewById(R.id.container);
                RadioButton radioButton = (RadioButton) group.getChildAt(0);
                radioButton.setChecked(true);
                cooking_name = radioButton.getText().toString();
            }
            int pos = getPosByText(cooking_name);
            String schema = getSchemaByName(category3_name);
            replaceFragmentWith(CookingFragment.class, "", schema, pos + "");
        } else if (btnTag.equals(UI_Constant.USER_CONFIRM)) { // 确认用餐
            if (UserListAdapter.getIsSelected() != null) {
                HashMap<Integer, Boolean> checked = UserListAdapter
                        .getIsSelected();
                int count = 0;
                for (Entry<Integer, Boolean> entry : checked.entrySet()) {
                    if (entry.getValue()) {
                        count++;
                    }
                }
                CookUtensilFragment fragment = new CookUtensilFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(UI_Constant.USER_COUNT, count); // 用餐总人数
                bundle.putString(UI_Constant.FLAG, "cooking"); // 执行烹调flag
                SmartHomeApplication.exctCookingFlag = "cooking";
                Log.i("Main", "count = " + count);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.xiaye_fragment, fragment).commit();

            }
        } else if (btnTag.equals(UI_Constant.COOKING_RECORD)) {
            // 编制菜谱
            replaceFragmentWith(DvinfoListFragment.class);

        } else if (btnTag.equals(UI_Constant.GROUP_SETTINGS)) {
            //群组设置
            replaceFragmentWith(GroupSettingFragment.class);
        } else if (btnTag.equals(UI_Constant.LIGHT_PASSAGEWAY) ||
                btnTag.equals(UI_Constant.LIGHT_MANAGER) ||
                btnTag.equals(UI_Constant.LIGHT_HALL) ||
                btnTag.equals(UI_Constant.LIGHT_KITCHEN) ||
                btnTag.equals(UI_Constant.LIGHT_MEETING) ||
                btnTag.equals(UI_Constant.LIGHT_TEA) ||
                btnTag.equals(UI_Constant.LIGHT_PCROOM) ||
                btnTag.equals(UI_Constant.LIGHT_REST) ||
                btnTag.equals(UI_Constant.LIGHT_PCPASS) ||
                btnTag.equals(UI_Constant.LIGHT_TOILET) ||
                btnTag.equals(UI_Constant.LIGHT_WASHING)) {
            //入门通道等
            replaceFragmentWith(LightControlFragment.class, "", "", "", category3_name);

        } else {

        }
    }

    private void clearBackStack() {
        final FragmentManager fragmentManager = getFragmentManager();
        while (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
    }


    /**
     * @param cooking_name
     * @return
     * @Description:
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-29 下午2:10:42
     */
    private int getPosByText(String cooking_name) {

        int pos = -1;
        // 头部菜单
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            if (line.contains(cooking_name)) {
                pos = i;
            }
        }
        return pos;
    }

    private String getSchemaByName(String name) {
        String[] arrayString = getResources()
                .getStringArray(R.array.category_2);
        for (String line : arrayString) {
            if (line.contains(name)) {
                Uri uri = Uri.parse(line);
                return uri.getScheme();
            }
        }

        return null;
    }

    /***
     * @param header_name 按钮上的文字
     * @Description: header事件处理
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-26 上午9:17:03
     */
    private void headerHandleEvents(String header_name) {
        String flag = "0";
        if ("确认用餐".equals(category3_name)) {
            flag = "1";
            category3_name = "本地";
            list = getCategoryById(R.array.header, "cook3_1");
        }
        if (SmartHomeApplication.exctCookingFlag.startsWith("cooking")) {
            flag = "1";
        }
        int pos = getPosByText(header_name);
        String schema = getSchemaByName(category3_name);
        Log.e("category3_name", category3_name + "..." + header_name + pos);
        replaceFragmentWith(CookingFragment.class, flag, schema, pos + "");
    }

    /**
     * @param fClass
     * @param name
     * @Description: 替换Fragment
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-24 上午9:59:44
     */
    public void replaceFragmentWith(Class<? extends Fragment> fClass,
                                    String... name) {
        try {
            Fragment fragment = fClass.newInstance();
            if (name != null && name.length != 0) {
                Bundle bundle = new Bundle();
                if ("1".equals(name[0])) {
                    bundle.putString(UI_Constant.FLAG, "cooking");
                }
                bundle.putString(UI_Constant.COOKING_SCHEMA, name[1]);
                bundle.putString(UI_Constant.COOKING_NAME, name[2]);
                if (name.length > 3) {
                    //如果长度大于3，则是灯光控制界面（带“位置”参数）
                    bundle.putString(UI_Constant.LIGHT_LOCATION, name[3]);
                }
                fragment.setArguments(bundle);
            }
            android.app.FragmentTransaction ft = getFragmentManager()
                    .beginTransaction();
            ft.replace(R.id.xiaye_fragment, fragment);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     * @Description:获取当前系统时间戳
     * @author ChenSir
     * @version 1.0
     * @date 2014-11-27 下午3:59:51
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /*
     * 测试用
     */
    public void setDataToSharePreference() {
        PluginBean bean = new PluginBean();
        bean.setMachineId(2);
        bean.setFileName("2234891058638.zip");
        bean.setMatchMcSCode("2234891058638");
        GetDataFromSharedPreference.setPluginBean("2234891058638", bean,
                getApplicationContext());
    }

    /********************* Handler异步消息处理 ***********************************/
    /******************
     * 总控消息处理、插件及主程序消息处理
     *****************************/

    // 器具列表Adapter刷新
    DeviceInformationBean deviceinfobean = null;

    Context plugContext = null;

    /**
     * @Description:当有器具上线时刷新器具列表
     * @author ChenSir
     * @version 1.0
     * @date 2014-11-28 下午9:19:25
     */

    public void refreshMachineList() {

        if (CookUtensilFragment.mCookUtensilBeanList != null) {
            CookUtensilFragment.mCookUtensilBeanList.clear();
            String recieve_Machine = info.inquire(MainActivity.interfaceId,
                    Type.SELECT_MACHINE1,
                    jsonParse.pagingJsonParse(0, 0, Type.SORT_HOMEAPP));
            try {
                CookUtensilFragment.mCookUtensilBeanList.addAll(ParseJson
                        .parseCookUtensilList(recieve_Machine));
            } catch (Exception e) {
                e.printStackTrace();
            }
            CookUtensilFragment.CookUtensilAdapter.notifyDataSetChanged();
        }
    }

    public class JNIHandler extends Handler implements Type {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 如果下层传来消息
            if (msg.what == 1) {
                // 获取msg.obj即下层传来的消息类型
                int temp = (Integer) msg.obj;
                switch (temp) {

                    case SYSTM_FUN_REPLY_REGISTER:// 设备注册

                    case SYSTM_FUN_REGISTER_LOAD:// 设备注册，正在下载界面插件

                    case SYSTM_FUN_REGISTER_NOLOAD:// 设备注册，无法下载界面插件
                        // 获取家电上线时返回的设备信息数组
                        deviceinfoByteArray = callmsg.getPara();
                        try {
                            deviceinfobean = ParseJson
                                    .parseDeviceInformation(new String(callmsg
                                            .getPara()));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        String registerUpname = deviceinfobean.getMachineName();
                        int ID = deviceinfobean.getDevice_Vaddrs();
                        // 刷新器具列表
                        refreshMachineList();
                        showRegisterActivity(ID);

                        if (temp == SYSTM_FUN_REPLY_REGISTER) {
                            Toast.makeText(getApplicationContext(),
                                    registerUpname + "注册成功！", Toast.LENGTH_LONG)
                                    .show();
                        } else if (temp == SYSTM_FUN_REGISTER_LOAD) {
                            Toast.makeText(getApplicationContext(),
                                    registerUpname + "注册成功并正在下载插件！",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mainContext, "设备注册成功！但无法下载！",
                                    Toast.LENGTH_LONG).show();
                        }

                        break;

                    case SYSTM_FUN_REGISTER_GROUP:
                        //通知新建群组的群组id
                        int groupId = ChangeByteAndInt.bytesToInt(callmsg.getPara(), 0);
                        showGroupRegisterFragment(groupId);
                        break;

                    case SYSTM_FUN_DEVICE_ONLINE:// 家电上线
                        // 获取家电上线时返回的设备信息数组
                        deviceinfoByteArray = callmsg.getPara();
                        Log.i(TAG, "家电信息：" + new String(deviceinfoByteArray));

                    case SYSTM_FUN_DEVICE_OFFLINE:// 家电下线
                        try {
                            deviceinfobean = ParseJson
                                    .parseDeviceInformation(new String(callmsg
                                            .getPara()));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        // 用于获得设备名称
                        registerUpname = deviceinfobean.getMachineName();


                        int state;
                        if (temp == SYSTM_FUN_DEVICE_ONLINE) {
                            Toast.makeText(getApplicationContext(),
                                    registerUpname + "上线", Toast.LENGTH_LONG)
                                    .show();
                            state = 0;

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    registerUpname + "下线", Toast.LENGTH_LONG)
                                    .show();
                            state = 1;
                        }
                        // 设置MachineBeanList
                        refreshMachineList();

                        /**
                         * 设备上/下线时，将插件管理类中对应的插件状态设为1/0
                         */
                        String device_ID = deviceinfobean.getDevice_id();
                        plugService.setCertainState(device_ID, state, mainContext);

                        /**
                         * 设备上/下线后且当插件开启时发送“断开/连接”状态消息
                         */
                        // TODO:向“在线操作Activity”发送“断开/连接”状态消息
                        byte[] data = {(byte) state};
                        ShareData.SetdataToPlugin(ProtocolInfo.GETDEVICEBRKTYPE,
                                data.length, data, getCurrentTime());
                        break;

                    case IF_MSG:
                        String str = new String(callmsg.getPara());
                        String strs = "收到消息:" + str;
                        System.out.println("strs");
                        Toast.makeText(mainContext, strs, Toast.LENGTH_SHORT)
                                .show();
                        break;

                    case SYSTM_DEVICE_MSG:
                    /*
                     * 设备状态变化报告（通用）
					 */
                        byte[] cReceive = callmsg.getPara();
                        byte[] ctrlData = new byte[cReceive.length - 4];
                        for (int i = 0; i < ctrlData.length; i++) {
                            ctrlData[i] = cReceive[i + 4];
                        }
                        ShareData.SetdataToPlugin(ProtocolInfo.GETSTATUSINFTYPE,
                                ctrlData.length, ctrlData, getCurrentTime());
                        break;

                    // 界面插件程序下载成功
                    case SYSTM_INTERFACE_LOAD_OK:
                    case SYSTM_INTERFACE_UPDATE_OK:
                        // TODO: 插件程序下载成功
                        final String cbMsg = new String(callmsg.getPara());
                        final String zipPath = ParseJson.getApkURL(cbMsg);

                        if (zipPath.endsWith(".zip") || zipPath.endsWith(".ZIP")) {
                            Toast.makeText(MainActivity.this, "文件下载成功",
                                    Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "zipPath == " + zipPath);

                            File file = new File(zipPath);
                            String fileName = file.getName();
                            if (fileName.equals(zipPath))
                                return;
                            String mcSCode = fileName.split("\\.")[0];

                            PluginBean plugin = new PluginBean();
                            plugin.setFileName(fileName);
                            plugin.setMatchMcSCode(mcSCode);

                            GetDataFromSharedPreference.setPluginBean(mcSCode,
                                    plugin, mainContext);

                            try {
                                ZipUtils.unZipFiles(zipPath,
                                        SmartHomeApplication.mHTMLPath + mcSCode
                                                + "/");
                                Toast.makeText(MainActivity.this, "文件解压安装成功!",
                                        Toast.LENGTH_SHORT).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e(TAG, "ZIP文件解压失败");
                            }
                        } else {
                            Log.e(TAG, "ZIP路径错误：  " + zipPath);
                        }
                        break;

                    case SYSTM_INTERFACE_LOAD_FAIL:
                        Log.e(TAG, "界面插件程序下载失败!");
                        Toast.makeText(mainContext, "界面插件程序下载失败！",
                                Toast.LENGTH_LONG).show();
                        break;

                    case SYSTM_INTERFACE_UPDATE_FAIL:// 界面插件程序更新失败
                        Log.e("Main Handler", "界面插件程序更新失败!");
                        Toast.makeText(mainContext, "界面插件程序更新失败！",
                                Toast.LENGTH_LONG).show();
                        break;

                    // 手机总控文件同步成功
                    case SYN_C_SYNOK:
                        final String path = new String(callmsg.getPara());
                        Log.i(TAG, "手机总控文件同步成功=====" + path);
                        if (path.endsWith(".zip")) {
                            Toast.makeText(MainActivity.this, "手机总控文件同步成功",
                                    Toast.LENGTH_SHORT).show();
                            File file = new File(path);
                            String fileName = file.getName();
                            Log.i(TAG, "fileName == " + fileName);

                            String mcSCode = fileName.split("\\.")[0];
                            PluginBean plugin = new PluginBean();
                            plugin.setFileName(fileName);
                            plugin.setMatchMcSCode(mcSCode);
                            /**
                             * 以序列号作为Key保存PluginBean在SharePreference
                             */
                            GetDataFromSharedPreference.setPluginBean(fileName,
                                    plugin, mainContext);

                            try {
                                ZipUtils.unZipFiles(path,
                                        SmartHomeApplication.mHTMLPath + mcSCode
                                                + "/");
                                Toast.makeText(MainActivity.this, "文件解压安装成功!",
                                        Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e(TAG, "ZIP文件解压失败");
                            }
                        }
                        break;

                    // 下载文件成功
                    case PROTO_FILE_TODEVICE_OK:
                        Log.i(TAG, "下载文件成功!");
                        if (downloadFlag == 1) {
                            System.out.println("下载文件成功,执行菜谱流程！");
                            String para = new String(callmsg.getPara());
                            String machineIdString = para.substring(1, 5);
                            int machineId = ChangeByteAndInt.bytesToInt(
                                    machineIdString.getBytes(), 0);
                            String machineShapeCode = para.substring(9);

                            Toast.makeText(getApplicationContext(), "文件下载成功！",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("machineShapeCode", machineShapeCode);
                            intent.putExtra("machineId", machineId);
                            intent.setClass(MainActivity.this,
                                    OnlineOperationActivity.class);
                            startActivity(intent);

                        } else {
                            // 编制菜谱
                        }

                        break;

                    case PROTO_FILE_TODEVICE_FAIL:

                        Log.e("Main Handler", "下载文件到设备失败！");
                        Toast.makeText(mainContext, "下载文件到设备失败！", Toast.LENGTH_LONG)
                                .show();
                        break;

                    // 请求设备上传文件到总控失败
                    case PROTO_FILE_DEVICEUP_FAIL:
                        Log.e("Main Handler", "请求设备上传文件到总控失败");
                        Toast.makeText(mainContext, "上传失败！", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case PROTO_FILE_DEVICEUP_OK:// 请求设备上传文件到总控成功
                        Toast.makeText(mainContext, "上传文件到总控成功!", Toast.LENGTH_LONG)
                                .show();
                        if (callmsg.getPara() != null) {
                            byte fileFlag = callmsg.getPara()[0];
                            if (fileFlag == 0x30) {
                                // 0x30烹调记录文件标志
                                byte[] upFid = GetSubStringUtil.getUpfId(callmsg
                                        .getPara());
                                int UpfpID = ChangeByteAndInt.bytesToInt(upFid, 0);
                                showCookMenuFragment(UpfpID);
                            }
                        }
                        break;

                    case IF_FULL_USERID: // 注册ID 数已满

                        Toast.makeText(mainContext, "总控允许连接数已达上限!无法继续连接！",
                                Toast.LENGTH_LONG).show();
                        break;

                    case DRIVER_DRIVER_REPLY_SETTING:
                    /*
                     * 设备状态变化报告（自定义）
					 */
                        break;

                    case SWEBKEEPLIVEOUTTIME:
                        // web连接断开，保活超时
                        tv_status.setText("web连接断开，保活超时！");
                        Toast.makeText(mainContext, "web连接断开，保活超时！",
                                Toast.LENGTH_LONG).show();
                        break;

                    case IF_FAILCONNECT:
                        tv_status.setText("与总控断开连接！");
                        Toast.makeText(mainContext, "与总控断开连接！", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case IF_CONNECTAGAIN:
                        tv_status.setText("与总控重新连接成功!");
                        byte[] result2 = callmsg.getPara();
                        int ifId = ChangeByteAndInt.bytesToInt(result2, 0);
                        interfaceId = ifId;
                        Log.i(TAG, "interfaceID == " + interfaceId);
                        break;

                    // 登录服务器成功
                    case LOGINSERVER_OK:
                        Log.i(TAG, "登录服务器成功");
                        // 前四个byte为界面ID 后面数据为 json 总控列表
                        // 选择总控后，调用u_loginTerml本地方法
                        byte[] result = callmsg.getPara();
                        int length = result.length;
                        // 前四位为ID
                        byte[] idArray = new byte[4];
                        byte[] controllerArray = new byte[length - 4];
                        if (length != 0 && length > 4) {
                            for (int i = 0, j = 0, k = 0; i < length; i++) {
                                if (i < 4) {
                                    idArray[j] = result[i];
                                    j++;
                                } else {
                                    controllerArray[k] = result[i];
                                    k++;
                                }
                            }
                            interfaceId = ChangeByteAndInt.bytesToInt(idArray, 0);
                            String controllerListJson = new String(controllerArray);
                            try {
                                // TODO
                                controllerList = ParseJson
                                        .parseContrloControllerBeans(controllerListJson);
                                controllerAdapter = new ControllerListAdapter(
                                        controllerList, mainContext);
                                if (controllerList.size() != 0) {
                                    AlertDialog.Builder choiceBuiler = new Builder(
                                            mainContext);
                                    choiceBuiler.setTitle("请点击选择中控");
                                    choiceBuiler.setView(controllerListlayout);
                                    final AlertDialog dialog1 = choiceBuiler.show();

                                    controllerListView
                                            .setAdapter(controllerAdapter);

                                    controllerListView
                                            .setOnItemClickListener(new OnItemClickListener() {

                                                @Override
                                                public void onItemClick(
                                                        AdapterView<?> parent,
                                                        View view, int position,
                                                        long id) {
                                                    ControllerBean controllerBean = controllerList
                                                            .get(position);
                                                    int id1 = controllerBean
                                                            .getControllerID();
                                                    String name = controllerBean
                                                            .getControllerName();
                                                    if ((info.u_loginTerml(id1,
                                                            name)) < 0) {
                                                        Toast.makeText(mainContext,
                                                                "登录中控出错！",
                                                                Toast.LENGTH_LONG)
                                                                .show();
                                                    } else {
                                                        Toast.makeText(mainContext,
                                                                "登录中控成功！",
                                                                Toast.LENGTH_LONG)
                                                                .show();
                                                        dialog1.dismiss();
                                                    }

                                                }

                                            });
                                } else {
                                    Toast.makeText(mainContext, "中控列表为空",
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(mainContext, "解析中控列表出错！",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        Toast.makeText(mainContext, "登录服务器成功", Toast.LENGTH_LONG)
                                .show();
                        tv_status.setText("登录服务器成功");

                        break;

                    // 局域网下成功搜索到总控
                    case SERACH_OK:
                        Log.i(TAG, "局域网下成功搜索到总控");
                        byte[] back = callmsg.getPara();
                        // 总控ID
                        byte[] contrId = new byte[4];
                        // 总控别名
                        byte[] conName = new byte[back.length - 4];
                        for (int i = 0, j = 0; i < back.length; i++) {
                            if (i < 4) {
                                contrId[i] = back[i];
                            } else {
                                conName[j] = back[i];
                                j++;
                            }
                        }
                        int id = ChangeByteAndInt.bytesToInt(contrId, 0);
                        String name = new String(conName);
                        ControllerBean bean = new ControllerBean();
                        bean.setControllerID(id);
                        bean.setControllerName(name);
                        // 局域网下搜索到总控都是在线的
                        bean.setControllerState(1);
                        if (controllerList.size() == 0) {
                            controllerList.add(bean);

                            final AlertDialog.Builder builder11 = new Builder(
                                    mainContext);
                            builder11.setTitle("请点击选择总控");
                            builder11.setView(controllerListlayout);
                            final AlertDialog dialog = builder11.show();
                            controllerAdapter = new ControllerListAdapter(
                                    controllerList, mainContext);
                            controllerListView.setAdapter(controllerAdapter);
                            controllerListView
                                    .setOnItemClickListener(new OnItemClickListener() {

                                        @Override
                                        public void onItemClick(
                                                AdapterView<?> parent, View view,
                                                int position, long id) {

                                            ControllerBean controllerBean = controllerList
                                                    .get(position);
                                            int id1 = controllerBean
                                                    .getControllerID();
                                            String name = controllerBean
                                                    .getControllerName();
                                            if ((info.u_loginTerml(id1, name)) < 0) {
                                                Toast.makeText(mainContext,
                                                        "登录中控出错！",
                                                        Toast.LENGTH_LONG).show();
                                            } else {

                                                Toast.makeText(mainContext,
                                                        "登录中控成功！",
                                                        Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });

                        } else {
                            for (int i = 0; i < controllerList.size(); i++) {
                                if (controllerList.get(i).getControllerID() == id) {
                                    continue;
                                } else {
                                    controllerList.add(bean);
                                    controllerAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        break;

                    case LOGINSERVER_FAILED:

                        tv_status.setText("登录服务器失败！");
                        Toast.makeText(mainContext, "登录服务器失败！", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case LOGINSERVER_PWDERR:
                        tv_status.setText("登录服务器失败，密码错误");
                        Toast.makeText(mainContext, "登录服务器失败，密码错误",
                                Toast.LENGTH_LONG).show();

                        break;

                    case LOGINSERVER_TIMEOUT:
                        tv_status.setText("登录服务器失败，超时");
                        break;

                    case LOGINSERVER_FULL:

                        tv_status.setText("登录服务器失败，连接已满");
                        Toast.makeText(mainContext, "登录服务器失败，连接已满",
                                Toast.LENGTH_LONG).show();
                        break;

                    case LOGINSERVER_REPEAT:
                        tv_status.setText("登录服务器失败，重复登录");
                        Toast.makeText(mainContext, "登录服务器失败，重复登录",
                                Toast.LENGTH_LONG).show();
                        break;

                    case SERVER_BREAK:
                        tv_status.setText("与服务器断开连接");
                        Toast.makeText(mainContext, "与服务器断开连接", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case LOGINTERML_OK:
                        tv_status.setText("登录总控成功");
                        Toast.makeText(mainContext, "登录总控成功", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case LOGINTERML_FAILED:
                        tv_status.setText("登录总控失败");
                        Toast.makeText(mainContext, "登录总控失败", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case LOGINTERML_PWDERR:
                        tv_status.setText("登录总控失败，密码错误");
                        Toast.makeText(mainContext, "登录总控失败，密码错误",
                                Toast.LENGTH_LONG).show();
                        break;

                    case LOGINTERML_TIMEOUT:
                        tv_status.setText("登录总控失败，超时");
                        Toast.makeText(mainContext, "登录总控失败，超时", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case LOGINTERML_FULL:
                        tv_status.setText("登录总控失败，连接已满");
                        Toast.makeText(mainContext, "登录总控失败，连接已满",
                                Toast.LENGTH_LONG).show();
                        break;

                    case LOGINTERML_REPEAT:
                        tv_status.setText("登录总控失败，重复登录");
                        Toast.makeText(mainContext, "登录总控失败，重复登录",
                                Toast.LENGTH_LONG).show();
                        break;

                    case TERML_BREAK:
                        tv_status.setText("与总控断开连接");
                        Toast.makeText(mainContext, "与总控断开连接", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case CLIENT_OFFLINE:

                        Toast.makeText(mainContext, "客户端下线", Toast.LENGTH_LONG)
                                .show();
                        break;

                    case CLIENT_ONLINE:

                        Toast.makeText(mainContext, "客户端上线", Toast.LENGTH_LONG)
                                .show();
                        break;

                    default:
                        break;
                }
            }
        }
    }

    // MainActivity接收OnlineOperationActivity发送的数据并处理
    public class Plugin_MainHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;
            if (what == 1) {
                /*
                 * 查询和控制信息
				 */
                byte[] data1 = (byte[]) msg.obj;
                // 获取插件对应的设备虚拟地址
                int machineId = OnlineOperationActivity.machineID;
                byte[] mIdData = null;
                if (machineId > 0) {
                    mIdData = ChangeByteAndInt.intToBytes(machineId);
                    byte[] data3 = Connect2ByteArrays.conn2ByteArrays(mIdData,
                            data1);
                    if (info.control(interfaceId,
                            Type.DRIVER_DRIVER_MODIFY_SETTING, data3, output) != -1) {
                        byte[] ctrlBack = output.getOutput();// 返回操作的响应
                        ShareData.SetClearFlag(0);
                        ShareData.SetdataToPlugin(ProtocolInfo.GETCTRLBACKTYPE,
                                ctrlBack.length, ctrlBack, getCurrentTime());
                    }
                }

            } else if (what == 5) {

                if (deviceinfoByteArray != null) {
                    String json = new String(deviceinfoByteArray);
                    try {
                        byte[] dvInfo = parseDeviceInformation(json).getBytes();
                        ShareData.SetClearFlag(0);
                        ShareData.SetdataToPlugin(ProtocolInfo.GETBASEINFTYPE,
                                dvInfo.length, dvInfo, getCurrentTime());
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),
                                "解析设备信息出错", Toast.LENGTH_LONG)
                                .show();
                        e.printStackTrace();
                    }

                } else {
                    ShareData.SetClearFlag(0);
                }
            } else {

            }
        }

        private String parseDeviceInformation(String json) throws JSONException {

            JSONObject jsonObject = new JSONObject(json);

            String a = jsonObject.getInt("device_addrs") + "";// 设备虚拟地址
            String b = jsonObject.getString("device_id");// 条形码
            String c = jsonObject.getString("device_name");
            String d = jsonObject.getString("manufact");
            String e = jsonObject.getString("date");
            String f = jsonObject.getString("software");
            String g = jsonObject.getInt("registerWay") + "";

			/*
             * 根据协议 设备名称:0x30 生产日期:0x31 设备虚拟地址:0x32 设备序列号:0x33 程序版本:0x34
			 * 生产厂家:0x35 注册方式:0x36
			 */
            JSONObject resultJson = new JSONObject();
            resultJson.put(0x30 + "", c);
            resultJson.put(0x31 + "", e);
            resultJson.put(0x32 + "", a);
            resultJson.put(0x33 + "", b);
            resultJson.put(0x34 + "", f);
            resultJson.put(0x35 + "", d);
            resultJson.put(0x36 + "", g);

            String result = resultJson.toString();
            return result;

        }

    }

    public class UpdateUIHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int flag = msg.what;

            switch (flag) {
                case 1:
                    // 插件快捷键更新
                    final CookUtensilBean bean = (CookUtensilBean) msg.obj;
                    String name = bean.getMachineName();
                    int id = bean.getMachineId();

                    LinearLayout lin = null;
                    if (shortCutIndex < 5) {
                        // 如果小于5，就是第一层按钮
                        lin = (LinearLayout) findViewById(R.id.shortcut_lay1);
                    } else {
                        lin = (LinearLayout) findViewById(R.id.shortcut_lay2);
                    }

                    Button btn;

                    for (int m = 0; m < shortCutIndex || shortCutIndex == 0; m++) {
                        setFlag = true;
                        btn = (Button) lin.getChildAt(m);
                        Object tagObject = btn.getTag();
                        int tagId = Integer.MIN_VALUE;

                        if (tagObject == null) {
                            setFlag = true;
                            break;
                        } else { // 找到被绑定的按钮
                            tagId = Integer.parseInt(tagObject + "");
                            if (tagId == id) { // 如果与当前ID相等的按钮存在
                                if (!btn.getText().equals(name)) { // 名称不同
                                    btn.setText(name);
                                }
                                setFlag = false;
                                break;
                            } else {

                            }
                        }
                    }

                    if (setFlag) {
                        String machineShapeCode = bean.getMachineShapeCode();
                        PluginBeanService pService = new PluginBeanService();
                        String pckgName = pService.getPackageName(machineShapeCode,
                                getApplicationContext());
                        if (pckgName != null) {
                            btn = (Button) lin.getChildAt(shortCutIndex);
                            btn.setText(bean.getMachineName());
                            btn.setTag(id);
                            btn.setTextSize(12);
                            shortCutIndex++;
                            SmartHomeApplication.mUpdateUIFlag = 0;
                            btn.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    Intent intentPlugin = new Intent();
                                    intentPlugin.putExtra("machineShapeCode",
                                            bean.getMachineShapeCode());
                                    intentPlugin.putExtra("machineId",
                                            bean.getMachineId());
                                    intentPlugin.setClass(mainContext,
                                            OnlineOperationActivity.class);
                                    startActivity(intentPlugin);
                                }
                            });
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @param id
     * @Description:设备注册时弹出通知
     * @author ChenSir
     * @version 1.0
     * @date 2014-11-28 下午7:34:19
     */
    public void showRegisterActivity(int id) {
        NotificationManager manager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建一个Notification
        Notification notification = new Notification();
        // 设置显示在手机最上边的状态栏的图标
        notification.icon = R.drawable.ic_launcher;
        // 当当前的notification被放到状态栏上的时候，提示内容
        notification.tickerText = "设备注册成功！点击补充设备信息！！！";
        // 添加声音提示
        notification.defaults = Notification.DEFAULT_SOUND;
        // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
        notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
        // 用户点击通知后删除该通知
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        DeviceInformationBean registerInquirybean = null;
        try {
            // 添加jsonParse.pagingJsonParse()
            registerInquirybean = ParseJson.parseRegisterInquirybean(info
                    .inquire(
                            interfaceId,
                            Type.SELECT_MACHINE_TYPE2,
                            jsonParse.pagingJsonParse(0, 0,
                                    deviceinfobean.getDevice_Vaddrs())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        bundle.putInt("machineId", id);
        bundle.putInt("id", 1);
        bundle.putSerializable("sendRegisterInquirybean", registerInquirybean);
        intent.putExtras(bundle);
        intent.setClass(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id + 2,
                intent, PendingIntent.FLAG_ONE_SHOT);
        // 点击状态栏的图标出现的提示信息设置
        notification.setLatestEventInfo(this, "设备注册", "设备条形码"
                + registerInquirybean.getDevice_id() + "设备名称"
                + registerInquirybean.getMachineName(), pendingIntent);

        manager.notify(id, notification);

    }

    /**
     * @param upfpID
     * @Description:烹调记录上传成功，提示用户补充信息
     * @author ChenSir
     * @version 1.0
     * @date 2014-12-4 上午11:25:31
     */
    public void showCookMenuFragment(int upfpID) {

        NotificationManager manager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建一个Notification
        Notification notification = new Notification();
        // 设置显示在手机最上边的状态栏的图标
        notification.icon = R.drawable.ic_launcher;
        // 当当前的notification被放到状态栏上的时候，提示内容
        notification.tickerText = "烹调记录更新成功！点击进入编辑！";

        /***
         * notification.contentIntent:一个PendingIntent对象，当用户点击了状态栏上的图标时，
         * 该Intent会被触发 notification.contentView:我们可以不在状态栏放图标而是放一个view
         * notification.deleteIntent 当当前notification被移除时执行的intent
         * notification.vibrate 当手机震动时，震动周期设置
         */
        // 添加声音提示
        notification.defaults = Notification.DEFAULT_SOUND;
        // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
        notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
        // 用户点击通知后删除该通知
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        Intent intent = new Intent();
        // 通过烹调记录ID查询菜谱ID，将其传递到下一个Activity
        intent.putExtra("foodProcessingId", upfpID);

        intent.putExtra("id", 2);
        intent.setClass(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);
        // 点击状态栏的图标出现的提示信息设置
        notification.setLatestEventInfo(this, "请完善菜谱相关信息", "点击进行完善",
                pendingIntent);
        manager.notify(0, notification);
    }

    /**
     * 群组注册通知
     *
     * @param groupId
     */
    public void showGroupRegisterFragment(int groupId) {

        NotificationManager manager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建一个Notification
        Notification notification = new Notification();
        // 设置显示在手机最上边的状态栏的图标
        notification.icon = R.drawable.ic_launcher;
        // 当当前的notification被放到状态栏上的时候，提示内容
        notification.tickerText = "烹调记录更新成功！点击进入编辑！";

        /***
         * notification.contentIntent:一个PendingIntent对象，当用户点击了状态栏上的图标时，
         * 该Intent会被触发 notification.contentView:我们可以不在状态栏放图标而是放一个view
         * notification.deleteIntent 当当前notification被移除时执行的intent
         * notification.vibrate 当手机震动时，震动周期设置
         */
        // 添加声音提示
        notification.defaults = Notification.DEFAULT_SOUND;
        // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
        notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
        // 用户点击通知后删除该通知
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        Intent intent = new Intent();
        // 群组ID
        intent.putExtra("groupId", groupId);

        intent.putExtra("id", 3);
        intent.setClass(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);
        // 点击状态栏的图标出现的提示信息设置
        notification.setLatestEventInfo(this, "请完善菜谱相关信息", "点击进行完善",
                pendingIntent);
        manager.notify(0, notification);
    }


    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        int id = intent.getIntExtra("id", 0);
        if (id == 1) {
            // 设备注册通知
            Log.i("Main", "收到设备注册通知");
            Bundle bundle = new Bundle();
            bundle.putInt("machineId", intent.getIntExtra("machineId", 0));
            bundle.putSerializable("sendRegisterInquirybean",
                    intent.getSerializableExtra("sendRegisterInquirybean"));

            DeviceRegisterFragment dvRegisterFragment = new DeviceRegisterFragment();
            dvRegisterFragment.setArguments(bundle);
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.xiaye_fragment, dvRegisterFragment).commit();

        } else if (id == 2) {
            // 烹调记录上传成功通知
            Log.i("Main", "烹调记录上传成功通知");
            CookMenuEditFragment cEditFragment = new CookMenuEditFragment();
            Bundle bundle = new Bundle();
            int upfpID = intent.getIntExtra("foodProcessingId", 0);
            bundle.putInt("foodProcessingId", upfpID);
            cEditFragment.setArguments(bundle);

            this.getFragmentManager().beginTransaction()
                    .replace(R.id.xiaye_fragment, cEditFragment).commit();
        } else if (id == 3) {
            Log.i("Main", "群组注册通知");
            GroupRegisterFragment fragment = new GroupRegisterFragment();
            Bundle bundle = new Bundle();
            int groupId = intent.getIntExtra("groupId", 0);
            bundle.putInt("groupId", groupId);
            fragment.setArguments(bundle);
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.xiaye_fragment, fragment).commit();
        } else {

        }
    }

    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) // System.currentTimeMillis()无论何时调用，肯定大于2000
            {
                Toast.makeText(getApplicationContext(), "再按一次退出",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销界面
        info.unregiste(interfaceId, Type.IF_USER_M, "");
        mJNIHandler.removeCallbacks(flagThread);
        mPlugin_mainHandler.removeCallbacks(flThread);
        mUIHandler.removeCallbacks(mUpdateUIThread);
        mJNIHandler = null;
        mPlugin_mainHandler = null;
        mUIHandler = null;
        info.close();
    }
}
