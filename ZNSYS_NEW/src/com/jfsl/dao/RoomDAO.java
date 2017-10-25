package com.jfsl.dao;

import java.util.List;

import org.hibernate.Query;
import com.jfsl.pojo.Room;

@SuppressWarnings("unchecked")
public class RoomDAO extends DAOImp {
	
	public List<Room> ListAll()
	{
		String sqlstr="from Room";
		Query query =this.getOracleCurrentSession().createQuery(sqlstr);
		List<Room> ls=(List<Room>)query.list();
		return ls;
	}
	
	public Room find(int id)
	{
		Room room=(Room)this.getOracleCurrentSession().get(Room.class, id);
		return room;
	}
}
