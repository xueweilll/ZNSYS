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
@Table(name = "T_SA_Push")
public class Push extends Pojo
{
	/**主键自增长*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/**普通属性*/
	@Column(name = "Receiver")
	private String receiver;
	@Column(name = "Publisher")
	private String publisher;
	@Column(name = "Kind")
	private String kind;
	@Column(name = "Title")
	private String title;
	@Column(name = "Content")
	private String content;
	@Column(name = "URL")
	private String url;
	@Column(name = "Publishtime")
	private Date publishtime;
	@Column(name = "Deadline")
	private Date deadline;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/*时间需要特殊处理*/
	public String getPublishtime() {
		if(publishtime == null) return "";
		else return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(publishtime);
	}
	
	public void setPublishtime(String publishtime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			this.publishtime = sdf.parse(publishtime);
		} catch (ParseException e) {
			this.publishtime = null;
		}
	}
	
	public String getDateline() {
		if(deadline == null) return "";
		else return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deadline);
	}
	
	public void setDateline(String dateline) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			this.deadline = sdf.parse(dateline);
		} catch (ParseException e) {
			this.deadline = null;
		}
	}
}
