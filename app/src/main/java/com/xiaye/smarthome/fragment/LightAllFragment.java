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
import com.xiaye.smarthome.adapter.LightAllAdapter;
import com.xiaye.smarthome.adapter.LightGroupAdapter;

/**
 * Created by ChenSir on 2015/8/10 0010.
 */
public class LightAllFragment extends Fragment {

    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lights_all, null);
        mListView = (ListView) view.findViewById(R.id.light_all_list);
        mListView.setAdapter(new LightAllAdapter(getActivity()));
        return view;
    }
}
