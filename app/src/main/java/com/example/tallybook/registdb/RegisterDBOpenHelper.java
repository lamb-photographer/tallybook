package com.example.tallybook.registdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RegisterDBOpenHelper extends SQLiteOpenHelper {

    public RegisterDBOpenHelper(@Nullable Context context){super(context,"register.db" , null, 1);}

    @Override
    public void onCreate(SQLiteDatabase registerdb) {
        String sql="create table registertb(id integer primary key autoincrement,username varchar(80),password varchar(80))";
        registerdb.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
