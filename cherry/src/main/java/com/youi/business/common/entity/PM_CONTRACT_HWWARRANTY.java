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
@Table(name="PM_CONTRACT_HWWARRANTY")
public class PM_CONTRACT_HWWARRANTY implements Serializable
{
	public PM_CONTRACT_HWWARRANTY(){}
	public PM_CONTRACT_HWWARRANTY(Long id, Long cid, String hw_type, Long devid, String status){
		this.id = id;
		this.cid = cid;
		this.hw_type = hw_type;
		this.devid = devid;
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
	 *设备类型
	 */
	private String hw_type;

	/**
	 *设备ID
	 */
	private Long devid;

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


	public void setHw_type(String hw_type)
	{
		this.hw_type = hw_type;
	}

	@Column(name = "hw_type")
	public String getHw_type()
	{
		return hw_type;
	}


	public void setDevid(Long devid)
	{
		this.devid = devid;
	}

	@Column(name = "devid")
	public Long getDevid()
	{
		return devid;
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
