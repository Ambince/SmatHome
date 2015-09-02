package com.xiaye.smarthome.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.LightGroupAdapter;
import com.xiaye.smarthome.bean.LightGroupBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.ParseJson;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenSir on 2015/8/10 0010.
 */
public class GroupSettingFragment extends Fragment {

    public final static String TAG = GroupSettingFragment.class.getSimpleName();

    ListView mListView;
    Button mSearch_btn;
    EditText mContent_edt;


    InfoDealIF info;

    String dbResult;
    List<LightGroupBean> mGroupList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lightgroup_setting, null);

        info = new InfoDealIF();
        mGroupList = new ArrayList<>();
        mSearch_btn = (Button) view.findViewById(R.id.lightgroup_search);
        mContent_edt = (EditText) view.findViewById(R.id.lightgroup_edt);

        //查询数据库 群组列表
        dbResult = info.inquire(MainActivity.interfaceId, Type.SELECT_GROUP3, null);
        Log.e(TAG, "群组列表 ==" + dbResult);
        if (dbResult != null) {
            try {
                mGroupList = ParseJson.parseLightGroupBeans(dbResult);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "解析群组列表出错！", Toast.LENGTH_LONG).show();
            }

            mListView = (ListView) view.findViewById(R.id.lightgroup_list);
            if (mGroupList.size() != 0) {
                mListView.setAdapter(new LightGroupAdapter(getActivity(), mGroupList));
                mSearch_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //地址或端口号
                        if (mContent_edt.getText().toString().trim().equals(""))
                            return;
                        int content = Integer.parseInt(mContent_edt.getText().toString().trim());
                        for (int i = 0; i < mGroupList.size(); i++) {
                            LightGroupBean bean = mGroupList.get(i);
                            if (content == bean.getGroupAddress() || content == bean.getGroupPort()) {
                                mListView.smoothScrollToPosition(i);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "未查询到相关群组！", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    int groupId = mGroupList.get(position).getGroupId();
                    Bundle bundle = new Bundle();
                    bundle.putInt("groupId", groupId);
                    LightSettingFragment lightSettingFragment = new LightSettingFragment();
                    lightSettingFragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity()
                            .getFragmentManager().beginTransaction();
                    transaction.replace(R.id.xiaye_fragment, lightSettingFragment);
                    transaction.addToBackStack(TAG);
                    transaction.commit();
                }
            });

        } else {
            Toast.makeText(getActivity().getApplicationContext(), "未获取到群组列表！", Toast.LENGTH_LONG).show();
        }
        return view;
    }
}
