package com.jfsl.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.Area;
import com.jfsl.pojo.Backup;
import com.jfsl.pojo.Equipment;

@SuppressWarnings("unchecked")
public class BackupDAO extends DAOImp {
	
	public List<Backup> ListAll()
	{
		try{
		System.out.println("ddddjojoj");
		Query query =this.getOracleCurrentSession().createQuery("from Backup");
		List<Backup> ls=query.list();
		System.out.println(ls.size());
		return ls;}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
//未写完 ，，数据库的备份  名字的取规则
	public void addBackup() {
		// TODO Auto-generated method stub
		String path="c:/back";
		Session s=this.getOracleCurrentSession();
	    Transaction t=s.beginTransaction();
	    SQLQuery sq=s.createSQLQuery("");
	    Backup b=new Backup();
	    b.setCreTime(new Date());
	    b.setPath(path);
	    s.save(b);
	    t.commit();
	}
    
}
