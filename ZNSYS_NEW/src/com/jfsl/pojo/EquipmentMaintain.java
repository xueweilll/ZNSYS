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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "E_EquipmentMaintain")
public class EquipmentMaintain extends Pojo
{
	@Id
	@SequenceGenerator(name = "SEQ_E_EQUIPMENTMAINTAIN", sequenceName = "SEQ_E_EQUIPMENTMAINTAIN", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_E_EQUIPMENTMAINTAIN", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private int id;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "EquipmentID")
	private Equipment equipment;
	
	@Column(name = "EquipmentMaintainName")
	private String equipmentmaintainname;
	
	@Column(name = "EquipmentMaintainContent")
	private String equipmentmaintaincontent;
	
	@Column(name = "MaintainPeople")
	private String maintainpeople;
	
	@Column(name = "MaintainTime")
	private Date maintaintime;
	
	@Column(name = "Memo")
	private String memo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public String getEquipmentmaintainname() {
		return equipmentmaintainname;
	}
	public void setEquipmentmaintainname(String equipmentmaintainname) {
		this.equipmentmaintainname = equipmentmaintainname;
	}
	public String getEquipmentmaintaincontent() {
		return equipmentmaintaincontent;
	}
	public void setEquipmentmaintaincontent(String equipmentmaintaincontent) {
		this.equipmentmaintaincontent = equipmentmaintaincontent;
	}
	public String getMaintainpeople() {
		return maintainpeople;
	}
	public void setMaintainpeople(String maintainpeople) {
		this.maintainpeople = maintainpeople;
	}
	
	/*时间需要特殊转化*/
	public String getMaintaintime() {
		if(maintaintime == null) return "";
		else return new java.text.SimpleDateFormat("yyyy-MM-dd").format(maintaintime);
	}
	
	public void setMaintaintime(String maintaintime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			this.maintaintime = sdf.parse(maintaintime);
		}catch(Exception e){
			this.maintaintime = null;
		}
	}
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
