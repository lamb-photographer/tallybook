package com.example.tallybook.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tallybook.R;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends BaseAdapter {
    Context context;
    List<String>mDatas;
    public int year;
    public int selPos=-1;

    public void setYear(int year) {
        this.year = year;
        mDatas.clear();
        loadDatas(year);
        notifyDataSetChanged();
    }

    public CalendarAdapter(Context context, int year) {
        this.context = context;
        this.year = year;
        mDatas = new ArrayList<>();
        loadDatas(year);
    }

    private void loadDatas(int year) {
        for (int i=1;i<13;i++){
            String data= year +"/"+i;
            mDatas.add(data);
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_dialogcal_gv,viewGroup,false);
        TextView tv = view.findViewById(R.id.item_dialogcal_gv_tv);
        tv.setText(mDatas.get(i));
        tv.setBackgroundResource(R.color.grey);
        tv.setTextColor(Color.BLACK);
        if (i==selPos) {
            tv.setBackgroundResource(R.color.green);
            tv.setTextColor(Color.WHITE);
        }
        return view;
    }
}
