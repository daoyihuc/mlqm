package com.s.shenghuang.mlqm.base;

public class BaseResponse {

    String msg;//返回信息
    int code;//返回码

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
