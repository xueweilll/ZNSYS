package com.jfsl.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;

@SuppressWarnings("unchecked")
public class RiverR1DAO extends DAOImp {
	
	public List<Map> ListAll(String stationid,String begintime,String endtime)
	{
		String sqlstr="select a.stcd,b.f_stnm as stnm,tm,z,wptn from ST_RIVER_R a,T_B_Station b " +
				"where a.stcd=b.f_stcd ";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+="and a.stcd='"+stationid+"' ";
		sqlstr+="and tm between '"+begintime+"' and '"+endtime+"' order by a.stcd,tm";
		this.clearOracleCurrentSession();
		Query query =this.getOracleCurrentSession().createSQLQuery(sqlstr);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> ls=(List<Map>)query.list();
		return ls;
	}
	
	public List<Map> GetReal(String stationid)
	{
		String sqlstr="select stcd,stnm,bsnm,tm,sttp,z,wptn,z0,z1,warnlevel from V_ST_RIVER_RALARM";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+=" where stcd='"+stationid+"'";
		this.clearOracleCurrentSession();
		Query query =this.getOracleCurrentSession().createSQLQuery(sqlstr);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> ls=(List<Map>)query.list();
		return ls;
	}
	
	public List<Map> GetAll(String stationid,String begintime,String endtime)
	{
		String sqlstr="select stcd,tm,z,wptn from ST_RIVER_R where tm between '"+begintime+"' and '"+endtime+"' ";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+="and stcd='"+stationid+"' ";
		sqlstr+="order by stcd,tm";
		this.clearOracleCurrentSession();
		Query query =this.getOracleCurrentSession().createSQLQuery(sqlstr);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> ls=(List<Map>)query.list();
		return ls;
	}
}
