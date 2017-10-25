package com.jfsl.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


import com.jfsl.dao.*;
import com.jfsl.pojo.*;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
	@Result(name = "ListProduce", location = "jsp/produce/ListProduce.jsp"), 
	@Result(name = "listbefore", location = "jsp/produce/FilterProcess.jsp"),
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class ProduceAction extends BaseAction
{
	//需要用到的属性
	private int produceid;
	private int flowid;
	
	//需要用到的pojo
	private DataDictionary datadictionary;
	private List<DataDictionary> DataDictionarys;
	private Flow flow;
	private List<Flow> flows;
	
	//需要用到的dao
	private DataDictionaryDAO ddao = new DataDictionaryDAO();
	private FlowDAO flowdao = new FlowDAO();
	private OracleDAO od=new OracleDAO();
	private ProduceDAO pdao=new ProduceDAO();
	
	public int getProduceid() {
		return produceid;
	}

	public void setProduceid(int produceid) {
		this.produceid = produceid;
	}

	public int getFlowid() {
		return flowid;
	}

	public void setFlowid(int flowid) {
		this.flowid = flowid;
	}

	public DataDictionary getDatadictionary() {
		return datadictionary;
	}

	public void setDatadictionary(DataDictionary datadictionary) {
		this.datadictionary = datadictionary;
	}

	public List<DataDictionary> getDataDictionarys() {
		return DataDictionarys;
	}

	public void setDataDictionarys(List<DataDictionary> dataDictionarys) {
		DataDictionarys = dataDictionarys;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public List<Flow> getFlows() {
		return flows;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	public DataDictionaryDAO getDdao() {
		return ddao;
	}

	public void setDdao(DataDictionaryDAO ddao) {
		this.ddao = ddao;
	}

	public FlowDAO getFlowdao() {
		return flowdao;
	}

	public void setFlowdao(FlowDAO flowdao) {
		this.flowdao = flowdao;
	}
	
	public OracleDAO getOd() {
		return od;
	}

	public void setOd(OracleDAO od) {
		this.od = od;
	}
	
	@Action(value = "ListProduce")
	public String ListProduce(){
		produceid = 33;
		datadictionary = ddao.Find(produceid);
		return "ListProduce";
	}
	@SuppressWarnings("unchecked")
	@Action(value="JsonProcess")
	public void jsonprocess(){
		try{
	//	System.out.println("JSON PROCESS...........");
		List l=od.jsonProcess();
		JSONObject jo=new JSONObject();
		jo.put("total",l.size());
		JSONArray ja=new JSONArray();
		for(int i=0;i<l.size();i++){
			JSONObject jjo=new JSONObject();
			Object[] o=(Object[]) l.get(i);
			String DQ=(String) o[0];
			String JH=(String) o[1];
			String JCXM=(String) o[2];
			String YPPH=(String) o[3];
			BigDecimal YPSL=(BigDecimal) o[4];
			String JYR=(String) o[5];
			String JCR=(String) o[6];
			String JHR=(String) o[7];
			String SPR=(String) o[8];
			String BGJSF=(String) o[9];
			String SCF=(String) o[10];
			jjo.put("YPPH",YPPH);
			jjo.put("DQ",DQ);
			jjo.put("JH",JH);
			jjo.put("JCXM",JCXM);
			jjo.put("YPSL",YPSL);
			jjo.put("WC","");
				jjo.put("JYR",JYR);
				jjo.put("JCR",JCR);
				jjo.put("JHR",JHR);
				jjo.put("SPR",SPR);
			
			if("0".equals(BGJSF))
				jjo.put("BGJSF","n");
			else
				jjo.put("BGJSF","y");
			if("0".equals(SCF))
				jjo.put("SCF","n");
			else
				jjo.put("SCF","y");
			 jjo.put("DDSY","y");
			ja.add(jjo);
		}
		jo.put("rows",ja);
		System.out.println("流程Json："+jo.toString());
		this.printJson(jo.toString());}
		catch(Exception e){
			e.printStackTrace();
	
		}
	}
	@Action(value="ListProduceBefore")
	public String be(){
		return "listbefore";
	}
	@Action (value="searchProgram")
	public void searchprogram() throws UnsupportedEncodingException{
		byte[] search=ServletActionContext.getRequest().getParameter("searchProgram").getBytes("ISO-8859-1");
		String serarchp=new String(search,"utf-8").replaceAll(" ","");
	//	System.out.println(serarchp);
		String s=pdao.searchbyprogram(serarchp);
	//	System.out.println(s);
		this.printJson(s);		
	}
	@Action (value="searchArea")
	public void searcharea() throws UnsupportedEncodingException{
		byte[] search=ServletActionContext.getRequest().getParameter("searchArea").getBytes("ISO-8859-1");
		String area=new String(search,"utf-8").replaceAll(" ","");
		System.out.println(area);
		String s=pdao.searchAreabyprogram(area);
		System.out.println(s);
		this.printJson(s);		
	}
	@Action (value="searchWell")
	public void searchwell() throws UnsupportedEncodingException{
		byte[] search=ServletActionContext.getRequest().getParameter("searchWell").getBytes("ISO-8859-1");
		String well=new String(search,"utf-8").replaceAll(" ","");
		System.out.println(well);
		String s=pdao.searchWellbyprogram(well);
		System.out.println(s);
		this.printJson(s);		
	}
	@Action (value="searchPh")
	public void searchph() throws UnsupportedEncodingException{
		byte[] search=ServletActionContext.getRequest().getParameter("searchPh").getBytes("ISO-8859-1");
		String searchPh=new String(search,"utf-8").replaceAll(" ","");
		System.out.println(searchPh);
		String s=pdao.searchPhbyprogram(searchPh);
		System.out.println(s);
		this.printJson(s);		
	}
	@Action (value="searchWellByArea")
	public void searchWellByArea() throws UnsupportedEncodingException{
		byte[] search=ServletActionContext.getRequest().getParameter("searchWell").getBytes("ISO-8859-1");
		String well=new String(search,"utf-8").replaceAll(" ","");
		System.out.println(well);
		String s=pdao.searchWellbyArea(well);
		System.out.println(s);
		this.printJson(s);	
	}
	
	@Action (value="searchPhByWell")
	public void searchPhByWell() throws UnsupportedEncodingException{
		byte[] search=ServletActionContext.getRequest().getParameter("searchPh").getBytes("ISO-8859-1");
		String ph=new String(search,"utf-8").replaceAll(" ","");
		System.out.println(ph);
		String s=pdao.searchPhbyWell(ph);
		System.out.println(s);
		this.printJson(s);	
	}
	
	@Action (value="searchProByPh")
	public void searchProByPh() throws UnsupportedEncodingException{
		String ph=ServletActionContext.getRequest().getParameter("searchProgram").replaceAll(" ","");
		System.out.println(ph);
		String s=pdao.searchProByPh(ph);
		System.out.println(s);
		this.printJson(s);	
	}
	@Action(value="JsonProcessByArea")
	public void JsonProcessByArea() throws UnsupportedEncodingException, SQLException{
		System.out.println("通过地区获取过程JsonProcessByArea");
		String area=ServletActionContext.getRequest().getParameter("area").replaceAll(" ","");
		System.out.println("地区名："+area);
		List l=od.jsonProcessByArea(area);
		this.jsonprocessByArea(l);
	}
	@Action(value="JsonProcessByWell")
	public void JsonProcessByWell() throws UnsupportedEncodingException, SQLException{
		System.out.println("通过地区获取过程JsonProcessByWell");
		String well=ServletActionContext.getRequest().getParameter("well").replaceAll(" ","");
		System.out.println("井号名："+well);
		List l=od.jsonProcessByWell(well);
		this.jsonprocessByArea(l);
	}
	@Action(value="JsonProcessByPh")
	public void JsonProcessByPh() throws UnsupportedEncodingException, SQLException{
		System.out.println("通过地区获取过程JsonProcessByWell");
		String ph=ServletActionContext.getRequest().getParameter("ph").replaceAll(" ","");
		System.out.println("批号名："+ph);
		List l=od.jsonProcessByPh(ph);
		this.jsonprocessByArea(l);
	}
	@Action(value="JsonProcessByJcxm")
	public void JsonProcessByJcxm() throws UnsupportedEncodingException, SQLException{
		System.out.println("通过检测项目获取过程JsonProcessByWell");
		//byte[] search=ServletActionContext.getRequest().getParameter("jcxm");
		String jcxm=ServletActionContext.getRequest().getParameter("jcxm").replaceAll(" ","");
		System.out.println("检测项目："+jcxm);
		List l=od.jsonProcessByJcxm(jcxm);
		this.jsonprocessByArea(l);
	}
	public void jsonprocessByArea(List l){
		try{
		System.out.println("JSON PROCESS...........");
		JSONObject jo=new JSONObject();
		jo.put("total",l.size());
		JSONArray ja=new JSONArray();
		for(int i=0;i<l.size();i++){
			JSONObject jjo=new JSONObject();
			Object[] o=(Object[]) l.get(i);
			String DQ=(String) o[0];
			String JH=(String) o[1];
			String JCXM=(String) o[2];
			String YPPH=(String) o[3];
			BigDecimal YPSL=(BigDecimal) o[4];
			String JYR=(String) o[5];
			String JCR=(String) o[6];
			String JHR=(String) o[7];
			String SPR=(String) o[8];
			String BGJSF=(String) o[9];
			String SCF=(String) o[10];
			jjo.put("YPPH",YPPH);
			jjo.put("DQ",DQ);
			jjo.put("JH",JH);
			jjo.put("JCXM",JCXM);
			jjo.put("YPSL",YPSL);
			jjo.put("WC","");
				jjo.put("JYR",JYR);
				jjo.put("JCR",JCR);
				jjo.put("JHR",JHR);
				jjo.put("SPR",SPR);
			
			if("0".equals(BGJSF))
				jjo.put("BGJSF","n");
			else
				jjo.put("BGJSF","y");
			if("0".equals(SCF))
				jjo.put("SCF","n");
			else
				jjo.put("SCF","y");
			 jjo.put("DDSY","y");
			ja.add(jjo);
		}
		jo.put("rows",ja);
		System.out.println("流程Json："+jo.toString());
		this.printJson(jo.toString());}
		catch(Exception e){
			e.printStackTrace();
	
		}
	}
}
