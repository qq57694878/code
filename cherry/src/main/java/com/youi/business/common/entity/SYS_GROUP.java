package com.youi.business.common.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="SYS_GROUP")
public class SYS_GROUP implements Serializable
{
	public SYS_GROUP(){}
	public SYS_GROUP(Long group_id,String group_code,String group_name,Long cuser_id,Date cdate,Long euser_id,Date edate,String valid_type){
		this.group_id = group_id;
		this.group_code = group_code;
		this.group_name = group_name;
		this.cuser_id = cuser_id;
		this.cdate = cdate;
		this.euser_id = euser_id;
		this.edate = edate;
		this.valid_type = valid_type;
}
	/**
	 *角色ID
	 */
	private Long group_id;

	/**
	 *角色编码
	 */
	private String group_code;

	/**
	 *角色名称
	 */
	private String group_name;

	/**
	 *创建人
	 */
	private Long cuser_id;

	/**
	 *创建时间
	 */
	private Date cdate;

	/**
	 *修改人
	 */
	private Long euser_id;

	/**
	 *修改时间
	 */
	private Date edate;

	/**
	 *有效标志,1-有效,0-无效
	 */
	private String valid_type;



	public void setGroup_id(Long group_id)
	{
		this.group_id = group_id;
	}
    @Id
	@Column(name = "GROUP_ID")
	public Long getGroup_id()
	{
		return group_id;
	}


	public void setGroup_code(String group_code)
	{
		this.group_code = group_code;
	}

	@Column(name = "GROUP_CODE")
	public String getGroup_code()
	{
		return group_code;
	}


	public void setGroup_name(String group_name)
	{
		this.group_name = group_name;
	}

	@Column(name = "GROUP_NAME")
	public String getGroup_name()
	{
		return group_name;
	}


	public void setCuser_id(Long cuser_id)
	{
		this.cuser_id = cuser_id;
	}

	@Column(name = "CUSER_ID")
	public Long getCuser_id()
	{
		return cuser_id;
	}


	public void setCdate(Date cdate)
	{
		this.cdate = cdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CDATE" , length = 26)
	public Date getCdate()
	{
		return cdate;
	}


	public void setEuser_id(Long euser_id)
	{
		this.euser_id = euser_id;
	}

	@Column(name = "EUSER_ID")
	public Long getEuser_id()
	{
		return euser_id;
	}


	public void setEdate(Date edate)
	{
		this.edate = edate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EDATE" , length = 26)
	public Date getEdate()
	{
		return edate;
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

}
