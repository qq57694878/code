package com.youi.business.common.entity;


	/**
	 *虚拟化平台
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="HW_VM_PLATFORM")
public class HW_VM_PLATFORM implements Serializable
{
	public HW_VM_PLATFORM(){}
	public HW_VM_PLATFORM(Long id,String name,String brand,String type,String console,Long cuserid,Date ctime){
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.type = type;
		this.console = console;
		this.cuserid = cuserid;
		this.ctime = ctime;
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
	 *型号
	 */
	private String type;

	/**
	 *控制台地址
	 */
	private String console;

	/**
	 *
	 */
	private Long cuserid;

	/**
	 *
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


	public void setType(String type)
	{
		this.type = type;
	}

	@Column(name = "type")
	public String getType()
	{
		return type;
	}


	public void setConsole(String console)
	{
		this.console = console;
	}

	@Column(name = "console")
	public String getConsole()
	{
		return console;
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

}
