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
@Results({
		@Result(name = "LaboratorySampleStatics", location = "jsp/highChart/LaboratorySampleStatics.jsp"),
		@Result(name = "MonitorSituationStatistics", location = "jsp/highChart/MonitorSituationStatistics.jsp"),
		@Result(name = "PersonBatchStatistics", location = "jsp/highChart/PersonBatchStatistics.jsp"),
		@Result(name = "ProjectSampleStatics", location = "jsp/highChart/ProjectSampleStatics.jsp"),
		@Result(name = "StaticsHistogram", location = "jsp/highChart/StaticsHistogram.jsp"),
		@Result(name = "WellStatistics", location = "jsp/highChart/WellStatistics.jsp"), })
@InterceptorRefs({ @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class tongjiAction extends BaseAction {

	private String sql;
	private String year;
	private TongjiDAO jdao = new TongjiDAO();

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
	
	

	/*
	 * @Action(value="ListProcedureAnalysis") public String listDic(){ return
	 * "ListProcedureAnalysis"; }
	 */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	// author:liupingtoday,后来添加的LaboratorySampleStatics页面的Action
	//@Action(value = "ListRegionSample")
	@Action(value = "ListLaboratorySampleStatics")
	public String listLaboratorySampleStatics() {
		return "LaboratorySampleStatics";
	}

	// author:liupingtoday,后来添加的MonitorSituationStatistics页面的Action
	@Action(value = "ListMonitorSituationStatistics")
	public String listMonitorSituationStatistics() {
		System.out.println("===");
		return "MonitorSituationStatistics";
	}

	// author:liupingtoday,后来添加的MonitorSituationStatistics页面的Action
	// @Action(value="ListPersonBatchStatistics")
	@Action(value = "ListPersonBatchStatistics")
	public String listPersonBatchStatistics() {
		return "PersonBatchStatistics";
	}

	// author:liupingtoday,后来添加的MonitorSituationStatistics页面的Action
	// @Action(value="ListProjectSampleStatics")
	@Action(value = "ListProjectSampleStatics")
	public String listProjectSampleStatics() {
		return "ProjectSampleStatics";
	}

	// author:liupingtoday,后来添加的MonitorSituationStatistics页面的Action
	@Action(value = "ListStaticsHistogram")
	public String listStaticsHistogram() {
		return "StaticsHistogram";
	}

	// author:liupingtoday,后来添加的MonitorSituationStatistics页面的Action
	//@Action(value = "ListWellStatistics")
	//@Action(value = "ListItemStatistic")
	@Action(value = "ListWellStatistics")
	public String listWellStatistics() {
		return "WellStatistics";
	}

	/*
	 * @Action(value="ListEmployeeAnalysis") public String JsonDatadictionary(){
	 * return "ListEmployeeAnalysis";
	 * 
	 * }
	 */
	/*@Action(value = "ListAnalysis")
	public String JsonDatadictionaryBysjbmc() {
		return "ListAnalysis";
	}*/

	@Action(value = "jsonph")
	public void jsonph() {
		String ph = this.jdao.jsonph(year);
		System.out.println(ph);
		this.printJson(ph);
	}

	@Action(value = "jsonwcqk")
	public void jsonwcqk() {
		String ph = this.jdao.jsonwcqk(year);
		System.out.println(ph);
		this.printJson(ph);
	}
/*
	@Action(value = "jsonsql")
	public void jsonsql() {
		System.out.println("自定义" + sql);
		String s = this.jdao.jsonsql(sql);
		System.out.println(s);
		this.printJson(s);
	}*/

	@Action(value = "createSql")
	public void ceateSql() throws UnsupportedEncodingException {
		List<?> result = this.jdao.createSql(java.net.URLDecoder.decode(sql,"utf-8"));
		this.jsonpArray(result);
	}

	// 一般都不用的，可以删除
	@Action(value = "createSqlJSON")
	public void ceateSqlJSON() {
		List<?> result = this.jdao.createSql(sql);
		// this.jsonDataGrid(result);
		this.jsonArray(result);
	}
}