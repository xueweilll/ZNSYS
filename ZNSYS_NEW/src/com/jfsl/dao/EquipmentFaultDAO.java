package com.jfsl.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.EquipmentFault;

@SuppressWarnings("unchecked")
public class EquipmentFaultDAO extends DAOImp {
	
	public List<EquipmentFault> ListAll()
	{
		String sqlstr="from EquipmentFault";
		Query query =this.getOracleCurrentSession().createQuery(sqlstr);
		List<EquipmentFault> ls=query.list();
		return ls;
	}
	
	public EquipmentFault find(int id)
	{
		EquipmentFault dep=(EquipmentFault)this.getOracleCurrentSession().get(EquipmentFault.class, id);
		return dep;
	}
	
	public void delete(int id)
	{
		Session session=this.getOracleCurrentSession();
		Transaction tx=null;
		Query query=session.createQuery("delete EquipmentFault as a where a.id=:id");
		query.setInteger("id",id);
		tx=session.beginTransaction();
		query.executeUpdate();
		tx.commit();
		session.clear();
		
	}

}
