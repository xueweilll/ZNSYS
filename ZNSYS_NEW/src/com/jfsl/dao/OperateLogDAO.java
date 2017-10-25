package com.jfsl.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;

import com.jfsl.pojo.Action;
import com.jfsl.pojo.OperateLog;
import com.jfsl.pojo.OperateLogPk;
import com.jfsl.pojo.User;

public class OperateLogDAO extends DAOImp
{
	public void saveLog(String username,String ipaddress,String actionname)
	{
		Action action=(Action)getOracleCurrentSession().get(Action.class, actionname);
		if (action==null) return;
		User user=(User)getOracleCurrentSession().load(User.class,username);
		OperateLogPk olpk=new OperateLogPk();
		olpk.setUser(user);
		olpk.setOperatetime(Calendar.getInstance().getTime());
		OperateLog ol=new OperateLog();
		ol.setOperatelogpk(olpk);
		ol.setIpaddress(ipaddress);
		ol.setAction(action);
		OracleAdd(ol);
	}
	
	@SuppressWarnings("unchecked")
	public List<OperateLog> listLog(String departmentid,String username,String begintime,String endtime) throws Exception
	{
		StringBuffer hqlstr=new StringBuffer();
		hqlstr.append("from OperateLog as ol where 1=1");
		if ((departmentid!=null)&&(!departmentid.equals("")))
			hqlstr.append(" and ol.operatelogpk.user.department.id like '"+departmentid+"%'");
		if ((username!=null)&&(!username.equals("")))
			hqlstr.append(" and ol.operatelogpk.user.username='"+username+"'");
		/*if ((begintime!=null)&&(!begintime.equals("")))
			hqlstr.append(" and ol.operatelogpk.operatetime>='"+begintime+"'");
		if ((endtime!=null)&&(!endtime.equals("")))
			hqlstr.append(" and ol.operatelogpk.operatetime<='"+endtime+"'");*/
		if ((begintime!=null)&&(!begintime.equals("")))
			hqlstr.append(" and ol.operatelogpk.operatetime>= to_date(:begintime ,'yyyy-MM-dd HH24-mi-ss')");
		if ((endtime!=null)&&(!endtime.equals("")))
			hqlstr.append(" and ol.operatelogpk.operatetime<= to_date(:endtime,'yyyy-MM-dd HH24-mi-ss')");
		hqlstr.append(" order by ol.operatelogpk.operatetime desc");
		Query query=getOracleCurrentSession().createQuery(hqlstr.toString());
		if(begintime!=null&&!begintime.isEmpty()){
			query.setString("begintime", begintime);
			
		}
		if(endtime!=null&&!endtime.isEmpty()){
			query.setString("endtime", endtime);
		}
		List<OperateLog> ls=query.list();
		return ls;
	}
	
	public List<?> StatisticByUser(String begintime,String endtime) throws Exception
	{
		StringBuffer hqlstr=new StringBuffer();
		hqlstr.append("select ol.operatelogpk.user.username,ol.operatelogpk.user.name");
		hqlstr.append(",ol.operatelogpk.user.department.name,count(*) as operatecount");
		hqlstr.append(" from OperateLog ol where 1=1");
		/*if ((begintime!=null)&&(!begintime.equals("")))
			hqlstr.append(" and ol.operatelogpk.operatetime>='"+begintime+"'");
		if ((endtime!=null)&&(!endtime.equals("")))
			hqlstr.append(" and ol.operatelogpk.operatetime<='"+endtime+"'");*/
		if ((begintime!=null)&&(!begintime.equals("")))
			hqlstr.append(" and ol.operatelogpk.operatetime>= to_date(:begintime,'yyyy-MM-dd HH24-mi-ss')");
		if ((endtime!=null)&&(!endtime.equals("")))
			hqlstr.append(" and ol.operatelogpk.operatetime<= to_date(:endtime,'yyyy-MM-dd HH24-mi-ss')");
		hqlstr.append(" group by ol.operatelogpk.user.username,ol.operatelogpk.user.name");
		hqlstr.append(",ol.operatelogpk.user.department.name");
		hqlstr.append(" order by ol.operatelogpk.user.username");
		Query query=getOracleCurrentSession().createQuery(hqlstr.toString());
		if(begintime!=null&&!begintime.isEmpty()){
			query.setString("begintime", begintime);
			
		}
		if(endtime!=null&&!endtime.isEmpty()){
			query.setString("endtime", endtime);
		}
		List<?> ls=query.list();
		return ls;
	}
}
