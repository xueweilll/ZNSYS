package com.jfsl.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.jfsl.dao.DictionaryDAO;
import com.jfsl.pojo.User;
import com.jfsl.pojo.UserOperation;
import com.jfsl.util.ExcelOperator;
import com.jfsl.util.JSONOperator;

@SuppressWarnings({ "serial", "unchecked" })
@ParentPackage("default")
@Results({
	@Result(name = "doExport", type = "streamx", params = { "contentType",
			"application/octet-stream;charset=ISO8859-1", "inputName",
			"fileStream", "bufferSize", "4096", "contentDisposition",
			"attachment;filename=${title}" })
})
@InterceptorRefs({ 
	@InterceptorRef("defaultStack"),
	@InterceptorRef("validateUserInterceptor"),
	@InterceptorRef("operateLogInterceptor")
})
public class BaseAction extends ActionSupport {
	
	private int page,rows;
	private String tablename;
	private String fieldname;

	private String title,url,fields,titles;
	
	public String getTablename() {
		return tablename;
	}
	
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	
	public String getFieldname() {
		return fieldname;
	}
	
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getTitle() {
		return ExcelOperator.EncodingFilename(title)+".xls";
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	private InputStream fileStream;
	
	public InputStream getFileStream()
	{
		return fileStream;
	}
	
	//获取当前登陆系统的用户
	public User getPresentUser()
	{
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession session = req.getSession();
		return (User)session.getAttribute("user");
	}
	
	public List<UserOperation> getPresentFunctions()
	{
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpSession se = req.getSession();
		return (List<UserOperation>) se.getAttribute("functions");
	}
	
	public String getUsername() {
		return getPresentUser().getUsername();
	}

	public String getRealname() {
		return getPresentUser().getName();
	}

	public String getUserdepartmentid() {
		return getPresentUser().getDepartment().getId();
	}

	public String getUserdepartmentname() {
		return getPresentUser().getDepartment().getName();
	}

	//导出excel文件
	//2012-7-10房道伟
	@Action(value = "ExportExcel")
	public String doExport() throws IOException
	{
		try
		{
			if (url.equals("")) return "errorexport";
			else
			{
				HttpServletRequest req = ServletActionContext.getRequest();
				int l=url.indexOf("?");
				if (l>-1)
					url=req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+
				        req.getContextPath()+"/"+url.substring(0,l)+";jsessionid="+req.getSession().getId()+url.substring(l);
				else
					url=req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+
				        req.getContextPath()+"/"+url+";jsessionid="+req.getSession().getId();
				
				JSONObject js=JSONOperator.readJsonFromUrl(url);
				if (js==null) return "errorexport";
				else
				{
					String[] atitles=titles.split(",");
					String[] afields=fields.split(",");	
					JSONArray ja=js.getJSONArray("rows");
					if (ja==null) return "errorexport";
					else
					{
						if (ja.size()==0) return "emptyexport";
						else
						{
							fileStream=ExcelOperator.ExportExcel(title, atitles, afields, ja);
							
							if (fileStream==null) return "errorexport";
							else return "doExport";
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			return "errorexport";
		}
	}

	public void jsonArray(List list)
	{
		printJson(JSONOperator.jsonArray(list));
	}
	
	public void jsonCombobox(List list)
	{
		printJson(JSONOperator.jsonCombobox(list));
	}
	
	public void jsonDataGrid(List list)
	{
		printJson(JSONOperator.jsonDataGrid(list, page, rows));
	}
	
	public void jsonObject(Object obj)
	{
		printJson(JSONOperator.jsonObject(obj));
	}
	
	public void jsonObject(Map map)
	{
		printJson(JSONOperator.jsonObject(map));
	}
	
	public void jsonTree(List list,String id,String text,boolean hasAttributes,int style)
	{
		printJson(JSONOperator.jsonTree(list,id,text,hasAttributes,style));
	}
	
	public void jsonTree(List list,int id,int text,boolean hasAttributes,int style)
	{
		printJson(JSONOperator.jsonTree(list,id,text,hasAttributes,style));
	}
	
	public void jsonTree(List list,String id,String text,String iconCls,boolean hasAttributes,int style)
	{
		printJson(JSONOperator.jsonTree(list,id,text,iconCls,hasAttributes,style));
	}
	
	public void jsonTree(List list,int id,int text,int iconCls,boolean hasAttributes,int style)
	{
		printJson(JSONOperator.jsonTree(list,id,text,iconCls,hasAttributes,style));
	}
	
	public void printString(String str)
	{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setHeader("Cache-Control","no-store"); 
		response.setHeader("Pragrma","no-cache"); 
		response.setDateHeader("Expires",0);
		response.setContentType("text/html;charset=GBK");
		try {
			response.getWriter().write(str);
			response.getWriter().close();
		} catch (IOException e) {
		}
	}
	
	public void printJson(String json)
	{
		printString(json);
	}
	
	//根据表名和字段名,得到数据字典中的信息
	@Action(value = "JsonDictCaption")
	public void doJsonDictCaption()
	{
		DictionaryDAO dd=new DictionaryDAO();
		dd.clearCurrentSession();
		this.jsonCombobox(dd.ListAll(tablename, fieldname));
	}
	
	public void jsonSingleTree(List list,String rootid,String roottext,int id,int text,boolean hasAttributes)
	{
		printJson(JSONOperator.jsonSingleTree(list,rootid,roottext,id,text,hasAttributes));
	}
	
	public void jsonSingleTree(List list,String rootid,String roottext,String id,String text,boolean hasAttributes)
	{
		printJson(JSONOperator.jsonSingleTree(list,rootid,roottext,id,text,hasAttributes));
	}
}