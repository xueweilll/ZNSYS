package com.jfsl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import jxl.Workbook;
import jxl.format.*;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelOperator 
{	
	private static final String basepath = "/WEB-INF/downloadfiles";
	
	public static String EncodingFilename(String filename)
	{
		String s="filename";
		try
		{
			s=new String(filename.getBytes(),"ISO8859-1");
		}
		catch (UnsupportedEncodingException e)
		{
		}
		return s;
	}
	
	static void AddTitle(WritableSheet ws,int i,int j,int l,String s)
	{
		try
		{
			WritableFont wf=new WritableFont(WritableFont.ARIAL,16,WritableFont.BOLD);
			WritableCellFormat wcf=new WritableCellFormat(wf);
			wcf.setAlignment(Alignment.CENTRE);
			ws.addCell(new Label(j,i,s,wcf));
			ws.mergeCells(0,0,l,0);
		}
		catch(Exception e){}		
	}
	
	static void AddCell(WritableSheet ws,int i,int j,String s,Alignment a)
	{
		try
		{
			WritableCellFormat wcf=new WritableCellFormat();
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcf.setAlignment(a);
			ws.addCell(new Label(j,i,s,wcf));
		}
		catch(Exception e){}
	}
	
	public static InputStream ExportExcel(String filename,String[] titles,List<String[]> ls)
	{
		WritableWorkbook wwb=null;
		WritableSheet ws=null;
		try 
		{
			String realpath=ServletActionContext.getServletContext().getRealPath(basepath);
			File savedir = new File(realpath);
			if (!savedir.exists())savedir.mkdirs();
			String fullfilename=realpath+"/"+filename+".xls";
			File file=new File(fullfilename);
			wwb=Workbook.createWorkbook(file);
			if(wwb!=null)
			{
				ws=wwb.createSheet(filename, 0);
				//添加标题
				AddTitle(ws,0,0,titles.length,filename);
				//添加列标题
				AddCell(ws,1,0,"序号",Alignment.CENTRE);
				for (int j=0;j<titles.length;j++) AddCell(ws,1,j+1,titles[j],Alignment.CENTRE);
				int i=1;
				//添加内容
				for(String[] s:ls)
				{
					AddCell(ws,i+1,0,String.valueOf(i),Alignment.CENTRE);
					for (int j=0;j<s.length;j++) AddCell(ws,i+1,j+1,s[j],Alignment.GENERAL);
					i++;
				}
				
				wwb.write();
				wwb.close();
				InputStream is=new FileInputStream(file);
				return is;
			}
			else
				return null;
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
	
	public static InputStream ExportExcel(String filename,String[] titles,String[] fields,JSONArray ja)
	{
		WritableWorkbook wwb=null;
		WritableSheet ws=null;
		try 
		{
			String realpath=ServletActionContext.getServletContext().getRealPath(basepath);
			File savedir = new File(realpath);
			if (!savedir.exists())savedir.mkdirs();
			String fullfilename=realpath+"/"+filename+".xls";
			File file=new File(fullfilename);
			wwb=Workbook.createWorkbook(file);
			if(wwb!=null)
			{
				String s="";
				ws=wwb.createSheet(filename, 0);
				//添加标题
				AddTitle(ws,0,0,titles.length,filename);
				//添加列标题
				AddCell(ws,1,0,"序号",Alignment.CENTRE);
				for (int j=0;j<titles.length;j++) AddCell(ws,1,j+1,titles[j],Alignment.CENTRE);
				//添加内容
				for(int i=0;i<ja.size();i++)
				{
					JSONObject jo=ja.getJSONObject(i);
					AddCell(ws,i+2,0,String.valueOf(i+1),Alignment.CENTRE);
					for (int j=0;j<fields.length;j++) 
					{
						try
						{
							String[] childfields=fields[j].split("\\.");
							if (childfields.length>1)
							{
								JSONObject job=jo.getJSONObject(childfields[0]);
								for (int k=1;k<childfields.length-1;k++)
								{
									if (job!=null) job=job.getJSONObject(childfields[k]);
									else break;
								}
								if (job!=null) s=job.getString(childfields[childfields.length-1]);
								else s="";
							}
							else
								s=jo.getString(fields[j]);
						}
						catch(Exception e)
						{
							s="";
						}
						if ((s==null)||(s.toLowerCase().equals("null"))) s="";
						AddCell(ws,i+2,j+1,s,Alignment.GENERAL);
					}
				}
				
				wwb.write();
				wwb.close();
				InputStream is=new FileInputStream(file);
				return is;
			}
			else
				return null;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

}
