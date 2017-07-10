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
@Table(name="PM_PROJECT_ATTACHMENT")
public class PM_PROJECT_ATTACHMENT implements Serializable
{
	public PM_PROJECT_ATTACHMENT(){}
	public PM_PROJECT_ATTACHMENT(Long id, Long pid, Long attach_id, String attach_code){
		this.id = id;
		this.pid = pid;
		this.attach_id = attach_id;
		this.attach_code = attach_code;
}
	/**
	 *无意义主键
	 */
	private Long id;

	/**
	 *项目id
	 */
	private Long pid;

	/**
	 *附件id
	 */
	private Long attach_id;

	/**
	 *附件类型
	 */
	private String attach_code;



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


	public void setAttach_id(Long attach_id)
	{
		this.attach_id = attach_id;
	}

	@Column(name = "attach_id")
	public Long getAttach_id()
	{
		return attach_id;
	}


	public void setAttach_code(String attach_code)
	{
		this.attach_code = attach_code;
	}

	@Column(name = "attach_code")
	public String getAttach_code()
	{
		return attach_code;
	}

}
