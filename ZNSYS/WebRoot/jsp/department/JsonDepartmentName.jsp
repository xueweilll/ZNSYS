<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="com.jfsl.pojo.Department"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<% 
List<Department> departments = (List<Department>)request.getAttribute("departments");
JSONObject jo;
JSONArray ja=new JSONArray();
for(Department d:departments)
{
	jo=new JSONObject();
	jo.put("id",d.getId());
	jo.put("name",d.getName());
	ja.add(jo);
}
out.print(ja.toString());
%>