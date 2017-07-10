package com.youi.business.common.entity;


	/**
	 *虚拟化平台和x86服务器关系表
	 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="HW_VM_PLATFORM_X86_RELATION")
public class HW_VM_PLATFORM_X86_RELATION implements Serializable
{
	public HW_VM_PLATFORM_X86_RELATION(){}
	public HW_VM_PLATFORM_X86_RELATION(Long id,Long x86_id,Long vm_platform_id){
		this.id = id;
		this.x86_id = x86_id;
		this.vm_platform_id = vm_platform_id;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *
	 */
	private Long x86_id;

	/**
	 *
	 */
	private Long vm_platform_id;



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


	public void setX86_id(Long x86_id)
	{
		this.x86_id = x86_id;
	}

	@Column(name = "x86_id")
	public Long getX86_id()
	{
		return x86_id;
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

}
