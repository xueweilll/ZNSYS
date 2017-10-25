<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
List statistics=(List)request.getAttribute("statistics");
int index=(Integer)request.getAttribute("page");
int rows=(Integer)request.getAttribute("rows");
JSONObject json=new JSONObject();
JSONObject js;
JSONArray ja=new JSONArray();
Object[] li;
json.put("total",String.valueOf(statistics.size()));
if ((index==0)||(rows==0))
{
	index=1;
	rows=statistics.size();
}
for(int i=(index-1)*rows;(i<index*rows)&&(i<statistics.size());i++)
	{
		if ((i<statistics.size())&&(statistics.get(i)!=null))
		{
			li=(Object[])statistics.get(i);
			js=new JSONObject();
			js.put("username",li[0]);
			js.put("name",li[1]);
			js.put("departmentname",li[2]);
			js.put("operatecount",String.valueOf(li[3]));
			ja.add(js);
		}
	}
json.put("rows",ja);
out.print(json.toString());
%>