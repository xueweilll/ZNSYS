<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="./inc/nocache.inc"%>
<%@ include file="./inc/validateuser.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智能实验室协同管理平台欢迎页</title>
	<%@ include file="../../inc/easyui.inc"%>
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
		$(function(){
			$('#pp').portal({
				border:false,
				fit:true
			});
			add();
		});
		function add(){
			for(var i=0; i<3; i++){
				var p = $('<div/>').appendTo('body');
				p.panel({
					title:'Title'+i,
					content:'<div style="padding:5px;">Content'+(i+1)+'</div>',
					height:100,
					closable:true,
					collapsible:true
				});
				$('#pp').portal('add', {
					panel:p,
					columnIndex:i
				});
			}
			$('#pp').portal('resize');
		}
		function remove(){
			$('#pp').portal('remove',$('#pgrid'));
			$('#pp').portal('resize');
		}
	</script>
</head>
<body class="easyui-layout">
	<div region="north" class="title" border="false" style="height:60px;">
		jQuery EasyUI Portal
	</div>
	<div region="center" border="false">
		<div id="pp" style="position:relative">
			<div style="width:30%;">
				<div title="Clock" style="text-align:center;background:#f3eeaf;height:150px;padding:5px;">
					<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="100" height="100">
				      <param name="movie" value="http://www.respectsoft.com/onlineclock/analog.swf">
				      <param name=quality value=high>
				      <param name="wmode" value="transparent">
				      <embed src="http://www.respectsoft.com/onlineclock/analog.swf" width="100" height="100" quality=high pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" wmode="transparent"></embed>
				    </object>
			    </div>
			    <div title="Tutorials" collapsible="true" closable="true" style="height:200px;padding:5px;">
			    	<div class="t-list"><a href="http://www.w3cschool.cc/jeasyui/jeasyui-layout-layout.html">创建边框布局</a></div>
			    	<div class="t-list"><a href="http://www.w3cschool.cc/jeasyui/jeasyui-layout-panel.html">创建复杂布局</a></div>
			    	<div class="t-list"><a href="http://www.w3cschool.cc/jeasyui/jeasyui-layout-accordion.html">创建折叠面板</a></div>
			    	<div class="t-list"><a href="http://www.w3cschool.cc/jeasyui/jeasyui-layout-tabs1.html">创建标签页</a></div>
			    	<div class="t-list"><a href="http://www.w3cschool.cc/jeasyui/jeasyui-layout-tabs2.html">动态添加标签页</a></div>
					<div class="t-list"><a href="http://www.w3cschool.cc/jeasyui/jeasyui-layout-tabs3.html">添加自动播放标签页</a></div>
			    	<div class="t-list"><a href="http://www.w3cschool.cc/jeasyui/jeasyui-layout-xp.html">创建 XP 风格左侧面板</a></div>
			    </div>
			</div>
			<div style="width:40%;">
				<div id="pgrid" title="DataGrid" closable="true" style="height:200px;">
					<table class="easyui-datagrid" style="width:650px;height:auto"
							fit="true" border="false"
							singleSelect="true"
							idField="itemid" url="datagrid_data.json">
						<thead>
							<tr>
								<th field="itemid" width="60">Item ID</th>
								<th field="productid" width="60">Product ID</th>
								<th field="listprice" width="80" align="right">List Price</th>
								<th field="unitcost" width="80" align="right">Unit Cost</th>
								<th field="attr1" width="120">Attribute</th>
								<th field="status" width="50" align="center">Status</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<div style="width:30%;">
				<div title="Searching" iconCls="icon-search" closable="true" style="height:80px;padding:10px;">
					<input class="easyui-searchbox">
				</div>
				<div title="Graph" closable="true" style="height:200px;text-align:center;">
					<img height="160" src="http://knol.google.com/k/-/-/3mudqpof935ww/ip4n5y/web-graph.png"></img>
				</div>
			</div>
		</div>
	</div>
</body>
</html>