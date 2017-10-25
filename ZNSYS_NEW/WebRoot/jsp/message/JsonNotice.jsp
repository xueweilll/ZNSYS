<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="com.jfsl.pojo.Message"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
List<Message> messages = (List<Message>)request.getAttribute("messages");
JSONObject jo;
JSONArray ja=new JSONArray();
jo=new JSONObject();

for(Message m:messages)
{
	jo=new JSONObject();
	jo.put("messageid",m.getMessageid());
	jo.put("title",m.getTitle());
	jo.put("author",m.getAuthor());
	jo.put("content",m.getContent());
	jo.put("publishtime",m.getPublishtime().replace("00:00:00",""));
	jo.put("dateline",m.getDateline().replace("00:00:00",""));
	ja.add(jo);
}
//System.out.println(ja.toString());
out.print(ja.toString());
%>