//package com.jfsl.dboper;
//
//import java.math.BigDecimal;
//import org.hibernate.Session
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.Statement;
//import java.sql.Types;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.apache.log4j.Logger;
///**
// * 
// * @author 0242_gaoefeng
// *
// */
//
//public class DbOperator extends BaseOpera
//{
//   protected Logger log = Logger.getLogger(this.getClass());
//   public static long _maxNumber = 100000;
//   
//   
//	public boolean executeInsert(String sql,Object[] obj) 
//	{
//		return this.executeUpdate(sql,obj);
//	}
//   
//	public long executeInsert_backID(String sql,Object[] obj) 
//	{
//		return this.executeUpdate_backID(sql,obj);
//	}
//	
//	
//	public long executeUpdate_backID(String sql,Object[] obj) 
//	{
//		PreparedStatement ps = null;
//		Connection conn = null;
//		Session session = getSession();
//		long return_id = -1;
//		try 
//		{
//			conn = session.;
//			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
//			if(obj!=null && obj.length>0)
//			{
//				for(int i=0,len=obj.length;i<len;i++)
//				{
//					ps.setObject(i+1, obj[i]);			
//				}
//			}			
//			ps.executeUpdate();
//			ResultSet rs = ps.getGeneratedKeys();
//			if (rs.next())
//			{
//				return_id = rs.getInt(1);
//			}
//			obj = null;
//			conn.commit();
//			return return_id;
//		} catch (Exception e) 
//		{
//			log.error("异常:" + e);
//			log.error("异常sql�?" + sql);
//			e.printStackTrace();
//			return -1;
//		} finally 
//		{
//			this.clean(null,ps,null);
//			session.close();
//		}
//	}
//	
//	public boolean executeUpdate(String sql,Object[] obj) 
//	{
//		PreparedStatement ps = null;
//		Connection conn = null;
//		SqlSession session = getSession();
//		try 
//		{
//			conn = session.getConnection();
//			ps = conn.prepareStatement(sql);
//			if(obj!=null && obj.length>0)
//			{
//				for(int i=0,len=obj.length;i<len;i++)
//				{
//					ps.setObject(i+1, obj[i]);			
//				}
//			}			
//			ps.executeUpdate();
//			obj = null;
//			conn.commit();
//			return true;
//		} catch (Exception e) {
//			log.error("异常:" + e);
//			log.error("异常sql�?" + sql);
//			e.printStackTrace();
//			return false;
//		} finally 
//		{
//			this.clean(null,ps,null);
//			session.close();
//		}
//	}
//   
//   
//   
//   /**
//    * 根据sql直接获取map,已经跟数据库中字段对�?
//    * @param sql
//    * @param obj
//    * @return
//    */
//	public ArrayList<Map<String, Object>> getList_Pre(String sql,Object[] obj) 
//	{
//		log.debug("当前方法：getList_Pre");;
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		ResultSetMetaData rsmd = null;
//		SqlSession session = getSession();
//		ArrayList<Map<String, Object>> pkv = new ArrayList<Map<String, Object>>();
//		try 
//		{
//			conn = session.getConnection();
//			if(conn!=null)
//			{
//				stmt = conn.prepareStatement(sql);
//				if(obj!=null && obj.length>0)
//				{
//					for(int i=0,len=obj.length;i<len;i++)
//					{
//						if(obj[i]!=null)
//						{
//					       stmt.setObject(i+1, obj[i]);			
//						}
//						else
//						{
//							stmt.setObject(i+1, i+1);
//						}
//					}
//				}
//				obj = null;
//				rs = stmt.executeQuery();
//				conn.commit();
//				log.debug("sql:" + sql);
//				rsmd = rs.getMetaData();
//				int num = rsmd.getColumnCount();
//				while (rs.next()) 
//				{
//					Map<String, Object> map = new HashMap<String, Object>();
//					for (int i = 1; i <= num; i++) 
//					{
//						String key = rsmd.getColumnLabel(i).toUpperCase();
//						int colType = rsmd.getColumnType(i);
//						if (colType == Types.VARCHAR || colType == Types.CHAR) 
//						{
//							String value = rs.getString(i);
//							map.put(key, value);
//						} 
//						else if (colType == Types.INTEGER || colType == Types.DECIMAL ||colType == Types.NUMERIC || 
//								colType == Types.BIGINT || colType==Types.FLOAT || colType==Types.DOUBLE || colType==Types.REAL)
//						{
//							BigDecimal value = rs.getBigDecimal(i);
//							if (value == null) 
//							{
//								value = new BigDecimal(0);
//							}
//							map.put(key, value);
//						} else if (colType == Types.TIMESTAMP
//								|| colType == Types.DATE) 
//						{
//							Date value = null;
//							value = rs.getTimestamp(i);
//							map.put(key, DateUtils.getTimeALL(value));
//							map.put(key + "_SHOW_DATE",
//									DateUtils.getTimeStr(value)[1]);
//							map.put(key + "_FORMAT_DATE", DateUtils
//									.getTimeStr(value)[0]);
//						}
//						else
//						{
//							Object value = rs.getString(i);
//							map.put(key, value);
//						}
//					}
//					pkv.add(map);
//				}
//				return pkv;
//			}
//			else
//			{
//				log.error("getList_Pre：获取数据库连接为null");
//				return null;
//			}
//		} catch (Exception e) 
//		{
//			log.error("异常�? + e);
//			log.error("异常sql:" + sql);
//			e.fillInStackTrace();
//			return null;
//		} finally 
//		{
//			this.clean(rs, stmt, null);
//			session.close();
//		}
//	}
//	
//	/**
//	 * 只获取一条首记录
//	 * @param sql
//	 * @param obj
//	 * @return
//	 */
//	public Map<String, Object> getHashMap_Pre(String sql,Object[] obj) 
//	{
//		List<Map<String, Object>> list = this.getList_Pre(sql,obj);
//		if (list != null && list.size() > 0) 
//		{
//			Map<String, Object> map_tmp = list.get(0);
//			list = null;
//			return map_tmp;
//		} 
//		else 
//		{
//			return null;
//		}
//	}
//	
//	protected void clean(ResultSet rs, PreparedStatement ps, Statement st)
//	{
//		if (rs != null) 
//		{
//			try {
//				Statement st2 = rs.getStatement();
//				if (st2 != null) 
//				{
//					st2.close();
//					st2 = null;
//				}
//				rs.close();
//				rs = null;
//			} catch (Exception e) {
//				log.error("异常:" + e);
//				e.printStackTrace();
//			}
//		}
//		if (ps != null) 
//		{
//			try {
//				ps.close();
//				ps = null;
//			} catch (Exception e) {
//				log.error("异常:" + e);
//				e.printStackTrace();
//			}
//		}
//		if (st != null) 
//		{
//			try {
//				st.close();
//				st = null;
//			} catch (Exception e) {
//				log.error("异常:" + e);
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	/**
//	 * 判断记录有多少条
//	 * @param sql
//	 * @param obj
//	 * @return
//	 */
//	public int getCount(String sql,Object[] obj) 
//	{
//		String sql2 = "select count(1) count1 from (" + sql
//				+ ") a ";
//				//limit 0," + _maxNumber;
//		 Map<String, Object> map = this.getHashMap_Pre(sql2, obj);
//		 return map==null?0:Integer.valueOf(String.valueOf(map.get("COUNT1"))).intValue();
//	}
//	
//	
//	/**
//	 * 获得数据库时�?
//	 * 
//	 * @return
//	 */
//	public Date getSysdate() 
//	{
//		String sql = "select NOW() today";
//		Map<String, Object> map = this.getHashMap_Pre(sql,null);
//		Date date_tmp = (Date) map.get("TODAY");
//		map = null;
//		return date_tmp;
//	}
//	
//	
//	
//	/**
//	 * 预执行批处理
//	 * @param sql1
//	 * @param obj_list1  数据集合1
//	 * @param sql2
//	 * @param obj_list2  数据集合2
//	 * @param sql3
//	 * @param obj_list3  数据集合3
//	 * @return
//	 */
//	public  boolean executeBatch_Pre(String sql1,ArrayList<Object[]> obj_list1,
//							         String sql2,ArrayList<Object[]> obj_list2,
//			                         String sql3,ArrayList<Object[]> obj_list3
//	                                  ) 
//	{
//		log.debug("当前方法：executeBatch_Pre");;
//		Connection conn = null;
//		PreparedStatement stmt1 = null;
//		PreparedStatement stmt2 = null;
//		PreparedStatement stmt3 = null;
//		SqlSession session = getSession();
//		try 
//		{
//			conn = session.getConnection();
//			if(conn!=null)
//			{
//				conn.setAutoCommit(false);
//				stmt1 = conn.prepareStatement(sql1);
//				for(Object[] obj:obj_list1)
//				{
//					if(obj!=null && obj.length>0)
//					{
//						for(int i=0,len=obj.length;i<len;i++)
//						{
//							if(obj[i]!=null)
//							{
//						       stmt1.setObject(i+1, obj[i]);			
//							}
//							else
//							{
//								stmt1.setObject(i+1, "");
//							}
//						}
//					}
//					stmt1.addBatch();
//				}
//				
//				stmt2 = conn.prepareStatement(sql2);
//				for(Object[] obj:obj_list2)
//				{
//					if(obj!=null && obj.length>0)
//					{
//						for(int i=0,len=obj.length;i<len;i++)
//						{
//							if(obj[i]!=null)
//							{
//						       stmt2.setObject(i+1, obj[i]);			
//							}
//							else
//							{
//								stmt2.setObject(i+1, "");
//							}
//						}
//					}
//					stmt2.addBatch();
//				}
//				
//				stmt3 = conn.prepareStatement(sql3);
//				for(Object[] obj:obj_list3)
//				{
//					if(obj!=null && obj.length>0)
//					{
//						for(int i=0,len=obj.length;i<len;i++)
//						{
//							if(obj[i]!=null)
//							{
//						       stmt3.setObject(i+1, obj[i]);			
//							}
//							else
//							{
//								stmt3.setObject(i+1, "");
//							}
//						}
//					}
//					stmt3.addBatch();
//				}
//				stmt1.executeBatch();
//				stmt2.executeBatch();
//				stmt3.executeBatch();
//				conn.commit();
//				return true;
//			}
//			else
//			{
//				return false;
//			}
//
//		}
//		 catch (Exception e) 
//			{
//				rollback(conn);
//				log.error("异常:" + e);
//				e.printStackTrace();
//				return false;
//			} finally 
//			{
//				clean(null,null,stmt1);
//				clean(null,null,stmt2);
//				clean(null,null,stmt3);
//				session.close();
//			}
//	}
//	
//	/**
//	 * 预执行批处理
//	 * @param sql1
//	 * @param obj_list1  数据集合1
//	 * @param sql2
//	 * @param obj_list2  数据集合2
//	 * @return
//	 */
//	public  boolean executeBatch_Pre2(String sql1,ArrayList<Object[]> obj_list1,
//							         String sql2,ArrayList<Object[]> obj_list2) 
//	{
//		log.debug("当前方法：executeBatch_Pre2");;
//		Connection conn = null;
//		PreparedStatement stmt1 = null;
//		PreparedStatement stmt2 = null;
//		SqlSession session = getSession();
//		try 
//		{
//			conn = session.getConnection();
//			if(conn!=null)
//			{
//				conn.setAutoCommit(false);
//				stmt1 = conn.prepareStatement(sql1);
//				for(Object[] obj:obj_list1)
//				{
//					if(obj!=null && obj.length>0)
//					{
//						for(int i=0,len=obj.length;i<len;i++)
//						{
//							if(obj[i]!=null)
//							{
//						       stmt1.setObject(i+1, obj[i]);			
//							}
//							else
//							{
//								stmt1.setObject(i+1, "");
//							}
//						}
//					}
//					stmt1.addBatch();
//				}
//				
//				stmt2 = conn.prepareStatement(sql2);
//				for(Object[] obj:obj_list2)
//				{
//					if(obj!=null && obj.length>0)
//					{
//						for(int i=0,len=obj.length;i<len;i++)
//						{
//							if(obj[i]!=null)
//							{
//						       stmt2.setObject(i+1, obj[i]);			
//							}
//							else
//							{
//								stmt2.setObject(i+1, "");
//							}
//						}
//					}
//					stmt2.addBatch();
//				}
//				
//				stmt1.executeBatch();
//				stmt2.executeBatch();
//				conn.commit();
//				return true;
//			}
//			else
//			{
//				return false;
//			}
//
//		}
//		 catch (Exception e) 
//			{
//				rollback(conn);
//				log.error("异常:" + e);
//				e.printStackTrace();
//				return false;
//			} finally 
//			{
//				clean(null,null,stmt1);
//				clean(null,null,stmt2);
//				session.close();
//			}
//	}
//	
//	
//	/**
//	 * 执行批处�?
//	 * @param al
//	 * @return
//	 */
//	public boolean executeBatch(ArrayList<String> al) 
//	{
//		Connection conn = null;
//		Statement st = null;
//		SqlSession session = getSession();
//		try {
//			conn = session.getConnection();
//			conn.setAutoCommit(false);
//			st = conn.createStatement();
//			for (int i = 0; i < al.size(); i++) 
//			{
//				st.addBatch(al.get(i));
//			}
//			st.executeBatch();
//			conn.commit();
//			return true;
//		} catch (Exception e) 
//		{
//			rollback(conn);
//			log.error("异常:" + e);
//			for (int i = 0; i < al.size(); i++) {
//				log.error("异常sql@ �?" + (i + 1) + "行："
//								+ al.get(i));
//			}
//			e.printStackTrace();
//			return false;
//		} finally 
//		{
//			clean(null,null,st);
//			session.close();
//		}
//	}
//	
//	
//	private void rollback(Connection conn) 
//	{
//		try {
//			conn.rollback();
//		} catch (Exception e) {
//			log.error("异常:" + e);
//			e.printStackTrace();
//		}
//	}
//	
//	public String getSqlForPageSelect(Long beginnum,Long num,String sql)
//	{
//		return
//			 "SELECT A.* FROM ("+sql+" ) A " +
//			 " limit " +beginnum+","+num.longValue();
//	}
//	
//	
//	
//	/**
//	 * 获取oracle某行范围内的数据
//	 * 
//	 * @param sql
//	 *            查询语句
//	 * @param begin
//	 *            �?���?
//	 * @param end
//	 *            结束�?
//	 * @return list
//	 */
//	@SuppressWarnings("unchecked")
//	protected List<Map<String, Object>> getRangeList(String sql, int begin,
//			int per_num,Object[] obj) 
//	{
//          String sql_ = 	"SELECT A.* FROM ("+sql+" ) A " +
//			 " limit " +begin+","+per_num;
//          return this.getList_Pre(sql_, obj);
//	}
//
//
//	/**
//	 * 获得序列的下�?��
//	 * 
//	 * @param seq_name  select nextval('MovieSeq');
//	 * @return
//	 */
//	public String getSeq(String seq_string) 
//	{
//		String return_sql ="";
//		String sql = "select nextval('"+seq_string+"') as NEXTVAL ";
//		Map<String, Object> map = this.getHashMap_Pre(sql, null);
//		int seq = Integer.valueOf(String.valueOf(map.get("NEXTVAL"))).intValue();
//		if(seq> 9999)
//		{
//			String sql_ = "select setval('"+seq_string+"',1000) as NEXTVAL";
//			Map<String, Object> map_ = this.getHashMap_Pre(sql_, null);
//			return_sql = String.valueOf(map_.get("NEXTVAL"));
//		}
//		else
//		{
//			return_sql = String.valueOf(map.get("NEXTVAL"));
//		}
//		return return_sql;
//	}
//	
//	/**
//	 * 获得补帧的序列下�?��
//	 * @param seq_String
//	 * @return
//	 */
//	public String getXXFillTrans_id(String seq_string){
//		String return_sql ="";
//		String sql = "select nextval('"+seq_string+"') as NEXTVAL ";
//		Map<String, Object> map = this.getHashMap_Pre(sql, null);
//		int seq = Integer.valueOf(String.valueOf(map.get("NEXTVAL"))).intValue();
//		if(seq > 999)
//		{
//			String sql_ = "select setval('"+seq_string+"',1) as NEXTVAL";
//			Map<String, Object> map_ = this.getHashMap_Pre(sql_, null);
//			return_sql = String.valueOf(map_.get("NEXTVAL"));
//		}
//		else
//		{
//			return_sql = String.valueOf(map.get("NEXTVAL"));
//		}
//		return return_sql;
//	}
//	
//	
//	public String getSeq_Next(String seq_string) 
//	{
//		String return_sql ="";
//		String sql = "select nextval('"+seq_string+"') as NEXTVAL ";
//		Map<String, Object> map = this.getHashMap_Pre(sql, null);
//		return_sql = String.valueOf(map.get("NEXTVAL"));
//		return return_sql;
//	}
//	
//	
//	public  boolean executeBatch_Pre(String sql1,ArrayList<Object[]> obj_list1) 
//	{
//		log.debug("当前方法：executeBatch_Pre");;
//		Connection conn = null;
//		PreparedStatement stmt1 = null;
//		SqlSession session = getSession();
//		try 
//		{
//			conn = session.getConnection();
//			if(conn!=null)
//			{
//				conn.setAutoCommit(false);
//				stmt1 = conn.prepareStatement(sql1);
//				for(Object[] obj:obj_list1)
//				{
//					if(obj!=null && obj.length>0)
//					{
//						for(int i=0,len=obj.length;i<len;i++)
//						{
//							if(obj[i]!=null)
//							{
//						      stmt1.setObject(i+1, obj[i]);			
//							}
//							else
//							{
//								stmt1.setObject(i+1, "");
//							}
//						}
//					}
//					stmt1.addBatch();
//				}			
//				stmt1.executeBatch();
//				conn.commit();
//				return true;
//			}
//			else
//			{
//			return false;
//			}		
//		}
//		catch (Exception e) 
//		{
//			rollback(conn);
//			log.error("异常:" + e);
//			e.printStackTrace();
//			return false;
//		} 
//		finally 
//		{
//			clean(null,null,stmt1);
//			session.close();
//		}
//	}
//	
//}
