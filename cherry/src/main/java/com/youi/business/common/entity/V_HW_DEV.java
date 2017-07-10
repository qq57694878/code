package com.youi.business.common.entity;


	/**
	 *VIEW
	 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="V_HW_DEV")
public class V_HW_DEV implements Serializable
{
	public V_HW_DEV(){}
	public V_HW_DEV(Long dev_id, String dev_name, String dev_type, String dev_no){
		this.dev_id = dev_id;
		this.dev_name = dev_name;
		this.dev_type = dev_type;
		this.dev_no = dev_no;
}
	/**
	 *
	 */
	private Long dev_id;

	/**
	 *名称
	 */
	private String dev_name;

	/**
	 *
	 */
	private String dev_type;

	/**
	 *
	 */
	private String dev_no;



	public void setDev_id(Long dev_id)
	{
		this.dev_id = dev_id;
	}
    @Id
	@Column(name = "dev_id")
	public Long getDev_id()
	{
		return dev_id;
	}


	public void setDev_name(String dev_name)
	{
		this.dev_name = dev_name;
	}

	@Column(name = "dev_name")
	public String getDev_name()
	{
		return dev_name;
	}


	public void setDev_type(String dev_type)
	{
		this.dev_type = dev_type;
	}

	@Column(name = "dev_type")
	public String getDev_type()
	{
		return dev_type;
	}


	public void setDev_no(String dev_no)
	{
		this.dev_no = dev_no;
	}

	@Column(name = "dev_no")
	public String getDev_no()
	{
		return dev_no;
	}

}
