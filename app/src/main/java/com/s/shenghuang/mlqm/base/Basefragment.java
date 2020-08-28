package com.s.shenghuang.mlqm.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import rx.Subscriber;

/**
 * @author 道义（daoyi）
 * @version 1.0
 * @date 2020-03-12
 * @params  fragment 基础类
 **/
public abstract class Basefragment extends Fragment {

    private View view;
    private Context context;
    protected boolean isNeedMsgcount =true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=loadView();
        initData();
        initUI();
        http();
        return view;
    }

    //view 的加载
    public abstract View loadView();
    //  数据加载
    public abstract void initData();
    // ui  初始化
    public abstract void initUI();
    //获取view对象
    public View getview(){
        return view;
    }


    //未读消息数量

    @Override
    public void onStart() {
        super.onStart();

        http();
    }

    private void http(){
    }

    @Override
    public void onResume() {
        super.onResume();
        http();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        http();
    }
}
