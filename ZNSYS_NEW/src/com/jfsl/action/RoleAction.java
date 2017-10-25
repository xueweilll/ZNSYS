package com.jfsl.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.FunctionDAO;
import com.jfsl.dao.RoleDAO;
import com.jfsl.pojo.Function;
import com.jfsl.pojo.Role;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doList", location = "jsp/role/ListRole.jsp"),
		@Result(name = "doJson", location = "jsp/role/JsonRole.jsp"),
		@Result(name = "doJsonGrantedFunction", location = "jsp/role/JsonGrantedFunction.jsp"), })
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class RoleAction extends ActionSupport
{
	private int roleid;
	private String rolename;
	private String description;
	private List<Role> roles;
	private List<Function> allfunctions;
	private Role granted;
	private Role role;
	private String functionids;

	private RoleDAO roledao = new RoleDAO();
	private FunctionDAO functiondao = new FunctionDAO();

	public int getRoleid()
	{
		return roleid;
	}

	public void setRoleid(int roleid)
	{
		this.roleid = roleid;
	}

	public String getRolename()
	{
		return rolename;
	}

	public void setRolename(String rolename)
	{
		this.rolename = rolename;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<Role> getRoles()
	{
		return roles;
	}

	public Role getRole()
	{
		return role;
	}

	public List<Function> getAllfunctions()
	{
		return allfunctions;
	}

	public Role getGranted()
	{
		return granted;
	}

	public void setFunctionids(String functionids)
	{
		this.functionids = functionids;
	}

	@Action(value = "ListRole")
	public String doList()
	{
		return "doList";
	}

	@Action(value = "JsonRole")
	public String doJson()
	{
		roledao.clearOracleCurrentSession();
		roles = roledao.ListAll();
		return "doJson";
	}

	@Action(value = "InsertRole")
	public String doInsert()
	{
		if (rolename != null)
		{
			Role role = new Role();
			role.setName(rolename);
			role.setDescription(description);
			roledao.OracleAdd(role);
		}
		return "";
	}

	@Action(value = "UpdateRole")
	public String doUpdate()
	{
		if (roleid > 0)
		{
			Role r = roledao.Find(roleid);
			r.setName(rolename);
			r.setDescription(description);
			roledao.OracleUpdate(r);
		}
		return "";
	}

	@Action(value = "DeleteRole")
	public String doDelete()
	{
		if (roleid > 0)
		{
			Role r = roledao.Find(roleid);
			roledao.OracleDelete(r);
		}
		return "";
	}

	@Action(value = "JsonGrantedFunction")
	public String doJsonGrantedFunction()
	{
		allfunctions = functiondao.ListAll();
		granted = roledao.ListGranted(roleid);
		return "doJsonGrantedFunction";
	}

	@Action(value = "GrantFunction")
	public String goGrant()
	{
		String ids[] = functionids.split(",");
		roledao.Grant(roleid, ids);
		return "";
	}
}
