package com.youi.business.common.entity;


	/**
	 *存储阵列
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="HW_STORAGE")
public class HW_STORAGE implements Serializable
{
	public HW_STORAGE(){}
	public HW_STORAGE(Long id,String name,Long room_id,Long cabinet_id,String brand,String type,Long high,Long width,Long depth,String cpu_type,Long disk_size,String disk_interface_type,Long disk_current_num,Long disk_max_num,String disk_raid,String main_usage,String description,Date cdate,Long userid){
		this.id = id;
		this.name = name;
		this.room_id = room_id;
		this.cabinet_id = cabinet_id;
		this.brand = brand;
		this.type = type;
		this.high = high;
		this.width = width;
		this.depth = depth;
		this.cpu_type = cpu_type;
		this.disk_size = disk_size;
		this.disk_interface_type = disk_interface_type;
		this.disk_current_num = disk_current_num;
		this.disk_max_num = disk_max_num;
		this.disk_raid = disk_raid;
		this.main_usage = main_usage;
		this.description = description;
		this.cdate = cdate;
		this.userid = userid;
}
	/**
	 *
	 */
	private Long id;

	private String name;

	/**
	 *
	 */
	private Long room_id;

	/**
	 *
	 */
	private Long cabinet_id;

	/**
	 *品牌
	 */
	private String brand;

	/**
	 *型号
	 */
	private String type;

	/**
	 *
	 */
	private Long high;

	/**
	 *
	 */
	private Long width;

	/**
	 *
	 */
	private Long depth;

	/**
	 *cpu型号
	 */
	private String cpu_type;

	/**
	 *磁盘容量
	 */
	private Long disk_size;

	/**
	 *存储接口类型
	 */
	private String disk_interface_type;

	/**
	 *当前磁盘数
	 */
	private Long disk_current_num;

	/**
	 *最大磁盘数
	 */
	private Long disk_max_num;

	/**
	 *
	 */
	private String disk_raid;

	/**
	 *主要用途,1-业务服务器,2-虚拟化服务器,3-数据库服务器
	 */
	private String main_usage;

	/**
	 *其他描述
	 */
	private String description;

	/**
	 *创建时间
	 */
	private Date cdate;

	/**
	 *创建者userid
	 */
	private Long userid;



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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoom_id(Long room_id)
	{
		this.room_id = room_id;
	}

	@Column(name = "room_id")
	public Long getRoom_id()
	{
		return room_id;
	}


	public void setCabinet_id(Long cabinet_id)
	{
		this.cabinet_id = cabinet_id;
	}

	@Column(name = "cabinet_id")
	public Long getCabinet_id()
	{
		return cabinet_id;
	}


	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	@Column(name = "brand")
	public String getBrand()
	{
		return brand;
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


	public void setHigh(Long high)
	{
		this.high = high;
	}

	@Column(name = "high")
	public Long getHigh()
	{
		return high;
	}


	public void setWidth(Long width)
	{
		this.width = width;
	}

	@Column(name = "width")
	public Long getWidth()
	{
		return width;
	}


	public void setDepth(Long depth)
	{
		this.depth = depth;
	}

	@Column(name = "depth")
	public Long getDepth()
	{
		return depth;
	}


	public void setCpu_type(String cpu_type)
	{
		this.cpu_type = cpu_type;
	}

	@Column(name = "cpu_type")
	public String getCpu_type()
	{
		return cpu_type;
	}


	public void setDisk_size(Long disk_size)
	{
		this.disk_size = disk_size;
	}

	@Column(name = "disk_size")
	public Long getDisk_size()
	{
		return disk_size;
	}


	public void setDisk_interface_type(String disk_interface_type)
	{
		this.disk_interface_type = disk_interface_type;
	}

	@Column(name = "disk_interface_type")
	public String getDisk_interface_type()
	{
		return disk_interface_type;
	}


	public void setDisk_current_num(Long disk_current_num)
	{
		this.disk_current_num = disk_current_num;
	}

	@Column(name = "disk_current_num")
	public Long getDisk_current_num()
	{
		return disk_current_num;
	}


	public void setDisk_max_num(Long disk_max_num)
	{
		this.disk_max_num = disk_max_num;
	}

	@Column(name = "disk_max_num")
	public Long getDisk_max_num()
	{
		return disk_max_num;
	}


	public void setDisk_raid(String disk_raid)
	{
		this.disk_raid = disk_raid;
	}

	@Column(name = "disk_raid")
	public String getDisk_raid()
	{
		return disk_raid;
	}


	public void setMain_usage(String main_usage)
	{
		this.main_usage = main_usage;
	}

	@Column(name = "main_usage")
	public String getMain_usage()
	{
		return main_usage;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "description")
	public String getDescription()
	{
		return description;
	}


	public void setCdate(Date cdate)
	{
		this.cdate = cdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cdate" , length = 26)
	public Date getCdate()
	{
		return cdate;
	}


	public void setUserid(Long userid)
	{
		this.userid = userid;
	}

	@Column(name = "userid")
	public Long getUserid()
	{
		return userid;
	}

}
