<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jfsl.pojo.OperateLog"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
List<OperateLog> operatelog = (List<OperateLog>)request.getAttribute("operatelog");
int index=(Integer)request.getAttribute("page");
int rows=(Integer)request.getAttribute("rows");
JSONObject json=new JSONObject();
JSONObject js;
JSONArray ja=new JSONArray();
OperateLog li;
json.put("total",String.valueOf(operatelog.size()));
if ((index==0)||(rows==0))
{
	index=1;
	rows=operatelog.size();
}
for(int i=(index-1)*rows;(i<index*rows)&&(i<operatelog.size());i++)
	{
		if ((i<operatelog.size())&&(operatelog.get(i)!=null))
		{
			li=operatelog.get(i);
			js=new JSONObject();
			js.put("username",li.getOperatelogpk().getUser().getUsername());
			js.put("name",li.getOperatelogpk().getUser().getName());
			js.put("departmentname",li.getOperatelogpk().getUser().getDepartment().getName());
			js.put("ipaddress",li.getIpaddress());
			js.put("operatetime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(li.getOperatelogpk().getOperatetime()));
			js.put("operateobject",li.getAction().getDisplayname());
			ja.add(js);
		}
	}
json.put("rows",ja);
out.print(json.toString());
%>