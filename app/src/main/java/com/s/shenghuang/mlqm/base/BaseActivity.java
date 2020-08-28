package com.s.shenghuang.mlqm.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author:"道翼(yanwen)"
 * @params:"baseactivity"
 * @data:20-8-22 上午8:54
 * @email:1966287146@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //初始化数据
    protected  void init_data(){}
    // 初始化界面
    protected  void init_view(){}
}
