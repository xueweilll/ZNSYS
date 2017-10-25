package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_SA_Operations")
public class Operation extends Pojo
{
	@Id
	private String id;
	@Column(name = "DisplayName")
	private String displayname;
	@Column(name = "Name")
	private String name;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDisplayname()
	{
		return displayname;
	}

	public void setDisplayname(String displayname)
	{
		this.displayname = displayname;
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
