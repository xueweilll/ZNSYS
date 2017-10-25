package com.jfsl.dao;

import java.util.List;

import org.hibernate.Query;

public class DictionaryDAO extends DAOImp {
	
	public List<?> ListAll(String tablename,String fieldname)
	{
		String sqlstr="select new Map(d.code as code,d.caption as caption) from Dictionary as d where d.tablename=:tablename and d.fieldname=:fieldname order by d.code";
		Query query =this.getOracleCurrentSession().createQuery(sqlstr);
		query.setString("tablename",tablename);
		query.setString("fieldname",fieldname);
		List<?> ls=query.list();
		return ls;
	}
}
