package com.jfsl.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_R_WarnLog")
public class WarnLog extends Pojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "F_ID")
	private int id;
	@Column(name = "F_WarnCode")
	private String warncode;
	@Column(name = "F_WarnTime")
	private Date warntime;
	@Column(name = "F_FreeTime")
	private Date freetime;
	@Column(name = "F_WarnState")
	private String warnstate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWarntime() {
		if (warntime==null) return "";
		else return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(warntime);
	}

	public void setWarntime(Date warntime) {
		this.warntime = warntime;
	}

	public String getFreetime() {
		if (freetime==null) return "";
		else return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(freetime);
	}

	public void setFreetime(Date freetime) {
		this.freetime = freetime;
	}

	public String getWarnstate() {
		return warnstate;
	}

	public void setWarnstate(String warnstate) {
		this.warnstate = warnstate;
	}

	public String getWarncode() {
		return warncode;
	}

	public void setWarncode(String warncode) {
		this.warncode = warncode;
	}
}
