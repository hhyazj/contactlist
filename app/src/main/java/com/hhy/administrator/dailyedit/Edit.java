package com.hhy.administrator.dailyedit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/12/20.
 */

public class Edit extends Activity implements View.OnClickListener{
    private Button yes,back;
    private EditText titel,contents;
    private  SQLite msql;
    private TextView delete;
    private SQLiteDatabase db;
    private String titeltext;
    private String contenttext;
    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.edit);

        //定义一个数据库帮助类对象
        MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(this,"Dailycontent",null);
        //创建或打开一个读写数据库
        db=myDatabaseHelper.getWritableDatabase();
        msql=new SQLite(db);

        yes= (Button) findViewById(R.id.yes);
        back= (Button) findViewById(R.id.back);
        titel= (EditText) findViewById(R.id.e_titel);
        contents= (EditText) findViewById(R.id.e_content);
        delete= (TextView) findViewById(R.id.tv_del);

        yes.setOnClickListener(this);
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        titeltext=titel.getText().toString();
        contenttext=contents.getText().toString();

        //定义一个数组存放从当前被点击的某行获取的数据
        String shuju;
        Intent intent=getIntent();
        if (intent.getStringExtra(MyDatabaseHelper.TITEL)!=null){

            //定义一个数组存放从当前被点击的某行获取的数据
            shuju=intent.getStringExtra(MyDatabaseHelper.TITEL);

            //将传过来的数据排除换行内容，只取标题
            String []s=shuju.split("\n");
            String titels=s[1];

            //根据标题，调用查找函数查找日记内容
            String content=msql.Querycontents(titels);

            //设置编辑页面的内容
            titel.setText(s[1]);
            contents.setText(content);
        }
    }
    public void onClick(View view) {
        switch(view.getId()){

            //返回按钮点击事件
            case R.id.back:
                finish();
                break;

            //保存按钮点击事件
            case R.id.yes:
                titeltext=titel.getText().toString();
                contenttext=contents.getText().toString();
                if ((!TextUtils.isEmpty(titeltext))&& (!TextUtils.isEmpty(contenttext))){
                if (msql.Save(titeltext,contenttext)){
                    Toast.makeText(this,"保存成功！",Toast.LENGTH_SHORT).show();
                }}else{
                    Toast.makeText(this,"请填写完整标题和内容",Toast.LENGTH_SHORT).show();
                }
                break;

            //删除按钮点击事件
            case R.id.tv_del:
                titeltext=titel.getText().toString();
                if (msql.Delete(titeltext)){
                    Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
