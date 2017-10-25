package com.jfsl.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.DepartmentDAO;
import com.jfsl.dao.UserOperationDAO;
import com.jfsl.pojo.*;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doList", location = "jsp/department/ListDepartment.jsp"),
		@Result(name = "doJson", location = "jsp/department/JsonDepartment.jsp"),
		@Result(name = "doAdd", location = "jsp/department/AddDepartment.jsp"),
		@Result(name = "doJsonEx", location = "jsp/department/JsonExDepartment.jsp"),
		@Result(name = "doJsonScopes", location = "jsp/department/JsonDataScopes.jsp"),
		@Result(name="doJsonName",location="jsp/department/JsonDepartmentName.jsp"),
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class DepartmentAction extends BaseAction
{

	private String id;
	private String name;
	private String parentid;
	private int showorder;
	private int belongin;
	private int level;
	private Department dep;
	private List<Department> departments;
	
	@SuppressWarnings("unchecked")
	private List deps;
	private String seldeps;
	private String functionname;

	private DepartmentDAO depdao = new DepartmentDAO();

	public String getSeldeps()
	{
		return seldeps;
	}

	public void setSeldeps(String seldeps)
	{
		this.seldeps = seldeps;
	}

	public String getFunctionname()
	{
		return functionname;
	}

	public void setFunctionname(String functionname)
	{
		this.functionname = functionname;
	}

	public Department getDep()
	{
		return dep;
	}

	@SuppressWarnings("unchecked")
	public List getDeps()
	{
		return deps;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setParentid(String parentid)
	{
		this.parentid = parentid;
	}

	public String getParentid()
	{
		return parentid;
	}

	public int getShoworder()
	{
		return showorder;
	}

	public void setShoworder(int showorder)
	{
		this.showorder = showorder;
	}
	

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	@Action(value = "ListDepartment")
	public String doList()
	{
		return "doList";
	}

	@Action(value = "JsonDepartment")
	public String doJson()
	{
		depdao.clearCurrentSession();
		deps = depdao.ListAll(0);
		return "doJson";
	}

	@Action(value = "AddDepartment", interceptorRefs = @InterceptorRef("validatePowerInterceptor"))
	public String doAdd()
	{
		if (parentid == null)
			parentid = "";
		id = depdao.GenerateID(parentid);

		return "doAdd";
	}

	@Action(value = "InsertDepartment")
	public String doInsert()
	{
		try {
			if (depdao.isValid(id, showorder))
			{
				dep = new Department();
				dep.setId(id);
				dep.setName(name);
				dep.setShoworder(showorder);
				dep.setBelongin(belongin);
				depdao.Add(dep);
				return "add";
			}
			else
				return "replicate";	
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateDepartment")
	public String doUpdate()
	{
		try {
			if (depdao.isValid(id, showorder))
			{
				dep = depdao.find(id);
				dep.setName(name);
				dep.setShoworder(showorder);
				dep.setBelongin(belongin);
				depdao.Update(dep);
				return "update";
			}
			else
				return "replicate";	
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "DeleteDepartment", interceptorRefs = @InterceptorRef("validatePowerInterceptor"))
	public String doDelete()
	{
		try {
			depdao.delete(id);
			return "delete";	
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "JsonExDepartment")
	public String doJsonEx()
	{
		deps = depdao.ListAll(0);
		return "doJsonEx";
	}

	@Action(value = "JsonScopesDepartment")
	public String doJsonScopes()
	{
		UserOperationDAO ud = new UserOperationDAO();
		String[] datascopes = ud.findScopes(getPresentFunctions(),getPresentUser(),functionname);
		deps = depdao.ListScopesDepartment(datascopes,belongin);
		return "doJsonScopes";
	}
	
	@Action(value="JsonDepartmentName")
	public String doJsonName()
	{
		if (level==1) 
			departments=depdao.ListAllStation(getPresentUser().getDepartment().getId());
		else
			departments=depdao.ListAllSegment(getPresentUser().getDepartment().getId());
		return "doJsonName";
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setBelongin(int belongin) {
		this.belongin = belongin;
	}

	public int getBelongin() {
		return belongin;
	}

}
