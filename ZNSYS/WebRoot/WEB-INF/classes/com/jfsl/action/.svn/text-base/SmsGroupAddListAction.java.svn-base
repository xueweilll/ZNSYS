package com.jfsl.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.jfsl.dao.AddListDAO;
import com.jfsl.dao.SmsGroupAddListDAO;
import com.jfsl.dao.SmsGroupDAO;
import com.jfsl.pojo.SmsGroup;
import com.jfsl.pojo.SmsGroupAddList;

@SuppressWarnings("serial")
@ParentPackage("default")
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class SmsGroupAddListAction extends BaseAction {
	private int id;
	private int groupid;
	private int addlistid;
	private String groupids;
	private String addlistids;

	private SmsGroupAddList smsgroupaddlist;
	private List<SmsGroupAddList> smsgroupaddlists;
	private SmsGroupAddListDAO adao = new SmsGroupAddListDAO();

	private SmsGroupDAO sd = new SmsGroupDAO();
	private AddListDAO ad = new AddListDAO();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getAddlistid() {
		return addlistid;
	}

	public void setAddlistid(int addlistid) {
		this.addlistid = addlistid;
	}

	public String getAddlistids() {
		return addlistids;
	}

	public void setAddlistids(String addlistids) {
		this.addlistids = addlistids;
	}

	public String getGroupids() {
		return groupids;
	}

	public void setGroupids(String groupids) {
		this.groupids = groupids;
	}

	@Action(value = "JsonSmsGroupAddList")
	public void doJson() {
		adao.clearCurrentSession();
		smsgroupaddlists = adao.ListAll(groupid);
		this.jsonDataGrid(smsgroupaddlists);
	}

	@Action(value = "ListSmsGroupAddList")
	public String doList() {
		return "doList";
	}

	@Action(value = "InsertSmsGroupAddList")
	public String doInsert() {
		try {
			String[] addlistidArray = addlistids.split(",");
			SmsGroup sg=sd.find(groupid); 
			for (int i = 0; i < addlistidArray.length; i++) {
				smsgroupaddlist = new SmsGroupAddList();
				smsgroupaddlist.setSmsgroup(sg);
				smsgroupaddlist.setAddlist(ad.find(Integer.parseInt(addlistidArray[i])));
				adao.Add(smsgroupaddlist);
			}
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "DeleteSmsGroupAddList")
	public String doDelete() {
		try {
			smsgroupaddlist = adao.find(id);
			adao.Delete(smsgroupaddlist);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "JsonSmsGroupAddListName")
	public void doJsonName() {
		adao.clearCurrentSession();
		smsgroupaddlists = adao.ListAll(groupid);
		this.jsonArray(smsgroupaddlists);
	}
	
	@Action(value = "JsonAddListBySmsGroup")
	public void doJsonAddList() {
		adao.clearCurrentSession();
		this.jsonArray(adao.ListAll(groupids));
	}
}
