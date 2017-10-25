<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="com.jfsl.util.JSONOperator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.jfsl.pojo.Department"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>

<% 
List deps=(List)request.getAttribute("deps");
String seldeps=(String)request.getAttribute("seldeps");
List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
List<JSONObject> jsonresults;
JSONObject jo;
Object[] ob;
String id,name;
String[] seldepArray=null;
if (seldeps!=null)
{
	seldepArray=seldeps.split(",");
	Arrays.sort(seldepArray);
}
for(int i=0;i<deps.size();i++)
{
	ob=(Object[])deps.get(i);
	id=(String)ob[0];
	name=(String)ob[1];
	jo=new JSONObject();
	jo.put("id",id);
	jo.put("text",name);
	if ((seldeps!=null)&&(seldepArray!=null)&&(Arrays.binarySearch(seldepArray,id)>-1))
		jo.put("checked",true);
	jsonobjects.add(jo);
}
out.print(JSONOperator.createJsonTree(jsonobjects,1));
%>