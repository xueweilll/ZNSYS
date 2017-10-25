package com.jfsl.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.TongjiDAO;
@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {	
		/*@Result(name = "ListAnalysis", location = "jsp/jqplot/sqltj.jsp"),
		@Result(name = "ListEmployeeAnalysis", location = "jsp/jqplot/test.jsp"),
		@Result(name = "ListProcedureAnalysis", location = "jsp/jqplot/tongji.jsp"),*/
		
		
		
		@Result(name = "ListAnalysis", location = "jsp/highChart/sqltj.jsp"),
		@Result(name = "ListEmployeeAnalysis", location = "jsp/highChart/test.jsp"),
		@Result(name = "ListProcedureAnalysis", location = "jsp/highChart/tongji.jsp"),
		
		})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class highChartStatisticAction extends BaseAction
{
	
	private String sql;
	private String year;
	private TongjiDAO jdao=new TongjiDAO();
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		try {
			this.sql = java.net.URLDecoder.decode(sql , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} ;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Action(value="ListProcedureAnalysis")
	public String listDic(){
		return "ListProcedureAnalysis";
	}
	 
	@Action(value="ListEmployeeAnalysis")
	public String JsonDatadictionary(){
		return "ListEmployeeAnalysis";
		
	}
	@Action(value="ListAnalysis")
	public String JsonDatadictionaryBysjbmc(){
		return "ListAnalysis";
	}
	
	@Action(value="jsonph")
	public void jsonph(){
		 String ph=this.jdao.jsonph(year);
		 System.out.println(ph);
		 this.printJson(ph);
	 }
	 
	@Action(value="jsonwcqk")
	public void jsonwcqk(){
		 String ph=this.jdao.jsonwcqk(year);
		 System.out.println(ph);
		 this.printJson(ph);
	 }
	@Action(value="jsonsql")
	public void jsonsql(){
		System.out.println("×Ô¶¨Òå"+sql);
		 String s=this.jdao.jsonsql(sql);
		 System.out.println(s);
		 this.printJson(s);
	}
	
	@Action(value="createSql")
	public void ceateSql(){
		List<?> result = null;
		try {
			result = this.jdao.createSql(java.net.URLDecoder.decode(sql,"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 //this.jsonDataGrid(result);
		System.out.println(result);
		 this.jsonpArray(result);
	}
}