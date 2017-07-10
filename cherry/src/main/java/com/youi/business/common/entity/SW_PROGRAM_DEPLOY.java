package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="SW_PROGRAM_DEPLOY")
public class SW_PROGRAM_DEPLOY implements Serializable
{
	public SW_PROGRAM_DEPLOY(){}
	public SW_PROGRAM_DEPLOY(Long id, Long business_app, String instanceid, String title, String userid, Date cdate, Long c_org_id, Long company_id, Date stime, Date etime, String org_id_list, Long req_change_id, String description,  Date plan_deploy_time, Date actual_deploy_time, String biz_ext, String act_ext){
		this.id = id;
		this.business_app = business_app;
		this.instanceid = instanceid;
		this.title = title;
		this.userid = userid;
		this.cdate = cdate;
		this.c_org_id = c_org_id;
		this.company_id = company_id;
		this.stime = stime;
		this.etime = etime;
		this.org_id_list = org_id_list;
		this.req_change_id = req_change_id;
		this.description = description;
		this.plan_deploy_time = plan_deploy_time;
		this.actual_deploy_time = actual_deploy_time;
		this.biz_ext = biz_ext;
		this.act_ext = act_ext;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *业务系统ID
	 */
	private Long business_app;

	/**
	 *工作流实例ID
	 */
	private String instanceid;

	/**
	 *标题，后台自动生成，业务系统名称+主键ID号
	 */
	private String title;

	/**
	 *发起人
	 */
	private String userid;

	/**
	 *创建时间
	 */
	private Date cdate;

	/**
	 *
	 */
	private Long c_org_id;

	/**
	 *外协单位
	 */
	private Long company_id;

	/**
	 *计划开始时间
	 */
	private Date stime;

	/**
	 *计划结束时间
	 */
	private Date etime;

	/**
	 *程序发布涉及业务处室list，用,分割
	 */
	private String org_id_list;

	/**
	 *需求变更ID
	 */
	private Long req_change_id;

	/**
	 *描述
	 */
	private String description;


	/**
	 *计划发布时间
	 */
	private Date plan_deploy_time;

	/**
	 *实际部署时间
	 */
	private Date actual_deploy_time;

	/**
	 *业务扩展预留字段
	 */
	private String biz_ext;

	/**
	 *审核人预留字段
	 */
	private String act_ext;



	public void setId(Long id)
	{
		this.id = id;
	}
    @Id
	@Column(name = "id")
	public Long getId()
	{
		return id;
	}


	public void setBusiness_app(Long business_app)
	{
		this.business_app = business_app;
	}

	@Column(name = "BUSINESS_APP")
	public Long getBusiness_app()
	{
		return business_app;
	}


	public void setInstanceid(String instanceid)
	{
		this.instanceid = instanceid;
	}

	@Column(name = "INSTANCEID")
	public String getInstanceid()
	{
		return instanceid;
	}


	public void setTitle(String title)
	{
		this.title = title;
	}

	@Column(name = "title")
	public String getTitle()
	{
		return title;
	}


	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	@Column(name = "USERID")
	public String getUserid()
	{
		return userid;
	}


	public void setCdate(Date cdate)
	{
		this.cdate = cdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CDATE" , length = 26)
	public Date getCdate()
	{
		return cdate;
	}


	public void setC_org_id(Long c_org_id)
	{
		this.c_org_id = c_org_id;
	}

	@Column(name = "C_ORG_ID")
	public Long getC_org_id()
	{
		return c_org_id;
	}


	public void setCompany_id(Long company_id)
	{
		this.company_id = company_id;
	}

	@Column(name = "COMPANY_ID")
	public Long getCompany_id()
	{
		return company_id;
	}


	public void setStime(Date stime)
	{
		this.stime = stime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STIME" , length = 26)
	public Date getStime()
	{
		return stime;
	}


	public void setEtime(Date etime)
	{
		this.etime = etime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ETIME" , length = 26)
	public Date getEtime()
	{
		return etime;
	}


	public void setOrg_id_list(String org_id_list)
	{
		this.org_id_list = org_id_list;
	}

	@Column(name = "org_id_list")
	public String getOrg_id_list()
	{
		return org_id_list;
	}


	public void setReq_change_id(Long req_change_id)
	{
		this.req_change_id = req_change_id;
	}

	@Column(name = "req_change_id")
	public Long getReq_change_id()
	{
		return req_change_id;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription()
	{
		return description;
	}


	public void setPlan_deploy_time(Date plan_deploy_time)
	{
		this.plan_deploy_time = plan_deploy_time;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "plan_deploy_time" , length = 26)
	public Date getPlan_deploy_time()
	{
		return plan_deploy_time;
	}


	public void setActual_deploy_time(Date actual_deploy_time)
	{
		this.actual_deploy_time = actual_deploy_time;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Actual_deploy_time" , length = 26)
	public Date getActual_deploy_time()
	{
		return actual_deploy_time;
	}


	public void setBiz_ext(String biz_ext)
	{
		this.biz_ext = biz_ext;
	}

	@Column(name = "biz_EXT")
	public String getBiz_ext()
	{
		return biz_ext;
	}


	public void setAct_ext(String act_ext)
	{
		this.act_ext = act_ext;
	}

	@Column(name = "act_ext")
	public String getAct_ext()
	{
		return act_ext;
	}

}
