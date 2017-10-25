package com.jfsl.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class ZGCDAO extends DAOImp
{
	/**check all*/
	@SuppressWarnings("unchecked")
	public List ListAll()
	{	
		//System.out.println("***************");
		Session session = this.getOracleCurrentSession();
		Query query = session.createQuery("from ZGC");
		//System.out.println("list all");
		List ls = query.list();
		return ls;
	}
	
	/**check csname*/
	public List Find(String ypph , String jcxm ,String csname)
	{	
		String sql = "select SCGC from SY_ZGC where YPPH='"+ypph+"' and JCXM = '"+jcxm+"' and ( CSNAME='"+csname+"' or CSNAME='" + jcxm + "')";
		System.out.println("Oracle sql £º"+ sql);
		Query query=this.getOracleCurrentSession().createSQLQuery(sql);
		List ls=query.list();
		return ls;
	}
	
	/**check jcxm*/
	public List Find(String ypph , String jcxm)
	{	
		String sql = "select  SCGC from SY_ZGC where YPPH='"+ypph+"' and JCXM = '"+jcxm+"'";
		//System.out.println("Oracle sql £º"+ sql);
		Query query=this.getOracleCurrentSession().createSQLQuery(sql);
		List ls=query.list();
		return ls;
	}
}
