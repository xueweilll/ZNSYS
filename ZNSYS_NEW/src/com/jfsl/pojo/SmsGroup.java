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
@Table(name = "T_B_SmsGroup")
public class SmsGroup extends Pojo {

	@Id
	@SequenceGenerator(name = "SEQ_T_B_SMSGROUP", sequenceName = "SEQ_T_B_SMSGROUP", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_B_SMSGROUP", strategy = GenerationType.SEQUENCE)
	@Column(name = "F_ID", unique = true, nullable = false, precision = 22, scale = 0)
	private int id;
	@Column(name = "F_Caption")
	private String caption;
	@Column(name = "F_Memo")
	private String memo;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "F_UserID")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
