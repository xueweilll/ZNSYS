package com.jfsl.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.HibernateSessionFactory;
import com.jfsl.OracleHibernateSessionFactory;
import com.jfsl.pojo.Pojo;

public class DAOImp implements DAO
{
	public void clearCurrentSession()
	{
		getCurrentSession().clear();
	}
	
	public Session getCurrentSession()
	{
		return HibernateSessionFactory.getSession();
	}
	
	public void closeCurrentSession()
	{
		HibernateSessionFactory.closeSession();
	}
	
	public void Add(Pojo pojo)
	{
		Session session=null;
		Transaction tx=null;
		try{
			session=getCurrentSession();		//创建hibernate连接
			tx=session.beginTransaction();
			session.save(pojo);
			tx.commit();						//保存到数据库
		}
		catch(HibernateException e){
			tx.rollback();						//错误时回滚操作
			System.out.println("store failure!@DAOImp.java");
		}
	}

	public void Delete(Pojo pojo)
	{
		Session session=null;
		Transaction tx=null;
		try{
			session=getCurrentSession();
			tx=session.beginTransaction();
			session.delete(pojo);
			tx.commit();
		}
		catch(HibernateException e){
			tx.rollback();
		}
	}

	public void Update(Pojo pojo)
	{
		Session session=null;
		Transaction tx=null;
		try{
			session=getCurrentSession();
			tx=session.beginTransaction();
			session.update(pojo);
			tx.commit();
		}
		catch(HibernateException e){
			tx.rollback();
			System.out.println("DAOImp updata failure !"+e);
		}
	}
	//Oracle连接

	public void clearOracleCurrentSession()
	{
		getOracleCurrentSession().clear();
	}
	
	public Session getOracleCurrentSession()
	{
		return OracleHibernateSessionFactory.getSession();
	}
	
	public void closeOracleCurrentSession()
	{
		OracleHibernateSessionFactory.closeSession();
	}
	
	public void OracleAdd(Pojo pojo)
	{
		Session session=null;
		Transaction tx=null;
		try{
			session=getOracleCurrentSession();		//创建Oraclehibernate连接
			tx=session.beginTransaction();
			session.save(pojo);
			tx.commit();						//保存到数据库
			System.out.println("store Oraclesuccess!@DAOImp.java");
		}
		catch(HibernateException e){
			tx.rollback();						//错误时回滚操作
			System.out.println("store Oraclefailure!@DAOImp.java");
		}
	}

	public void OracleDelete(Pojo pojo)
	{
		Session session=null;
		Transaction tx=null;
		try{
			session=getOracleCurrentSession();
			tx=session.beginTransaction();
			session.delete(pojo);
			tx.commit();
		}
		catch(HibernateException e){
			tx.rollback();
		}
	}

	public void OracleUpdate(Pojo pojo)
	{
		Session session=null;
		Transaction tx=null;
		try{
			session=getOracleCurrentSession();
			tx=session.beginTransaction();
			session.update(pojo);
			tx.commit();
		}
		catch(HibernateException e){
			tx.rollback();
		}
	}
	
	
}
