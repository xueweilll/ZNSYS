<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.jfsl.pojo.*"%>
<html>
<head>
<title>通知公告</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="../../inc/easyui.inc"%>
<!--[if lt IE 10]><srcipt src="ie.js"></script><![endif]-->
<script type="text/javascript" src="../../js/jquery.portal.js"></script>
<link rel="stylesheet" href="../../css/portal.css" type="text/css"></link>
<style type="text/css">
	.title{
		font-size:16px;
		font-weight:bold;
		padding:20px 10px;
		background:#eee;
		overflow:hidden;
		border-bottom:1px solid #ccc;
	}
	.t-list{
		padding:5px;
	}
</style>

<script>
var win;
var grid1;
var grid2;
var win;

$(function(){
	$('#pp').portal({
		border:false,
		fit:true
	});
	//初始化大框架后，添加portal元素
    
	win=$('#display').window({
		closed: true,
	    modal: true,
	    closable: true,
	    minimizable: false,
	    maximizable: false,
	    collapsible: false,
	    resizable: false
	});
	
	grid1=$('#grid1').datagrid({
		title:'通知公告',
		url:'JsonMessage',
		fit:true,
		nowrap: false,
		rownumbers:true,
		striped: true,
		iconCls:'icon-mail',
		columns:[[				
			{title:'ID',field:'messageid',align:'center',hidden:'true'},    						
		    {title:'标题',field:'title',width:200,align:'left'},
		    {title:'发布者',field:'author',width:80,align:'center'},
		    {title:'内容',field:'content',width:400,align:'left'},
		    {title:'发布时间',field:'publishtime',width:100,align:'center'},
		    {title:'截止时间',field:'dateline',width:100,align:'center'}
		]],
		singleSelect:true,
		rownumbers:true,
		fitColumns:true,
		onLoadError:function(){
			showLoadError();
		},
		onClickRow:function(rowIndex,rowData){
			//alert(rowIndex + 1);
			$("#title").html(rowData.title);
			$("#author").html(rowData.author);
			$("#content").html(rowData.content);
			$("#publishtime").html(rowData.publishtime);
			$("#dateline").html(rowData.dateline);
			win.window('open');
		}
	 });
	//setInterval("grid1.datagrid('reload')",60000);
	var browser=navigator.appName;
	if(browser=="Microsoft Internet Explorer"){ 
		$("#timeContent").attr("style","display:none");
	}
});

/*function initPanel()
{
	var h=Math.round($(window).height()*0.4);
	var w=$(window).width();
	$('#p1').panel('resize',{'height':h,'width':w-20});
	$('#p2').panel('resize',{'height':h,'width':w-20});
}*/

//改变主题（覆盖了包中的方法）
function changeThemeFun(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for ( var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			$(ifr).contents().find('#easyuiTheme').attr('href', href);
			//$(ifr).contents().changeThemeFun(themeName);
		}
	}
}
</script>
<style>
#tb td{
	padding:3px;
	border:1px solid #99BBE8;
	align:center;
	vertical-align:top;
}
</style>
</head> 
<body>
	<!-- 门户 -->
	<div id="portal" style="position:relative">
        <div id="p1" style="height:220px;margin-bottom:10px;">
			<table id="grid1"></table>
        </div>
        <!--<div id="timeContent">
        	<div style="width:305px;float:left;" data-options="iconCls:'icon-save',closable:true,collapsible:true,minimizable:true,maximizable:true">
				<div class="easyui-panel" title="北京时间" data-options="iconCls:'icon-save',closable:true,collapsible:true,minimizable:true,maximizable:true">
					<embed src="../../js/clock115.swf" height="300"></embed>
				</div>
			</div>
			<div  style="width:815px;float:left;margin-left:10px;">
				<div class="easyui-panel" title="发展趋势" data-options="iconCls:'icon-save',closable:true,collapsible:true,minimizable:true,maximizable:true">
					<img src="tongji.jpg" height="290px"/>
					<img src="bingtu.png" height="210px" style="margin-bottom:40px"/>
				</div>
			</div>
			<div style="clear: both;"></div>
        </div>-->
        <div class="easyui-panel" title="发展趋势" data-options="iconCls:'icon-save',closable:true,collapsible:true,minimizable:true,maximizable:true">
			<img src="tongji.jpg" height="290px"/>
			<img src="bingtu.png" height="210px" style="margin-bottom:40px"/>
		</div>
    </div>
    
	<div id="display" style="width:800px;height:480px;padding:20px 20px 20px 20px;">
		<table width="100%" height="100%" id="tb">
			<tr height="20px">
				<td width="85px">
					标题名称：
				</td>
				<td id="title"></td>
			</tr>
			<tr height="20px">
				<td>
					消息来源：
				</td>
				<td id="author"></td>
			</tr>
			<tr height="20px">
				<td>
					通知时间：
				</td>
				<td id="publishtime"></td>
			</tr>
			<tr height="20px">
				<td>
					截止时间：
				</td>
				<td id="dateline"></td>
			</tr>
			<tr>
				<td>
					详细内容：
				</td>
				<td id="content"></td>
			</tr>
		</table>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>