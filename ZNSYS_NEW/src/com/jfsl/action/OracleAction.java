package com.jfsl.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.OracleDAO;


@ParentPackage("default")
@Results( {	
		@Result(name = "doList", location = "jsp/area/ListArea.jsp")
		})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class OracleAction extends BaseAction
{
	
	@Action(value="oracle")
	public void oracle(){
		OracleDAO odao=new OracleDAO();
		odao.test();
		
	}
}