package com.youi.business.common.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="SYS_USER")
public class SYS_USER implements Serializable
{
	public SYS_USER(){}
	public SYS_USER(Long user_id,String password,Date pass_mod_date,String valid_type,String username,String usercode,Long org_id,Long user_sort){
		this.user_id = user_id;
		this.password = password;
		this.pass_mod_date = pass_mod_date;
		this.valid_type = valid_type;
		this.username = username;
		this.usercode = usercode;
		this.org_id = org_id;
		this.user_sort = user_sort;
}
	/**
	 *用户ID
	 */
	private Long user_id;

	/**
	 *密码
	 */
	private String password;

	/**
	 *密码修改日期
	 */
	private Date pass_mod_date;

	/**
	 *有效标识，1-有效，0-无效
	 */
	private String valid_type;

	/**
	 *用户姓名
	 */
	private String username;

	/**
	 *用户登录名
	 */
	private String usercode;

	/**
	 *组织机构ID
	 */
	private Long org_id;

	/**
	 *内部顺序
	 */
	private Long user_sort;



	public void setUser_id(Long user_id)
	{
		this.user_id = user_id;
	}
	@Id
	@Column(name = "USER_ID")
	public Long getUser_id()
	{
		return user_id;
	}


	public void setPassword(String password)
	{
		this.password = password;
	}

	@Column(name = "PASSWORD")
	public String getPassword()
	{
		return password;
	}


	public void setPass_mod_date(Date pass_mod_date)
	{
		this.pass_mod_date = pass_mod_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PASS_MOD_DATE" , length = 26)
	public Date getPass_mod_date()
	{
		return pass_mod_date;
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


	public void setUsername(String username)
	{
		this.username = username;
	}

	@Column(name = "USERNAME")
	public String getUsername()
	{
		return username;
	}


	public void setUsercode(String usercode)
	{
		this.usercode = usercode;
	}

	@Column(name = "USERCODE")
	public String getUsercode()
	{
		return usercode;
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


	public void setUser_sort(Long user_sort)
	{
		this.user_sort = user_sort;
	}

	@Column(name = "USER_SORT")
	public Long getUser_sort()
	{
		return user_sort;
	}

}
