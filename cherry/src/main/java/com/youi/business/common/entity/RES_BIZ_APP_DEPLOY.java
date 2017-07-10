package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="RES_BIZ_APP_DEPLOY")
public class RES_BIZ_APP_DEPLOY implements Serializable
{
	public RES_BIZ_APP_DEPLOY(){}
	public RES_BIZ_APP_DEPLOY(Long id,Long biz_app_id,String ip,String port,String server_name,String deploy_type,Long cuserid,Date cdate,Long zabbixid,String zabbix_switch){
		this.id = id;
		this.biz_app_id = biz_app_id;
		this.ip = ip;
		this.port = port;
		this.server_name = server_name;
		this.deploy_type = deploy_type;
		this.cuserid = cuserid;
		this.cdate = cdate;
		this.zabbixid = zabbixid;
		this.zabbix_switch = zabbix_switch;
}
	/**
	 *ID
	 */
	private Long id;

	/**
	 *res_bussiness_app表ID
	 */
	private Long biz_app_id;

	/**
	 *数据库服务IP地址
	 */
	private String ip;

	/**
	 *服务端口
	 */
	private String port;

	/**
	 *服务名称
	 */
	private String server_name;

	/**
	 *部署类型：1-外网访问地址，2-内网集群地址，3-内网访问地址
	 */
	private String deploy_type;

	/**
	 *
	 */
	private Long cuserid;

	/**
	 *
	 */
	private Date cdate;

	/**
	 *
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


	public void setBiz_app_id(Long biz_app_id)
	{
		this.biz_app_id = biz_app_id;
	}

	@Column(name = "biz_app_id")
	public Long getBiz_app_id()
	{
		return biz_app_id;
	}


	public void setIp(String ip)
	{
		this.ip = ip;
	}

	@Column(name = "ip")
	public String getIp()
	{
		return ip;
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


	public void setServer_name(String server_name)
	{
		this.server_name = server_name;
	}

	@Column(name = "server_name")
	public String getServer_name()
	{
		return server_name;
	}


	public void setDeploy_type(String deploy_type)
	{
		this.deploy_type = deploy_type;
	}

	@Column(name = "deploy_type")
	public String getDeploy_type()
	{
		return deploy_type;
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


	public void setCdate(Date cdate)
	{
		this.cdate = cdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CDATE" , length = 26)
	public Date getCdate()
	{
		return cdate;
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
