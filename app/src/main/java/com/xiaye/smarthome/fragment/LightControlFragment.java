package com.xiaye.smarthome.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.LightControlAdapter;
import com.xiaye.smarthome.bean.LightGroupBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.constant.UI_Constant;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;

import org.json.JSONException;

import java.util.List;

/**
 * Created by ChenSir on 2015/8/10.
 */
public class LightControlFragment extends Fragment {

    ListView mListView = null;

    InfoDealIF info;
    String dbResult;

    List<LightGroupBean> mList;
    String location;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.light_control, null);
        info = new InfoDealIF();
        location = getArguments().getString(UI_Constant.LIGHT_LOCATION, "");

        if (!location.equals("")) {
            JsonParse jsonParse = new JsonParse();
            if (!location.equals("其他")) {
                dbResult = info.inquire(MainActivity.interfaceId, Type.SELECT_GROUP4, jsonParse.pagingJsonParse(0, 0, location));
            } else {
                //用户自己新建的群组
                dbResult = info.inquire(MainActivity.interfaceId, Type.SELECT_GROUP5, jsonParse.pagingJsonParse(0, 0, location));
            }
            if (dbResult != null) {
                try {
                    mList = ParseJson.parseLightControlBeans(dbResult);
                    mListView.setAdapter(new LightControlAdapter(getActivity(), mList));
                } catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "解析列表出错！", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "未查询到相关数据！", Toast.LENGTH_LONG).show();
            }
        } else {

        }
        return view;
    }
}
