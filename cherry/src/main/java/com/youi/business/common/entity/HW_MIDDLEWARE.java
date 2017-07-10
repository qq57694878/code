package com.youi.business.common.entity;


	/**
	 *中间件
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="HW_MIDDLEWARE")
public class HW_MIDDLEWARE implements Serializable
{
	public HW_MIDDLEWARE(){}
	public HW_MIDDLEWARE(Long id,String name,String brand,String version,String console,Long cuserid,Date ctime){
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.version = version;
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
	 *版本
	 */
	private String version;

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


	public void setVersion(String version)
	{
		this.version = version;
	}

	@Column(name = "version")
	public String getVersion()
	{
		return version;
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
