package com.example.tallybook;

import android.app.Application;

import com.example.tallybook.db.DBManager;
import com.example.tallybook.registdb.RegisterDBManager;

public class UniteAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());
        RegisterDBManager.initDB(getApplicationContext());
    }
}
