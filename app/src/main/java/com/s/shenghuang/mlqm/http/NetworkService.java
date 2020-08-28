package com.s.shenghuang.mlqm.http;


import com.s.shenghuang.mlqm.base.BaseResponse;
import com.s.shenghuang.mlqm.bean.ShopBean;
import com.s.shenghuang.mlqm.bean.UploadResponse;
import com.s.shenghuang.mlqm.resp.CardResponse;
import com.s.shenghuang.mlqm.resp.PayResponse;
import com.s.shenghuang.mlqm.resp.QuestionResponse;
import com.s.shenghuang.mlqm.resp.ShopResponse;
import com.s.shenghuang.mlqm.resp.SubjectResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 请求服务接口类
 */
public interface NetworkService{

    //套餐列表接口
    @Headers("XX-Device-Type:android")
    @GET("souti/get_package_list")
    Observable<ShopResponse> shop(@Header("XX-Token") String token);


    //优惠卷列表
    @Headers("XX-Device-Type:android")
    @GET("souti/couponlist")
    Observable<CardResponse> card_list(@Header("XX-Token") String token,@Header("status") String status);


    //文件上传
    @Multipart
    @Headers("XX-Device-Type:android")
    @POST("user/upload/one")
    Observable<UploadResponse> upload(@Part List<MultipartBody.Part> parts,@Header("XX-Token") String token);

    @Headers("XX-Device-Type:android")
    @POST("souti/subject")
    Observable<SubjectResponse> subject(@Header("XX-Token") String token);


    //souti/add
    @FormUrlEncoded
    @Headers("XX-Device-Type:android")
    @POST("souti/add")
    Observable<BaseResponse> add(@Header("XX-Token") String token, @FieldMap() Map<String,Object> map);


    //https://mlqms.shenghuang365.com/api/souti/findlist?order=-find_id
    //souti/findlist

    @Headers("XX-Device-Type:android")
    @GET("souti/findlist")
    Observable<QuestionResponse> question(@Header("XX-Token") String token);

    //souti/get_conpon_list
    @Headers("XX-Device-Type:android")
    @GET("souti/get_conpon_list")
    Observable<CardResponse> get_conpon_list(@Header("XX-Token") String token);


    //souti/get_conpon_list
    @Headers("XX-Device-Type:android")
    @GET("souti/get_pay_params")
    Observable<PayResponse> get_pay_params(@Header("XX-Token") String token, @QueryMap Map<String,Object> map);
}

