package com.jfsl.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.DataDictionaryDAO;
import com.jfsl.dao.JsonDAO;
import com.jfsl.pojo.DataDictionary;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {	
		@Result(name = "doList", location = "jsp/area/ListArea.jsp")
		})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
		
public class ProduceJsonAction extends BaseAction
{
	private String TableName;
	private JsonDAO jdao=new JsonDAO();
	private DataDictionary dictionary;
	private DataDictionaryDAO ddao = new DataDictionaryDAO();
	
	public String getTableName() {
		return TableName;
	}
	
	public void setTableName(String TableName) {
		this.TableName = TableName;
	}
	
	public void setDictionary(DataDictionary dictionary) {
		this.dictionary = dictionary;
	}
	
	public DataDictionary getDictionary() {
		return dictionary;
	}
	
	/*@SuppressWarnings({ "unchecked", "null" })
	@Action(value = "JsonDatagrid")
	public void doList()
	{
		try{
		//System.out.print(TableName);
		dictionary  = ddao.Find(TableName);
		String jsonstr = dictionary.getStructure();
		//System.out.println(dictionary.getTableName()+"的structure:"+jsonstr);
		JSONArray obj = JSONArray.fromObject(jsonstr);//得到structure数组
        JSONObject jo = new JSONObject(); //返回的datagrid的json对象     
		List list=jdao.ListAll(TableName);//得到表中的所有数据 的list list是若干个Object[] 
		//System.out.println("从oracle中得到数据条数："+list.size());
		//System.out.println("从structure中得到表的字段个数："+obj.size());
		jo.put("total",list.size()); //list个数 是数组的个数
		JSONArray ja = new JSONArray();
		for(int i=0;i<list.size();i++){//将i条数据差进去
			JSONObject jjo=new JSONObject();
			Object[] o=(Object[])list.get(i);//第i条数据  o的结构为["","","","","",""]
			for(int j=0;j<obj.size();j++){ 
				//将o中的数据取出来
				
				if(o[j]!=null&&o[j]!=""){
					System.out.println(o[j].getClass().toString());
					if(o[j].getClass().toString().equals("class java.sql.Date")){
					//	System.out.println("时间戳");
						SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
					//	System.out.println(o[j]);
						String time=s.format(o[j]);
						//System.out.println("第"+i+"条数据，第"+j+"个字段值："+time);
						jjo.put(obj.getJSONObject(j).get("DName"),time);
					}
					else 
						{
					//	System.out.println("第"+i+"条数据，第"+j+"个字段值："+o[j]);
						jjo.put(obj.getJSONObject(j).get("DName"),o[j]);
						}
				}
				else
				{
				//	System.out.println("第"+i+"条数据，第"+j+"个字段值："+o[j]);
					jjo.put(obj.getJSONObject(j).get("DName"),o[j]);
				}
			}
			ja.add(jjo);							
		}
		jo.put("rows", ja);
		System.out.println(jo.toString());
		this.printJson(jo.toString());	}
		catch(Exception e){
			e.printStackTrace();
		 
		}
	}*/
	@Action(value="JsonDatagridBySCGC")
	public void jsondatagrid() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String ypph = URLDecoder.decode((String) request.getParameter("ypph"),"utf-8");
		String jcxm = URLDecoder.decode((String) request.getParameter("jcxm"),"utf-8");
		String scgc = URLDecoder.decode((String) request.getParameter("scgc"),"utf-8");
		//String csname = URLDecoder.decode((String) request.getParameter("csname"),"utf-8");
		String a=ddao.getDataBySCGC(ypph, jcxm, scgc);
		this.printJson(a);
	}
}