package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="RES_VENDOR")
public class RES_VENDOR implements Serializable
{
	public RES_VENDOR(){}
	public RES_VENDOR(Long id,String manager,String tel,Long sys_org_info_id,String property,String scope,Date register_date,String register_money,String num_people,Long c_userid,Date c_date){
		this.id = id;
		this.manager = manager;
		this.tel = tel;
		this.sys_org_info_id = sys_org_info_id;
		this.property = property;
		this.scope = scope;
		this.register_date = register_date;
		this.register_money = register_money;
		this.num_people = num_people;
		this.c_userid = c_userid;
		this.c_date = c_date;
}
	/**
	 *主键
	 */
	private Long id;

	/**
	 *负责人
	 */
	private String manager;

	/**
	 *联系电话
	 */
	private String tel;

	/**
	 *组织机构表对应ID
	 */
	private Long sys_org_info_id;

	/**
	 *企业性质
	 */
	private String property;

	/**
	 *经营范围
	 */
	private String scope;

	/**
	 *注册日期
	 */
	private Date register_date;

	/**
	 *注册资金
	 */
	private String register_money;

	/**
	 *员工人数
	 */
	private String num_people;

	/**
	 *创建人
	 */
	private Long c_userid;

	/**
	 *创建时间
	 */
	private Date c_date;



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


	public void setManager(String manager)
	{
		this.manager = manager;
	}

	@Column(name = "manager")
	public String getManager()
	{
		return manager;
	}


	public void setTel(String tel)
	{
		this.tel = tel;
	}

	@Column(name = "tel")
	public String getTel()
	{
		return tel;
	}


	public void setSys_org_info_id(Long sys_org_info_id)
	{
		this.sys_org_info_id = sys_org_info_id;
	}

	@Column(name = "sys_org_info_id")
	public Long getSys_org_info_id()
	{
		return sys_org_info_id;
	}


	public void setProperty(String property)
	{
		this.property = property;
	}

	@Column(name = "property")
	public String getProperty()
	{
		return property;
	}


	public void setScope(String scope)
	{
		this.scope = scope;
	}

	@Column(name = "scope")
	public String getScope()
	{
		return scope;
	}


	public void setRegister_date(Date register_date)
	{
		this.register_date = register_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "register_date" , length = 26)
	public Date getRegister_date()
	{
		return register_date;
	}


	public void setRegister_money(String register_money)
	{
		this.register_money = register_money;
	}

	@Column(name = "register_money")
	public String getRegister_money()
	{
		return register_money;
	}


	public void setNum_people(String num_people)
	{
		this.num_people = num_people;
	}

	@Column(name = "num_people")
	public String getNum_people()
	{
		return num_people;
	}


	public void setC_userid(Long c_userid)
	{
		this.c_userid = c_userid;
	}

	@Column(name = "c_userid")
	public Long getC_userid()
	{
		return c_userid;
	}


	public void setC_date(Date c_date)
	{
		this.c_date = c_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "c_date" , length = 26)
	public Date getC_date()
	{
		return c_date;
	}

}
