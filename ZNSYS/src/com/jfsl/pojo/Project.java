package com.jfsl.pojo;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "A_Project")
public class Project extends Pojo {
	@Column(name = "ProjectPrincipal")
	private String projectPrincipal;
	@Column(name = "ProjectTime")
	private Date projectTime;
	@Column(name = "ProjectContent")
	private String projectContent;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ProjectID")
	private Project project;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "StepID")
	private Steps steps;
	public String getProjectPrincipal() {
		return projectPrincipal;
	}
	public void setProjectPrincipal(String projectPrincipal) {
		this.projectPrincipal = projectPrincipal;
	}
	public Date getProjectTime() {
		return projectTime;
	}
	public void setProjectTime(Date projectTime) {
		this.projectTime = projectTime;
	}
	public String getProjectContent() {
		return projectContent;
	}
	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Steps getSteps() {
		return steps;
	}
	public void setSteps(Steps steps) {
		this.steps = steps;
	}
	 
	 
	
}
