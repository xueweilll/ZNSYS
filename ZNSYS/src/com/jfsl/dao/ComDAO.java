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
import com.jfsl.pojo.Com;

public class ComDAO extends DAOImp
{
	/**check all*/
	@SuppressWarnings("unchecked")
	public List<Com> ListAll()
	{	
		//System.out.println("***************");
		Session session = this.getCurrentSession();
		Query query = session.createQuery("from Com");
		//System.out.println("list all");
		List<Com> ls = query.list();
		return ls;
	}
	
	/**check id*/
	@SuppressWarnings("unchecked")
	public Com Find(int id)
	{
		Query query=this.getCurrentSession().createQuery("select c from Com as c where c.id=:id");
		query.setInteger("id", id);
		Com ls=(Com)query.list().get(0);
		return ls;
	}
}
