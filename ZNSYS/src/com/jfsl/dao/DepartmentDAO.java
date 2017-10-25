package com.jfsl.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.Department;

public class DepartmentDAO extends DAOImp {
	
	@SuppressWarnings("unchecked")
	public List ListAll(int belongin)
	{
		String sqlstr="select id,name,showorder,belongin from V_SA_Departments ";
		if (belongin!=0) sqlstr=sqlstr+" where belongin=0 or belongin="+belongin;
		sqlstr=sqlstr+" order by showindex";
		Query query =this.getCurrentSession().createSQLQuery(sqlstr);
		List ls=query.list();
		return ls;
	}
	
	public Department find(String id)
	{
		Department dep=(Department)this.getCurrentSession().get(Department.class, id);
		return dep;
	}
	
	//删除部门id及其下属部门
	public void delete(String id)
	{
		Session session=this.getCurrentSession();
		Transaction tx=null;
		String strHql="delete Department where id like '"+id+"%'";
		Query query =session.createQuery(strHql);
		tx=session.beginTransaction();
		query.executeUpdate();
		tx.commit();
		session.clear();
		
	}
	
	//得到新的下属部门编号
	@SuppressWarnings("unchecked")
	public String GenerateID(String parentid)
	{
		String strHql="select max(d.id) from Department as d where subString(d.id,1,length(d.id)-3)=:parentid";
		Query query=this.getCurrentSession().createQuery(strHql);
		query.setString("parentid", parentid);
		List ls=query.list();
		
		if(ls.size()==0) return parentid+"001";
		else{
			String maxid=(String)ls.get(0);
			if(maxid==null) return parentid+"001";
			else{
				int nextid=Integer.parseInt(maxid.substring(maxid.length()-3))+1;
				DecimalFormat df=new DecimalFormat("000");
				return parentid+df.format(nextid);
			}			
		}		
	}
	
	public String getDepartmentNames(String ids)
	{
		if ((ids==null)||(ids.equals(""))) return "";
		String[] idarray=ids.split(",");
		String result="";
		Department d;
		for(String id:idarray)
		{
			d=find(id);
			if (d!=null)
				result=result+d.getName()+",";
		}
		if (result.equals("")) 
			return "";
		else 
			return result.substring(0,result.length()-1);
	}
	
	@SuppressWarnings("unchecked")
	public List ListScopesDepartment(String[] datascopes, int belongin)
	{
		String[] scopes=this.getScopes(datascopes, belongin);
		List ls=this.ListAll(belongin);
		List dells=new ArrayList();
		Object[] ob;
		try
		{
			for(int i=0;i<ls.size();i++)
			{
				ob=(Object[])ls.get(i);
				if(Arrays.binarySearch(scopes,(String)ob[0])<0)
				{
					dells.add(ob);
				}
			}
			ls.removeAll(dells);
		}catch(Exception e){
		}
		return ls;
	}
	
	public String[] getScopes(String[] datascopes, int belongin)
	{
		String[] pids=this.getParentScopes(datascopes);
		String[] subids=this.getSubScopes(datascopes,belongin);
		String[] scopes=new String[pids.length+datascopes.length+subids.length];
		if(pids.length!=0)	System.arraycopy(pids, 0, scopes, 0, pids.length);
		if(datascopes.length!=0)	System.arraycopy(datascopes, 0, scopes, pids.length, datascopes.length);
		if(subids.length!=0)	System.arraycopy(subids, 0, scopes, pids.length+datascopes.length, subids.length);
		Arrays.sort(scopes);
		return scopes;
	}
	
	public String[] getParentScopes(String[] datascopes)
	{
		ArrayList<String> al=new ArrayList<String>();		
		String temp;
		for(int i=0;i<datascopes.length;i++)
		{
			for(int j=datascopes[i].length();j>3;j=j-3)
			{
				temp=datascopes[i].substring(0, j-3);
				if(al.indexOf(temp)<0){
					al.add(temp);
				}else break;
			}
		}
		String[] pids = new String[al.size()];
		al.toArray(pids);
		Arrays.sort(pids);
		return pids;
	}
	
	@SuppressWarnings("unchecked")
	public String[] getSubScopes(String[] datascopes, int belongin)
	{
		List ls=this.ListAll(belongin);
		String[] depids=new String[ls.size()];
		Object[] ob;
		for(int i=0;i<ls.size();i++)
		{
			ob=(Object[])ls.get(i);
			depids[i]=(String)ob[0];
		}
		
		ArrayList<String> al=new ArrayList<String>();
		
		String temp;
		for(int i=0;i<datascopes.length;i++)
		{
			for(int j=0;j<depids.length;j++)
			{
				//截取子串
				if(depids[j].length()>datascopes[i].length()){
					temp=depids[j].substring(0, datascopes[i].length());
					if(temp.equals(datascopes[i]))
						al.add(depids[j]);
				}
			}
		}
		String[] subids = new String[al.size()];
		al.toArray(subids);
		Arrays.sort(subids);
		return subids;
	}
	
	public boolean isValid(String id,int showorder)
	{
		String sqlstr="select id from T_SA_Departments ";
		sqlstr=sqlstr+"where left(id,len(id)-3)='"+id.substring(0,id.length()-3)+"' ";
		sqlstr=sqlstr+"and id!='"+id+"' and showorder="+String.valueOf(showorder);
		this.clearCurrentSession();
		Query query=this.getCurrentSession().createSQLQuery(sqlstr);
		return (query.list().size()==0);
	}
	
	//显示所
	@SuppressWarnings("unchecked")
	public List<Department> ListAllStation(String depid)
	{
		StringBuffer hql=new StringBuffer();
		hql.append("from Department as d where d.belongin=1 and len(d.id)=6 and d.id like '"+depid+"%' order by d.id");
		this.clearCurrentSession();
		Query query =this.getCurrentSession().createQuery(hql.toString());
		List<Department> ls=(List<Department>)query.list();
		return ls;
	}
	
	//用于显示当前登陆用户（例如秦一所001001）所管辖的管理段
	@SuppressWarnings("unchecked")
	public List<Department> ListAllSegment(String depid)
	{
		StringBuffer hql=new StringBuffer();
		hql.append("from Department as d where d.belongin=1 and len(d.id)=9 and d.id like '"+depid+"%' order by d.id");
		this.clearCurrentSession();
		Query query =this.getCurrentSession().createQuery(hql.toString());
		List<Department> ls=(List<Department>)query.list();
		return ls;
	}
		
}
