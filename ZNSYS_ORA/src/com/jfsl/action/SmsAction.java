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

import sychel.com.util.SendMessage;		//�����ŵĹ��߽ӿ�

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
			sms.setSmstype("�Զ������");
			sms.setCreatetime(new Date());
			sms.setPoststate("�༭��");
			sms.setContent(content);
			sms.setUser(getPresentUser());
			adao.Add(sms);
			
			sld.insertByAddList(sms.getId(), addlistids);
			printString("�����ѱ��棡");
		} catch (Exception e) {
			printString("�༭���ŷ�������");
		}
	}
	
	/**
	 * @param can send message , use smslib.3.5.2.jar ,sychel.com.util.SendMessage
	 * @author Sychel
	 * PhoneNumber ����Ϊ�ܶ����ļ��ϣ��м��ö��Ÿ�������ѭ�����Ͷ��ţ�Ⱥ�����ܣ���
	 * SendMessage��һ�־�̬�ķ���ȥ�������������ºܶ�����������û�а취���ٷ����Դ���
	 * close��������û���ã�ֻ���������㶨��
	 * */
	public Boolean SendMessage(String PhoneNumber,String Content){
		int successnumber  = SendMessage.Send(PhoneNumber, Content);
		if(successnumber != 0) return true;
		else return false;
	} 
	
	@Action(value = "InsertAndSendSms")
	public void doInsertAndSend(){
		try {
			/*��ȡĿ������id����������Ϊ�����ַ���*/
			String[] list  = addlistids.split(",");
			StringBuffer PhoneNumber = new StringBuffer();
			for(int i = 0;i<list.length;i++){
				int temp = Integer.parseInt(list[i]);
				adlist = addlistdao.find(temp);
				PhoneNumber.append(adlist.getTel() + ",");
			}
			PhoneNumber.deleteCharAt(PhoneNumber.length()-1);	//�Ƴ����һ������
			
			/*���÷����Ͷ���*/
			if(this.SendMessage(PhoneNumber.toString(), content)){
				sms = new Sms();
				sms.setSmstype("�Զ������");
				sms.setCreatetime(new Date());
				sms.setPoststate("���ύ");
				sms.setSendreqtime(new Date());
				sms.setContent(content);
				sms.setUser(getPresentUser());
				adao.Add(sms);	//��ӵ������б��У��൱�ڱ���
				
				sld.insertByAddList(sms.getId(), addlistids);		//��ӵ���¼�У��൱����־
				printString("�����ѷ��ͣ�");
			}
			else{
				printString("���Ͷ��ŷ�������");
			}
			
		} catch (Exception e) {
			printString("���Ͷ��ŷ�������");
		}
	}
	
	@Action(value = "InsertAndSendMeetSms")
	public void doInsertAndSendMeet(){
		try {
			/*��ȡĿ������id����������Ϊ�����ַ���*/
			String[] list  = addlistids.split(",");
			StringBuffer PhoneNumber = new StringBuffer();
			for(int i = 0;i<list.length;i++){
				int temp = Integer.parseInt(list[i]);
				adlist = addlistdao.find(temp);
				PhoneNumber.append(adlist.getTel() + ",");
			}
			PhoneNumber.deleteCharAt(PhoneNumber.length()-1);	//�Ƴ����һ������
			
			/*���÷����Ͷ���*/
			if(this.SendMessage(PhoneNumber.toString(), content)){
				sms = new Sms();
				
				sms.setSmstype("����֪ͨ");
				sms.setCreatetime(new Date());
				sms.setPoststate("���ύ");
				sms.setSendreqtime(new Date());
				sms.setContent(content);
				sms.setUser(getPresentUser());
				adao.Add(sms);
				
				sld.insertByTels(sms.getId(), tels);
				printString("�����ѷ��ͣ�");
			}
			else{
				printString("���Ͷ��ŷ�������");
			}
		} catch (Exception e) {
			printString("���Ͷ��ŷ�������");
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
			printString("�����ѱ��棡");
		} catch (Exception e) {
			printString("�༭���ŷ�������");
		}
	}
	
	@Action(value = "UpdateAndSendSms")
	public void doUpdateAndSend(){
		try {
			/*��ȡĿ������id����������Ϊ�����ַ���*/
			String[] list  = addlistids.split(",");
			StringBuffer PhoneNumber = new StringBuffer();
			for(int i = 0;i<list.length;i++){
				int temp = Integer.parseInt(list[i]);
				adlist = addlistdao.find(temp);
				PhoneNumber.append(adlist.getTel() + ",");
			}
			PhoneNumber.deleteCharAt(PhoneNumber.length()-1);	//�Ƴ����һ������
			
			/*���÷����Ͷ���*/
			if(this.SendMessage(PhoneNumber.toString(), content)){
				sms = adao.find(id);
				sms.setCreatetime(new Date());
				sms.setPoststate("���ύ");
				sms.setSendreqtime(new Date());
				sms.setContent(content);
				adao.Update(sms);
				sld.delete(id);
				sld.insertByAddList(id, addlistids);
				printString("�����ѷ��ͣ�");
			}
			else printString("���Ͷ��ŷ�������");
		} catch (Exception e) {
			printString("���Ͷ��ŷ�������");
		}
	}
	
	@Action(value = "SendSms")
	public void doSend(){
		try {
			String[] idlist = ids.split(",");
			StringBuffer PhoneNumber = new StringBuffer();
			
			/*���ҳ���Ҫ���͵Ķ���id��Ȼ�����ݶ��ŵ�����ѭ���������ɶ���*/
			for(int i=0;i<idlist.length;i++){
				int idtemp = Integer.parseInt(idlist[i]);
				sms = adao.find(idtemp);
				String Content = sms.getContent();
				smsls = sld.listAll(sms.getId());	//���ˣ��õ�һ�ֶ���Ҫ���͵����ж��ź�����󼯺ϣ���ʼƴ�պ���
				for(SmsLog s :smsls){
					PhoneNumber.append(s.getTel() + ",");
				}
				PhoneNumber.deleteCharAt(PhoneNumber.length()-1);	//�Ƴ����һ������
				System.out.println("���Ž������͹��ܣ������б�("+PhoneNumber.toString()+"),��������Ϊ��"+Content);
				/*��ʼ���Ͷ�������������*/
				if(this.SendMessage(PhoneNumber.toString(), Content)){
					adao.send(ids);
					printString("�����ѷ��ͣ�");
				}
				else printString("���ŷ���ʧ�ܣ�");
			}			
		} catch (Exception e) {}
	}

	@Action(value = "DeleteSms")
	public void doDelete()
	{
		try {
			adao.delete(ids);
			printString("������ɾ����");
		} catch (Exception e) {
			printString("ɾ�����ŷ�������");
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
