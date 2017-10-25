package com.jfsl.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class TongjiDAO extends DAOImp {
	public String sqltj(String sql) {
		String array = "";
		try {
			Session s = this.getOracleCurrentSession();
			SQLQuery q = s.createSQLQuery(sql);
			List<?> l = q.list();
			if (l.size() == 0)
				array = "[]";
			else
				array = createdata(l);
		} catch (Exception e) {
			e.printStackTrace();
			array = "errorsql";
		}
		return array;

	}

	public String createdata(List<?> l) {
		// String s="";
		for (int i = 0; i < l.size(); i++) {

		}
		return null;

	}

	public String jsonph(String year) {
		try {
			Session s = this.getOracleCurrentSession();
			Query q = s.createSQLQuery(" select  YPPH,count(YPPH) as num from AJHD01 group by YPPH having ypph like '"
							+ year + "%' order by YPPH ");
			List<?> l = q.list();
			String hzb = "[";
			String sz = "[";
			/* System.out.println("按样品批号排序统计:"+l.size()); */
			for (int i = 0; i < l.size(); i++) {
				Object[] o = (Object[]) l.get(i);
				hzb += "'" + o[0] + "',";
				sz += o[1] + ",";
			}
			hzb = hzb.substring(0, hzb.length() - 1);
			hzb += "]";
			sz = sz.substring(0, sz.length() - 1);
			sz += "]";
			String fanhui = hzb + "@@@" + sz;
			return fanhui;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String jsonwcqk(String year) {
		try {
			Session s = this.getOracleCurrentSession();
			Query q = s.createSQLQuery(" select  YPPH,count(YPPH) as num from AJHD01 group by YPPH having ypph like '"
							+ year + "%' order by YPPH ");
			List<?> l = q.list();
			Query all = s.createSQLQuery("select  YPPH,count(YPPH) as num from AJHF03 group by YPPH,YSC having  YSC=-1 and ypph like '"
							+ year + "%' order by YPPH ");
			List<?> al = all.list();
			String[] hzb = new String[l.size()];
			String sz = "[";
			for (int i = 0; i < l.size(); i++) {
				Object[] o = (Object[]) l.get(i);
				hzb[i] = (String) o[0];
				sz += o[1] + ",";
			}
			sz = sz.substring(0, sz.length() - 1);
			sz += "]";
			// 完成情况
			BigDecimal[] wcsz = new BigDecimal[l.size()];
			for (int i = 0; i < al.size(); i++) {
				Object[] o = (Object[]) al.get(i);
				for (int j = 0; j < hzb.length; j++) {
					if (o[0].equals(hzb[j]))
						wcsz[j] = (BigDecimal) o[1];
				}
			}
			// 将完成情况的数组转化为字符串
			String wcqksz = "[";
			for (int i = 0; i < wcsz.length; i++) {
				if (wcsz[i] == null || wcsz[i].equals("null")) {
					continue;
				}
				wcqksz += wcsz[i] + ",";
			}
			wcqksz = wcqksz.substring(0, wcqksz.length() - 1);
			wcqksz += "]";
			// 将横坐标转化为字符串
			String hzbstr = "[";
			for (int i = 0; i < hzb.length; i++) {
				hzbstr += "'" + hzb[i] + "',";
			}
			hzbstr = hzbstr.substring(0, hzbstr.length() - 1);
			hzbstr += "]";
			String returnstr = hzbstr + "@@@" + sz + "@@@" + wcqksz;
			return returnstr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String jsonsql(String sql) {
		try {
			Session s = this.getOracleCurrentSession();
			SQLQuery sq = s.createSQLQuery(sql);
			List<?> l = sq.list();
			String hzb = "[";
			String zzb = "[";
			for (int i = 0; i < l.size(); i++) {
				Object[] sz = (Object[]) l.get(i);
				Object h = sz[0];
				Object z = sz[1];
				hzb += "'" + h + "',";
				zzb += z + ",";
			}
			hzb = hzb.substring(0, hzb.length() - 1);
			hzb += "]";
			zzb = zzb.substring(0, zzb.length() - 1);
			zzb += "]";
			return hzb + "@@@" + zzb;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public List<Object> createSql(String sql) {
		try {
			Session s = this.getOracleCurrentSession();
			SQLQuery sq = s.createSQLQuery(sql);
			List<?> l = sq.list();
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < l.size(); i++) {				
				if(l.get(i)==null){
					continue;
				}
				//System.out.println(l.get(i).getClass() + "：第" + i);
				if (l.get(i).getClass() != Object[].class) {
					if (l.get(i) == null) {
						result.add("null");
						continue;
					}
					result.add(l.get(i));
					continue;
				}
				Object[] sz = (Object[]) l.get(i);
				List<Object> ls1 = new ArrayList<Object>();
				for (int j = 0; j < sz.length; j++) {
					ls1.add(sz[j]);
				}
				result.add(ls1);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
