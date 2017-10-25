package com.jfsl.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_B_SmsGroupAddList")
public class SmsGroupAddList extends Pojo {

	@Id
	@SequenceGenerator(name = "SEQ_T_B_SMSGROUPADDLIST", sequenceName = "SEQ_T_B_SMSGROUPADDLIST", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_B_SMSGROUPADDLIST", strategy = GenerationType.SEQUENCE)
	@Column(name = "F_ID", unique = true, nullable = false, precision = 22, scale = 0)
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
