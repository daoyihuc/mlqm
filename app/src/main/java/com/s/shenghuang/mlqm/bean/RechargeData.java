package com.s.shenghuang.mlqm.bean;

/**
 * AUTHOR : daoyi
 * TODO : 充值接口返回Data
 * DATE : 2020/3/18
 * VERSION : 1.0
 */
public class RechargeData {

    //微信支付字段
    private String appid;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String noncestr;
    private String sign;

    //支付宝支付字段
    private String pay_info;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPay_info() {
        return pay_info ==null?"":pay_info;
    }

    public void setPay_info(String pay_info) {
        this.pay_info = pay_info;
    }
}
