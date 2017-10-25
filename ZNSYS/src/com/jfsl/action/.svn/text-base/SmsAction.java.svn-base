package com.jfsl.action;

import java.util.List;
import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.AddListDAO;
import com.jfsl.dao.SmsDAO;
import com.jfsl.dao.SmsLogDAO;
import com.jfsl.pojo.AddList;
import com.jfsl.pojo.Sms;
import com.jfsl.pojo.SmsLog;

import sychel.com.util.SendMessage;		//发短信的工具接口

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {	
		@Result(name = "doList", location = "jsp/sms/ListSms.jsp")
		})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class SmsAction extends BaseAction
{
	private int id;
	private String content;
	private String begintime;
	private String endtime;
	private String ids;
	private String addlistids;
	private String tels;
	
	private Sms sms;
	private List<Sms> smses;
	private AddList adlist;
	private List<AddList> adlists;
	private SmsLog smsl;
	private List<SmsLog> smsls;
	
	private SmsDAO adao = new SmsDAO();
	private SmsLogDAO sld=new SmsLogDAO();
	private AddListDAO addlistdao = new AddListDAO(); 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getIds() {
		return ids;
	}

	public void setAddlistids(String addlistids) {
		this.addlistids = addlistids;
	}

	public String getAddlistids() {
		return addlistids;
	}

	public void setTels(String tels) {
		this.tels = tels;
	}

	public String getTels() {
		return tels;
	}
	
	public SmsLog getSmsl() {
		return smsl;
	}

	public void setSmsl(SmsLog smsl) {
		this.smsl = smsl;
	}

	public List<SmsLog> getSmsls() {
		return smsls;
	}

	public void setSmsls(List<SmsLog> smsls) {
		this.smsls = smsls;
	}
	
	public Sms getSms() {
		return sms;
	}

	public void setSms(Sms sms) {
		this.sms = sms;
	}

	public List<Sms> getSmses() {
		return smses;
	}

	public void setSmses(List<Sms> smses) {
		this.smses = smses;
	}

	public AddList getAdlist() {
		return adlist;
	}

	public void setAdlist(AddList adlist) {
		this.adlist = adlist;
	}

	public List<AddList> getAdlists() {
		return adlists;
	}

	public void setAdlists(List<AddList> adlists) {
		this.adlists = adlists;
	}

	public SmsDAO getAdao() {
		return adao;
	}

	public void setAdao(SmsDAO adao) {
		this.adao = adao;
	}

	public SmsLogDAO getSld() {
		return sld;
	}

	public void setSld(SmsLogDAO sld) {
		this.sld = sld;
	}

	public AddListDAO getAddlistdao() {
		return addlistdao;
	}

	public void setAddlistdao(AddListDAO addlistdao) {
		this.addlistdao = addlistdao;
	}

	@Action(value = "JsonSms")
	public void doJson()
	{
		adao.clearCurrentSession();
		smses = adao.listAll(getPresentUser().getUsername());
		this.jsonDataGrid(smses);
	}

	@Action(value = "ListSms")
	public String doList()
	{
		return "doList";
	}

	@Action(value = "InsertSms")
	public void doInsert()
	{
		try {
			sms = new Sms();
			sms.setSmstype("自定义短信");
			sms.setCreatetime(new Date());
			sms.setPoststate("编辑中");
			sms.setContent(content);
			sms.setUser(getPresentUser());
			adao.Add(sms);
			
			sld.insertByAddList(sms.getId(), addlistids);
			printString("短信已保存！");
		} catch (Exception e) {
			printString("编辑短信发生错误！");
		}
	}
	
	/**
	 * @param can send message , use smslib.3.5.2.jar ,sychel.com.util.SendMessage
	 * @author Sychel
	 * PhoneNumber 可以为很多号码的集合，中间用逗号隔开，并循环发送短信（群发功能）。
	 * SendMessage是一种静态的方法去处理，这样会留下很多隐患。但是没有办法，官方包自带的
	 * close方法根本没有用，只好这样来搞定。
	 * */
	public Boolean SendMessage(String PhoneNumber,String Content){
		int successnumber  = SendMessage.Send(PhoneNumber, Content);
		if(successnumber != 0) return true;
		else return false;
	} 
	
	@Action(value = "InsertAndSendSms")
	public void doInsertAndSend(){
		try {
			/*获取目标号码的id，并解析成为号码字符串*/
			String[] list  = addlistids.split(",");
			StringBuffer PhoneNumber = new StringBuffer();
			for(int i = 0;i<list.length;i++){
				int temp = Integer.parseInt(list[i]);
				adlist = addlistdao.find(temp);
				PhoneNumber.append(adlist.getTel() + ",");
			}
			PhoneNumber.deleteCharAt(PhoneNumber.length()-1);	//移除最后一个逗号
			
			/*调用服务发送短信*/
			if(this.SendMessage(PhoneNumber.toString(), content)){
				sms = new Sms();
				sms.setSmstype("自定义短信");
				sms.setCreatetime(new Date());
				sms.setPoststate("已提交");
				sms.setSendreqtime(new Date());
				sms.setContent(content);
				sms.setUser(getPresentUser());
				adao.Add(sms);	//添加到短信列表中，相当于保存
				
				sld.insertByAddList(sms.getId(), addlistids);		//添加到记录中，相当于日志
				printString("短信已发送！");
			}
			else{
				printString("发送短信发生错误！");
			}
			
		} catch (Exception e) {
			printString("发送短信发生错误！");
		}
	}
	
	@Action(value = "InsertAndSendMeetSms")
	public void doInsertAndSendMeet(){
		try {
			/*获取目标号码的id，并解析成为号码字符串*/
			String[] list  = addlistids.split(",");
			StringBuffer PhoneNumber = new StringBuffer();
			for(int i = 0;i<list.length;i++){
				int temp = Integer.parseInt(list[i]);
				adlist = addlistdao.find(temp);
				PhoneNumber.append(adlist.getTel() + ",");
			}
			PhoneNumber.deleteCharAt(PhoneNumber.length()-1);	//移除最后一个逗号
			
			/*调用服务发送短信*/
			if(this.SendMessage(PhoneNumber.toString(), content)){
				sms = new Sms();
				
				sms.setSmstype("报警通知");
				sms.setCreatetime(new Date());
				sms.setPoststate("已提交");
				sms.setSendreqtime(new Date());
				sms.setContent(content);
				sms.setUser(getPresentUser());
				adao.Add(sms);
				
				sld.insertByTels(sms.getId(), tels);
				printString("短信已发送！");
			}
			else{
				printString("发送短信发生错误！");
			}
		} catch (Exception e) {
			printString("发送短信发生错误！");
		}
	}

	@Action(value = "UpdateSms")
	public void doUpdate(){
		try {
			sms = adao.find(id);
			sms.setCreatetime(new Date());
			sms.setContent(content);
			adao.Update(sms);
			
			sld.delete(id);
			sld.insertByAddList(id, addlistids);
			printString("短信已保存！");
		} catch (Exception e) {
			printString("编辑短信发生错误！");
		}
	}
	
	@Action(value = "UpdateAndSendSms")
	public void doUpdateAndSend(){
		try {
			/*获取目标号码的id，并解析成为号码字符串*/
			String[] list  = addlistids.split(",");
			StringBuffer PhoneNumber = new StringBuffer();
			for(int i = 0;i<list.length;i++){
				int temp = Integer.parseInt(list[i]);
				adlist = addlistdao.find(temp);
				PhoneNumber.append(adlist.getTel() + ",");
			}
			PhoneNumber.deleteCharAt(PhoneNumber.length()-1);	//移除最后一个逗号
			
			/*调用服务发送短信*/
			if(this.SendMessage(PhoneNumber.toString(), content)){
				sms = adao.find(id);
				sms.setCreatetime(new Date());
				sms.setPoststate("已提交");
				sms.setSendreqtime(new Date());
				sms.setContent(content);
				adao.Update(sms);
				sld.delete(id);
				sld.insertByAddList(id, addlistids);
				printString("短信已发送！");
			}
			else printString("发送短信发生错误！");
		} catch (Exception e) {
			printString("发送短信发生错误！");
		}
	}
	
	@Action(value = "SendSms")
	public void doSend(){
		try {
			String[] idlist = ids.split(",");
			StringBuffer PhoneNumber = new StringBuffer();
			
			/*先找出需要发送的短信id，然后依据短信的数量循环发送若干短信*/
			for(int i=0;i<idlist.length;i++){
				int idtemp = Integer.parseInt(idlist[i]);
				sms = adao.find(idtemp);
				String Content = sms.getContent();
				smsls = sld.listAll(sms.getId());	//至此，拿到一种短信要发送的所有短信号码对象集合，开始拼凑号码
				for(SmsLog s :smsls){
					PhoneNumber.append(s.getTel() + ",");
				}
				PhoneNumber.deleteCharAt(PhoneNumber.length()-1);	//移除最后一个逗号
				System.out.println("短信仅仅发送功能：号码列表("+PhoneNumber.toString()+"),短信内容为："+Content);
				/*开始发送短信啦！！！！*/
				if(this.SendMessage(PhoneNumber.toString(), Content)){
					adao.send(ids);
					printString("短信已发送！");
				}
				else printString("短信发送失败！");
			}			
		} catch (Exception e) {}
	}

	@Action(value = "DeleteSms")
	public void doDelete()
	{
		try {
			adao.delete(ids);
			printString("短信已删除！");
		} catch (Exception e) {
			printString("删除短信发生错误！");
		}
	}

	@Action(value = "QuerySms")
	public void doQuery()
	{
		adao.clearCurrentSession();
		smses = adao.Query(content,begintime,endtime);
		this.jsonDataGrid(smses);
	}
}
