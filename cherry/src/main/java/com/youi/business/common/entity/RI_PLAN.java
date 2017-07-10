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
@Table(name="RI_PLAN")
public class RI_PLAN implements Serializable
{
	public RI_PLAN(){}
	public RI_PLAN(Long id, String name, Long tpl_id, String period, Long worker_id, String status){
		this.id = id;
		this.name = name;
		this.tpl_id = tpl_id;
		this.period = period;
		this.worker_id = worker_id;
		this.status = status;
}
	/**
	 *ID
	 */
	private Long id;

	/**
	 *计划名称
	 */
	private String name;

	/**
	 *模板ID
	 */
	private Long tpl_id;

	/**
	 *周期 01 每日一次 11 每周一次 21 每月一次 31 每季一次 
	 */
	private String period;

	/**
	 *执行人ID
	 */
	private Long worker_id;

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


	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "name")
	public String getName()
	{
		return name;
	}


	public void setTpl_id(Long tpl_id)
	{
		this.tpl_id = tpl_id;
	}

	@Column(name = "tpl_id")
	public Long getTpl_id()
	{
		return tpl_id;
	}


	public void setPeriod(String period)
	{
		this.period = period;
	}

	@Column(name = "period")
	public String getPeriod()
	{
		return period;
	}


	public void setWorker_id(Long worker_id)
	{
		this.worker_id = worker_id;
	}

	@Column(name = "worker_id")
	public Long getWorker_id()
	{
		return worker_id;
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
