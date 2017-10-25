package com.jfsl;


import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import com.jfsl.dao.DAOImp;

public class TongjiDAO extends DAOImp
{
	
	public String sqltj(String sql){
		try{
			Session s=this.getOracleCurrentSession();
			SQLQuery q=s.createSQLQuery(sql);
			List<?> l=q.list();
			if(l.size()==0) {
			} else
				createdata(l);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
		
	}
    
	public String createdata(List<?> l){
    	for(int i=0;i<l.size();i++){
    		
    		
    	}
    	return null;
    	
    }
	
    public String jsonph() {
		try{
		Session s=this.getOracleCurrentSession();
		Query q=s.createSQLQuery("select YPPH,count(YPPH) as num from AJHD01 group by YPPH");
		List<?> l=q.list();
		String hzb="[";
		String sz="[";
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
		Query q=s.createSQLQuery("select YPPH,count(YPPH) as num from AJHD01 group by YPPH");
        List<?> l=q.list();
        Query all=s.createSQLQuery("select YPPH,count(YPPH) from AJHF03 group by YPPH,YSC having YSC=-1");
        List<?> al=all.list();
        String[] hzb=new String[l.size()];
		String sz="[";
        for(int i=0;i<l.size();i++){
			Object[] o=(Object[]) l.get(i);
			hzb[i]=(String) o[0];
			sz+=o[1]+",";
		}
		sz=sz.substring(0,sz.length()-1);
		sz+="]";
		//锟斤拷锟斤拷锟斤拷
		BigDecimal[] wcsz=new BigDecimal[l.size()];
		for(int i=0;i<al.size();i++){
			Object[] o=(Object[]) al.get(i);
			for(int j=0;j<hzb.length;j++){
				if(o[0].equals(hzb[j]))
					wcsz[j]=(BigDecimal) o[1];
			}
		}
		//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷转锟斤拷为锟街凤拷
		String wcqksz="[";
		for(int i=0;i<wcsz.length;i++){
			wcqksz+=wcsz[i]+",";
		}
		wcqksz=wcqksz.substring(0,wcqksz.length()-1);
		wcqksz+="]";
		//锟斤拷锟斤拷锟斤拷锟阶拷锟轿拷址锟�
		String hzbstr="[";
		for(int i=0;i<hzb.length;i++){
			hzbstr+="'"+hzb[i]+"',";
		}
		hzbstr=hzbstr.substring(0,hzbstr.length()-1);
		hzbstr+="]";
		//System.out.println("锟斤拷锟斤拷锟斤拷锟街�+hzbstr);
		String returnstr=hzbstr+"@@@"+sz+"@@@"+wcqksz;
		System.out.println(returnstr);
		return returnstr;}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String jsonsql(String sql) {
		try{
			Session s=this.getOracleCurrentSession();
			SQLQuery sq=s.createSQLQuery(sql);
			List<?> l=sq.list();
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
			//System.out.println("锟斤拷锟斤拷锟斤拷锟街�+hzb);
			zzb=zzb.substring(0,zzb.length()-1);
			zzb+="]";
			//System.out.println("锟斤拷锟斤拷锟斤拷锟街�+zzb);
			 return hzb+"@@@"+zzb;
		}catch(Exception e ){
			e.printStackTrace();
			return "error";
		}
	}
	
	public List<?> createSql(String sql) {
		try{
			Session s=this.getOracleCurrentSession();
			SQLQuery sq=s.createSQLQuery(sql);
			List<?> l=sq.list();
			return l;
		}catch(Exception e ){
			e.printStackTrace();
			return null;
		}
	}

}
