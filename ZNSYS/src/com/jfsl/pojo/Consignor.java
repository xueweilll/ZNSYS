package com.jfsl.pojo;

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
@Table(name = "A_Consignor")
public class Consignor extends Pojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ConsignorId")
	private int consignorId;
	@Column(name = "ConsignorName")
	private String consignorName;
	@Column(name = "ConsignorAddress")
	private String consignorAddress;
	@Column(name = "ContacWay")
	private String contacWay;
	@Column(name = "ConsignorRemarks")
	private String consignorRemarks;
	
	public int getConsignorId() {
		return consignorId;
	}
	public void setConsignorId(int consignorId) {
		this.consignorId = consignorId;
	}
	public String getConsignorName() {
		return consignorName;
	}
	public void setConsignorName(String consignorName) {
		this.consignorName = consignorName;
	}
	public String getConsignorAddress() {
		return consignorAddress;
	}
	public void setConsignorAddress(String consignorAddress) {
		this.consignorAddress = consignorAddress;
	}
	public String getContacWay() {
		return contacWay;
	}
	public void setContacWay(String contacWay) {
		this.contacWay = contacWay;
	}
	public String getConsignorRemarks() {
		return consignorRemarks;
	}
	public void setConsignorRemarks(String consignorRemarks) {
		this.consignorRemarks = consignorRemarks;
	}
	
	 
	
}
