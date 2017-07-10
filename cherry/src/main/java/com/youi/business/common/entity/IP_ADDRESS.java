package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="IP_ADDRESS")
public class IP_ADDRESS implements Serializable
{
	public IP_ADDRESS(){}
	public IP_ADDRESS(Long id,String biz_type,Long biz_id,String ip_add,Long subnet_mask,String gateway,String mac,Long cuserid,Date ctime,Date mac_fresh_time,String ip_type,Long zabbixid,String zabbix_switch){
		this.id = id;
		this.biz_type = biz_type;
		this.biz_id = biz_id;
		this.ip_add = ip_add;
		this.subnet_mask = subnet_mask;
		this.gateway = gateway;
		this.mac = mac;
		this.cuserid = cuserid;
		this.ctime = ctime;
		this.mac_fresh_time = mac_fresh_time;
		this.ip_type = ip_type;
		this.zabbixid = zabbixid;
		this.zabbix_switch = zabbix_switch;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *业务类型,表名
	 */
	private String biz_type;

	/**
	 *业务表主键
	 */
	private Long biz_id;

	/**
	 *ip地址
	 */
	private String ip_add;

	/**
	 *子网掩码
	 */
	private Long subnet_mask;

	/**
	 *网关地址
	 */
	private String gateway;

	/**
	 *mac地址
	 */
	private String mac;

	/**
	 *
	 */
	private Long cuserid;

	/**
	 *
	 */
	private Date ctime;

	/**
	 *mac地址刷新时间
	 */
	private Date mac_fresh_time;

	/**
	 *ip类型1-主IP，2-从IP
	 */
	private String ip_type;

	/**
	 *zabbix监控项ID
	 */
	private Long zabbixid;

	/**
	 *监控开启状态1-开启，0-关闭
	 */
	private String zabbix_switch;



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


	public void setBiz_type(String biz_type)
	{
		this.biz_type = biz_type;
	}

	@Column(name = "biz_type")
	public String getBiz_type()
	{
		return biz_type;
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


	public void setIp_add(String ip_add)
	{
		this.ip_add = ip_add;
	}

	@Column(name = "ip_add")
	public String getIp_add()
	{
		return ip_add;
	}


	public void setSubnet_mask(Long subnet_mask)
	{
		this.subnet_mask = subnet_mask;
	}

	@Column(name = "subnet_mask")
	public Long getSubnet_mask()
	{
		return subnet_mask;
	}


	public void setGateway(String gateway)
	{
		this.gateway = gateway;
	}

	@Column(name = "gateway")
	public String getGateway()
	{
		return gateway;
	}


	public void setMac(String mac)
	{
		this.mac = mac;
	}

	@Column(name = "mac")
	public String getMac()
	{
		return mac;
	}


	public void setCuserid(Long cuserid)
	{
		this.cuserid = cuserid;
	}

	@Column(name = "cuserid")
	public Long getCuserid()
	{
		return cuserid;
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


	public void setMac_fresh_time(Date mac_fresh_time)
	{
		this.mac_fresh_time = mac_fresh_time;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "mac_fresh_time" , length = 26)
	public Date getMac_fresh_time()
	{
		return mac_fresh_time;
	}


	public void setIp_type(String ip_type)
	{
		this.ip_type = ip_type;
	}

	@Column(name = "ip_type")
	public String getIp_type()
	{
		return ip_type;
	}


	public void setZabbixid(Long zabbixid)
	{
		this.zabbixid = zabbixid;
	}

	@Column(name = "zabbixid")
	public Long getZabbixid()
	{
		return zabbixid;
	}


	public void setZabbix_switch(String zabbix_switch)
	{
		this.zabbix_switch = zabbix_switch;
	}

	@Column(name = "zabbix_switch")
	public String getZabbix_switch()
	{
		return zabbix_switch;
	}

}
