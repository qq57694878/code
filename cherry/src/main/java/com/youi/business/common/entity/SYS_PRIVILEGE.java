package com.youi.business.common.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="SYS_PRIVILEGE")
public class SYS_PRIVILEGE implements Serializable
{
	public SYS_PRIVILEGE(){}
	public SYS_PRIVILEGE(Long privilege_id,String menu_name,String menu_type,String url,Long parentid,Long cuser_id,Date cdate,Long euser_id,Date edate,Long sort,String image){
		this.privilege_id = privilege_id;
		this.menu_name = menu_name;
		this.menu_type = menu_type;
		this.url = url;
		this.parentid = parentid;
		this.cuser_id = cuser_id;
		this.cdate = cdate;
		this.euser_id = euser_id;
		this.edate = edate;
		this.sort = sort;
		this.image = image;
}
	/**
	 *权限流水号
	 */
	private Long privilege_id;

	/**
	 *菜单名称
	 */
	private String menu_name;

	/**
	 *菜单类型，0-菜单，1-按钮
	 */
	private String menu_type;

	/**
	 *
	 */
	private String url;

	/**
	 *父节点ID
	 */
	private Long parentid;

	/**
	 *创建者
	 */
	private Long cuser_id;

	/**
	 *创建时间
	 */
	private Date cdate;

	/**
	 *修改者
	 */
	private Long euser_id;

	/**
	 *修改时间
	 */
	private Date edate;

	/**
	 *序号
	 */
	private Long sort;

	/**
	 *图片地址
	 */
	private String image;



	public void setPrivilege_id(Long privilege_id)
	{
		this.privilege_id = privilege_id;
	}
	@Id
	@Column(name = "PRIVILEGE_ID")
	public Long getPrivilege_id()
	{
		return privilege_id;
	}


	public void setMenu_name(String menu_name)
	{
		this.menu_name = menu_name;
	}

	@Column(name = "MENU_NAME")
	public String getMenu_name()
	{
		return menu_name;
	}


	public void setMenu_type(String menu_type)
	{
		this.menu_type = menu_type;
	}

	@Column(name = "MENU_TYPE")
	public String getMenu_type()
	{
		return menu_type;
	}


	public void setUrl(String url)
	{
		this.url = url;
	}

	@Column(name = "URL")
	public String getUrl()
	{
		return url;
	}


	public void setParentid(Long parentid)
	{
		this.parentid = parentid;
	}

	@Column(name = "PARENTID")
	public Long getParentid()
	{
		return parentid;
	}


	public void setCuser_id(Long cuser_id)
	{
		this.cuser_id = cuser_id;
	}

	@Column(name = "CUSER_ID")
	public Long getCuser_id()
	{
		return cuser_id;
	}


	public void setCdate(Date cdate)
	{
		this.cdate = cdate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CDATE" , length = 26)
	public Date getCdate()
	{
		return cdate;
	}


	public void setEuser_id(Long euser_id)
	{
		this.euser_id = euser_id;
	}

	@Column(name = "EUSER_ID")
	public Long getEuser_id()
	{
		return euser_id;
	}


	public void setEdate(Date edate)
	{
		this.edate = edate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EDATE" , length = 26)
	public Date getEdate()
	{
		return edate;
	}


	public void setSort(Long sort)
	{
		this.sort = sort;
	}

	@Column(name = "SORT")
	public Long getSort()
	{
		return sort;
	}


	public void setImage(String image)
	{
		this.image = image;
	}

	@Column(name = "IMAGE")
	public String getImage()
	{
		return image;
	}

}
