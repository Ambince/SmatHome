package com.xiaye.smarthome.fragment;

import java.util.List;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.CookingItemAdapter;
import com.xiaye.smarthome.bean.CookMenuBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.constant.UI_Constant;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;

/**
 * @author Android组-ChengBin/ChenSir
 * @version 1.0
 * @ClassName: CoookingItemFragment
 * @Description: 具体菜品名称
 * @date 2014-11-29 上午11:35:49
 */
public class CookingItemFragment extends Fragment {

    public static String TAG = CookingItemFragment.class.getSimpleName();

    private ListView lv_cooking;
    private Button btn_back;
    private InfoDealIF info;
    private String recieve_localcuisine;
    private String cooking_name;
    private String schema;
    private List<CookMenuBean> datas;

    private int[] cooking_code = {0x80201004, 0x80201002, 0x80201006,
            0x80201007, 0x80201008, 0x80201009, 0x8020100a, 0x8020100b};

    String exctCookingFlag = null;
    int useNum = 0;
    String machineShapeCode = null;
    String cuisinesName = null;
    String uriString = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//		if (MainActivity.header_category != null) {
//			MainActivity.header_category.removeAllViews();
//		}

        schema = getArguments().getString(UI_Constant.COOKING_SCHEMA, "");
        cooking_name = getArguments().getString(UI_Constant.COOKING_NAME, "");
        Log.e("CookingItem", cooking_name);
        exctCookingFlag = getArguments().getString(UI_Constant.FLAG, "");

        return inflater.inflate(R.layout.fragfment_cooking_item_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        info = new InfoDealIF();

        initView(view);

        setListener();

        bindData();
    }

    private void initView(View view) {

        btn_back = (Button) view.findViewById(R.id.btn_back);

        lv_cooking = (ListView) view.findViewById(R.id.lv_list_item);

    }

    /**
     * @Description:(这里用一句话描述这个方法的作用)
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-29 下午7:04:14
     */
    private void setListener() {
        btn_back.setOnClickListener(backListener);
        lv_cooking.setOnItemClickListener(itemClickListener);
    }

    /**
     * @Description: 绑定数据到控件
     * @author ChengSir
     * @version 1.0
     * @date 2014-11-29 下午7:03:50
     */
    private void bindData() {
        uriString = getArguments().getString(UI_Constant.COOKING_URI, "");

        Uri uri = Uri.parse(uriString);
        String authority = uri.getAuthority();
        datas = loadDataFromDB(authority);
        if (datas != null && datas.size() != 0) {
            lv_cooking.setAdapter(new CookingItemAdapter(getActivity(), datas));
        }
    }

    /**
     * @param authority
     * @return
     * @Description: 从数据库加载数据
     * @author ChengBin/ChenSir
     * @version 1.0
     * @date 2014-11-29 下午7:01:39
     */
    private List<CookMenuBean> loadDataFromDB(String authority) {

        int pos = Integer.parseInt(authority);
        // 判断是否为执行烹调流程
        if (exctCookingFlag != null && exctCookingFlag.startsWith("cooking")) {
            cuisinesName = getArguments().getString("cuisinesName");
            useNum = getArguments().getInt("useNum");
            machineShapeCode = getArguments().getString("machineShapeCode");

            Log.e("imp_info", cuisinesName + " " + useNum + " " + machineShapeCode);
            JsonParse jParse = new JsonParse();
            String condition = jParse.threeConditionsQuery(0, 0, useNum,
                    machineShapeCode, cuisinesName);
            recieve_localcuisine = info.inquire(MainActivity.interfaceId,
                    Type.SELECT_PROCESS3, condition);
            Log.i("CookingItemFragment", "recieve_localcuisine = "
                    + recieve_localcuisine);
        } else {
            recieve_localcuisine = info.inquire(MainActivity.interfaceId,
                    cooking_code[pos], null);
        }
        try {
            if (recieve_localcuisine != null) {
                datas = ParseJson.parseCookbookbean(recieve_localcuisine);
            } else {
                Toast.makeText(getActivity(), "没有符合条件的记录!", Toast.LENGTH_LONG)
                        .show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "解析信息失败!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return datas;
    }

    OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            CookMenuBean itemBean = datas.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("cookMenuBean", itemBean);
            bundle.putString(UI_Constant.COOKING_URI, uriString);

            if (exctCookingFlag.startsWith("cooking")) {
                Log.e("CookingItemFg 171", "exctCookingFlag = "
                        + exctCookingFlag);
                bundle.putInt("useNum", useNum);
                bundle.putString(UI_Constant.FLAG, exctCookingFlag);
                bundle.putString(UI_Constant.COOKING_NAME, cooking_name);
                bundle.putString(UI_Constant.COOKING_SCHEMA, schema);
                bundle.putString(UI_Constant.FLAG, exctCookingFlag);
                bundle.putInt("useNum", useNum);
                bundle.putString("cuisinesName", cuisinesName);
                bundle.putString("machineShapeCode", machineShapeCode);
                bundle.putString(UI_Constant.COOKING_URI, uriString);
            }

            CookingInroFragment fragment = new CookingInroFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().addToBackStack(TAG);
            getFragmentManager().beginTransaction()
                    .replace(R.id.xiaye_fragment, fragment).commit();

        }
    };

    OnClickListener backListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            getFragmentManager().popBackStackImmediate();
        }
    };

}
