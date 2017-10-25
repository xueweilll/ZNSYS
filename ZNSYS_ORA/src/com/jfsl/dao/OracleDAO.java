package com.jfsl.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.jfsl.pojo.DataDictionary;
import com.jfsl.servlet.Conn;


public class OracleDAO extends DAOImp
{
	 
	public void test(){
		
		System.out.println("test");
		try{
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select * from SYZX_AJHF03");
		List l=sq.list();
		System.out.println("oracleDAO test().."+l.size());}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//jdbc��ȡprocess
	public List jsonProcess() throws SQLException{
		try{
		Conn c=new Conn();
		String sql="select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from SYZX_AJHF03 where jcxm in (select distinct jcxm from syzx_sy_zgc) and ypph in (select distinct ypph from syzx_sy_zgc)";
		ResultSet rs=c.getResulset(sql);
		List l=new ArrayList();
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
			String JYR=rs.getString("JYR");
			if(rs.wasNull())
				o[5]="n";
			else
				o[5]="y";
			String JCR=rs.getString("JCR");
			if(rs.wasNull())
				o[6]="n";
			else
				o[6]="y";
			String JHR=rs.getString("JHR");
			if(rs.wasNull())
				o[7]="n";
			else
				o[7]="y";
			String SPR=rs.getString("SPR");
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
		System.out.println("jdbc���ɵ�list"+l.size());
		return l;}
		catch(Exception e){
		  e.printStackTrace();
		  return null;
		}
		 
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> jsonprocess(){
		SQLQuery sq=this.getOracleCurrentSession().createSQLQuery("select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from SYZX_AJHF03");
		List<Object> l=sq.list();
		System.out.println("��AJH003��ȡ������Ϣ"+l.size());
		return l;
	}
	//ͨ��area��ѯprocess
	
	//jdbc��ȡprocess
	public List jsonProcessByArea(String area) throws SQLException{
		Conn c=new Conn();
		String sql="select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from SYZX_AJHF03 where DQ='"+area+"'";
		ResultSet rs=c.getResulset(sql);
		List l=new ArrayList();
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
			String JYR=rs.getString("JYR");
			if(rs.wasNull())
				o[5]="n";
			else
				o[5]="y";
			String JCR=rs.getString("JCR");
			if(rs.wasNull())
				o[6]="n";
			else
				o[6]="y";
			String JHR=rs.getString("JHR");
			if(rs.wasNull())
				o[7]="n";
			else
				o[7]="y";
			String SPR=rs.getString("SPR");
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
		System.out.println("jdbcͨ��area���ɵ�list"+l.size());
		return l;
		 
	}
	
	
	//jdbc��ȡprocessByWell
	public List jsonProcessByWell(String well) throws SQLException{
		Conn c=new Conn();
		String sql="select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from SYZX_AJHF03 where JH='"+well+"'";
		ResultSet rs=c.getResulset(sql);
		List l=new ArrayList();
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
			String JYR=rs.getString("JYR");
			if(rs.wasNull())
				o[5]="n";
			else
				o[5]="y";
			String JCR=rs.getString("JCR");
			if(rs.wasNull())
				o[6]="n";
			else
				o[6]="y";
			String JHR=rs.getString("JHR");
			if(rs.wasNull())
				o[7]="n";
			else
				o[7]="y";
			String SPR=rs.getString("SPR");
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
		System.out.println("jdbcͨ��well���ɵ�list"+l.size());
		return l;
		 
	}

	public List jsonProcessByPh(String ph) throws SQLException {
		Conn c=new Conn();
		String sql="select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from SYZX_AJHF03 where YPPH='"+ph+"'";
		ResultSet rs=c.getResulset(sql);
		List l=new ArrayList();
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
			String JYR=rs.getString("JYR");
			if(rs.wasNull())
				o[5]="n";
			else
				o[5]="y";
			String JCR=rs.getString("JCR");
			if(rs.wasNull())
				o[6]="n";
			else
				o[6]="y";
			String JHR=rs.getString("JHR");
			if(rs.wasNull())
				o[7]="n";
			else
				o[7]="y";
			String SPR=rs.getString("SPR");
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
		System.out.println("jdbcͨ��ph���ɵ�list"+l.size());
		return l;
		 
	}

	public List jsonProcessByJcxm(String jcxm) throws SQLException {
		Conn c=new Conn();
		String sql="select DQ,JH,JCXM,YPPH,YPSL,JYR,JCR,JHR,SPR,BGJSF,SCF from SYZX_AJHF03 where JCXM='"+jcxm+"'";
		ResultSet rs=c.getResulset(sql);
		List l=new ArrayList();
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
			String JYR=rs.getString("JYR");
			if(rs.wasNull())
				o[5]="n";
			else
				o[5]="y";
			String JCR=rs.getString("JCR");
			if(rs.wasNull())
				o[6]="n";
			else
				o[6]="y";
			String JHR=rs.getString("JHR");
			if(rs.wasNull())
				o[7]="n";
			else
				o[7]="y";
			String SPR=rs.getString("SPR");
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
		System.out.println("jdbcͨ��ph���ɵ�list"+l.size());
		return l;
		 
	}
}
