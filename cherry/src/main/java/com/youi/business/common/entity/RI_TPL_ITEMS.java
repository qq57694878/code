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
@Table(name="RI_TPL_ITEMS")
public class RI_TPL_ITEMS implements Serializable
{
	public RI_TPL_ITEMS(){}
	public RI_TPL_ITEMS(Long id, Long tpl_id, String item){
		this.id = id;
		this.tpl_id = tpl_id;
		this.item = item;
}
	/**
	 *ID
	 */
	private Long id;

	/**
	 *模板ID
	 */
	private Long tpl_id;

	/**
	 *检查项说明
	 */
	private String item;



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


	public void setTpl_id(Long tpl_id)
	{
		this.tpl_id = tpl_id;
	}

	@Column(name = "tpl_id")
	public Long getTpl_id()
	{
		return tpl_id;
	}


	public void setItem(String item)
	{
		this.item = item;
	}

	@Column(name = "item")
	public String getItem()
	{
		return item;
	}

}
