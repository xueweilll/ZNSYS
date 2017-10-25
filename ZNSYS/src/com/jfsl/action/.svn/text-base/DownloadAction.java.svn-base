package com.jfsl.action;

import java.io.InputStream;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings({ "serial"})
@ParentPackage("default")
@Results({
	@Result(name = "doDownload", type = "streamx", params = { "contentType",
			"application/octet-stream;charset=ISO8859-1", "inputName",
			"fileStream", "bufferSize", "4096", "contentDisposition",
			"attachment;filename=${filename}" })
})
public class DownloadAction extends ActionSupport {
	
	private static final String basepath = "/WEB-INF/uploadfiles/";
	
	private String filename;
	
	private InputStream fileStream;
	
	public String getFilename() {
		String downfilename = filename;
		try
		{
			downfilename = new String(downfilename.getBytes(), "ISO8859-1");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return downfilename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public InputStream getFileStream() throws Exception
	{
		fileStream=ServletActionContext.getServletContext().getResourceAsStream(basepath+"/"+filename);
		return fileStream;
	}

	@Action(value = "DownloadFile")
	public String doDownload()
	{
		return "doDownload";
	}
}