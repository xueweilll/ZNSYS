package com.jfsl.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.Function;
import com.jfsl.pojo.Role;

public class RoleDAO extends DAOImp
{
	@SuppressWarnings("unchecked")
	public List<Role> ListAll()
	{
		Query query=this.getOracleCurrentSession().createQuery("from Role");
		List<Role> ls=(List<Role>)query.list();
		return ls;
	}
	
	public Role Find(int id)
	{
		Role r=(Role)this.getOracleCurrentSession().load(Role.class, id);
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public Role ListGranted(int id)
	{
		Query query=this.getOracleCurrentSession().createQuery("select distinct r from Role as r left join r.functions as f where r.id=:id");
		query.setInteger("id", id);
		List<Role> ls=(List<Role>)query.list();
		if (ls.size()==0) 
			return null;
		else
			return ls.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public void Grant(int id,String[] ids)
	{
		Session session=null;
		Transaction tx=null;
		Query query;
		try{
			session=getOracleCurrentSession();
			query=session.createQuery("select distinct r from Role as r left join r.functions as f where r.id=:id");
			query.setInteger("id", id);
			Role r=((List<Role>)query.list()).get(0);
			r.getFunctions().clear();
			for(int i=0;i<ids.length;i++)
			{
				if (!ids[i].equals(""))
					r.getFunctions().add((Function)session.load(Function.class, ids[i]));
			}
			tx=session.beginTransaction();
			session.saveOrUpdate(r);
			tx.commit();
		}
		catch(HibernateException e){
			tx.rollback();
			e.printStackTrace();
		}		
	}
}
