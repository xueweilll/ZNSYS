<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>

<html>
<head>
<title>数据字典管理</title>
<%@ include file="../../inc/easyui.inc"%>

<script>
	var arraydata = new Array();
	var num = 0;
	var strid = 0;
	var grid;
	var detaildiv;
	$(function() {
		$('#detailgrid').datagrid({
			//title:'字段信息',
			fit : true,
			nowrap : false,
			rownumbers : true,
			striped : false,
			columns : [ [{title:'列名称(缩写)',field:'PYDM',width:100,align:'center',formatter : function(value, row, index) {return row.dicpk.PYDM;}},
			 			{title:'序号',field:'XH',width:60,align:'center'},
						{title:'数据类型',field:'LX',width:100,align:'center'},
						{title:'数据项名称',field:'SJXMC',width:100,align:'center'},
						{title:'数据长度',field:'KD',width:80,align:'center'},
						{title:'精确位数(浮点数选填)',field:'XSWS',width:150,align:'center'},
						{title:'计量单位(选填)',field:'JLDW',width:100,align:'center'},
						{title:'是主键',field:'PK',width:60,align:'center'},
						{title:'可否为空',field:'KFWK',width:60,align:'center'},
						{title:'是附键',field:'AK',width:60,align:'center'},
						{title:'关联表',field:'CZB',width:120,align:'center',editor:'text'},
						{title:'是否外键',field:'FKZ',width:60,align:'center'},
						{title:'外键表',field:'FKB',width:120,align:'center'},
						{title:'外键字段',field:'FKZD',width:120,align:'center'},
						{title:'值约束',field:'ZYS',width:120,align:'center'},
						{title:'提示信息',field:'TXGD',width:120,align:'center'}] ],
			singleSelect : true,
			rownumbers : true,
			//pagination:true,
			fitColumns : true,
			onLoadError : function() {
				showLoadError();
			}
		});

		detaildiv = $('#detaildiv').window({
			title : "具体信息",
			closed : true,
			modal : true,
			closable : true,
			minimizable : false,
			maximizable : false,
			collapsible : false,
			resizable : false
		});

		grid = $('#grid').datagrid(
				{
					title : '流程信息',
					url : 'JsonDatadictionary',
					fit : true,
					nowrap : false,
					rownumbers : true,
					striped : true,
					columns : [ [
							{
								title : '表名',
								field : 'sjbmc',
								width : 100,
								align : 'center'
							},
							{
								title : '具体字段',
								field : 'jtck',
								align : 'center',
								formatter : function(value, row, index) {
									return "<a onClick=look('" + value
											+ "') href='#' >点击查看</a>";
								}
							}, {
								title : '中文名',
								field : 'sjbhy',
								width : 100,
								align : 'center'
							} ] ],
					singleSelect : true,
					rownumbers : true,
					//pagination : true,
					fitColumns : true,
					toolbar : [ {
						text : '新增',
						iconCls : 'icon-add',
						handler : add
					}, '-', {
						text : '删除',
						iconCls : 'icon-remove',
						handler : remove
					}, '-', {
						text : '编辑',
						iconCls : 'icon-edit',
						handler : edit
					}, '-', {
						text : '刷新',
						iconCls : 'icon-reload',
						handler : reload
					} ],
					//显示加载错误
					onLoadError : function() {
						showLoadError();
					}
				});
		//调整自动适应表格
		adjuestTable(grid);
		$(window).resize(function() {
			adjuestTable(grid);
		});
	});

	function add() {
		$('#editdiv').dialog({
			title : '新增数据字典',
			width : '800',
			height : '400',
			closed : false,
			cache : false,
			fit:true,
			href : 'ListAddDictionary?sjbmc=adds',
			modal : true,
			method : 'post'
		}).dialog("open");
	}

	function remove() {
		var row = grid.datagrid('getSelected');
		if (row == null) {
			$.messager.alert('提示', '请您先选择要删除的信息', 'warning');
		} else {
			confirmDelete('DeleteDatadictionary?sjbmc=' + row.sjbmc);
		}
	}

	function reload() {
		grid.datagrid('reload');
	}

	function look(tablename) {
		$.getJSON("JsonDatadictionaryBysjbmc?sjbmc=" + tablename,
				function(json) {
					$('#detailgrid').datagrid('loadData', json);
					//detaildiv.window("open");
		});
		$('#detaildiv').dialog({
			title : '查看明细',
			width : '800',
			height : '400',
			closed : false,
			cache : false,
			fit:true,
			modal : true,
			method : 'post'
		}).dialog("open");

	}

	function edit() {
		var row = grid.datagrid('getSelected');
		if (row == null) {
			$.messager.alert('警告', '请您先选择要编辑的信息！', 'warning');
		} else {
			$('#editdiv').dialog({
				title : '编辑数据字典',
				width : '800',
				height : '400',
				fit:true,
				closed : false,
				cache : false,
				href : 'ListAddDictionary?sjbmc=' + row.sjbmc,
				modal : true,
				queryParams:{
					sjbhy:row.sjbhy
				},
				method : 'post'
			}).dialog("open");
		}
	}
</script>
</head>
<body>
	<table id="grid"></table>
	<div id="detaildiv">
		<table id="detailgrid"></table>
	</div>
	<div id="editdiv" ></div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>