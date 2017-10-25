package com.jfsl.dao;

/**time package*/
import java.text.*;
import java.util.Date;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**java bean*/
import com.jfsl.pojo.Push;

public class PushDAO extends DAOImp
{
	/**check all*/
	@SuppressWarnings("unchecked")
	public List<Push> ListAll()
	{	
		//System.out.println("***************");
		Session session = this.getOracleCurrentSession();
		Query query = session.createQuery("from Push");
		//System.out.println("list all");
		List<Push> ls = query.list();
		return ls;
	}
	
	/**check id*/
	@SuppressWarnings("unchecked")
	public Push Find(int id)
	{
		Query query=this.getOracleCurrentSession().createQuery("select p from Push as p where p.id=:id");
		query.setInteger("id", id);
		Push ls=(Push) query.list().get(0);
		return ls;
	}
	
	/**find Push before deadline from Push*/
	@SuppressWarnings("unchecked")
	public List<Push> ListUsed()
	{
		/**get today time*/
		java.util.Date today=new java.util.Date();
		//SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//String nowtime = simple.format(today);
		
		/**query*/
		Query query = this.getOracleCurrentSession().createQuery("select p from Push as p where p.publishtime <= :nowtime and p.deadline >= :nowtime");
		//query.setString("nowtime",nowtime);
		query.setDate("nowtime", today);
		List<Push> ls = query.list();
		return ls;
	}
}
