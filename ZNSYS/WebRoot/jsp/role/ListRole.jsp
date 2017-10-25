<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
<head>
	<title>角色管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var win;
		var form;
		var grid;
		var win2;
		var form2;
		$(function(){
			
			win=$('#win1').window({
				title:"角色管理",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			form=win.find('form');
			win2=$('#win2').window({
				title:"角色授权",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			form2=win2.find('form');
			grid=$('#grid').datagrid({
				height:400,
				loadMsg:'正在加载数据，请稍候...',
				nowrap: false,
				striped: true,
				url:'JsonRole',
				columns:[[
			        {title:'角色名称',field:'rolename',width:200,align:'center'},
					{title:'描述',field:'description',width:300,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				fitColumns:true,
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addRole
				},{
					text:'编辑',
					iconCls:'icon-edit',
					handler:editRole
				},{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteRole
				},
				{
					text:'授权',
					iconCls:'icon-reload',
					handler:grantRole
				}]
			});
		})
		function addRole()
		{
			form.form('clear');
			win.window('open');
			form.url='InsertRole';
		}
		function editRole()
		{
			var selected=$('#grid').datagrid('getSelected');
			if (selected==null)
				$.messager.alert('警告','请先选择要修改的角色！','warning');
			else
			{
				form.form('clear');
				$('#id').val(selected.roleid);
				$('#rolename').val(selected.rolename);
				$('#description').val(selected.description);
				win.window('open');
				form.url='UpdateRole';
			}
		}
		function deleteRole()
		{
			var selected=$('#grid').datagrid('getSelected');
			if (selected==null)
				$.messager.alert('警告','请先选择要删除的角色！','warning');
			else
			{	
				var rowid=selected.roleid;
				$.messager.confirm('提示信息','您确认要删除该记录吗?',function(data){
					if(data){
						$.ajax({
							url: 'DeleteRole?roleid='+rowid,
							type: 'GET',
							timeout: 1000,
							success:function(data){
								grid.datagrid('reload');
							}
						});
					}
				});
			}
		}
		function grantRole()
		{
			var selected=$('#grid').datagrid('getSelected');
			if (selected==null)
				$.messager.alert('警告','请先选择要授权的角色！','warning');
			else
			{				
				$('#tree').tree({
					checkbox:true,
					url:'JsonGrantedFunction?roleid='+selected.roleid
				});
				form2.form('clear');
				win2.window('open');
			}
		}
		function save(){
			form.form('submit',{
				url:form.url,
				success:function(data){
					grid.datagrid('reload');
					win.window('close');
				}
			});
		}
		
		function cancel(){
			win.window('close');
			win2.window('close');
		}
		
		function save2()
		{
			var ids = [];
			var nodes = $('#tree').tree('getChecked');
			for(var i=0;i<nodes.length;i++)
			{
				if ($('#tree').tree('isLeaf',nodes[i].target))
				{
					ids.push(nodes[i].id);
				}
			}
			var selected=$('#grid').datagrid('getSelected');
			form2.form('submit',{
				url:'GrantFunction?roleid='+selected.roleid+'&functionids='+ids,
				success:function(data){
					grid.datagrid('reload');
					win2.window('close');
				}
			});
		}
	</script>
</head>  
<body>
	<div id="title"><a style="margin-left:30px;">角色管理</a></div>
	<table id="grid"></table>
	
	<div id="win1" style="width:350px;height:200px;">
		<div style="padding:10px 10px 10px 40px;">
			<form id="form1" method="post">
			   <input id="id" name="roleid" type="hidden"/> 	
				<table>	
					<tr>
					<td><div align="right"><sup>*</sup>角色名称：</div></td>
					<td><input type="text" id="rolename" name="rolename"/><br></td>					
					</tr>
					<tr>
					<td align="right"><div align="right"><sup>*</sup>描述：</div></td>
					<td><input type="text" id="description" name="description"/><br></td>					
					</tr>					
				</table>
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">取消</a>
			</p>
		</div>
	</div>
	
	<div id="win2" style="width:380px;height:420px; ">
		<div style="padding:10px 10px 10px 40px;"><div align="right"><sup>*</sup>请选择权限:</div>
			
			<div style="border:2px solid #99BBE8;width:285;height:285;overflow:scroll;">
			<ul id="tree"></ul>
			</div>
			<form id="function" method="post">
			<div align="center">
			<a href="#" class="easyui-linkbutton" icon="icon-save" onclick="save2();">保存</a>
			<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="cancel();">取消</a>
			</div>
			</form>
		</div>
	</div>	
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>