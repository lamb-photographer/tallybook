package com.example.tallybook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.tallybook.adapter.RecordPagerAdapter;
import com.example.tallybook.frag_record.BaseRecordFragment;
import com.example.tallybook.frag_record.outcomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.example.tallybook.frag_record.incomeFragment;

public class RecordActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        //查找控件
        tabLayout=findViewById(R.id.record_tabs);
        viewPager=findViewById(R.id.record_vp);
        //设置加载页面
        initPager();
    }

    private void initPager() {
        List<Fragment>fragmentList=new ArrayList<>();
        outcomeFragment outFrag=new outcomeFragment();
        incomeFragment inFrag=new incomeFragment();
        fragmentList.add(outFrag);
        fragmentList.add(inFrag);

        RecordPagerAdapter pagerAdapter=new RecordPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    //点击事件
    public void onClick(View view) {
       switch(view.getId()){
           case R.id.record_iv_back:
               finish();
               break;
       }
    }
}
