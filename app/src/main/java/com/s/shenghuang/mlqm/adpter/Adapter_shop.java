package com.s.shenghuang.mlqm.adpter;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.bean.ShopBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-23 下午10:16
 * @email:1966287146@qq.com
 */
public class Adapter_shop extends BaseQuickAdapter<ShopBean, BaseViewHolder> {
    public Adapter_shop(int layoutResId, @Nullable List<ShopBean> data) {
        super(layoutResId, data);
    }

    public Adapter_shop(@Nullable List<ShopBean> data) {
        super(R.layout.adpter_shop,data);
    }

    public Adapter_shop(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopBean item) {

        TextView view = helper.getView(R.id.adap_shop_money2);
        view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.adap_shop_name,item.getPackage_title())
                .setText(R.id.adap_shop_details,item.getPackage_description())
                .setText(R.id.adap_shop_rank, item.getYears())//年级
                .setText(R.id.adap_shop_class,"科目:"+item.getSubjects())//科目
                .setText(R.id.adap_shop_time,"可使用:"+item.getFree_times()+"秒"+item.getAdd_time()+"天内有效")
                .setText(R.id.adap_shop_money,"￥"+item.getNow_fee())
                .setText(R.id.adap_shop_money2,"￥"+item.getStart_fee());
        helper.addOnClickListener(R.id.adap_shop_go);
    }
}
