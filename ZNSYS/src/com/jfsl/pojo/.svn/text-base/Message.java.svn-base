package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_SA_Message")
public class Message extends Pojo
{
	/**主键自增长*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int messageid;
	
	/**普通属性*/
	@Column(name = "Title")
	private String title;
	@Column(name = "Author")
	private String author;
	@Column(name = "Content")
	private String content;
	@Column(name = "PublishTime")
	private Date publishtime;
	@Column(name = "DateLine")
	private Date dateline;
	
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
	public String getPublishtime() {
		if(publishtime == null) return "";
		else return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(publishtime);
	}
	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}
	public String getDateline() {
		if(dateline == null) return "";
		else return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateline);
	}
	public void setDateline(Date dateline) {
		this.dateline = dateline;
	}
}
