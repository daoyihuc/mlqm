package com.s.shenghuang.mlqm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.adpter.Adapter_Question;
import com.s.shenghuang.mlqm.bean.QuestionBean;
import com.s.shenghuang.mlqm.http.HttpMethods;
import com.s.shenghuang.mlqm.resp.QuestionResponse;
import com.s.shenghuang.mlqm.thrid.SpacesItemDecoration;
import com.s.shenghuang.mlqm.utils.MacUtils;
import com.s.shenghuang.mlqm.view.WrapContentLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * @author:"道翼(yanwen)"
 * @params:"代理升级"
 * @data:20-8-22 下午2:07
 * @email:1966287146@qq.com
 */
public class QuestionActivity extends BaseTitleActivity{

    private RecyclerView recyclerView;
    private View view;
    private Adapter_Question adapter_question;
    private List<QuestionBean> list_question;
    private String token="7ad8b41d4b55e6db009ac1e2917b73fbfd75a2436a48a3ba4a927c59b6b160d3";
    private SmartRefreshLayout smart_refresh;

    public static  void start(Activity activity){
        Intent  intent=new Intent();
        intent.setClass(activity,QuestionActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MacUtils.initWindow(this, 0xffffffff, false, null, true);
        init_childview();
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initDatas() {
        super.initDatas();
        list_question=new ArrayList<>();
        http();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void init_childview() {
        super.init_childview();
        view=getLayoutInflater().inflate(R.layout.activity_question,relativeLayout);
        View empty=getLayoutInflater().inflate(R.layout.activity_empty,null);
        smart_refresh=findViewById(R.id.smart_refresh);
        recyclerView=view.findViewById(R.id.question_recycler);
        WrapContentLinearLayoutManager linearLayoutManager=new WrapContentLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter_question=new Adapter_Question(this,list_question);
        adapter_question.setEmptyView(empty);
        recyclerView.setAdapter(adapter_question);
        recyclerView.addItemDecoration(new SpacesItemDecoration(MacUtils.dpto(10)));
        adapter_question.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(list_question!=null&&list_question.size()>0){

                    if(list_question.size()>position){
                        WebViewActivity.start(QuestionActivity.this,list_question.get(position).getQuestion_url());
                    }
                }
            }
        });
        setSmart_refresh();
    }

    private void setSmart_refresh(){
        smart_refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                pageNo ++;
                http();
            }
        });

        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                pageNo =1;
                http();

            }
        });
    }

    @Override
    public void setClip_padding(boolean fa) {
        super.setClip_padding(fa);
    }

    @Override
    protected void setTitlabar() {
        super.setTitlabar();
        titlabar.setCenterTitle("我的提问");
        titlabar.setLeftTitle("返回");
        titlabar.setLeftColor(0xff000000);
        titlabar.setLeftFontSize(18);
        titlabar.setLeftPadding(0,0,0,0);
        titlabar.setLeftMargin(MacUtils.dpto(10),0,0,0);
        titlabar.setLeftDrawable(R.drawable.ic_navigate_before_black_24dp,0x4d000000);
    }

    //http
    private void http(){
        Subscriber<QuestionResponse> subscriber=new Subscriber<QuestionResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                MacUtils.ToastShow(QuestionActivity.this,"请求超时");
                smart_refresh.setNoMoreData(false);
                smart_refresh.finishRefresh();
                smart_refresh.finishLoadMore();
            }

            @Override
            public void onNext(QuestionResponse questionResponse) {

                if(questionResponse.getCode()==1){
                    success(questionResponse);

                    smart_refresh.setNoMoreData(false);
                    smart_refresh.finishRefresh();
                    smart_refresh.finishLoadMore();
                }
            }
        };
        HttpMethods.getInstance().question(subscriber,token);
    }
    private void success(QuestionResponse resp){
       list_question.addAll(resp.getData().getList());
       if(adapter_question!=null){
           adapter_question.notifyDataSetChanged();
       }

    }
}
