<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<%@ page import="com.jfsl.pojo.*"%>
<%@ page import="java.util.List" %>
<html>
  <head>
    <title>用户管理</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
	    var grid;
		var win;
		var form,formurl;
		var grid2;
		var win2;
		var form2;		
		var depid="001";
		$(function(){
			$('#tree1').tree({
				checkbox: false,
				url: 'JsonDepartment',
				onClick:function(node){
					depid=node.id;
					$('#grid').datagrid({'url':'JsonUser?departmentid='+depid});
					$('#grid').datagrid('reload');
				},
				onLoadError:function(){
					showError('加载数据发生错误JsonDepartment！');
				}
			});
			$('#departmentname').combotree({
				url:'JsonDepartment',
				valueField:'id',
				textField:'text'
			});

			$('#grid2').datagrid({
				loadMsg:'正在加载数据，请稍候...',
				nowrap: false,
				striped: true,
				idField:'roleid',
				columns:[[
			        {field:'ck',checkbox:true},	
			        {title:'角色名称',field:'rolename',width:100,align:'center'},
					{title:'描述',field:'description',width:200,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				fitColumns:true
			});
			 win2=$('#win2').window({
				title:"角色管理",
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			    });
			 form2=win2.find('form');
						
			  win=$('#user').window({
				title:"用户管理",
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			    });
			 //form=win.find('addform');

			grid=$('#grid').datagrid
			({
				title:'用户列表',
				url:'JsonUser?departmentid=001',
				fit:true,
				loadMsg:'正在加载数据，请稍候...',
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[				    						
				    {title:'用户名',field:'username',width:100,align:'center'},
				    {title:'姓名',field:'employeename',width:160,align:'center'},
				    {title:'部门',field:'departmentname',width:160,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				fitColumns:true,
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addUser
				},{
					text:'编辑',
					iconCls:'icon-edit',
					handler:editUser
				},{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteUser
				},{
					text:'修改密码',
					iconCls:'icon-edit',
					handler:ChangPsw
				}],
				onLoadError:function(){
					showError('加载数据发生错误grid！');
				}
			 });
		})
		
		function addUser()
		{
			hiddenAll();
			$('#add').attr('style',"padding:10px 10px 10px 20px;");
			if (depid==null)
			{
				showWarning('请先选择部门！');
				return;
			}
			form=$('#addform').form({
		        url:'InsertUser'
			});
			formurl="InsertUser";
			form.form('clear');
			$('#departmentid').val(depid);
			win.window('open');
		}
		function editUser()
		{
			hiddenAll();
			$('#changename').attr('style',"padding:10px 10px 10px 20px;");
			var row = grid.datagrid('getSelected');
			if (row==null)
				showWarning('请先选择要修改的记录！');
			else
			{
				form=$('#changnameform').form({
			        url:'UpdateUser'		        
				});
				formurl="UpdateUser";
				form.form('clear');	
				$('#name2').val(row.employeename);			
				$('#username2').val(row.username);
				$('#departmentname').combotree('setValue',row.departmentid);
				win.window('open');
				
			}
		}
		function deleteUser()
		{
			var row = grid.datagrid('getSelected');
			if (row==null)
				showWarning('请先选择要删除的记录！');
			else
				confirmDelete('DeleteUser?username='+row.username);
		}
		
		function hiddenAll()			//隐藏内容
		{
			$('#add').attr('style',"visibility:false;display:none");
			$('#changename').attr('style',"visibility:false;display:none");
			$('#changepsw').attr('style',"visibility:false;display:none");
		}
		
		function ChangPsw()
		{
			hiddenAll();
			$('#changepsw').attr('style',"padding:10px 10px 10px 20px;");
			var row = grid.datagrid('getSelected');
			if (row==null)
				showWarning('请先选择一个用户！');
			else
			{
				form=$('#changpswform').form({
			        url:'UpdateUser'		        
				});
				formurl="UpdateUser";
				form.form('clear');	
				$('#username3').val(row.username);
				
				win.window('open');
			}
		}
		function editUserRole()
		{
			var selected=grid.datagrid('getSelected');
			if (selected==null)
				showWarning('请先选择要修改的记录！');
			else
				{
				
				var ids=selected.roleids.split(",");
				$('#grid2').datagrid('clearSelections');
					$('#grid2').datagrid({'url':'JsonRole',
						onLoadSuccess:function(){
							var rows=$('#grid2').datagrid('getRows');
							for(var i=0;i<rows.length;i++){
								if($.inArray(rows[i].roleid,ids)>-1)
									$('#grid2').datagrid('selectRow',i);
							}
						}
						});
					
					win2.window('open');
				}
		}
		function save(){
			form.form('submit',{
				url:formurl,
				success:function(data){
					//alert("submit success!");
					eval(data);
					grid.datagrid('reload');
					win.window('close');
				}
			});
		}

		function save3(){
			var newpsw=$('#userpsw3').val();
			var repsw=$('#reuserpsw3').val();
			if(newpsw=="")
			{
				showWarning("请输入新密码");
				return;
			}
			if(newpsw!=repsw)
			{
				showWarning("两次输入密码不一致");
				return;
			}
			form.form('submit',{
				url:formurl,
				success:function(data){					
					eval(data);
					win.window('close');
				}
			});
		}
		
		function save2()
		{
			var ids = [];
			var rows = $('#grid2').datagrid('getSelections');
			for(var i=0;i<rows.length;i++)
			{
				ids.push(rows[i].roleid);
			}
			var selected=$('#grid').datagrid('getSelected');
			
			form2.form('submit',{
				url:'setUserRole?username='+selected.username+'&roleids='+ids,
				success:function(data){
					eval(data);
					grid.datagrid('reload');
					win2.window('close');
				}
			});
		}
		
		function cancel(){
			win.window('close');
			win2.window('close');
		}
		
		
	</script>
</head>  
<body class="easyui-layout">
	<div region="west" title="部门列表" border="true" style="width:200px;">
		<ul id="tree1"></ul>
	</div>
	<div region="center" border="false">
		<table id="grid"></table>
	</div>
	
	<div id="win2" style="width:500px;height:330px;">
		<div style="padding:10px 10px 10px 20px;">
			<h4>选择相应角色点击保存(单选)</h4>
			    <table id="grid2"></table>
			<br>
			<form id="function" method="post">
			<p align="center">
			<a href="#" class="easyui-linkbutton" icon="icon-save" onclick="save2();">保存</a>
			<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="cancel();">取消</a>
			</p>
			</form>
		</div>
	</div>
	<div id="user" style="width:420px;height:250px;">
	<div id="popup_content">
		<div id="add" style="padding:10px 10px 10px 20px;">
			<form method="post" id="addform">
				<input type="hidden" id="departmentid" name="departmentid"/>
				<table>
					<tr>
					<td align="right"><div align="right"><sup>*</sup>姓&nbsp;名：</div></td>
					<td>
						<input type="text" id="name" name="name"  style="width:200px;"/>
					</td></tr>
					<tr>
					<td align="right"><div align="right"><sup>*</sup>登陆名：</div></td>
					<td><input type="text" id="username" name="username" style="width:200px;"/>
					</td></tr>
					<tr>
					<td align="right"><div align="right"><sup>*</sup>密&nbsp;码：</div></td>
					<td><input type="password" id="userpsw" name="userpsw" style="width:200px;"/>
					</td>
					</tr>	
				</table>					
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">取消</a>
			</p>
			</div>
			
			<div id="changename" style="padding:40px 40px 40px 40px;">
			<form method="post" id="changnameform" >
				<input type="hidden" id="username2" name="username" style="width:200px;"/>
				<table>
					<tr>
					<td align="right"><div align="right"><sup>*</sup>员工姓名：</div></td>
					<td>
						<input type="text" id="name2" name="name"  style="width:200px;"/>
					</td></tr>
					<tr>
					<td align="right"><div align="right"><sup>*</sup>所在部门：</div></td>
					<td>
						<select type="text" readonly="true" name="departmentid" id="departmentname" style="width:200px;"></select>
					</td></tr>
					<tr>
					
				</table>
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">取消</a>
			</p>
			</div>
		
			<div id="changepsw" style="padding:10px 10px 10px 20px;">
			<form method="post" id="changpswform" >
				<input type="hidden" id="username3" name="username" style="width:150px;"/>
				<table>
					<tr>
					<td align="right"><div align="right"><sup>*</sup>新密码：</div></td>
					<td><input type="password" id="userpsw3" name="userpsw" style="width:200px;"/>
					</td>
					</tr>
					<tr>
					<td align="right"><div align="right"><sup>*</sup>确认密码：</div></td>
					<td><input type="password" id="reuserpsw3" style="width:200px;"/>
					</td>
					</tr>						
				</table>
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save3();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">取消</a>
			</p>
			</div>
	</div>
	</div>	
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>