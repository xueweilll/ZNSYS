package com.jfsl.dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;


public class JsonDAO extends DAOImp
{
	/**��ѯĿ�����ݿ�����*/
	public List<?> ListAll(String TableName)
	{
		try{
		Session session=this.getOracleCurrentSession();
		SQLQuery query=session.createSQLQuery("select * from "+TableName);
		List<?> l=query.list();
		/*
		 * 
		 * list�������ɸ�Object[]�������а��������ֶ�ֵ
		 */
		return l;}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
