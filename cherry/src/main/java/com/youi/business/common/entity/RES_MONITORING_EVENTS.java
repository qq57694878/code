package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="RES_MONITORING_EVENTS")
public class RES_MONITORING_EVENTS implements Serializable
{
	public RES_MONITORING_EVENTS(){}
	public RES_MONITORING_EVENTS(Long id, Long eventid, String type, Long objectid, Long hostid, Long value, Long clock, String trigger_description, String trigger_comments, Date ctime){
		this.id = id;
		this.eventid = eventid;
		this.type = type;
		this.objectid = objectid;
		this.hostid = hostid;
		this.value = value;
		this.clock = clock;
		this.trigger_description = trigger_description;
		this.trigger_comments = trigger_comments;
		this.ctime = ctime;
}
	/**
	 *id
	 */
	private Long id;

	/**
	 *时间id
	 */
	private Long eventid;

	/**
	 *监控类型：业务系统 host：服务器 db：数据库
	 */
	private String type;

	/**
	 *triggerid
	 */
	private Long objectid;

	/**
	 *hostid
	 */
	private Long hostid;

	/**
	 *值1：有问题 0：正常
	 */
	private Long value;

	/**
	 *事件发生的时间
	 */
	private Long clock;

	/**
	 *触发器名字
	 */
	private String trigger_description;

	/**
	 *触发器说明
	 */
	private String trigger_comments;

	/**
	 *录入时间
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


	public void setEventid(Long eventid)
	{
		this.eventid = eventid;
	}

	@Column(name = "eventid")
	public Long getEventid()
	{
		return eventid;
	}


	public void setType(String type)
	{
		this.type = type;
	}

	@Column(name = "type")
	public String getType()
	{
		return type;
	}


	public void setObjectid(Long objectid)
	{
		this.objectid = objectid;
	}

	@Column(name = "objectid")
	public Long getObjectid()
	{
		return objectid;
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


	public void setValue(Long value)
	{
		this.value = value;
	}

	@Column(name = "value")
	public Long getValue()
	{
		return value;
	}


	public void setClock(Long clock)
	{
		this.clock = clock;
	}

	@Column(name = "clock")
	public Long getClock()
	{
		return clock;
	}


	public void setTrigger_description(String trigger_description)
	{
		this.trigger_description = trigger_description;
	}

	@Column(name = "trigger_description")
	public String getTrigger_description()
	{
		return trigger_description;
	}


	public void setTrigger_comments(String trigger_comments)
	{
		this.trigger_comments = trigger_comments;
	}

	@Column(name = "trigger_comments")
	public String getTrigger_comments()
	{
		return trigger_comments;
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
