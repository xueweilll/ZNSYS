package com.jfsl.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.jfsl.servlet.Conn;


public class OracleDAO extends DAOImp
{
	public void test(){
		
		System.out.println("test");
		try{
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select * from AJHF03");
		List<?> l=sq.list();
		System.out.println("oracleDAO test().."+l.size());}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int jsonProcessCount(String strWhere) throws SQLException{
		try{
		Conn c=new Conn();
		String sql="select count(*) as total from AJHF03  a inner join (select distinct jcxm, ypph from sy_zgc)  s on s.jcxm = a.jcxm and s.ypph = a.ypph and yqwcrq > to_date('2015/01/01','YYYY-MM-DD') "+strWhere+" order by a.YPPH desc";
		ResultSet rs=c.getResulset(sql);
		rs.next();
		int l = rs.getInt("total");
		return l;}
		catch(Exception e){
		  e.printStackTrace();
		  return 0;
		}
		 
	}
	
	
	//jdbc获取process
	public List<Object> jsonProcess(int start,int rows, String strWhere) throws SQLException{
		try{
		Conn c=new Conn();
		//String sql="select a.DQ,a.JH,a.JCXM,a.YPPH,a.YPSL,a.JYR,a.JCR,a.JHR,a.SPR,a.BGJSF,a.SCF,a.SYRQ,a.JYRQ,a.FXRQ,a.JHRQ,a.SPRQ,a.GDRQ,a.SCRQ  from AJHF03  a inner join (select distinct jcxm, ypph from sy_zgc)  s on s.jcxm = a.jcxm and s.ypph = a.ypph and yqwcrq > to_date('2015/01/01','YYYY-MM-DD')";
		String sql = "SELECT a.* FROM (select a.DQ,a.JH,a.JCXM,a.YPPH,a.YPSL,a.JYR,a.JCR,a.JHR,a.SPR,a.BGJSF,a.SCF,a.SYRQ,a.JYRQ,a.FXRQ,a.JHRQ,a.SPRQ,a.GDRQ,a.SCRQ,ROWNUM rn from AJHF03  a inner join (select distinct jcxm, ypph from sy_zgc)  s on s.jcxm = a.jcxm and s.ypph = a.ypph and yqwcrq > to_date('2015/01/01','YYYY-MM-DD') "+strWhere+" order by a.YPPH desc)a WHERE rn BETWEEN "+start+" AND "+rows;
		ResultSet rs=c.getResulset(sql);
		List<Object> l=new ArrayList<Object>();
		while(rs.next()){
			Object[] o=new Object[12];
			o[0]=rs.getString("DQ");
			o[1]=rs.getString("JH");
			o[2]=rs.getString("JCXM");
			o[3]=rs.getString("YPPH");
			String SYRQ=rs.getString("SYRQ");
			if(SYRQ == null || SYRQ.equals("")){
				o[5]="n";
			}
			else{
				o[5]=StringPattern(SYRQ,"yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss");
			}
			String JYRQ=rs.getString("JYRQ");
			if(JYRQ == null || JYRQ.equals(""))
				o[6]="n";
			else
				o[6]=StringPattern(JYRQ,"yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss");
			String FXRQ=rs.getString("FXRQ");
			if(FXRQ == null || FXRQ.equals(""))
				o[7]="n";
			else
				o[7]=StringPattern(FXRQ,"yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss");
			String JHRQ=rs.getString("JHRQ");
			if(JHRQ == null || JHRQ.equals(""))
				o[8]="n";
			else
				o[8]=StringPattern(JHRQ,"yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss");
			String SPRQ=rs.getString("SPRQ");
			if(SPRQ == null || SPRQ.equals(""))
				o[9]="n";
			else
				o[9]=StringPattern(SPRQ,"yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss");
			String GDRQ=rs.getString("GDRQ");
			if(GDRQ == null || GDRQ.equals(""))
				o[10]="n";
			else
				o[10]=StringPattern(GDRQ,"yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss");
			String SCRQ=rs.getString("SCRQ");
			if(SCRQ == null || SCRQ.equals(""))
				o[11]="n";
			else
				o[11]=StringPattern(SCRQ,"yyyy-MM-dd HH:mm:ss.SSS","yyyy-MM-dd HH:mm:ss");
			BigDecimal YPSL=rs.getBigDecimal("YPSL");
			o[4]=YPSL;
			l.add(o);
		}
		//System.out.println("jdbc生成的list"+l.size());
		return l;}
		catch(Exception e){
		  e.printStackTrace();
		  return null;
		}
		 
	}
	
	 public String StringPattern(String date, String oldPattern, String newPattern) {   
	        if (date == null || oldPattern == null || newPattern == null)   
	            return "";   
	        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象    
	        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // 实例化模板对象    
	        Date d = null ;    
	        try{    
	            d = sdf1.parse(date) ;   // 将给定的字符串中的日期提取出来    
	        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理    
	            e.printStackTrace() ;       // 打印异常信息    
	        }    
	        return sdf2.format(d);  
	    }   
	
	@SuppressWarnings("unchecked")
	public List<Object> jsonprocess(){
		SQLQuery sq=this.getOracleCurrentSession().createSQLQuery("select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from AJHF03");
		List<Object> l=sq.list();
		//System.out.println("从AJH003获取流程信息"+l.size());
		return l;
	}
	//通过area查询process
	
	//jdbc获取process
	public List<Object> jsonProcessByArea(String area) throws SQLException{
		Conn c=new Conn();
		String sql="select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from AJHF03 where DQ='"+area+"'";
		ResultSet rs=c.getResulset(sql);
		List<Object> l=new ArrayList<Object>();
		while(rs.next()){
			Object[] o=new Object[11];
			String DQ=rs.getString("DQ");
			o[0]=DQ;
			String JH=rs.getString("JH");
			o[1]=JH;
			String JCXM=rs.getString("JCXM");
			o[2]=JCXM;
			String YPPH=rs.getString("YPPH");
			o[3]=YPPH;
			//String JYR=rs.getString("JYR");
			if(rs.wasNull())
				o[5]="n";
			else
				o[5]="y";
			//String JCR=rs.getString("JCR");
			if(rs.wasNull())
				o[6]="n";
			else
				o[6]="y";
			//String JHR=rs.getString("JHR");
			if(rs.wasNull())
				o[7]="n";
			else
				o[7]="y";
			//String SPR=rs.getString("SPR");
			if(rs.wasNull())
				o[8]="n";
			else
				o[8]="y";
			String BGJSF=rs.getString("BGJSF");
			o[9]=BGJSF;
			String SCF=rs.getString("SCF");
			o[10]=SCF;
			BigDecimal YPSL=rs.getBigDecimal("YPSL");
			o[4]=YPSL;
			l.add(o);
		}
		System.out.println("jdbc通过area生成的list"+l.size());
		return l;
		 
	}
	
	
	//jdbc获取processByWell
	public List<Object> jsonProcessByWell(String well) throws SQLException{
		Conn c=new Conn();
		String sql="select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from AJHF03 where JH='"+well+"'";
		ResultSet rs=c.getResulset(sql);
		List<Object> l=new ArrayList<Object>();
		while(rs.next()){
			Object[] o=new Object[11];
			String DQ=rs.getString("DQ");
			o[0]=DQ;
			String JH=rs.getString("JH");
			o[1]=JH;
			String JCXM=rs.getString("JCXM");
			o[2]=JCXM;
			String YPPH=rs.getString("YPPH");
			o[3]=YPPH;
			//String JYR=rs.getString("JYR");
			if(rs.wasNull())
				o[5]="n";
			else
				o[5]="y";
			//String JCR=rs.getString("JCR");
			if(rs.wasNull())
				o[6]="n";
			else
				o[6]="y";
			//String JHR=rs.getString("JHR");
			if(rs.wasNull())
				o[7]="n";
			else
				o[7]="y";
			//String SPR=rs.getString("SPR");
			if(rs.wasNull())
				o[8]="n";
			else
				o[8]="y";
			String BGJSF=rs.getString("BGJSF");
			o[9]=BGJSF;
			String SCF=rs.getString("SCF");
			o[10]=SCF;
			BigDecimal YPSL=rs.getBigDecimal("YPSL");
			o[4]=YPSL;
			l.add(o);
		}
		System.out.println("jdbc通过well生成的list"+l.size());
		return l;
		 
	}

	public List<Object> jsonProcessByPh(String ph) throws SQLException {
		Conn c=new Conn();
		String sql="select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from AJHF03 where YPPH='"+ph+"'";
		ResultSet rs=c.getResulset(sql);
		List<Object> l=new ArrayList<Object>();
		while(rs.next()){
			Object[] o=new Object[11];
			String DQ=rs.getString("DQ");
			o[0]=DQ;
			String JH=rs.getString("JH");
			o[1]=JH;
			String JCXM=rs.getString("JCXM");
			o[2]=JCXM;
			String YPPH=rs.getString("YPPH");
			o[3]=YPPH;
			//String JYR=rs.getString("JYR");
			if(rs.wasNull())
				o[5]="n";
			else
				o[5]="y";
			//String JCR=rs.getString("JCR");
			if(rs.wasNull())
				o[6]="n";
			else
				o[6]="y";
			//String JHR=rs.getString("JHR");
			if(rs.wasNull())
				o[7]="n";
			else
				o[7]="y";
			//String SPR=rs.getString("SPR");
			if(rs.wasNull())
				o[8]="n";
			else
				o[8]="y";
			String BGJSF=rs.getString("BGJSF");
			o[9]=BGJSF;
			String SCF=rs.getString("SCF");
			o[10]=SCF;
			BigDecimal YPSL=rs.getBigDecimal("YPSL");
			o[4]=YPSL;
			l.add(o);
		}
		System.out.println("jdbc通过ph生成的list"+l.size());
		return l;
		 
	}

	public List<Object> jsonProcessByJcxm(String jcxm) throws SQLException {
		Conn c=new Conn();
		String sql="select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from AJHF03 where JCXM='"+jcxm+"'";
		ResultSet rs=c.getResulset(sql);
		List<Object> l=new ArrayList<Object>();
		while(rs.next()){
			Object[] o=new Object[11];
			String DQ=rs.getString("DQ");
			o[0]=DQ;
			String JH=rs.getString("JH");
			o[1]=JH;
			String JCXM=rs.getString("JCXM");
			o[2]=JCXM;
			String YPPH=rs.getString("YPPH");
			o[3]=YPPH;
			//String JYR=rs.getString("JYR");
			if(rs.wasNull())
				o[5]="n";
			else
				o[5]="y";
			//String JCR=rs.getString("JCR");
			if(rs.wasNull())
				o[6]="n";
			else
				o[6]="y";
			//String JHR=rs.getString("JHR");
			if(rs.wasNull())
				o[7]="n";
			else
				o[7]="y";
			//String SPR=rs.getString("SPR");
			if(rs.wasNull())
				o[8]="n";
			else
				o[8]="y";
			String BGJSF=rs.getString("BGJSF");
			o[9]=BGJSF;
			String SCF=rs.getString("SCF");
			o[10]=SCF;
			BigDecimal YPSL=rs.getBigDecimal("YPSL");
			o[4]=YPSL;
			l.add(o);
		}
		System.out.println("jdbc通过ph生成的list"+l.size());
		return l;
		 
	}
}
