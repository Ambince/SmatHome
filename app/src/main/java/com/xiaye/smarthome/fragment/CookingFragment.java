package com.xiaye.smarthome.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.constant.UI_Constant;
import com.xiaye.smarthome.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Android组-ChengBin
 * @version 1.0
 * @ClassName: CookingFragment
 * @Description: 烹调菜谱
 * @date 2014-11-25 下午4:34:38
 */
public class CookingFragment extends Fragment {

    public static String TAG = CookingFragment.class.getSimpleName();

    private GridView gridview;
    private String cooking_name;
    private String schema;
    private int[] cooking_array = {R.array.local, R.array.meat, R.array.isdry, R.array.color, R.array.taste, R.array.isfish};

    String exctCookingFlag = null;

    static int useNum = 0;
    static String mCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        exctCookingFlag = getArguments().getString(UI_Constant.FLAG, "");
        if ("cooking1".equals(exctCookingFlag)) {
            useNum = getArguments().getInt("useNum");
            mCode = getArguments().getString("machineShapeCode");
        }

        Log.e("useNum", useNum + "..");
        Log.e("mCode", mCode + "..");

        return inflater.inflate(R.layout.fragment_cooking, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        cooking_name = (String) getArguments().get(UI_Constant.COOKING_NAME);
        Log.e(TAG, "cooking_name= " + cooking_name);
        if (cooking_name != null) {
            int pos = Integer.parseInt(cooking_name);
            if (pos < cooking_array.length && pos != -1) {
                bindData(pos);
            }
        }
    }

    /**
     * @param view
     * @Description: 初始化所有布局
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-25 下午4:48:41
     */
    private void initView(View view) {
        gridview = (GridView) view.findViewById(R.id.gv_cooking);

        if (exctCookingFlag.startsWith("cooking")) {
            List<String> list = getCategoryById(R.array.header, "cook3_1");
            MainActivity.header_category.addCategory(
                    list.toArray(new String[list.size()]),
                    UI_Constant.CATEGORY_HEADER,
                    UI_Constant.CATEGORY_HORIZONTAL);
            View view_v0 = MainActivity.header_category.getChildAt(0);
            RadioGroup group = (RadioGroup) view_v0
                    .findViewById(R.id.container);
            RadioButton radioButton = (RadioButton) group.getChildAt(0);
            radioButton.setChecked(true);
        }
    }

    /**
     * @param pos
     * @Description: 绑定数据
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-25 下午4:48:43
     */
    private void bindData(int pos) {
        ArrayList<HashMap<String, Object>> datas = loadDataFromDB(pos);

        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
                datas, // 数据来源
                R.layout.fragment_cooking_item, // fragment_cooking_item
                new String[]{"cooking_name"},
                new int[]{R.id.cooking_item});
        gridview.setOnItemClickListener(itemClickListener);
        gridview.setAdapter(saImageItems);
    }

    /**
     * 对item的点击事件处理
     */
    OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int pos,
                                long size) {
            TextView tv_view = (TextView) view.findViewById(R.id.cooking_item);
            String cooking_item_name = tv_view.getText().toString();
            Log.e("cookingFG", "cooking_item_name= " + cooking_item_name);
            Bundle bundle = new Bundle();
            String uri = getUri(cooking_item_name, cooking_name);

            bundle.putString(UI_Constant.COOKING_URI, uri);
            bundle.putString(UI_Constant.COOKING_SCHEMA, schema);
            bundle.putString(UI_Constant.COOKING_NAME, cooking_name);

            Log.e(TAG,"exctCookingFlag = " + exctCookingFlag);

            // 传递“执行烹调”Flag
            if (exctCookingFlag.startsWith("cooking")) {

                bundle.putString("cuisinesName", cooking_item_name);
                bundle.putInt("useNum", useNum);
                bundle.putString("machineShapeCode", mCode);
                bundle.putString(UI_Constant.FLAG, exctCookingFlag);
            }

            CookingItemFragment itemFragment = new CookingItemFragment();
            itemFragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity()
                    .getFragmentManager().beginTransaction();
            transaction.replace(R.id.xiaye_fragment, itemFragment);
            transaction.addToBackStack(TAG);
            transaction.commit();
        }
    };

    /**
     * @param text
     * @param pos
     * @return
     * @Description: 获取URI
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-29 下午5:53:50
     */
    private String getUri(String text, String pos) {
        String[] array = getResources().getStringArray(
                cooking_array[Integer.parseInt(pos)]);
        for (String line : array) {
            if (line.contains(text)) {
                return line;
            }
        }
        return null;
    }

    /***
     * @param pos
     * @return
     * @Description: 从数据库获取数据
     * @author ChengBin
     * @version 1.0
     * @date 2014-11-26 上午10:02:37
     */
    private ArrayList<HashMap<String, Object>> loadDataFromDB(int pos) {
        String[] arrayString = getActivity().getResources().getStringArray(
                cooking_array[pos]);
        ArrayList<String> list = new ArrayList<String>();
        schema = (String) getArguments().get(UI_Constant.COOKING_SCHEMA);
        for (String line : arrayString) {
            if (line.contains(schema)) {
                list.add(line);
            }
        }

        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();

        for (String cooking_name : list) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Uri uri = Uri.parse(cooking_name);
            String[] names = uri.getScheme().split("-");
            if (names == null || names.length < 2)
                continue;
            map.put("cooking_name", names[1]);
            lstImageItem.add(map);
        }
        return lstImageItem;
    }

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
        String[] loops = getActivity().getResources().getStringArray(array_id);
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
}
