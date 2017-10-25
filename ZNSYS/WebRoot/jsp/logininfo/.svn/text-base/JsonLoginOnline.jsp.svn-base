<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jfsl.pojo.User"%>
<%@page import="com.jfsl.pojo.LoginInfo"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
List<LoginInfo> loginonline = (List<LoginInfo>)request.getAttribute("loginonline");
JSONObject json=new JSONObject();
JSONObject js;
JSONArray ja=new JSONArray();
json.put("total",String.valueOf(loginonline.size()));
for(LoginInfo li:loginonline)
{
	js=new JSONObject();
	js.put("username",li.getLogininfopk().getUser().getUsername());
	js.put("name",li.getLogininfopk().getUser().getName());
	js.put("departmentname",li.getLogininfopk().getUser().getDepartment().getName());
	js.put("ipaddress",li.getIpaddress());
	js.put("logintime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(li.getLogininfopk().getLogintime()));
	ja.add(js);
}
json.put("rows",ja);
out.print(json.toString());
%>