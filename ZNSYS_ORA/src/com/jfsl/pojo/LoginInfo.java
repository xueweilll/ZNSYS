package com.jfsl.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_LOGININFO")
public class LoginInfo extends Pojo
{
	@Id
	private LoginInfoPk logininfopk;
	@Column(name = "IPADDRESS")
	private String ipaddress;
	@Column(name = "LOGOUTTIME")
	private Date logouttime;

	public LoginInfoPk getLogininfopk()
	{
		return logininfopk;
	}

	public void setLogininfopk(LoginInfoPk logininfopk)
	{
		this.logininfopk = logininfopk;
	}

	public String getIpaddress()
	{
		return ipaddress;
	}

	public void setIpaddress(String ipaddress)
	{
		this.ipaddress = ipaddress;
	}

	public Date getLogouttime()
	{
		return logouttime;
	}

	public void setLogouttime(Date logouttime)
	{
		this.logouttime = logouttime;
	}
}
