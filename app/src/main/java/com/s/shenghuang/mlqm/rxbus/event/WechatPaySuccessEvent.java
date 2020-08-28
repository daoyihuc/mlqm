package com.s.shenghuang.mlqm.rxbus.event;

/**
 * AUTHOR : 谢明峰
 * TODO : 微信支付成功Event
 * DATE : 2020/3/20
 * VERSION : 1.0
 */
public class WechatPaySuccessEvent {

    private String type;
    public WechatPaySuccessEvent(String type) {
        this.type =type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
