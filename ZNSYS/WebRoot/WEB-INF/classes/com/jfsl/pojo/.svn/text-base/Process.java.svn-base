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
@Table(name = "A_Process")
public class Process extends Pojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProcessID")
	private int processID;
	@Column(name = "ProcessName")
	private String processName;
	public int getProcessID() {
		return processID;
	}
	public void setProcessID(int processID) {
		this.processID = processID;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	 
	 
	
}
