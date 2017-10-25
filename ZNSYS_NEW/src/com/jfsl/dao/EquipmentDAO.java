package com.jfsl.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.Equipment;

@SuppressWarnings("unchecked")
public class EquipmentDAO extends DAOImp {
	
	public List<Equipment> ListAll()
	{
		Query query =this.getOracleCurrentSession().createQuery("from Equipment");
		List<Equipment> ls=query.list();
		return ls;
	}
	
	public Equipment find(int id)
	{
		Equipment dep=(Equipment)this.getOracleCurrentSession().get(Equipment.class, id);
		return dep;
	}
	
	public void delete(int id)
	{
		Session session=this.getOracleCurrentSession();
		Transaction tx=null;
		Query query=session.createQuery("delete Equipment as a where a.id=:id");
		query.setInteger("id",id);
		tx=session.beginTransaction();
		query.executeUpdate();
		tx.commit();
		session.clear();
		
	}
	
	public List<Equipment> query(String field,String value){
		Query query =this.getOracleCurrentSession().createQuery("from Equipment as e where e."+field+" like '%"+value+"%'");
		List<Equipment> ls=query.list();
		return ls;
	}

}
