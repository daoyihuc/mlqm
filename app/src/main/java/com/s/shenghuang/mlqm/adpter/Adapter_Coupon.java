package com.s.shenghuang.mlqm.adpter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.bean.CouponBean;

import java.util.List;

/**
 * @author:"道翼(yanwen)"
 * @params:"优惠卷"
 * @data:20-8-23 下午4:24
 * @email:1966287146@qq.com
 */
public class Adapter_Coupon extends BaseQuickAdapter<CouponBean, BaseViewHolder> {

    public Adapter_Coupon(int layoutResId, @Nullable List<CouponBean> data) {
        super(layoutResId, data);
    }

    public Adapter_Coupon(@Nullable List<CouponBean> data) {
        super(R.layout.adpter_coupon,data);
    }

    public Adapter_Coupon(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponBean item) {
        helper.setText(R.id.coupon_username,item.getUsername())
                .setText(R.id.coupon_time,item.getRegister_time());
    }
}
