package com.jfsl.dao;

import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;

import com.jfsl.pojo.LoginInfo;

public class LogoutDAO extends DAOImp
{
	@SuppressWarnings("unchecked")
	public void Logout(String username)
	{
		String sqlstr="from LoginInfo as li where li.logininfopk.user.username=:username and li.logouttime is null";
		Query query=getCurrentSession().createQuery(sqlstr);
		query.setString("username", username);
		List<LoginInfo> ls=query.list();
		if (ls.size()>0)
		{
			LoginInfo li=ls.get(0);
			if (li!=null)
			{
				li.setLogouttime(Calendar.getInstance().getTime());
				Update(li);
			}
		}
		closeCurrentSession();
	}
}
