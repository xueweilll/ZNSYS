package com.jfsl.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**java bean*/
import com.jfsl.pojo.Control;

public class ControlDAO extends DAOImp
{
	/**check all*/
	@SuppressWarnings("unchecked")
	public List<Control> ListAll()
	{	
		//System.out.println("***************");
		Session session = this.getCurrentSession();
		Query query = session.createQuery("from Control");
		//System.out.println("list all");
		List<Control> ls = query.list();
		return ls;
	}
	
	/**check id*/
	@SuppressWarnings("unchecked")
	public List<Control> Find(int id)
	{
		if(id == 0) return this.ListAll();
		Query query=this.getCurrentSession().createQuery("select c from Control as c where c.id=:id");
		query.setInteger("id", id);
		List<Control> ls=query.list();
		return ls;
	}

	/**delete control by id*/
	public void DeleteControl(int id)
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = getCurrentSession();
			Control control=(Control)session.load(Control.class, id);
			tx=session.beginTransaction();
			session.delete(control);
			tx.commit();
		}
		catch(Exception e)
		{
			System.out.println("delete control error!");
		}
	}
}
