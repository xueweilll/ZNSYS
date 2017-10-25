package com.jfsl.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
//import org.apache.struts2.jasper.util.SystemLogHandler;
import org.omg.CORBA.Request;

import com.jfsl.dao.DataDictionaryDAO;
import com.jfsl.dao.SCGCDAO;
import com.jfsl.pojo.*;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doList", location = "jsp/scgc/ListScgc.jsp")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class SCGCAction extends BaseAction{
	private String xmzmc;
	private String jcxm;
	private String scgcname;
	
	private SCGC scgc;
	private List<SCGC> scgcs;

	private SCGCDAO sdao = new SCGCDAO();
	private DataDictionaryDAO ddao = new DataDictionaryDAO();

	public SCGC getScgc() {
		return scgc;
	}

	public void setScgc(SCGC scgc) {
		this.scgc = scgc;
	}
	
	public String getXmzmc() {
		return xmzmc;
	}

	public void setXmzmc(String xmzmc) {
		this.xmzmc = xmzmc;
	}

	public String getJcxm() {
		return jcxm;
	}

	public void setJcxm(String jcxm) {
		this.jcxm = jcxm;
	}

	public String getScgcname() {
		return scgcname;
	}

	public void setScgcname(String scgcname) {
		this.scgcname = scgcname;
	}

	public List<SCGC> getScgcs() {
		return scgcs;
	}

	public void setScgcs(List<SCGC> scgcs) {
		this.scgcs = scgcs;
	}

	public SCGCDAO getSdao() {
		return sdao;
	}

	public void setSdao(SCGCDAO sdao) {
		this.sdao = sdao;
	}

	public DataDictionaryDAO getDdao() {
		return ddao;
	}

	public void setDdao(DataDictionaryDAO ddao) {
		this.ddao = ddao;
	}

	@Action(value = "ListScgc")
	public String doList(){
		return "doList";
	}
	@Action(value = "JsonSCGC")
	public void doJson(){
		sdao.clearOracleCurrentSession();
		scgcs = sdao.ListAll();
		this.jsonDataGrid(scgcs);
	}
	
	@Action(value = "CombXMZMC")
	public void Combxmzmc(){
		int number = 1;
		List list = new ArrayList();
		
		sdao.clearOracleCurrentSession();
		List ls = sdao.ListCombxmzmc();
		for(Iterator i = ls.iterator();i.hasNext();){
			String temp = (String)i.next();
			String data = "{\"id\":"+number+",\"text\":\""+temp+"\"}";
			list.add(data);
			number++;
		}
		
		this.jsonCombobox(list);
	}
	
	@Action(value = "CombJCXM")
	public void CombJCXM(){
		int number = 1;
		List list = new ArrayList();
		
		sdao.clearOracleCurrentSession();
		List ls = sdao.ListComJCXM();
		for(Iterator i = ls.iterator();i.hasNext();){
			String temp = (String)i.next();
			String data = "{\"id\":"+number+",\"text\":\""+temp+"\"}";
			list.add(data);
			number++;
		}
		
		this.jsonCombobox(list);
	}
	
	@Action(value = "JsonSCGCbyJCXM")
	public void CombSCGCbyJCXM(){
		sdao.clearOracleCurrentSession();
		scgcs = sdao.ListJCXM(jcxm);
		
		this.jsonDataGrid(scgcs);
	}
	
	@Action(value = "JsonSCGCbyXMZMC")
	public void CombSCGCbyXMZMC(){
		sdao.clearOracleCurrentSession();
		scgcs = sdao.ListXMZMC(xmzmc);
		
		this.jsonDataGrid(scgcs);
	}
	
	@Action(value = "ComboSJZD")
	public void ComboSJZD(){
		ddao.clearOracleCurrentSession();
		this.printJson(ddao.jsonTable());
	}
}
