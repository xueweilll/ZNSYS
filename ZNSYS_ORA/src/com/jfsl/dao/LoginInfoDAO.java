package com.jfsl.dao;

import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;

import com.jfsl.pojo.Flow;
import com.jfsl.pojo.LoginInfo;

public class LoginInfoDAO extends DAOImp
{
	//获取所有在线用户信息
	@SuppressWarnings("unchecked")
	public List<LoginInfo> getLoginOnline()
	{
		String hqlstr="from SYS_LOGININFO as li where li.logouttime is null order by li.logininfopk.logintime desc";
		List ls=getCurrentSession().createQuery(hqlstr).list();
		return ls;
	}
	
	@SuppressWarnings("unchecked")
	public List<LoginInfo> getLoginHistory(String departmentid,String username,String begintime,String endtime)
	{
		StringBuffer hqlstr=new StringBuffer();
		hqlstr.append("from SYS_LOGININFO as li where li.logouttime is not null");
		if ((departmentid!=null)&&(!departmentid.equals("")))
			hqlstr.append(" and li.logininfopk.user.department.id like '"+departmentid+"%'");
		if ((username!=null)&&(!username.equals("")))
			hqlstr.append(" and li.logininfopk.user.username='"+username+"'");
		if ((begintime!=null)&&(!begintime.equals("")))
			hqlstr.append(" and li.logininfopk.logintime<='"+begintime+"'");
		if ((endtime!=null)&&(!endtime.equals("")))
			hqlstr.append(" and li.logininfopk.logintime>='"+endtime+"'");
		hqlstr.append(" order by li.logininfopk.logintime desc");
		Query query=getCurrentSession().createQuery(hqlstr.toString());
		List<LoginInfo> ls=query.list();
		return ls;
	}
	
	@SuppressWarnings("unchecked")
	public List StatisticByUser(String departmentid,String begintime,String endtime)
	{
		StringBuffer hqlstr=new StringBuffer();
		hqlstr.append("select li.username,u.name,d.name as departmentname");
		hqlstr.append(",count(*) as logincount");
		hqlstr.append(",sum(datediff(minute,li.logintime,li.logouttime)) as timelength");
		hqlstr.append(" from SYS_LOGININFO li inner join T_SA_Users u on li.username=u.username");
		hqlstr.append(" inner join SYS_DEPARTMENTS d on u.Department_ID=d.ID");
		hqlstr.append(" where li.logouttime is not null");
		if ((departmentid!=null)&&(!departmentid.equals("")))
			hqlstr.append(" and d.ID like '"+departmentid+"%'");
		if ((begintime!=null)&&(!begintime.equals("")))
			hqlstr.append(" and li.logintime<='"+begintime+"'");
		if ((endtime!=null)&&(!endtime.equals("")))
			hqlstr.append(" and li.logintime>='"+endtime+"'");
		hqlstr.append(" group by li.username,u.name,d.name");
		hqlstr.append(" order by li.username");
		List ls=getCurrentSession().createSQLQuery(hqlstr.toString()).list();
		return ls;
	}
	
	@SuppressWarnings("unchecked")
	public void Logout(String username)
	{
		String sqlstr="from SYS_LOGININFO as li where li.logininfopk.user.username=:username and li.logouttime is null";
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
	}
}
