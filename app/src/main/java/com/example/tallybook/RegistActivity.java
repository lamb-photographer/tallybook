package com.example.tallybook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tallybook.registdb.RegistBean;
import com.example.tallybook.registdb.RegisterDBManager;


public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
    EditText usernameEt,passwordEt,passConfirmEt;
    Button registBtn;
    ImageView backIv;
    String UserName,PW,PWCon;
    RegistBean registBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        usernameEt = findViewById(R.id.regist_username);
        passwordEt = findViewById(R.id.regist_password);
        passConfirmEt = findViewById(R.id.regist_passwordConfirm);
        registBtn = findViewById(R.id.regist_register_btn);
        backIv = findViewById(R.id.regist_back);
        registBtn.setOnClickListener(this);
        backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        UserName = usernameEt.getText().toString().trim();
        PW = passwordEt.getText().toString();
        PWCon = passConfirmEt.getText().toString();
        switch(view.getId()){
            case R.id.regist_back:
                Intent it =new Intent(this,LoginActivity.class);//跳转
                startActivity(it);
                break;
            case R.id.regist_register_btn:
                showMessage();
                break;
        }
    }

    private void showMessage() {
        if(TextUtils.isEmpty(UserName)){
            Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
        }else {
            if (TextUtils.isEmpty(PW)){
                Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            }else {
                if (TextUtils.isEmpty(PWCon)) {
                    Toast.makeText(this, "确认密码不能为空！", Toast.LENGTH_SHORT).show();
                }else{
                    boolean exist = RegisterDBManager.findUsername(UserName);
                    if (exist) {
                        Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (PW.equals(PWCon)) {
                            registBean = new RegistBean();
                            registBean.setUsername(UserName);
                            registBean.setPassword(PW);
                            RegisterDBManager.insertItemToRegistertb(registBean);

                            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "前后密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }

    }
}