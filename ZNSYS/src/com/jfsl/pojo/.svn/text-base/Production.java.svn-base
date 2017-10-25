package com.jfsl.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "A_Production")
public class Production extends Pojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProjectID")
	private int projectID;
	@Column(name = "TestItem")
	private String testItem;
	@Column(name = "SubmitTime")
	private Date submitTime;
	@Column(name = "SendSample")
	private String sendSample;
	@Column(name = "JobAcceptSample")
	private String jobAcceptSample;
	@Column(name = "JobAnalysis")
	private String jobAnalysis;
	@Column(name = "JobCheck")
	private String jobCheck;
	@Column(name = "ReportApproval")
	private String reportApproval;
	@Column(name = "ReportAccept")
	private String reportAccept;
	@Column(name = "DataUpload")
	private String dataUpload;
	@Column(name = "CompleteProject")
	private String completeProject;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "AreaID")
	private Area area;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "WellID")
	private Well well;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ConsignorID")
	private Consignor consignorID;
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getTestItem() {
		return testItem;
	}
	public void setTestItem(String testItem) {
		this.testItem = testItem;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getSendSample() {
		return sendSample;
	}
	public void setSendSample(String sendSample) {
		this.sendSample = sendSample;
	}
	public String getJobAcceptSample() {
		return jobAcceptSample;
	}
	public void setJobAcceptSample(String jobAcceptSample) {
		this.jobAcceptSample = jobAcceptSample;
	}
	public String getJobAnalysis() {
		return jobAnalysis;
	}
	public void setJobAnalysis(String jobAnalysis) {
		this.jobAnalysis = jobAnalysis;
	}
	public String getJobCheck() {
		return jobCheck;
	}
	public void setJobCheck(String jobCheck) {
		this.jobCheck = jobCheck;
	}
	public String getReportApproval() {
		return reportApproval;
	}
	public void setReportApproval(String reportApproval) {
		this.reportApproval = reportApproval;
	}
	public String getReportAccept() {
		return reportAccept;
	}
	public void setReportAccept(String reportAccept) {
		this.reportAccept = reportAccept;
	}
	public String getDataUpload() {
		return dataUpload;
	}
	public void setDataUpload(String dataUpload) {
		this.dataUpload = dataUpload;
	}
	public String getCompleteProject() {
		return completeProject;
	}
	public void setCompleteProject(String completeProject) {
		this.completeProject = completeProject;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Well getWell() {
		return well;
	}
	public void setWell(Well well) {
		this.well = well;
	}
	public Consignor getConsignorID() {
		return consignorID;
	}
	public void setConsignorID(Consignor consignorID) {
		this.consignorID = consignorID;
	}
    
	
}
