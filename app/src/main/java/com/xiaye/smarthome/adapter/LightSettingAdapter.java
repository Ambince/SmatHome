package com.xiaye.smarthome.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.xiaye.smarthome.R;

/**
 * Created by ChenSir on 2015/8/10 0010.
 */
public class LightSettingAdapter extends BaseAdapter {

    private Holder mHolder;

    public LayoutInflater inflater;

    public LightSettingAdapter(Context context) {
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
            convertView = inflater.inflate(R.layout.light_setting_item, null);
            mHolder = new Holder();
            //            mHolder.mid = (TextView) convertView.findViewById(R.id.lightgroup_item_id);
            mHolder.mAddr = (TextView) convertView.findViewById(R.id.lightsetting_item_addr);
            mHolder.mType = (TextView) convertView.findViewById(R.id.lightsetting_item_type);
            mHolder.mPort = (TextView) convertView.findViewById(R.id.lightsetting_item_port);
            mHolder.mRemark = (TextView) convertView.findViewById(R.id.lightsetting_item_remark);
            mHolder.mButton = (ImageButton) convertView.findViewById(R.id.lightsetting_item_delete);
            convertView.setTag(mHolder);


        } else {
            mHolder = (Holder) convertView.getTag();
        }

//        mHolder.mid.setText(position+"");
        mHolder.mAddr.setText("地址 " + position);
        mHolder.mType.setText("类型 " + position);
        mHolder.mPort.setText("端口 " + position);
        mHolder.mRemark.setText("这是灯光 " + position);

        return convertView;
    }


    private class Holder {

        //        public TextView mid;
        public TextView mType;
        public TextView mAddr;
        public TextView mPort;
        public TextView mRemark;
        public ImageButton mButton;

    }
}
