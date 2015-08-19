package com.xiaye.smarthome.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xiaye.smarthome.R;
import com.xiaye.smarthome.adapter.LightControlAdapter;

/**
 * Created by hemingli on 2015/8/10.
 */
public class LightControlFragment extends Fragment {
    ListView mListView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.light_control, null);

        mListView= (ListView) view.findViewById(R.id.light_contral_list);

        mListView.setAdapter(new LightControlAdapter(getActivity()));

        return view;
    }
}
