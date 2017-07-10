package com.youi.business.common.entity;


	/**
	 *数据查询
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="SW_DATA_QUERY")
public class SW_DATA_QUERY implements Serializable
{
	public SW_DATA_QUERY(){}
	public SW_DATA_QUERY(Long id,String instanceid,String userid,Date cdate,Long c_org_id,String title,String description,Date stime,Date etime,Long business_app,Long company_id,Long leader_id,Date actually_stime,Date actually_etime,String actually_sql,String biz_ext,String act_ext){
		this.id = id;
		this.instanceid = instanceid;
		this.userid = userid;
		this.cdate = cdate;
		this.c_org_id = c_org_id;
		this.title = title;
		this.description = description;
		this.stime = stime;
		this.etime = etime;
		this.business_app = business_app;
		this.company_id = company_id;
		this.leader_id = leader_id;
		this.actually_stime = actually_stime;
		this.actually_etime = actually_etime;
		this.actually_sql = actually_sql;
		this.biz_ext = biz_ext;
		this.act_ext = act_ext;
}
	/**
	 *ID
	 */
	private Long id;

	/**
	 *
	 */
	private String instanceid;

	/**
	 *创建人
	 */
	private String userid;

	/**
	 *创建时间
	 */
	private Date cdate;

	/**
	 *申请部门ID
	 */
	private Long c_org_id;

	/**
	 *标题，业务系统名称+数据主键
	 */
	private String title;

	/**
	 *查询申请原因
	 */
	private String description;

	/**
	 *计划开始时间
	 */
	private Date stime;

	/**
	 *计划结束时间
	 */
	private Date etime;

	/**
	 *业务系统appid
	 */
	private Long business_app;

	/**
	 *外协公司id
	 */
	private Long company_id;

	/**
	 *领导id
	 */
	private Long leader_id;

	/**
	 *实际开始时间
	 */
	private Date actually_stime;

	/**
	 *实际结束时间
	 */
	private Date actually_etime;

	/**
	 *查询SQL
	 */
	private String actually_sql;

	/**
	 *扩展字段
	 */
	private String biz_ext;

	/**
	 *
	 */
	private String act_ext;



	public void setId(Long id)
	{
		this.id = id;
	}
    @Id
	@Column(name = "ID")
	public Long getId()
	{
		return id;
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

	@Column(name = "C_org_id")
	public Long getC_org_id()
	{
		return c_org_id;
	}


	public void setTitle(String title)
	{
		this.title = title;
	}

	@Column(name = "TITLE")
	public String getTitle()
	{
		return title;
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


	public void setBusiness_app(Long business_app)
	{
		this.business_app = business_app;
	}

	@Column(name = "BUSINESS_APP")
	public Long getBusiness_app()
	{
		return business_app;
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


	public void setLeader_id(Long leader_id)
	{
		this.leader_id = leader_id;
	}

	@Column(name = "LEADER_ID")
	public Long getLeader_id()
	{
		return leader_id;
	}


	public void setActually_stime(Date actually_stime)
	{
		this.actually_stime = actually_stime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTUALLY_STIME" , length = 26)
	public Date getActually_stime()
	{
		return actually_stime;
	}


	public void setActually_etime(Date actually_etime)
	{
		this.actually_etime = actually_etime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTUALLY_ETIME" , length = 26)
	public Date getActually_etime()
	{
		return actually_etime;
	}


	public void setActually_sql(String actually_sql)
	{
		this.actually_sql = actually_sql;
	}

	@Column(name = "ACTUALLY_SQL")
	public String getActually_sql()
	{
		return actually_sql;
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
