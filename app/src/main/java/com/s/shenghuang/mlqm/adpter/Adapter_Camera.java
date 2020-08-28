package com.s.shenghuang.mlqm.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.interfaces.Constants;

import java.util.List;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-27 上午9:22
 * @email:1966287146@qq.com
 */
public class Adapter_Camera extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context context;
    public Adapter_Camera(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public Adapter_Camera(Context context,@Nullable List<String> data) {
        super(R.layout.adpter_camearlist,data);
        this.context=context;
    }

    public Adapter_Camera(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView view = helper.getView(R.id.picture_iv);
        Glide.with(context).load(Constants.MAIN_URLS+item).into(view);
        Log.e("图片",Constants.MAIN_URLS+item);
    }
}
