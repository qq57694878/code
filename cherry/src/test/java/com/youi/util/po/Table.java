package com.youi.util.po;

import java.util.ArrayList;
import java.util.List;


/**
 * com.dhc.framework.test2.service
 * Table.java
 * @version 2008-4-29
 */
public class Table
{
	private String name;
	private String comment;

	private List columns = new ArrayList();;
	
	public List getColumns(){
		return columns;
	}
	
	public void addColumn(Column column){
		columns.add(column);
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}

