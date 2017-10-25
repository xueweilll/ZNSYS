package com.jfsl.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.*;
import com.jfsl.pojo.*;
import com.jfsl.util.MD5Encrypt;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doList", location = "jsp/user/ListUser.jsp"),
		@Result(name = "doJson", location = "jsp/user/JsonUser.jsp"),
		@Result(name = "dogetRole", location = "jsp/user/ListUserRole.jsp"),
		@Result(name = "doJsonUsername", location = "jsp/user/JsonUsername.jsp"),
		@Result(name = "doJsonExFunction", location = "jsp/user/JsonExFunction.jsp"),
		@Result(name = "errorpsw", location = "jsp/user/errorpsw.jsp"), 
		@Result(name = "successpsw", location = "jsp/user/successpsw.jsp")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class UserAction extends ActionSupport
{

	private String username;
	private String userpsw;
	private String newpsw;
	private String name;
	private String departmentid;
	private Department dep;

	private User user;
	private List<User> users;

	private User userrole;
	private List<Role> allroles;
	private String roleids;
	private List<Function> allfunctions;

	private UserDAO userdao = new UserDAO();
	private FunctionDAO functiondao = new FunctionDAO();
	private DepartmentDAO depdao = new DepartmentDAO();

	public User getUserrole()
	{
		return userrole;
	}

	public List<Role> getAllroles()
	{
		return allroles;
	}

	public void setRoleids(String roleids)
	{
		this.roleids = roleids;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUserpsw()
	{
		return userpsw;
	}

	public void setUserpsw(String userpsw)
	{
		this.userpsw = userpsw;
	}

	public void setNewpsw(String newpsw)
	{
		this.newpsw = newpsw;
	}

	public String getNewpsw()
	{
		return newpsw;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setDepartmentid(String departmentid)
	{
		this.departmentid = departmentid;
	}

	public String getDepartmentid()
	{
		return departmentid;
	}

	public User getUser()
	{
		return user;
	}

	public List<User> getUsers()
	{
		return users;
	}

	public List<Function> getAllfunctions()
	{
		return allfunctions;
	}

	@Action(value = "ListUser")
	public String doList()
	{

		return "doList";
	}

	@Action(value = "JsonUser")
	public String doJson()
	{

		userdao.clearCurrentSession();
		users = userdao.ListAll(departmentid);

		return "doJson";
	}

	@Action(value = "JsonUsername")
	public String doJsonUsername()
	{

		users = userdao.ListAll(departmentid);
		return "doJsonUsername";
	}

	@Action(value = "UpdateUser")
	public String doUpdate()
	{
		try {
			user = userdao.Find(username);
			if (name != null && !name.equals(""))
				user.setName(name);
			if ((userpsw != null) && (!userpsw.equals("")))
				user.setUserpsw(userpsw);
				//user.setUserpsw(new MD5Encrypt().getMD5ofStr(userpsw));
			if ((departmentid != null) && (!departmentid.equals("")))
			{
				dep = depdao.find(departmentid);
				user.setDepartment(dep);
			}

			userdao.Update(user);

			return "update";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "ChangePsw")
	public String doChangPsw()
	{
		try {
			user = userdao.Find(username);
			String oldpsw = userpsw;
			//String oldpsw = new MD5Encrypt().getMD5ofStr(userpsw);
			if (oldpsw.equals(user.getUserpsw()))
			{
				user.setUserpsw(newpsw);
				//user.setUserpsw(new MD5Encrypt().getMD5ofStr(newpsw));
				userdao.Update(user);
				return "successpsw";
			}
			else
			{
				return "errorpsw";
			}
		} catch (Exception e) {
			return "error";
		}
		
	}

	@Action(value = "InsertUser")
	public String doInsert()
	{
		try {
			user = new User();
			user.setUsername(username);		//前台直接映射，不需要获取
			//user.setUserpsw(new MD5Encrypt().getMD5ofStr(userpsw));
			user.setUserpsw(userpsw);
			user.setName(name);
			dep = depdao.find(departmentid);
			user.setDepartment(dep);		//以一个对象的方式传递内容
			
			userdao.Add(user);				//调用add方法加入数据库

			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "DeleteUser")
	public String doDelete()
	{
		try {
			new UserOperationDAO().delete(username);

			user = userdao.Find(username);
			userdao.Delete(user);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "getUserRole")
	public String doGetRoles()
	{

		userrole = userdao.ListRole(username);

		return "dogetRole";
	}

	@Action(value = "setUserRole")
	public String doSetRoles()
	{
		try {
			String ids[] = new String[10];
			ids = roleids.split(",");
			userdao.addRoletoUser(username, ids);

			// 根据相应角色赋予此用户功能权限
			UserOperationDAO t = new UserOperationDAO();
			t.delete(username);
			t.add(username);

			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "JsonExFunction")
	public String doJsonExFunction()
	{
		allfunctions = functiondao.ListExFunction(username);
		return "doJsonExFunction";
	}
}
