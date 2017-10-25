package com.jfsl.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_R_Sms")
public class Sms extends Pojo {

	@Id
	@SequenceGenerator(name = "SEQ_T_R_SMS", sequenceName = "SEQ_T_R_SMS", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_R_SMS", strategy = GenerationType.SEQUENCE)
	@Column(name = "F_ID", unique = true, nullable = false, precision = 22, scale = 0)
	private int id;
	@Column(name = "F_SmsType")
	private String smstype;
	@Column(name = "F_CreateTime")
	private Date createtime;
	@Column(name = "F_SendReqTime")
	private Date sendreqtime;
	@Column(name = "F_PostState")
	private String poststate;
	@Column(name = "F_Content")
	private String content;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "F_UserID")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSmstype() {
		return smstype;
	}

	public void setSmstype(String smstype) {
		this.smstype = smstype;
	}

	public String getCreatetime() {
		if (createtime == null)
			return "";
		else
			return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(createtime);
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getSendreqtime() {
		if (sendreqtime == null)
			return "";
		else
			return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(sendreqtime);
	}

	public void setSendreqtime(Date sendreqtime) {
		this.sendreqtime = sendreqtime;
	}

	public String getPoststate() {
		return poststate;
	}

	public void setPoststate(String poststate) {
		this.poststate = poststate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
