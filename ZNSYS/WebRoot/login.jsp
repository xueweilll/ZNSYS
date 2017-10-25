<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="com.jfsl.pojo.User" %>
<%@ include file="./inc/nocache.inc"%>
<% 
User user=(User)session.getAttribute("user");
if (user!=null)
	response.sendRedirect("main.jsp");
String flag=(String)session.getAttribute("flag");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>智能实验室协同管理平台</title>
		<link rel="stylesheet" href="css/index.css" />
		<%@ include file="./inc/easyui.inc"%>
		<link rel="SHORTCUT ICON" href="favicon.ico">
		<script type="text/javascript">
			<%
			if ((flag!=null)&&(flag.equals("useragain")))
			{
			%>
				showInfo('该用户已登录,无法同时使用同一个用户登录！',function(){$("#username").focus();});
				if(!document.getElementById("auto").checked){
					DeleteCookie("autologin","/");
				}
			<%
			}
			if ((flag!=null)&&(flag.equals("failure")))
			{
			%>
				showInfo('用户名或者密码错误！',function(){$("#username").focus();});
				if(!document.getElementById("auto").checked){
					DeleteCookie("autologin","/");
				}
			<%
			}
			if ((flag!=null)&&(flag.equals("norole")))
			{
			%>
				showInfo('该用户尚未分配权限，无法登录系统！',function(){$("#username").focus();});
				if(!document.getElementById("auto").checked){
					DeleteCookie("autologin","/");
				}
			<%
			}
			%>
			$(function(){
				var browser=navigator.appName;
				/*if(browser=="Microsoft Internet Explorer" && (trim_Version=="MSIE6.0"trim_Version=="MSIE7.0")){ 
					alert("IE 6.0"); 
				} */
				
				if(browser=="Microsoft Internet Explorer"){ 
					$("#win").window("open");
				}
			
				var bodyHeight = document.body.clientHeight;
				var bodyWidth = document.body.clientWidth;
				
				$(".title,.titile_div").width(bodyWidth*3/6);
				//alert($(".title").width());
				
				if (window!=$(window).attr('top')){
					$($(window).attr('top')).attr('location','login.jsp');
					return;
				}
				$("#username").focus();//一进来自动获取焦点
				
				
			});
			
			//添加cookie
			function AddCookie(name,value,days,path){
				var name = escape(name);
				var value = escape(value);
				var expires = new Date();
				expires.setTime(expires.getTime() + days * 3600000 * 24);
				path = path == ""?"":";path=" + path;
				
				var _expires = (typeof days) =="string"?"":";expires="+expires.toUTCString();
				document.cookie = name + "=" +value + _expires + path;
			}
			
			//获取cookie
			function GetCookie(name){
				var name = escape(name);
				var allcookies = document.cookie;
				name += "=";
				var pos = allcookies.indexOf(name);
				if(pos != -1){
					var start = pos + name.length;
					var end = allcookies.indexOf(";",start);
					if(end == -1) end = allcookies.length;
					var value = allcookies.substring(start,end);
					return value;
				}else return "";
			}
			
			//删除cookie
			function DeleteCookie(name,path){
				var name = escape(name);
				var expires = new Date(0);
				
				path = path ==""?"":";path=" + path;
				document.cookie = name + "=" + ";expires="+expires.toUTCString()+path;
			}
			function checkValid() {
				if ($.trim($("#username").val()).length == 0) {
					alert('请输入用户名！');
					$("#username").focus();
					return;
				}
				if ($("#userpsw").val().length == 0) {
					alert('请输入密码！');
					$("#userpsw").focus();
					return;
				}
				var remenber = document.getElementById("remenber");
				if(remenber.checked){
					//alert("remenber");
					AddCookie("username",$("#username").val(),30,"/");
					AddCookie("password",$("#userpsw").val(),30,"/");
					if(document.getElementById("auto").checked){
						AddCookie("autologin","true",30,"/");
					}
				}else{
					DeleteCookie("username","/");
					DeleteCookie("password","/");
					if(!document.getElementById("auto").checked){
						DeleteCookie("autologin","/");
					}
				}
				$("#loginForm").submit();
			}
		</script>
	</head>
	<body class="back" height="100%">
		<div class="title_div">
			<img src="images/znsys.png"  class="title"/>
			<div class="form">
				<form action="Login" mothed="POST" id="loginForm">
					用户名：
					<input name="username" id="username"  type="text" class="input"/><br/><br>
					密&nbsp;&nbsp;码：
					<input name="userpsw" id="userpsw"  type="password" class="input"/>
					<div class="loginbutton" onclick="checkValid()">登陆</div>
					<div>
						<input type="checkbox" class="checkbox" id="remenber">记住我
						<input type="checkbox" class="checkbox" id="auto" style="margin-left: 30px;" onchange="document.getElementById('remenber').checked = true"/>自动登陆
					</div>
				</form>
			</div>
		</div>
		
		<div id="win" class="easyui-window" title="友情提示" data-options="iconCls:'icon-info',modal:false,closed:true" style="width:320px">
			<div style='font-size:1.4em;line-height:1.7em;'>
				<p style="text-indent:2em">低版本IE浏览器无法使用部分平台功能。建议使用360浏览器在极速模式下进行访问！</p>
				<p align="center">
				<a href='<%=request.getContextPath()%>/upload/360.exe' style="color:red;font-family:'黑体'">点击这里极速下载360浏览器</a></p>
			</div>
		</div>
	</body>
</html>
