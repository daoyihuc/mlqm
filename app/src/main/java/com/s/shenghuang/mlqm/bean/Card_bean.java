package com.s.shenghuang.mlqm.bean;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-23 下午5:21
 * @email:1966287146@qq.com
 */
public class Card_bean {

    /**
     * end_time : 2020-08-27
     * add_time : 2020-08-26
     * day : 1
     * status : 0
     * money : 50
     * max_money : 100
     * id : 2
     * inviter_uid : 9496
     */

    private String end_time;
    private String add_time;
    private String day;
    private String status;
    private String money;
    private String max_money;
    private String id;
    private String inviter_uid;
    private boolean isClick;

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMax_money() {
        return max_money;
    }

    public void setMax_money(String max_money) {
        this.max_money = max_money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInviter_uid() {
        return inviter_uid;
    }

    public void setInviter_uid(String inviter_uid) {
        this.inviter_uid = inviter_uid;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
