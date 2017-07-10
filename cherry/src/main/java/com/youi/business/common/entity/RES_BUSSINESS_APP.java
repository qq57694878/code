package com.youi.business.common.entity;


	/**
	 *业务系统
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="RES_BUSSINESS_APP")
public class RES_BUSSINESS_APP implements Serializable
{
	public RES_BUSSINESS_APP(){}
	public RES_BUSSINESS_APP(Long id, String name, String description, Long vendor_id, String org_id_list, String status, Date online_time, Date cdate, Long userid){
		this.id = id;
		this.name = name;
		this.description = description;
		this.vendor_id = vendor_id;
		this.org_id_list = org_id_list;
		this.status = status;
		this.online_time = online_time;
		this.cdate = cdate;
		this.userid = userid;
}
	/**
	 *id
	 */
	private Long id;

	/**
	 *业务系统名称
	 */
	private String name;

	/**
	 *描述
	 */
	private String description;

	/**
	 *开发单位ID
	 */
	private Long vendor_id;

	/**
	 *业务使用部门ID，多个部门,分割
	 */
	private String org_id_list;

	/**
	 *状态1有效；0无效
	 */
	private String status;

	/**
	 *上线时间
	 */
	private Date online_time;

	/**
	 *创建时间
	 */
	private Date cdate;

	/**
	 *创建人
	 */
	private Long userid;



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


	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "NAME")
	public String getName()
	{
		return name;
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


	public void setVendor_id(Long vendor_id)
	{
		this.vendor_id = vendor_id;
	}

	@Column(name = "VENDOR_ID")
	public Long getVendor_id()
	{
		return vendor_id;
	}


	public void setOrg_id_list(String org_id_list)
	{
		this.org_id_list = org_id_list;
	}

	@Column(name = "ORG_ID_LIST")
	public String getOrg_id_list()
	{
		return org_id_list;
	}


	public void setStatus(String status)
	{
		this.status = status;
	}

	@Column(name = "STATUS")
	public String getStatus()
	{
		return status;
	}


	public void setOnline_time(Date online_time)
	{
		this.online_time = online_time;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ONLINE_TIME" , length = 26)
	public Date getOnline_time()
	{
		return online_time;
	}


	public void setCdate(Date cdate)
	{
		this.cdate = cdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cdate" , length = 26)
	public Date getCdate()
	{
		return cdate;
	}


	public void setUserid(Long userid)
	{
		this.userid = userid;
	}

	@Column(name = "userid")
	public Long getUserid()
	{
		return userid;
	}

}
