package com.xiaye.smarthome.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.LightGroupMemberBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.main.SmartHomeApplication;
import com.xiaye.smarthome.util.ChangeByteAndInt;
import com.xiaye.smarthome.util.Connect2ByteArrays;

import java.util.List;

/**
 * Created by ChenSir on 2015/8/10 0010.
 */
public class LightSettingAdapter extends BaseAdapter {

    private Holder mHolder;

    public LayoutInflater inflater;

    public List<LightGroupMemberBean> mList;

    InfoDealIF info ;

    public LightSettingAdapter(Context context, List<LightGroupMemberBean> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
        info = new InfoDealIF();
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
            convertView = inflater.inflate(R.layout.light_setting_item, null);
            mHolder = new Holder();

            mHolder.mNo = (TextView) convertView.findViewById(R.id.lightsetting_item_no);
            mHolder.mAddr = (TextView) convertView.findViewById(R.id.lightsetting_item_addr);
            mHolder.mPort = (TextView) convertView.findViewById(R.id.lightsetting_item_port);
            mHolder.mRemark = (TextView) convertView.findViewById(R.id.lightsetting_item_remark);
            mHolder.mButton = (ImageButton) convertView.findViewById(R.id.lightsetting_item_delete);

            convertView.setTag(mHolder);

        } else {
            mHolder = (Holder) convertView.getTag();
        }

        LightGroupMemberBean bean = mList.get(position);
        mHolder.mNo.setText(position + 1 + "");
        mHolder.mAddr.setText(bean.getGroupAddress() + "");
        mHolder.mPort.setText(bean.getGroupPort() + "");
        mHolder.mRemark.setText(bean.getRemarks() + "");

        mHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoDealIF.OutPut output = new InfoDealIF.OutPut();
                int groupId = mList.get(position).getGroupId();
                int device_Vaddrs = mList.get(position).getDevice_Vaddrs();
                byte[] input = Connect2ByteArrays.conn2ByteArrays(ChangeByteAndInt.intToBytes(groupId), ChangeByteAndInt.intToBytes(device_Vaddrs));
                int flag = info.control(MainActivity.interfaceId,Type.PROTO_FUN_GROUPOUT,input,output);
                if (flag != -1 && (output.getOutput()[0] == 0)) {
                    Toast.makeText(SmartHomeApplication.mAppContext, "删除成功！", Toast.LENGTH_SHORT)
                            .show();
//                    mHolder.mNo.setTextColor(Color.GRAY);
//                    mHolder.mAddr.setTextColor(Color.GRAY);
//                    mHolder.mType.setTextColor(Color.GRAY);
//                    mHolder.mPort.setTextColor(Color.GRAY);
//                    mHolder.mRemark.setTextColor(Color.GRAY);
//                    mHolder.mButton.setClickable(false);
//                    mHolder.mButton.setBackgroundColor(Color.GRAY);

                    mList.remove(position);
                    LightSettingAdapter.this.notifyDataSetChanged();

                } else {
                    Toast.makeText(SmartHomeApplication.mAppContext, "删除失败！请重试！", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        return convertView;
    }


    private class Holder {

        public TextView mNo;
        public TextView mAddr;
        public TextView mPort;
        public TextView mRemark;
        public ImageButton mButton;

    }
}
