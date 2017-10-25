package com.jfsl.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.SmsGroupDAO;
import com.jfsl.pojo.SmsGroup;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( { @Result(name = "doList", location = "jsp/smsgroup/ListSmsGroup.jsp") })
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class SmsGroupAction extends BaseAction {
	private int id;
	private String caption;
	private String memo;
	private String username;

	private SmsGroup smsgroup;
	private List<SmsGroup> smsgroups;
	private SmsGroupDAO adao = new SmsGroupDAO();

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Action(value = "JsonSmsGroup")
	public void doJson() {
		adao.clearCurrentSession();
		smsgroups = adao.ListAll(getPresentUser().getUsername());
		this.jsonSingleTree(smsgroups, "", "全部分组", "id", "caption", true);
	}

	@Action(value = "ListSmsGroup")
	public String doList() {
		return "doList";
	}

	@Action(value = "InsertSmsGroup")
	public String doInsert() {
		try {
			smsgroup = new SmsGroup();
			smsgroup.setCaption(caption);
			smsgroup.setMemo(memo);
			smsgroup.setUser(getPresentUser());
			adao.Add(smsgroup);
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateSmsGroup")
	public String doUpdate() {
		try {
			smsgroup = adao.find(id);
			smsgroup.setCaption(caption);
			smsgroup.setMemo(memo);
			adao.Update(smsgroup);

			return "update";

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Action(value = "DeleteSmsGroup")
	public String doDelete() {
		try {
			smsgroup = adao.find(id);
			adao.Delete(smsgroup);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "JsonSmsGroupName")
	public void doJsonName() {
		adao.clearCurrentSession();
		smsgroups = adao.ListAll(getPresentUser().getUsername());
		this.jsonCombobox(smsgroups);
	}
	
	@Action(value = "JsonSmsGroupGrid")
	public void doJsonGrid() {
		adao.clearCurrentSession();
		smsgroups = adao.ListAll(getPresentUser().getUsername());
		this.jsonDataGrid(smsgroups);
	}
}
