package com.s.shenghuang.mlqm.resp;

import com.s.shenghuang.mlqm.base.BaseResponse;
import com.s.shenghuang.mlqm.bean.RechargeData;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-27 下午5:40
 * @email:1966287146@qq.com
 */
public class PayResponse extends BaseResponse {
    private RechargeData data;

    public RechargeData getData() {
        return data;
    }

    public void setData(RechargeData data) {
        this.data = data;
    }
}
