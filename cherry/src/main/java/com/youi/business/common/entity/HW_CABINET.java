package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="HW_CABINET")
public class HW_CABINET implements Serializable
{
	public HW_CABINET(){}
	public HW_CABINET(Long id, String brand, Long room_id, String name, String type, String asset_num, Long high, Long width, Long depth, String description, Long unit, String materials, String def_level, Long load_capacity, Date cdate, Long userid){
		this.id = id;
		this.brand = brand;
		this.room_id = room_id;
		this.name = name;
		this.type = type;
		this.asset_num = asset_num;
		this.high = high;
		this.width = width;
		this.depth = depth;
		this.description = description;
		this.unit = unit;
		this.materials = materials;
		this.def_level = def_level;
		this.load_capacity = load_capacity;
		this.cdate = cdate;
		this.userid = userid;
}
	/**
	 *
	 */
	private Long id;

	/**
	 *品牌
	 */
	private String brand;

	/**
	 *机房ID
	 */
	private Long room_id;

	/**
	 *机柜名称
	 */
	private String name;

	/**
	 *型号
	 */
	private String type;

	/**
	 *资产编号
	 */
	private String asset_num;

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
	 *详细描述
	 */
	private String description;

	/**
	 *机柜容量（U）
	 */
	private Long unit;

	/**
	 *材料及工艺
	 */
	private String materials;

	/**
	 *防护等级
	 */
	private String def_level;

	/**
	 *承重（kg）
	 */
	private Long load_capacity;

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


	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	@Column(name = "brand")
	public String getBrand()
	{
		return brand;
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


	public void setName(String name)
	{
		this.name = name;
	}

	@Column(name = "name")
	public String getName()
	{
		return name;
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


	public void setAsset_num(String asset_num)
	{
		this.asset_num = asset_num;
	}

	@Column(name = "asset_num")
	public String getAsset_num()
	{
		return asset_num;
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


	public void setDescription(String description)
	{
		this.description = description;
	}

	@Column(name = "description")
	public String getDescription()
	{
		return description;
	}


	public void setUnit(Long unit)
	{
		this.unit = unit;
	}

	@Column(name = "unit")
	public Long getUnit()
	{
		return unit;
	}


	public void setMaterials(String materials)
	{
		this.materials = materials;
	}

	@Column(name = "materials")
	public String getMaterials()
	{
		return materials;
	}


	public void setDef_level(String def_level)
	{
		this.def_level = def_level;
	}

	@Column(name = "def_level")
	public String getDef_level()
	{
		return def_level;
	}


	public void setLoad_capacity(Long load_capacity)
	{
		this.load_capacity = load_capacity;
	}

	@Column(name = "load_capacity")
	public Long getLoad_capacity()
	{
		return load_capacity;
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
