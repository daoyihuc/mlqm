package com.s.shenghuang.mlqm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.base.BaseActivity;
import com.s.shenghuang.mlqm.fragment.MessageFragment;
import com.s.shenghuang.mlqm.utils.MacUtils;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-24 下午2:42
 * @email:1966287146@qq.com
 */
public class MessageActivity extends BaseActivity {

    private MessageFragment messageFragment;
    private RelativeLayout relativeLayout;

    public static void start(Activity activity){
        Intent intent=new Intent();
        intent.setClass(activity,MessageActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MacUtils.initWindow(this,0xff0F8ED8,false,null,false);
        setContentView(R.layout.activity_message);
        init_view();
    }

    @Override
    protected void init_data() {
        super.init_data();
    }

    @Override
    protected void init_view() {
        super.init_view();
        messageFragment=new MessageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.message_contains, messageFragment).commit();
    }
}
