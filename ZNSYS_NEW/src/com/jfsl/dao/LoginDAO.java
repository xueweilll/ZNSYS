package com.jfsl.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;

import com.jfsl.pojo.Function;
import com.jfsl.pojo.LoginInfo;
import com.jfsl.pojo.LoginInfoPk;
import com.jfsl.pojo.Operation;
import com.jfsl.pojo.User;
import com.jfsl.pojo.UserOperation;

public class LoginDAO extends DAOImp
{
	//发生异常退出系统后再次登录需等待时长(分钟)，也可以通过系统管理员注销
	//正常退出后再次登录无需等待
	private static final int LoginTimeLength=30;
	//	检查用户的合法性
	public boolean checkUser(String username,String userpsw)
	{
		String sqlstr="from User as c where c.username=:username and c.userpsw=:userpsw";
		Query query=getOracleCurrentSession().createQuery(sqlstr);
		query.setString("username", username);
		//query.setString("userpsw", new MD5Encrypt().getMD5ofStr(userpsw));		暂时不加密，用来统筹办公oa，Sychel@15.2.5
		query.setString("userpsw",userpsw);
		List<?> ls=query.list();
		return ls.size()>0;
	}
	
	//记录登录信息
	public void Login(String username,String ipaddress)
	{
		User user=new UserDAO().Find(username);
		LoginInfoPk lipk=new LoginInfoPk();
		lipk.setUser(user);
		lipk.setLogintime(Calendar.getInstance().getTime());
		LoginInfo li=new LoginInfo();
		li.setLogininfopk(lipk);
		li.setIpaddress(ipaddress);
		OracleAdd(li);
	}
	
	//	获取用户所有能够访问的子模块
	@SuppressWarnings("unchecked")
	public List<Function> getModulesByName(String username)
	{
		String hqlstr="select f from Function as f where f.id in "+
		              "(select substring(f.id,1,3) from UserOperation as uo join uo.function as f "+
		              "where uo.username=:username)";
		Query query=this.getOracleCurrentSession().createQuery(hqlstr);
		query.setString("username", username);
		return query.list();		
	}
	
	//得到用户的操作权限
	@SuppressWarnings("unchecked")
	public List<String> getOperaions(String username)
	{
		String hqlstr="from Operation as op order by op.id";
		Query query=this.getOracleCurrentSession().createQuery(hqlstr);
		List<Operation> ls1=query.list();
		String[] operations=new String[ls1.size()];
		int i=0;
		for(Operation op:ls1)
		{
			operations[i]=op.getName();
			i++;
		}
		
		UserOperationDAO uod=new UserOperationDAO();
		List<UserOperation> ls2=uod.getOperations(username);
		List<String> ls_operation=new ArrayList<String>();
		int operation;
		String name;
		for(UserOperation uo:ls2)
		{
			operation=uo.getOperations();
			name=uo.getFunction().getName();
			i=0;
			while (operation>0)
			{
				if (operation%2>0) ls_operation.add(operations[i]+name);
				operation=operation/2;
				i=i+1;
			}
		}
		return ls_operation;
	}

	//检查该用户是否已登录
	@SuppressWarnings("unchecked")
	public boolean userEnable(String username)
	{
		String sqlstr="from LoginInfo as li where li.logininfopk.user.username=:username and li.logouttime is null";
		Query query=getOracleCurrentSession().createQuery(sqlstr);
		query.setString("username", username);
		query.setLockMode("li",LockMode.UPGRADE);
		List<LoginInfo> ls=query.list();
		if (ls.size()==0)
			return true;
		else
		{
			LoginInfo li=ls.get(0);
			Date logintime=li.getLogininfopk().getLogintime();
			if ((Calendar.getInstance().getTime().getTime()-logintime.getTime())/1000/60>LoginTimeLength)
			{
				li.setLogouttime(Calendar.getInstance().getTime());
				OracleUpdate(li);
				return true;
			}
			else
				return false;
		}
	}
}
