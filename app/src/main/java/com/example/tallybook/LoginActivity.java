package com.example.tallybook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.tallybook.db.AccountBean;
import com.example.tallybook.db.DBManager;
import com.example.tallybook.registdb.RegistBean;
import com.example.tallybook.registdb.RegisterDBManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nameEt,passwordEt;
    Button loginBtn,registerBtn;
    String name,pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameEt = findViewById(R.id.login_username);
        passwordEt = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_login_btn);
        registerBtn = findViewById(R.id.login_register_btn);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        name = nameEt.getText().toString().trim();
        pw = passwordEt.getText().toString();
        switch (view.getId()) {
            case R.id.login_login_btn:
                showMessage();
                break;
            case R.id.login_register_btn:
                Intent it1 =new Intent(this,RegistActivity.class);//跳转
                startActivity(it1);
                break;
        }
    }

    private void showMessage() {
        String judge = RegisterDBManager.judge(name, pw);
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
        }else{
            if (TextUtils.isEmpty(pw)){
                Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            }else{
                if (judge.equals("登录成功")){
                    DBManager.setAccounttb(name);
                    Intent it =new Intent(this,MainActivity.class);//跳转
                    startActivity(it);
                }
                else if(judge.equals("密码错误")){
                    Toast.makeText(this, judge, Toast.LENGTH_SHORT).show();
                }
                else if(judge.equals("用户名不存在")){
                    Toast.makeText(this, "用户名不存在，请注册！", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}