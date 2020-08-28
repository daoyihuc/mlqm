package com.s.shenghuang.mlqm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.fragment.ChatFragment;
import com.s.shenghuang.mlqm.utils.MacUtils;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-24 上午11:32
 * @email:1966287146@qq.com
 */
public class ChatActivity extends BaseTitleActivity{

    private ChatFragment chatFragment;
    private String toChatUsername;
    private String myUserAvatar, toUserAvatar;    //自己的头像和对方头像


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MacUtils.initWindow(this,0xff0F8ED8,false,null,false);
        setContentView(R.layout.activity_chat);
        init_childview();
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    @Override
    public void init_childview() {
        super.init_childview();
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment



        Bundle bundle =getIntent().getExtras();
        chatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void setTitlabar() {
        super.setTitlabar();
    }
}
