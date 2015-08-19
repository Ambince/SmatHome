package com.xiaye.smarthome.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.LightGroupAdapter;
import com.xiaye.smarthome.adapter.LightSettingAdapter;

/**
 * Created by ChenSir on 2015/8/10 0010.
 */
public class LightSettingFragment extends Fragment {

    ListView mListView;
    Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.light_setting, null);
        mListView = (ListView) view.findViewById(R.id.light_setting_list);
        mButton = (Button) view.findViewById(R.id.lightsetting_addlight);
        mListView.setAdapter(new LightSettingAdapter(getActivity()));
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LightAllFragment lightAllFragment= new LightAllFragment();
                getActivity().getFragmentManager().beginTransaction().replace(R.id.xiaye_fragment, lightAllFragment).commit();

            }
        });
        return view;
    }
}
