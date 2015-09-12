package com.xiaye.smarthome.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.LightGroupAdapter;
import com.xiaye.smarthome.adapter.LightSettingAdapter;
import com.xiaye.smarthome.bean.LightGroupMemberBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;
import org.json.JSONException;

import java.util.List;

/**
 * Created by ChenSir on 2015/8/10 0010.
 */
public class LightSettingFragment extends Fragment {

    public final static String TAG = LightSettingFragment.class.getSimpleName();

    ListView mListView;
    Button mAddButton;
    Button mBackbtn;

    InfoDealIF info;
    List<LightGroupMemberBean> mLightList;
    int groupId;
    LightSettingAdapter mAdapter;

    String dbResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.light_setting, null);
        info = new InfoDealIF();

        JsonParse jsonParse = new JsonParse();
        groupId = getArguments().getInt("groupId", -1);

        mListView = (ListView) view.findViewById(R.id.light_setting_list);
        mAddButton = (Button) view.findViewById(R.id.lightsetting_addlight);
        mBackbtn = (Button) view.findViewById(R.id.lightsetting_back);

        if (groupId != -1) {
            dbResult = info.inquire(MainActivity.interfaceId, Type.SELECT_GROUPMEMBER4, jsonParse.pagingJsonParse(0, 0, groupId));
            Log.e(TAG, "群成员列表 ==" + dbResult);
            if (dbResult != null) {
                try {
                    mLightList = ParseJson.parseLightGroupMemberBeans(dbResult);
                    mAdapter = new LightSettingAdapter(getActivity(), mLightList);
                    mListView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "解析成员列表出错！", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "未获取到数据！", Toast.LENGTH_LONG).show();
            }
        }

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("groupId", groupId);
                LightAllFragment lightAllFragment = new LightAllFragment();
                lightAllFragment.setArguments(bundle);

                FragmentTransaction transaction = getActivity()
                        .getFragmentManager().beginTransaction();
                transaction.replace(R.id.xiaye_fragment, lightAllFragment);
                transaction.addToBackStack(TAG);
                transaction.commit();
            }
        });

        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStackImmediate();
            }
        });

        return view;
    }
}
