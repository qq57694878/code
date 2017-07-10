package com.youi.business.common.entity;


	/**
	 *数据库和存储关系表
	 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="HW_DATABASE_STORAGE_RELATION")
public class HW_DATABASE_STORAGE_RELATION implements Serializable
{
	public HW_DATABASE_STORAGE_RELATION(){}
	public HW_DATABASE_STORAGE_RELATION(Long id,Long database_id,Long storage_id){
		this.id = id;
		this.database_id = database_id;
		this.storage_id = storage_id;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *
	 */
	private Long database_id;

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


	public void setDatabase_id(Long database_id)
	{
		this.database_id = database_id;
	}

	@Column(name = "database_id")
	public Long getDatabase_id()
	{
		return database_id;
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
