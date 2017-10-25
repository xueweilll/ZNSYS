<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="com.jfsl.pojo.User" %>
<%@ include file="./inc/nocache.inc"%>
<% 
User user=(User)session.getAttribute("user");
if (user!=null)
	response.sendRedirect("main.jsp");
String flag=(String)session.getAttribute("flag");
%>
<!DOCTYPE HTML>
<html dir="ltr" lang="zh-CN">
<head>
<meta charset="UTF-8" />
<title>智能实验室协同管理平台</title>
<meta name="keywords" content="智能实验室,协同管理,管理平台,新疆石油" />
<meta name="description" content="智能实验室,协同管理,管理平台,新疆石油" />
<meta name="Language" content="zh-CN" />
<meta name="Copyright" content="www.jsmjzl.com" />
<meta name="Designer" content="www.jsmjzl.com" />
<meta name="Publisher" content="www.jsmjzl.com" />
<meta name="Distribution" content="Global" />
<meta name="author"  content="Sychel" />
<meta name="robots" content="index,follow" />
<meta name="googlebot" content="index,follow,archive" />
<link rel="SHORTCUT ICON" href="favicon.ico">

<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/nobackspace.js"></script>
<script type="text/javascript" src="js/easyui/defaults.js"></script>
<script type="text/javascript" src="js/prompt/ymPrompt.js"></script>
<script type="text/javascript" src="js/formatutil.js"></script>
<link rel="stylesheet" id="skin" type="text/css" href="js/prompt/skin/ymPrompt.css" />
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
<script type="text/javascript" src="js/norightbutton.js"></script>
<link href="css/login.css" rel="stylesheet" type="text/css" />
<script>
	$(document).ready(function(){
		if (window!=$(window).attr('top'))
		{
			$($(window).attr('top')).attr('location','login.jsp');
			return;
		}

		<%
		if ((flag!=null)&&(flag.equals("useragain")))
		{
		%>
			showInfo('该用户已登录,无法同时使用同一个用户登录！',function(){$("#username").focus();});
		<%
		}
		if ((flag!=null)&&(flag.equals("failure")))
		{
		%>
			showInfo('用户名或者密码错误！',function(){$("#username").focus();});
		<%
		}
		if ((flag!=null)&&(flag.equals("notlogin")))
		{
		%>
			showInfo('未登录或登录已超时，请从登录窗口重新登录系统！',function(){$("#username").focus();});
		<%
		}
		if ((flag!=null)&&(flag.equals("norole")))
		{
		%>
			showInfo('该用户尚未分配权限，无法登录系统！',function(){$("#username").focus();});
		<%
		}
		%>

		$("#username").focus();//一进来自动获取焦点
});

		function checkValid(){
			if($.trim($("#username").val()).length==0)
			{
				showInfo('请输入用户名！',function(){$("#username").focus();});
				return;
			}	
			if($("#userpsw").val().length==0)
			{
				showInfo('请输入密码！',function(){$("#userpsw").focus();});
				return;
			}				
			$("#loginForm").submit();
		}
</script>
</head>

<body>
<div class="login">
	<div class="menus">
    	<div class="public"><a href="#" target="_blank">使用帮助</a><a href="http://www.901studio.com" target="_blank">联系开发者</a></div>
    </div>
    <div class="box png">
    	<form action="Login" mothed="POST" id="loginForm">
        	<div class="header">
            	<h2 class="logo png"><a href="#" target="_blank"></a></h2>
        	</div>
        	<ul>
            	<li>
            		<label>用户名</label>
            		<input name="username" id="username"  type="text" style="border:1px solid #CCC; padding:5px; background-color:#FCFCFC; line-height:14px; width:220px; font-size:12px; -webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px;-webkit-box-shadow: #CCC 0px 0px 5px;-moz-box-shadow: #CCC 0px 0px 5px;box-shadow: #CCC 0px 0px 5px; border:1px solid #CCC; font-size:12px;"/></li>
            	<li>
            		<label>密　码</label>
            		<input name="userpsw" id="userpsw"  type="password" style="border:1px solid #CCC; padding:5px; background-color:#FCFCFC; line-height:14px; width:220px; font-size:12px; -webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px;-webkit-box-shadow: #CCC 0px 0px 5px;-moz-box-shadow: #CCC 0px 0px 5px;box-shadow: #CCC 0px 0px 5px; border:1px solid #CCC; font-size:12px; "/></li> 
            	<li class="submits"><input style="padding:6px 20px;text-align:center;border:none; font-weight:bold;color:#FFF; margin-top:5px;-webkit-border-radius:3px;-moz-border-radius:3px;border-radius:3px;-webkit-box-shadow: #CCC 0px 0px 5px;-moz-box-shadow: #CCC 0px 0px 5px;box-shadow: #CCC 0px 0px 5px;background: #31b6e7; cursor: pointer;" type="submit" value="登录" onclick="checkValid()"/></li>
        	</ul>
        	<div class="copyright">
        		&copy; 2014-2020 | <a href="#" target="_blank" title="新疆克拉玛依试验检测研究院">新疆克拉玛依实验检测研究院版权所有</a>
        	</div>
        </form>
    </div>
    <div class="air-balloon ab-1 png"></div><div class="air-balloon ab-2 png"></div>
    <div class="footer"></div>
</div>
<script type="text/javascript" src="js/login/fun.base.js"></script>
<script type="text/javascript" src="js/login/login.js"></script>
<script src="./js/jquery-1.7.2.min.js"></script>

<!--[if lt IE 8]>
<script src="js/login/PIE.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    if (window.PIE && ( $.browser.version >= 6 && $.browser.version < 10 )){
        $('input.text,input.submit').each(function(){
            PIE.attach(this);
        });
    }
});
</script>
<![endif]-->

<!--[if IE 6]>
<script src="js/login/DD_belatedPNG.js" type="text/javascript"></script>
<script>DD_belatedPNG.fix('.png')</script>
<![endif]-->
</body>
</html>