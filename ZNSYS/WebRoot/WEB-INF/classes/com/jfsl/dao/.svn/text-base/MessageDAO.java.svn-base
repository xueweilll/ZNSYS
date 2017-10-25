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
import com.jfsl.pojo.Message;

public class MessageDAO extends DAOImp
{
	/**check all*/
	@SuppressWarnings("unchecked")
	public List<Message> ListAll()
	{	
		//System.out.println("***************");
		Session session = this.getCurrentSession();
		Query query = session.createQuery("from Message");
		//System.out.println("list all");
		List<Message> ls = query.list();
		return ls;
	}
	
	/**check id*/
	@SuppressWarnings("unchecked")
	public List<Message> Find(int messageid)
	{
		if(messageid == 0) return this.ListAll();
		Query query=this.getCurrentSession().createQuery("select m from Message as m where m.messageid=:messageid");
		query.setInteger("messageid", messageid);
		List<Message> ls=query.list();
		return ls;
	}
	
	/**find message before dateline from Message*/
	@SuppressWarnings("unchecked")
	public List<Message> ListUsed()
	{
		/**get today time*/
		java.util.Date today=new java.util.Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		String nowtime = simple.format(today);
		
		/**query*/
		Query query = this.getCurrentSession().createQuery("select m from Message as m where m.dateline >= :nowtime");
		query.setString("nowtime",nowtime);
		List<Message> ls = query.list();
		return ls;
	}

	/**delete message by messageid*/
	public void DeleteMessage(int messageid)
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = getCurrentSession();
			Message message=(Message)session.load(Message.class, messageid);
			tx=session.beginTransaction();
			session.delete(message);
			tx.commit();
		}
		catch(Exception e)
		{
			System.out.println("delete message error!");
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> QueryTime(String begintime,String endtime)
	{
		//String begin = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(begintime);
		//String end = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endtime);
		Query query = this.getCurrentSession().createQuery("select m from Message as m where m.publishtime > :begintime and m.publishtime <:endtime");
		query.setString("begintime",begintime);
		query.setString("endtime",endtime);
		List<Message> ls = (List<Message>)query.list();
		return ls;
	}
	
	@SuppressWarnings("unchecked")
	public List<Message> QueryAuthor(String author)
	{
		Query query = this.getCurrentSession().createQuery("select m from Message as m where m.author = :author");
		query.setString("author",author);
		List<Message> ls = (List<Message>)query.list();
		if(ls.size() == 0) return null;
		else return ls;
	}
}
