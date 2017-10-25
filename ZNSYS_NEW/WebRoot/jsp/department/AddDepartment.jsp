<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="com.jfsl.pojo.User"%>
<%@page import="net.sf.json.JSONObject"%>
<% 
String id=(String)request.getAttribute("id");
JSONObject json=new JSONObject();
json.put("id",id);
out.print(json.toString());
%>
