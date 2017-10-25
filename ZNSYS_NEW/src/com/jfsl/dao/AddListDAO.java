package com.jfsl.dao;

import java.util.List;

import org.hibernate.Query;

import com.jfsl.pojo.AddList;

public class AddListDAO extends DAOImp {
	
	@SuppressWarnings("unchecked")
	public List<AddList> ListAll()
	{
		StringBuffer hql = new StringBuffer();
		hql.append("from AddList as a order by a.id");
		Query query = this.getOracleCurrentSession().createQuery(hql.toString());
		List<AddList> ls = (List<AddList>) query.list();
		return ls;
	}
	
	public AddList find(int id)
	{
		AddList a = (AddList) this.getOracleCurrentSession().load(AddList.class,
				id);
		return a;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AddList> Query(String membername, String memberdept)
	{

		StringBuffer hql = new StringBuffer();
		hql.append("from AddList as ap ");
		hql.append("where 1=1 ");
		if ((membername != null) && (!membername.equals("")))
			hql.append("and ap.membername like '%" + membername + "%' ");
		if ((memberdept != null) && (!memberdept.equals("")))
			hql.append("and ap.memberdept like '%" + memberdept +"%' ");
		hql.append("order by ap.id");

		Query query = this.getOracleCurrentSession().createQuery(hql.toString());
		List<AddList> ls = (List<AddList>) query.list();
		return ls;
	}
		
}


