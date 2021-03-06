package com.jfsl.dao;

/**time package*/
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**java bean*/
import com.jfsl.pojo.DataDictionary;

public class DataDictionaryDAO extends DAOImp
{
 
//    @SuppressWarnings("unchecked")
//	public List<DataDictionary> ListAll(){
//    	Query q=this.getCurrentSession().createQuery("from DataDictionary");
//    	List<DataDictionary> list=(List<DataDictionary>)q.list();
//    	return list;
//    }
	public void createTable(String sql,DataDictionary[] dics){
		System.out.println("creat dics dao");
		try{
		Session session=this.getOracleCurrentSession();
		Transaction t=session.beginTransaction();
		for(int i=0;i<dics.length;i++){
			session.save(dics[i]);
		}
		Query q=session.createSQLQuery(sql);
		q.executeUpdate();
		t.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void updateTable(String tablename,String addNewTableSql,DataDictionary[] dics){
		try{
		System.out.println("update table.....................");
		Session session=this.getCurrentSession();
		Transaction t=session.beginTransaction();
		String deleteTableStr="drop table "+tablename;
		session.createSQLQuery(deleteTableStr).executeUpdate();//删除原来的表
		String deleteDicStr="delete from SYZX_SJZD where SJBMC='"+tablename+"'";
		session.createSQLQuery(deleteDicStr).executeUpdate();//删除原来的数据字典的列
		session.createSQLQuery(addNewTableSql).executeUpdate();//创建新的表
		for(int i=0;i<dics.length;i++)
			session.save(dics[i]);
		t.commit();
		}
		catch(Exception e){
			
			e.printStackTrace();
			
		}
	}
	 
	/**check id*/
	public DataDictionary Find(int id)
	{
		Query query=this.getCurrentSession().createQuery("select d from DataDictionary as d where d.id=:id");
		query.setInteger("id", id);
		DataDictionary ls=(DataDictionary)query.list().get(0);
		return ls;
	}
	
	/**check TableName*/
	public DataDictionary Find(String tablename)
	{
		System.out.println("daofind"+tablename);
		if(tablename == "") return null;
		Query query=this.getCurrentSession().createQuery("from DataDictionary where tableName='"+tablename+"'");
		List<DataDictionary> l=(List<DataDictionary>)query.list();
		System.out.println("querysize:"+l.size());
		DataDictionary ls=l.get(0);
		 
		
		return ls;
	}
	
	public void deleteDatadictionary(String tableName){
		try{
			Session s=this.getCurrentSession();
			Transaction t=s.beginTransaction();
		    //s.createSQLQuery("drop table "+tableName).executeUpdate();
		    //s.createSQLQuery("delete from SYZX_SJZD where SJBMC=' "+tableName+"'").executeUpdate();
			t.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getDataBySCGC(String ypph,String jcxm,String scgc) {
		//System.out.println("通过样品批号，检测项目，生产过程得到具体数据流程的表，表的结构，表的数据-------------------");
		 Session s=this.getOracleCurrentSession();
		 SQLQuery sq=s.createSQLQuery("select NAME_TABLE from SYZX_SY_SCGC where SCGC='"+scgc+"'");
		 List l=sq.list();
		
		 //System.out.println(l.get(0));
		 String temp=(String) l.get(0);
		 String tablename = temp;
		 if(temp.indexOf("\\") != -1){
			 int i = temp.indexOf("\\");
			 tablename = temp.substring(0,i-1);
		 }
		// System.out.println("表是："+tablename);
		// System.out.println("从数据字典表通过数据表名称，查找该表"+tablename+"的表的结构---");
		 SQLQuery sq2=s.createSQLQuery("select PYDM from SYZX_SJZD where SJBMC='"+tablename+"'");
		 List l2=sq2.list();
		 //1表检测不到数据
		 if(l2.size()==0){
			 if(temp.indexOf("\\") != -1){
				 int i = temp.indexOf("\\");
				 tablename = temp.substring(i+1,temp.length());
			 }
			 sq2=s.createSQLQuery("select PYDM from SYZX_SJZD where SJBMC='"+tablename+"'");
			 l2=sq2.list();
		 }
		 //System.out.println("从数据字典中得到表名对应的字段list"+l2.size());
		 String[] zd=new String[l2.size()];
		 for(int i=0;i<l2.size();i++){
			 String zdm=(String) l2.get(i);
			 zd[i]=zdm;
		 }
		 //以上得到字段名 和表名zd  tablename
		 //测试
//		 for(int i=0;i<zd.length;i++){
//			 System.out.println(zd[i]+"  ");
//		 }
		// System.out.println("");
		// System.out.println("查找该标的具体数据");
		 //测试
		 String datasql="select ";
		 for(int i=0;i<zd.length;i++){
			 datasql+=zd[i]+",";
		 }
		 datasql=datasql.substring(0,datasql.length()-1);
		 datasql+=" from "+tablename+" where YPPH='"+ypph+"' and JCXM='"+jcxm+"'";
		// System.out.println("查询具体表的sql："+datasql);
		 SQLQuery sq3=s.createSQLQuery(datasql);
		 List l3=sq3.list();
		 //System.out.println("通过ypph jcxm scgc查到具体表的数据"+l3.size());
		 JSONObject jo=new JSONObject();
		 jo.put("total",l3.size());
		 JSONArray ja=new JSONArray();
		 try{
			 for(int j=0;j<l3.size();j++){
				 JSONObject jjo=new JSONObject();
				 Object[] o=new Object[zd.length];
				 o=(Object[]) l3.get(j);
				 for(int z=0;z<zd.length;z++){
					 if(o[z] instanceof java.util.Date){
						 //System.out.println("date");
						 String time = new java.text.SimpleDateFormat("yyy-MM-dd").format(o[z]);
						 jjo.put(zd[z],time);
						 //System.out.println(zd[z]+":"+time);
					 }
					 else{
						 jjo.put(zd[z],o[z]);
						 //System.out.println(zd[z]+":"+o[z]);
					 }
				 }
				 ja.add(jjo);
			 }
			 jo.put("rows",ja);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 String Tojson=jo.toString();
		 SQLQuery sq4=s.createSQLQuery("select PYDM,SJXMC from SYZX_SJZD where SJBMC='"+tablename+"'");
		 List l4=sq4.list();
		
		 
		 //JSONObject jsonjo=new JSONObject();
		 //jsonjo.put("total",l4.size());
		 JSONArray jsonja=new JSONArray();
		 for(int j=0;j<l4.size();j++){
			 JSONObject jjo=new JSONObject();
			 Object[] o=new Object[zd.length];
			 o=(Object[])l4.get(j);
			 jjo.put("zdm",o[0]);
			 jjo.put("memo",o[1]);
			 jsonja.add(jjo);
		 }
		 //jsonjo.put("rows",jsonja);
		 String jsonzdm=jsonja.toString();
		 String zjson=Tojson+"~"+jsonzdm;
		 //System.out.println(zjson);
		 return zjson;
	}
	 public String jsonTable(){
		 try{
			//System.out.println("json table.....................");
			Session session=this.getOracleCurrentSession();
			Transaction t=session.beginTransaction();
			String sql="select distinct SJBMC,SJBHY from SYZX_SJZD";
			Query q=session.createSQLQuery(sql);
			List l=q.list();
			//System.out.println("careate table...sql ..................");
			t.commit();
			JSONArray jsonja=new JSONArray();
			if(l.size()!=0){
				for(int j=0;j<l.size();j++){
					JSONObject jjo=new JSONObject();
					Object[] o=new Object[2];
					o=(Object[])l.get(j);
					jjo.put("sjbmc",o[0]);
					jjo.put("sjbhy",o[1]);
					jsonja.add(jjo);
				}
				String json=jsonja.toString();
				return json;
			}
			else return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	 }
	 
	 public String jsonColumn(String sjbmc){
		 try{
				System.out.println("json column by sjbmc.....................");
				Session session=this.getOracleCurrentSession();
				Transaction t=session.beginTransaction();
				String sql="select PYDM,SJXMC from SYZX_SJZD where SJBMC='"+sjbmc+"'";
				Query q=session.createSQLQuery(sql);
				List l=q.list();
				//System.out.println("careate table...sql ..................");
				t.commit();
				 JSONArray jsonja=new JSONArray();
				 if(l.size()!=0){
				 for(int j=0;j<l.size();j++){
					 JSONObject jjo=new JSONObject();
					 Object[] o=new Object[2];
					 o=(Object[])l.get(j);
					 jjo.put("sjxmc",o[0]);
					 jjo.put("sjxhy",o[1]);
					 jsonja.add(jjo);
				 }
				 String json=jsonja.toString();
				  return json;}
				 else return null;
				}
				catch(Exception e){
					e.printStackTrace();
					return null;
				}
	 }

	public String list() {
		System.out.println("datagrid json jjjjj");
		try{
			Session s=this.getOracleCurrentSession();
			SQLQuery sq=s.createSQLQuery("select distinct SJBMC,SJBHY from SYZX_SJZD ");
			List l=sq.list();
			//System.out.println(l.size());
			JSONObject jo=new JSONObject();
			jo.put("total",l.size());
			JSONArray ja=new JSONArray();
			for(int i=0;i<l.size();i++){
				JSONObject joo=new JSONObject();
				Object[] o=(Object[]) l.get(i);
				String name=(String) o[0];
				String mean=(String) o[1];
				joo.put("sjbmc",name);
				joo.put("jtck", name);
				joo.put("sjbhy",mean);
				ja.add(joo);
			}
			jo.put("rows",ja);
			return jo.toString();
		}
		catch(Exception e){
			
			e.printStackTrace();
			return null;
		}
		 
		
	}

//	public String jsonbysjbmc(String sjbmc) {
//		System.out.println("jsonbysjbmc");
//		try{
//			Session s=this.getOracleCurrentSession();
//			SQLQuery sq=s.createSQLQuery("select * from SYZX_SJZD where SJBMC='"+sjbmc+"'");
//			List l=sq.list();
//			System.out.println(l.size());
//			JSONObject jo=new JSONObject();
//			jo.put("totle",l.size());
//			JSONArray ja=new JSONArray();
//			for(int i=0;i<l.size();i++){
//				JSONObject joo=new JSONObject();
//				Object[] o=(Object[]) l.get(i);
//				String SJBMC=(String) o[0];
//				String PYDM=(String) o[1];
//				BigDecimal XH=(BigDecimal) o[2];
//				String SJXMC=(String) o[3];
//				String LX=(String) o[4];
//				String KD=(String) o[5];
//				String XSWS=(String) o[6];
//				String JLDW=(String) o[7];
//				String PK=(String) o[8];
//				String AK=(String) o[9];
//				String CZB=(String) o[10];
//				String FKZ=(String) o[11];
//				String ZYS=(String) o[12];
//				String TXGD=(String) o[13];
//				String SYBHY=(String) o[14];
//				joo.put("SJBMC",SJBMC);
//				joo.put("PYDM", PYDM);
//				joo.put("XH",XH);
//				joo.put("SJXMC",SJXMC);
//				joo.put("LX", LX);
//				joo.put("KD",KD);
//				joo.put("XSWS",XSWS);
//				joo.put("JLDW", JLDW);
//				joo.put("PK",PK);
//				joo.put("AK",AK);
//				joo.put("CZB", CZB);
//				joo.put("FKZ",FKZ);
//				joo.put("ZYS", ZYS);
//				joo.put("TXGD",TXGD);
//				joo.put("SYBHY",SYBHY);
//				ja.add(joo);
//			}
//			jo.put("rows",ja);
//			System.out.println(jo.toString());
//			return jo.toString();
//		}
//		catch(Exception e){
//			
//			e.printStackTrace();
//			return null;
//		}
//		 
//		
//	}
//	通过数据表名称查找
	@SuppressWarnings("unchecked")
	public List<DataDictionary> jsonbysjbmc(String sjbmc) {
	System.out.println("jsonbysjbmc");
	try{
		Session s=this.getOracleCurrentSession();
		SQLQuery sq=s.createSQLQuery("select * from SYZX_SJZD where SJBMC='"+sjbmc+"'").addEntity(DataDictionary.class);
		List<DataDictionary> l=sq.list();
		System.out.println("通过数据表名称查出有多少列...."+l.size());
		return l;
	}
	catch(Exception e){
		e.printStackTrace();
		return null;
	}
	 
	
}

	//添加数据字典
	public void addDic(DataDictionary[] ds){
		Session s=this.getOracleCurrentSession();
		Transaction tran=s.beginTransaction();
		for(int i=0;i<ds.length;i++){
			s.save(ds[i]);
		}
		tran.commit();
	}
}
