<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="com.jfsl.pojo.Control"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
List<Control> controls = (List<Control>)request.getAttribute("controls");
JSONObject jo;
JSONArray ja=new JSONArray();
jo=new JSONObject();

for(Control c:controls)
{
	jo=new JSONObject();
	jo.put("id",c.getId());
	jo.put("number",c.getNumber());
	jo.put("name",c.getName());
	jo.put("address",c.getAddress());
	jo.put("ip",c.getIp());
	jo.put("memo",c.getMemo());
	ja.add(jo);
}
System.out.println(ja.toString());
out.print(ja.toString());
%>