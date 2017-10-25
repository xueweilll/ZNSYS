<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@page import="java.util.List"%>
<%@page import="com.jfsl.pojo.UserOperation"%>
<%@page import="com.jfsl.pojo.User"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<% 
List<UserOperation> userperations=(List<UserOperation>)request.getAttribute("useroperations");
User user=(User)request.getAttribute("user");
List<String> departmentnames=(List<String>)request.getAttribute("departmentnames");
JSONObject json=new JSONObject();
JSONObject js;
JSONArray ja=new JSONArray();
if (userperations==null)
{
	json.put("total","0");
}
else
{
	json.put("total",String.valueOf(userperations.size()));
	int i=0;
	for(UserOperation r:userperations)
	{
		js=new JSONObject();
		js.put("id",String.valueOf(r.getId()));
		js.put("username",r.getUsername());
		js.put("displayname",r.getFunction().getDisplayname());
		if (r.getDatascopes()==null)
		{
			js.put("datascopes","");
			js.put("datascopenames","");
		}
		else 
		{
			js.put("datascopes",r.getDatascopes());
			js.put("datascopenames",departmentnames.get(i));
		}
		js.put("depid",user.getDepartment().getId());
		ja.add(js);
		i++;
	}
}
json.put("rows",ja);
out.print(json.toString());
%>
