package com.jfsl.dao;

import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.jfsl.pojo.Function;

public class ProduceDAO extends DAOImp
{
	@SuppressWarnings("unchecked")
	public List<Function> ListAll()
	{
		Session session=this.getOracleCurrentSession();
		Query query=session.createQuery("from Function f order by f.id");
		List<Function> ls=(List<Function>)query.list();
		return ls;
	}
	
	public Function Find(String id)
	{
		Session session=this.getOracleCurrentSession();
		Function f=(Function)session.load(Function.class, id);
		return f;
	}
	
	@SuppressWarnings("unchecked")
	public List<Function> ListExFunction(String username)
	{
		String hqlstr="from Function f where f.formurl is not null and f.id not in";
		hqlstr=hqlstr+" (select uo.function.id from UserOperation as uo where uo.username=:username)";
		hqlstr=hqlstr+" order by f.id";
		Session session=this.getOracleCurrentSession();
		Query query=session.createQuery(hqlstr);
		query.setString("username",username);
		List<Function> ls=(List<Function>)query.list();
		String ids="";
		String id;
		for(Function f:ls)
		{
			id=f.getId();
			while (id.length()>3)
			{
				ids=ids+"'"+(id.substring(0,id.length()-3))+"',";
				id=id.substring(0,id.length()-3);
			}
		}
		ids=ids.substring(0,ids.length()-1);
		hqlstr="select distinct f from Function f where f.id in("+ids+")";
		query=session.createQuery(hqlstr);
		List<Function> ls1=(List<Function>)query.list();
		ls.addAll(ls1);
		
		Collections.sort(ls);
		return ls;
	}
	//���ưٶ�����
	@SuppressWarnings("unchecked")
	public String searchbyprogram(String program){
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select  DISTINCT JCXM from SY_ZGC where JCXM like '%"+program+"%' order by JCXM asc");
		List<Function> l=sq.list();
	//	System.out.println("��Ŀ��������"+l.size());
		if(l.size()==0)
			return "[{\"jcxm\":\"null\"},{\"text\":\"û�����������Ŀ\"}]";
		else{
			JSONArray ja=new JSONArray();
			for(int i=0;i<l.size();i++){
				JSONObject jo=new JSONObject();
			//	System.out.print(l.get(i));
				jo.put("jcxm",l.get(i));
				jo.put("text",l.get(i));
				ja.add(jo);
			}
			System.out.println("��⵽����Ŀ��"+ja.toString());
			return ja.toString();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public String searchcsbyprogram(String program){
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select  DISTINCT CSNAME from SY_ZGC where JCXM like '%"+program+"%'");
		List<Function> l=sq.list();
	//	System.out.println("��Ŀ��������"+l.size());
		if(l.size()==0)
			return "[{\"jcxm\":\"null\"},{\"text\":\"û�����������Ŀ\"}]";
		else{
			JSONArray ja=new JSONArray();
			for(int i=0;i<l.size();i++){
				JSONObject jo=new JSONObject();
			//	System.out.print(l.get(i));
				jo.put("jcxm",l.get(i));
				jo.put("text",l.get(i));
				ja.add(jo);
			}
			System.out.println("��⵽����Ŀ��"+ja.toString());
			return ja.toString();
		}
		
	}
	
	//����area
	@SuppressWarnings("unchecked")
	public String searchAreabyprogram(String area){
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select DISTINCT DQ  from AJHF03 where JCXM like '%"+area+"%' order by DQ asc");
		List<Function> l=sq.list();
		System.out.println("����������"+l.size());
		if(l.size()==0)
			return "[{\"area\":\"null\"},{\"text\":\"û�������������\"}]";
		else{
			JSONArray ja=new JSONArray();
			for(int i=0;i<l.size();i++){
				JSONObject jo=new JSONObject();
				System.out.print(l.get(i));
				jo.put("area",l.get(i));
				jo.put("text",l.get(i));
				ja.add(jo);
			}
			System.out.println("��⵽�ĵ�����"+ja.toString());
			return ja.toString();
		}
		
		
	}
	//����well
	@SuppressWarnings("unchecked")
	public String searchWellbyprogram(String well){
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select DISTINCT JH from AJHF03 where JCXM like '%"+well+"%' order by JH asc");
		List<Function> l=sq.list();
		System.out.println("���Ÿ�����"+l.size());
		if(l.size()==0)
			return "[{\"well\":\"null\"},{\"text\":\"û�������������\"}]";
		else{
			JSONArray ja=new JSONArray();
			for(int i=0;i<l.size();i++){
				JSONObject jo=new JSONObject();
				System.out.print(l.get(i));
				jo.put("well",l.get(i));
				jo.put("text",l.get(i));
				ja.add(jo);
			}
			System.out.println("��⵽�ľ��ţ�"+ja.toString());
			return ja.toString();
		}
		
		
	}
//����
	public String searchPhbyprogram(String searchPh) {
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select DISTINCT YPPH from AJHF03 where JCXM like '%"+searchPh+"%' order by YPPH desc");
		List<Function> l=sq.list();
		System.out.println("���Ÿ�����"+l.size());
		if(l.size()==0)
			return "[{\"ph\":\"null\"},{\"text\":\"û�������������\"}]";
		else{
			JSONArray ja=new JSONArray();
			for(int i=0;i<l.size();i++){
				JSONObject jo=new JSONObject();
				System.out.print(l.get(i));
				jo.put("ph",l.get(i));
				jo.put("text",l.get(i));
				ja.add(jo);
			}
			System.out.println("��⵽���ţ�"+ja.toString());
			return ja.toString();
		}
	}

	public String searchWellbyArea(String area) {
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select DISTINCT JH from AJHF03 where JCXM='"+area+"'");
		List<Function> l=sq.list();
		System.out.println("���Ÿ�����"+l.size());
		if(l.size()==0)
			return "[{\"well\":\"null\"},{\"text\":\"û�������������\"}]";
		else{
			JSONArray ja=new JSONArray();
			for(int i=0;i<l.size();i++){
				JSONObject jo=new JSONObject();
				System.out.print(l.get(i));
				jo.put("well",l.get(i));
				jo.put("text",l.get(i));
				ja.add(jo);
			}
			System.out.println("��⵽�ľ��ţ�"+ja.toString());
			return ja.toString();
		}
		
	}

	public String searchPhbyWell(String jh) {
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select DISTINCT YPPH from AJHF03 where JH='"+jh+"'");
		List<Function> l=sq.list();
		System.out.println("���Ÿ�����"+l.size());
		if(l.size()==0)
			return "[{\"ph\":\"null\"},{\"text\":\"û�������������\"}]";
		else{
			JSONArray ja=new JSONArray();
			for(int i=0;i<l.size();i++){
				JSONObject jo=new JSONObject();
				System.out.print(l.get(i));
				jo.put("ph",l.get(i));
				jo.put("text",l.get(i));
				ja.add(jo);
			}
			System.out.println("��⵽�����ţ�"+ja.toString());
			return ja.toString();
		}
		
	}

	public String searchProByPh(String ph) {
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select DISTINCT JCXM from SY_ZGC where YPPH='"+ph+"'");
		List<Function> l=sq.list();
		System.out.println("���Ÿ�����"+l.size());
		if(l.size()==0)
			return "[{\"jcxm\":\"null\"},{\"text\":\"û�����������Ŀ\"}]";
		else{
			JSONArray ja=new JSONArray();
			for(int i=0;i<l.size();i++){
				JSONObject jo=new JSONObject();
				System.out.print(l.get(i));
				jo.put("jcxm",l.get(i));
				jo.put("text",l.get(i));
				ja.add(jo);
			}
			System.out.println("��⵽����Ŀ��"+ja.toString());
			return ja.toString();
		}
	}
}
