package com.s.shenghuang.mlqm.adpter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.bean.Card_bean;

import java.util.List;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-23 下午5:23
 * @email:1966287146@qq.com
 */
public class Adapter_Cara  extends BaseQuickAdapter<Card_bean, BaseViewHolder> {

    private Context context;

    public Adapter_Cara(int layoutResId, @Nullable List<Card_bean> data) {
        super(layoutResId, data);
    }

    public Adapter_Cara(Context context, @Nullable List<Card_bean> data) {
        super(R.layout.adpter_crad, data);
        this.context=context;
    }
    public Adapter_Cara(int layoutResId) {
        super(layoutResId);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, Card_bean item) {

        if(!item.getStatus().equals("0")){
            RelativeLayout view = helper.getView(R.id.adper_card_box);
            view.setBackground(context.getDrawable(R.drawable.shap_cardpackage_un));
        }
        helper.setText(R.id.card_money,"￥"+item.getMoney())
                .setText(R.id.card_details,"满"+item.getMax_money()+"减"+item.getMoney()+"元")
                .setText(R.id.card_time,"有效期至"+item.getEnd_time())
        ;


    }
}
