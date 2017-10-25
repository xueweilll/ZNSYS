<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.jfsl.pojo.*"%>
<%@page import="com.jfsl.util.JSONOperator"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
List deps=(List)request.getAttribute("deps");
List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
JSONObject jo,joo;
Object[] ob;
for(int i=0;i<deps.size();i++)
{
	ob=(Object[])deps.get(i);
	jo=new JSONObject();
	jo.put("id",ob[0]);
	jo.put("text",ob[1]);
	joo=new JSONObject();
	joo.put("showorder",ob[2]);
	joo.put("belongin",ob[3]);
	jo.put("attributes",joo);
	jsonobjects.add(jo);
}
out.print(JSONOperator.createJsonTree(jsonobjects,1));
%>
