package com.s.shenghuang.mlqm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.adpter.Adapter_Coupon;
import com.s.shenghuang.mlqm.adpter.Adapter_Question;
import com.s.shenghuang.mlqm.bean.CouponBean;
import com.s.shenghuang.mlqm.utils.MacUtils;
import com.s.shenghuang.mlqm.view.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * @author:"道翼(yanwen)"
 * @params:"代理升级"
 * @data:20-8-23 下午3:45
 * @email:1966287146@qq.com
 */
public class CouponActivity extends BaseTitleActivity{

    private StringBuilder lable;
    private int number=0;
    private int number1=3;
    private int money=30;
    private List<CouponBean> list;
    private View root_view;
    private View Hearder_view;
    private Button shared_friend,shared_poster;
    private RecyclerView recyclerView;
    private Adapter_Coupon adapter_question;


    public static void start(Activity activity){
        Intent intent=new Intent();
        intent.setClass(activity,CouponActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init_childview();

    }

    @Override
    public void initViews() {
        super.initViews();
        root_view=getLayoutInflater().inflate(R.layout.activty_coupon,relativeLayout);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        list=new ArrayList<>();

    }


    @SuppressLint("WrongConstant")
    @Override
    public void init_childview() {
        super.init_childview();
        shared_friend=findViewById(R.id.shared_friend);
        shared_poster=findViewById(R.id.shared_poster);
        recyclerView=findViewById(R.id.coupon_recycler);
        WrapContentLinearLayoutManager linearLayoutManager=new WrapContentLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //适配器
        adapter_question=new Adapter_Coupon(list);
        //布局加载
        Hearder_view= LayoutInflater.from(this).inflate(R.layout.header_view,null,false);
        adapter_question.addHeaderView(Hearder_view);
        View empty=getLayoutInflater().inflate(R.layout.activity_empty,null);
//        adapter_question.setEmptyView(empty);
        recyclerView.setAdapter(adapter_question);


    }

    @Override
    protected void setTitlabar() {
        super.setTitlabar();
        titlabar.setCenterTitle("优惠卷");
        titlabar.setLeftTitle("返回");
        titlabar.setRightTitle("卡包");
        titlabar.setRightFontSize(18);
        titlabar.setRightColor(0xff000000);
        titlabar.setLeftColor(0xff000000);
        titlabar.setLeftFontSize(18);
        titlabar.setLeftPadding(0,0,0,0);
        titlabar.setLeftMargin(MacUtils.dpto(10),0,0,0);
        titlabar.setRightMargin(0,0,MacUtils.dpto(10),0);
        titlabar.setLeftDrawable(R.drawable.ic_navigate_before_black_24dp,0x4d000000);
        titlabar.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardpackageActivity.start(CouponActivity.this);
            }
        });
    }
    private String  create_lable(){
        lable=new StringBuilder();
        lable.append("您本月已经邀请了");
        lable.append(number);
        lable.append("位");
        lable.append("同学前来学习,再邀请");
        lable.append(number1);
        lable.append("位同学就可以获得一张");
        lable.append(money);
        lable.append("元的优惠卷");

        return lable.toString();

    }
}
