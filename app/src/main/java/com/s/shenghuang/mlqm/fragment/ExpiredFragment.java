package com.s.shenghuang.mlqm.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.adpter.Adapter_Cara;
import com.s.shenghuang.mlqm.base.Basefragment;
import com.s.shenghuang.mlqm.bean.Card_bean;
import com.s.shenghuang.mlqm.http.HttpMethods;
import com.s.shenghuang.mlqm.resp.CardResponse;
import com.s.shenghuang.mlqm.thrid.ItemOffsetDecoration;
import com.s.shenghuang.mlqm.utils.MacUtils;
import com.s.shenghuang.mlqm.view.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * @author:"道翼(yanwen)"
 * @params:"已过期"
 * @data:20-8-23 下午5:07
 * @email:1966287146@qq.com
 */
public class ExpiredFragment extends Basefragment {
    private RecyclerView recyclerView;
    private Adapter_Cara adapter_cara;
    private List<Card_bean> list;


    @Override
    public View loadView() {
        return getLayoutInflater().inflate(R.layout.fragment_card,null);
    }

    @Override
    public void initData() {
        list=new ArrayList<>();
//        for (int i=0;i<6;i++){
//            Card_bean card_bean=new Card_bean();
//            list.add(card_bean);
//        }
        Http("7ad8b41d4b55e6db009ac1e2917b73fbfd75a2436a48a3ba4a927c59b6b160d3","2");
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initUI() {
        recyclerView=getview().findViewById(R.id.card_recycler);
        WrapContentLinearLayoutManager linearLayoutManager=new WrapContentLinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter_cara=new Adapter_Cara(getActivity(),list);
        recyclerView.setAdapter(adapter_cara);

        recyclerView.addItemDecoration(new ItemOffsetDecoration(MacUtils.dpto(10)));


    }
    //http
    private void Http(String token,String status){
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
                    success(cardResponse);
                }
            }
        };
        HttpMethods.getInstance().card_list(subscriber,token,status);
    }

    private void success(CardResponse data){
        list.addAll(data.getData());
        adapter_cara.notifyDataSetChanged();
    }
}
