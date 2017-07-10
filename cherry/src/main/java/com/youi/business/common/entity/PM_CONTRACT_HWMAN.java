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
@Table(name="PM_CONTRACT_HWMAN")
public class PM_CONTRACT_HWMAN implements Serializable
{
	public PM_CONTRACT_HWMAN(){}
	public PM_CONTRACT_HWMAN(Long id, Long cid, Long inspection_id, String status){
		this.id = id;
		this.cid = cid;
		this.inspection_id = inspection_id;
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
	 *巡检ID
	 */
	private Long inspection_id;

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


	public void setInspection_id(Long inspection_id)
	{
		this.inspection_id = inspection_id;
	}

	@Column(name = "inspection_id")
	public Long getInspection_id()
	{
		return inspection_id;
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
