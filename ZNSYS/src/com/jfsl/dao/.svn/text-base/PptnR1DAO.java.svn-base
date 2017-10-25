package com.jfsl.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;

@SuppressWarnings("unchecked")
public class PptnR1DAO extends DAOImp {
	
	public List<Map> ListAll(String stationid,String begintime,String endtime,int flag)
	{
		String sqlstr="select a.stcd,b.f_stnm as stnm,tm,drp from ST_PPTN_R a,T_B_Station b " +
				"where a.stcd=b.f_stcd ";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+="and a.stcd='"+stationid+"' ";
		if (flag==0) sqlstr+="and intv=0.05 ";
		else sqlstr+="and intv=1.00 ";
		sqlstr+="and tm between '"+begintime+"' and '"+endtime+"' order by a.stcd,tm";
		this.clearCurrentSession();
		Query query =this.getCurrentSession().createSQLQuery(sqlstr);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> ls=(List<Map>)query.list();
		return ls;
	}
	
	public List<Map> GetReal(String stationid,int flag)
	{
		String sqlstr="select stcd,stnm,tm,sttp,drp from V_ST_PPTN_R";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+=" where stcd='"+stationid+"'";
		if (flag==0) sqlstr+="and intv=0.05 ";
		else sqlstr+="and intv=1.00 ";
		this.clearCurrentSession();
		Query query =this.getCurrentSession().createSQLQuery(sqlstr);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> ls=(List<Map>)query.list();
		return ls;
	}
	
	public List<Map> GetAll(String stationid,String begintime,String endtime,int flag)
	{
		String sqlstr="select stcd,tm,drp from ST_PPTN_R where tm between '"+begintime+"' and '"+endtime+"' ";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+="and stcd='"+stationid+"' ";
		if (flag==0) sqlstr+="and intv=0.05 ";
		else sqlstr+="and intv=1.00 ";
		sqlstr+="order by stcd,tm";
		this.clearCurrentSession();
		Query query =this.getCurrentSession().createSQLQuery(sqlstr);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> ls=(List<Map>)query.list();
		return ls;
	}
	
	public List<Map> GetDayAll(String stationid,String begintime,String endtime,int flag)
	{
		String sqlstr="select stcd,sum(drp) as drp from ST_PPTN_R where tm between '"+begintime+"' and '"+endtime+"' ";
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+="and stcd='"+stationid+"' ";
		if (flag==0) sqlstr+="and intv=0.05 ";
		else sqlstr+="and intv=1.00 ";
		sqlstr+="group by stcd order by stcd";
		this.clearCurrentSession();
		Query query =this.getCurrentSession().createSQLQuery(sqlstr);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map> ls=(List<Map>)query.list();
		return ls;
	}
}
