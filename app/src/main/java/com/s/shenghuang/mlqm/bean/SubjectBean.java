package com.s.shenghuang.mlqm.bean;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * @author:"道翼(yanwen)"
 * @params:""
 * @data:20-8-27 上午11:09
 * @email:1966287146@qq.com
 */
public class SubjectBean implements IPickerViewData {


    /**
     * subject_id : 1
     * subject_name : 数学
     */

    private String subject_id;
    private String subject_name;

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    @Override
    public String getPickerViewText() {
        return this.subject_name;
    }
}
