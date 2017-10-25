<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.jfsl.pojo.Function"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.jfsl.util.JSONOperator"%>
<% 
List<Function> functions=(List<Function>)request.getAttribute("allfunctions");
List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
JSONObject jo;
for(Function f:functions)
{
	jo=new JSONObject();
	jo.put("id",f.getId());
	jo.put("text",f.getDisplayname());
	jsonobjects.add(jo);
}
out.print(JSONOperator.createJsonTree(jsonobjects,1));
%>