package com.s.shenghuang.mlqm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.adpter.Adapter_shop;
import com.s.shenghuang.mlqm.bean.RechargeData;
import com.s.shenghuang.mlqm.bean.ShopBean;
import com.s.shenghuang.mlqm.demoss.BaseConfig;
import com.s.shenghuang.mlqm.http.HttpMethods;
import com.s.shenghuang.mlqm.resp.PayResponse;
import com.s.shenghuang.mlqm.resp.ShopResponse;
import com.s.shenghuang.mlqm.rxbus.RxBus;
import com.s.shenghuang.mlqm.rxbus.event.PaySuccessEvent;
import com.s.shenghuang.mlqm.rxbus.event.WechatPaySuccessEvent;
import com.s.shenghuang.mlqm.thrid.SpacesItemDecoration;
import com.s.shenghuang.mlqm.utils.MacUtils;
import com.s.shenghuang.mlqm.view.ShopAlertDialog;
import com.s.shenghuang.mlqm.view.WrapContentLinearLayoutManager;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author:"道翼(yanwen)"
 * @params:"购买服务"
 * @data:20-8-23 下午9:49
 * @email:1966287146@qq.com
 */
public class ShopActivity extends BaseTitleActivity {

    private View rootview;
    private RecyclerView recyclerView;
    private Adapter_shop adapter_shop;
    private List<ShopBean> list;
    private IWXAPI api;
    private Subscription wechatPaySuccessEvent,paySubscriver;
    private String token="7ad8b41d4b55e6db009ac1e2917b73fbfd75a2436a48a3ba4a927c59b6b160d3";
    private ShopAlertDialog alertDialog;
    private String package_id,pay_type;

    public static void start(Activity activity){
        Intent intent=new Intent();
        intent.setClass(activity,ShopActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init_childview();
        initSubscription();
        initSubscriptions();
    }

    @Override
    public void initViews() {
        super.initViews();
        rootview=getLayoutInflater().inflate(R.layout.activity_shop,relativeLayout);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        list=new ArrayList<>();
//        for (int i=0;i<2;i++){
//            ShopBean shopBean=new ShopBean();
//            list.add(shopBean);
//        }
        alertDialog=new ShopAlertDialog(this);
        Http();

    }

    @SuppressLint("WrongConstant")
    @Override
    public void init_childview() {
        super.init_childview();
        recyclerView=rootview.findViewById(R.id.shop_recycler);
        WrapContentLinearLayoutManager linearLayoutManager=new WrapContentLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter_shop=new Adapter_shop(list);
        recyclerView.setAdapter(adapter_shop);
        recyclerView.addItemDecoration(new SpacesItemDecoration(MacUtils.dpto(10)));
        adapter_shop.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.adap_shop_go){
                    package_id=list.get(position).getPackage_id();
                    alertDialog.show();
                }

            }
        });

    }

    @Override
    protected void setTitlabar() {
        super.setTitlabar();
        titlabar.setCenterTitle("服务购买");
        titlabar.setLeftTitle("返回");
        titlabar.setRightFontSize(18);
        titlabar.setRightColor(0xff000000);
        titlabar.setLeftColor(0xff000000);
        titlabar.setLeftFontSize(18);
        titlabar.setLeftPadding(0,0,0,0);
        titlabar.setLeftMargin(MacUtils.dpto(10),0,0,0);
        titlabar.setRightMargin(0,0,MacUtils.dpto(10),0);
        titlabar.setLeftDrawable(R.drawable.ic_navigate_before_black_24dp,0x4d000000);
    }
    //初始化观察者Subscription - 微信支付
    private void initSubscription() {

        wechatPaySuccessEvent = RxBus.getDefault().toObserverable(WechatPaySuccessEvent.class)
                .subscribe(new Action1<WechatPaySuccessEvent>() {
                    @Override
                    public void call(WechatPaySuccessEvent event) {

                        if(TextUtils.equals("success", event.getType())) {
                            paySuccess();
                        } else if(TextUtils.equals("cancel", event.getType())) {
                            Toast.makeText(ShopActivity.this, "支付取消", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(ShopActivity.this, "支付失败", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
    }

    private void initSubscriptions() {

        paySubscriver = RxBus.getDefault().toObserverable(PaySuccessEvent.class)
                .subscribe(new Action1<PaySuccessEvent>() {
                    @Override
                    public void call(PaySuccessEvent paySuccessEvent) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("package_id",package_id);
                        map.put("pay_type","app");
                        Log.e("pay",paySuccessEvent.getType());
                        if(!paySuccessEvent.getType().equals("0x11")){
                            map.put("coupon_id",paySuccessEvent.getType());
                        }
//                        Log.e("pay",map.get("coupon_id").toString());
                        payHttp(map);
                    }
                });
    }
    private void payHttp(Map<String,Object> map){
        Subscriber<PayResponse> subscriber=new Subscriber<PayResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                MacUtils.ToastShow(ShopActivity.this,"请求超时");
            }

            @Override
            public void onNext(PayResponse payResponse) {
                actionWxPay(payResponse.getData());
            }
        };
        HttpMethods.getInstance().get_pay_params(subscriber,token,map);
    }

    //微信支付，唤起微信app支付
    private void actionWxPay(RechargeData data) {

        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp(BaseConfig.WXCHAT_APPID);
        PayReq request = new PayReq();
        request.appId = data.getAppid();                        //微信应用ID
        request.partnerId = data.getPartnerid();                //商户号
        request.prepayId= data.getPrepayid();                   //预支付交易会话ID
        request.packageValue = "Sign=WXPay";                    //扩展字段，写死
        request.nonceStr= data.getNoncestr();                   //随机字符串
        request.timeStamp= data.getTimestamp();                 //时间戳
        request.sign= data.getSign();                           //签名
        api.sendReq(request);
    }

    //支付成功
    private void paySuccess() {


        Toast.makeText(ShopActivity.this, "支付成功", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }

    private void Http(){
        Subscriber<ShopResponse> subscriber=new Subscriber<ShopResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ShopResponse shopResponse) {

                if(shopResponse.getCode()==1){
                    MacUtils.ToastShow(ShopActivity.this,"加载完成");
                    success(shopResponse);
                }else{
                    MacUtils.ToastShow(ShopActivity.this,"请求失败");
                }

            }
        };
        HttpMethods.getInstance().shop(subscriber,token);
    }
    //success
    public void success(ShopResponse data){

        list.addAll(data.getData());
        if(adapter_shop!=null){
            adapter_shop.notifyDataSetChanged();
        }

    }
}
