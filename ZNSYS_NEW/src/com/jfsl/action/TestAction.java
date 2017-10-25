package com.jfsl.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.jfsl.dao.OracleDAO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
		@Result(name = "doList", location = "jsp/jqplot/jqplot.jsp"),
		@Result(name = "doListEntering", location = "jsp/entering/ListEntering.jsp") })
public class TestAction extends ActionSupport {

	private String name;
	private String Text1;

	public String getText1() {
		return Text1;
	}

	public void setText1(String text1) {
		Text1 = text1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	HttpServletRequest req = ServletActionContext.getRequest();
	HttpServletResponse resp = ServletActionContext.getResponse();

	@Action(value = "ListProcessStatistics")
	public String doList() {
		return "doList";
	}

	@Action(value = "ListEntering")
	public String doListEntering() {
		return "doListEntering";
	}

	@Action(value = "JsonProcedure")
	public void JsonProcedure() {
		String json = "{'total':1,'rows':[{'p':'2013-05-09','a':'a','w':'w'}]}";
		// printJson(json);

	}


	@Action(value = "TestHttp1")
	public void TestHttp1() throws IOException {
		DataInputStream in = new DataInputStream(req.getInputStream());
		System.out.print(in.available());
		int len = 0;
		byte[] buffer = new byte[1024];
		String value = "";
		while ((len = in.read(buffer)) > 0) {
			value = new String(buffer, 0, len);
		}
		System.out.println(value);
		try {
			System.out.println("success!");
			resp.getWriter().write("success!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}