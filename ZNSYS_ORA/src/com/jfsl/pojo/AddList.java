package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_B_AddList")
public class AddList extends Pojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "F_ID")
	private int id;
	@Column(name = "F_MemberName")
	private String membername;
	@Column(name = "F_Tel")
	private String tel;
	@Column(name = "F_MemberDept")
	private String memberdept;
	@Column(name = "F_Memo")
	private String memo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMemberdept() {
		return memberdept;
	}

	public void setMemberdept(String memberdept) {
		this.memberdept = memberdept;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
