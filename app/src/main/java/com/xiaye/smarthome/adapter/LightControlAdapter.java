package com.xiaye.smarthome.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.xiaye.smarthome.R;

import java.util.List;

/**
 * Created by hemingli on 2015/8/10.
 */
public class LightControlAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater = null;
    private List list;
    private Holder holder = null;
    private Activity mactivity;

    public LightControlAdapter(Activity mactivity) {
        this.mactivity = mactivity;
//        this.list = list;
        layoutInflater = mactivity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.lightcontrol_item,
                    null);
            holder = new Holder();
            holder.lightname = (TextView) convertView.findViewById(R.id.light_name);
            holder.lightstate = (TextView) convertView.findViewById(R.id.light_state);
            holder.mIncrease = (Button) convertView.findViewById(R.id.light_increase);
            holder.mReduce = (Button) convertView.findViewById(R.id.light_reduce);
            convertView.setTag(holder);


        } else {
            holder = (Holder) convertView.getTag();
        }

        //TODO
        if (position < 2) {
            holder.lightname.setText("灯光" + position);
            holder.lightstate.setText("开");
        } else if (position == 2) {

            holder.lightname.setText("群组" + position);
            holder.lightstate.setText("部分开启");
        } else if (position == 3) {

            holder.lightname.setText("群组" + position);
            holder.lightstate.setText("全开");
        } else {
            holder.lightname.setText("群组" + position);
            holder.lightstate.setText("全关");
        }


        return convertView;
    }

    private class Holder {
        private TextView lightname;
        private TextView lightstate;
        private Button mIncrease;
        private Button mReduce;
    }
}
