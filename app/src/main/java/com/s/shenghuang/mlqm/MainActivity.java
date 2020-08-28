package com.s.shenghuang.mlqm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.s.shenghuang.mlqm.activity.CouponActivity;
import com.s.shenghuang.mlqm.activity.MessageActivity;
import com.s.shenghuang.mlqm.activity.QuestionActivity;
import com.s.shenghuang.mlqm.activity.ShopActivity;
import com.s.shenghuang.mlqm.activity.SurfaceCameraActivity;
import com.s.shenghuang.mlqm.activity.WebViewActivity;
import com.s.shenghuang.mlqm.base.BaseActivity;
import com.s.shenghuang.mlqm.utils.MacUtils;
import com.s.shenghuang.mlqm.utils.requestPermissionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private ImageView main_camera,main_message;
    //使用说明 我的优惠卷 我的提问 购买服务
    private CardView home_clip_vedio,home_clip_image,home_detach_music,home_compound_VM;

    private static int REQUEST_CAMERA_1 = 1;
    private static int REQUEST_CAMERA_2 = 2;
    private String mFilePath;
    private static final int PERMISSION_REQUEST = 0x11;

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
//            Manifest.permission.SYSTEM_ALERT_WINDOW,
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MacUtils.initWindow(this,0xff0F8ED8,false,null,false);
        setContentView(R.layout.activity_main);
        getWindow().setEnterTransition(setEnterTransition());
//        init_data();
        init_view();
    }

    @Override
    protected void init_data() {
        super.init_data();


    }

    @Override
    protected void init_view() {
        super.init_view();

        main_camera=findViewById(R.id.main_camera);
        main_message=findViewById(R.id.main_message);
        home_clip_vedio=findViewById(R.id.home_clip_vedio);
        home_clip_image=findViewById(R.id.home_clip_image);
        home_detach_music=findViewById(R.id.home_detach_music);
        home_compound_VM=findViewById(R.id.home_compound_VM);

        home_clip_vedio.setOnClickListener(this);
        home_clip_image.setOnClickListener(this);
        home_detach_music.setOnClickListener(this);
        home_compound_VM.setOnClickListener(this);
        main_camera.setOnClickListener(this);
        main_message.setOnClickListener(this);



    }

    //设置transition
    public Transition setEnterTransition(){
        Transition transition= TransitionInflater.from(this).inflateTransition(R.transition.home_transition);

        return transition;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            //使用说明
            case R.id.home_clip_vedio:
                WebViewActivity.start(this,"");
                break;
                //我的优惠卷
            case R.id.home_clip_image:
                CouponActivity.start(this);
                break;
                //我的提问
            case R.id.home_detach_music:
                QuestionActivity.start(this);
                break;
                //购买服务
            case R.id.home_compound_VM:
                ShopActivity.start(this);
                break;
                //相机拍摄
            case R.id.main_camera:
                requestPermissionUtils requestPermissionUtils=new requestPermissionUtils(this);
                boolean requests = requestPermissionUtils.requests(0x11, permissions);
                if(requests){
//                    openCamera_1();
                    upimg();
                }else{
                    requestPermissionUtils.request(0x11, permissions);
                }
                break;
                //消息
            case R.id.main_message:
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
//                EMMessage message = EMMessage.createTxtSendMessage("5552", "789456");
//                EMClient.getInstance().chatManager().sendMessage(message);
                MessageActivity.start(this);

                break;


        }
    }
    // 提交问题
    private void upimg(){
        Intent intent = new Intent();// 启动系统相机
        intent.setClass(this, SurfaceCameraActivity.class);
        startActivity(intent);
    }

    // 拍照并显示图片
    private void openCamera_1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        startActivityForResult(intent, REQUEST_CAMERA_1);
    }

    // 拍照后存储并显示图片
    private void openCamera_2() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        Uri photoUri = Uri.fromFile(new File(mFilePath)); // 传递路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
        startActivityForResult(intent, REQUEST_CAMERA_2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA_1) { // 判断请求码是否为REQUEST_CAMERA,如果是代表是这个页面传过去的，需要进行获取
                Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
            } else if (requestCode == REQUEST_CAMERA_2) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(mFilePath); // 根据路径获取数据
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();// 关闭流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==0x11){
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted. Continue the action or workflow
                // in your app.
                openCamera_1();

            }  else {
                // Explain to the user that the feature is unavailable because
                // the features requires a permission that the user has denied.
                // At the same time, respect the user's decision. Don't link to
                // system settings in an effort to convince the user to change
                // their decision.
                //向用户说明该功能不可用，因为//功能需要用户拒绝的权限。 //同时，尊重用户的决定。
                // 不要链接到//系统设置，以说服用户更改自己的决定。
                MacUtils.ToastShow(this,"未开启相机权限");
            }
        }
    }
}
