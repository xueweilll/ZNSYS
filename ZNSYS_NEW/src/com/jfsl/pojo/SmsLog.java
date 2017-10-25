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
@Table(name = "T_R_SmsLog")
public class SmsLog extends Pojo {

	@Id
	@SequenceGenerator(name = "SEQ_T_R_SMSLOG", sequenceName = "SEQ_T_R_SMSLOG", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_R_SMSLOG", strategy = GenerationType.SEQUENCE)
	@Column(name = "F_ID", unique = true, nullable = false, precision = 22, scale = 0)
	private int id;
	@Column(name = "F_Tel")
	private String tel;
	@Column(name = "F_DecType")
	private String dectype;
	@Column(name = "F_SendState")
	private String sendstate;
	@Column(name = "F_ResponseTime")
	private Date responsetime;
	@Column(name = "F_Memo")
	private String memo;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "F_SmsID")
	private Sms sms;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "F_DscID")
	private AddList addlist;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDectype() {
		return dectype;
	}

	public void setDectype(String dectype) {
		this.dectype = dectype;
	}

	public String getSendstate() {
		return sendstate;
	}

	public void setSendstate(String sendstate) {
		this.sendstate = sendstate;
	}

	public String getResponsetime() {
		if (responsetime == null)
			return "";
		else
			return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(responsetime);
	}

	public void setResponsetime(Date responsetime) {
		this.responsetime = responsetime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Sms getSms() {
		return sms;
	}

	public void setSms(Sms sms) {
		this.sms = sms;
	}

	public void setAddlist(AddList addlist) {
		this.addlist = addlist;
	}

	public AddList getAddlist() {
		return addlist;
	}

}
