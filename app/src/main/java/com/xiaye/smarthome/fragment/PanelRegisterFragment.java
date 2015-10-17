package com.xiaye.smarthome.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.jni.info.InfoDealIF.OutPut;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class PanelRegisterFragment extends Fragment implements OnClickListener {

    InfoDealIF info = null;
    TextView panelAddr_txt = null;

    EditText pLoc_edt = null;

    Button determine = null;

    int deviceAddr = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        info = new InfoDealIF();

        deviceAddr = getArguments().getInt("deviceAddr", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_register, null);
        initView(view);
        bindData();
        return view;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String loc = pLoc_edt.getText().toString().trim();
        if (id == R.id.panel_register_confirm_btn) {
            JSONObject stoneObject = new JSONObject();
            try {
                stoneObject.put("machineLocation", loc);
                stoneObject.put("machineAddress", deviceAddr);

                String sendRegist = stoneObject.toString();
                OutPut output = new OutPut();

                if (!(info.control(MainActivity.interfaceId,
                        Type.UPDATE_GROUP, sendRegist.getBytes(), output) < 0)) {
                    Toast.makeText(getActivity(), "信息补充成功！！", Toast.LENGTH_LONG)
                            .show();

                } else {
                    Toast.makeText(getActivity(), "信息补充失败！！请重试！",
                            Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

        }

    }

    /**
     * @param view
     * @Description:初始化控件
     * @author ChenSir
     * @version 1.0
     * @date 2015-9-2 下午6:13:29
     */
    public void initView(View view) {

        panelAddr_txt = (TextView) view.findViewById(R.id.register_paddr_txt);
        pLoc_edt = (EditText) view.findViewById(R.id.register_ploc_edt);
        determine = (Button) view.findViewById(R.id.panel_register_confirm_btn);

        determine.setOnClickListener(this);
    }

    /**
     * @Description:绑定数据
     * @author ChenSir
     * @version 1.0
     * @date 2015-9-2 下午6:14:03
     */
    public void bindData() {
        panelAddr_txt.setText(deviceAddr+"");
    }
}
