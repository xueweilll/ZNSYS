package com.jfsl.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "E_EquipmentFault")
public class EquipmentFault extends Pojo
{
	@Id
	@SequenceGenerator(name = "SEQ_E_EQUIPMENTFAULT", sequenceName = "SEQ_E_EQUIPMENTFAULT", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_E_EQUIPMENTFAULT", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private int id;
	@Column(name = "EquipmentID")
	private String equipmentid;
	@Column(name = "FaultPhenomenon")
	private String faultphenomenon;
	@Column(name = "FaultRepairContent")
	private String faultrepaircontent;
	@Column(name = "RepairMan")
	private Date repairman;
	@Column(name = "RepairCatagory")
	private String repaircatagory;
	@Column(name = "TimeOfFauilureStart")
	private Date timeoffauilurestart;
	@Column(name = "TimeOfFaultEnd")
	private Date timeoffaultend;
	@Column(name = "Memo")
	private String memo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEquipmentid() {
		return equipmentid;
	}
	public void setEquipmentid(String equipmentid) {
		this.equipmentid = equipmentid;
	}
	public String getFaultphenomenon() {
		return faultphenomenon;
	}
	public void setFaultphenomenon(String faultphenomenon) {
		this.faultphenomenon = faultphenomenon;
	}
	public String getFaultrepaircontent() {
		return faultrepaircontent;
	}
	public void setFaultrepaircontent(String faultrepaircontent) {
		this.faultrepaircontent = faultrepaircontent;
	}
	public Date getRepairman() {
		return repairman;
	}
	public void setRepairman(Date repairman) {
		this.repairman = repairman;
	}
	public String getRepaircatagory() {
		return repaircatagory;
	}
	public void setRepaircatagory(String repaircatagory) {
		this.repaircatagory = repaircatagory;
	}
	public Date getTimeoffauilurestart() {
		return timeoffauilurestart;
	}
	public void setTimeoffauilurestart(Date timeoffauilurestart) {
		this.timeoffauilurestart = timeoffauilurestart;
	}
	public Date getTimeoffaultend() {
		return timeoffaultend;
	}
	public void setTimeoffaultend(Date timeoffaultend) {
		this.timeoffaultend = timeoffaultend;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
