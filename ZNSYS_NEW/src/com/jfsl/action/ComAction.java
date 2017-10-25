package com.jfsl.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
//import org.apache.struts2.jasper.util.SystemLogHandler;
import org.omg.CORBA.Request;

import com.jfsl.dao.ComDAO;
import com.jfsl.pojo.*;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doList", location = "jsp/com/ListCom.jsp")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class ComAction extends BaseAction
{
	//基本参数
	private int id;
	private String com;
	private String baudrate;
	private String brand;
	
	//调用的pojo
	private Com c;
	private List<Com> cs;
	
	//调用的dao
	private ComDAO Comdao = new ComDAO();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public String getBaundrate() {
		return baudrate;
	}

	public void setBaundrate(String baundrate) {
		this.baudrate = baundrate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Com getC() {
		return c;
	}

	public void setC(Com c) {
		this.c = c;
	}

	public List<Com> getCs() {
		return cs;
	}

	public void setCs(List<Com> cs) {
		this.cs = cs;
	}

	public ComDAO getComdao() {
		return Comdao;
	}

	public void setComdao(ComDAO comdao) {
		Comdao = comdao;
	}
	
	//方法
	@Action(value = "JsonCom")
	public void doJson()
	{
		//System.out.println("JsonCom");
		Comdao.clearOracleCurrentSession();
		cs = Comdao.ListAll();
		
		this.jsonDataGrid(cs);//转化为json，并打印回前台
	}
	
	@Action(value = "ListCom")
	public String ListCom()
	{
		return "doList"; 
	}
	
	@Action(value = "InsertCom")
	public String doInsert(){
		try {
			c = new Com();		//新建一个com对象
			c.setCom(com);
			c.setBaudrate(baudrate);
			c.setBrand(brand);	//id没放，id是自增长的，
			
			Comdao.OracleAdd(c);
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateCom")
	public String doUpdate(){
		try {
			c = Comdao.Find(id);	//找出id的com
			c.setCom(com);
			c.setBaudrate(baudrate);
			c.setBrand(brand);	//id没放，id是自增长的，
			
			Comdao.OracleUpdate(c);
			return "update";
		} catch (Exception e) {
			return "error";
		}
	}
	
	@Action(value = "DeleteCom")
	public String doDelete()
	{
		try {
			c = Comdao.Find(id);	//找出id的com
			Comdao.OracleDelete(c);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}
}
