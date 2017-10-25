package com.jfsl.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.OracleDAO;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {	
		@Result(name = "doList", location = "jsp/process/ListProcess.jsp")
		})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class ProcessAction extends BaseAction
{

	@Action(value = "ListProcess")
	public String doList()
	{
		return "doList";
	}

	
}