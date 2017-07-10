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
@Table(name="ACTE_NODE_ATTACHMENT")
public class ACTE_NODE_ATTACHMENT implements Serializable
{
	public ACTE_NODE_ATTACHMENT(){}
	public ACTE_NODE_ATTACHMENT(Long id,Long biz_id,String biz_type,Long attachment_id,String node_id){
		this.id = id;
		this.biz_id = biz_id;
		this.biz_type = biz_type;
		this.attachment_id = attachment_id;
		this.node_id = node_id;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *业务主键
	 */
	private Long biz_id;

	/**
	 *业务类型
	 */
	private String biz_type;

	/**
	 *附件id
	 */
	private Long attachment_id;

	/**
	 *流程节点id
	 */
	private String node_id;



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


	public void setBiz_id(Long biz_id)
	{
		this.biz_id = biz_id;
	}

	@Column(name = "BIZ_ID")
	public Long getBiz_id()
	{
		return biz_id;
	}


	public void setBiz_type(String biz_type)
	{
		this.biz_type = biz_type;
	}

	@Column(name = "BIZ_TYPE")
	public String getBiz_type()
	{
		return biz_type;
	}


	public void setAttachment_id(Long attachment_id)
	{
		this.attachment_id = attachment_id;
	}

	@Column(name = "ATTACHMENT_ID")
	public Long getAttachment_id()
	{
		return attachment_id;
	}


	public void setNode_id(String node_id)
	{
		this.node_id = node_id;
	}

	@Column(name = "NODE_ID")
	public String getNode_id()
	{
		return node_id;
	}

}
