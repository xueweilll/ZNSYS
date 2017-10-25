package com.jfsl.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

//import com.jfsl.HibernateSessionFactory;
import com.jfsl.OracleHibernateSessionFactory;
import com.jfsl.pojo.Pojo;

public class DAOImp implements DAO
{
	/*public void clearCurrentSession()
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
			session=getCurrentSession();		//����hibernate����
			tx=session.beginTransaction();
			session.save(pojo);
			tx.commit();						//���浽��ݿ�
		}
		catch(HibernateException e){
			tx.rollback();						//����ʱ�ع�����
			System.out.println("store failure!@DAOImp.java");
		}finally{
			closeOracleCurrentSession();
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
		}finally{
			closeOracleCurrentSession();
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
		}finally{
			closeOracleCurrentSession();
		}
	}*/
	//Oracle����

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
			session=getOracleCurrentSession();		//����Oraclehibernate����
			tx=session.beginTransaction();
			session.save(pojo);
			tx.commit();						//���浽��ݿ�
			System.out.println("store Oraclesuccess!@DAOImp.java");
		}
		catch(HibernateException e){
			tx.rollback();						//����ʱ�ع�����
			System.out.println("store Oraclefailure!@DAOImp.java");
		}finally{
			closeOracleCurrentSession();
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
		}finally{
			closeOracleCurrentSession();
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
			e.printStackTrace();
			tx.rollback();
		}finally{
			closeOracleCurrentSession();
		}
	}
	
	
}
