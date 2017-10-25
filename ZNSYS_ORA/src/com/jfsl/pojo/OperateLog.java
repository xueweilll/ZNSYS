package com.jfsl.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_OPERATELOG")
public class OperateLog extends Pojo
{
	@Id
	private OperateLogPk operatelogpk;
	@Column(name = "IPADDRESS")
	private String ipaddress;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "OPERATEOBJECT")
	private Action action;

	public OperateLogPk getOperatelogpk()
	{
		return operatelogpk;
	}

	public void setOperatelogpk(OperateLogPk operatelogpk)
	{
		this.operatelogpk = operatelogpk;
	}

	public String getIpaddress()
	{
		return ipaddress;
	}

	public void setIpaddress(String ipaddress)
	{
		this.ipaddress = ipaddress;
	}

	public Action getAction()
	{
		return action;
	}

	public void setAction(Action action)
	{
		this.action = action;
	}
}
