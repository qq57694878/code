package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="PM_PROJECT")
public class PM_PROJECT implements Serializable
{
	public PM_PROJECT(){}
	public PM_PROJECT(Long id, String year, String name, Long apply_dept_id, java.math.BigDecimal total_investment, Date expected_sdate, Date expected_edate, String description, String status, Long applicant, Date apply_date){
		this.id = id;
		this.year = year;
		this.name = name;
		this.apply_dept_id = apply_dept_id;
		this.total_investment = total_investment;
		this.expected_sdate = expected_sdate;
		this.expected_edate = expected_edate;
		this.description = description;
		this.status = status;
		this.applicant = applicant;
		this.apply_date = apply_date;
}
	/**
	 *项目ID
	 */
	private Long id;

	/**
	 *年度
	 */
	private String year;

	/**
	 *项目名称
	 */
	private String name;

	/**
	 *申请部门
	 */
	private Long apply_dept_id;

	/**
	 *概算总投资
	 */
	private java.math.BigDecimal total_investment;

	/**
	 *项目预计开始时间
	 */
	private Date expected_sdate;

	/**
	 *项目预计完成时间
	 */
	private Date expected_edate;

	/**
	 *项目概述
	 */
	private String description;

	/**
	 *项目状态 1 已申请 0 草稿
	 */
	private String status;

	/**
	 *申请人
	 */
	private Long applicant;

	/**
	 *申请时间
	 */
	private Date apply_date;



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


	public void setYear(String year)
	{
		this.year = year;
	}

	@Column(name = "year")
	public String getYear()
	{
		return year;
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


	public void setApply_dept_id(Long apply_dept_id)
	{
		this.apply_dept_id = apply_dept_id;
	}

	@Column(name = "apply_dept_id")
	public Long getApply_dept_id()
	{
		return apply_dept_id;
	}


	public void setTotal_investment(java.math.BigDecimal total_investment)
	{
		this.total_investment = total_investment;
	}

	@Column(name = "total_investment")
	public java.math.BigDecimal getTotal_investment()
	{
		return total_investment;
	}


	public void setExpected_sdate(Date expected_sdate)
	{
		this.expected_sdate = expected_sdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expected_sdate" , length = 26)
	public Date getExpected_sdate()
	{
		return expected_sdate;
	}


	public void setExpected_edate(Date expected_edate)
	{
		this.expected_edate = expected_edate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expected_edate" , length = 26)
	public Date getExpected_edate()
	{
		return expected_edate;
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


	public void setStatus(String status)
	{
		this.status = status;
	}

	@Column(name = "status")
	public String getStatus()
	{
		return status;
	}


	public void setApplicant(Long applicant)
	{
		this.applicant = applicant;
	}

	@Column(name = "applicant")
	public Long getApplicant()
	{
		return applicant;
	}


	public void setApply_date(Date apply_date)
	{
		this.apply_date = apply_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "apply_date" , length = 26)
	public Date getApply_date()
	{
		return apply_date;
	}

}
