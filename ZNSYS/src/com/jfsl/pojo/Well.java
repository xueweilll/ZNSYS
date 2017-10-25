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
@Table(name = "A_Well")
public class Well extends Pojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WellId")
	private int wellId;
	@Column(name = "WellType")
	private String wellType;
	@Column(name = "WellName")
	private String wellName;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "AreaID")
	private Area area;
	public int getWellId() {
		return wellId;
	}
	public void setWellId(int wellId) {
		this.wellId = wellId;
	}
	public String getWellType() {
		return wellType;
	}
	public void setWellType(String wellType) {
		this.wellType = wellType;
	}
	public String getWellName() {
		return wellName;
	}
	public void setWellName(String wellName) {
		this.wellName = wellName;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
	

}
