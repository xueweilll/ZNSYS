<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>常州市防汛信息集成平台</title>
 
<script>
	var initarea=7;
	var RealTimePlayOcx;
	var username="admin";
	var password="12345";
	var server=" 10.72.21.14";//"10.32.222.107";
	var port="0";
	$(function(){
		 
						RealTimePlayOcx.StartPlay(strGepID, nChnId);
					 
			 
		});

		$(window).unload(function(){
			 
		try
		{
			RealTimePlayOcx=document.getElementById("wmplay");
			RealTimePlayOcx.SetProxyNatInfo(1,0,"",0,"","");
			RealTimePlayOcx.PersonalConfig(16,1);
			RealTimePlayOcx.ConnectToServer(server,port,username,password);
		}
		catch(error){}
	})
	
	 
</script>
</head>  
<body>
				 <object id="wmplay" codeBase="webcudev.cab#version=1,1,0,3314" style="width:100%;height:100%" 
		   			classid="clsid:731B048F-7419-41FB-88C7-F74A852CF09A" >    																
		              <param name="_Version" value="65536">
		              <param name="_ExtentX" value="15875">
		              <param name="_ExtentY" value="13229">
		              <param name="_StockProps" value="0">    																
		   		 </object>
</body>
</html>
