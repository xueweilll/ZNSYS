package com.jfsl.dao;

import java.util.List;

import org.hibernate.Query;

@SuppressWarnings("unchecked")
public class RsvravR1DAO extends DAOImp {
	
	public List ListAll(String stationid,String begintime,String endtime,String sttdrcd)
	{
		String sqlstr="select a.stcd,b.f_stnm as stnm,idtm,avrz from V_ST_RSVR_S";
		if (sttdrcd.equals("1")) sqlstr+="0";
		else if(sttdrcd.equals("6")) sqlstr+="2";
		else sqlstr+="1";
		sqlstr+=" a,T_B_Station b " +
				"where a.stcd=b.f_stcd and idtm between :tm1 and :tm2 " ;
		if ((stationid!=null)&&(!stationid.equals(""))) sqlstr+="and a.stcd=:stationid ";
		sqlstr+="order by a.stcd,idtm";
		this.clearCurrentSession();
		Query query =this.getCurrentSession().createSQLQuery(sqlstr);
		if ((stationid!=null)&&(!stationid.equals(""))) query.setString("stationid",stationid);
		query.setString("tm1",begintime+" 00:00:00");
		query.setString("tm2",endtime+" 23:59:59");
		List ls=query.list();
		return ls;
	}
}
