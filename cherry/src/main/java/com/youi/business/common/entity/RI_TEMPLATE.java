package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="RI_TEMPLATE")
public class RI_TEMPLATE implements Serializable
{
	public RI_TEMPLATE(){}
	public RI_TEMPLATE(Long id, String name, String ri_code, String description, String status){
		this.id = id;
		this.name = name;
		this.ri_code = ri_code;
		this.description = description;
		this.status = status;
}
	/**
	 *ID
	 */
	private Long id;

	/**
	 *巡检模板名称
	 */
	private String name;

	/**
	 *巡检类型 0 机房基础设施、1 服务器、2 网络设备、3 支撑软件
	 */
	private String ri_code;

	/**
	 *概述
	 */
	private String description;

	/**
	 *模板状态 1 启用 0 停用
	 */
	private String status;



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


	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "name")
	public String getName()
	{
		return name;
	}


	public void setRi_code(String ri_code)
	{
		this.ri_code = ri_code;
	}

	@Column(name = "ri_code")
	public String getRi_code()
	{
		return ri_code;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "description")
	public String getDescription()
	{
		return description;
	}


	public void setStatus(String status)
	{
		this.status = status;
	}

	@Column(name = "status")
	public String getStatus()
	{
		return status;
	}

}
