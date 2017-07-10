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
@Table(name="PM_CONTRACT_EXEC")
public class PM_CONTRACT_EXEC implements Serializable
{
	public PM_CONTRACT_EXEC(){}
	public PM_CONTRACT_EXEC(Long id, Long cid, String ctype, Long hwlist_file, Long hwlist_arrival_file, String status){
		this.id = id;
		this.cid = cid;
		this.ctype = ctype;
		this.hwlist_file = hwlist_file;
		this.hwlist_arrival_file = hwlist_arrival_file;
		this.status = status;
}
	/**
	 *无意义主键
	 */
	private Long id;

	/**
	 *关联合同ID
	 */
	private Long cid;

	/**
	 *合同执行类型 0 软件开发类 1 软件运维类 2 硬件购置类 3 硬件运维类 4 硬件质保类，以逗号分隔 比如 0,2,3
	 */
	private String ctype;

	/**
	 *合同执行计划硬件购置单
	 */
	private Long hwlist_file;

	/**
	 *用户上传的硬件到货单 用于一次性导入硬件设备
	 */
	private Long hwlist_arrival_file;

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


	public void setCtype(String ctype)
	{
		this.ctype = ctype;
	}

	@Column(name = "ctype")
	public String getCtype()
	{
		return ctype;
	}


	public void setHwlist_file(Long hwlist_file)
	{
		this.hwlist_file = hwlist_file;
	}

	@Column(name = "hwlist_file")
	public Long getHwlist_file()
	{
		return hwlist_file;
	}


	public void setHwlist_arrival_file(Long hwlist_arrival_file)
	{
		this.hwlist_arrival_file = hwlist_arrival_file;
	}

	@Column(name = "hwlist_arrival_file")
	public Long getHwlist_arrival_file()
	{
		return hwlist_arrival_file;
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
