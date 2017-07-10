package com.youi.business.common.entity;


	/**
	 *程序设置参数表
	 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="RES_SETTINGS")
public class RES_SETTINGS implements Serializable
{
	public RES_SETTINGS(){}
	public RES_SETTINGS(String k, String v){
		this.k = k;
		this.v = v;
}
	/**
	 *记录的参数key
	 */
	private String k;

	/**
	 *记录的参数值
	 */
	private String v;



	public void setK(String k)
	{
		this.k = k;
	}
    @Id
	@Column(name = "k")
	public String getK()
	{
		return k;
	}


	public void setV(String v)
	{
		this.v = v;
	}

	@Column(name = "v")
	public String getV()
	{
		return v;
	}

}
