package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_ACTIONS")
public class Action extends Pojo
{
	@Id
	private String name;
	@Column(name = "DISPLAYNAME")
	private String displayname;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDisplayname()
	{
		return displayname;
	}

	public void setDisplayname(String displayname)
	{
		this.displayname = displayname;
	}
}
