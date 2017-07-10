package com.youi.business.common.store;

import com.youi.business.common.holder.CurrentWebPath;

import javax.activation.DataSource;
import java.util.Date;

/**
 * Created by jinliang on 2016/9/2.
 */
public class StoreDTO {
    /**
     *文件主键
     */
    private Long id;

    /**
     *文件名称
     */
    private String file_name;
    /**
     *文件显示名称，原始文件名
     */
    private String show_name;

    /**
     *文件类型（image）、flash（flash）、媒体（media）、文件（file）
     */
    private String file_type;

    /**
     *文件大小
     */
    private String file_size;

    /**
     *文件后缀名
     */
    private String file_suffix;

    /**
     *文件存放的相对路径
     */
    private String file_path;

    /**
     *文件MD5值
     */
    private String md5;

    /**
     *文件创建时间
     */
    private Date create_date;

    /**
     *文件创建者名称
     */
    private String create_user;
    /**
     * 业务分类
     */
    private String yw_type;
    /**
     * 业务id
     */
    private String yw_id;
    /**
     * 数据源
     */
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_suffix() {
        return file_suffix;
    }

    public void setFile_suffix(String file_suffix) {
        this.file_suffix = file_suffix;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getYw_type() {
        return yw_type;
    }

    public void setYw_type(String yw_type) {
        this.yw_type = yw_type;
    }

    public String getYw_id() {
        return yw_id;
    }

    public String getShow_name() {
        return show_name;
    }

    public void setShow_name(String show_name) {
        this.show_name = show_name;
    }

    public void setYw_id(String yw_id) {
        this.yw_id = yw_id;
    }

    public String getUrl(){
       return  CurrentWebPath.getCurrentWebPath()+"/exclude/file/download/"+String.valueOf(this.getId())+".do?t="+System.currentTimeMillis();
    }

}
