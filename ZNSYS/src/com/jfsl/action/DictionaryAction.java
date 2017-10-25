package com.jfsl.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.DataDictionaryDAO;
import com.jfsl.pojo.DataDictionary;
import com.jfsl.pojo.DataDictionaryPK;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {	
		@Result(name = "ListAddDictionary", location = "jsp/datadictionary/AddDictionary.jsp"),
		@Result(name = "CreTableSuccess", location = "common/cretablesuccess.jsp"),
		@Result(name = "CreTableError", location = "common/cretableerror.jsp"),
		@Result(name = "deleteTableSuccess", location = "common/deletetablesuccess.jsp"),
		@Result(name = "deleteTableError", location = "common/deletetableerror.jsp"),
		@Result(name = "UpdateTableSuccess", location = "common/updatetablesuccess.jsp"),
		@Result(name = "UpdateTableError", location = "common/updatetableerror.jsp"),
		@Result(name = "doManager", location = "jsp/datadictionary/ManagerDictionary.jsp"),
	})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class DictionaryAction extends BaseAction{
	private String table;
	private DataDictionaryDAO ddao=new DataDictionaryDAO();
	private String sjbmc;
	private String sjbhy;
	public DataDictionaryDAO getDdao() {
		return ddao;
	}
	public void setDdao(DataDictionaryDAO ddao) {
		this.ddao = ddao;
	}
	public String getSjbmc() {
		return sjbmc;
	}
	public void setSjbmc(String sjbmc) {
		this.sjbmc = sjbmc.toUpperCase();
	}
	public String getSjbhy() {
		return sjbhy;
	}
	public void setSjbhy(String sjbhy) {
		try {
			this.sjbhy = URLDecoder.decode(sjbhy,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("###ת�����:sjbhy");
			e.printStackTrace();
		}
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("###ת�����:table");
			e.printStackTrace();
		}
	}
	
	//�ַ�ת��ĺ���
	public String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        if(str != null) {
            //��Դ�ַ���������ַ���
            byte[] bs = str.getBytes(oldCharset);
            return new String(bs, newCharset);
        }
        return null;
    }
	
	@Action(value="ListAddDictionary")
	public String listDic(){
		System.out.println("dictionary");
		return "ListAddDictionary";
	}
	/*
	 * 
	 * ����� Ϊ���е����� ������Ϊ PK sqlΪ������� insertΪ���������ֵ����
	 * ��Datadictionary�������SJZD��
	 * ��������ƴд
	 */
   	@SuppressWarnings("unchecked")
	@Action(value="CreateTable")
	public String createTable(){
        try{
   		JSONObject jo=JSONObject.fromObject(table);
   		int total=jo.getInt("total");//���м���
   		DataDictionary[] dics=new DataDictionary[total];
   		JSONArray rows=jo.getJSONArray("rows");
   		String cresql="create table "+this.sjbmc+"(";//create���
   		List pks=new ArrayList();//��������
   		List fks=new ArrayList();//�������
   		for(int i=0;i<rows.size();i++)
   		{
   			JSONObject joo=rows.getJSONObject(i);
   			String pydm=joo.getString("pydm");
   			Integer xh=joo.getInt("xh");
   			String lx=joo.getString("lx");
   			String sjxmc=joo.getString("sjxmc");
   			Integer   kd    =joo.getInt("kd");
   			String kfwk="��";
   			String n="";
   			if("false".equals(joo.getString("kfwk")))
   				{
   				kfwk="��";
   				n="not null";
   				}
   			else
   				{
   				kfwk="��";
   				n="";
   				}
   			String  xsws   =joo.getString("xsws");
   			String  jldw    =joo.getString("jldw");
   			String  pk    ="��";
   			if("��".equals(joo.getString("pk"))){
   				pk="��";
   			}
   			String  ak    ="��";
   			if("��".equals(joo.getString("ak"))){
   				ak  ="��";
   			}
   			String  czb    =joo.getString("czb");
   			String  fkz    ="��";
   			if("��".equals(joo.getString("fkz"))){
   				fkz="��";
   			}
   			String  zys    =joo.getString("zys");
   			String  txgd    =joo.getString("txgd");
   			String  fkb =joo.getString("fkb");
   			String  fkzd=joo.getString("fkzd");
   			DataDictionaryPK dpk=new DataDictionaryPK();
   			dpk.setPYDM(pydm);
   			dpk.setSJBMC(sjbmc);
   			DataDictionary d=new DataDictionary();
   			d.setDicpk(dpk);
   			d.setAK(ak);
   			d.setCZB(czb);
   			d.setFKZ(fkz);
   			d.setJLDW(jldw);
   			d.setKD(kd);
   			d.setLX(lx);
   			d.setPK(pk);
   			d.setSJXMC(sjxmc);
   			d.setTXGD(txgd);
   			d.setXH(xh);
   			d.setXSWS(xsws);
   			d.setZYS(zys);
   			d.setFKB(fkb);
   			d.setFKZD(fkzd);
   			d.setKFWK(kfwk);
   			dics[i]=d;
   			//�������ݱ����ƴд
   			if("true".equals(pk))
   				pks.add(pydm);
   			if("true".equals(fkz))
   				{
   				String fk="FOREIGN KEY ("+pydm+") REFERENCES "+fkb+"("+fkzd+"),";
   				fks.add(fk);
   				}
   			cresql+=pydm+" "+lx+"("+kd+") "+n+", ";
   				
   		}
   		if(pks.size()!=0){
   			cresql+="primary key(";
   			for(int i=0;i<pks.size();i++){
   				cresql+=pks.get(i)+",";
   			}
   			cresql=cresql.substring(0,cresql.length()-1)+"),";
   		}
   		if(fks.size()!=0){
   			for(int i=0;i<fks.size();i++)
   				cresql+=fks.get(i);
   		}
   		int last=cresql.lastIndexOf(",");
   		cresql=cresql.substring(0,last)+")";
   		System.out.println(cresql);
   		ddao.createTable(cresql, dics);
   		return "CreTableSuccess";
        }
        catch(Exception e){
        	e.printStackTrace();
        	return "CreTableError";
        }
	}
