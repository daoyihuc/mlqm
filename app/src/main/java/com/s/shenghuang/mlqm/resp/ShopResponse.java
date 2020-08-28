package com.s.shenghuang.mlqm.resp;

import com.s.shenghuang.mlqm.base.BaseResponse;
import com.s.shenghuang.mlqm.bean.ShopBean;

import java.util.List;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-26 上午11:01
 * @email:1966287146@qq.com
 */
public class ShopResponse extends BaseResponse {
    private List<ShopBean> data;

    public List<ShopBean> getData() {
        return data;
    }

    public void setData(List<ShopBean> data) {
        this.data = data;
    }

}
