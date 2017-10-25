<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.jfsl.pojo.Function"%>
<%@page import="com.jfsl.pojo.Role"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.jfsl.util.JSONOperator"%>
<% 
List<Function> functions=(List<Function>)request.getAttribute("allfunctions");
Role granted=(Role)request.getAttribute("granted");
List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
JSONObject jo;
for(Function f:functions)
{
	jo=new JSONObject();
	jo.put("id",f.getId());
	jo.put("text",f.getDisplayname());
	for(Function e:granted.getFunctions())
	{
		if (f.getId().equals(e.getId()))
		{
			jo.put("checked",true);
			break;
		}
	}
	jsonobjects.add(jo);
}
out.print(JSONOperator.createJsonTree(jsonobjects));
%>