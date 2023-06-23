package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;

import com.example.app.adapter.MyAdapter;
import com.example.app.fragment.Page1Fragment;
import com.example.app.fragment.Page2Fragment;
import com.example.app.fragment.Page3Fragment;
import com.example.app.fragment.Page4Fragment;
import com.example.app.fragment.Page5Fragment;
import com.example.app.util.MyDatabaseHelper;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout myTabLayout;
    private ViewPager2 myPager2;
    List<String> titles=new ArrayList<>();
    public List<Fragment> fragments=new ArrayList<>();


    private Page1Fragment page1Fragment;
    private Page2Fragment page2Fragment;
    private Page3Fragment page3Fragment;
    private Page4Fragment page4Fragment;
    private Page5Fragment page5Fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题
        setContentView(R.layout.activity_main);
//        导入数据库
        openDatabase();

        myTabLayout = findViewById(R.id.myTabLayout);
        myPager2 = findViewById(R.id.myPager2);

        InitFragment initFragment = new InitFragment();
        initFragment.init();
        page1Fragment = initFragment.getPage1();
        page2Fragment = initFragment.getPage2();
        page3Fragment = initFragment.getPage3();
        page4Fragment = initFragment.getPage4();
        page5Fragment = initFragment.getPage5();

        //添加标题
        titles.add("简介");
        titles.add("金牌榜");
        titles.add("赛程");
        titles.add("论坛");
        titles.add("个人信息");

        //添加Fragment进去
        fragments.add(page1Fragment);
        fragments.add(page2Fragment);
        fragments.add(page3Fragment);
        fragments.add(page4Fragment);
        fragments.add(page5Fragment);

        Intent intent=getIntent();
        String account = intent.getStringExtra("account");
        String name = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String sex = intent.getStringExtra("sex");

// 向fragment传递数据
        Bundle bundle=new Bundle();
        bundle.putString("account",account);
        bundle.putString("username",name);
        bundle.putString("password",password);
        bundle.putString("sex",sex);

        page5Fragment.setArguments(bundle);


        //实例化适配器
        MyAdapter myAdapter=new MyAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        //设置适配器
        myPager2.setAdapter(myAdapter);
        //TabLayout和Viewpager2进行关联
        new TabLayoutMediator(myTabLayout, myPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
            }
        }).attach();
    }

    public void openDatabase(){
        MyDatabaseHelper myHelper = new MyDatabaseHelper(MainActivity.this);
        try {
            myHelper.CopyDBFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}