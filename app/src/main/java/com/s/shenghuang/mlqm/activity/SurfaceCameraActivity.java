package com.s.shenghuang.mlqm.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.adpter.Adapter_Camera;
import com.s.shenghuang.mlqm.base.BaseResponse;
import com.s.shenghuang.mlqm.bean.SubjectBean;
import com.s.shenghuang.mlqm.bean.UploadResponse;
import com.s.shenghuang.mlqm.http.HttpMethods;
import com.s.shenghuang.mlqm.resp.SubjectResponse;
import com.s.shenghuang.mlqm.utils.ImageUtils;
import com.s.shenghuang.mlqm.utils.MacUtils;
import com.s.shenghuang.mlqm.view.CameraSurfaceView;
import com.s.shenghuang.mlqm.view.WrapContentLinearLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

public class SurfaceCameraActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SurfaceCameraActivity";

    private ImageView mCloseIv;
    private ImageView mSwitchCameraIv;
    private ImageView mTakePictureIv;
    private ImageView mPictureIv;
    private CameraSurfaceView mCameraView;
    private RecyclerView recyclerView;
    private Adapter_Camera adapter_camera;
    private CameraProxy mCameraProxy;
    private Button camera_delete,camera_sumbit;
    private List<String> list;//图片存粗
    private List<SubjectBean> Lists_year;//年级学科
    private String subject_id="";
    private OptionsPickerView pvOptions;
    List<MultipartBody.Part> parts = new ArrayList<>();

    private String token="7ad8b41d4b55e6db009ac1e2917b73fbfd75a2436a48a3ba4a927c59b6b160d3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MacUtils.initWindow(this,0xff0F8ED8,false,null,false);
        setContentView(R.layout.activity_surface_camera);
        initView();
        Http_sub();
    }

    private void initView() {
        list=new ArrayList<>();
        Lists_year=new ArrayList<>();

        mCloseIv = findViewById(R.id.toolbar_close_iv);
        mCloseIv.setOnClickListener(this);
        mSwitchCameraIv = findViewById(R.id.toolbar_switch_iv);
        mSwitchCameraIv.setOnClickListener(this);
        mTakePictureIv = findViewById(R.id.take_picture_iv);
        mTakePictureIv.setOnClickListener(this);
        mPictureIv = findViewById(R.id.picture_iv);
        mPictureIv.setOnClickListener(this);
        mPictureIv.setImageBitmap(ImageUtils.getLatestThumbBitmap());
        mCameraView = findViewById(R.id.camera_view);
        recyclerView=findViewById(R.id.image_recycler);
        camera_delete=findViewById(R.id.camera_delete);
        camera_sumbit=findViewById(R.id.camera_sumbit);
        camera_delete.setOnClickListener(this);
        camera_sumbit.setOnClickListener(this);

        mCameraProxy = mCameraView.getCameraProxy();
        setRecyclerView();
        setPvOptions();


    }
    public void setPvOptions(){
        pvOptions= new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int position, int options2, int options3, View v) {
                subject_id=Lists_year.get(position).getSubject_id();
               MacUtils.ToastShow(SurfaceCameraActivity.this,Lists_year.get(position).getSubject_name());
                add();
            }
        })
                .setTitleText("请选择课程")//设置标题
                .setTitleSize(17)//设置标题字体大小
                .setTitleColor(Color.parseColor("#ff000000"))//设置标题字体颜色
                .setCancelColor(Color.parseColor("#ffE90505"))//设置取消字体颜色
                .setSubmitColor(Color.parseColor("#3888E8"))//设置确定字体颜色
                .setDividerColor(Color.parseColor("#E6E6E6"))//设置分割线的颜色
                .setTextColorCenter(Color.parseColor("#3888E8")) //设置选中项文字颜色
                .setContentTextSize(18)//设置内容文字的大小
                .build();
        pvOptions.setPicker(Lists_year);//一级选择器
    }


    @SuppressLint("WrongConstant")
    public void setRecyclerView(){
        WrapContentLinearLayoutManager linearLayoutManager=new WrapContentLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter_camera=new Adapter_Camera(SurfaceCameraActivity.this,list);
        recyclerView.setAdapter(adapter_camera);
        adapter_camera.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(list!=null){
                    if(list.size()>position){
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //关闭
            case R.id.toolbar_close_iv:
                finish();
                break;
                //镜头切换
            case R.id.toolbar_switch_iv:
                mCameraProxy.switchCamera();
                mCameraProxy.startPreview(mCameraView.getHolder());
                break;
                //拍照
            case R.id.take_picture_iv:
                if(list.size()<10){
                    mCameraProxy.takePicture(mPictureCallback);
                }else{
                    MacUtils.ToastShow(this,"图片超过数量了哦");
                }

                break;
            case R.id.picture_iv:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(intent);
                break;
                //删除
            case R.id.camera_delete:
                if(list!=null&&list.size()>0){
                    list.remove(list.size()-1);
                    adapter_camera.notifyDataSetChanged();
                }
                break;
            //提交
            case R.id.camera_sumbit:
                pvOptions.show();
                break;


        }
    }

    private final Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            mCameraProxy.startPreview(mCameraView.getHolder()); // 拍照结束后继续预览
            new ImageSaveTask().execute(data); // 保存图片
        }
    };
    public void upload(String src){

        File file = new File(src);
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);   //说明该文件为图片类型
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        parts.add(part);

        Subscriber<UploadResponse> subscriber=new Subscriber<UploadResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                MacUtils.ToastShow(SurfaceCameraActivity.this,"请求超时");
            }

            @Override
            public void onNext(UploadResponse uploadResponse) {

                if(uploadResponse.getCode()==1){
                    list.add((String) uploadResponse.getData().get("url"));
                    adapter_camera.notifyDataSetChanged();
                }else{
                    MacUtils.ToastShow(SurfaceCameraActivity.this,"上传失败");
                }
            }
        };
        HttpMethods.getInstance().upload(subscriber,parts,token);
    }

    //年级科目
    public void Http_sub(){

        Subscriber<SubjectResponse> subscriber=new Subscriber<SubjectResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                MacUtils.ToastShow(SurfaceCameraActivity.this,"请求超时");
            }

            @Override
            public void onNext(SubjectResponse subjectResponse) {

                if(subjectResponse.getCode()==1){
                    Lists_year.addAll(subjectResponse.getData());
                }else{
                    MacUtils.ToastShow(SurfaceCameraActivity.this,"获取失败");
                }
            }
        };
        HttpMethods.getInstance().subject(subscriber,token);
    }
    //提交题目
    public void add(){

        Map<String,Object> map=new HashMap<>();
        map.put("subject_id",subject_id);
        map.put("form_id","0");
        for(int i=0;i<list.size();i++){
            if(i==0){
                map.put("image_path",list.get(i));
            }else{
                map.put("image_path"+i+1,list.get(i));
            }
        }


        Subscriber<BaseResponse> subscriber=new Subscriber<BaseResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                MacUtils.ToastShow(SurfaceCameraActivity.this,"请求超时");
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                Log.e(SurfaceCameraActivity.TAG,baseResponse.getMsg());
                MacUtils.ToastShow(SurfaceCameraActivity.this,baseResponse.getMsg());
            }
        };
        HttpMethods.getInstance().add(subscriber,
                token,map);

    }





    private class ImageSaveTask extends AsyncTask<byte[], Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(byte[]... bytes) {
            long time = System.currentTimeMillis();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes[0], 0, bytes[0].length);
            Log.d(TAG, "BitmapFactory.decodeByteArray time: " + (System.currentTimeMillis() - time));
            int rotation = mCameraProxy.getLatestRotation();
            time = System.currentTimeMillis();
            Bitmap rotateBitmap = ImageUtils.rotateBitmap(bitmap, rotation, mCameraProxy.isFrontCamera(), true);
            Log.d(TAG, "rotateBitmap time: " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
            String s = ImageUtils.saveBitmaps(rotateBitmap);
            upload(s);
            Log.d(TAG, "saveBitmap time: " + (System.currentTimeMillis() - time));
            rotateBitmap.recycle();
            return ImageUtils.getLatestThumbBitmap();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mPictureIv.setImageBitmap(bitmap);
            adapter_camera.notifyDataSetChanged();
        }
    }
}
