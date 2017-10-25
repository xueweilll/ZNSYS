package com.jfsl.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.ControlDAO;
import com.jfsl.pojo.*;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doJson", location = "jsp/control/JsonControl.jsp"),
		@Result(name = "doList", location = "jsp/control/ListControl.jsp"),
		@Result(name = "doListManage", location = "jsp/control/ListControlManage.jsp")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class ControlAction extends BaseAction
{

	private int id;
	private String number;
	private String name;
	private String address;
	private String ip;
	private String memo;
	
	private Control control;
	private List<Control> controls;

	private ControlDAO controldao = new ControlDAO();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public List<Control> getControls() {
		return controls;
	}

	public void setControls(List<Control> controls) {
		this.controls = controls;
	}

	public ControlDAO getControldao() {
		return controldao;
	}

	public void setControldao(ControlDAO controldao) {
		this.controldao = controldao;
	}

	@Action(value = "JsonControl")
	public String doJson()
	{
		//System.out.println("JsonControl");
		controldao.clearCurrentSession();
		controls = controldao.ListAll();
		
		return "doJson";
	}
	
	@Action(value = "ListControl")
	public String ListControl()
	{
		return "doList"; 
	}
	
	@Action(value = "ListControlManage")
	public String ListControlManage()
	{
		return "doListManage"; 
	}
	
	@Action(value = "InsertControl")
	public String doInsert(){
		try {
			control = new Control();
			control.setNumber(number);
			control.setName(name);
			control.setAddress(address);
			control.setIp(ip);
			control.setMemo(memo);
			controldao.Add(control);
			
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateControl")
	public String doUpdate(){
		try {
			control = controldao.Find(id).get(0);
			control.setNumber(number);
			control.setName(name);
			control.setAddress(address);
			control.setIp(ip);
			control.setMemo(memo);
			controldao.Add(control);
			controldao.Update(control);
			return "update";
		} catch (Exception e) {
			return "error";
		}
	}
	
	@Action(value = "DeleteControl")
	public String doDelete()
	{
		try {
			//System.out.println("title:"+title);
			controldao.DeleteControl(id);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}
}
