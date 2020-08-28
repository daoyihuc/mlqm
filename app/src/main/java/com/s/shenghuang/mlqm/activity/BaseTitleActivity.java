package com.s.shenghuang.mlqm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.s.shenghuang.mlqm.base.BaseActivity;
import com.s.shenghuang.mlqm.utils.MacUtils;
import com.s.shenghuang.mlqm.view.Titlabar;


/**
 * @author 道义（daoyi）
 * @version 1.0
 * @date 2020-03-20
 * @params 导航栏和二级布局编写
 **/
public class BaseTitleActivity extends BaseActivity   {




    protected LinearLayout linearLayout;
    protected Titlabar titlabar;
    protected RelativeLayout relativeLayout;

    protected  boolean is_stutar=true;

    //布局属性
    protected LinearLayout.LayoutParams root_params,title_params,relative_params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(is_stutar){
            MacUtils.initWindows(this,0xffffffff,false,null,true);
        }

        initDatas();
        initViews();
        setContentView(linearLayout);

    }



    public  void initViews(){
        //根部局设置
        linearLayout=new LinearLayout(this);
        root_params=new LinearLayout.LayoutParams(-1,-1);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(root_params);
        linearLayout.setFitsSystemWindows(true);
        linearLayout.setClipToPadding(true);
        //导航栏设置
        titlabar=new Titlabar(this);
        title_params=new LinearLayout.LayoutParams(-1, MacUtils.dpto(44));
        titlabar.setLayoutParams(title_params);
        setTitlabar();
        relativeLayout=new RelativeLayout(this);

        relative_params=new LinearLayout.LayoutParams(-1,-1);
        relativeLayout.setLayoutParams(relative_params);
        linearLayout.addView(titlabar);
        linearLayout.addView(relativeLayout);



    }
    public  void initDatas(){}

    public void init_childview(){};

    //去除 clip
    public void setClip_padding(boolean fa){
        linearLayout.setFitsSystemWindows(fa);
        linearLayout.setClipToPadding(fa);
    }

    //初始化导航栏
    protected void setTitlabar(){
        titlabar.setCenterTitle("Title");
        titlabar.setCenterColor(0xff1D1D1D);
        titlabar.setCenterFontSize(MacUtils.dpto(12));
        titlabar.setCenterFontSize(18);
        titlabar.setCenterFontStyle(1);
        titlabar.setLeftPadding(MacUtils.dpto(20),0,0,0);
        titlabar.setLeftOnClickListener(leftonclickListener);
        titlabar.setBackGroundColor(0xffffffff);
        titlabar.addviews();
    }
    View.OnClickListener leftonclickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
    //隐藏标题栏
     public void setHideTitle(){
         titlabar.setVisibility(View.GONE);
     }
     //显示标题栏
    public void setShowTitle(){
        titlabar.setVisibility(View.VISIBLE);
    }




}
