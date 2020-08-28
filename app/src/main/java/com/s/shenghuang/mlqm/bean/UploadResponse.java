package com.s.shenghuang.mlqm.bean;

import com.s.shenghuang.mlqm.base.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-27 上午10:27
 * @email:1966287146@qq.com
 */
public class UploadResponse extends BaseResponse {
    private Map<String,Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
