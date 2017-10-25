package com.jfsl.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.WarnLogDAO;

@SuppressWarnings({ "serial" })
@ParentPackage("default")
@Results( {	
		@Result(name = "doList", location = "jsp/warnlog/ListWarnLog.jsp")
		})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class WarnLogAction extends BaseAction
{
	private String stationid;
	private String begintime;
	private String endtime;

	public String getStationid() {
		return stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@Action(value = "JsonWarnLog")
	public void doJson()
	{
		this.jsonDataGrid(new WarnLogDAO().ListAll(stationid,begintime,endtime));
	}

	@Action(value = "ListWarnLog")
	public String doList()
	{
		return "doList";
	}

}
