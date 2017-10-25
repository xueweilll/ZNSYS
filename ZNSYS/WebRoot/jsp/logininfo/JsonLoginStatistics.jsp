<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jfsl.pojo.LoginInfo"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%

List loginstatistics=(List)request.getAttribute("loginstatistics");
int index=(Integer)request.getAttribute("page");
int rows=(Integer)request.getAttribute("rows");
JSONObject json=new JSONObject();
JSONObject js;
JSONArray ja=new JSONArray();
Object[] li;
json.put("total",String.valueOf(loginstatistics.size()));
if ((index==0)||(rows==0))
{
	index=1;
	rows=loginstatistics.size();
}
for(int i=(index-1)*rows;(i<index*rows)&&(i<loginstatistics.size());i++)
	{
		if ((i<loginstatistics.size())&&(loginstatistics.get(i)!=null))
		{
			li=(Object[])loginstatistics.get(i);
			js=new JSONObject();
			js.put("username",li[0]);
			js.put("name",li[1]);
			js.put("departmentname",li[2]);
			js.put("logincount",String.valueOf(li[3]));
			js.put("timelength",String.valueOf(((Integer)li[4]).intValue()/60)+"小时"+String.valueOf(((Integer)li[4]).intValue()%60)+"分钟");
			ja.add(js);
		}
	}
json.put("rows",ja);
out.print(json.toString());
%>