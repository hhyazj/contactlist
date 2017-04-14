package com.hhy.administrator.dailyedit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/19.
 */
//数据库帮助类
public class MyDatabaseHelper extends SQLiteOpenHelper{
    private Context mContext;
    public  final static String TABLE_NAME="Daily";
    public final static String TITEL="titel";
    public  final static String CONTENT="content";
    public final static String CREATE_DAILY="create table Daily("
            + "id integer primary key autoincrement,"
            +"titel text,"
            +"content text)";
    //构造函数
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 1);
        mContext=context;
    }

    @Override
    //构造数据库
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_DAILY);
        Toast.makeText(mContext,"alter",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
