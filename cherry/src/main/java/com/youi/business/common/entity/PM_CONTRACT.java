package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="PM_CONTRACT")
public class PM_CONTRACT implements Serializable
{
	public PM_CONTRACT(){}
	public PM_CONTRACT(Long id, Long pid, String cno, String name, Long vender, java.math.BigDecimal amount, Date sdate, Date edate, Long responsible_person, Date estimated_reception_time, Long contract_file, String supervision, String status, Long applicant, Date apply_date, Date reception_time, String expert, Long expert_opinion_file, Long verdict_file, Date next_estimated_date, java.math.BigDecimal amount_paid, java.math.BigDecimal unpaid_amount, java.math.BigDecimal nextpay_amount, String payment_status){
		this.id = id;
		this.pid = pid;
		this.cno = cno;
		this.name = name;
		this.vender = vender;
		this.amount = amount;
		this.sdate = sdate;
		this.edate = edate;
		this.responsible_person = responsible_person;
		this.estimated_reception_time = estimated_reception_time;
		this.contract_file = contract_file;
		this.supervision = supervision;
		this.status = status;
		this.applicant = applicant;
		this.apply_date = apply_date;
		this.reception_time = reception_time;
		this.expert = expert;
		this.expert_opinion_file = expert_opinion_file;
		this.verdict_file = verdict_file;
		this.next_estimated_date = next_estimated_date;
		this.amount_paid = amount_paid;
		this.unpaid_amount = unpaid_amount;
		this.nextpay_amount = nextpay_amount;
		this.payment_status = payment_status;
}
	/**
	 *合同ID
	 */
	private Long id;

	/**
	 *关联采购项目ID
	 */
	private Long pid;

	/**
	 *合同编号
	 */
	private String cno;

	/**
	 *合同名称
	 */
	private String name;

	/**
	 *中标单位 外协公司id
	 */
	private Long vender;

	/**
	 *合同金额
	 */
	private java.math.BigDecimal amount;

	/**
	 *合同开始时间
	 */
	private Date sdate;

	/**
	 *合同结束时间
	 */
	private Date edate;

	/**
	 *合同负责人
	 */
	private Long responsible_person;

	/**
	 *预计验收时间
	 */
	private Date estimated_reception_time;

	/**
	 *合同影印件id
	 */
	private Long contract_file;

	/**
	 *监理公司
	 */
	private String supervision;

	/**
	 *合同状态 0 登记中 1 计划中 2 执行中 3 已完成
	 */
	private String status;

	/**
	 *申请人ID
	 */
	private Long applicant;

	/**
	 *合同登记时间
	 */
	private Date apply_date;

	/**
	 *实际验收时间
	 */
	private Date reception_time;

	/**
	 *参与验收专家
	 */
	private String expert;

	/**
	 *验收专家意见
	 */
	private Long expert_opinion_file;

	/**
	 *结论
	 */
	private Long verdict_file;

	/**
	 *合同下次预计付款时间
	 */
	private Date next_estimated_date;

	/**
	 *已付金额
	 */
	private java.math.BigDecimal amount_paid;

	/**
	 *未付款金额
	 */
	private java.math.BigDecimal unpaid_amount;

	/**
	 *下次付款金额
	 */
	private java.math.BigDecimal nextpay_amount;

	/**
	 *支付状态
	 */
	private String payment_status;



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


	public void setCno(String cno)
	{
		this.cno = cno;
	}

	@Column(name = "cno")
	public String getCno()
	{
		return cno;
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


	public void setVender(Long vender)
	{
		this.vender = vender;
	}

	@Column(name = "vender")
	public Long getVender()
	{
		return vender;
	}


	public void setAmount(java.math.BigDecimal amount)
	{
		this.amount = amount;
	}

	@Column(name = "amount")
	public java.math.BigDecimal getAmount()
	{
		return amount;
	}


	public void setSdate(Date sdate)
	{
		this.sdate = sdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sdate" , length = 26)
	public Date getSdate()
	{
		return sdate;
	}


	public void setEdate(Date edate)
	{
		this.edate = edate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "edate" , length = 26)
	public Date getEdate()
	{
		return edate;
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


	public void setEstimated_reception_time(Date estimated_reception_time)
	{
		this.estimated_reception_time = estimated_reception_time;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "estimated_reception_time" , length = 26)
	public Date getEstimated_reception_time()
	{
		return estimated_reception_time;
	}


	public void setContract_file(Long contract_file)
	{
		this.contract_file = contract_file;
	}

	@Column(name = "contract_file")
	public Long getContract_file()
	{
		return contract_file;
	}


	public void setSupervision(String supervision)
	{
		this.supervision = supervision;
	}

	@Column(name = "supervision")
	public String getSupervision()
	{
		return supervision;
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


	public void setReception_time(Date reception_time)
	{
		this.reception_time = reception_time;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reception_time" , length = 26)
	public Date getReception_time()
	{
		return reception_time;
	}


	public void setExpert(String expert)
	{
		this.expert = expert;
	}

	@Column(name = "expert")
	public String getExpert()
	{
		return expert;
	}


	public void setExpert_opinion_file(Long expert_opinion_file)
	{
		this.expert_opinion_file = expert_opinion_file;
	}

	@Column(name = "expert_opinion_file")
	public Long getExpert_opinion_file()
	{
		return expert_opinion_file;
	}


	public void setVerdict_file(Long verdict_file)
	{
		this.verdict_file = verdict_file;
	}

	@Column(name = "verdict_file")
	public Long getVerdict_file()
	{
		return verdict_file;
	}


	public void setNext_estimated_date(Date next_estimated_date)
	{
		this.next_estimated_date = next_estimated_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "next_estimated_date" , length = 26)
	public Date getNext_estimated_date()
	{
		return next_estimated_date;
	}


	public void setAmount_paid(java.math.BigDecimal amount_paid)
	{
		this.amount_paid = amount_paid;
	}

	@Column(name = "amount_paid")
	public java.math.BigDecimal getAmount_paid()
	{
		return amount_paid;
	}


	public void setUnpaid_amount(java.math.BigDecimal unpaid_amount)
	{
		this.unpaid_amount = unpaid_amount;
	}

	@Column(name = "unpaid_amount")
	public java.math.BigDecimal getUnpaid_amount()
	{
		return unpaid_amount;
	}


	public void setNextpay_amount(java.math.BigDecimal nextpay_amount)
	{
		this.nextpay_amount = nextpay_amount;
	}

	@Column(name = "nextpay_amount")
	public java.math.BigDecimal getNextpay_amount()
	{
		return nextpay_amount;
	}


	public void setPayment_status(String payment_status)
	{
		this.payment_status = payment_status;
	}

	@Column(name = "payment_status")
	public String getPayment_status()
	{
		return payment_status;
	}

}
