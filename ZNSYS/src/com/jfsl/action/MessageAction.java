package com.jfsl.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
//import org.apache.struts2.jasper.util.SystemLogHandler;
import org.omg.CORBA.Request;

import com.jfsl.dao.MessageDAO;
import com.jfsl.pojo.*;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doJson", location = "jsp/message/JsonNotice.jsp"),
		@Result(name = "ListAll", location = "jsp/message/JsonAll.jsp"),
		@Result(name = "Query", location = "jsp/message/JsonAll.jsp"),
		@Result(name = "doList", location = "jsp/message/ListMessage.jsp")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class MessageAction extends BaseAction
{
	//具体元素
	private int messageid;
	private String title;
	private String author;
	private String content;
	private Date publishtime;
	private Date dateline;
	
	//pojo
	private Message message;
	private List<Message> messages;
	
	//dao
	private MessageDAO messagedao = new MessageDAO();

	public int getMessageid() {
		return messageid;
	}

	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public Date getDateline() {
		return dateline;
	}

	public void setDateline(Date dateline) {
		this.dateline = dateline;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public MessageDAO getMessagedao() {
		return messagedao;
	}

	public void setMessagedao(MessageDAO messagedao) {
		this.messagedao = messagedao;
	}

	@Action(value = "JsonMessage")
	public void doJson()
	{
		//System.out.println("JsonMessage");
		messagedao.clearCurrentSession();		//清空
		messages = messagedao.ListUsed();
		
		this.jsonDataGrid(messages);
	}
	
	@Action(value = "ListMessage")
	public String ListMessage()
	{
		return "doList"; 
	}
	
	@Action(value = "JsonAllMessage")
	public String ListAll()
	{
		//System.out.println("List All Action");
		messagedao.clearCurrentSession();
		messages = messagedao.ListAll();
		return "ListAll";
	}
	
	@Action(value = "JsonQuery")
	public String Query()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String begintime = request.getParameter("begintime");
		String endtime = request.getParameter("endtime");
		if(begintime!=null&&endtime!=null){
			messagedao.clearCurrentSession();
			messages = messagedao.QueryTime(begintime,endtime);
			//if(messages.size() == 0) return "nocontent";
			return "Query";
		}
		else{
			if(author!=null){
				messagedao.clearCurrentSession();
				messages = messagedao.QueryAuthor(author);
				return "Query";
			}
			else return "error";
		}
	}
	
	@Action(value = "InsertMessage")
	public String doInsert(){
		try {
			message = new Message();
			message.setTitle(title);
			message.setAuthor(author);
			message.setContent(content);
			message.setPublishtime(publishtime);
			message.setDateline(dateline);
			messagedao.Add(message);
			
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateMessage")
	public String doUpdate(){
		try {
			message = messagedao.Find(messageid).get(0);
			message.setTitle(title);
			message.setAuthor(author);
			message.setContent(content);
			message.setPublishtime(publishtime);
			message.setDateline(dateline);
			messagedao.Update(message);
			return "update";
		} catch (Exception e) {
			return "error";
		}
	}
	
	@Action(value = "DeleteMessage")
	public String doDelete()
	{
		try {
			//System.out.println("title:"+title);
			messagedao.DeleteMessage(messageid);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}
}
