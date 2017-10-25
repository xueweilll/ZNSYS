package com.jfsl.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "A_Area")
public class Area extends Pojo {
	@Id
	@SequenceGenerator(name = "SEQ_T_B_AREA", sequenceName = "SEQ_T_B_AREA", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_B_AREA", strategy = GenerationType.SEQUENCE)
	@Column(name = "AreaID", unique = true, nullable = false, precision = 22, scale = 0)
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
