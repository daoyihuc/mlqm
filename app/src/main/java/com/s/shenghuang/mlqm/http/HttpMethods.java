package com.s.shenghuang.mlqm.http;

import android.util.Log;

import com.s.shenghuang.mlqm.base.BaseResponse;
import com.s.shenghuang.mlqm.bean.UploadResponse;
import com.s.shenghuang.mlqm.interfaces.Constants;
import com.s.shenghuang.mlqm.resp.CardResponse;
import com.s.shenghuang.mlqm.resp.PayResponse;
import com.s.shenghuang.mlqm.resp.QuestionResponse;
import com.s.shenghuang.mlqm.resp.ShopResponse;
import com.s.shenghuang.mlqm.resp.SubjectResponse;


import java.security.Key;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenjing on 2017/6/27.
 */
public class HttpMethods{
    //TODO 7.13 fqj添加原始url
    public static final String MAIN_ENGINE = Constants.MAIN_URL;
    //设置超时时间
    private static final int DEFAULT_TIMEOUT = 30;
    private final Retrofit retrofit;
    private final NetworkService networkService;
    //构造方法私有
    private HttpMethods(){
        //添加日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
            @Override
            public void log(String message){
                //TODO 打印retrofit日志(Json格式数据)
                //TODO 需要的时候再开启
                Log.d("Log", "JSON = " + message);
            }

        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //初始化OKHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置超时时间
                         .addInterceptor(new CommonParamInterceptor())
                         .addInterceptor(loggingInterceptor)//添加日志拦截器
//                         .addInterceptor(new ResponseCookieInterceptor())//token判断
                         .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                         .sslSocketFactory(getSSLSocketFactory(),new CustomTrustManager())//支持https
                         .hostnameVerifier(getHostnameVerifier())//信任所有
                         .build();
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())/*Gason转换器*/
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//这样一来我们定义的service返回值就不在是一个Call了，而是一个Observable
                .baseUrl(MAIN_ENGINE)
                .build();
        networkService = retrofit.create(NetworkService.class);
    }
    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }
    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }
    public static HostnameVerifier getHostnameVerifier(){
        HostnameVerifier   hostnameVerifier= new HostnameVerifier(){
            public boolean verify(String hostname, SSLSession session){
                return true;
            }
        };
        return hostnameVerifier;
    }
    public static SSLSocketFactory getSSLSocketFactory(){
        SSLSocketFactory ssfFactory = null;
        try{
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new CustomTrustManager()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        }catch (Exception e){

        }
        return ssfFactory;
    }
    //发生绑定注册关系的方法（被观察者，观察者）创建RxJava最简单的步骤，一、创建被观察者对象，二、创建观者对象，三创建订阅关系；
    private void toSubscribe(Observable observable, Subscriber subscriber){
        observable.subscribeOn(Schedulers.io())//指定订阅关系发生在IO线程
                .unsubscribeOn(Schedulers.io())//指定解绑发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())//指回调发生在主线程
                .subscribe(subscriber);//创建订阅关系
    }
    /**
     * 方法样例
     *   public void login(Subscriber<LoginResponse> subscriber, Map<String, Object> map) {
         Observable observable = networkService.login(map);
         toSubscribe(observable, subscriber);
         }
     ........................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................
     */
    //套餐列表接口
    public void shop(Subscriber<ShopResponse> subscriber, String token) {
        Observable observable = networkService.shop(token);
        toSubscribe(observable, subscriber);
    }
    //优惠卷列表
    public void card_list(Subscriber<CardResponse> subscriber, String token, String status) {
        Observable observable = networkService.card_list(token,status);
        toSubscribe(observable, subscriber);
    }

    //文件上传
    public void upload(Subscriber<UploadResponse> subscriber, List<MultipartBody.Part> parts,String token){
        Observable observable = networkService.upload(parts,token);
        toSubscribe(observable,subscriber);
    }

    //年级课程
    public void subject(Subscriber<SubjectResponse> subscriber, String token){
        Observable observable = networkService.subject(token);
        toSubscribe(observable,subscriber);
    }

    //提交题目
    public void add(Subscriber<BaseResponse> subscriber, String token,Map<String,Object> map){
        Observable observable = networkService.add(token,map);
        toSubscribe(observable,subscriber);
    }

    //我的提问
    public void question(Subscriber<QuestionResponse> subscriber, String token){
        Observable observable = networkService.question(token);
        toSubscribe(observable,subscriber);
    }

    //我的提问
    public void get_conpon_list(Subscriber<CardResponse> subscriber, String token){
        Observable observable = networkService.get_conpon_list(token);
        toSubscribe(observable,subscriber);
    }
    //支付
    public void get_pay_params(Subscriber<PayResponse> subscriber, String token, Map<String,Object> map){
        Observable observable = networkService.get_pay_params(token,map);
        toSubscribe(observable,subscriber);
    }
}

