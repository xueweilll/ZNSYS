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
@Table(name = "DATABACK")
public class Backup extends Pojo  
{
	@Id
	@SequenceGenerator(name = "SEQ_BACKUP", sequenceName = "SEQ_BACKUP", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_BACKUP", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private int id;
	@Column(name = "PATH")
	private String path;
	@Column(name = "CRETIME")
	private Date creTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getCreTime() {
		return creTime;
	}
	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}
	 
	 

	 
}
