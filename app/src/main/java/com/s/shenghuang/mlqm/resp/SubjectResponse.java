package com.s.shenghuang.mlqm.resp;

import com.s.shenghuang.mlqm.base.BaseResponse;
import com.s.shenghuang.mlqm.bean.SubjectBean;

import java.util.List;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-27 上午11:10
 * @email:1966287146@qq.com
 */
public class SubjectResponse extends BaseResponse {

    private List<SubjectBean> data;

    public List<SubjectBean> getData() {
        return data;
    }

    public void setData(List<SubjectBean> data) {
        this.data = data;
    }
}
