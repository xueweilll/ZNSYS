package com.jfsl.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
/**java bean*/
import com.jfsl.pojo.Com;

public class ComDAO extends DAOImp
{
	/**check all*/
	@SuppressWarnings("unchecked")
	public List<Com> ListAll()
	{	
		//System.out.println("***************");
		Session session = this.getOracleCurrentSession();
		Query query = session.createQuery("from Com");
		//System.out.println("list all");
		List<Com> ls = query.list();
		return ls;
	}
	
	/**check id*/
	public Com Find(int id)
	{
		Query query=this.getOracleCurrentSession().createQuery("select c from Com as c where c.id=:id");
		query.setInteger("id", id);
		Com ls=(Com)query.list().get(0);
		return ls;
	}
}
