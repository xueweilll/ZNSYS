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
@Table(name = "T_C_Control")
public class Control extends Pojo
{
	/**����������*/
	@Id
	@SequenceGenerator(name = "SEQ_T_C_CONTROL", sequenceName = "SEQ_T_C_CONTROL", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_C_CONTROL", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private int id;
	
	/**��ͨ����*/
	@Column(name = "Numbers")
	private String number;
	@Column(name = "Name")
	private String name;
	@Column(name = "Address")
	private String address;
	@Column(name = "IP")
	private String ip;
	@Column(name = "Memo")
	private String memo;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
