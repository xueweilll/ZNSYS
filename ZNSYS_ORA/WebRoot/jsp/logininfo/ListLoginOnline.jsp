<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<%@ page import="com.jfsl.pojo.User" %>
<%
User user=(User)session.getAttribute("user");
%>
<html>
  <head>
    <title>在线用户管理</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var operatelogwin,operateloggrid;
		$(function(){
			grid=$('#grid').datagrid
			({
				title:'在线用户管理',
				url:'JsonLoginOnline',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[				    						
				    {title:'用户名',field:'username',width:100,align:'center'},
				    {title:'姓名',field:'name',width:160,align:'center'},
				    {title:'部门',field:'departmentname',width:160,align:'center'},
				    {title:'IP地址',field:'ipaddress',width:200,align:'center'},
				    {title:'登录时间',field:'logintime',width:200,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				fitColumns:true,
				toolbar:[{
					text:'注销',
					iconCls:'icon-cancel',
					handler:logout
				},{
					text:'刷新',
					iconCls:'icon-reload',
					handler:function(){
						grid.datagrid('reload');
					}
				},{
					text:'操作日志',
					iconCls:'icon-doc',
					handler:operatelog
				}],
				onLoadError:function(){
					showLoadError();
				}
			 });

			operatelogwin=$('#operatelogform').window({
				title:"操作日志",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			operateloggrid=$('#operateloggrid').datagrid({
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[				    						
				    {title:'用户名',field:'username',width:100,align:'center'},
				    {title:'姓名',field:'name',width:100,align:'center'},
				    {title:'部门',field:'departmentname',width:100,align:'center'},
				    {title:'IP地址',field:'ipaddress',width:100,align:'center'},
				    {title:'操作时间',field:'operatetime',width:150,align:'center'},
				    {title:'操作内容',field:'operateobject',width:180,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				onLoadError:function(){
					showLoadError();
				}
			});
		});

		function logout()
		{
			var row = grid.datagrid('getSelected');
			if (row==null)
				showWarning('请先选择要注销的用户！');
			else{
				if ('<%=user.getUsername()%>'==row.username)
					showError('不能注销当前用户自己！');
				else
				{
					showConfirm('您确认要注销该用户吗?',function(){
						$.post('LogoutOnline?username='+row.username,function(){
							showInfo('该用户已注销！');
							grid.datagrid('reload');
						});
					});
				}
			}		
		}

		function operatelog()
		{
			var row=grid.datagrid('getSelected');
			if (row){
				operateloggrid.datagrid('loadData',{"total":0,"rows":[]});
				operateloggrid.datagrid({'pageNumber':1});
				operatelogwin.window('open');
				var username=row.username;
				var begintime=row.logintime;
				operateloggrid.datagrid({url:'JsonOperateLog?username='+username+'&begintime='+begintime});
				operateloggrid.datagrid('reload');		
			}else{
				showWarning('请先选择要查看的用户！');
			}			
		}
	</script>
</head>  
<body>
	<table id="grid"></table>
	
	<div id="operatelogform" style="width:900px;height:420px;">
		<table id="operateloggrid"></table>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>