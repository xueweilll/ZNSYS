package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "T_P_Flow")
public class Flow extends Pojo
{
	/**主键自增长*/
	@Id
	@SequenceGenerator(name = "SEQ_T_P_FLOW", sequenceName = "SEQ_T_P_FLOW", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_P_FLOW", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private int id;
	
	/**普通属性*/
	@Column(name = "Title")
	private String title;
	@Column(name = "LineData")
	private String linedata;
	@Column(name = "LineCount")
	private String linecount;
	@Column(name = "NodeData")
	private String nodedata;
	@Column(name = "NodeCount")
	private String nodecount;
	@Column(name = "AreaData")
	private String areadata;
	@Column(name = "AreaCount")
	private String areacount;
	@Column(name = "Author")
	private String author;
	@Column(name = "Publishtime")
	private String publishtime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLinedata() {
		return linedata;
	}
	
	public void setLinedata(String linedata) {
		this.linedata = linedata;
	}
	
	public String getLinecount() {
		return linecount;
	}
	
	public void setLinecount(String linecount) {
		this.linecount = linecount;
	}
	
	public String getNodedata() {
		return nodedata;
	}
	
	public void setNodedata(String nodedata) {
		this.nodedata = nodedata;
	}
	
	public String getNodecount() {
		return nodecount;
	}
	
	public void setNodecount(String nodecount) {
		this.nodecount = nodecount;
	}
	
	public String getAreadata() {
		return areadata;
	}
	
	public void setAreadata(String areadata) {
		this.areadata = areadata;
	}
	
	public String getAreacount() {
		return areacount;
	}
	
	public void setAreacount(String areacount) {
		this.areacount = areacount;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}
}
