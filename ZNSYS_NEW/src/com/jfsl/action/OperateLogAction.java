package com.jfsl.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.OperateLogDAO;
import com.jfsl.pojo.OperateLog;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results
({
	@Result(name="doListOperateLog",location="./jsp/operatelog/ListOperateLog.jsp"),
	@Result(name="doJsonOperateLog",location="./jsp/operatelog/JsonOperateLog.jsp"),
	@Result(name="doListOperateLogStatistics",location="./jsp/operatelog/ListOperateLogStatistics.jsp"),
	@Result(name="doJsonOperateLogStatistics",location="./jsp/operatelog/JsonOperateLogStatistics.jsp"),

})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class OperateLogAction extends BaseAction
{
	private OperateLogDAO old=new OperateLogDAO();
	
	private String begintime,endtime,username,departmentid;
	private List<OperateLog> operatelog;
	
	@SuppressWarnings("unchecked")
	private List statistics;
	
	private int page,rows;
	
	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getRows()
	{
		return rows;
	}

	public void setRows(int rows)
	{
		this.rows = rows;
	}
	
	@SuppressWarnings("unchecked")
	public List getStatistics()
	{
		return statistics;
	}

	public String getBegintime()
	{
		return begintime;
	}
	public void setBegintime(String begintime)
	{
		this.begintime = begintime;
	}
	public String getEndtime()
	{
		return endtime;
	}
	public void setEndtime(String endtime)
	{
		this.endtime = endtime;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getDepartmentid()
	{
		return departmentid;
	}
	public void setDepartmentid(String departmentid)
	{
		this.departmentid = departmentid;
	}
	public List<OperateLog> getOperatelog()
	{
		return operatelog;
	}
	public void setOperatelog(List<OperateLog> operatelog)
	{
		this.operatelog = operatelog;
	}
	
	@Action(value="ListOperateLog")
	public String doListOperateLog()
	{
		return "doListOperateLog";
	}
	
	@Action(value="JsonOperateLog")
	public String doJsonOperateLog()
	{
		try {
			operatelog=old.listLog(departmentid, username, begintime, endtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "doJsonOperateLog";
	}
	
	@Action(value="ListOperateLogStatistics")
	public String doListOperateLogStatistics()
	{
		return "doListOperateLogStatistics";
	}
	
	@Action(value="JsonOperateLogStatistics")
	public String doJsonOperateLogStatistics()
	{
		try {
			statistics=old.StatisticByUser(begintime,endtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "doJsonOperateLogStatistics";
	}
}