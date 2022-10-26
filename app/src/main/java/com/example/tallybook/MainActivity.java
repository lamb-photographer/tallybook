package com.example.tallybook;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tallybook.adapter.AccountAdapter;
import com.example.tallybook.db.AccountBean;
import com.example.tallybook.db.DBManager;
import com.example.tallybook.utils.BudgetDialog;
import com.example.tallybook.utils.MoreDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ListView todayLv;//今日收支listview
    ImageView searchIV;
    Button editBtn;
    ImageButton moreBtn;
    //数据源
    List<AccountBean>mDatas;
    AccountAdapter adapter;
    int year,month,day;
    //头布局控件
    View headerView;
    TextView topOutTv,topInTv,topbudgetTv,topConTv;
    ImageView topShowIv;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTime();
        initView();
        //添加listview头布局
        addLVHeaderView();
        mDatas = new ArrayList<>();
        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
        //设置适配器加载数据
        adapter = new AccountAdapter(this, mDatas);
        todayLv.setAdapter(adapter);
    }
    private void initView() {
        todayLv = findViewById(R.id.main_lv);
        editBtn = findViewById(R.id.main_btn_edit);
        moreBtn = findViewById(R.id.main_btn_more);
        searchIV = findViewById(R.id.main_iv_search);
        editBtn.setOnClickListener(this);
        moreBtn.setOnClickListener(this);
        searchIV.setOnClickListener(this);
        setLVLongClickListener();
    }
    //设置listview长按事件
    private void setLVLongClickListener() {
        todayLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0) {
                    return false;
                }
                int pos=i-1;
                AccountBean clickBean = mDatas.get(pos);//获取正在被点击的信息
                //弹出对话框
                showDeleteItemDialog(clickBean);
                return false;
            }
        });
    }

    private void showDeleteItemDialog(AccountBean clickBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录吗？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int click_id = clickBean.getId();
                        DBManager.deleteItemFromAccounttbById(click_id);
                        mDatas.remove(clickBean);
                        adapter.notifyDataSetChanged();
                        setTopTvShow();
                    }
                });
        builder.create().show();//显示对话框
    }

    private void addLVHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        todayLv.addHeaderView(headerView);
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_iv_out);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_iv_in);
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_iv_budget);
        topConTv = headerView.findViewById(R.id.item_mainlv_top_iv_day);
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_iv_hide);

        topbudgetTv.setOnClickListener(this);
        headerView.setOnClickListener(this);
        topShowIv.setOnClickListener(this);
    }
    //获取今日时间
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    //获取焦点调用
    @Override
    protected void onResume() {
        super.onResume();
        loadDBData();
        setTopTvShow();
    }
    //设置头布局文本内容显示
    private void setTopTvShow() {
        //今日总金额
        float incomeOneDay = DBManager.getMoneyOneDay(year, month, day, 1);
        float outcomeOneDay = DBManager.getMoneyOneDay(year, month, day, 0);
        String infoOneDay = "今日支出￥"+outcomeOneDay+"收入￥"+incomeOneDay;
        topConTv.setText(infoOneDay);
        //本月总金额
        float incomeOneMonth = DBManager.getMoneyOneMonth(year, month, 1);
        float outcomeOneMonth = DBManager.getMoneyOneMonth(year, month, 0);
        topInTv.setText("￥"+incomeOneMonth);
        topOutTv.setText("￥"+outcomeOneMonth);
        //设置显示预算剩余
        float bmoney = preferences.getFloat("bmoney", 0);
        if (bmoney == 0) {
            topbudgetTv.setText("￥ 0");
        }else{
            float syMoney = bmoney-outcomeOneMonth;
            topbudgetTv.setText("￥"+syMoney);
        }
    }
    private void loadDBData() {
        mDatas.clear();
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccounttb(year,month,day);
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_iv_search:
                Intent it =new Intent(this,SearchActivity.class);//跳转
                startActivity(it);
                break;
            case R.id.main_btn_edit:
                Intent it1 =new Intent(this,RecordActivity.class);//跳转
                startActivity(it1);
                break;
            case R.id.main_btn_more:
                MoreDialog moreDialog = new MoreDialog(this);
                moreDialog.show();
                moreDialog.setDialogSize();
                break;
            case R.id.item_mainlv_top_iv_budget:
                showBudgetDialog();
                break;
            case R.id.item_mainlv_top_iv_hide:
                toggleShow();
                break;
        }
        if (view==headerView) {
            //头布局被点击
            Intent intent = new Intent();
            intent.setClass(this, MonthChartActivity.class);
            startActivity(intent);
        }
    }
    //显示预算对话框
    private void showBudgetDialog() {
        BudgetDialog dialog = new BudgetDialog(this);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new BudgetDialog.OnEnsureListener() {
            @Override
            public void onEnsure(float money) {
                //将预算金额写入到共享参数当中，进行存储
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat("bmoney",money);
                editor.commit();
                //计算剩余
                float outcomeOneMonth = DBManager.getMoneyOneMonth(year, month, 0);
                float syMoney = money-outcomeOneMonth;
                topbudgetTv.setText("￥"+syMoney);
            }
        });
    }
    boolean isShow = true;
    //点击眼睛加密和显示
    private void toggleShow() {
        if (isShow) {
            PasswordTransformationMethod passwordMethod = PasswordTransformationMethod.getInstance();
            topInTv.setTransformationMethod(passwordMethod);
            topOutTv.setTransformationMethod(passwordMethod);
            topbudgetTv.setTransformationMethod(passwordMethod);
            topShowIv.setImageResource(R.mipmap.ih_hide);
            isShow = false;//设置标志位为隐藏
        }else {
            HideReturnsTransformationMethod hideMethod = HideReturnsTransformationMethod.getInstance();
            topInTv.setTransformationMethod(hideMethod);
            topOutTv.setTransformationMethod(hideMethod);
            topbudgetTv.setTransformationMethod(hideMethod);
            topShowIv.setImageResource(R.mipmap.ih_show);
            isShow = true;
        }
    }
}