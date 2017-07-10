package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="SYS_USER_ORG")
public class SYS_USER_ORG implements Serializable
{
	public SYS_USER_ORG(){}
	public SYS_USER_ORG(Long userid,Long org_id,Long userorder){
		this.userid = userid;
		this.org_id = org_id;
		this.userorder = userorder;
}

	/**
	 *
	 */
	private Long id;
	/**
	 *
	 */
	private Long userid;

	/**
	 *
	 */
	private Long org_id;

	/**
	 *部门内部排序
	 */
	private Long userorder;
    @Id
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserid(Long userid)
	{
		this.userid = userid;
	}

	@Column(name = "USERID")
	public Long getUserid()
	{
		return userid;
	}


	public void setOrg_id(Long org_id)
	{
		this.org_id = org_id;
	}

	@Column(name = "ORG_ID")
	public Long getOrg_id()
	{
		return org_id;
	}


	public void setUserorder(Long userorder)
	{
		this.userorder = userorder;
	}

	@Column(name = "USERORDER")
	public Long getUserorder()
	{
		return userorder;
	}

}
