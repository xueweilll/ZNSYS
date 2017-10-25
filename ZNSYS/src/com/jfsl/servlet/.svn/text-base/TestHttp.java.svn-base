package com.jfsl.servlet;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class TestHttp extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		 
		if(ServletFileUpload.isMultipartContent(request)){
			 try {
				this.doFile2(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().write("###Error:ended unexpectedly$$$");
			}
		}
		else{
		String value=getFromVB(request);
		if("error".equals(value)){
			response.getWriter().write("###Error:Data Format$$$");
		}
		else{
			System.out.println("发送的字符串："+value);
			String type=this.getType(value);
			if(type.equals("Login")){
				String IMEI=this.doLogin(value);
				response.setCharacterEncoding("GB2312");
				response.getWriter().write(IMEI);
			}else if(type.equals("Insert")){
				this.doInsert();
			}else if(type.equals("Delete")){
				this.doDelete();
			}else{
				response.getWriter().write("###Error:Data Format$$$");
			}
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.flush();
			out.close();
			}
		}
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
       this.doGet(request,response);
	}
	//获取传输字符串
	public String getFromVB(HttpServletRequest request) throws IOException{
		int i=request.getContentLength();
		DataInputStream in = new DataInputStream(request.getInputStream());
		int len=0;
		byte[] buffer=new byte[1024];
		String value=""; 
		while((len=in.read(buffer))>0){
		 value=new String(buffer,0,len);
		}
		System.out.println(value);
		if(value.lastIndexOf("#")!=-1&&value.indexOf("$")!=-1){
			int first=value.lastIndexOf("#");
			int last=value.indexOf("$");
		    String s=value.substring(first+1,last).replace(" ","");
			return s;
		}
		else
			return "error";
	}
	//获取类型
	public String getType(String value){
	    String type=String.valueOf(value.charAt(5));
	    System.out.println(type);
	    if("L".equals(type)){
	    	return "Login";
	    	
	    }
	    else if("I".equals(type)){
	    	return "Insert";
	    	
	    } else if("D".equals(type)){
	    	return "Delete";
	    }
	    else{
	    	return "error";
	    }
	   
	}
   //根据传输类型
	public String doLogin(String value){
		System.out.println("doLogin.."+value);
		String[] s=value.split(";");
		String username=s[1].substring(9);
		String psw=s[2].substring(9);
		System.out.println(username+":"+psw);
		
		/**
		 * 
		 * 数据库查找生成串号
		 * */
		
		return "###IMEI:65151491259756835416856275686452;User:邓泳$$$";
	}
	//插入
	public String doInsert(){
		/*
		 * 数据库插入或更新
		 */
		try{
			return "###Success$$$";
		}
		catch(Exception e){
			e.printStackTrace();
			return "###Error:Insert$$$";
		}
	   
	}
	//删除
	public String doDelete(){
		try{
			return "###Success$$$";
		}
		catch(Exception e){
			e.printStackTrace();
			return "###Error:Delete$$$";
		}
		
	}
	public void all(HttpServletRequest request) throws IOException{
		int i=request.getContentLength();
		DataInputStream in = new DataInputStream(request.getInputStream());
		int len=0;
		byte[] buffer=new byte[1024];
		String value=""; 
		while((len=in.read(buffer))>0){
		 value=new String(buffer,0,len);
		}
		System.out.println("all:"+value);
	}
	public void doFile(HttpServletRequest request) throws IOException{
		System.out.println("file tst");
		FileItemFactory factory = new DiskFileItemFactory();// 固定用法，创建一个FileItemFactory对象
	    ServletFileUpload upload = new ServletFileUpload(factory);// 固定用法，创建一个ServletFileUpload对象
	    Iterator<FileItem> items;// 定义一个保存所有表单数据的集合
	    try {
	    	items = upload.parseRequest(request).iterator();// 将表单数据全部赋值给items
	    	while (items.hasNext()) {// while循环迭代items，得到所有的表单数据
			      FileItem item = items.next();
			      if (!item.isFormField()) {// 判断此表单数据是否是file类型没事的话  就进行上传
			       String name = item.getName();// 得到file文件的名字
			       System.out.println(name);
			       String fileName = name.substring(name.lastIndexOf('\\') + 1, name.length());
			       System.out.println(fileName);
			       fileName = System.currentTimeMillis() + "_" + fileName;// 生成保存file的文件名，利用当前时间的毫秒值加上源文件的后缀名
			       String path = "F:/"+ fileName;// 生成保存该文件的路径名
			       System.out.println(path);
			       File uploadedFile = new File(path);
			       item.write(uploadedFile);// 保存文件
			      }
	     	}
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	public void doFile2(HttpServletRequest request,HttpServletResponse response) throws Exception{
		System.out.println("file tst2");
		
		try{
		 DiskFileItemFactory factory = new DiskFileItemFactory();
		 factory.setSizeThreshold(4096);
		 ServletFileUpload upload = new ServletFileUpload(factory); 
		 upload.setHeaderEncoding("gb2312");
		 List items = upload.parseRequest(request);
		 factory.setRepository(new File("D://temp"));
		 Iterator iter = items.iterator(); 
		 while( iter.hasNext() ){
			 FileItem item = (FileItem)iter.next();
			 //检查是一个普通的表单域还是File组件
			 if( !item.isFormField() ){

			 System.out.println("FileName:==>"+item.getName());

			 System.out.println("FieldName:==>"+item.getFieldName());

			 System.out.println("Size:==>"+item.getSize());

			 //item.getName 返回的是完整的文件名，

		 

			 //所以这里用一个fullFile来取文件名

			File fullFile = new File(item.getName());

			 File uploadedFile = new File("D://test//",fullFile.getName());

			 item.write(uploadedFile);

			 }

			 } 
		}
		catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("###Error:ended unexpectedly$$$");
		}
	}
}
