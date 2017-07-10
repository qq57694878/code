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
@Table(name="SYS_ORG_INFO")
public class SYS_ORG_INFO implements Serializable
{
	public SYS_ORG_INFO(){}
	public SYS_ORG_INFO(Long org_id,String org_code,String org_name,String up_org_id,String valid_type,Long org_order,String org_type){
		this.org_id = org_id;
		this.org_code = org_code;
		this.org_name = org_name;
		this.up_org_id = up_org_id;
		this.valid_type = valid_type;
		this.org_order = org_order;
		this.org_type = org_type;
}
	/**
	 *部门ID
	 */
	private Long org_id;

	/**
	 *部门代码
	 */
	private String org_code;

	/**
	 *部门名称
	 */
	private String org_name;

	/**
	 *上级部门ID
	 */
	private String up_org_id;

	/**
	 *有效标志
	 */
	private String valid_type;

	/**
	 *
	 */
	private Long org_order;

	/**
	 *组织机构类型，0-单位，1-部门
	 */
	private String org_type;



	public void setOrg_id(Long org_id)
	{
		this.org_id = org_id;
	}
    @Id
	@Column(name = "ORG_ID")
	public Long getOrg_id()
	{
		return org_id;
	}


	public void setOrg_code(String org_code)
	{
		this.org_code = org_code;
	}

	@Column(name = "ORG_CODE")
	public String getOrg_code()
	{
		return org_code;
	}


	public void setOrg_name(String org_name)
	{
		this.org_name = org_name;
	}

	@Column(name = "ORG_NAME")
	public String getOrg_name()
	{
		return org_name;
	}


	public void setUp_org_id(String up_org_id)
	{
		this.up_org_id = up_org_id;
	}

	@Column(name = "UP_ORG_ID")
	public String getUp_org_id()
	{
		return up_org_id;
	}


	public void setValid_type(String valid_type)
	{
		this.valid_type = valid_type;
	}

	@Column(name = "VALID_TYPE")
	public String getValid_type()
	{
		return valid_type;
	}


	public void setOrg_order(Long org_order)
	{
		this.org_order = org_order;
	}

	@Column(name = "ORG_ORDER")
	public Long getOrg_order()
	{
		return org_order;
	}


	public void setOrg_type(String org_type)
	{
		this.org_type = org_type;
	}

	@Column(name = "ORG_TYPE")
	public String getOrg_type()
	{
		return org_type;
	}

}
