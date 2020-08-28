package com.s.shenghuang.mlqm.bean;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-23 下午10:17
 * @email:1966287146@qq.com
 */
public class ShopBean {


    /**
     * package_id : 4
     * package_title : 1-9年级10分钟体验套餐
     * package_description : 可与老师视频问答学习10分钟，可看回播。更多优惠请见“我的优惠券”。
     * start_fee : 0.01
     * now_fee : 0.01
     * free_times : 600
     * call_time : 0
     * add_time : 7
     * years : 一年级,二年级,三年级,四年级,五年级,六年级,七年级,八年级,九年级
     * subjects : 数学,语文,英语
     * status : 1
     */

    private String package_id;//套餐id
    private String package_title;//套餐明
    private String package_description;// 说明
    private String start_fee; //开时收费
    private String now_fee; //现在收费
    private String free_times; //空闲时间
    private String call_time;
    private String add_time;
    private String years;//辅导年级
    private String subjects;//辅导科目
    private String status;

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getPackage_title() {
        return package_title;
    }

    public void setPackage_title(String package_title) {
        this.package_title = package_title;
    }

    public String getPackage_description() {
        return package_description;
    }

    public void setPackage_description(String package_description) {
        this.package_description = package_description;
    }

    public String getStart_fee() {
        return start_fee;
    }

    public void setStart_fee(String start_fee) {
        this.start_fee = start_fee;
    }

    public String getNow_fee() {
        return now_fee;
    }

    public void setNow_fee(String now_fee) {
        this.now_fee = now_fee;
    }

    public String getFree_times() {
        return free_times;
    }

    public void setFree_times(String free_times) {
        this.free_times = free_times;
    }

    public String getCall_time() {
        return call_time;
    }

    public void setCall_time(String call_time) {
        this.call_time = call_time;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
