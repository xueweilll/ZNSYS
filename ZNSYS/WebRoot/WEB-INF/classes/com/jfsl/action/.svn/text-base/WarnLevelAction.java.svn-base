package com.jfsl.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.WarnLevelDAO;
import com.jfsl.pojo.WarnLevel;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results
({
	@Result(name="doList",location="jsp/warnlevel/ListWarnLevel.jsp"),
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class WarnLevelAction extends BaseAction{
	private int id;
	private String code;
	private String caption;
	private String color;
	private String picture;
	private String memo;
	
	private WarnLevel warnlevel;
	private List<WarnLevel> warnlevels;
	
	private WarnLevelDAO wl=new WarnLevelDAO();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public WarnLevel getWarnlevel() {
		return warnlevel;
	}

	public void setWarnlevel(WarnLevel warnlevel) {
		this.warnlevel = warnlevel;
	}

	public List<WarnLevel> getWarnlevels() {
		return warnlevels;
	}

	public void setWarnlevels(List<WarnLevel> warnlevels) {
		this.warnlevels = warnlevels;
	}

	public WarnLevelDAO getWl() {
		return wl;
	}

	public void setWl(WarnLevelDAO wl) {
		this.wl = wl;
	}
	@Action(value="JsonWarnLevel")
	public void doJson()
	{
		wl.clearCurrentSession();
		warnlevels=wl.ListAll();
		this.jsonDataGrid(warnlevels);
	}
	
	@Action(value="ListWarnLevel")
	public String doList()
	{
		return "doList";
	}
	
	@Action(value="InsertWarnLevel")
	public String doInsert()
	{		
		try {
			warnlevel=new WarnLevel();
			warnlevel.setCode(code);
			warnlevel.setCaption(caption);
			warnlevel.setColor(color);
			warnlevel.setPicture(picture);
			warnlevel.setMemo(memo);
			wl.Add(warnlevel);
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}
	@Action(value="UpdateWarnLevel")
	public String doUpdate()
	{
		try {
			warnlevel=wl.Find(id);
			warnlevel.setCode(code);
			warnlevel.setCaption(caption);
			warnlevel.setColor(color);
			warnlevel.setPicture(picture);
			warnlevel.setMemo(memo);
			wl.Update(warnlevel);
			return "update";
		} catch (Exception e) {
			return "error";
		}
	}
	
	@Action(value="DeleteWarnLevel")
	public String doDelete()
	{
		try {
			warnlevel=wl.Find(id);
			wl.Delete(warnlevel);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}
	
	@Action(value="QueryWarnLevel")
	public void doQuery()
	{
		wl.clearCurrentSession();
		warnlevels=wl.Query(code,caption);
		this.jsonDataGrid(warnlevels);
	}
	
	@Action(value="JsonWarnLevelname")
	public void doJsonName()
	{
		wl.clearCurrentSession();
		warnlevels=wl.ListAll();
		this.jsonCombobox(warnlevels);
	}
}
