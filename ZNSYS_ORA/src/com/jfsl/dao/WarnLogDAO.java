package com.jfsl.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;

@SuppressWarnings("unchecked")
public class WarnLogDAO extends DAOImp {
	
	/*
	public List<WarnLog> ListAll(String stationid,String begintime,String endtime)
	{
		String sqlstr="from WarnLog as pr where pr.warntime between :tm1 and :tm2 ";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+="and pr.warndict.station.stcd=:stationid ";
		sqlstr+="order by pr.warntime,pr.warndict.station.stcd";
		this.clearCurrentSession();
		Query query =this.getCurrentSession().createQuery(sqlstr);
		if ((stationid!=null)&&(!stationid.equals(""))) query.setString("stationid",stationid);
		query.setString("tm1",begintime+" 00:00:00");
		query.setString("tm2",endtime+" 23:59:59");
		List<WarnLog> ls=(List<WarnLog>)query.list();
		return ls;
	}
	*/

	public List<Map> ListAll(String stationid,String begintime,String endtime)
	{
		String sqlstr="select stationcode,stationname,warncode,warncaption,warntime," +
				"freetime,warnstate from V_R_WarnLog where f_warntime between :tm1 and :tm2 ";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+="and stationcode=:stationid ";
		sqlstr+="order by warntime,stationcode";
		this.clearCurrentSession();
		Query query =this.getCurrentSession().createSQLQuery(sqlstr);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		if ((stationid!=null)&&(!stationid.equals(""))) query.setString("stationid",stationid);
		query.setString("tm1",begintime+" 00:00:00");
		query.setString("tm2",endtime+" 23:59:59");
		List<Map> ls=(List<Map>)query.list();
		return ls;
	}
}
