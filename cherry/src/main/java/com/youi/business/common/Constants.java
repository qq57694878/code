package com.youi.business.common;

/**
 * Created by jinliang on 2016/7/29.
 */
public class Constants {
    public final static String USERID = "userid";
    // -- Header 定义 --//
    /** authencation header. */
    public static final String AUTHENTICATION_HEADER = "authentication";
    /**
     * 接口版本
     */
    public final static String VERSION = "ver";
    /**
     * 默认接口版本
     */
    public final static String DEFAULT_VERSION = "1.0";

    public final static int ERROR_CODE_200=200;
    public final static int ERROR_CODE_401=401;
    public final static int ERROR_CODE_500=500;

    /**
     * 任务状态
     */
    public final static String TASK_FLAG_NORMAL="normal";//正常流转
    public final static String TASK_FLAG_REJECT="reject";//驳回
    public final static String TASK_FLAG_STOP="stop";//终止
    public final static String TASK_FLAG_DELETE="delete";//删除
    /**
     * ORG
     */
    public final static String TOP_ORG_ID="*";
    public final static String OTG_TYPE_VENDOR="0";//单位
    public final static String OTG_TYPE_DEPARTMENT="0";//部门
    //SNMP
    public final static String SNMP_REQ_atPhysAddress="1.3.6.1.2.1.3.1.1.2";

}
