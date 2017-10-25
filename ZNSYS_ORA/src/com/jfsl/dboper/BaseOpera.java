package com.jfsl.dboper;

import org.hibernate.Session;

import com.jfsl.OracleHibernateSessionFactory;


public class BaseOpera 
{
	
	public Session getSession()
	{
		return OracleHibernateSessionFactory.getSession();
	}
}
