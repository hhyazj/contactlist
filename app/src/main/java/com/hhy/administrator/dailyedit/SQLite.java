package com.hhy.administrator.dailyedit;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/12/19.
 */
//操作数据库
public class SQLite {
    private SQLiteDatabase db;

    private int mYear;
    private int mMonth;
    private int mDay;

    //构造函数
    SQLite(SQLiteDatabase db){
        this.db=db;
    }

    //数据保存
    public boolean Save(String titel,String content){
        ContentValues values=new ContentValues();
            values.put(MyDatabaseHelper.TITEL,titel);
            values.put(MyDatabaseHelper.CONTENT,content);

        long result =db.insert(MyDatabaseHelper.TABLE_NAME,null,values);
        return result>0;
    }

    //数据删除
    public boolean Delete(String titel){
        long jieguo =db.delete(MyDatabaseHelper.TABLE_NAME,MyDatabaseHelper.TITEL+"=?",
                new String[]{titel});
        return jieguo>0;
    }

    //根据名字查找其数据
    public String Querycontents(String titelo){
        //以下是你要查的内容
        String []colunmso={MyDatabaseHelper.CONTENT};
        //query(表名，要查找的内容，查找的条件，)
        Cursor cursoro=db.query(MyDatabaseHelper.TABLE_NAME,colunmso,
                MyDatabaseHelper.TITEL+"=?",new String[]{titelo},null,null,null);
        String contents="";
        if (cursoro.getCount()>0){
            while(cursoro.moveToNext()){
        contents=cursoro.getString(cursoro.getColumnIndex(MyDatabaseHelper.CONTENT));
                Log.e("SQLite",contents);
        }}

        cursoro.close();
        return contents;
    }

    //查找所有数据，存如列表
    public List<String> QueryAll(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR); //获取当前年份
        mMonth = c.get(Calendar.MONTH);//获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码

        List<String> list=new ArrayList<>();
        Cursor cursor;
        String []colunms={MyDatabaseHelper.TITEL,MyDatabaseHelper.CONTENT};
        cursor=db.query(MyDatabaseHelper.TABLE_NAME,colunms,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do{
                list.add(+mYear+"/"+mMonth+"/"+mDay+
                "\n"+cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.TITEL))+"\n");
            }while(cursor.moveToNext());
        }
        return  list;
    }

}
