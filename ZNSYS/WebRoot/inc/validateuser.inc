<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.jfsl.util.Validator" %>
<%
	if(!Validator.validateUser(request))		//验证session中的用户名
	{
		session.setAttribute("flag", "notlogin");		//如果用户名不存在，标识flag
		response.sendRedirect(request.getContextPath()+"/login.jsp");//跳转回登陆界面
		return;
	}
%>