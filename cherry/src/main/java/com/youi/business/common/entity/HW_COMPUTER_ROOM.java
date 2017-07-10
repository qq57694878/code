package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="HW_COMPUTER_ROOM")
public class HW_COMPUTER_ROOM implements Serializable
{
	public HW_COMPUTER_ROOM(){}
	public HW_COMPUTER_ROOM(Long id, String name, String description, String area, Date complete_date, String status, Date cdate, Long userid){
		this.id = id;
		this.name = name;
		this.description = description;
		this.area = area;
		this.complete_date = complete_date;
		this.status = status;
		this.cdate = cdate;
		this.userid = userid;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *机房名称
	 */
	private String name;

	/**
	 *描述
	 */
	private String description;

	/**
	 *面积
	 */
	private String area;

	/**
	 *
	 */
	private Date complete_date;

	/**
	 *
	 */
	private String status;

	/**
	 *
	 */
	private Date cdate;

	/**
	 *
	 */
	private Long userid;



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


	public void setArea(String area)
	{
		this.area = area;
	}

	@Column(name = "area")
	public String getArea()
	{
		return area;
	}


	public void setComplete_date(Date complete_date)
	{
		this.complete_date = complete_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "complete_date" , length = 26)
	public Date getComplete_date()
	{
		return complete_date;
	}


	public void setStatus(String status)
	{
		this.status = status;
	}

	@Column(name = "status")
	public String getStatus()
	{
		return status;
	}


	public void setCdate(Date cdate)
	{
		this.cdate = cdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cdate" , length = 26)
	public Date getCdate()
	{
		return cdate;
	}


	public void setUserid(Long userid)
	{
		this.userid = userid;
	}

	@Column(name = "userid")
	public Long getUserid()
	{
		return userid;
	}

}
