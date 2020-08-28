package com.s.shenghuang.mlqm.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.s.shenghuang.mlqm.R;
import com.s.shenghuang.mlqm.bean.QuestionBean;

import java.util.List;

/**
 * @author:"道翼(yanwen)"
 * @params:"我的提问"
 * @data:20-8-22 下午2:12
 * @email:1966287146@qq.com
 */
public class Adapter_Question extends BaseQuickAdapter<QuestionBean, BaseViewHolder> {

    private Context context;
    public Adapter_Question(int layoutResId, @Nullable List<QuestionBean> data) {
        super(layoutResId, data);
    }

    public Adapter_Question(Context context, @Nullable List<QuestionBean> data) {
        super(R.layout.adpter_question,data);
        this.context=context;
    }

    public Adapter_Question(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionBean item) {
        if( item.getStatus_content()==null||item.getStatus_content().equals("")){
            helper.setText(R.id.question_details, "老师正在查看中");
        }else{
            helper.setText(R.id.question_details, item.getStatus_content());
        }

        helper.setText(R.id.question_time,"提交时间:"+item.getUpdate_time());
        ImageView view = helper.getView(R.id.question_image);
        Glide.with(context).load(item.getImage_url()).into(view);
    }
}
