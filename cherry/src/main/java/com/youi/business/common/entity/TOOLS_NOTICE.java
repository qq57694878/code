package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="TOOLS_NOTICE")
public class TOOLS_NOTICE implements Serializable
{
	public TOOLS_NOTICE(){}
	public TOOLS_NOTICE(Long id, String notice_type, String notice_info, Date ctime, Long cuserid, Long ruserid, String has_read, Date read_time){
		this.id = id;
		this.notice_type = notice_type;
		this.notice_info = notice_info;
		this.ctime = ctime;
		this.cuserid = cuserid;
		this.ruserid = ruserid;
		this.has_read = has_read;
		this.read_time = read_time;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *通知类型
	 */
	private String notice_type;

	/**
	 *
	 */
	private String notice_info;

	/**
	 *创建时间
	 */
	private Date ctime;

	/**
	 *发送人ID，系统发送写0
	 */
	private Long cuserid;

	/**
	 *收件人ID
	 */
	private Long ruserid;

	/**
	 *是否已读，0-未读，1-已读
	 */
	private String has_read;

	/**
	 *阅读时间
	 */
	private Date read_time;



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


	public void setNotice_type(String notice_type)
	{
		this.notice_type = notice_type;
	}

	@Column(name = "notice_type")
	public String getNotice_type()
	{
		return notice_type;
	}


	public void setNotice_info(String notice_info)
	{
		this.notice_info = notice_info;
	}

	@Column(name = "notice_info")
	public String getNotice_info()
	{
		return notice_info;
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


	public void setRuserid(Long ruserid)
	{
		this.ruserid = ruserid;
	}

	@Column(name = "ruserid")
	public Long getRuserid()
	{
		return ruserid;
	}


	public void setHas_read(String has_read)
	{
		this.has_read = has_read;
	}

	@Column(name = "has_read")
	public String getHas_read()
	{
		return has_read;
	}


	public void setRead_time(Date read_time)
	{
		this.read_time = read_time;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "read_time" , length = 26)
	public Date getRead_time()
	{
		return read_time;
	}

}
