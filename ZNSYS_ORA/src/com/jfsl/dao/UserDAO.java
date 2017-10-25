package com.jfsl.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.Role;
import com.jfsl.pojo.User;

public class UserDAO extends DAOImp
{

	@SuppressWarnings("unchecked")
	public List<User> ListAll()
	{
		Session session = this.getOracleCurrentSession();
		Query query = session.createQuery("from User");
		List<User> ls = query.list();
		return ls;
	}
	@SuppressWarnings("unchecked")
	public List<User> ListAll(String depid)
	{
		if(depid==null) return this.ListAll();
		Query query=this.getOracleCurrentSession().createQuery("select u from User as u join u.department as d where d.id like :depid");
		query.setString("depid", depid+"%");
		List<User> ls=query.list();
		return ls;
	}

	public User Find(String username)
	{
		User user = (User) this.getOracleCurrentSession().get(User.class, username);
		return user;
	}

	@SuppressWarnings("unchecked")
	public User ListRole(String username)
	{
		Session session = this.getOracleCurrentSession();
		Query query = session
				.createQuery("select distinct u from User as u left join u.roles as r where u.username=:username");
		query.setString("username", username);
		List<User> ls = (List<User>) query.list();
		if (ls.size() == 0)
		{
			return null;
		}else{
			return ls.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	public void addRoletoUser(String username, String[] ids)
	{
		Session session = null;
		Transaction tx = null;
		Query query;
		try
		{
			session = getCurrentSession();
			query = session
					.createQuery("select distinct u from User as u left join u.roles as r where u.username=:username");
			query.setString("username", username);
			User user = ((List<User>) query.list()).get(0);
			user.getRoles().clear();
			Role role;
			for (int i = 0; i < ids.length; i++)
			{
				role = (Role) session
						.load(Role.class, Integer.parseInt(ids[i]));
				user.getRoles().add(role);
			}
			tx = session.beginTransaction();
			session.saveOrUpdate(user);
			tx.commit();
		}
		catch (HibernateException e)
		{
			tx.rollback();
			e.printStackTrace();
		}
	}
	
	public void DeleteUser(String username)
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = getCurrentSession();
			User user=(User)session.load(User.class, username);
			tx=session.beginTransaction();
			session.delete(user);
			tx.commit();
		}
		catch(Exception e)
		{
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public User getUserDepartment(String username)
	{
		String hqlstr="select u from User as u where u.username=:username";
		Session session=this.getOracleCurrentSession();
		Query query=session.createQuery(hqlstr);
		query.setString("username",username);
		List<User> ls=query.list();
		if (ls.size()==0) 
			return null;
		else 
			return ls.get(0);
	}

}
