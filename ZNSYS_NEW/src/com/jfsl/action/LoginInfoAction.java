package com.jfsl.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.LoginInfoDAO;
import com.jfsl.pojo.LoginInfo;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doListLoginOnline", location = "./jsp/logininfo/ListLoginOnline.jsp"),
		@Result(name = "doJsonLoginOnline", location = "./jsp/logininfo/JsonLoginOnline.jsp"),
		@Result(name = "doListLoginHistory", location = "./jsp/logininfo/ListLoginHistory.jsp"),
		@Result(name = "doJsonLoginHistory", location = "./jsp/logininfo/JsonLoginHistory.jsp"),
		@Result(name = "doListLoginStatistics", location = "./jsp/logininfo/ListLoginStatistics.jsp"),
		@Result(name = "doJsonLoginStatistics", location = "./jsp/logininfo/JsonLoginStatistics.jsp"),
		})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })

		public class LoginInfoAction extends BaseAction
{
	private LoginInfoDAO lid = new LoginInfoDAO();

	private String begintime, endtime, username, departmentid;
	private List<LoginInfo> loginonline;
	private List<LoginInfo> loginhistory;
	@SuppressWarnings("unchecked")
	private List loginstatistics;

	public List<LoginInfo> getLoginhistory()
	{
		return loginhistory;
	}

	@SuppressWarnings("unchecked")
	public List getLoginstatistics()
	{
		return loginstatistics;
	}

	public List<LoginInfo> getLoginonline()
	{
		return loginonline;
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

	@Action(value = "ListLoginOnline")
	public String doListLoginOnline()
	{
		return "doListLoginOnline";
	}
	
	//在线用户
	@Action(value = "JsonLoginOnline")
	public String doJsonLoginOnline()
	{
		loginonline = lid.getLoginOnline();
		return "doJsonLoginOnline";
	}

	@Action(value = "LogoutOnline")
	public String doLogoutUser()
	{
		lid.Logout(username);
		return "";
	}

	@Action(value = "ListLoginStatistics")
	public String doListLoginStatistics()
	{
		return "doListLoginStatistics";
	}

	@Action(value = "JsonLoginStatistics")
	public String doJsonLoginStatistics()
	{
		try {
			loginstatistics = lid.StatisticByUser(departmentid, begintime, endtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "doJsonLoginStatistics";
	}

	@Action(value = "ListLoginHistory")
	public String doListLoginHistory()
	{
		return "doListLoginHistory";
	}

	@Action(value = "JsonLoginHistory")
	public String doJsonLoginHistory()
	{
		try {
			loginhistory = lid.getLoginHistory(departmentid, username, begintime,
					endtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "doJsonLoginHistory";
	}
}