package com.youi.business.common.entity;


	/**
	 *虚拟化平台和存储关系表
	 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="HW_VM_PLATFORM_STORAGE_RELATION")
public class HW_VM_PLATFORM_STORAGE_RELATION implements Serializable
{
	public HW_VM_PLATFORM_STORAGE_RELATION(){}
	public HW_VM_PLATFORM_STORAGE_RELATION(Long id,Long vm_platform_id,Long storage_id){
		this.id = id;
		this.vm_platform_id = vm_platform_id;
		this.storage_id = storage_id;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *
	 */
	private Long vm_platform_id;

	/**
	 *
	 */
	private Long storage_id;



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


	public void setVm_platform_id(Long vm_platform_id)
	{
		this.vm_platform_id = vm_platform_id;
	}

	@Column(name = "vm_platform_id")
	public Long getVm_platform_id()
	{
		return vm_platform_id;
	}


	public void setStorage_id(Long storage_id)
	{
		this.storage_id = storage_id;
	}

	@Column(name = "storage_id")
	public Long getStorage_id()
	{
		return storage_id;
	}

}
