package com.jfsl.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ApplicationAware;
//import org.apache.struts2.jasper.util.SystemLogHandler;
import org.omg.CORBA.Request;

import com.jfsl.dao.PushDAO;
import com.jfsl.pojo.*;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doList", location = "jsp/message/ListMessage.jsp")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class PushAction extends BaseAction{

	private int id;
	private String receiver;
	private String publisher;
	private String kind;
	private String title;
	private String content;
	private String url;
	private String publishtime;
	private String deadline;
	
	private Push push;
	private List<Push> pushs;

	private PushDAO pushdao = new PushDAO();
	
	/*Application 的定义与获取*/
	private HttpServletRequest request = null;
	private HttpSession session = null;
	private ServletContext  application = null;

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

	public String getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Push getPush() {
		return push;
	}

	public void setPush(Push push) {
		this.push = push;
	}

	public List<Push> getPushs() {
		return pushs;
	}

	public void setPushs(List<Push> pushs) {
		this.pushs = pushs;
	}

	public PushDAO getPushdao() {
		return pushdao;
	}

	public void setPushdao(PushDAO pushdao) {
		this.pushdao = pushdao;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public ServletContext getApplication() {
		return application;
	}

	public void setApplication(ServletContext application) {
		this.application = application;
	}

	@Action(value = "LoginPush")
	public void LoginPush(){
		request = ServletActionContext.getRequest();
		session = request.getSession();
		application = session.getServletContext();
		
		//登陆检测Application
		if(application.getAttribute("push") == null){
			pushs = pushdao.ListUsed();
			application.setAttribute("push", pushs);
		}
	}
	
	/**查询用户推送的操作*/
	@SuppressWarnings("unchecked")
	@Action(value = "GetPush")
	public void getMessage(){
		long startTime=System.currentTimeMillis();   //获取开始时间
		List<Push> plist = new ArrayList();
		int[] idlist = new int[50];
		
		request = ServletActionContext.getRequest();
		session = request.getSession();
		application = session.getServletContext();
		
		application.removeAttribute("push");
		/*获取Application*/
		if((pushs = (List<Push>)application.getAttribute("push"))==null){
			LoginPush();	//载入push
		}

		/*获取用户名*/
		User user = this.getPresentUser();
		String username = user.getUsername();
		
		if(session.getAttribute("push")==null){
			/*循环查找*/
			int i = 0;
			for(Push p : pushs){
				if("All".equals(p.getReceiver())){
					plist.add(p);
					idlist[i++] = p.getId();
				}
				else{
					if(username.equals(p.getReceiver())){
						plist.add(p);
						idlist[i++] = p.getId();
					}
				}
			}
		}
		else{
			idlist = (int[]) session.getAttribute("push");
			/*加入id的循环查找*/
			for(Push p : pushs){
				if("All".equals(p.getReceiver())){
					int flag = 0;
					int len = idlist.length;
					for(int i = 0;i<len;i++){
						if(idlist[i] == p.getId())flag++;
					}
					if(flag == 0){
						plist.add(p);
						idlist[len] = p.getId();
					}
				}
				else{
					if(username.equals(p.getReceiver())){
						int flag = 0;
						int len = idlist.length;
						for(int i = 0;i<len;i++){
							if(idlist[i] == p.getId())flag++;
						}
						if(flag == 0){
							plist.add(p);
							idlist[len] = p.getId();
						}
					}
				}
			}
		}
		//session.setAttribute("push", idlist);
		this.jsonArray(plist);
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("Push程序运行时间： "+(endTime-startTime)+"ms");
	}
	
	//@Action(value = "InsertPush")
	public Boolean InsertPush(Push p){
		try{
			pushdao.OracleAdd(p);
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean UpdatePush(int id,Push p){
		try{
			push = pushdao.Find(id);
			push.setContent(p.getContent());
			push.setDateline(p.getDateline());
			push.setKind(p.getKind());
			push.setPublisher(p.getPublisher());
			push.setPublishtime(p.getPublishtime());
			push.setReceiver(p.getReceiver());
			push.setTitle(p.getTitle());
			push.setUrl(p.getUrl());
			pushdao.OracleUpdate(push);
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean DeletePush(int id){
		try {
			push = pushdao.Find(id);
			pushdao.OracleDelete(push);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
