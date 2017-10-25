package com.jfsl.dao;

import java.util.List;

import org.hibernate.Query;
import com.jfsl.pojo.WarnDict;

@SuppressWarnings("unchecked")
public class WarnDictDAO extends DAOImp {
	
	public List<WarnDict> ListAll()
	{
		String sqlstr="from WarnDict as v order by v.station.stcd,v.code";
		Query query =this.getCurrentSession().createQuery(sqlstr);
		List<WarnDict> ls=(List<WarnDict>)query.list();
		return ls;
	}
	
	public WarnDict find(int id)
	{
		WarnDict dep=(WarnDict)this.getCurrentSession().get(WarnDict.class, id);
		return dep;
	}
	
	public List<WarnDict> query(String stationid)
	{
		String sqlstr="from WarnDict as v where v.station.stcd=:stationid order by v.station.stcd,v.code";
		Query query =this.getCurrentSession().createQuery(sqlstr);
		query.setString("stationid",stationid);
		List<WarnDict> ls=(List<WarnDict>)query.list();
		return ls;
	}
	
}
