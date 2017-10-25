package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_V_Room")
public class Room extends Pojo
{
	@Id
	@SequenceGenerator(name = "SEQ_T_V_ROOM", sequenceName = "SEQ_T_V_ROOM", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_V_ROOM", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
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
