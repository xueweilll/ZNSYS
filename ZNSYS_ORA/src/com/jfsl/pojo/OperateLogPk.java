package com.jfsl.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Embeddable
public class OperateLogPk extends Pojo
{
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "UserName")
	private User user;
	@Column(name = "OPERATETIME")
	private Date operatetime;

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Date getOperatetime()
	{
		return operatetime;
	}

	public void setOperatetime(Date operatetime)
	{
		this.operatetime = operatetime;
	}
}
