package com.s.shenghuang.mlqm.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.adpter.Adapter_shop;
import com.s.shenghuang.mlqm.adpter.Adpter_ShopCard;
import com.s.shenghuang.mlqm.bean.Card_bean;
import com.s.shenghuang.mlqm.http.HttpMethods;
import com.s.shenghuang.mlqm.resp.CardResponse;
import com.s.shenghuang.mlqm.rxbus.RxBus;
import com.s.shenghuang.mlqm.rxbus.event.PaySuccessEvent;
import com.s.shenghuang.mlqm.thrid.SpacesItemDecoration;
import com.s.shenghuang.mlqm.utils.MacUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-27 下午4:09
 * @email:1966287146@qq.com
 */
public class ShopAlertDialog extends Dialog {


    private Window window;
    //设置弹窗比例
    private float windowsize=0.8f;
    //上下文对象
    private Context context;
    //组建声明
    private RecyclerView recyclerView;
    private Button sumbit;
    private Adpter_ShopCard adpter_shopCard;
    private List<Card_bean> list;
    private String coupon_id="";
    private String token="7ad8b41d4b55e6db009ac1e2917b73fbfd75a2436a48a3ba4a927c59b6b160d3";
    public ShopAlertDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public ShopAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    protected ShopAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context=context;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_shop);
        initData();
        recyclerView=findViewById(R.id.alert_recycler);
        WrapContentLinearLayoutManager linearLayoutManager=new WrapContentLinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adpter_shopCard=new Adpter_ShopCard(list);
        recyclerView.setAdapter(adpter_shopCard);
        recyclerView.addItemDecoration(new SpacesItemDecoration(MacUtils.dpto(5)));
        adpter_shopCard.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(list!=null&&list.size()>0){
                    if(list.size()>position){

                        View rootView = view.getRootView();
                        AppCompatCheckBox viewById = rootView.findViewById(R.id.checkbox);
                        if(list.get(position).isClick()){
                            viewById.setChecked(false);
                        }else{
                            viewById.setChecked(true);
                        }
                        list.get(position).setClick(true);
                        for(int i=0;i<list.size();i++){
                            if(i!=position){
                                list.get(i).setClick(false);
                            }
                        }
                        adpter_shopCard.notifyDataSetChanged();

                    }
                }
            }
        });
        sumbit=findViewById(R.id.sumbit);
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<list.size();i++){
                    if(list.get(i).isClick()){
                       coupon_id=list.get(i).getId();
                    }else{
                        coupon_id="0x11";
                    }
                }

                dismiss();
                RxBus.getDefault().post(new PaySuccessEvent(coupon_id));

            }
        });
    }

    private void initData(){
        list=new ArrayList<>();
        http();
    }

    @Override
    public void show() {
        super.show();
        //获取弹窗位置
        window=getWindow();
        setCanceledOnTouchOutside(true);//不允许外部点击消失
        setCancelable(true);
        window.setWindowAnimations(R.style.bottom_menu_animations);
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0,0,0,dpto(20));
        WindowManager.LayoutParams layoutParams=window.getAttributes();//获取窗口属性
        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        layoutParams.width= (int) (displayMetrics.widthPixels*windowsize);
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity=Gravity.CENTER;
        GradientDrawable gradientDrawable=new GradientDrawable();
        gradientDrawable.setCornerRadius(dpto(10));
        window.setBackgroundDrawable(gradientDrawable);

        window.setAttributes(layoutParams);
    }

    //dpto
    public int dpto(int dp){
        return (int)(context.getResources().getDisplayMetrics().density*dp+0.5);
    }
    //http
    private  void http(){
        Subscriber<CardResponse> subscriber=new Subscriber<CardResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CardResponse cardResponse) {
                if(cardResponse.getCode()==1){
                    list.addAll(cardResponse.getData());
                    if(adpter_shopCard!=null){
                        adpter_shopCard.notifyDataSetChanged();
                    }
                }
            }
        };
        HttpMethods.getInstance().get_conpon_list(subscriber,token);
    }
}
