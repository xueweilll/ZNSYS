package com.jfsl.dao;

/**time package*/
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.SCGC;

public class SCGCDAO extends DAOImp
{
	@SuppressWarnings("unchecked")
	public List<SCGC> ListAll(){
    	Query query=this.getOracleCurrentSession().createQuery("select distinct s from SCGC s order by s.id.csname,gcsx");
    	List<SCGC> list = (List<SCGC>)query.list();
    	return list;
    }
	
	public List<SCGC> ListXMZMC(String xmzmc){
		Query query=this.getOracleCurrentSession().createQuery("select distinct s from SCGC as s where s.id.xmzmc =:xmzmc order by s.id.csname,gcsx");
    	query.setString("xmzmc", xmzmc);
    	List<SCGC> list = (List<SCGC>)query.list();
    	return list;
    }
	
	public List<SCGC> ListJCXM(String jcxm){
    	Query query=this.getOracleCurrentSession().createQuery("select distinct s from SCGC as s where s.id.jcxm =:jcxm order by s.id.csname,gcsx");
    	query.setString("jcxm", jcxm);
    	List<SCGC> list = (List<SCGC>)query.list();
    	return list;
    }
	
	public List ListCombxmzmc(){
		Query query  = this.getOracleCurrentSession().createQuery("select distinct s.id.xmzmc from SCGC as s");
		List list = query.list();
		return list;
	}
	
	public List ListComJCXM(){
		Query query  = this.getOracleCurrentSession().createQuery("select distinct s.id.jcxm from SCGC as s");
		List list = query.list();
		return list;
	}
	
	public List ListCombCSNAME(){
		Query query  = this.getOracleCurrentSession().createQuery("select distinct s.id.csname from SCGC as s");
		List list = query.list();
		return list;
	}
}
