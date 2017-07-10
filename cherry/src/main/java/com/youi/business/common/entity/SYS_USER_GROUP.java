package com.youi.business.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="SYS_USER_GROUP")
public class SYS_USER_GROUP implements Serializable
{
	public SYS_USER_GROUP(){}
	public SYS_USER_GROUP(Long id,Long user_id,Long group_id){
		this.id = id;
		this.user_id = user_id;
		this.group_id = group_id;
}
	/**
	 *用户角色流水号
	 */
	private Long id;

	/**
	 *用户ID
	 */
	private Long user_id;

	/**
	 *角色ID
	 */
	private Long group_id;



	public void setId(Long id)
	{
		this.id = id;
	}
	@Id
	@Column(name = "ID")
	public Long getId()
	{
		return id;
	}


	public void setUser_id(Long user_id)
	{
		this.user_id = user_id;
	}

	@Column(name = "USER_ID")
	public Long getUser_id()
	{
		return user_id;
	}


	public void setGroup_id(Long group_id)
	{
		this.group_id = group_id;
	}

	@Column(name = "GROUP_ID")
	public Long getGroup_id()
	{
		return group_id;
	}

}
