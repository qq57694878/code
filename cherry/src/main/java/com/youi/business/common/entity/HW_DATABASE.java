package com.youi.business.common.entity;


	/**
	 *数据库
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="HW_DATABASE")
public class HW_DATABASE implements Serializable
{
	public HW_DATABASE(){}
	public HW_DATABASE(Long id,String name,String brand,String version,String instance_name,String port,String address,Long cuserid,Date ctime,Long zabbixid,String zabbix_switch){
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.version = version;
		this.instance_name = instance_name;
		this.port = port;
		this.address = address;
		this.cuserid = cuserid;
		this.ctime = ctime;
		this.zabbixid = zabbixid;
		this.zabbix_switch = zabbix_switch;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *名称
	 */
	private String name;

	/**
	 *品牌
	 */
	private String brand;

	/**
	 *版本号
	 */
	private String version;

	/**
	 *实例名
	 */
	private String instance_name;

	/**
	 *端口
	 */
	private String port;

	/**
	 *IP地址
	 */
	private String address;

	/**
	 *
	 */
	private Long cuserid;

	/**
	 *
	 */
	private Date ctime;

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


	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "name")
	public String getName()
	{
		return name;
	}


	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	@Column(name = "brand")
	public String getBrand()
	{
		return brand;
	}


	public void setVersion(String version)
	{
		this.version = version;
	}

	@Column(name = "version")
	public String getVersion()
	{
		return version;
	}


	public void setInstance_name(String instance_name)
	{
		this.instance_name = instance_name;
	}

	@Column(name = "instance_name")
	public String getInstance_name()
	{
		return instance_name;
	}


	public void setPort(String port)
	{
		this.port = port;
	}

	@Column(name = "port")
	public String getPort()
	{
		return port;
	}


	public void setAddress(String address)
	{
		this.address = address;
	}

	@Column(name = "address")
	public String getAddress()
	{
		return address;
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
