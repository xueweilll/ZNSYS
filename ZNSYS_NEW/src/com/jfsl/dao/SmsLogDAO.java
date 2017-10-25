package com.jfsl.dao;


import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.SmsLog;

public class SmsLogDAO extends DAOImp
{
	
	@SuppressWarnings("unchecked")
	public List<SmsLog> listAll(int smsid)
	{
		StringBuffer hql = new StringBuffer();
		hql.append("from SmsLog as c where c.sms.id=:smsid order by c.id");
		Query query = this.getOracleCurrentSession().createQuery(hql.toString());
		query.setInteger("smsid",smsid);
		List<SmsLog> ls = (List<SmsLog>) query.list();
		return ls;
	}
	
	@SuppressWarnings("unchecked")
	public SmsLog find(int id){
		Query query = this.getOracleCurrentSession().createQuery("from SmsLog as c where c.id");
		query.setInteger("id",id);
		List<SmsLog> ls = (List<SmsLog>) query.list();
		return ls.get(0);
	}
	
	public void delete(int smsid)
	{
		Session session = this.getOracleCurrentSession();
		Transaction tx = null;
		String sqlstr="delete from t_r_smslog where f_smsid="+smsid;
		Query query = session.createSQLQuery(sqlstr);
		tx = session.beginTransaction();
		query.executeUpdate();
		tx.commit();
	}
	
	public void insertByAddList(int smsid,String addlistids)
	{
		Session session = this.getOracleCurrentSession();
		Transaction tx = null;
		String sqlstr="insert into t_r_smslog(f_smsid,f_tel,f_dscid) " +
				"select distinct "+smsid+" as f_smsid,f_tel,f_id "+
				"from t_b_addlist " +
				"where t_b_addlist.f_id in ("+addlistids+") " +
				"and t_b_addlist.f_id not in " +
				"(select f_dscid from t_r_smslog " +
				"where f_smsid="+smsid+")";
		Query query = session.createSQLQuery(sqlstr);
		tx = session.beginTransaction();
		query.executeUpdate();
		tx.commit();
	}
	
	public void insertByTels(int smsid,String tels)
	{
		String telArray[]=tels.split(",");
		Session session = this.getOracleCurrentSession();
		Transaction tx = null;
		String sqlstr="insert into t_r_smslog(f_smsid,f_tel) values(:smsid,:tel)";
		Query query = session.createSQLQuery(sqlstr);
		tx = session.beginTransaction();
		for(String tel:telArray)
		{
			query.setInteger("smsid",smsid);
			query.setString("tel",tel);
			query.executeUpdate();
		}
		tx.commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<SmsLog> Query(String smstype,String content,String tel,String begintime,String endtime)throws Exception
	{
		StringBuffer hql = new StringBuffer();
		hql.append("from SmsLog as c where 1=1 ");
		if((smstype!=null)&&(!smstype.equals(""))) 
			hql.append("and c.sms.smstype='"+smstype+"'");
		if((content!=null)&&(!content.equals(""))) 
			hql.append("and c.sms.content like '%"+content+"%'");
		if((tel!=null)&&(!tel.equals(""))) 
			hql.append("and c.tel like '%"+tel+"%'");
		if ((begintime != null) && (!begintime.equals("")))
			hql.append("and c.sms.sendreqtime>= to_date('"+begintime+" 00:00:00','yyyy-MM-dd HH24-mi-ss')");
		if ((endtime != null) && (!endtime.equals("")))
			hql.append("and c.sms.sendreqtime<= to_date('"+endtime+" 23:59:59','yyyy-MM-dd HH24-mi-ss')");
		hql.append("order by c.sms.sendreqtime desc");
		Query query = this.getOracleCurrentSession().createQuery(hql.toString());
		List<SmsLog> ls = query.list();
		return ls;
	}
}
