package com.jfsl.dao;


import java.util.List;
import org.hibernate.Query;

import com.jfsl.pojo.AddList;
import com.jfsl.pojo.SmsGroupAddList;


public class SmsGroupAddListDAO extends DAOImp
{

	@SuppressWarnings("unchecked")
	public List<SmsGroupAddList> ListAll(int groupid)
	{
		StringBuffer hql = new StringBuffer();
		hql.append("from SmsGroupAddList as c where c.smsgroup.id=:groupid");
		Query query = this.getCurrentSession().createQuery(hql.toString());
		query.setInteger("groupid",groupid);
		List<SmsGroupAddList> ls = (List<SmsGroupAddList>) query.list();
		return ls;
	}

	public SmsGroupAddList find(int id)
	{
		SmsGroupAddList c = (SmsGroupAddList) this.getCurrentSession().load(SmsGroupAddList.class, id);
		return c;
	}
	
	@SuppressWarnings("unchecked")
	public List<AddList> ListAll(String groupids)
	{
		StringBuffer hql = new StringBuffer();
		hql.append("select distinct c.addlist from SmsGroupAddList as c " +
				"where c.smsgroup.id in ("+groupids+") order by c.addlist.membername");
		Query query = this.getCurrentSession().createQuery(hql.toString());
		List<AddList> ls = (List<AddList>) query.list();
		return ls;
	}
}
