package com.jfsl.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Embeddable
public class LoginInfoPk extends Pojo
{
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "UserName")
	private User user;
	@Column(name = "LoginTime")
	private Date logintime;

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Date getLogintime()
	{
		return logintime;
	}

	public void setLogintime(Date logintime)
	{
		this.logintime = logintime;
	}
}
