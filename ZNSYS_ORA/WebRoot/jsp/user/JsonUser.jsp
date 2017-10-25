<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="com.jfsl.pojo.User"%>
<%@page import="com.jfsl.pojo.Role"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
List<User> users = (List<User>)request.getAttribute("users");
JSONObject json=new JSONObject();
JSONObject js;
JSONArray ja=new JSONArray();
json.put("total",String.valueOf(users.size()));
for(User u:users)
{
	js=new JSONObject();
	js.put("username",u.getUsername());
	js.put("userpsw",u.getUserpsw());
	js.put("employeename",u.getName());
	js.put("departmentid",u.getDepartment().getId());
	js.put("departmentname",u.getDepartment().getName());
	String ids="";
	for(Role r:u.getRoles())
	{
		ids=ids+r.getId()+",";
	}
	if (ids.equals(""))
		js.put("roleids",ids);
	else
		js.put("roleids",ids.substring(0,ids.length()-1));
	

	ja.add(ja.size(),js);
	
}
json.put("rows",ja);
out.print(json.toString());
%>