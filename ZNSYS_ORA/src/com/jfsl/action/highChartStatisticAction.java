package com.jfsl.action;

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
		@Result(name = "ListAnalysis", location = "jsp/jqplot/sqltj.jsp"),
		@Result(name = "ListEmployeeAnalysis", location = "jsp/jqplot/test.jsp"),
		@Result(name = "ListProcedureAnalysis", location = "jsp/jqplot/tongji.jsp"),
		
		
		
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
	private TongjiDAO jdao=new TongjiDAO();
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
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
	@SuppressWarnings("null")
	@Action(value="jsonph")
	public void jsonph(){
		 String ph=this.jdao.jsonph();
		 System.out.println(ph);
		 this.printJson(ph);
	 }
	 
	@Action(value="jsonwcqk")
	public void jsonwcqk(){
		 String ph=this.jdao.jsonwcqk();
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
		 List result=this.jdao.createSql(sql);
		 //this.jsonDataGrid(result);
		 this.jsonpArray(result);
	}
}