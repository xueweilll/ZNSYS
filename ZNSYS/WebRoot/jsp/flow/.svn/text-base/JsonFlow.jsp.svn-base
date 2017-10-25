<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="com.jfsl.pojo.Flow"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
List<Flow> flows = (List<Flow>)request.getAttribute("flows");
JSONObject jo;
JSONArray ja=new JSONArray();
jo=new JSONObject();

for(Flow f:flows)
{
	jo=new JSONObject();
	jo.put("id",f.getId());
	jo.put("title",f.getTitle());
	jo.put("linedata",f.getLinedata());
	jo.put("linecount",f.getLinecount());
	jo.put("nodedata",f.getNodedata());
	jo.put("nodecount",f.getNodecount());
	jo.put("areadata",f.getAreadata());
	jo.put("areacount",f.getAreacount());
	jo.put("author",f.getAuthor());
	jo.put("publishtime",f.getPublishtime());
	ja.add(jo);
}
System.out.println(ja.toString());
out.print(ja.toString());
%>