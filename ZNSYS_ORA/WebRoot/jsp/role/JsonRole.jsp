<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="com.jfsl.pojo.Role"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<% 
List<Role> roles=(List<Role>)request.getAttribute("roles");
JSONObject json=new JSONObject();
JSONObject js;
JSONArray ja=new JSONArray();
int i=0;
json.put("total",String.valueOf(roles.size()));
for(Role r:roles)
{
	js=new JSONObject();
	js.put("roleid",String.valueOf(r.getId()));
	js.put("rolename",r.getName());
	js.put("description",r.getDescription());
	ja.add(i,js);
	i=i+1;
}
json.put("rows",ja);
out.print(json.toString());
%>
