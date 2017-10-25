<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
<head>
	<title>通讯录分组表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var win,querywin;
		var form,queryform;		
		
		$(function(){	
			$('#areacode').combobox({
				url:'JsonAreaName',
				valueField:'code',
			    textField:'areacode',
			    panelHeight:'auto'
				    
			  querywin=$('#queryform').window({
				title:"查询条件",
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			queryform=querywin.find('form');
			  
				win=$('#smsgroupaddlistwin').window({
				title:"分组状况",
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			form=win.find('form');
										
			grid=$('#smsgroupaddlistgrid').datagrid({
			title:'通讯录分组表',
			url:'JsonSmsGroupAddList',
	        height:420,
			nowrap: false,
		    striped: true,
			columns:[[
			        {title:'ID',field:'id',width:40,align:'center'},
					{title:'分组ID',field:'groupid',width:80,align:'center'},
					{title:'通讯录ID',field:'addlistid',width:80,align:'center'}
				]],
			singleSelect:true,
			rownumbers:true,
			pagination:true,
			fitColumns:true,
			toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addSmsGroupAddList
				},{
					text:'编辑',
					iconCls:'icon-edit',
					handler:editSmsGroupAddList
				},{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteSmsGroupAddList
				}{
					text:'查询',
					iconCls:'icon-search',
				    handler:querySmsGroupAddList
				}],
				onLoadError:function(){
					showLoadError();
				}
			});
			adjuestTable(grid);
			$(window).resize(function(){
				adjuestTable(grid);
			});
     
		})
		
		function query()
		{
			var queryid1=$('#id1').val();
			grid.datagrid({url:'QuerySmsGroupAddList?GoodsSmsGroupAddListid1='+querySmsGroupAddListid1
				            });
            grid.datagrid('reload');
			querywin.window('close');
		}
		
		function querySmsGroupAddList(){
				queryform.form('clear');
				querywin.window('open');
			}

			
		function addSmsGroupAddList(){
			form.form('clear');
			win.window('open');
			form.url='InsertSmsGroupAddList';
		}
		
		function editSmsGroupAddList(){
			var row = grid.datagrid('getSelected');
			if (row){
				form.form('clear');
				$('#id').val(row.id);				
				$('#groupid').combobox('setValue',row.groupid);
				$('#addlistid').ombobox('setValue',row.addlistid);		
				win.window('open');
			}else{
				showUpdate();
			}
		}
		function deleteSmsGroupAddList(){
			var row = grid.datagrid('getSelected');
			if (row){
				confirmDelete('DeleteSmsGroupAddList?id='+row.id);
			}else{
				showDelete();
			}			
		}
		
		
		function save(){		
			if ($.trim($('#SmsGroupAddListid').val()).length==0)
			{
				showWarning('请输入ID！');
				$('#SmsGroupAddListid').focus();
				return;
			}
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
	<table id="smsgroupaddlistgrid"></table>
	<div id="smsgroupaddlistwin" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
			<form method="post">
				<input type="hidden" name="id" id="id"/>
				<table border="0">
				 <tr>
				    <td align="right"><div align="right"><sup>*</sup>ID：</div></td>
				    <td><input type="text" class="easyui-numberbox" name="id" id="id" style="width:180px;" /></td>
				    <td align="right"><div align="right"><sup>*</sup>分组ID：</div></td>
				    <td><input type="text" class="easyui-combobox" name="groupid" id="groupid" style="width:180px;" /></td>
				  </tr>
				  <tr>
				    <td align="right"><div align="right"><sup>*</sup>通讯录ID：</div></td>
				    <td><input type="text" name="addlistid" id="addlistid" style="width:180px;" /></td>
				  </tr>
		</table>
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">取消</a>
			</p>
		</div>
	</div>
	
	<div id="equeryform" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
			<form>
			<table>					
					 <tr>
				    <td align="right"><div align="right"><sup>*</sup>ID：</div></td>
				    <td><input type="text" name="id" id="id" style="width:180px;" /></td>
				    </tr>

			</table>
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-search" onClick="query();">查询</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel2();">取消</a>
			</p>
		</div>
	</div>
	
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>
