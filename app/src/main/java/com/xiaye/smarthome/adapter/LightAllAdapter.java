package com.xiaye.smarthome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.LightGroupMemberBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ChenSir on 2015/8/10 0010.
 */
public class LightAllAdapter extends BaseAdapter {

    private Holder mHolder;

    public List<LightGroupMemberBean> mList;
    public LayoutInflater inflater;

    public static HashMap<Integer, Boolean> isSelected;

    public LightAllAdapter(Context context,List<LightGroupMemberBean> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        isSelected = new HashMap<>();
        initData();
    }

    private void initData() {
        for (int i = 0; i < mList.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lights_all_item, null);
            mHolder = new Holder();
            mHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.al_selected);
            mHolder.mAddr = (TextView) convertView.findViewById(R.id.al_selected_addr);
            mHolder.mName = (TextView) convertView.findViewById(R.id.al_selected_id);
            mHolder.mPort = (TextView) convertView.findViewById(R.id.al_selected_port);

            convertView.setTag(mHolder);
        } else {
            mHolder = (Holder) convertView.getTag();
        }

        LightGroupMemberBean bean = mList.get(position);

        mHolder.mAddr.setText(bean.getDv_addrs()+"");
        mHolder.mName.setText(bean.getGroupId()+"");
        mHolder.mPort.setText(bean.getGroupPort()+"");

        mHolder.mCheckBox.setSelected(isSelected.get(position));
        mHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getIsSelected().put(position, isChecked);
            }
        });

        return convertView;

    }


    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    private class Holder {
        public CheckBox mCheckBox;
        public TextView mName;
        public TextView mAddr;
        public TextView mPort;
    }
}
