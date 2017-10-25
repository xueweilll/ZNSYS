<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jfsl.pojo.LoginInfo"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%
List<LoginInfo> loginhistory = (List<LoginInfo>)request.getAttribute("loginhistory");
int index=(Integer)request.getAttribute("page");
int rows=(Integer)request.getAttribute("rows");
JSONObject json=new JSONObject();
JSONObject js;
JSONArray ja=new JSONArray();
json.put("total",String.valueOf(loginhistory.size()));
if ((index==0)||(rows==0))
{
	index=1;
	rows=loginhistory.size();
}
for(int i=(index-1)*rows;(i<index*rows)&&(i<loginhistory.size());i++)
	{
		if ((i<loginhistory.size())&&(loginhistory.get(i)!=null))
		{
			LoginInfo li=loginhistory.get(i);
			js=new JSONObject();
			js.put("username",li.getLogininfopk().getUser().getUsername());
			js.put("name",li.getLogininfopk().getUser().getName());
			js.put("departmentname",li.getLogininfopk().getUser().getDepartment().getName());
			js.put("ipaddress",li.getIpaddress());
			js.put("logintime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(li.getLogininfopk().getLogintime()));
			js.put("logouttime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(li.getLogouttime()));
			js.put("timelength",String.valueOf((li.getLogouttime().getTime()-li.getLogininfopk().getLogintime().getTime())/1000/60));
			ja.add(js);
		}
	}
json.put("rows",ja);
out.print(json.toString());
%>