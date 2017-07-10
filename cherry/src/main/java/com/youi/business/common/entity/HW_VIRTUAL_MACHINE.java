package com.youi.business.common.entity;


	/**
	 *虚拟机
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="HW_VIRTUAL_MACHINE")
public class HW_VIRTUAL_MACHINE implements Serializable
{
	public HW_VIRTUAL_MACHINE(){}
	public HW_VIRTUAL_MACHINE(Long id, Long platform_id, String name, Long core_num, Long memory, Long disk, String system, Date ctime, Long cuserid, Long zabbixid, String zabbix_switch){
		this.id = id;
		this.platform_id = platform_id;
		this.name = name;
		this.core_num = core_num;
		this.memory = memory;
		this.disk = disk;
		this.system = system;
		this.ctime = ctime;
		this.cuserid = cuserid;
		this.zabbixid = zabbixid;
		this.zabbix_switch = zabbix_switch;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *虚拟化平台ID
	 */
	private Long platform_id;

	/**
	 *名称
	 */
	private String name;

	/**
	 *cpu核心数
	 */
	private Long core_num;

	/**
	 *内存容量
	 */
	private Long memory;

	/**
	 *硬盘容量
	 */
	private Long disk;

	/**
	 *操作系统
	 */
	private String system;

	/**
	 *
	 */
	private Date ctime;

	/**
	 *
	 */
	private Long cuserid;

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


	public void setPlatform_id(Long platform_id)
	{
		this.platform_id = platform_id;
	}

	@Column(name = "platform_id")
	public Long getPlatform_id()
	{
		return platform_id;
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


	public void setCore_num(Long core_num)
	{
		this.core_num = core_num;
	}

	@Column(name = "core_num")
	public Long getCore_num()
	{
		return core_num;
	}


	public void setMemory(Long memory)
	{
		this.memory = memory;
	}

	@Column(name = "memory")
	public Long getMemory()
	{
		return memory;
	}


	public void setDisk(Long disk)
	{
		this.disk = disk;
	}

	@Column(name = "disk")
	public Long getDisk()
	{
		return disk;
	}


	public void setSystem(String system)
	{
		this.system = system;
	}

	@Column(name = "system")
	public String getSystem()
	{
		return system;
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


	public void setCuserid(Long cuserid)
	{
		this.cuserid = cuserid;
	}

	@Column(name = "cuserid")
	public Long getCuserid()
	{
		return cuserid;
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
