package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "SYZX_SY_SCGC")
public class SCGC extends Pojo
{
	private static final long serialVersionUID = 3524215936351012384L;
	
	@Id
	private SCGCKey id;
	
	/**∆’Õ® Ù–‘*/
	@Column(name = "GCSX")
	private String gcsx;
	@Column(name = "GRIDVIEWSOURCE")
	private String gridviewsource;
	@Column(name = "URL")
	private String url;
	@Column(name = "SIGN")
	private Integer sign;
	@Column(name = "YSC")
	private Integer ysc;
	@Column(name = "Name_Table")
	private String name;
	
	public String getGcsx() {
		return gcsx;
	}
	public void setGcsx(String gcsx) {
		this.gcsx = gcsx;
	}
	public String getGridviewsource() {
		return gridviewsource;
	}
	public void setGridviewsource(String gridviewsource) {
		this.gridviewsource = gridviewsource;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSign() {
		return sign;
	}
	public void setSign(Integer sign) {
		this.sign = sign;
	}
	public Integer getYsc() {
		return ysc;
	}
	public void setYsc(Integer ysc) {
		this.ysc = ysc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SCGCKey getId() {
		return id;
	}
	public void setId(SCGCKey id) {
		this.id = id;
	}
}
