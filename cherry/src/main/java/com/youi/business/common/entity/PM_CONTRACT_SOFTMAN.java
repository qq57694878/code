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
@Table(name="PM_CONTRACT_SOFTMAN")
public class PM_CONTRACT_SOFTMAN implements Serializable
{
	public PM_CONTRACT_SOFTMAN(){}
	public PM_CONTRACT_SOFTMAN(Long id, Long cid, Long biz_id, String scope, String status){
		this.id = id;
		this.cid = cid;
		this.biz_id = biz_id;
		this.scope = scope;
		this.status = status;
}
	/**
	 *无意义主键
	 */
	private Long id;

	/**
	 *合同ID
	 */
	private Long cid;

	/**
	 *关联业务系统ID
	 */
	private Long biz_id;

	/**
	 *需求变更 1 数据修改 2 数据查询 3 程序发布,以逗号分隔
	 */
	private String scope;

	/**
	 *
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


	public void setCid(Long cid)
	{
		this.cid = cid;
	}

	@Column(name = "cid")
	public Long getCid()
	{
		return cid;
	}


	public void setBiz_id(Long biz_id)
	{
		this.biz_id = biz_id;
	}

	@Column(name = "biz_id")
	public Long getBiz_id()
	{
		return biz_id;
	}


	public void setScope(String scope)
	{
		this.scope = scope;
	}

	@Column(name = "scope")
	public String getScope()
	{
		return scope;
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