@SuppressWarnings("unchecked")
@Action (value="UpdateTable")
	public String updatetable(){
	try{
   		JSONObject jo=JSONObject.fromObject(table);
   		int total=jo.getInt("total");//���м���
   		DataDictionary[] dics=new DataDictionary[total];
   		JSONArray rows=jo.getJSONArray("rows");
   		String cresql="create table "+this.sjbmc+"(";//create���
   		List pks=new ArrayList();//��������
   		List fks=new ArrayList();//�������
   		for(int i=0;i<rows.size();i++)
   		{
   			JSONObject joo=rows.getJSONObject(i);
   			String pydm=joo.getString("pydm");
   			Integer xh=joo.getInt("xh");
   			String lx=joo.getString("lx");
   			String sjxmc=joo.getString("sjxmc");
   			Integer   kd    =joo.getInt("kd");
   			String kfwk="��";
   			String n="";
   			if("false".equals(joo.getString("kfwk")))
   				{
   				kfwk="��";
   				n="not null";
   				}
   			else
   				{
   				kfwk="��";
   				n="";
   				}
   			String  xsws   =joo.getString("xsws");
   			String  jldw    =joo.getString("jldw");
   			String  pk    ="��";
   			if("��".equals(joo.getString("pk"))){
   				pk="��";
   			}
   			String  ak    ="��";
   			if("��".equals(joo.getString("ak"))){
   				ak  ="��";
   			}
   			String  czb    =joo.getString("czb");
   			String  fkz    ="��";
   			if("��".equals(joo.getString("fkz"))){
   				fkz="��";
   			}
   			String  zys    =joo.getString("zys");
   			String  txgd    =joo.getString("txgd");
   			String  fkb =joo.getString("fkb");
   			String  fkzd=joo.getString("fkzd");
   			DataDictionaryPK dpk=new DataDictionaryPK();
   			dpk.setPYDM(pydm);
   			dpk.setSJBMC(sjbmc);
   			DataDictionary d=new DataDictionary();
   			d.setDicpk(dpk);
   			d.setAK(ak);
   			d.setCZB(czb);
   			d.setFKZ(fkz);
   			d.setJLDW(jldw);
   			d.setKD(kd);
   			d.setLX(lx);
   			d.setPK(pk);
   			d.setSJXMC(sjxmc);
   			d.setTXGD(txgd);
   			d.setXH(xh);
   			d.setXSWS(xsws);
   			d.setZYS(zys);
   			d.setFKB(fkb);
   			d.setFKZD(fkzd);
   			d.setKFWK(kfwk);
   			dics[i]=d;
   			//�������ݱ����ƴд
   			if("true".equals(pk))
   				pks.add(pydm);
   			if("true".equals(fkz))
   				{
   				String fk="FOREIGN KEY ("+pydm+") REFERENCES "+fkb+"("+fkzd+"),";
   				fks.add(fk);
   				}
   			cresql+=pydm+" "+lx+"("+kd+") "+n+", ";
   				
   		}
   		if(pks.size()!=0){
   			cresql+="primary key(";
   			for(int i=0;i<pks.size();i++){
   				cresql+=pks.get(i)+",";
   			}
   			cresql=cresql.substring(0,cresql.length()-1)+"),";
   		}
   		if(fks.size()!=0){
   			for(int i=0;i<fks.size();i++)
   				cresql+=fks.get(i);
   		}
   		int last=cresql.lastIndexOf(",");
   		cresql=cresql.substring(0,last)+")";
   		System.out.println(cresql);
   		ddao.updateTable(sjbmc,cresql, dics);
   		return "UpdateTableSuccess";
        }
        catch(Exception e){
        	e.printStackTrace();
        	return "UpdateTableError";
        }
	}
	@Action(value="ListDataDictionary")
	public String managerdictionary(){
		return "doManager";
	}
//	@Action(value="JsonDatadictionary")
//	public void json(){
//		List<DataDictionary> l=ddao.ListAll();
//		this.printJson(JSONOperator.jsonDataGrid(l,0,0));
//	}
	@Action(value="DeleteDatadictionary")
	public String deleteDatadictionary(){
		try{
			ddao.deleteDatadictionary(sjbmc);
			return "deleteTableSuccess";
		}
		catch(Exception e){
			e.printStackTrace();
			return "deleteTableError";
		}			
	}
	/*
	 * 
	 * �����ݿ���Ĳ��� 
	 */
 
	@Action(value="jsonTable")
	public void jsontable(){
		this.printJson(ddao.jsonTable());
	}
	@Action(value="jsonColumn")
	public void jsoncolumn(){
		this.printJson(ddao.jsonColumn(sjbmc));
	}
	@Action(value="JsonDatadictionary")
	public void JsonDatadictionary(){
		this.printJson(ddao.list());
		
	}
	@Action(value="JsonDatadictionaryBysjbmc")
	public void JsonDatadictionaryBysjbmc(){
		try{
		List<DataDictionary> l=ddao.jsonbysjbmc(sjbmc);
		this.jsonDataGrid(l);}
		catch(Exception e){
			this.printJson("[]");
		}
	}
}