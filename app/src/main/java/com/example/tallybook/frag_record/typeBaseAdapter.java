package com.example.tallybook.frag_record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tallybook.R;
import com.example.tallybook.db.TypeBean;

import java.util.List;

public class typeBaseAdapter extends BaseAdapter {
    Context context;
    List<TypeBean>mDatas;
    int selectPos=0;
    public typeBaseAdapter(Context context, List<TypeBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
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
        view = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv,viewGroup,false);
        ImageView iv= view.findViewById(R.id.item_recordfrag_iv);
        TextView tv= view.findViewById(R.id.item_recordfrag_tv);
        TypeBean typeBean = mDatas.get(i);
        tv.setText(typeBean.getTypename());
        if (selectPos==i){
            iv.setImageResource(typeBean.getSimageId());
        }else{
            iv.setImageResource(typeBean.getImageId());
        }
        return view;
    }
}
