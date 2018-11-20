package com.yuang.yuangapplication.entity;

/**
 * 类描述:
 * 创建人: Yuang
 * 创建时间: 2018/11/20 1:15 PM
 */
public class ActivityBean {
    private Class activityClass;
    private String activityName;

    public ActivityBean(Class activityClass, String activityName) {
        this.activityClass = activityClass;
        this.activityName = activityName;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
