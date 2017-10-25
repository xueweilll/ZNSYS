package com.jfsl.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.BackupDAO;
import com.jfsl.pojo.Backup;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {	
		@Result(name = "doList", location = "jsp/backup/backup.jsp")
		})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class BackupAction extends BaseAction
{
	int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BackupDAO getBdao() {
		return bdao;
	}
	public void setBdao(BackupDAO bdao) {
		this.bdao = bdao;
	}
	BackupDAO bdao=new BackupDAO();
	@Action(value = "ListBackup")
	public String doList()
	{
		return "doList";
	}
	@Action(value = "JsonBackup")
	public void JsonBackup()
	{
		System.out.println("json back  up");
	    this.jsonDataGrid(bdao.ListAll()); 
	}
	@Action(value="AddBackup")
	public String addBackup(){
		try{
			bdao.addBackup();
			return "add";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		
	}
	@Action(value="DelBackup")
	public String delBackup(){
		try{
		Backup b=(Backup) bdao.getOracleCurrentSession().get(Backup.class, id);
		bdao.OracleDelete(b);
		return "delete";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "error";
		}
	}
}