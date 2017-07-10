package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="PM_PAYMENT")
public class PM_PAYMENT implements Serializable
{
	public PM_PAYMENT(){}
	public PM_PAYMENT(Long id, Long cid, String cno, String terms, java.math.BigDecimal estimated_amount, java.math.BigDecimal amount, Date estimated_date, Date paydate, String invoice_no, Long invoice_file, String check_no, Long check_file, String status){
		this.id = id;
		this.cid = cid;
		this.cno = cno;
		this.terms = terms;
		this.estimated_amount = estimated_amount;
		this.amount = amount;
		this.estimated_date = estimated_date;
		this.paydate = paydate;
		this.invoice_no = invoice_no;
		this.invoice_file = invoice_file;
		this.check_no = check_no;
		this.check_file = check_file;
		this.status = status;
}
	/**
	 *ID
	 */
	private Long id;

	/**
	 *关联合同ID
	 */
	private Long cid;

	/**
	 *合同编号
	 */
	private String cno;

	/**
	 *付款条件
	 */
	private String terms;

	/**
	 *预计付款金额
	 */
	private java.math.BigDecimal estimated_amount;

	/**
	 *付款金额
	 */
	private java.math.BigDecimal amount;

	/**
	 *预计付款时间
	 */
	private Date estimated_date;

	/**
	 *实际付款时间
	 */
	private Date paydate;

	/**
	 *发票号
	 */
	private String invoice_no;

	/**
	 *发票附件id
	 */
	private Long invoice_file;

	/**
	 *支票号
	 */
	private String check_no;

	/**
	 *支票附件
	 */
	private Long check_file;

	/**
	 *付款状态 0 未付 1 已付
	 */
	private String status;



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


	public void setCid(Long cid)
	{
		this.cid = cid;
	}

	@Column(name = "cid")
	public Long getCid()
	{
		return cid;
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


	public void setTerms(String terms)
	{
		this.terms = terms;
	}

	@Column(name = "terms")
	public String getTerms()
	{
		return terms;
	}


	public void setEstimated_amount(java.math.BigDecimal estimated_amount)
	{
		this.estimated_amount = estimated_amount;
	}

	@Column(name = "estimated_amount")
	public java.math.BigDecimal getEstimated_amount()
	{
		return estimated_amount;
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


	public void setEstimated_date(Date estimated_date)
	{
		this.estimated_date = estimated_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "estimated_date" , length = 26)
	public Date getEstimated_date()
	{
		return estimated_date;
	}


	public void setPaydate(Date paydate)
	{
		this.paydate = paydate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "paydate" , length = 26)
	public Date getPaydate()
	{
		return paydate;
	}


	public void setInvoice_no(String invoice_no)
	{
		this.invoice_no = invoice_no;
	}

	@Column(name = "invoice_no")
	public String getInvoice_no()
	{
		return invoice_no;
	}


	public void setInvoice_file(Long invoice_file)
	{
		this.invoice_file = invoice_file;
	}

	@Column(name = "invoice_file")
	public Long getInvoice_file()
	{
		return invoice_file;
	}


	public void setCheck_no(String check_no)
	{
		this.check_no = check_no;
	}

	@Column(name = "check_no")
	public String getCheck_no()
	{
		return check_no;
	}


	public void setCheck_file(Long check_file)
	{
		this.check_file = check_file;
	}

	@Column(name = "check_file")
	public Long getCheck_file()
	{
		return check_file;
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

}
