package com.youi.business.common.entity;


	/**
	 *中间件和x86服务器关系表
	 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="HW_MIDDLEWARE_X86_RELATION")
public class HW_MIDDLEWARE_X86_RELATION implements Serializable
{
	public HW_MIDDLEWARE_X86_RELATION(){}
	public HW_MIDDLEWARE_X86_RELATION(Long id,Long x86_id,Long mw_id){
		this.id = id;
		this.x86_id = x86_id;
		this.mw_id = mw_id;
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


	public void setX86_id(Long x86_id)
	{
		this.x86_id = x86_id;
	}

	@Column(name = "x86_id")
	public Long getX86_id()
	{
		return x86_id;
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
