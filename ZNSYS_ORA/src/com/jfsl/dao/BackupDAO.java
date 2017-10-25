package com.jfsl.dao;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import sychel.com.util.OracleDatabaseBackup;

import com.jfsl.HibernateSessionFactory;
import com.jfsl.pojo.Area;
import com.jfsl.pojo.Backup;
import com.jfsl.pojo.Equipment;

@SuppressWarnings("unchecked")
public class BackupDAO extends DAOImp {
	
	public List<Backup> ListAll()
	{
		try{
//		System.out.println("ddddjojoj");
		Query query =this.getOracleCurrentSession().createQuery("from Backup");
		List<Backup> ls=query.list();
		System.out.println(ls.size());
		return ls;}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			HibernateSessionFactory.closeSession();
		}
	}
//未写完 ，，数据库的备份  名字的取规则
	public void addBackup() {
		// TODO Auto-generated method stub
		Backup b=new Backup();
		try {
			
			String path="c:/BackupDatabase";
			File file = new File(path);
			if(!file.isDirectory())
			{
				file.mkdir();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
				Process process1 = Runtime.getRuntime().exec("exp " + "scott" + "/" + "tgq2012" + "@" + "syy_data" + " file=" +path+"/"+ "databasebackup-"+df.format(new Date())+ ".dmp");
				
				Session s=this.getOracleCurrentSession();
				Transaction t=s.beginTransaction();
				
				b.setCreTime(new Date());
				b.setPath(path);
				s.save(b);
				t.commit();
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
				Process process1 = Runtime.getRuntime().exec("exp " + "scott" + "/" + "tgq2012" + "@" + "syy_data" + " file=" +path+"/"+ "databasebackup-"+df.format(new Date())+ ".dmp");
				Session s=this.getOracleCurrentSession();
				Transaction t=s.beginTransaction();
				b.setCreTime(new Date());
				b.setPath(path);
				s.save(b);
				t.commit();
			}
		} catch (HibernateException e) {
			System.out.println("error!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
	    
	}
    
}
