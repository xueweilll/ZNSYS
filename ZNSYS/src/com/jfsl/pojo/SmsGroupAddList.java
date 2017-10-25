package com.jfsl.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_B_SmsGroupAddList")
public class SmsGroupAddList extends Pojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "F_ID")
	private int id;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "F_GroupID")
	private SmsGroup smsgroup;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "F_AddListID")
	private AddList addlist;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SmsGroup getSmsgroup() {
		return smsgroup;
	}

	public void setSmsgroup(SmsGroup smsgroup) {
		this.smsgroup = smsgroup;
	}

	public AddList getAddlist() {
		return addlist;
	}

	public void setAddlist(AddList addlist) {
		this.addlist = addlist;
	}

}
