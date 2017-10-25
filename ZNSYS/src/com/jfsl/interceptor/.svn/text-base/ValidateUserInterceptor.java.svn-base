package com.jfsl.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.jfsl.util.*;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ValidateUserInterceptor extends AbstractInterceptor
{
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		if (!Validator.validateUser(request))
		{
			session.setAttribute("flag", "notlogin");
			return "login";
		}
		return invocation.invoke();
	}

}
