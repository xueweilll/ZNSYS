package com.jfsl.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.SmsLogDAO;
import com.jfsl.pojo.SmsLog;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( { @Result(name = "doList", location = "jsp/smslog/ListSmsLog.jsp") })
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class SmsLogAction extends BaseAction {
	private int smsid;
	private String ids;
	
	private String smstype;
	private String content;
	private String begintime;
	private String endtime;
	private String tel;

	private List<SmsLog> smslogs;
	private SmsLogDAO adao = new SmsLogDAO();

	public int getSmsid() {
		return smsid;
	}

	public void setSmsid(int smsid) {
		this.smsid = smsid;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getSmstype() {
		return smstype;
	}

	public void setSmstype(String smstype) {
		this.smstype = smstype;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTel() {
		return tel;
	}

	@Action(value = "JsonSmsLog")
	public void doJson() {
		adao.clearOracleCurrentSession();
		smslogs = adao.listAll(smsid);
		this.jsonDataGrid(smslogs);
	}

	@Action(value = "ListSmsLog")
	public String doList() {
		return "doList";
	}
	
	@Action(value = "QuerySmsLog")
	public void doQuery() {
		adao.clearOracleCurrentSession();
		try {
			smslogs = adao.Query(smstype, content, tel, begintime, endtime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.jsonDataGrid(smslogs);
	}
}
