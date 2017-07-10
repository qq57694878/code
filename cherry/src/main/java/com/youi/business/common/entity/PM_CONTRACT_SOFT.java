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
@Table(name="PM_CONTRACT_SOFT")
public class PM_CONTRACT_SOFT implements Serializable
{
	public PM_CONTRACT_SOFT(){}
	public PM_CONTRACT_SOFT(Long id, Long cid, Long biz_id, String status){
		this.id = id;
		this.cid = cid;
		this.biz_id = biz_id;
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
	 *关联项目ID
	 */
	private Long biz_id;

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
