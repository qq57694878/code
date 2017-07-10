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
@Table(name="RI_PLAN_TARGET")
public class RI_PLAN_TARGET implements Serializable
{
	public RI_PLAN_TARGET(){}
	public RI_PLAN_TARGET(Long id, Long plan_id, String dev_type, Long dev_id, String status){
		this.id = id;
		this.plan_id = plan_id;
		this.dev_type = dev_type;
		this.dev_id = dev_id;
		this.status = status;
}
	/**
	 *ID
	 */
	private Long id;

	/**
	 *巡检计划ID
	 */
	private Long plan_id;

	/**
	 *设备类型
	 */
	private String dev_type;

	/**
	 *设备ID
	 */
	private Long dev_id;

	/**
	 *状态 1 启用 0 停用
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


	public void setPlan_id(Long plan_id)
	{
		this.plan_id = plan_id;
	}

	@Column(name = "plan_id")
	public Long getPlan_id()
	{
		return plan_id;
	}


	public void setDev_type(String dev_type)
	{
		this.dev_type = dev_type;
	}

	@Column(name = "dev_type")
	public String getDev_type()
	{
		return dev_type;
	}


	public void setDev_id(Long dev_id)
	{
		this.dev_id = dev_id;
	}

	@Column(name = "dev_id")
	public Long getDev_id()
	{
		return dev_id;
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
