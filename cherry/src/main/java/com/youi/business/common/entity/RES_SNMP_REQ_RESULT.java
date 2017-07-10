package com.youi.business.common.entity;


	/**
	 *snmp请求结果表
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="RES_SNMP_REQ_RESULT")
public class RES_SNMP_REQ_RESULT implements Serializable
{
	public RES_SNMP_REQ_RESULT(){}
	public RES_SNMP_REQ_RESULT(Long id, String req_address, String req_oid, Date req_time, String resp_oid, String resp_value, String resp_ext1, String resp_ext2, String resp_ext3, String resp_ext4, Date ctime){
		this.id = id;
		this.req_address = req_address;
		this.req_oid = req_oid;
		this.req_time = req_time;
		this.resp_oid = resp_oid;
		this.resp_value = resp_value;
		this.resp_ext1 = resp_ext1;
		this.resp_ext2 = resp_ext2;
		this.resp_ext3 = resp_ext3;
		this.resp_ext4 = resp_ext4;
		this.ctime = ctime;
}
	/**
	 *id
	 */
	private Long id;

	/**
	 *请求的地址
	 */
	private String req_address;

	/**
	 *请求oid
	 */
	private String req_oid;

	/**
	 *请求时间，同一批次时间相同
	 */
	private Date req_time;

	/**
	 *返回的oid值
	 */
	private String resp_oid;

	/**
	 *返回的值
	 */
	private String resp_value;

	/**
	 *扩展字段1
	 */
	private String resp_ext1;

	/**
	 *扩展字段2
	 */
	private String resp_ext2;

	/**
	 *扩展字段3
	 */
	private String resp_ext3;

	/**
	 *扩展字段4
	 */
	private String resp_ext4;

	/**
	 *创建时间
	 */
	private Date ctime;



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


	public void setReq_address(String req_address)
	{
		this.req_address = req_address;
	}

	@Column(name = "req_address")
	public String getReq_address()
	{
		return req_address;
	}


	public void setReq_oid(String req_oid)
	{
		this.req_oid = req_oid;
	}

	@Column(name = "req_oid")
	public String getReq_oid()
	{
		return req_oid;
	}


	public void setReq_time(Date req_time)
	{
		this.req_time = req_time;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "req_time" , length = 26)
	public Date getReq_time()
	{
		return req_time;
	}


	public void setResp_oid(String resp_oid)
	{
		this.resp_oid = resp_oid;
	}

	@Column(name = "resp_oid")
	public String getResp_oid()
	{
		return resp_oid;
	}


	public void setResp_value(String resp_value)
	{
		this.resp_value = resp_value;
	}

	@Column(name = "resp_value")
	public String getResp_value()
	{
		return resp_value;
	}


	public void setResp_ext1(String resp_ext1)
	{
		this.resp_ext1 = resp_ext1;
	}

	@Column(name = "resp_ext1")
	public String getResp_ext1()
	{
		return resp_ext1;
	}


	public void setResp_ext2(String resp_ext2)
	{
		this.resp_ext2 = resp_ext2;
	}

	@Column(name = "resp_ext2")
	public String getResp_ext2()
	{
		return resp_ext2;
	}


	public void setResp_ext3(String resp_ext3)
	{
		this.resp_ext3 = resp_ext3;
	}

	@Column(name = "resp_ext3")
	public String getResp_ext3()
	{
		return resp_ext3;
	}


	public void setResp_ext4(String resp_ext4)
	{
		this.resp_ext4 = resp_ext4;
	}

	@Column(name = "resp_ext4")
	public String getResp_ext4()
	{
		return resp_ext4;
	}


	public void setCtime(Date ctime)
	{
		this.ctime = ctime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ctime" , length = 26)
	public Date getCtime()
	{
		return ctime;
	}

}
