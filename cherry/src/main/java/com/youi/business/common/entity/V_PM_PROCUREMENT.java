package com.youi.business.common.entity;


	/**
	 *VIEW
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="V_PM_PROCUREMENT")
public class V_PM_PROCUREMENT implements Serializable
{
	public V_PM_PROCUREMENT(){}
	public V_PM_PROCUREMENT(Long id, Long pid, String name, String budget_amount, String procurement_method, Long responsible_person, String bidding_company, Date bidding_hang_date, Date bidding_open_date, Date bidding_win_date, String bid_amount, Date signup_end_date, Date contract_record_date, String bidding_win_company, String status, Long applicant, Date apply_date, Long bid_notification){
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.budget_amount = budget_amount;
		this.procurement_method = procurement_method;
		this.responsible_person = responsible_person;
		this.bidding_company = bidding_company;
		this.bidding_hang_date = bidding_hang_date;
		this.bidding_open_date = bidding_open_date;
		this.bidding_win_date = bidding_win_date;
		this.bid_amount = bid_amount;
		this.signup_end_date = signup_end_date;
		this.contract_record_date = contract_record_date;
		this.bidding_win_company = bidding_win_company;
		this.status = status;
		this.applicant = applicant;
		this.apply_date = apply_date;
		this.bid_notification = bid_notification;
}
	/**
	 *采购项目ID
	 */
	private Long id;

	/**
	 *关联项目ID
	 */
	private Long pid;

	/**
	 *采购项目名称
	 */
	private String name;

	/**
	 *预算金额
	 */
	private String budget_amount;

	/**
	 *采购方式码值
	 */
	private String procurement_method;

	/**
	 *负责人
	 */
	private Long responsible_person;

	/**
	 *招标代理公司
	 */
	private String bidding_company;

	/**
	 *挂标日期
	 */
	private Date bidding_hang_date;

	/**
	 *开标日期
	 */
	private Date bidding_open_date;

	/**
	 *中标日期
	 */
	private Date bidding_win_date;

	/**
	 *中标金额
	 */
	private String bid_amount;

	/**
	 *报名截止日期
	 */
	private Date signup_end_date;

	/**
	 *合同备案日期
	 */
	private Date contract_record_date;

	/**
	 *中标单位
	 */
	private String bidding_win_company;

	/**
	 *
	 */
	private String status;

	/**
	 *申请人ID
	 */
	private Long applicant;

	/**
	 *项目登记时间
	 */
	private Date apply_date;

	/**
	 *中标通知书附件ID
	 */
	private Long bid_notification;



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


	public void setPid(Long pid)
	{
		this.pid = pid;
	}

	@Column(name = "pid")
	public Long getPid()
	{
		return pid;
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


	public void setBudget_amount(String budget_amount)
	{
		this.budget_amount = budget_amount;
	}

	@Column(name = "budget_amount")
	public String getBudget_amount()
	{
		return budget_amount;
	}


	public void setProcurement_method(String procurement_method)
	{
		this.procurement_method = procurement_method;
	}

	@Column(name = "procurement_method")
	public String getProcurement_method()
	{
		return procurement_method;
	}


	public void setResponsible_person(Long responsible_person)
	{
		this.responsible_person = responsible_person;
	}

	@Column(name = "responsible_person")
	public Long getResponsible_person()
	{
		return responsible_person;
	}


	public void setBidding_company(String bidding_company)
	{
		this.bidding_company = bidding_company;
	}

	@Column(name = "bidding_company")
	public String getBidding_company()
	{
		return bidding_company;
	}


	public void setBidding_hang_date(Date bidding_hang_date)
	{
		this.bidding_hang_date = bidding_hang_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bidding_hang_date" , length = 26)
	public Date getBidding_hang_date()
	{
		return bidding_hang_date;
	}


	public void setBidding_open_date(Date bidding_open_date)
	{
		this.bidding_open_date = bidding_open_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bidding_open_date" , length = 26)
	public Date getBidding_open_date()
	{
		return bidding_open_date;
	}


	public void setBidding_win_date(Date bidding_win_date)
	{
		this.bidding_win_date = bidding_win_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "bidding_win_date" , length = 26)
	public Date getBidding_win_date()
	{
		return bidding_win_date;
	}


	public void setBid_amount(String bid_amount)
	{
		this.bid_amount = bid_amount;
	}

	@Column(name = "bid_amount")
	public String getBid_amount()
	{
		return bid_amount;
	}


	public void setSignup_end_date(Date signup_end_date)
	{
		this.signup_end_date = signup_end_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "signup_end_date" , length = 26)
	public Date getSignup_end_date()
	{
		return signup_end_date;
	}


	public void setContract_record_date(Date contract_record_date)
	{
		this.contract_record_date = contract_record_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "contract_record_date" , length = 26)
	public Date getContract_record_date()
	{
		return contract_record_date;
	}


	public void setBidding_win_company(String bidding_win_company)
	{
		this.bidding_win_company = bidding_win_company;
	}

	@Column(name = "bidding_win_company")
	public String getBidding_win_company()
	{
		return bidding_win_company;
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


	public void setBid_notification(Long bid_notification)
	{
		this.bid_notification = bid_notification;
	}

	@Column(name = "bid_notification")
	public Long getBid_notification()
	{
		return bid_notification;
	}

}
