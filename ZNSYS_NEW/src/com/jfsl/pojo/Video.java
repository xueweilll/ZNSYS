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
@Table(name = "T_B_Video")
public class Video extends Pojo
{
	@Id
	@SequenceGenerator(name = "SEQ_T_B_VIDEO", sequenceName = "SEQ_T_B_VIDEO", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_B_VIDEO", strategy = GenerationType.SEQUENCE)
	@Column(name = "F_ID", unique = true, nullable = false, precision = 22, scale = 0)
	private int id;
	@Column(name = "F_Code")
	private String code;
	@Column(name = "F_Caption")
	private String caption;
	@Column(name = "F_Url")
	private String url;
	@Column(name = "F_Memo")
	private String memo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
