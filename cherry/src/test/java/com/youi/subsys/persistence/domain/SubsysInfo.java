package com.youi.subsys.persistence.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by cheng on 16/6/25.
 */
@Entity
@Table(name="SUBSYS_INFO")
public class SubsysInfo implements Serializable{
    private static final long serialVersionUID = 0L;
    private Long id;
    private String name;
    private PartyEntity developed_department;
    private PartyEntity  use_department;
    
    private String status;
    private Date online_time;

    public SubsysInfo() {
    }

    public SubsysInfo(Long id, String name, PartyEntity developed_department, PartyEntity use_department, String status, Date online_time) {
        this.id = id;
        this.name = name;
        this.developed_department = developed_department;
        this.use_department = use_department;
        this.status = status;
        this.online_time = online_time;
    }
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /** @return 外键，组织类型. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEVELOPED_DEPARTMENT_ID")
    public PartyEntity getDeveloped_department() {
        return developed_department;
    }

    public void setDeveloped_department(PartyEntity developed_department) {
        this.developed_department = developed_department;
    }
    /** @return 外键，组织类型. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USE_DEPARTMENT_ID")
    public PartyEntity getUse_department() {
        return use_department;
    }

    public void setUse_department(PartyEntity use_department) {
        this.use_department = use_department;
    }
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ONLINE_TIME" , length = 26)
    public Date getOnline_time() {
        return online_time;
    }

    public void setOnline_time(Date online_time) {
        this.online_time = online_time;
    }
}
