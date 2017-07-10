package com.youi.business.common.entity;


	/**
	 *中间件和虚拟机关系表
	 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="HW_MIDDLEWARE_VM_RELATION")
public class HW_MIDDLEWARE_VM_RELATION implements Serializable
{
	public HW_MIDDLEWARE_VM_RELATION(){}
	public HW_MIDDLEWARE_VM_RELATION(Long id,Long vm_id,Long mw_id){
		this.id = id;
		this.vm_id = vm_id;
		this.mw_id = mw_id;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *
	 */
	private Long vm_id;

	/**
	 *
	 */
	private Long mw_id;



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


	public void setVm_id(Long vm_id)
	{
		this.vm_id = vm_id;
	}

	@Column(name = "vm_id")
	public Long getVm_id()
	{
		return vm_id;
	}


	public void setMw_id(Long mw_id)
	{
		this.mw_id = mw_id;
	}

	@Column(name = "mw_id")
	public Long getMw_id()
	{
		return mw_id;
	}

}
