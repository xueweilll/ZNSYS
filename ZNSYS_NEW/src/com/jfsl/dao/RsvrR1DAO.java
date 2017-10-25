package com.jfsl.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;

@SuppressWarnings("unchecked")
public class RsvrR1DAO extends DAOImp {
	
	public List<Map> ListAll(String stationid,String begintime,String endtime)
	{
		String sqlstr="select a.stcd,b.f_stnm as stnm,tm,rz,rwptn from ST_RSVR_R a,T_B_Station b " +
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
		String sqlstr="select stcd,stnm,bsnm,tm,sttp,rz,rwptn,z0,z1,warnlevel from V_ST_RSVR_RALARM";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+=" where stcd='"+stationid+"'";
		this.clearOracleCurrentSession();
		Query query =this.getOracleCurrentSession().createSQLQuery(sqlstr);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> ls=(List<Map>)query.list();
		return ls;
	}
	
	public List<Map> GetAll(String stationid,String begintime,String endtime)
	{
		String sqlstr="select stcd,tm,rz,rwptn from ST_RSVR_R where tm between '"+begintime+"' and '"+endtime+"' ";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+="and stcd='"+stationid+"' ";
		sqlstr+="order by stcd,tm";
		this.clearOracleCurrentSession();
		Query query =this.getOracleCurrentSession().createSQLQuery(sqlstr);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> ls=(List<Map>)query.list();
		return ls;
	}
}
