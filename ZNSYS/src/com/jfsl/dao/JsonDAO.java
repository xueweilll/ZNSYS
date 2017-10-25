package com.jfsl.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;


public class JsonDAO extends DAOImp
{
	/**查询目标数据库内容*/
	@SuppressWarnings("unchecked")
	public List ListAll(String TableName)
	{
		try{
		Session session=this.getOracleCurrentSession();
		SQLQuery query=session.createSQLQuery("select * from "+TableName);
		List l=query.list();
		/*
		 * 
		 * list中是若干个Object[]，数组中包括各个字段值
		 */
		return l;}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
