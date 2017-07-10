package com.youi.business.common.entity;


	/**
	 *
	 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="SYS_ATTACHMENT")
public class SYS_ATTACHMENT implements Serializable
{
	public SYS_ATTACHMENT(){}
	public SYS_ATTACHMENT(Long id, Long biz_id, String biz_type, String file_name, String show_name, String file_path, String file_size, String file_suffix, Long create_user, Date create_date, String valid_type){
		this.id = id;
		this.biz_id = biz_id;
		this.biz_type = biz_type;
		this.file_name = file_name;
		this.show_name = show_name;
		this.file_path = file_path;
		this.file_size = file_size;
		this.file_suffix = file_suffix;
		this.create_user = create_user;
		this.create_date = create_date;
		this.valid_type = valid_type;
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
	 *文件名
	 */
	private String file_name;

	/**
	 *显示名字，原始名字
	 */
	private String show_name;

	/**
	 *附件路径
	 */
	private String file_path;

	/**
	 *文件大小
	 */
	private String file_size;

	/**
	 *文件后缀
	 */
	private String file_suffix;

	/**
	 *创建人
	 */
	private Long create_user;

	/**
	 *创建时间
	 */
	private Date create_date;

	/**
	 *有效标记
	 */
	private String valid_type;



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

	@Column(name = "biz_id")
	public Long getBiz_id()
	{
		return biz_id;
	}


	public void setBiz_type(String biz_type)
	{
		this.biz_type = biz_type;
	}

	@Column(name = "biz_type")
	public String getBiz_type()
	{
		return biz_type;
	}


	public void setFile_name(String file_name)
	{
		this.file_name = file_name;
	}

	@Column(name = "file_name")
	public String getFile_name()
	{
		return file_name;
	}


	public void setShow_name(String show_name)
	{
		this.show_name = show_name;
	}

	@Column(name = "show_name")
	public String getShow_name()
	{
		return show_name;
	}


	public void setFile_path(String file_path)
	{
		this.file_path = file_path;
	}

	@Column(name = "file_path")
	public String getFile_path()
	{
		return file_path;
	}


	public void setFile_size(String file_size)
	{
		this.file_size = file_size;
	}

	@Column(name = "file_size")
	public String getFile_size()
	{
		return file_size;
	}


	public void setFile_suffix(String file_suffix)
	{
		this.file_suffix = file_suffix;
	}

	@Column(name = "file_suffix")
	public String getFile_suffix()
	{
		return file_suffix;
	}


	public void setCreate_user(Long create_user)
	{
		this.create_user = create_user;
	}

	@Column(name = "create_user")
	public Long getCreate_user()
	{
		return create_user;
	}


	public void setCreate_date(Date create_date)
	{
		this.create_date = create_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date" , length = 26)
	public Date getCreate_date()
	{
		return create_date;
	}


	public void setValid_type(String valid_type)
	{
		this.valid_type = valid_type;
	}

	@Column(name = "valid_type")
	public String getValid_type()
	{
		return valid_type;
	}

}
