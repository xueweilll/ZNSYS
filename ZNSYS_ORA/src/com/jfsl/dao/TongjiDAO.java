package com.jfsl.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.Role;
import com.jfsl.pojo.User;

public class TongjiDAO extends DAOImp
{
	public String sqltj(String sql){
		String array="";
		try{
			Session s=this.getOracleCurrentSession();
			SQLQuery q=s.createSQLQuery(sql);
			List l=q.list();
			if(l.size()==0)
				array="[]";
			else
				array=createdata(l);
		}
		catch(Exception e){
			e.printStackTrace();
			array="errorsql";
		}
		return null;
		
		
	}
    public String createdata(List l){
    	String s="";
    	for(int i=0;i<l.size();i++){
    		
    		
    	}
    	return null;
    	
    }
	public String jsonph() {
		// TODO Auto-generated method stub
		try{
		Session s=this.getOracleCurrentSession();
		Query q=s.createSQLQuery("select YPPH,count(YPPH) as num from AH001 group by YPPH");
		List l=q.list();
		String hzb="[";
		String sz="[";
		System.out.println("按样品批号排序统计:"+l.size());
		for(int i=0;i<l.size();i++){
			Object[] o=(Object[]) l.get(i);
			hzb+="'"+o[0]+"',";
			sz+=o[1]+",";
		}
		hzb=hzb.substring(0,hzb.length()-1);
		hzb+="]";
		sz=sz.substring(0,sz.length()-1);
		sz+="]";
	    String fanhui=hzb+"@@@"+sz;
		return fanhui;}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String jsonwcqk(){
		try{
		Session s=this.getOracleCurrentSession();
		Query q=s.createSQLQuery("select YPPH,count(YPPH) as num from AH001 group by YPPH");
        List l=q.list();
        Query all=s.createSQLQuery("select YPPH,count(YPPH) from SYZX_AJHF03 group by YPPH,YSC having YSC=-1");
        List al=all.list();
        String[] hzb=new String[l.size()];
		String sz="[";
        for(int i=0;i<l.size();i++){
			Object[] o=(Object[]) l.get(i);
			hzb[i]=(String) o[0];
			sz+=o[1]+",";
		}
		sz=sz.substring(0,sz.length()-1);
		sz+="]";
		System.out.println("所有数值"+sz);
		//完成情况
		BigDecimal[] wcsz=new BigDecimal[l.size()];
		for(int i=0;i<al.size();i++){
			Object[] o=(Object[]) al.get(i);
			for(int j=0;j<hzb.length;j++){
				if(o[0].equals(hzb[j]))
					wcsz[j]=(BigDecimal) o[1];
			}
		}
		//将完成情况的数组转化为字符串
		String wcqksz="[";
		for(int i=0;i<wcsz.length;i++){
			wcqksz+=wcsz[i]+",";
		}
		wcqksz=wcqksz.substring(0,wcqksz.length()-1);
		wcqksz+="]";
		System.out.println("完成情况数值"+wcqksz);
		//将横坐标转化为字符串
		String hzbstr="[";
		for(int i=0;i<hzb.length;i++){
			hzbstr+="'"+hzb[i]+"',";
		}
		hzbstr=hzbstr.substring(0,hzbstr.length()-1);
		hzbstr+="]";
		System.out.println("横坐标数值"+hzbstr);
		String returnstr=hzbstr+"@@@"+sz+"@@@"+wcqksz;
		System.out.println(returnstr);
		return returnstr;}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public String jsonsql(String sql) {
		// TODO Auto-generated method stub
		try{
			Session s=this.getOracleCurrentSession();
			SQLQuery sq=s.createSQLQuery(sql);
			List l=sq.list();
			String hzb="[";
			String zzb="[";
			for(int i=0;i<l.size();i++){
				Object[] sz=(Object[]) l.get(i);
				Object h=sz[0];
				Object z=sz[1];
				hzb+="'"+h+"',";
				zzb+=z+",";
			}
			hzb=hzb.substring(0,hzb.length()-1);
			hzb+="]";
			System.out.println("横坐标数值"+hzb);
			zzb=zzb.substring(0,zzb.length()-1);
			zzb+="]";
			System.out.println("纵坐标数值"+zzb);
			 return hzb+"@@@"+zzb;
		}catch(Exception e ){
			e.printStackTrace();
			return "error";
		}
	}
	
	public List createSql(String sql) {
		try{
			Session s=this.getOracleCurrentSession();
			SQLQuery sq=s.createSQLQuery(sql);
			List l=sq.list();
			List result = new ArrayList();
			for(int i=0;i<l.size();i++){
				System.out.println(l.get(i).getClass()+"：第"+i);
				if(l.get(i).getClass()!=Object[].class){
					if(l.get(i)==null){
						result.add("null");
						continue;
					}
					result.add(l.get(i));
					continue;
				}
				Object[] sz=(Object[]) l.get(i);
				List ls1 = new ArrayList();
				for(int j=0;j<sz.length;j++){
					/*if(sz[j]==null)
						sz[j] = "";
					if(sz[j].getClass()==BigDecimal.class)
						sz[j] = ((BigDecimal) sz[j]).doubleValue();*/
					ls1.add(sz[j]);
				}
				result.add(ls1);
			}
			return result;
		}catch(Exception e ){
			e.printStackTrace();
			return null;
		}
	}
}
