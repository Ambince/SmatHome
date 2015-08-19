package com.xiaye.smarthome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.xiaye.smarthome.R;

/**
 * Created by dell on 2015/8/10 0010.
 */
public class LightGroupAdapter extends BaseAdapter {

    private Holder mHolder;

    public LayoutInflater inflater;

    public LightGroupAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 3;
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
            convertView = inflater.inflate(R.layout.lightgroupitem, null);
            mHolder = new Holder();
            mHolder.mid = (TextView) convertView.findViewById(R.id.lightgroup_item_id);
            mHolder.mName = (TextView) convertView.findViewById(R.id.lightgroup_item_name);
            mHolder.mPosition = (TextView) convertView.findViewById(R.id.lightgroup_item_pos);
            mHolder.mAddr = (TextView) convertView.findViewById(R.id.lightgroup_item_addr);
            mHolder.mType = (TextView) convertView.findViewById(R.id.lightgroup_item_type);
            mHolder.mPort = (TextView) convertView.findViewById(R.id.lightgroup_item_port);
            mHolder.mRemark = (TextView) convertView.findViewById(R.id.lightgroup_item_remark);
            convertView.setTag(mHolder);

        } else {
            mHolder = (Holder) convertView.getTag();
        }

        mHolder.mid.setText(position + "");
        mHolder.mName.setText("名称" + position);
        mHolder.mPosition.setText("位置" + position);
        mHolder.mAddr.setText("地址 " + position);
        mHolder.mType.setText("类型 " + position);
        mHolder.mPort.setText("端口 " + position);
        mHolder.mRemark.setText("这是群组 " + position);

        return convertView;
    }


    private class Holder {

        public TextView mid;
        public TextView mName;
        public TextView mPosition;
        public TextView mType;
        public TextView mAddr;
        public TextView mPort;
        public TextView mRemark;

    }
}
