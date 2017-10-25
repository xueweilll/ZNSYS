package com.jfsl.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.HibernateSessionFactory;
import com.jfsl.OracleHibernateSessionFactory;
import com.jfsl.pojo.Pojo;

public class DAOImp implements DAO
{
	protected Logger log = Logger.getLogger(this.getClass());
	public void clearCurrentSession()
	{
		getCurrentSession().clear();
	}
	
	public Session getCurrentSession()
	{
		//return HibernateSessionFactory.getSession();
		return OracleHibernateSessionFactory.getSession();
	}
	
	public void closeCurrentSession()
	{
		OracleHibernateSessionFactory.closeSession();
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
			log.error("store failure!@DAOImp.java");
		}finally{
			closeOracleCurrentSession();
		}
	}
	
	/**
	 * 执行原生态的sql插入和更新
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean updateSql(String sql,Object[] obj)
	{
		   boolean return_f = false;
		   Session session=null;  
		      Transaction tx=null;  
		      try{  
		         session=getOracleCurrentSession();  
		         tx=session.beginTransaction();            
		         SQLQuery query = session.createSQLQuery(sql);
					if(obj!=null && obj.length>0)
					{
						for(int i=0,len=obj.length;i<len;i++)
						{
							query.setParameter(i, obj[i]);
						}
					}	
					int return_bz = query.executeUpdate();
					if(return_bz>=0)
					{
						return_f = true;
					}
		            tx.commit();  
		      }catch(HibernateException e)
		      {  
		         tx.rollback();  
		         e.printStackTrace();  
		         log.error("store failure!@DAOImp.java");
		      }
		      finally
		      {  
		    	  closeOracleCurrentSession(); 
		      } 
		      return return_f;
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
			tx.rollback();
		}finally{
			closeOracleCurrentSession();
		}
	}
	
	
}
