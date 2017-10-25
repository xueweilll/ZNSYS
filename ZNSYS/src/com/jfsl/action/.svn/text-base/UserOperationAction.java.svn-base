package com.jfsl.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.DepartmentDAO;
import com.jfsl.dao.FunctionDAO;
import com.jfsl.dao.UserDAO;
import com.jfsl.dao.UserOperationDAO;
import com.jfsl.pojo.Function;
import com.jfsl.pojo.User;
import com.jfsl.pojo.UserOperation;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doList", location = "jsp/useroperation/ListUserOperation.jsp"),
		@Result(name = "doJson", location = "jsp/useroperation/JsonUserOperation.jsp") })
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class UserOperationAction extends ActionSupport
{
	private UserOperationDAO uodao = new UserOperationDAO();
	private UserDAO userdao = new UserDAO();
	private FunctionDAO functiondao = new FunctionDAO();
	private DepartmentDAO departmentdao = new DepartmentDAO();
	private List<UserOperation> useroperations;
	private List<String> departmentnames;
	private String datascopes;
	private String username;
	private String functionid;
	private User user;
	private UserOperation useroperation;
	private int id;

	public List<String> getDepartmentnames()
	{
		return departmentnames;
	}

	public UserOperation getUseroperation()
	{
		return useroperation;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public User getUser()
	{
		return user;
	}

	public String getDatascopes()
	{
		return datascopes;
	}

	public void setDatascopes(String datascopes)
	{
		this.datascopes = datascopes;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getFunctionid()
	{
		return functionid;
	}

	public void setFunctionid(String functionid)
	{
		this.functionid = functionid;
	}

	public List<UserOperation> getUseroperations()
	{
		return useroperations;
	}

	@Action(value = "ListUserOperation")
	public String doList()
	{
		return "doList";
	}

	@Action(value = "JsonUserOperation")
	public String doJson()
	{
		uodao.clearCurrentSession();
		user = userdao.Find(username);
		useroperations = uodao.getOperations(username);
		departmentnames = new ArrayList<String>();
		for (UserOperation uo : useroperations)
		{
			departmentnames.add(departmentdao.getDepartmentNames(uo
					.getDatascopes()));
		}
		return "doJson";
	}

	@Action(value = "InsertUserOperation")
	public String doInsert()
	{
		try {
			String[] sa = functionid.split(",");
			UserOperation uo = null;
			Function f = null;
			if (sa.length > 0)
			{
				for (int i = 0; i < sa.length; i++)
				{
					if (!sa.equals(""))
					{
						f = functiondao.Find(sa[i]);
						uo = new UserOperation();
						uo.setUsername(username);
						uo.setFunction(f);
						uo.setOperations(63);
						uo.setDatascopes(datascopes);
						userdao.Add(uo);
					}
				}
			}
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "DeleteUserOperation")
	public String doDelete()
	{
		try {
			uodao.deleteOperation(id);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateUserOperation")
	public String doUpdate()
	{
		try {
			UserOperation uo = uodao.findOperation(id);
			uo.setDatascopes(datascopes);
			uodao.Update(uo);
			return "update";
		} catch (Exception e) {
			return "error";
		}
	}
}
