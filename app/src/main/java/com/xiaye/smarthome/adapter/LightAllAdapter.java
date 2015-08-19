package com.xiaye.smarthome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.xiaye.smarthome.R;

/**
 * Created by ChenSir on 2015/8/10 0010.
 */
public class LightAllAdapter extends BaseAdapter {

    private Holder mHolder;

    public LayoutInflater inflater;

    public LightAllAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lights_all_item, null);
            mHolder = new Holder();
            mHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.al_selected);
            mHolder.mAddr = (TextView) convertView.findViewById(R.id.al_selected_addr);
            mHolder.mid = (TextView) convertView.findViewById(R.id.al_selected_id);
            mHolder.mType = (TextView) convertView.findViewById(R.id.al_selected_type);
            mHolder.mPort = (TextView) convertView.findViewById(R.id.al_selected_port);

            convertView.setTag(mHolder);

        } else {
            mHolder = (Holder) convertView.getTag();
        }

        mHolder.mAddr.setText("地址 "+position);
        mHolder.mType.setText("类型 "+position);
        mHolder.mid.setText(position+"");
        mHolder.mPort.setText("端口 "+position);

        return convertView;
    }


    private class Holder {

        public CheckBox mCheckBox;
        public TextView mid;
        public TextView mType;
        public TextView mAddr;
        public TextView mPort;
    }
}
