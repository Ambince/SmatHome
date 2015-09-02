package com.xiaye.smarthome.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.LightAllAdapter;
import com.xiaye.smarthome.adapter.LightGroupAdapter;
import com.xiaye.smarthome.adapter.UserListAdapter;
import com.xiaye.smarthome.bean.CookMenuBean;
import com.xiaye.smarthome.bean.LightGroupBean;
import com.xiaye.smarthome.bean.LightGroupMemberBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.util.ChangeByteAndInt;
import com.xiaye.smarthome.util.Connect2ByteArrays;
import com.xiaye.smarthome.util.JsonParse;
import com.xiaye.smarthome.util.ParseJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenSir on 2015/8/10 0010.
 */
public class LightAllFragment extends Fragment {

    ListView mListView;

    Button mAddbtn;
    Button mBackbtn;
    Button mSearch;
    EditText mEditText;

    InfoDealIF info;
    List<LightGroupMemberBean> aList;
    String dbResult;

    int groupId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lights_all, null);
        mListView = (ListView) view.findViewById(R.id.light_all_list);
        mAddbtn = (Button) view.findViewById(R.id.lightall_addlights);
        mBackbtn = (Button) view.findViewById(R.id.lightall_back);
        mSearch = (Button) view.findViewById(R.id.lightall_search);
        mEditText = (EditText) view.findViewById(R.id.lightall_content);

        info = new InfoDealIF();
        groupId = getArguments().getInt("groupId", -1);
        dbResult = info.inquire(MainActivity.interfaceId, Type.SELECT_GROUPMEMBER5, null);
        if (dbResult != null) {
            try {
                aList = ParseJson.parseAllLightBeans(dbResult);
                mListView.setAdapter(new LightAllAdapter(getActivity(), aList));

                mSearch.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //地址或端口号
                        if (mEditText.getText().toString().trim().equals(""))
                            return;
                        int content = Integer.parseInt(mEditText.getText().toString().trim());
                        for (int i = 0; i < aList.size(); i++) {
                            LightGroupMemberBean bean = aList.get(i);
                            if (content == bean.getGroupAddress() || content == bean.getGroupPort()) {
                                mListView.smoothScrollToPosition(i);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "未查询到相关灯光！", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                mAddbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (LightAllAdapter.getIsSelected() != null) {
                            HashMap<Integer, Boolean> checked = UserListAdapter
                                    .getIsSelected();
                            for (Map.Entry<Integer, Boolean> entry : checked.entrySet()) {
                                if (entry.getValue()) {
                                    int position = entry.getKey();
                                    LightGroupMemberBean bean = aList.get(position);
                                    int groupId = bean.getGroupId();
                                    int device_Vaddrs = bean.getDevice_Vaddrs();
                                    byte[] input = Connect2ByteArrays.conn2ByteArrays(ChangeByteAndInt.intToBytes(groupId), ChangeByteAndInt.intToBytes(device_Vaddrs));
                                    InfoDealIF.OutPut output = new InfoDealIF.OutPut();
                                    if (input != null) {
                                        int flag = info.control(MainActivity.interfaceId,
                                                Type.PROTO_FUN_GROUPIN, input, output);

                                        if (flag != -1 && (output.getOutput()[0] == 0)) {
                                            Toast.makeText(getActivity().getApplicationContext(), "保存成功！", Toast.LENGTH_LONG)
                                                    .show();
                                            getActivity().getFragmentManager().popBackStackImmediate();

                                        } else {
                                            Log.e("LightAllFragment", "flag = " + flag);
                                            Toast.makeText(getActivity(), "保存失败！", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            } catch (JSONException e) {
                Toast.makeText(getActivity().getApplicationContext(), "解析出错！", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "未获取到数据！", Toast.LENGTH_LONG).show();
        }

        mBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStackImmediate();
            }
        });


        return view;
    }


    public String changeBeanToJsonString(LightGroupMemberBean bean) {

        JSONObject stoneObject = new JSONObject();

        int groupId = bean.getGroupId();
        String group_Hardware_Type = bean.getGroup_Hardware_Type();
        int groupAddress = bean.getGroupAddress();
        int groupPort = bean.getGroupPort();
        String remarks = bean.getRemarks();

        try {
            stoneObject.put("groupId", groupId);
            stoneObject.put("group_Hardware_Type", group_Hardware_Type);
            stoneObject.put("groupAddress", groupAddress);
            stoneObject.put("groupPort", groupPort);
            stoneObject.put("remarks", remarks);
            return stoneObject.toString();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "保存记录出错！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }
}
