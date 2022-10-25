package com.example.tallybook.registdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.tallybook.db.DBManager;
import com.example.tallybook.db.DBOpenHelper;

public class RegisterDBManager {
    private static SQLiteDatabase registerdb;
    public static void initDB(Context context){
        RegisterDBOpenHelper helper = new RegisterDBOpenHelper(context);  //得到帮助类对象
        registerdb = helper.getWritableDatabase();      //得到数据库对象
    }
    //插入数据
    public static void insertItemToRegistertb(RegistBean bean){
        ContentValues values = new ContentValues();
        values.put("username",bean.getUsername());
        values.put("password",bean.getPassword());
        DBManager.makeAccounttb(bean.getUsername());
        registerdb.insert("registertb",null,values);
    }
    //判断用户名是否存在（注册）
    public static boolean findUsername(String name){
        //默认没有该数据
        boolean result=false;
        Cursor cursor=registerdb.query("registertb",new String[]{"username"},null,null,null,null,null);
        //如果游标能往下移动
        while (cursor.moveToNext()){
            //遍历Cursor对象，并且跟传入进行比较,如果相同就返回true,说明数据库存在该数据
            String UserName = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            if(name.equals(UserName)){
                result=true;
            }
        }
        cursor.close();
        return result;
    }
    //判断输入用户名和密码是否相同(登陆页面)
    public static String judge(String username,String password){
        String s = new String();
        Cursor cursor=registerdb.query("registertb",new String[]{"username","password"},null,null,null,null,"id desc");
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String pw = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            if(TextUtils.equals(username,name)){
                if(TextUtils.equals(password,pw)){
                    s = new String("登录成功");
                    break;
                }else{
                    s= new String("密码错误");
                    break;
                }
            }else{
                s= new String("用户名不存在");
            }
        }
        return s;
    }
}
