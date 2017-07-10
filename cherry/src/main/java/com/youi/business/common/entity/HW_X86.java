package com.youi.business.common.entity;


	/**
	 *X86服务器
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="HW_X86")
public class HW_X86 implements Serializable
{
	public HW_X86(){}
	public HW_X86(Long id,String name,Long room_id,Long cabinet_id,String asset_num,String brand,String type,Long high,Long width,Long depth,String cpu_type,String cpu_frequency,Long core_num,Long cpu_current_num,Long cpu_max_num,String memory_type,Long memory_current_capacity,Long memory_max_capacity,String mainboard_type,String disk_interface_type,Long disk_size,Long disk_current_num,Long disk_max_num,String disk_raid,String main_usage,String description,Date cdate,Long userid){
		this.id = id;
		this.name = name;
		this.room_id = room_id;
		this.cabinet_id = cabinet_id;
		this.asset_num = asset_num;
		this.brand = brand;
		this.type = type;
		this.high = high;
		this.width = width;
		this.depth = depth;
		this.cpu_type = cpu_type;
		this.cpu_frequency = cpu_frequency;
		this.core_num = core_num;
		this.cpu_current_num = cpu_current_num;
		this.cpu_max_num = cpu_max_num;
		this.memory_type = memory_type;
		this.memory_current_capacity = memory_current_capacity;
		this.memory_max_capacity = memory_max_capacity;
		this.mainboard_type = mainboard_type;
		this.disk_interface_type = disk_interface_type;
		this.disk_size = disk_size;
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

	/**
	 *名称
	 */
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
	 *
	 */
	private String asset_num;

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
	 *频率(Hz)
	 */
	private String cpu_frequency;

	/**
	 *核心数
	 */
	private Long core_num;

	/**
	 *当前cpu数
	 */
	private Long cpu_current_num;

	/**
	 *cpu最大数
	 */
	private Long cpu_max_num;

	/**
	 *内存类型
	 */
	private String memory_type;

	/**
	 *内存当前容量
	 */
	private Long memory_current_capacity;

	/**
	 *内存最大容量
	 */
	private Long memory_max_capacity;

	/**
	 *主板型号
	 */
	private String mainboard_type;

	/**
	 *存储接口类型
	 */
	private String disk_interface_type;

	/**
	 *磁盘容量
	 */
	private Long disk_size;

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


	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "name")
	public String getName()
	{
		return name;
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


	public void setAsset_num(String asset_num)
	{
		this.asset_num = asset_num;
	}

	@Column(name = "asset_num")
	public String getAsset_num()
	{
		return asset_num;
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


	public void setCpu_frequency(String cpu_frequency)
	{
		this.cpu_frequency = cpu_frequency;
	}

	@Column(name = "cpu_frequency")
	public String getCpu_frequency()
	{
		return cpu_frequency;
	}


	public void setCore_num(Long core_num)
	{
		this.core_num = core_num;
	}

	@Column(name = "core_num")
	public Long getCore_num()
	{
		return core_num;
	}


	public void setCpu_current_num(Long cpu_current_num)
	{
		this.cpu_current_num = cpu_current_num;
	}

	@Column(name = "cpu_current_num")
	public Long getCpu_current_num()
	{
		return cpu_current_num;
	}


	public void setCpu_max_num(Long cpu_max_num)
	{
		this.cpu_max_num = cpu_max_num;
	}

	@Column(name = "cpu_max_num")
	public Long getCpu_max_num()
	{
		return cpu_max_num;
	}


	public void setMemory_type(String memory_type)
	{
		this.memory_type = memory_type;
	}

	@Column(name = "memory_type")
	public String getMemory_type()
	{
		return memory_type;
	}


	public void setMemory_current_capacity(Long memory_current_capacity)
	{
		this.memory_current_capacity = memory_current_capacity;
	}

	@Column(name = "memory_current_capacity")
	public Long getMemory_current_capacity()
	{
		return memory_current_capacity;
	}


	public void setMemory_max_capacity(Long memory_max_capacity)
	{
		this.memory_max_capacity = memory_max_capacity;
	}

	@Column(name = "memory_max_capacity")
	public Long getMemory_max_capacity()
	{
		return memory_max_capacity;
	}


	public void setMainboard_type(String mainboard_type)
	{
		this.mainboard_type = mainboard_type;
	}

	@Column(name = "mainboard_type")
	public String getMainboard_type()
	{
		return mainboard_type;
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


	public void setDisk_size(Long disk_size)
	{
		this.disk_size = disk_size;
	}

	@Column(name = "disk_size")
	public Long getDisk_size()
	{
		return disk_size;
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
