package com.hhy.administrator.dailyedit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DailyList extends AppCompatActivity {
    ListView dailyView;
    ArrayAdapter<String> adapter;
    List<String> dailyList=new ArrayList<>();

    private Button add;
    private SQLite msqlite;
    public SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_list);

        //定义一个数据库帮助类对象
        MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(this,"Dailycontent",null);
        //创建或打开一个读写数据库
        db=myDatabaseHelper.getWritableDatabase();
        msqlite=new SQLite(db);

        dailyView= (ListView) findViewById(R.id.daily_view);
        dailyView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(DailyList.this,Edit.class);
                //得到当前某行的位置
                intent.putExtra(MyDatabaseHelper.TITEL,dailyList.get(position));
                startActivity(intent);
            }
        });

        //添加日记的按钮点击事件
        add= (Button) findViewById(R.id.d_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DailyList.this,Edit.class);
                startActivity(intent);
            }
        });
        }
    //一般执行了onStart()后就执行onResume()。
    //onResume在Activity开始与用户交互之前被调用，
    //此时Activity位于栈顶部，并接受用户输入。
        protected void onResume(){
            super.onResume();
            //在这里每开始一次就查找一次，利于数据更新
            dailyList=msqlite.QueryAll();
            adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dailyList);
            dailyView.setAdapter(adapter);
        }
}
