package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;

@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_USEROPERATION")
public class UserOperation extends Pojo
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "USERNAME")
	private String username;
	@Column(name = "OPERATIONS")
	private int operations;
	@Column(name = "DATASCOPES")
	private String datascopes;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "FUNCTION_ID")
	private Function function;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public int getOperations()
	{
		return operations;
	}

	public void setOperations(int operations)
	{
		this.operations = operations;
	}

	public String getDatascopes()
	{
		return datascopes;
	}

	public void setDatascopes(String datascopes)
	{
		this.datascopes = datascopes;
	}

	public Function getFunction()
	{
		return function;
	}

	public void setFunction(Function function)
	{
		this.function = function;
	}

}
