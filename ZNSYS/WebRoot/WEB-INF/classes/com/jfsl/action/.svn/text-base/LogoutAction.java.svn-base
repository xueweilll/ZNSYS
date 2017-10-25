package com.jfsl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.LogoutDAO;
import com.jfsl.dao.OperateLogDAO;
import com.jfsl.pojo.User;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( { @Result(name = "success", type = "redirect", location = "login.jsp") })
public class LogoutAction extends ActionSupport
{
	LogoutDAO ld=new LogoutDAO();
	OperateLogDAO old=new OperateLogDAO();
	
	public String execute()
	{
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null)
		{
			String username=user.getUsername();
			String ipaddress = req.getRemoteAddr();
			old.saveLog(username, ipaddress, "Logout");
			ld.Logout(username);
		}

		// session.removeAttribute("modules");
		session.removeAttribute("functions");
		session.removeAttribute("user");
		session.removeAttribute("operations");
		return "success";
	}
}
