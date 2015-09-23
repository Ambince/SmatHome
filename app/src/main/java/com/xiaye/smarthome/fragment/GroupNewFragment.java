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
import android.widget.Toast;

import com.jni.info.InfoDealIF;
import com.jni.info.InfoDealIF.OutPut;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;

public class GroupNewFragment extends Fragment implements OnClickListener {

    InfoDealIF info = null;

    EditText gName_edt = null;

    Button determine = null;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        info = new InfoDealIF();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_new, null);
        initView(view);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String name = gName_edt.getText().toString().trim();
        if (id == R.id.group_new_confirm_btn && !name.equals("")) {
            OutPut output = new OutPut();
            if (!(info.control(MainActivity.interfaceId,
                    Type.PROTO_FUN_NEW_GROUP, name.getBytes(), output) < 0)) {
                Toast.makeText(getActivity(), "添加成功！！", Toast.LENGTH_LONG)
                        .show();
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.xiaye_fragment, new GroupSettingFragment()).commit();
            } else {
                Toast.makeText(getActivity(), "添加失败！！请重试！",
                        Toast.LENGTH_LONG).show();
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
        gName_edt = (EditText) view.findViewById(R.id.new_gname_edt);
        determine = (Button) view.findViewById(R.id.group_new_confirm_btn);

        determine.setOnClickListener(this);
    }
}
