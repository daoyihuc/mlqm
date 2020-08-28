package com.s.shenghuang.mlqm.resp;

import com.s.shenghuang.mlqm.base.BaseResponse;
import com.s.shenghuang.mlqm.bean.QuestionBeans;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-27 下午2:40
 * @email:1966287146@qq.com
 */
public class QuestionResponse extends BaseResponse {

    private QuestionBeans data;

    public QuestionBeans getData() {
        return data;
    }

    public void setData(QuestionBeans data) {
        this.data = data;
    }
}
