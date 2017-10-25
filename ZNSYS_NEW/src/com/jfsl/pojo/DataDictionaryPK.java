package com.jfsl.pojo;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class DataDictionaryPK extends Pojo {
	 
	@Column(name = "SJBMC")
	private String SJBMC;
	@Column(name = "PYDM")
	private String PYDM;
	public String getSJBMC() {
		return SJBMC;
	}
	public void setSJBMC(String sJBMC) {
		SJBMC = sJBMC;
	}
	public String getPYDM() {
		return PYDM;
	}
	public void setPYDM(String pYDM) {
		PYDM = pYDM;
	}
}
