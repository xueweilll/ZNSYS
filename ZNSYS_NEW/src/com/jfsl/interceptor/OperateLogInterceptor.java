package com.jfsl.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.jfsl.dao.OperateLogDAO;
import com.jfsl.pojo.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class OperateLogInterceptor extends AbstractInterceptor
{
	private static final long serialVersionUID = 1L;
	private OperateLogDAO old=new OperateLogDAO();

	@Override
	public String intercept(ActionInvocation invocation) throws Exception
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		String username=((User)session.getAttribute("user")).getUsername();
		String ipaddress=request.getRemoteAddr();
		String actionname=invocation.getProxy().getActionName();
		old.saveLog(username,ipaddress,actionname);
		return invocation.invoke();
	}

}
