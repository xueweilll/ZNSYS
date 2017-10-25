package com.jfsl.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.DataDictionaryDAO;
import com.jfsl.dao.FlowDAO;
import com.jfsl.dao.ZGCDAO;
import com.jfsl.pojo.*;
import com.jfsl.util.JSONOperator;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doList", location = "jsp/flow/ListFlow.jsp"),
		@Result(name = "doJson", location = "jsp/flow/JsonFlow.jsp")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class FlowAction extends BaseAction
{

	private int id;
	private String title;
	private String linedata;
	private String linecount;
	private String nodedata;
	private String nodecount;
	private String areadata;
	private String areacount;
	private String author;
	private String publishtime;
	
	private Flow flow;
	private List<Flow> flows;
	
	private FlowDAO flowdao = new FlowDAO();
	private ZGCDAO zgcdao = new ZGCDAO();
	private DataDictionaryDAO dictionarydao = new DataDictionaryDAO();

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
	
	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public List<Flow> getFlows() {
		return flows;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	public FlowDAO getFlowdao() {
		return flowdao;
	}

	public void setFlowdao(FlowDAO flowdao) {
		this.flowdao = flowdao;
	}

	public ZGCDAO getZgcdao() {
		return zgcdao;
	}

	public void setZgcdao(ZGCDAO zgcdao) {
		this.zgcdao = zgcdao;
	}

	@Action(value = "ListFlow")
	public String ListFlow()
	{
		return "doList"; 
	}
	
	@Action(value = "JsonFlow")
	public String doJson()
	{
		//System.out.println("JsonMessage");
		flowdao.clearOracleCurrentSession();
		flows = flowdao.ListAll();
		//System.out.println(flows.get(0).getNodedata());
		return "doJson";
	}
	
	@Action(value = "ListFlowNode")
	public void ListFlowNode()
	{
		String result;
		//System.out.println("JsonMessage");
		dictionarydao.clearOracleCurrentSession();
		result = dictionarydao.jsonTable();
		//System.out.println(flows.get(0).getNodedata());
		this.printJson(result);
	}
	
	@Action(value = "InsertFlow")
	public String doInsert() throws UnsupportedEncodingException{

		/**乱码处理*/
		String Title = URLDecoder.decode(title,"utf-8");
		flow = flowdao.Find(Title);
		if(flow != null){
			//如果已经有了，就update
			try {
				/**乱码处理*/
				Title = URLDecoder.decode(title,"utf-8");
				flow.setTitle(Title);
				String LineData = URLDecoder.decode(linedata,"utf-8");
				flow.setLinedata(LineData);
				flow.setLinecount(linecount);
				String NodeData = URLDecoder.decode(nodedata,"utf-8");
				flow.setNodedata(NodeData);
				flow.setNodecount(nodecount);
				String AreaData = URLDecoder.decode(areadata,"utf-8");
				flow.setAreadata(AreaData);
				flow.setAreacount(areacount);
				String Author = URLDecoder.decode(author,"utf-8");
				flow.setAuthor(Author);
				
				/**获取一个时间戳*/
				SimpleDateFormat nowtime=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分 E  ");  
		        publishtime = nowtime.format(new Date());
		        flow.setPublishtime(publishtime);
		        
				flowdao.OracleUpdate(flow);
				return "update";
			} catch (Exception e) {
				return "error";
			}
		}else{
			try {
				flow = new Flow();
			
				/**乱码处理*/
				Title = URLDecoder.decode(title,"utf-8");
				flow.setTitle(Title);
				String LineData = URLDecoder.decode(linedata,"utf-8");
				flow.setLinedata(LineData);
				flow.setLinecount(linecount);
				String NodeData = URLDecoder.decode(nodedata,"utf-8");
				flow.setNodedata(NodeData);
				flow.setNodecount(nodecount);
				String AreaData = URLDecoder.decode(areadata,"utf-8");
				flow.setAreadata(AreaData);
				flow.setAreacount(areacount);
				String Author = URLDecoder.decode(author,"utf-8");
				flow.setAuthor(Author);
			
				/**获取一个时间戳*/
				SimpleDateFormat nowtime=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分 E ");  
				publishtime = nowtime.format(new Date());
				flow.setPublishtime(publishtime);
	        
				flowdao.OracleAdd(flow);
			
				return "add";
			} catch (Exception e) {
				return "error";
			}
		}
	}

	@Action(value = "UpdateFlow")
	public String doUpdate(){
		try {
			flow = flowdao.Find(id).get(0);
			
			/**乱码处理*/
			String Title = URLDecoder.decode(title,"utf-8");
			flow.setTitle(Title);
			String LineData = URLDecoder.decode(linedata,"utf-8");
			flow.setLinedata(LineData);
			flow.setLinecount(linecount);
			String NodeData = URLDecoder.decode(linedata,"utf-8");
			flow.setNodedata(NodeData);
			flow.setNodecount(nodecount);
			String AreaData = URLDecoder.decode(areadata,"utf-8");
			flow.setAreadata(AreaData);
			flow.setAreacount(areacount);
			String Author = URLDecoder.decode(author,"utf-8");
			flow.setAuthor(Author);
			
			/**获取一个时间戳*/
			SimpleDateFormat nowtime=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分 E  ");  
	        publishtime = nowtime.format(new Date());
	        flow.setPublishtime(publishtime);
	        
			flowdao.OracleUpdate(flow);
			return "update";
		} catch (Exception e) {
			return "error";
		}
	}
	
	@Action(value = "DeleteFlow")
	public String doDelete()
	{
		try {
			//System.out.println("title:"+title);
			flowdao.DeleteFlow(id);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}
	
	/**处理总过程中的进程
	 * @throws UnsupportedEncodingException 
	 * */
	@Action(value = "ZGC")
	public void doZGC() throws UnsupportedEncodingException{
		/**post 传值需要转码*/
		HttpServletRequest request = ServletActionContext.getRequest();
		String ypph = URLDecoder.decode((String) request.getParameter("ypph"),"utf-8");
		String jcxm = URLDecoder.decode((String) request.getParameter("jcxm"),"utf-8");
		String csname = URLDecoder.decode((String) request.getParameter("csname"),"utf-8");
		
		if(csname.length() == 0 || csname == null){
			try {
				//读取总过程信息
				List<?> zgclist = zgcdao.Find(ypph,jcxm);
				String zgcJson  = JSONOperator.jsonArray(zgclist);
				//读取Flow信息(以项目名称为总过程)
				flow = flowdao.Find(jcxm);
				String flowJson = JSONOperator.jsonObject(flow);
				//System.out.println(flowJson);
				
				//Ajax回执到result
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html"); // 火狐浏览器必须加上这句
				response.setCharacterEncoding("GBK");
				response.getWriter().write(zgcJson+"~");  //直接write到前台，struts.xml的result里什么都不用配
				response.getWriter().write(flowJson);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				//读取总过程信息
				List<?> zgclist = zgcdao.Find(ypph,jcxm,csname);
				String zgcJson  = JSONOperator.jsonArray(zgclist);
				//System.out.println(zgcJson);
				
				//读取Flow信息(以项目名称为总过程)
				flow = flowdao.Find(csname);
				String flowJson = JSONOperator.jsonObject(flow);
				//System.out.println(flowJson);
				
				//Ajax回执到result
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html"); // 火狐浏览器必须加上这句
				response.setCharacterEncoding("GBK");
				response.getWriter().write(zgcJson+"~");  //直接write到前台，struts.xml的result里什么都不用配
				response.getWriter().write(flowJson);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
