package com.jfsl.pojo;

import java.math.BigDecimal;
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
@Table(name = "ST_WASAV_R")
public class WasavR extends Pojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "F_ID")
	private int id;
	@Column(name = "IDTM")
	private Date idtm;
	@Column(name = "STTDRCD")
	private BigDecimal sttdrcd;
	@Column(name = "AVUPZ")
	private BigDecimal avupz;
	@Column(name = "AVDWZ")
	private BigDecimal avdwz;
	@Column(name = "AVGTQ")
	private BigDecimal avgtq;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getIdtm() {
		return idtm;
	}

	public void setIdtm(Date idtm) {
		this.idtm = idtm;
	}

	public BigDecimal getSttdrcd() {
		return sttdrcd;
	}

	public void setSttdrcd(BigDecimal sttdrcd) {
		this.sttdrcd = sttdrcd;
	}

	public BigDecimal getAvupz() {
		return avupz;
	}

	public void setAvupz(BigDecimal avupz) {
		this.avupz = avupz;
	}

	public BigDecimal getAvdwz() {
		return avdwz;
	}

	public void setAvdwz(BigDecimal avdwz) {
		this.avdwz = avdwz;
	}

	public BigDecimal getAvgtq() {
		return avgtq;
	}

	public void setAvgtq(BigDecimal avgtq) {
		this.avgtq = avgtq;
	}
}
