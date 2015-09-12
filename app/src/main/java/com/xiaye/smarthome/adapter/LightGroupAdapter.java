package com.xiaye.smarthome.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.LightGroupBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.main.SmartHomeApplication;
import com.xiaye.smarthome.util.ChangeByteAndInt;
import com.xiaye.smarthome.util.Connect2ByteArrays;

import java.util.List;

/**
 * Created by dell on 2015/8/10 0010.
 */
public class LightGroupAdapter extends BaseAdapter {

    private Holder mHolder;

    public List mList;

    public LayoutInflater inflater;

    public LightGroupAdapter(Context context, List list) {
        inflater = LayoutInflater.from(context);
        mList = list;
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
            convertView = inflater.inflate(R.layout.lightgroupitem, null);
            mHolder = new Holder();
            mHolder.mid = (TextView) convertView.findViewById(R.id.lightgroup_item_id);
            mHolder.mName = (TextView) convertView.findViewById(R.id.lightgroup_item_name);
            mHolder.mPosition = (TextView) convertView.findViewById(R.id.lightgroup_item_pos);
            mHolder.mAddr = (TextView) convertView.findViewById(R.id.lightgroup_item_addr);
            mHolder.mPort = (TextView) convertView.findViewById(R.id.lightgroup_item_port);
            mHolder.mRemark = (TextView) convertView.findViewById(R.id.lightgroup_item_remark);
            mHolder.mDelete = (ImageButton) convertView.findViewById(R.id.lightgroup_item_del);
            convertView.setTag(mHolder);

        } else {
            mHolder = (Holder) convertView.getTag();
        }


        LightGroupBean bean = (LightGroupBean) mList.get(position);
        mHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoDealIF info = new InfoDealIF();
                int groupId = ((LightGroupBean) mList.get(position)).getGroupId();
                byte[] input = ChangeByteAndInt.intToBytes(groupId);
                int flag = info.control(MainActivity.interfaceId, Type.PROTO_FUN_DELETE_GROUP, input, null);
                if (flag != -1) {
                    Toast.makeText(SmartHomeApplication.mAppContext, "删除成功！", Toast.LENGTH_SHORT)
                            .show();
                    mHolder.mid.setTextColor(Color.GRAY);
                    mHolder.mName.setTextColor(Color.GRAY);
                    mHolder.mPosition.setTextColor(Color.GRAY);
                    mHolder.mAddr.setTextColor(Color.GRAY);
                    mHolder.mPort.setTextColor(Color.GRAY);
                    mHolder.mRemark.setTextColor(Color.GRAY);
                    mHolder.mDelete.setClickable(false);
                    mHolder.mDelete.setBackgroundColor(Color.GRAY);

                } else {
                    Toast.makeText(SmartHomeApplication.mAppContext, "删除失败！", Toast.LENGTH_SHORT)
                            .show();

                }
            }
        });
        mHolder.mid.setText(bean.getGroupId() + "");
        mHolder.mName.setText(bean.getGroupName() + "");
        mHolder.mPosition.setText(bean.getGroupLocation() + "");
        mHolder.mAddr.setText(bean.getGroupAddress() + "");
        mHolder.mPort.setText(bean.getGroupPort() + "");
        mHolder.mRemark.setText(bean.getRemarks() + "");

        return convertView;
    }


    private class Holder {

        public TextView mid;
        public TextView mName;
        public TextView mPosition;
        public TextView mAddr;
        public TextView mPort;
        public TextView mRemark;
        public ImageButton mDelete;

    }
}
