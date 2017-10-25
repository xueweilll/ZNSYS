package com.jfsl.dao;

/**time package*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;




/**java bean*/
import com.jfsl.pojo.DataDictionary;

public class DataDictionaryDAO extends DAOImp {

	// @SuppressWarnings("unchecked")
	// public List<DataDictionary> ListAll(){
	// Query q=this.getCurrentSession().createQuery("from DataDictionary");
	// List<DataDictionary> list=(List<DataDictionary>)q.list();
	// return list;
	// }
	public void createTable(String sql, DataDictionary[] dics, List<String> commonSql) {
		System.out.println("creat dics dao");
		try {
			Session session = this.getOracleCurrentSession();
			Transaction t = session.beginTransaction();
			for (int i = 0; i < dics.length; i++) {
				session.save(dics[i]);
			}
			Query q = session.createSQLQuery(sql);
			q.executeUpdate();
			for (String string : commonSql) {
				session.createSQLQuery(string).executeUpdate();
			}
			t.commit();
			this.closeOracleCurrentSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateTable(String tablename, String addNewTableSql,
			DataDictionary[] dics, List<String> commonSql) {
		try {
			System.out.println("update table.....................");
			Session session = this.getOracleCurrentSession();
			Transaction t = session.beginTransaction();
			String deleteTableStr = "drop table " + tablename;
			//String deleteTableStr = "if exists(select * from user_tables where table_name = upper('"+tablename+"')) drop table "+tablename;
			session.createSQLQuery(deleteTableStr).executeUpdate();// 删除原来的表
			String deleteDicStr = "delete from SY_SJZD where SJBMC='"
					+ tablename + "'";
			session.createSQLQuery(deleteDicStr).executeUpdate();// 删除原来的数据字典的列
			session.createSQLQuery(addNewTableSql).executeUpdate();// 创建新的表
			for (int i = 0; i < dics.length; i++)
				session.save(dics[i]);
			for (String string : commonSql) {
				session.createSQLQuery(string).executeUpdate();
			}
			t.commit();
			this.closeOracleCurrentSession();
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/** check id */
	public DataDictionary Find(int id) {
		Query query = this.getOracleCurrentSession().createQuery(
				"select d from DataDictionary as d where d.id=:id");
		query.setInteger("id", id);
		DataDictionary ls = (DataDictionary) query.list().get(0);
		this.closeOracleCurrentSession();
		return ls;
	}

	/** check TableName */
	@SuppressWarnings("unchecked")
	public DataDictionary Find(String tablename) {
		System.out.println("daofind" + tablename);
		if (tablename == "")
			return null;
		Query query = this.getOracleCurrentSession().createQuery(
				"from DataDictionary where tableName='" + tablename + "'");
		List<DataDictionary> l = (List<DataDictionary>) query.list();
		System.out.println("querysize:" + l.size());
		DataDictionary ls = l.get(0);
		this.closeOracleCurrentSession();
		return ls;
	}

	public void deleteDatadictionary(String tableName) {
		try {
			// Session s=this.getCurrentSession();
			Session s = this.getOracleCurrentSession();
			Transaction t = s.beginTransaction();
			s.createSQLQuery("drop table " + tableName).executeUpdate();
			s.createSQLQuery(
					"delete from SY_SJZD where SJBMC='" + tableName + "'")
					.executeUpdate();
			t.commit();
			this.closeOracleCurrentSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isSpecial(String scgc){
		String []ss={"速度敏感性","工作液评价","盐度敏感性","水敏敏感性","酸度敏感性","体积敏感性","应力敏感性","碱度敏感性","压裂液评价"};
		boolean result = false;
		for(String s : ss){
			if(s.equals(scgc)){
				result = true;
				break;
			}
		}
		return result;
	}
	
	public String getDataBySCGC(String ypph, String jcxm, String scgc,String csname) {
		// System.out.println("通过样品批号，检测项目，生产过程得到具体数据流程的表，表的结构，表的数据-------------------");
		//
		Session s = this.getOracleCurrentSession();
		//boolean re=isSpecial(scgc);
		String wsql="select DATA_WHERE,NAME_TABLE from SY_SCGC where JCXM ='"+ jcxm + "' and SCGC='" + scgc + "'";
		/*if(re){
			wsql="select DATA_WHERE,NAME_TABLE from SY_SCGC where JCXM ='"+ jcxm + "' and SCGC='" + "实验数据" + "'";
		}else{
			wsql="select DATA_WHERE,NAME_TABLE from SY_SCGC where JCXM ='"+ jcxm + "' and SCGC='" + scgc + "'";
			if(csname != null && csname.length() > 0){
				wsql += " and CSNAME = '"+ csname +"'";
			}
		}		*/
		SQLQuery sq = s.createSQLQuery(wsql);
		List<?> l = sq.list();
		if(l.size() == 0){
			return "2";
		}
		Object[] object = new Object[2];
		object = (Object[]) l.get(0);
		String where = (String) object[0];
		String temp = (String) object[1];
		if (temp == null || temp.equals("") || temp.length() == 0) {
			return "0";
		}
		if(where == null || where.length() ==0 || where.equals("")){
			return "3";
		}
		where = where.toUpperCase();
		temp=temp.toUpperCase();
		String[] tds = temp.split("\\\\");
		String hqlstr = "from DataDictionary as f where";
		if (tds.length == 1) {
			hqlstr += " f.id.SJBMC = '" + tds[0] + "'";
		} else if (tds.length == 2) {
			hqlstr += " f.id.SJBMC = '" + tds[0] + "' or f.id.SJBMC = '"
					+ tds[1] + "'";
		} else {
			for (int k = 0; k < tds.length; k++) {
				if (k == 0 || k == (tds.length - 1)) {
					hqlstr += " f.id.SJBMC = '" + tds[k] + "'";
				} else {
					hqlstr += " or f.id.SJBMC = '" + tds[k] + "'";
				}
			}
		}
		Query query = this.getOracleCurrentSession().createQuery(hqlstr);
		@SuppressWarnings("unchecked")
		List<DataDictionary> lds = query.list();
		if(lds.size() ==0){
			return "1";
		}
	
		System.out.println(lds.size());
		HashMap<String, String> map = new HashMap<String, String>();
		String error="";
		if(where.indexOf("@JCXM") != -1){
			if(jcxm != null && !jcxm.equals("")){
				map.put("@JCXM", jcxm);
			}else {
				error +="JCXM、";
			}
		}
		if(where.indexOf("@YPPH") != -1){
			if(ypph != null && !ypph.equals("")){
				map.put("@YPPH", ypph);
			}else {
				error +="YPPH、";
			}
		}
		if(where.indexOf("@SCGC") != -1){
			if(scgc != null && !scgc.equals("")){
				map.put("@SCGC", scgc);
				/*if(re){
					map.put("@SCGC", "实验数据");
				}else{
					map.put("@SCGC", scgc);
				}*/
			}else {
				error +="SCGC、";
			}
		}
		if(where.indexOf("@CSNAME") != -1){
			if(csname != null && !csname.equals("")){
				map.put("@CSNAME", csname);
			}else {
				error +="CSNAME、";
			}
		}
		if(error.length() > 0){
			error= error.substring(0,error.length()-1);
			error="error_4"+ error;
			return error;
		}
		List<String> zwj = new ArrayList<String>();
		List<String> fkzd = new ArrayList<String>();
		String fields = "";
		String tables = "";
		List<String> fieldList= new ArrayList<String>();
		JSONArray jsm = new JSONArray();
		for (DataDictionary d : lds) {
			JSONObject jjo = new JSONObject();
			jjo.put("zdm", d.getDicpk().getSJBMC() + "_"+ d.getDicpk().getPYDM());
			jjo.put("memo", d.getSJXMC());
			jsm.add(jjo);
			
			fieldList.add(d.getDicpk().getSJBMC() + "_"	+ d.getDicpk().getPYDM());
			fields += " " + d.getDicpk().getSJBMC() + "."+ d.getDicpk().getPYDM() + " as " + d.getDicpk().getSJBMC() + "_"+ d.getDicpk().getPYDM() +",";
			
			if (tds.length >1 && d.getDicpk().getSJBMC().equals(tds[1])) {
				String czb = d.getCZB();
				if (czb != null && czb.length() > 0 ) {
					if(czb.equals(tds[0])){
						zwj.add(d.getDicpk().getPYDM());
						fkzd.add(d.getDicpk().getPYDM());
					}
				}
			}
						
		}
		fields = fields.substring(0, fields.length() - 1);
		if (tds.length == 1) {
			tables = tds[0];
		} else if (tds.length == 2) {
			tables = tds[0] + " left join " + tds[1] + " on ";
			if(zwj.size() == 1){
				tables += tds[0] + "." + zwj.get(0) + " = " + tds[1] + "." + fkzd.get(0) + " ";
			}else {
				for(int k =0; k< zwj.size(); k++){
					if(k == 0 ){
						tables += tds[0] + "." + zwj.get(k) + " = " + tds[1] + "." + fkzd.get(k) + " ";
					}else {
						tables += " and " + tds[0] + "." + zwj.get(k) + " = " + tds[1] + "." +  fkzd.get(k) + " ";
					}
					
				}
			}
		}
		if(where != null && where.length() >0 && !where.equals("")){
			for (String key : map.keySet()) {
				where=where.replace(key, "'"+map.get(key)+"'");
			}
		}else {
			where= "";
		}
		System.out.println("select " + fields + " from " + tables + " where  "+ where);
		SQLQuery squery = s.createSQLQuery("select " + fields + " from " + tables + " where  "+ where);
		List<?> dlist = squery.list();
		
		JSONObject jos = new JSONObject();
		jos.put("total", dlist.size());
		JSONArray jay = new JSONArray();
		try {
			for (int j = 0; j < dlist.size(); j++) {
				JSONObject jjo = new JSONObject();
				Object[] o = new Object[fieldList.size()];
				o = (Object[]) dlist.get(j);
				for (int z = 0; z < fieldList.size(); z++) {
					if (o[z] instanceof java.util.Date) {
						String time = new java.text.SimpleDateFormat("yyy-MM-dd").format(o[z]);
						jjo.put(fieldList.get(z), time);
					} else {
						jjo.put(fieldList.get(z), o[z]);
					}
				}
				jay.add(jjo);
			}
			jos.put("rows", jay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String jsonzdm = jsm.toString();
		String zjson = jos.toString() + "~" + jsonzdm;
		this.closeOracleCurrentSession();
		return zjson;
		
		/*=====================================================================*/
		
		
		
		

		/*String tablename = temp;
		source = source.substring(source.indexOf("WHERE"), source.length() - 1);
		int st = 0;
		if (source.contains("JCXM") && source.contains("YPPH")) {
			st = 0;
		} else if (source.contains("YPPH") && !source.contains("JCXM")) {
			st = 1;
		} else if (!source.contains("YPPH") && source.contains("JCXM")) {
			st = 2;
		}

		if (temp.indexOf("\\") != -1) {
			int i = temp.indexOf("\\");
			tablename = temp.substring(0, i);
		}

		SQLQuery sq2 = s
				.createSQLQuery("select PYDM from SY_SJZD where SJBMC='"
						+ tablename + "'");
		List<String> l2 = sq2.list();
		// 1表检测不到数据
		if (l2.size() == 0) {
			if (temp.indexOf("\\") != -1) {
				int i = temp.indexOf("\\");
				tablename = temp.substring(i + 1, temp.length());
			}
			sq2 = s.createSQLQuery("select PYDM from SY_SJZD where SJBMC='"
					+ tablename + "'");
			l2 = sq2.list();
		}
		// System.out.println("从数据字典中得到表名对应的字段list"+l2.size());
		String[] zd = new String[l2.size()];
		for (int i = 0; i < l2.size(); i++) {
			String zdm = (String) l2.get(i);
			zd[i] = zdm;
		}

		// 测试
		String datasql = "select ";
		for (int i = 0; i < zd.length; i++) {
			datasql += zd[i] + ",";
		}
		datasql = datasql.substring(0, datasql.length() - 1);
		if (st == 0) {
			datasql += " from " + tablename + " where YPPH='" + ypph
					+ "' and JCXM='" + jcxm + "'";
		} else if (st == 1) {
			datasql += " from " + tablename + " where YPPH='" + ypph + "'";
		} else if (st == 2) {
			datasql += " from " + tablename + " where JCXM='" + jcxm + "'";
		}

		System.out.println("查询具体表的sql：" + datasql);
		SQLQuery sq3 = s.createSQLQuery(datasql);
		List<?> l3 = sq3.list();
		JSONObject jo = new JSONObject();
		jo.put("total", l3.size());
		JSONArray ja = new JSONArray();
		try {
			for (int j = 0; j < l3.size(); j++) {
				JSONObject jjo = new JSONObject();
				Object[] o = new Object[zd.length];
				o = (Object[]) l3.get(j);
				for (int z = 0; z < zd.length; z++) {
					if (o[z] instanceof java.util.Date) {
						String time = new java.text.SimpleDateFormat(
								"yyy-MM-dd").format(o[z]);
						jjo.put(zd[z], time);
					} else {
						jjo.put(zd[z], o[z]);
					}
				}
				ja.add(jjo);
			}
			jo.put("rows", ja);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String Tojson = jo.toString();
		SQLQuery sq4 = s
				.createSQLQuery("select PYDM,SJXMC from SY_SJZD where SJBMC='"
						+ tablename + "'");
		List<?> l4 = sq4.list();

		JSONArray jsonja = new JSONArray();
		for (int j = 0; j < l4.size(); j++) {
			JSONObject jjo = new JSONObject();
			Object[] o = new Object[zd.length];
			o = (Object[]) l4.get(j);
			jjo.put("zdm", o[0]);
			jjo.put("memo", o[1]);
			jsonja.add(jjo);
		}
		String jsonzdm = jsonja.toString();
		String zjson = Tojson + "~" + jsonzdm;
		return zjson;*/
	}

	public String jsonTable() {
		try {
			// System.out.println("json table.....................");
			Session session = this.getOracleCurrentSession();
			Transaction t = session.beginTransaction();
			String sql = "select distinct SJBMC,SJBHY from SY_SJZD";
			Query q = session.createSQLQuery(sql);
			List<?> l = q.list();
			// System.out.println("careate table...sql ..................");
			t.commit();
			JSONArray jsonja = new JSONArray();
			if (l.size() != 0) {
				for (int j = 0; j < l.size(); j++) {
					JSONObject jjo = new JSONObject();
					Object[] o = new Object[2];
					o = (Object[]) l.get(j);
					jjo.put("sjbmc", o[0]);
					jjo.put("sjbhy", o[1]);
					jsonja.add(jjo);
				}
				String json = jsonja.toString();
				this.closeOracleCurrentSession();
				return json;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String jsonColumn(String sjbmc) {
		try {
			System.out.println("json column by sjbmc.....................");
			Session session = this.getOracleCurrentSession();
			Transaction t = session.beginTransaction();
			String sql = "select PYDM,SJXMC from SY_SJZD where SJBMC='" + sjbmc
					+ "'";
			Query q = session.createSQLQuery(sql);
			List<?> l = q.list();
			// System.out.println("careate table...sql ..................");
			t.commit();
			JSONArray jsonja = new JSONArray();
			if (l.size() != 0) {
				for (int j = 0; j < l.size(); j++) {
					JSONObject jjo = new JSONObject();
					Object[] o = new Object[2];
					o = (Object[]) l.get(j);
					jjo.put("sjxmc", o[0]);
					jjo.put("sjxhy", o[1]);
					jsonja.add(jjo);
				}
				String json = jsonja.toString();
				this.closeOracleCurrentSession();
				return json;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String list() {
		System.out.println("datagrid json jjjjj");
		try {
			Session s = this.getOracleCurrentSession();
			SQLQuery sq = s
					.createSQLQuery("select distinct SJBMC,SJBHY from SY_SJZD order by SJBMC");
			List<?> l = sq.list();
			// System.out.println(l.size());
			JSONObject jo = new JSONObject();
			jo.put("total", l.size());
			JSONArray ja = new JSONArray();
			for (int i = 0; i < l.size(); i++) {
				JSONObject joo = new JSONObject();
				Object[] o = (Object[]) l.get(i);
				String name = (String) o[0];
				String mean = (String) o[1];
				joo.put("sjbmc", name);
				joo.put("jtck", name);
				joo.put("sjbhy", mean);
				ja.add(joo);
			}
			jo.put("rows", ja);
			this.closeOracleCurrentSession();
			return jo.toString();
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	// public String jsonbysjbmc(String sjbmc) {
	// System.out.println("jsonbysjbmc");
	// try{
	// Session s=this.getOracleCurrentSession();
	// SQLQuery
	// sq=s.createSQLQuery("select * from SY_SJZD where SJBMC='"+sjbmc+"'");
	// List l=sq.list();
	// System.out.println(l.size());
	// JSONObject jo=new JSONObject();
	// jo.put("totle",l.size());
	// JSONArray ja=new JSONArray();
	// for(int i=0;i<l.size();i++){
	// JSONObject joo=new JSONObject();
	// Object[] o=(Object[]) l.get(i);
	// String SJBMC=(String) o[0];
	// String PYDM=(String) o[1];
	// BigDecimal XH=(BigDecimal) o[2];
	// String SJXMC=(String) o[3];
	// String LX=(String) o[4];
	// String KD=(String) o[5];
	// String XSWS=(String) o[6];
	// String JLDW=(String) o[7];
	// String PK=(String) o[8];
	// String AK=(String) o[9];
	// String CZB=(String) o[10];
	// String FKZ=(String) o[11];
	// String ZYS=(String) o[12];
	// String TXGD=(String) o[13];
	// String SYBHY=(String) o[14];
	// joo.put("SJBMC",SJBMC);
	// joo.put("PYDM", PYDM);
	// joo.put("XH",XH);
	// joo.put("SJXMC",SJXMC);
	// joo.put("LX", LX);
	// joo.put("KD",KD);
	// joo.put("XSWS",XSWS);
	// joo.put("JLDW", JLDW);
	// joo.put("PK",PK);
	// joo.put("AK",AK);
	// joo.put("CZB", CZB);
	// joo.put("FKZ",FKZ);
	// joo.put("ZYS", ZYS);
	// joo.put("TXGD",TXGD);
	// joo.put("SYBHY",SYBHY);
	// ja.add(joo);
	// }
	// jo.put("rows",ja);
	// System.out.println(jo.toString());
	// return jo.toString();
	// }
	// catch(Exception e){
	//
	// e.printStackTrace();
	// return null;
	// }
	//
	//
	// }
	// 通过数据表名称查找
	@SuppressWarnings("unchecked")
	public List<DataDictionary> jsonbysjbmc(String sjbmc) {
		System.out.println("jsonbysjbmc");
		try {
			Session s = this.getOracleCurrentSession();
			SQLQuery sq = s.createSQLQuery(
					"select * from SY_SJZD where SJBMC='" + sjbmc
							+ "' order by xh").addEntity(DataDictionary.class);
			List<DataDictionary> l = sq.list();
			System.out.println("通过数据表名称查出有" + l.size() + "列....");
			this.closeOracleCurrentSession();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// 添加数据字典
	public void addDic(DataDictionary[] ds) {
		Session s = this.getOracleCurrentSession();
		Transaction tran = s.beginTransaction();
		for (int i = 0; i < ds.length; i++) {
			s.save(ds[i]);
		}
		tran.commit();
		this.closeOracleCurrentSession();
	}
	public String findTablesNames(){
		try {
			Session s = this.getOracleCurrentSession();
			SQLQuery sq = s
					.createSQLQuery("select distinct SJBMC from SY_SJZD order by SJBMC");
			List<?> l = sq.list();
			JSONArray ja = new JSONArray();
			for (int i = 0; i < l.size(); i++) {
				JSONObject joo = new JSONObject();
				String name = (String) l.get(i);
				joo.put("sjbmc", name);
				ja.add(joo);
			}
			this.closeOracleCurrentSession();
			return ja.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public String findTablesColumns(String sjbmc){
		try {
			Session s = this.getOracleCurrentSession();
			SQLQuery sq = s
					.createSQLQuery("select PYDM from SY_SJZD where sjbmc='"+sjbmc+"' order by SJBMC");
			List<?> l = sq.list();
			JSONArray ja = new JSONArray();
			for (int i = 0; i < l.size(); i++) {
				JSONObject joo = new JSONObject();
				String name = (String) l.get(i);
				joo.put("pydm", name);
				ja.add(joo);
			}
			this.closeOracleCurrentSession();
			return ja.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
