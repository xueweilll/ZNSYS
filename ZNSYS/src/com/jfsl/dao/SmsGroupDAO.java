package com.jfsl.dao;


import java.util.List;
import org.hibernate.Query;

import com.jfsl.pojo.SmsGroup;

public class SmsGroupDAO extends DAOImp
{

	@SuppressWarnings("unchecked")
	public List<SmsGroup> ListAll(String username)
	{
		StringBuffer hql = new StringBuffer();
		hql.append("from SmsGroup as c where c.user.username=:username order by c.id");
		Query query = this.getCurrentSession().createQuery(hql.toString());
		query.setString("username",username);
		List<SmsGroup> ls = (List<SmsGroup>) query.list();
		return ls;
	}

	public SmsGroup find(int id)
	{
		SmsGroup c = (SmsGroup) this.getCurrentSession().load(SmsGroup.class, id);
		return c;
	}

	

}
