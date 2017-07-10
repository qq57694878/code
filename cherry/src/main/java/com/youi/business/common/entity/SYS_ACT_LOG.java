package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="SYS_ACT_LOG")
public class SYS_ACT_LOG implements Serializable
{
	public SYS_ACT_LOG(){}
	public SYS_ACT_LOG(Long id, String instance_id, String task_id,String node_id, String task_def_key, String biz_id, String biz_table, String task_name, String executor, Date date, String type, String opinion){
		this.id = id;
		this.instance_id = instance_id;
		this.task_id = task_id;
		this.node_id = node_id;
		this.task_def_key = task_def_key;
		this.biz_id = biz_id;
		this.biz_table = biz_table;
		this.task_name = task_name;
		this.executor = executor;
		this.date = date;
		this.type = type;
		this.opinion = opinion;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *发起流程的唯一ID
	 */
	private String instance_id;

	/**
	 *task_id
	 */
	private String task_id;

	/**
	 *
	 */
	private String task_def_key;

	/**
	 * 节点id
	 */
	private String node_id;

	/**
	 *业务表id
	 */
	private String biz_id;

	/**
	 *业务表名
	 */
	private String biz_table;

	/**
	 *节点名称
	 */
	private String task_name;

	/**
	 *执行人
	 */
	private String executor;

	/**
	 *执行时间
	 */
	private Date date;

	/**
	 *类型1-通过，2-驳回，3-终止
	 */
	private String type;

	/**
	 *处理意见
	 */
	private String opinion;




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


	public void setInstance_id(String instance_id)
	{
		this.instance_id = instance_id;
	}

	@Column(name = "instance_id")
	public String getInstance_id()
	{
		return instance_id;
	}


	public void setTask_id(String task_id)
	{
		this.task_id = task_id;
	}

	@Column(name = "task_id")
	public String getTask_id()
	{
		return task_id;
	}

	@Column(name = "node_id")
	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	public void setTask_def_key(String task_def_key)
	{
		this.task_def_key = task_def_key;
	}

	@Column(name = "task_def_key")
	public String getTask_def_key()
	{
		return task_def_key;
	}


	public void setBiz_id(String biz_id)
	{
		this.biz_id = biz_id;
	}

	@Column(name = "biz_id")
	public String getBiz_id()
	{
		return biz_id;
	}


	public void setBiz_table(String biz_table)
	{
		this.biz_table = biz_table;
	}

	@Column(name = "biz_table")
	public String getBiz_table()
	{
		return biz_table;
	}


	public void setTask_name(String task_name)
	{
		this.task_name = task_name;
	}

	@Column(name = "task_name")
	public String getTask_name()
	{
		return task_name;
	}


	public void setExecutor(String executor)
	{
		this.executor = executor;
	}

	@Column(name = "executor")
	public String getExecutor()
	{
		return executor;
	}


	public void setDate(Date date)
	{
		this.date = date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date" , length = 26)
	public Date getDate()
	{
		return date;
	}


	public void setType(String type)
	{
		this.type = type;
	}

	@Column(name = "type")
	public String getType()
	{
		return type;
	}


	public void setOpinion(String opinion)
	{
		this.opinion = opinion;
	}

	@Column(name = "opinion")
	public String getOpinion()
	{
		return opinion;
	}

}
