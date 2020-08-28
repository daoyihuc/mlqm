package com.s.shenghuang.mlqm.resp;

import com.s.shenghuang.mlqm.base.BaseResponse;
import com.s.shenghuang.mlqm.bean.Card_bean;

import java.util.List;

/**
 * @author:"道翼(yanwen)"
 * @params:"优惠卷"
 * @data:20-8-26 下午6:49
 * @email:1966287146@qq.com
 */
public class CardResponse extends BaseResponse {
    private List<Card_bean> data;

    public List<Card_bean> getData() {
        return data;
    }

    public void setData(List<Card_bean> data) {
        this.data = data;
    }
}
