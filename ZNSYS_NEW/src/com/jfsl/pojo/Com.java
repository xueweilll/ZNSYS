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
@Table(name = "T_R_SmsCom")
public class Com extends Pojo
{
	/**主键自增长*/
	@Id
	@SequenceGenerator(name = "SEQ_T_R_SMSCOM", sequenceName = "SEQ_T_R_SMSCOM", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_R_SMSCOM", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private int id;
	
	/**普通属性*/
	@Column(name = "Com")
	private String com;
	@Column(name = "BaudRate")
	private String baudrate;
	@Column(name = "Brand")
	private String brand;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getBaudrate() {
		return baudrate;
	}
	public void setBaudrate(String baudrate) {
		this.baudrate = baudrate;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	/*public String getDateline() {
		if(dateline == null) return "";
		else return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateline);
	}
	public void setDateline(String dateline) {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.dateline =  simple.parse(dateline);
	}*/
}
