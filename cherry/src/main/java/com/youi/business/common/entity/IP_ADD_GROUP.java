package com.youi.business.common.entity;


	/**
	 *IP地址组
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="IP_ADD_GROUP")
public class IP_ADD_GROUP implements Serializable
{
	public IP_ADD_GROUP(){}
	public IP_ADD_GROUP(Long id, String name, String description, String ip_add, Long subnet_mask, String gateway, Long red_count, Long green_count, Long yellow_count, Long gray_count, Long cuserid, Date ctime){
		this.id = id;
		this.name = name;
		this.description = description;
		this.ip_add = ip_add;
		this.subnet_mask = subnet_mask;
		this.gateway = gateway;
		this.red_count = red_count;
		this.green_count = green_count;
		this.yellow_count = yellow_count;
		this.gray_count = gray_count;
		this.cuserid = cuserid;
		this.ctime = ctime;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *网段名称
	 */
	private String name;

	/**
	 *用途
	 */
	private String description;

	/**
	 *ip地址
	 */
	private String ip_add;

	/**
	 *子网掩码，存数字，读字符串
	 */
	private Long subnet_mask;

	/**
	 *网关
	 */
	private String gateway;

	/**
	 *未规划的活动IP数量
	 */
	private Long red_count;

	/**
	 *规划的活动IP数量
	 */
	private Long green_count;

	/**
	 *规划的非活动IP数量
	 */
	private Long yellow_count;

	/**
	 *未规划的非活动IP数量
	 */
	private Long gray_count;

	/**
	 *创建人
	 */
	private Long cuserid;

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


	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "name")
	public String getName()
	{
		return name;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "description")
	public String getDescription()
	{
		return description;
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


	public void setRed_count(Long red_count)
	{
		this.red_count = red_count;
	}

	@Column(name = "red_count")
	public Long getRed_count()
	{
		return red_count;
	}


	public void setGreen_count(Long green_count)
	{
		this.green_count = green_count;
	}

	@Column(name = "green_count")
	public Long getGreen_count()
	{
		return green_count;
	}


	public void setYellow_count(Long yellow_count)
	{
		this.yellow_count = yellow_count;
	}

	@Column(name = "yellow_count")
	public Long getYellow_count()
	{
		return yellow_count;
	}


	public void setGray_count(Long gray_count)
	{
		this.gray_count = gray_count;
	}

	@Column(name = "gray_count")
	public Long getGray_count()
	{
		return gray_count;
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
