package com.jfsl.dao;

import java.util.List;

import org.hibernate.Query;
import com.jfsl.pojo.Video;

@SuppressWarnings("unchecked")
public class VideoDAO extends DAOImp {
	
	public List<Video> ListAll()
	{
		String sqlstr="from Video";
		Query query =this.getOracleCurrentSession().createQuery(sqlstr);
		List<Video> ls=(List<Video>)query.list();
		return ls;
	}
	
	public Video find(int id)
	{
		Video dep=(Video)this.getOracleCurrentSession().get(Video.class, id);
		return dep;
	}
}
