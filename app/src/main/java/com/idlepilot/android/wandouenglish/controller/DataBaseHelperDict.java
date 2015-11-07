package com.idlepilot.android.wandouenglish.controller;

/**
 * Created by xuzywozz on 2015/10/24.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class DataBaseHelperDict extends SQLiteOpenHelper
{

    public Context mContext = null;
    public String tableName = null;
    public static int VERSION = 1;

    public static final String DATABASE_NAME = "dict.db";

    public DataBaseHelperDict(Context context, String name, CursorFactory factory,
                              int version)
    {
        super(context, DATABASE_NAME, factory, version);
        // TODO Auto-generated constructor stub
        mContext = context;
        tableName = name;
    }

    public DataBaseHelperDict(Context context, String name, CursorFactory factory)
    {
        this(context, DATABASE_NAME, factory, VERSION);
        mContext = context;
        tableName = name;
    }

    public DataBaseHelperDict(Context context, String name)
    {
        this(context, DATABASE_NAME, null);
        mContext = context;
        tableName = name;
    }

    public DataBaseHelperDict(Context context)
    {
        this(context, DATABASE_NAME, null);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // TODO Auto-generated method stub
        db.execSQL("create table dict(word text,pse text,prone text,psa text,prona text," +
                "interpret text, sentorig text, senttrans text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {
        // TODO Auto-generated method stub

    }

}