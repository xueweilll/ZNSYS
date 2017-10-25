package com.jfsl.pojo;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_SA_Roles")
public class Role extends Pojo
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "Name")
	private String name;
	@Column(name = "Description")
	private String description;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "T_SA_RoleFunction", joinColumns = { @JoinColumn(name = "Role_ID") }, inverseJoinColumns = { @JoinColumn(name = "Function_ID") })
	private Set<Function> functions = new LinkedHashSet<Function>();

	public int getId()
	{
		return id;
	}

	public void setId(int id)
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Set<Function> getFunctions()
	{
		return functions;
	}

	public void setFunctions(Set<Function> functions)
	{
		this.functions = functions;
	}
}
