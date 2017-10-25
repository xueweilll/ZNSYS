package com.jfsl.pojo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "A_Area")
public class Area extends Pojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AreaID")
	private int areaID;
	@Column(name = "AreaName")
	private String areaName;
	@Column(name = "ShowOrder")
	private int showOrder;
	@Column(name = "BelongIn")
	private int belongIn;
	public int getAreaID() {
		return areaID;
	}
	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	public int getBelongIn() {
		return belongIn;
	}
	public void setBelongIn(int belongIn) {
		this.belongIn = belongIn;
	}
	 
}
