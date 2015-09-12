package com.xiaye.smarthome.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.jni.info.InfoDealIF;
import com.xiaye.smarthome.R;
import com.xiaye.smarthome.bean.LightGroupBean;
import com.xiaye.smarthome.constant.Type;
import com.xiaye.smarthome.main.MainActivity;
import com.xiaye.smarthome.main.SmartHomeApplication;
import com.xiaye.smarthome.util.ChangeByteAndInt;

import java.util.List;

/**
 * Created by hemingli on 2015/8/10.
 */
public class LightControlAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater = null;
    private Holder holder = null;
    private Activity mactivity;
    List<LightGroupBean> mList;

    InfoDealIF info;

    public LightControlAdapter(Activity mactivity, List<LightGroupBean> list) {
        this.mactivity = mactivity;
        mList = list;
        layoutInflater = mactivity.getLayoutInflater();
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
            convertView = layoutInflater.inflate(R.layout.lightcontrol_item, null);
            holder = new Holder();
            holder.lightname = (TextView) convertView.findViewById(R.id.light_name);
            holder.mIncrease = (Button) convertView.findViewById(R.id.light_increase);
            holder.mReduce = (Button) convertView.findViewById(R.id.light_reduce);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        //TODO  0代表开，1代表关
        holder.lightname.setText(mList.get(position).getGroupName() + "");
        holder.mIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getGroupId();
                byte[] input = ChangeByteAndInt.intToBytes(id);
                int flag = info.control(MainActivity.interfaceId, Type.GROUP_DEVICE_POWON, input, null);
                Log.e("LightControlAdapter", "control increase callback is = " + flag);
//                if (flag != -1) {
//                    Toast.makeText(SmartHomeApplication.mAppContext, "增加亮度", Toast.LENGTH_SHORT)
//                            .show();
//                }
            }
        });

        holder.mReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mList.get(position).getGroupId();
                byte[] input = ChangeByteAndInt.intToBytes(id);
                int flag = info.control(MainActivity.interfaceId, Type.GROUP_DEVICE_POWOFF, input, null);
                Log.e("LightControlAdapter", "control reduce callback is = " + flag);
//                if (flag != -1) {
//                    Toast.makeText(SmartHomeApplication.mAppContext, "增加亮度", Toast.LENGTH_SHORT)
//                            .show();
//                }
            }
        });

        return convertView;
    }

    private class Holder {
        private TextView lightname;
        private Button mIncrease;
        private Button mReduce;
    }
}
