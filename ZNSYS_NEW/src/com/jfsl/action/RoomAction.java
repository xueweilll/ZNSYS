package com.jfsl.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.RoomDAO;
import com.jfsl.pojo.Room;

@SuppressWarnings({ "serial", "unused" })
@ParentPackage("default")
@Results( {	
		@Result(name = "doList", location = "jsp/room/ListRoom.jsp")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") 
})

public class RoomAction extends BaseAction
{
	private int id;
	private String roomname;
	private String memo;
	
	private Room room;
	private List<Room> rooms;
	private RoomDAO roomdao = new RoomDAO();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public RoomDAO getRoomdao() {
		return roomdao;
	}

	public void setRoomdao(RoomDAO roomdao) {
		this.roomdao = roomdao;
	}

	@Action(value = "JsonRoom")
	public void doJson()
	{
		roomdao.clearOracleCurrentSession();
		rooms = roomdao.ListAll();
		this.jsonDataGrid(rooms);
	}

	@Action(value = "ListRoom")
	public String doList()
	{
		return "doList";
	}
	
	@Action(value = "InsertRoom")
	public String doInsert()
	{
		try {
			room = new Room();
			room.setRoomname(roomname);
			room.setMemo(memo);
			roomdao.OracleAdd(room);
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateRoom")
	public String doUpdate()
	{
		try {
			room=roomdao.find(id);
			room.setRoomname(roomname);
			room.setMemo(memo);
			
			roomdao.OracleUpdate(room);

			return "update";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Action(value = "DeleteRoom")
	public String doDelete()
	{
		try {
			room = roomdao.find(id);
			roomdao.OracleDelete(room);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}
}
