package com.youi.business.common.entity;


	/**
	 *监控主机状态
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="RES_MONITORING_HOSTTRIGGER")
public class RES_MONITORING_HOSTTRIGGER implements Serializable
{
	public RES_MONITORING_HOSTTRIGGER(){}
	public RES_MONITORING_HOSTTRIGGER(Long id, Long hostid, Long triggerid, String trigger_key, Long trigger_status, String trigger_message, String biz_type, Date lastchange){
		this.id = id;
		this.hostid = hostid;
		this.triggerid = triggerid;
		this.trigger_key = trigger_key;
		this.trigger_status = trigger_status;
		this.trigger_message = trigger_message;
		this.biz_type = biz_type;
		this.lastchange = lastchange;
}
	/**
	 *id
	 */
	private Long id;

	/**
	 *
	 */
	private Long hostid;

	/**
	 *触发器标识关键字
	 */
	private Long triggerid;

	/**
	 *触发器id
	 */
	private String trigger_key;

	/**
	 *主机状态0：有报警1：正常
	 */
	private Long trigger_status;

	/**
	 *报警消息
	 */
	private String trigger_message;

	/**
	 *监控类型biz：业务系统 host：服务器 db：数据库
	 */
	private String biz_type;

	/**
	 *最后改变时间
	 */
	private Date lastchange;



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


	public void setHostid(Long hostid)
	{
		this.hostid = hostid;
	}

	@Column(name = "hostid")
	public Long getHostid()
	{
		return hostid;
	}


	public void setTriggerid(Long triggerid)
	{
		this.triggerid = triggerid;
	}

	@Column(name = "triggerid")
	public Long getTriggerid()
	{
		return triggerid;
	}


	public void setTrigger_key(String trigger_key)
	{
		this.trigger_key = trigger_key;
	}

	@Column(name = "trigger_key")
	public String getTrigger_key()
	{
		return trigger_key;
	}


	public void setTrigger_status(Long trigger_status)
	{
		this.trigger_status = trigger_status;
	}

	@Column(name = "trigger_status")
	public Long getTrigger_status()
	{
		return trigger_status;
	}


	public void setTrigger_message(String trigger_message)
	{
		this.trigger_message = trigger_message;
	}

	@Column(name = "trigger_message")
	public String getTrigger_message()
	{
		return trigger_message;
	}


	public void setBiz_type(String biz_type)
	{
		this.biz_type = biz_type;
	}

	@Column(name = "biz_type")
	public String getBiz_type()
	{
		return biz_type;
	}


	public void setLastchange(Date lastchange)
	{
		this.lastchange = lastchange;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastchange" , length = 26)
	public Date getLastchange()
	{
		return lastchange;
	}

}
