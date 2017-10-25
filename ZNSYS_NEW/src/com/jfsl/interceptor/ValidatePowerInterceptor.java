package com.jfsl.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.jfsl.util.*;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ValidatePowerInterceptor extends AbstractInterceptor
{
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception
	{
		String action=invocation.getProxy().getActionName();
		HttpServletRequest request=ServletActionContext.getRequest();
		if (!Validator.validatePower(request,action))
		{
			return "nopower";
		}
		return invocation.invoke();
	}

}
