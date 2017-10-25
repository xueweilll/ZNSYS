<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
<head>
	<title>角色管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var username='';
		var type;
		var win;
		var form;
		var grid;
		var depid;
		var style2="visibility:false;display:none";
		$(function(){
			grid=$('#grid').datagrid({
				fit:true,
				nowrap: false,
				striped: true,
				columns:[[
			        {title:'功能名称',field:'displayname',width:200,align:'center'},
					{title:'访问数据范围',field:'datascopenames',width:500,align:'center'}
				]],
				fitColumns:true,
				singleSelect:true,
				rownumbers:true,
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addOperation
				},{
					text:'编辑',
					iconCls:'icon-edit',
					handler:editOperation
				},{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteOperation
				}],
				onLoadError:function(){
					showError('加载数据发生错误！');
				}
			});
			$('#department').combotree({
				url:'JsonDepartment',
				onSelect:function(node)
				{
					$('#grid').datagrid('loadData',{total:0,rows:[]});
					$('#user').combobox({
						url:'JsonUsername?departmentid='+node.id
					});
					$('#user').combobox('setValue','请选择用户');
				}
			});
			$('#department').combotree('setValue','请选择部门');
			$('#user').combobox({
				valueField:'id',
				textField:'username',
				editable:false,
				onSelect:function(record)
				{
					if (record.id!='')
					{
						username=record.id;
						$('#grid').datagrid({
							url:'JsonUserOperation?username='+username
						});
					}
					else
					{
						$('#grid').datagrid('loadData',{total:0,rows:[]});
					}
				}
			});
			$('#user').combobox('setValue','请选择用户');
			
			win=$('#win1').window({
				title:"权限管理",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			form=win.find('form');
		});

		function addOperation()
		{
			
			if($('#user').combobox('getValue')=='请选择用户')
			{
				showWarning('请先选择要授权的用户！');
				return;
			}
			$('#tree1').tree({
				checkbox:true,
				url:'JsonExFunction?username='+username
			});
			var selected=$('#grid').datagrid('getSelected');
			$('#tree2').tree({
				checkbox:true,
				url:'JsonDepartment'
			});
			type="insert";
			$('#quanxian').attr('style',"");
			win.window('resize',{height:445});
			form.form('clear');
			win.window('open');
			form.url='InsertUserOperation';
		}
		function editOperation()
		{
			if($('#user').combobox('getValue')=='请选择用户')
			{
				showWarning('请先选择要修改权限的用户！');
				
				return;
			}
			var selected=$('#grid').datagrid('getSelected');
			if (selected==null)
				showWarning('请先选择要修改的权限！');
			else
			{
				$('#tree2').tree({
					checkbox:true,
					url:'JsonExDepartment?seldeps='+selected.datascopes
				});
				type="update";
				
				$('#quanxian').attr('style',style2);
				$('#username').val(username);
				$('#id').val(selected.id);
				win.window('resize',{height:300});
				win.window('open');
				form.url='UpdateUserOperation';
			}
		}
		function deleteOperation()
		{
			if($('#user').combobox('getValue')=='请选择用户')
			{
				showWarning('请先选择要修改的用户！');
				return;
			}
			var selected=$('#grid').datagrid('getSelected');
			if (selected==null)
				showWarning('请先选择要删除的权限！');
			else
				confirmDelete('DeleteUserOperation?id='+selected.id);
		}
		
		function save(){

			var ids1=[],ids2=[];
			if(type=="insert")
			{
				var nodes=$('#tree1').tree('getChecked');
				if (nodes.length==0)
				{
					showWarning('请选择权限！');
					return;
				}
				for(var i=0;i<nodes.length;i++)
				{
					if ($('#tree1').tree('isLeaf',nodes[i].target))
					
					{
						ids1.push(nodes[i].id);
					}
				}
			}
			nodes=$('#tree2').tree('getChecked');
			
			for(var i=0;i<nodes.length;i++)
			{
				var pnode=$('#tree1').tree('getParent',nodes[i].target);
					if((pnode==null)||((pnode!==null)&&(!pnode.checked)))
				{
					ids2.push(nodes[i].id);
				}
			}
			$('#username').val(username);
			$('#functionid').val(ids1);
			$('#datascopes').val(ids2);

			form.form('submit',{
				url:form.url,
				success:function(data){
					eval(data);
					grid.datagrid('reload');
					win.window('close');
				}
			});	
		}
		
		function cancel(){
			win.window('close');
		}
	</script>
</head>  
<body>
	<div class="easyui-panel" fit="true" title="权限管理">
	<label style="font-size:13px;font-weight:bold;color:#333333;">请选择部门:</label>
	<select id="department" style="width:180px;"></select>	
	<label style="font-size:13px;font-weight:bold;color:#333333;">请选择用户:</label>
	<select id="user" style="width:100px;"></select>	
	<table id="grid"></table>	
	</div>
	<div id="win1" style="width:auto;height:auto;">
		<div style="padding:0px 0px 0px 0px;">
			<form id="operation"  method="post">
			<input type="hidden" id="username" name="username"  />
			<input type="hidden" id="id" name="id" />
			<div id="quanxian">
			<div class="easyui-panel" title="请选择权限" style="border:2px solid #99BBE8;width:300;height:220;overflow:scroll;">			
				<ul id="tree1" ></ul>
			</div>
			</div>
			<input type="hidden" id="functionid" name="functionid" />
			<div class="easyui-panel" title="请选择部门" style="border:2px solid #99BBE8;width:300;height:140;overflow:scroll;">
				<ul id="tree2"></ul>
			</div>
			<input type="hidden" id="datascopes" name="datascopes" />
			<div align="center">
			<a href="#" class="easyui-linkbutton" icon="icon-save" onclick="save();">保存</a>
			<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="cancel();">取消</a>
			</div>
			</form>
			
		</div>
	</div>
	
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>