package com.jfsl.dao;

import java.util.Collections;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import com.jfsl.pojo.Function;

public class FunctionDAO extends DAOImp
{
	@SuppressWarnings("unchecked")
	public List<Function> ListAll()
	{
		Session session=this.getCurrentSession();
		Query query=session.createQuery("from Function f order by f.id");
		List<Function> ls=(List<Function>)query.list();
		return ls;
	}
	
	public Function Find(String id)
	{
		Session session=this.getCurrentSession();
		Function f=(Function)session.load(Function.class, id);
		return f;
	}
	
	@SuppressWarnings("unchecked")
	public List<Function> ListExFunction(String username)
	{
		String hqlstr="from Function f where f.formurl is not null and f.id not in";
		hqlstr=hqlstr+" (select uo.function.id from UserOperation as uo where uo.username=:username)";
		hqlstr=hqlstr+" order by f.id";
		Session session=this.getCurrentSession();
		Query query=session.createQuery(hqlstr);
		query.setString("username",username);
		List<Function> ls=(List<Function>)query.list();
		String ids="";
		String id;
		for(Function f:ls)
		{
			id=f.getId();
			while (id.length()>3)
			{
				ids=ids+"'"+(id.substring(0,id.length()-3))+"',";
				id=id.substring(0,id.length()-3);
			}
		}
		ids=ids.substring(0,ids.length()-1);
		hqlstr="select distinct f from Function f where f.id in("+ids+")";
		query=session.createQuery(hqlstr);
		List<Function> ls1=(List<Function>)query.list();
		ls.addAll(ls1);
		
		Collections.sort(ls);
		return ls;
	}
}
