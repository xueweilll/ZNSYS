<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="com.jfsl.pojo.User"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<% 
List<User> users = (List<User>)request.getAttribute("users");
JSONObject jo;
JSONArray ja=new JSONArray();
jo=new JSONObject();
for(User u:users)
{
	jo=new JSONObject();
	jo.put("id",u.getUsername());
	jo.put("username",u.getName());
	ja.add(jo);
}
out.print(ja.toString());
%>
