package com.jfsl.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.LoginDAO;
import com.jfsl.dao.OperateLogDAO;
import com.jfsl.dao.UserDAO;
import com.jfsl.dao.UserOperationDAO;
import com.jfsl.pojo.User;
import com.jfsl.pojo.UserOperation;
import com.jfsl.util.JSONOperator;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "failure", type = "redirect", location = "login.jsp"),
		@Result(name = "notlogin", type = "redirect", location = "login.jsp"),
		@Result(name = "useragain", type = "redirect", location = "login.jsp"),
		@Result(name = "success", type = "redirect", location = "Main"),
		@Result(name = "doJson", location = "menu.jsp"),
		@Result(name = "doMain", location = "main.jsp")
})
public class LoginAction extends ActionSupport
{
	LoginDAO logindao = new LoginDAO();
	UserDAO userdao = new UserDAO();
	UserOperationDAO uod = new UserOperationDAO();
	OperateLogDAO old=new OperateLogDAO();
	
	private String username;
	private String userpsw;
	@SuppressWarnings("unchecked")
	private List userfunctions;

	@SuppressWarnings("unchecked")
	public List getUserfunctions()
	{
		return userfunctions;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUserpsw()
	{
		return userpsw;
	}

	public void setUserpsw(String userpsw)
	{
		this.userpsw = userpsw;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Action(value = "Login")
	public String execute()
	{
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		String ipaddress = req.getRemoteAddr();

		if ((username == null) || (userpsw == null))
		{
			session.setAttribute("flag", "notlogin");
			return "notlogin";
		}
		/*
		 * «∑Ò‘ –Ì÷ÿ∏¥µ«¬º
		else if (!logindao.userEnable(username))
		{
			session.setAttribute("flag", "useragain");
			return "useragain";
		}
		*/
		else if (logindao.checkUser(username, userpsw))
		{
			logindao.Login(username, ipaddress);
			old.saveLog(username, ipaddress, "Login");
			
			List<UserOperation> ls_function = uod.getOperations(username);

			User user = userdao.getUserDepartment(username);
			List ls_operation = logindao.getOperaions(username);

			session.setAttribute("flag", "success");
			session.setAttribute("functions", ls_function);
			session.setAttribute("user", user);
			session.setAttribute("operations", ls_operation);
			session.setAttribute("password", userpsw);

			return "success";
		}
		else
		{
			session.setAttribute("flag", "failure");
			return "failure";
		}
	}

	@Action(value = "JsonUserFunction")
	public void doJson()
	{
		userfunctions = uod.getFunctions(username);
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setHeader("Cache-Control","no-store"); 
		response.setHeader("Pragrma","no-cache"); 
		response.setDateHeader("Expires",0);
		response.setContentType("text/html;charset=GBK");
		try {
			response.getWriter().write(JSONOperator.jsonTree(userfunctions,"id","displayname","icon",true,1));
		} catch (IOException e) {
		}
	}

	@Action(value = "ClearFlag")
	public String doClear()
	{
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		session.setAttribute("flag", null);
		return "";
	}

	@Action(value = "Main")
	public String doMain()
	{
		return "doMain";
	}
}