package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_SA_Departments")
public class Department extends Pojo
{

	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "Name")
	private String name;
	@Column(name = "ShowOrder")
	private Integer showorder;
	@Column(name = "BelongIn")
	private Integer belongin;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getShoworder()
	{
		return showorder;
	}

	public void setShoworder(Integer showorder)
	{
		this.showorder = showorder;
	}

	public Integer getBelongin() {
		return belongin;
	}

	public void setBelongin(Integer belongin) {
		this.belongin = belongin;
	}

}
