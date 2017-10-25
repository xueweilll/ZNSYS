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
@Table(name = "A_Steps")
public class Steps extends Pojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "StepsID")
	private int stepsID;
	@Column(name = "StepsName")
	private String stepsName;
	@Column(name = "StepsNumber")
	private int stepsNumber;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ProcessID") 
	private Process process;
	public int getStepsID() {
		return stepsID;
	}
	public void setStepsID(int stepsID) {
		this.stepsID = stepsID;
	}
	public String getStepsName() {
		return stepsName;
	}
	public void setStepsName(String stepsName) {
		this.stepsName = stepsName;
	}
	public int getStepsNumber() {
		return stepsNumber;
	}
	public void setStepsNumber(int stepsNumber) {
		this.stepsNumber = stepsNumber;
	}
	public Process getProcess() {
		return process;
	}
	public void setProcess(Process process) {
		this.process = process;
	}
	 
	 
	
}
