package com.youi.business.common.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="SYS_GROUP_PRIVILEGE")
public class SYS_GROUP_PRIVILEGE implements Serializable
{
	public SYS_GROUP_PRIVILEGE(){}
	public SYS_GROUP_PRIVILEGE(Long id,Long group_id,Long privilege_id){
		this.id = id;
		this.group_id = group_id;
		this.privilege_id = privilege_id;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *角色ID
	 */
	private Long group_id;

	/**
	 *权限ID
	 */
	private Long privilege_id;



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


	public void setGroup_id(Long group_id)
	{
		this.group_id = group_id;
	}

	@Column(name = "GROUP_ID")
	public Long getGroup_id()
	{
		return group_id;
	}


	public void setPrivilege_id(Long privilege_id)
	{
		this.privilege_id = privilege_id;
	}

	@Column(name = "PRIVILEGE_ID")
	public Long getPrivilege_id()
	{
		return privilege_id;
	}

}
