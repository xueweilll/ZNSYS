package com.jfsl.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "E_Equipment")
public class Equipment extends Pojo
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "EquipmentName")
	private String equipmentname;
	
	@Column(name = "EquipmentNumber")
	private String equipmentnumber;
	
	@Column(name = "BuyTime")
	private Date buytime;
	
	@Column(name = "Worth")
	private Float worth;
	
	@Column(name = "Brand")
	private String brand;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "Admin")
	private String admin;
	
	@Column(name = "Memo")
	private String memo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEquipmentname() {
		return equipmentname;
	}

	public void setEquipmentname(String equipmentname) {
		this.equipmentname = equipmentname;
	}

	public String getEquipmentnumber() {
		return equipmentnumber;
	}

	public void setEquipmentnumber(String equipmentnumber) {
		this.equipmentnumber = equipmentnumber;
	}

	public Float getWorth() {
		return worth;
	}

	public void setWorth(Float worth) {
		this.worth = worth;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/*时间需要特殊处理*/
	public String getBuytime() {
		if(buytime == null) return "";
		else return new java.text.SimpleDateFormat("yyyy-MM-dd").format(buytime);
		//return buytime;
	}
	
	public void setBuytime(String buytime){
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		try{
			this.buytime = date.parse(buytime);
		}catch(Exception e){
			this.buytime = null;
		}
	}
}
