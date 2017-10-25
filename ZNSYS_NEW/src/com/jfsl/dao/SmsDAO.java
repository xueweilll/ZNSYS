package com.jfsl.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.Sms;

public class SmsDAO extends DAOImp
{

	@SuppressWarnings("unchecked")
	public List<Sms> listAll(String username)
	{
		StringBuffer hql = new StringBuffer();
		hql.append("from Sms as c where c.smstype=:smstype and c.user.username=:username order by c.id");
		Query query = this.getOracleCurrentSession().createQuery(hql.toString());
		query.setString("smstype","自定义短信");
		query.setString("username",username);
		List<Sms> ls = (List<Sms>) query.list();
		return ls;
	}

	public Sms find(int id)
	{
		Sms c = (Sms) this.getOracleCurrentSession().load(Sms.class, id);
		return c;
	}
	
	public void send(String ids){	//这里传递过来的ID可以为一个逗号隔开的多值，所以SQL用in
		Session session = this.getOracleCurrentSession();
		Transaction tx = null;
		String sqlstr="update t_r_sms set f_poststate='已提交',f_sendreqtime=getDate() where f_id in ("+ids+")";
		Query query = session.createSQLQuery(sqlstr);
		tx = session.beginTransaction();
		query.executeUpdate();
		tx.commit();
	}
	
	public void delete(String ids)
	{
		Session session = this.getOracleCurrentSession();
		Transaction tx = null;
		String sqlstr="delete from t_r_sms where f_id in ("+ids+")";
		Query query = session.createSQLQuery(sqlstr);
		tx = session.beginTransaction();
		query.executeUpdate();
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	public List<Sms> Query(String smscontent,String begintime,String endtime)
	{
		StringBuffer hql = new StringBuffer();
		hql.append("from Sms as cp ");
		hql.append("where 1=1 ");
		if ((smscontent != null) && (!smscontent.equals("")))
			hql.append("and cp.content like '%" + smscontent + "%' ");
		if ((begintime != null) && (!begintime.equals("")))
			hql.append("and cp.createtime>='" + begintime + " 00:00:00' ");
		if ((endtime != null) && (!endtime.equals("")))
			hql.append("and cp.createtime<='" + endtime + " 23:59:59' ");
		hql.append("order by cp.createtime");

		Query query = this.getOracleCurrentSession().createQuery(hql.toString());
		List<Sms> ls = (List<Sms>) query.list();
		return ls;
	}

}
