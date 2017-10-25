package com.jfsl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfsl.action.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

@SuppressWarnings({ "unchecked" })
public class JSONOperator {

	private static String readAll(Reader rd) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		int flag=0;
		while ((cp = rd.read()) != -1) {
			if ((flag!=0)||((flag==0)&&((char)cp=='{')))
			{
				flag=1;
				sb.append((char) cp);
			}
		}
		return sb.toString();
	}
		
	public static JSONObject readJsonFromUrl(String url) throws IOException
	{
		InputStream is = new URL(url).openStream();
		try 
		{
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String jsonText = readAll(rd);
			JSONObject json = JSONObject.fromObject(jsonText);
			return json;
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally 
		{
			is.close();
		}
	}

	public static void jsonObject(JSONObject js,String pname,Object t)
	{
		Field[] fs=t.getClass().getDeclaredFields();
		for(int j=0;j<fs.length;j++)
		{
			Field f=fs[j];
			String fname=f.getName();
			try 
			{
				Method method=t.getClass().getMethod("get"+fname.substring(0,1).toUpperCase()+fname.substring(1));
				Object fvalue=method.invoke(t);
				if (fvalue!=null)
				{
					if (fvalue.getClass().getName().substring(0,4).equals("java"))
					{
						if (js.get(fname)==null) js.put(fname,fvalue);
						else js.put(pname+fname,fvalue);
					}
					else
						jsonObject(js,fname,fvalue);
				}
				else js.put(fname,"");
				
			} 
			catch (Exception e) 
			{
				js.put(fname,"");
			} 
		}
	}
	
	public static boolean hasChildren(List<JSONObject> ls,String id)
	{
		for(JSONObject jo:ls)
		{
			if (jo.getString("id").substring(0,jo.getString("id").length()-3).equals(id))
				return true;
		}
		return false;
	}	
	
	public static boolean hasChildren(List<JSONObject> ls,int id)
	{
		for(JSONObject jo:ls)
		{
			if (jo.getJSONObject("attributes").getInt("parentid")==id)
				return true;
		}
		return false;
	}
	
	public static String createJsonTree(List<JSONObject> ls,int style)//style:1为编码是字符串且包含父节点，2为编码是数值，父节点是单独表示的
	{
		List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
		if(style==1)
		{
			String id,parentid;
			for(JSONObject jo:ls)
			{
				id=jo.getString("id");
				if (hasChildren(ls,id)){		//是否有子节点
					jo.put("children", new JSONArray());		//创建下属子节点
					if(id.length()==3) jo.put("state", "true");	//原为大于等于3，修改为初节点展开，子节点不展开（changer：Sychel）
					else jo.put("state","closed");
				}
					
				
				parentid=id.substring(0,id.length()-3);
				if (parentid.length()==0) 
					jsonobjects.add(jo);
				else
					for(JSONObject e:jsonobjects)
					{
						if (AddToParent(e,jo,parentid))
							break;
					}
			}
		}
		else
		{
			int id,parentid;
			for(JSONObject jo:ls)
			{
				id=jo.getInt("id");
				parentid=jo.getJSONObject("attributes").getInt("parentid");
				if (hasChildren(ls,id)){
					jo.put("children", new JSONArray());
					if(parentid>0) jo.put("state", "closed");
				}

				if (parentid==0) 
					jsonobjects.add(jo);
				else
					for(JSONObject e:jsonobjects)
					{
						if (AddToParent(e,jo,parentid))
							break;
					}
			}
		}
		JSONArray ja=new JSONArray();
		for(JSONObject e:jsonobjects) ja.add(e);
		return ja.toString();
	}
	
	public static boolean AddToParent(JSONObject jo,JSONObject child,String parentid)
	{
		String id=jo.getString("id");
		if (id.equals(parentid))
		{
			jo.getJSONArray("children").add(child);
			return true;
		}
		else
		{
			if (jo.containsKey("children"))
			{		
				JSONArray ja=jo.getJSONArray("children");
				for(int i=0;i<ja.size();i++)
				{
					JSONObject j=ja.getJSONObject(i);
					AddToParent(j,child,parentid);
				}
			}
		}
		return false;
	}
	
	public static boolean AddToParent(JSONObject jo,JSONObject child,int parentid)
	{
		int id=jo.getInt("id");
		if (id==parentid)
		{
			jo.getJSONArray("children").add(child);
			return true;
		}
		else
		{
			if (jo.containsKey("children"))
			{		
				JSONArray ja=jo.getJSONArray("children");
				for(int i=0;i<ja.size();i++)
				{
					JSONObject j=ja.getJSONObject(i);
					AddToParent(j,child,parentid);
				}
			}
		}
		return false;
	}
	
	public static String jsonArray(List list)
	{	
		try{
			if (list==null) return "[]";
			else{
				JsonConfig jsonConfig = new JsonConfig();
				// json也时间的转换处理
				jsonConfig.registerJsonValueProcessor(java.util.Date.class,new JsonDateValueProcessor());
				jsonConfig.registerJsonValueProcessor(java.sql.Date.class,new JsonDateValueProcessor());
				jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
				jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
				return JSONArray.fromObject(list,jsonConfig).toString();
			}
		}catch(Exception e){
			e.printStackTrace();
			return "[]";
		}
	}
	
	public static String jsonCombobox(List list)
	{
		if (list==null) return "[]";
		else return JSONArray.fromObject(list).toString();
	}
	
	public static String jsonDataGrid(List list,int page,int rows)
	{
		if (list==null) return "{'total':0,'rows':[]}";
		else
		{
			JSONObject json=new JSONObject();
			JSONObject js;
			JSONArray ja=new JSONArray();
			json.put("total",String.valueOf(list.size()));
			if ((page==0)||(rows==0))
			{
				page=1;
				rows=list.size();
			}
	
			for(int i=(page-1)*rows;(i<page*rows)&&(i<list.size());i++)
			{
				JsonConfig jsonConfig = new JsonConfig();   //JsonConfig是net.sf.json.JsonConfig中的这个，为固定写法  
				jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());  
				js=JSONObject.fromObject(list.get(i),jsonConfig);
				ja.add(js);
			}
			json.put("rows",ja);
			
			return json.toString();
		}
	}
	
	public static String jsonObject(Object obj)
	{
		if (obj==null) return "{}";
		else{
			JsonConfig jsonConfig = new JsonConfig();
			// json也时间的转换处理
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(java.sql.Date.class,new JsonDateValueProcessor());
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			return JSONObject.fromObject(obj,jsonConfig).toString();
		}
	}
	
	public static String jsonObject(Map map)
	{
		if (map==null) return "{}";
		else return JSONObject.fromObject(map).toString();
	}
	
	public static String jsonTree(List list,String id,String text,boolean hasAttributes,int style)
	{
		if (list==null) return "[]";
		else
		{
			List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
			JSONObject jo;
			Object fvalue;
			Object obj;
			for(int i=0;i<list.size();i++)
			{
				obj=list.get(i);
				jo=new JSONObject();
				fvalue=getValue(obj,id);
				if (fvalue==null) jo.put("id","");
				else jo.put("id",fvalue);
				fvalue=getValue(obj,text);
				if (fvalue==null) jo.put("text","");
				else jo.put("text",fvalue);
				if (hasAttributes) jo.put("attributes",JSONObject.fromObject(obj));
				jsonobjects.add(jo);
			}
			
			return createJsonTree(jsonobjects,style);
		}
	}
	
	public static String jsonTree(List list,int id,int text,boolean hasAttributes,int style)
	{
		if (list==null) return "[]";
		else 
		{
			List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
			JSONObject jo;
			Object[] obj;
			for(int i=0;i<list.size();i++)
			{
				obj=(Object[])list.get(i);
				jo=new JSONObject();
				jo.put("id",(String)obj[id]);
				jo.put("text",(String)obj[text]);
				if (hasAttributes) jo.put("attributes",JSONObject.fromObject(obj));
				jsonobjects.add(jo);
			}
			
			return createJsonTree(jsonobjects,style);
		}
	}
	
	public static String jsonTree(List list,String id,String text,String iconCls,boolean hasAttributes,int style)
	{
		if (list==null) return "[]";
		else
		{
			List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
			JSONObject jo;
			Object fvalue;
			Object obj;
			for(int i=0;i<list.size();i++)
			{
				obj=list.get(i);
				jo=new JSONObject();
				fvalue=getValue(obj,id);
				if (fvalue==null) jo.put("id","");
				else jo.put("id",fvalue);
				fvalue=getValue(obj,text);
				if (fvalue==null) jo.put("text","");
				else jo.put("text",fvalue);
				fvalue=getValue(obj,iconCls);
				if (fvalue==null) jo.put("iconCls","");
				else jo.put("iconCls",fvalue);
				if (hasAttributes) jo.put("attributes",JSONObject.fromObject(obj));
				jsonobjects.add(jo);
			}
			
			return createJsonTree(jsonobjects,style);
		}
	}
	
	public static String jsonTree(List list,int id,int text,int iconCls,boolean hasAttributes,int style)
	{
		if (list==null) return "[]";
		else 
		{
			List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
			JSONObject jo;
			Object[] obj;
			for(int i=0;i<list.size();i++)
			{
				obj=(Object[])list.get(i);
				jo=new JSONObject();
				jo.put("id",(String)obj[id]);
				jo.put("text",(String)obj[text]);
				if (iconCls>=0) jo.put("iconCls",(String)obj[iconCls]);
				if (hasAttributes) jo.put("attributes",JSONObject.fromObject(obj));
				jsonobjects.add(jo);
			}
			
			return createJsonTree(jsonobjects,style);
		}
	}
	
	public static String jsonSingleTree(List list,String rootid,String roottext,int id,int text,boolean hasAttributes)
	{
		List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
		JSONObject root=new JSONObject();
		root.put("id",rootid);
		root.put("text", roottext);
		if (list==null) root.put("children",null);
		else 
		{
			List<JSONObject> children=new ArrayList<JSONObject>();
			JSONObject child;
			Object[] obj;
			for(int i=0;i<list.size();i++)
			{
				obj=(Object[])list.get(i);
				child=new JSONObject();
				child.put("id",(String)obj[id]);
				child.put("text",(String)obj[text]);
				if (hasAttributes) child.put("attributes",JSONObject.fromObject(obj));
				children.add(child);
			}
			root.put("children",children);
		}
		jsonobjects.add(root);
		return jsonobjects.toString();
	}
	
	public static String jsonSingleTree(List list,String rootid,String roottext,String id,String text,boolean hasAttributes)
	{
		List<JSONObject> jsonobjects=new ArrayList<JSONObject>();
		JSONObject root=new JSONObject();
		root.put("id",rootid);
		root.put("text", roottext);
		if (list==null) root.put("children",null);
		else 
		{
			List<JSONObject> children=new ArrayList<JSONObject>();
			JSONObject child;
			Object fvalue;
			Object obj;
			for(int i=0;i<list.size();i++)
			{
				obj=list.get(i);
				child=new JSONObject();
				fvalue=getValue(obj,id);
				if (fvalue==null) child.put("id","");
				else child.put("id",fvalue);
				fvalue=getValue(obj,text);
				if (fvalue==null) child.put("text","");
				else child.put("text",fvalue);
				if (hasAttributes) child.put("attributes",JSONObject.fromObject(obj));
				children.add(child);
			}
			root.put("children",children);
		}
		jsonobjects.add(root);
		return jsonobjects.toString();
	}
	
	public static Object getValue(Object obj,String fieldname)
	{
		try 
		{
			Method method=obj.getClass().getMethod("get"+fieldname.substring(0,1).toUpperCase()+fieldname.substring(1));
			Object fvalue=method.invoke(obj);
			return fvalue;
		}
		catch (Exception e) 
		{
			return null;
		}
		
	}
}
