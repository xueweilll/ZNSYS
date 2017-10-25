package com.jfsl.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.AreaDAO;
import com.jfsl.dao.VideoDAO;
import com.jfsl.dao.WarnDictDAO;
import com.jfsl.pojo.Video;
import com.jfsl.pojo.WarnDict;

@SuppressWarnings({ "serial", "unused" })
@ParentPackage("default")
@Results( {	
		@Result(name = "doList", location = "jsp/warndict/ListWarnDict.jsp")
		})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class WarnDictAction extends BaseAction
{
	private int id;
	private String stationid;
	private String code;
	private String caption;
	private String memo;
	
	private WarnDict warndict;
	private WarnDictDAO vdao = new WarnDictDAO();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStationid() {
		return stationid;
	}

	public void setStationid(String stationid) {
		this.stationid = stationid;
	}

	public String Getcode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getmemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Action(value = "JsonWarnDict")
	public void doJson()
	{
		vdao.clearCurrentSession();
		this.jsonDataGrid(vdao.ListAll());
	}

	@Action(value = "ListWarnDict")
	public String doList()
	{
		return "doList";
	}

	@Action(value = "InsertWarnDict")
	public String doInsert()
	{
		try {
			warndict = new WarnDict();
			warndict.setCode(code);
			warndict.setCaption(caption);
			warndict.setMemo(memo);
			vdao.Add(warndict);
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateWarnDict")
	public String doUpdate()
	{
		try {
			warndict=vdao.find(id);
			warndict.setCode(code);
			warndict.setCaption(caption);
			warndict.setMemo(memo);
			
			vdao.Update(warndict);

			return "update";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Action(value = "DeleteWarnDict")
	public String doDelete()
	{
		try {
			vdao.Delete(vdao.find(id));
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "QueryWarnDict")
	public void doQuery()
	{
		this.jsonDataGrid(vdao.query(stationid));
	}
}
