package com.xfeng.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerview;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new UserAdapter(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);

        findViewById(R.id.button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        addTestData();
        try {
            //3.获取对应实体类到数据库数据(get datas from database)
            ArrayList list = DataBaseUtil.getInstance(this).getList(UserBean.class);
            adapter.refresh(list);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void addTestData(){
        Random rand = new Random();
        UserBean userBean = new UserBean();
        userBean.setName("Name-" + ('a' + rand.nextInt(26 * 2 -1)));
        userBean.setAge(1 + rand.nextInt(200));
        userBean.setCity("city");
        //2.添加实体对象到数据库(add your object into database)
        DataBaseUtil.getInstance(this).insert(userBean);
    }
}
