package com.xiaye.smarthome.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.LightGroupAdapter;

/**
 * Created by ChenSir on 2015/8/10 0010.
 */
public class GroupSettingFragment extends Fragment {

    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lightgroup_setting, null);
        mListView = (ListView) view.findViewById(R.id.lightgroup_list);
        mListView.setAdapter(new LightGroupAdapter(getActivity()));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LightSettingFragment lightSettingFragment = new LightSettingFragment();
                getActivity().getFragmentManager().beginTransaction().replace(R.id.xiaye_fragment, lightSettingFragment).commit();
            }
        });
        return view;
    }
}
