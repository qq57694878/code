package com.youi.business.common.entity;


	/**
	 *监控主机item值
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="RES_MONITORING_HOSTITEMS")
public class RES_MONITORING_HOSTITEMS implements Serializable
{
	public RES_MONITORING_HOSTITEMS(){}
	public RES_MONITORING_HOSTITEMS(Long id, Long hostid, Long itemid, String item_key, String item_name, String item_value, Date lastchange){
		this.id = id;
		this.hostid = hostid;
		this.itemid = itemid;
		this.item_key = item_key;
		this.item_name = item_name;
		this.item_value = item_value;
		this.lastchange = lastchange;
}
	/**
	 *id
	 */
	private Long id;

	/**
	 *主机id
	 */
	private Long hostid;

	/**
	 *itemid
	 */
	private Long itemid;

	/**
	 *item
	 */
	private String item_key;

	/**
	 *item名称
	 */
	private String item_name;

	/**
	 *主机状态0：有报警1：正常
	 */
	private String item_value;

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


	public void setItemid(Long itemid)
	{
		this.itemid = itemid;
	}

	@Column(name = "itemid")
	public Long getItemid()
	{
		return itemid;
	}


	public void setItem_key(String item_key)
	{
		this.item_key = item_key;
	}

	@Column(name = "item_key")
	public String getItem_key()
	{
		return item_key;
	}


	public void setItem_name(String item_name)
	{
		this.item_name = item_name;
	}

	@Column(name = "item_name")
	public String getItem_name()
	{
		return item_name;
	}


	public void setItem_value(String item_value)
	{
		this.item_value = item_value;
	}

	@Column(name = "item_value")
	public String getItem_value()
	{
		return item_value;
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
