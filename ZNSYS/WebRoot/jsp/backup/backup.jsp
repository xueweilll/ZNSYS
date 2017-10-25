<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<%
String begintime=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()); 
%>
<html>
  <head>
    <title>数据库备份</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var querywin,queryform;
		$(function(){
			 
			
			grid=$('#grid').datagrid
			({
				title:'数据库备份与还原',
				url:'JsonBackup',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[
				    {title:'id',field:'id',width:100,align:'center'},			    						
				    {title:'路径',field:'path',width:100,align:'center'},
				    {title:'时间',field:'creTime',width:100,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[{
					text:'创建新备份',
					iconCls:'icon-add',
					handler:addBackup					
					},{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteBackup
				},
				{
					text:'还原备份',
					iconCls:'icon-reload',
				    handler:restoreBackup
				}],
				onLoadError:function(){
					showLoadError();
				}
			 });
		});

		function addBackup()
		{
			 showConfirm('您确认要备份数据吗?',function(){
		$.ajax({
			url: "AddBackup",
			type: 'GET',
			timeout: 1000,
			success:function(data){
				eval(data);
				grid.datagrid('reload');
			}
		});
	});
			  
		}

		function deleteBackup()
		{
			
			var row = grid.datagrid('getSelected');
			if (row==null)
				showDelete();
			else{
				confirmDelete('DelBackup?id='+row.id);
			}		
	
		}

		function restoreBackup()
		{
			querywin.window('close');
		}
	</script>
</head>  
<body>
	<table id="grid"></table>
	 
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>