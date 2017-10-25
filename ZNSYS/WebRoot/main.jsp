<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="./inc/nocache.inc"%>
<%@ include file="./inc/validateuser.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>智能实验室协同管理平台</title>
		<%@ include file="../../inc/easyui.inc"%>
		<link rel="stylesheet" href="css/index_main.css" />
		<link rel="SHORTCUT ICON" href="favicon.ico">
		<style>
			.messager-body{
				height:200px !important;
			}
		</style>
		<script type="text/javascript">
			var win;
			var push;
			
			$(function(){
				win = $('#win').window({
					closed: true,
					title:"请选择菜单类型",
				    modal: true,
				    closable: true,
				    minimizable: true,
				    maximizable: true,
				    collapsible: true
				});
				
				//加载消息推送
				$.ajax({
					type: "GET",
					url: "GetPush",
					dateType: "json",			
					success: function(data,status){
						//alert(JSON.stringify(data));
						push = eval(data);
						index=0;
						TimeOut();
					}
				});
			});
			
			function TimeOut(){
				//alert(JSON.stringify(push[index]));
				index++;
				if(index == 1) var time = 0;
				else var time = 7000;
				if(index<=push.length) setTimeout(function(){Push(push[index-1]);TimeOut();},time);
			}
			
			function Click(number){
				var content = "";
				var array = [];
				//格式{img:"001007",link:"001007",text:"实验流程定义"}
				switch(number){
					case 2:array.push({img:"002001",link:"002001",text:"生产过程监控"});array.push({img:"001007",link:"001015",text:"生产过程定义"});array.push({img:"001009",link:"001017",text:"生产过程管理"});break;
					case 3:array.push({img:"006001",link:"006001",text:"设备信息管理"});array.push({img:"001007",link:"006002",text:"设备保养维护"});break;
					case 5:array.push({img:"001001",link:"005001",text:"通讯录管理"});array.push({img:"001006",link:"005002",text:"分组管理"});array.push({img:"001003",link:"005003",text:"短消息编辑与发送"});array.push({img:"001005",link:"005004",text:"历史消息查看"});break;
					case 6:array.push({img:"003001",link:"003002",text:"实验桌面远程监控"});array.push({img:"003002",link:"003001",text:"监控设置"});break;
					case 10:array.push({img:"004001",link:"004001",text:"视频监控"});array.push({img:"004002",link:"004002",text:"视频设置"});array.push({img:"004003",link:"004003",text:"房间管理"});break;
					default:alert("参数错误！");
				}
				for(var a in array){
					//alert(array[a].img);
					content+="<td style='padding:0px 15px' onclick=\"window.location.href='index.jsp?menu="+
					array[a].link+"'\"><img src='images/96/"+
					array[a].img+".png'/><div style='text-align:center;font-size:1.5em;line-height:2em;font-weight:700'>"+
					array[a].text+"</div></td>";
				}
				$("#content").html("");
				$("#content").html(content);
				win.window("open");
			}
			
			function Push(push){
				//alert(JSON.stringify(push));
				var message = "<p'><b>来源：</b>"+push.kind+
				"<br/><b>内容：</b>"+push.content+
				"<br/><b>链接：</b><a href=\"javascript:addTab('"+push.kind+"','"+push.url+"','icon-0202',true,false)\">点击查看</a>"+
				"<br/><b>时间：</b>"+push.publishtime+"~"+push.dateline+"</p>";
				$.messager.show({
					icon:"info",
					title:push.title,
					msg:message,
					showSpeed:2000,
					width:300,
					timeout:5000,
					showType:'show'
				});
			}
		</script>
	</head>
	<body>
		<div class="head"></div>
		<div class="content">
			<div class="left">
				<div class="title">  
					智<br/>能<br/>实<br/>验<br/>室<br/>协<br/>同<br/>管<br/>理<br/>平<br/>台
				</div>
			</div>
			<div class="center">
				<div class="img">
					<img src="images/main/001.png" onclick="window.location.href='index.jsp?menu=010'">
					<img src="images/main/002.png" onclick="Click(2)">
					<img src="images/main/003.png" onclick="Click(3)">
					<img src="images/main/004.png" onclick="window.location.href='index.jsp?menu=008'"><br>
					<img src="images/main/005.png" onclick="Click(5)">
					<img src="images/main/006.png" onclick="Click(6)">
					<img src="images/main/007.png" onclick="">
					<img src="images/main/008.png" onclick="window.location.href='index.jsp?menu=009'"><br>
					<img src="images/main/009.png" onclick="alert('该系统尚未集成！')">
					<img src="images/main/010.png" onclick="Click(10)">
					<img src="images/main/011.png" onclick="window.location.href='index.jsp?menu=001001'">
					<img src="images/main/012.png" style="width:200px;height:200px;margin-top: -10px;margin-left: -10px;" onclick="alert('正在开发中！')">
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="win" style="height:200px;width:650px;">
			<div style="margin:10px;text-align:center">
				<table style="margin:0px auto;">
					<tr id="content">
					
					</tr>
				</table>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="js/index_mian.js" ></script>
</html>
