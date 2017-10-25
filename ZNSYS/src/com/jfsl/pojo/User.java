package com.jfsl.pojo;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_SA_Users")
public class User extends Pojo
{
	@Id
	private String username;
	@Column(name = "UserPsw")
	private String userpsw;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "T_SA_UserRole", joinColumns = { @JoinColumn(name = "UserName") }, inverseJoinColumns = { @JoinColumn(name = "Role_ID") })
	private Set<Role> roles = new LinkedHashSet<Role>();

	@Column(name = "Name")
	private String name;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "Department_ID")
	private Department department;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUserpsw()
	{
		return userpsw;
	}

	public void setUserpsw(String userpsw)
	{
		this.userpsw = userpsw;
	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}
}
