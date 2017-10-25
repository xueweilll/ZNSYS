package com.jfsl.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.EquipmentMaintain;

@SuppressWarnings("unchecked")
public class EquipmentMaintainDAO extends DAOImp {
	
	public List<EquipmentMaintain> ListAll()
	{
		String sqlstr="from EquipmentMaintain";
		Query query =this.getCurrentSession().createQuery(sqlstr);
		List<EquipmentMaintain> ls=query.list();
		return ls;
	}
	
	public EquipmentMaintain find(int id)
	{
		EquipmentMaintain dep=(EquipmentMaintain)this.getCurrentSession().get(EquipmentMaintain.class, id);
		return dep;
	}
	
	public List<EquipmentMaintain> ListMaintain(){
		/**get today time*/
		java.util.Date today=new java.util.Date(new java.util.Date().getTime() -1*24*60*60*1000);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowtime = simple.format(today);
		
		String sqlstr="from EquipmentMaintain as e where e.maintaintime <='"+nowtime+"' and ( e.memo = null or e.memo='')";
		Query query =this.getCurrentSession().createQuery(sqlstr);
		List<EquipmentMaintain> ls=query.list();
		return ls;
	}
	
	public void delete(int id)
	{
		Session session=this.getCurrentSession();
		Transaction tx=null;
		Query query=session.createQuery("delete EquipmentMaintain as a where a.id=:id");
		query.setInteger("id",id);
		tx=session.beginTransaction();
		query.executeUpdate();
		tx.commit();
		session.clear();
		
	}

}
