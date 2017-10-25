package com.jfsl.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

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
	
	private String oper;
	
	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

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
		List<String> list = new ArrayList<String>();
		
		sdao.clearOracleCurrentSession();
		List<?> ls = sdao.ListCombxmzmc();
		for(Iterator<?> i = ls.iterator();i.hasNext();){
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
		List<String> list = new ArrayList<String>();
		
		sdao.clearOracleCurrentSession();
		List<?> ls = sdao.ListComJCXM();
		for(Iterator<?> i = ls.iterator();i.hasNext();){
			String temp = (String)i.next();
			String data = "{\"id\":"+number+",\"text\":\""+temp+"\"}";
			list.add(data);
			number++;
		}
		
		this.jsonCombobox(list);
	}
	
	@Action(value = "JsonSCGCbyJCXM")
	public void CombSCGCbyJCXM() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String jcxm2 = URLDecoder.decode((String) request.getParameter("jcxm"),"utf-8");
		System.out.println(jcxm2);
		sdao.clearOracleCurrentSession();
		scgcs = sdao.ListJCXM(jcxm2);
		
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
	
	String json;
	
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	@Action(value = "DeleteScgc")
	public String DeleteScgc(){
		try {
			JSONObject jsonObj = JSONObject.fromObject(json);
			SCGCKey key = (SCGCKey) JSONObject.toBean(jsonObj,SCGCKey.class);
			SCGC pojo = sdao.SCGCById(key);
			sdao.OracleDelete(pojo);
			return "delete";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Action(value = "InsertScgc")
	public String InsertScgc(){
		JSONObject jsonObj = JSONObject.fromObject(json);
		scgc = (SCGC) JSONObject.toBean(jsonObj,SCGC.class);
		try {
			if("0".equals(oper)){
				sdao.OracleAdd(scgc);
			}else{
				SCGC pojo = sdao.SCGCById(scgc.getId());
				pojo.setGcsx(scgc.getGcsx());
				pojo.setGridviewsource(scgc.getGridviewsource());
				pojo.setDatawhere(scgc.getDatawhere());
				pojo.setName(scgc.getName());
				pojo.setSign(scgc.getSign());
				pojo.setUrl(scgc.getUrl());
				pojo.setYsc(scgc.getYsc());
				sdao.OracleUpdate(pojo);
			}
			return "add";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
}
