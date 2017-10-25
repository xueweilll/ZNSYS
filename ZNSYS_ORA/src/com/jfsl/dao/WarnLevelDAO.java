package com.jfsl.dao;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.jfsl.pojo.WarnLevel;
public class WarnLevelDAO extends DAOImp{
	@SuppressWarnings("unchecked")
	public List<WarnLevel> ListAll()
	{
		Session session = this.getCurrentSession();
		Query query = session.createQuery("from WarnLevel");
		List<WarnLevel> ls = query.list();
		return ls;
	}
	@SuppressWarnings("unchecked")
	public List<WarnLevel> Query(String code,String caption)
	{
		StringBuffer hql = new StringBuffer();
		hql.append("from WarnLevel as cp ");
		hql.append("where 1=1 ");
		if ((code != null) && (!code.equals("")))
			hql.append("and cp.code like '%" + code + "%' ");
		if ((caption != null) && (!caption.equals("")))
			hql.append("and cp.caption like '%" + caption + "%' ");
		hql.append("order by cp.id");
		Query query=this.getCurrentSession().createQuery(hql.toString());
		List<WarnLevel> ls=query.list();
		return ls;
	}

	public WarnLevel Find(int id)
	{
		WarnLevel warnlevel = (WarnLevel) this.getCurrentSession().get(WarnLevel.class, id);
		return warnlevel;
	}
}
