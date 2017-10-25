package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_V_Room")
public class Room extends Pojo
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	@Column(name = "RoomName")
	private String roomname;
	@Column(name = "Memo")
	private String memo;
	
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
}
