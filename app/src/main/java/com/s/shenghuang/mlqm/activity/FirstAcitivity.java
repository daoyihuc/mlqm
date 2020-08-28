package com.s.shenghuang.mlqm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.s.shenghuang.mlqm.MainActivity;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.base.BaseActivity;
import com.s.shenghuang.mlqm.demoss.DemoHelper;
import com.s.shenghuang.mlqm.utils.MacUtils;

import org.w3c.dom.Text;

/**
 * @author:"道翼(yanwen)"
 * @params:"代理升级"
 * @data:20-8-22 上午8:50
 * @email:1966287146@qq.com
 */
public class FirstAcitivity extends BaseActivity {

    private TextView tes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MacUtils.initWindow(this,0xffffffff,false,null,true);
        setContentView(R.layout.activity_first);
        init_data();
        init_view();
    }

    @Override
    protected void init_data() {
        super.init_data();
//        goMain();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    goMain();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
//        thread.start();
    }

    @Override
    protected void init_view() {
        super.init_view();

        tes=findViewById(R.id.test);
        tes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_HX("741258","123456");
                ActivityOptionsCompat activityOptions= ActivityOptionsCompat.makeSceneTransitionAnimation(FirstAcitivity.this);
                Intent intent=new Intent();
                intent.setClass(FirstAcitivity.this,MainActivity.class);
                startActivity(intent,activityOptions.toBundle());
            }
        });
    }
    private void goMain(){





//        Intent intent = new Intent();
//        intent.setClass(FirstAcitivity.this, MainActivity.class);
//        startActivity(intent);
    }
    //登录环形
    private void login_HX(String uniqueId,String pwd){
        EMClient.getInstance().login(uniqueId, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {

                // 将自己服务器返回的环信账号、昵称和头像URL设置到帮助类中。
                DemoHelper.getInstance().setCurrentUserName(uniqueId); // 环信Id
//                DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(name);
//                DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(avatar);
                Log.e("Login","开始跳转");
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

            }

            @Override
            public void onError(int i, String s) {
                Log.e("Login",s);
//                MacUtils.ToastShow(FirstAcitivity.this,"失败");
            }

            @Override
            public void onProgress(int i, String s) {
                Log.e("Login",s);
//                MacUtils.ToastShow(FirstAcitivity.this,"登录中");
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
