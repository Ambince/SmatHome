package com.xiaye.smarthome.fragment;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.CookUtensilListAdapter;
import com.xiaye.smarthome.bean.CookUtensilBean;
import com.xiaye.smarthome.bean.PluginBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.constant.UI_Constant;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.main.OnlineOperationActivity;
import com.xiaye.smarthome.main.SmartHomeApplication;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;
import com.xiaye.smarthome.util.PluginBeanService;

/*
 * 
 * @ClassName: CookUtensilFragment
 * @Description: 烹调器具Fragment
 * @author  Android组-ChenSir/HeMingli
 * @version 1.0
 * @date 2014-11-25 下午9:24:58
 *
 */
public class CookUtensilFragment extends Fragment {

    public final static String TAG = CookbookDetailFragment.class.getSimpleName();

    ListView mListView = null;
    Button look = null;
    public static List<CookUtensilBean> mCookUtensilBeanList = null;
    public static CookUtensilListAdapter CookUtensilAdapter = null;

    InfoDealIF info = null;
    String infoReceive = null;
    private PluginBeanService pService;

    String exctCookingFlag = null; // 是否为cooking
    int count = 0; // 总人数

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        exctCookingFlag = SmartHomeApplication.exctCookingFlag;
        SmartHomeApplication.exctCookingFlag = "";

        try {
            info = new InfoDealIF();
            JsonParse jsonParse = new JsonParse();

            // 查询数据库(智能家电)TODO 待完善查询条件
            infoReceive = info.inquire(MainActivity.interfaceId,
                    Type.SELECT_MACHINE1,
                    jsonParse.pagingJsonParse(0, 0, Type.SORT_HOMEAPP));
            if (infoReceive != null) {
                Log.i(TAG, "infoReceive = " + infoReceive);
                mCookUtensilBeanList = ParseJson
                        .parseCookUtensilList(infoReceive);

                if ("cooking".equals(exctCookingFlag)) {
                    Log.i(TAG, "exctCookingFlag is " + exctCookingFlag);
                    CookUtensilAdapter = new CookUtensilListAdapter(
                            getActivity(), mCookUtensilBeanList, getArguments()
                            .getInt(UI_Constant.USER_COUNT, 0));
                } else {
                    CookUtensilAdapter = new CookUtensilListAdapter(
                            getActivity(), mCookUtensilBeanList);
                }
            } else {
                Toast.makeText(activity.getApplicationContext(), "查询器具列表失败！",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.cookingutensil, null);
        mListView = (ListView) view.findViewById(R.id.cookutensil_list);
        // 绑定适配器
        mListView.setAdapter(CookUtensilAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {

                CookUtensilBean mCookUtensil = mCookUtensilBeanList
                        .get(position);

                // 生成插件快捷入口(以后封装更新的UI的方法)
                SmartHomeApplication.mUpdateUIFlag = 1;
                SmartHomeApplication.appMap.put(
                        UI_Constant.PLUGIN_SHORTCUT_KEY, mCookUtensil);

                if (mCookUtensil.getMachineState() == 1) {
                    if ("cooking".equals(exctCookingFlag)) {
                        SmartHomeApplication.machineId = mCookUtensil
                                .getMachineId();
                        // 执行烹调
                        count = getArguments()
                                .getInt(UI_Constant.USER_COUNT, 0);
                        String maCode = mCookUtensil.getMachineShapeCode();
                        CookingFragment fragment = new CookingFragment();

                        Bundle bundle = new Bundle();
                        bundle.putInt("useNum", count);
                        bundle.putString("machineShapeCode", maCode);
                        bundle.putString(UI_Constant.COOKING_SCHEMA, "cook3_1");
                        bundle.putString(UI_Constant.COOKING_NAME, 0 + "");
                        bundle.putString(UI_Constant.FLAG, "cooking1");

                        fragment.setArguments(bundle);

                        getActivity().getFragmentManager().beginTransaction()
                                .replace(R.id.xiaye_fragment, fragment)
                                .commit();
                    } else {
                        String machineShapeCode = mCookUtensil
                                .getMachineShapeCode();
                        List<PluginBean> list = PluginBeanService
                                .getAllPluginBean(getActivity()
                                        .getApplicationContext());
                        if (list != null && list.size() != 0) {
                            pService = new PluginBeanService();
                            String pckgName = pService.getPackageName(
                                    machineShapeCode, getActivity()
                                            .getApplicationContext());
                            Log.i("CookUtensilFragment", "pckgName=="
                                    + pckgName);
                            if (pckgName != null) {

                                Log.i("CookUtensilFragment", "进入在线操作");
                                Intent intentPlugin = new Intent();
                                intentPlugin.putExtra("machineShapeCode",
                                        mCookUtensil.getMachineShapeCode());
                                intentPlugin.putExtra("machineId",
                                        mCookUtensil.getMachineId());
                                intentPlugin.setClass(getActivity(),
                                        OnlineOperationActivity.class);
                                startActivity(intentPlugin);
                            } else {
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        "未安装对应插件！", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "不存在任何插件！", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {

                    Toast.makeText(getActivity().getApplicationContext(),
                            "器具不在线,无法进入操作界面", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
