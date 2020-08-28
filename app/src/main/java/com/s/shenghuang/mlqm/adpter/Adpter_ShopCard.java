package com.s.shenghuang.mlqm.adpter;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.bean.Card_bean;

import java.util.List;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-27 下午4:34
 * @email:1966287146@qq.com
 */
public class Adpter_ShopCard extends BaseQuickAdapter<Card_bean, BaseViewHolder> {
    public Adpter_ShopCard(int layoutResId, @Nullable List<Card_bean> data) {
        super(layoutResId, data);
    }

    public Adpter_ShopCard(@Nullable List<Card_bean> data) {
        super(R.layout.adpter_shop_card,data);
    }

    public Adpter_ShopCard(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Card_bean item) {

        AppCompatCheckBox view = helper.getView(R.id.checkbox);

        helper.setText(R.id.card_money,"￥"+item.getMoney())
                .setText(R.id.card_details,"满"+item.getMax_money()+"减"+item.getMoney()+"元")
                .setText(R.id.card_time,"有效期至"+item.getEnd_time())
        ;
        view.setChecked(item.isClick());
    }
}
