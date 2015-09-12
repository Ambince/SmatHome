package com.xiaye.smarthome.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.main.SmartHomeApplication;
import com.xiaye.smarthome.util.GenerationId;

public class CookMenuEditFragment extends Fragment implements OnClickListener {

    public static String TAG = CookMenuEditFragment.class.getSimpleName();

    private EditText menuName_edt;
    private EditText summarize_edt;
    private EditText makingMethod_edt;
    private Button confirm_btn;

    String menuId = null;
    int foodProcessingId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        foodProcessingId = getArguments().getInt("foodProcessingId", -1);
        View view = inflater.inflate(R.layout.fragment_cooking_edit1, null);
        initView(view);
        setListener();
        return view;
    }

    public void initView(View view) {
        menuName_edt = (EditText) view.findViewById(R.id.tv_cooking_name_edt);
        summarize_edt = (EditText) view.findViewById(R.id.tv_cooking_intro_edt);
        makingMethod_edt = (EditText) view.findViewById(R.id.tv_make_intro_edt);

        confirm_btn = (Button) view.findViewById(R.id.cookedt_btn_confirm);
    }

    public void setListener() {
        confirm_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.cookedt_btn_confirm) {

            SmartHomeApplication.appMap.put("menuName", menuName_edt.getText()
                    .toString().trim());
            SmartHomeApplication.appMap.put("summarize", summarize_edt
                    .getText().toString().trim());
            SmartHomeApplication.appMap.put("makingMethod", makingMethod_edt
                    .getText().toString().trim());

            menuId = GenerationId.getId();

            SmartHomeApplication.appMap.put("menuId", menuId);

            if (foodProcessingId != -1) {
                CookingAddFragment cRecordFg = new CookingAddFragment();
                Bundle bundle = new Bundle();
                bundle.putString("menuId", menuId);
                bundle.putInt("foodProcessingId", foodProcessingId);
                cRecordFg.setArguments(bundle);

                FragmentTransaction transaction = getActivity()
                        .getFragmentManager().beginTransaction();
                transaction.replace(R.id.xiaye_fragment, cRecordFg);
                transaction.addToBackStack(TAG);
                transaction.commit();

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "没有获取到烹调记录ID！请重新上传！",Toast.LENGTH_LONG).show();
            }


        } else {

        }
    }
}
