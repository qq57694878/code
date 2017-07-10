package com.youi.business.tools.vo;

import java.util.Date;

/**
 * Created by jinliang on 2016/9/28.
 */
public class VNoticeDetail {
    /**
     *
     */
    private Long id;

    /**
     *通知类型
     */
    private String notice_type;

    /**
     *
     */
    private String notice_info;

    /**
     *创建时间
     */
    private Date ctime;

    /**
     *发送人ID，系统发送写0
     */
    private Long cuserid;

    /**
     *收件人ID
     */
    private Long ruserid;

    /**
     *是否已读，0-未读，1-已读
     */
    private String has_read;

    /**
     *阅读时间
     */
    private Date read_time;

    private String cuser_name;//发件人
    private String ruser_name;//收件人

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotice_type() {
        return notice_type;
    }

    public void setNotice_type(String notice_type) {
        this.notice_type = notice_type;
    }

    public String getNotice_info() {
        return notice_info;
    }

    public void setNotice_info(String notice_info) {
        this.notice_info = notice_info;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Long getCuserid() {
        return cuserid;
    }

    public void setCuserid(Long cuserid) {
        this.cuserid = cuserid;
    }

    public Long getRuserid() {
        return ruserid;
    }

    public void setRuserid(Long ruserid) {
        this.ruserid = ruserid;
    }

    public String getHas_read() {
        return has_read;
    }

    public void setHas_read(String has_read) {
        this.has_read = has_read;
    }

    public Date getRead_time() {
        return read_time;
    }

    public void setRead_time(Date read_time) {
        this.read_time = read_time;
    }

    public String getCuser_name() {
        return cuser_name;
    }

    public void setCuser_name(String cuser_name) {
        this.cuser_name = cuser_name;
    }

    public String getRuser_name() {
        return ruser_name;
    }

    public void setRuser_name(String ruser_name) {
        this.ruser_name = ruser_name;
    }
}
