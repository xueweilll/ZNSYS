package com.jfsl.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.Area;

@SuppressWarnings("unchecked")
public class AreaDAO extends DAOImp {
	
	public List<Area> ListAll()
	{
		String sqlstr="from Area";
		Query query =this.getCurrentSession().createQuery(sqlstr);
		List<Area> ls=query.list();
		return ls;
	}
	
	public Area find(int id)
	{
		Area dep=(Area)this.getCurrentSession().get(Area.class, id);
		return dep;
	}
	
	public void delete(int id)
	{
		Session session=this.getCurrentSession();
		Transaction tx=null;
		Query query=session.createQuery("delete Area as a where a.id=:id or a.parentid=:parentid");
		query.setInteger("id",id);
		query.setInteger("parentid",id);
		tx=session.beginTransaction();
		query.executeUpdate();
		tx.commit();
		session.clear();
		
	}

}
