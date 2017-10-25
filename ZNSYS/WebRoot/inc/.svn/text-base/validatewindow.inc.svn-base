<%@ page pageEncoding="UTF-8"%>
<%
	if(request.getHeader("Referer")==null)	//判断推荐人，确定来源
	{		//提示不支持单独访问
		getServletContext().getRequestDispatcher("/common/nowindow.jsp").forward(request,response);
		return;
	}
%>