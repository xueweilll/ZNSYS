package com.jfsl.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.AddListDAO;
import com.jfsl.pojo.AddList;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( { @Result(name = "doList", location = "jsp/addlist/ListAddList.jsp") })
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class AddListAction extends BaseAction {
	private int id;
	private String membername;
	private String tel;
	private String memberdept;
	private String memo;

	private String addlistids;

	private AddList addlist;
	private List<AddList> addlists;
	private AddListDAO adao = new AddListDAO();

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

	public String getAddlistids() {
		return addlistids;
	}

	public void setAddlistids(String addlistids) {
		this.addlistids = addlistids;
	}

	@Action(value = "JsonAddList")
	public void doJson() {
		adao.clearOracleCurrentSession();
		addlists = adao.ListAll();
		this.jsonDataGrid(addlists);
	}

	@Action(value = "ListAddList")
	public String doList() {
		return "doList";
	}

	@Action(value = "InsertAddList")
	public String doInsert() {
		try {
			addlist = new AddList();
			addlist.setMembername(membername);
			addlist.setTel(tel);
			addlist.setMemberdept(memberdept);
			addlist.setMemo(memo);
			adao.OracleAdd(addlist);
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateAddList")
	public String doUpdate() {
		try {
			addlist = adao.find(id);
			addlist.setMembername(membername);
			addlist.setTel(tel);
			addlist.setMemberdept(memberdept);
			addlist.setMemo(memo);

			adao.OracleUpdate(addlist);

			return "update";

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Action(value = "DeleteAddList")
	public String doDelete() {
		try {
			addlist = adao.find(id);
			adao.OracleDelete(addlist);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "QueryAddList")
	public void doQuery() {
		adao.clearOracleCurrentSession();
		addlists = adao.Query(membername, memberdept);
		this.jsonDataGrid(addlists);
	}

}
