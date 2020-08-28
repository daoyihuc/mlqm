package com.s.shenghuang.mlqm.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;


import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.base.BaseActivity;
import com.s.shenghuang.mlqm.utils.MacUtils;
import com.s.shenghuang.mlqm.view.X5WebView;


import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author:"道翼(yanwen)"
 * @params:"代理升级"
 * @data:2019/10/15
 * @email:1966287146@qq.com
 */
public class WebViewActivity extends BaseTitleActivity {

    private String TAG=WebViewActivity.class.getSimpleName();


    private Context mContext;
    // Member fields
    private BluetoothAdapter mBtAdapter;

    //组件声明
    private LinearLayout mLinearlayout;
    private X5WebView mWebView;//浏览器

    private static final int PERMISSION_REQUEST = 1;



    private String url="file:///android_asset/html/ty.html";            //url地址
    private String pay_type = "1";
    //centertitle
    private String weburl;

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
            ,Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_APN_SETTINGS,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
    };


    public WebViewActivity() {
    }

    public static void start(Activity activity, String url) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MacUtils.initWindow(this, 0xffffffff, false, null, true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initDatass();
        initUI();
//        setContentView(mLinearlayout);

    }

    private void initDatass() {
        if(getIntent().hasExtra("url")){
            if(getIntent().getStringExtra("url")!=null&&
                    !getIntent().getStringExtra("url").equals("")){
                url = getIntent().getStringExtra("url");
            }

        }


    }

    @Override
    protected void setTitlabar() {
        super.setTitlabar();
        if(getIntent().getStringExtra("url")!=null&&
                !getIntent().getStringExtra("url").equals("")){
            titlabar.setCenterTitle("问题答案");
        }else{
            titlabar.setCenterTitle("使用说明");
        }
        titlabar.setLeftTitle("返回");
        titlabar.setLeftColor(0xff000000);
        titlabar.setLeftFontSize(18);
        titlabar.setLeftPadding(0,0,0,0);
        titlabar.setLeftMargin(MacUtils.dpto(10),0,0,0);
        titlabar.setLeftDrawable(R.drawable.ic_navigate_before_black_24dp,0x4d000000);
    }

    //初始化UI
    private void initUI() {




        LinearLayout.LayoutParams Line_prama = new LinearLayout.LayoutParams(-1, -1);
        mLinearlayout = new LinearLayout(this);
        mLinearlayout.setOrientation(LinearLayout.VERTICAL);
        mLinearlayout.setLayoutParams(Line_prama);
        mLinearlayout.setFitsSystemWindows(true);
        mLinearlayout.setClipToPadding(true);

        //webView
        mWebView = (X5WebView) new X5WebView(this,null);
        mWebView.setBackgroundColor(0xffffffff);
        LinearLayout.LayoutParams mWebView_param = new LinearLayout.LayoutParams(-1, -1);
        mWebView.setLayoutParams(mWebView_param);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        // 其他细节操作
        settings.setCacheMode(settings.LOAD_NO_CACHE); // 关闭webview中缓存
        settings.setAllowFileAccess(true); // 设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); // 支持自动加载图片
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("utf-8");// 设置编码格式
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        mWebView.setWebChromeClient(new wvcc());
        mWebView.loadUrl(url);
        mWebView.addJavascriptInterface(new MJSINTER(), "android");
        mWebView.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
                String urls = request.getUrl().toString();
                try {
                    if (urls.startsWith("http:") || urls.startsWith("https:")) {
                        view.loadUrl(urls);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls));
                        startActivity(intent);
                    }
                    return true;
                } catch (Exception e){
                    return false;
                }
            }

            //页面加载结束时

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            //界面开始加载时

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                dailog.show();

            }
            //正在加载时

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
//                dailog.show();

            }
        });

        mLinearlayout.addView(mWebView);
        relativeLayout.addView(mLinearlayout);


    }

    class  wvcc extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);

        }
        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                   JsResult arg3) {
            return super.onJsConfirm(arg0, arg1, arg2, arg3);
        }




    }




    private class MJSINTER {


    }





    @Override
    public void onBackPressed() {

        if (mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
        } else {
            finish();
        }
    }





    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
