<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>智能实验室协同管理平台</title>
<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var win,querywin;
		var form,queryform;
		$(function(){
			win=$('#roomwin').window({
				title:"桌面监控房间设置",
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			form=win.find('form');
			
			grid=$('#roomgrid').datagrid({
				title:'视频参数',
				fit:true,
				url:'JsonRoom',
				nowrap: false,
				striped: true,
				columns:[[
					{title:'id',field:'id',align:'center',hidden:true},
			        {title:'房间名称',field:'roomname',width:120,align:'center'},
					{title:'备注',field:'memo',width:400,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addRoom
					
				},{
					text:'编辑',
					iconCls:'icon-edit',
					handler:editRoom
					
				},{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteRoom
				}]
			});
		})
		
		function addRoom(){
			form.form('clear');
			win.window('open');
			$('#code').focus();
		}
		
		function save(){
			if (!$('#roomname').validatebox('isValid')) return;
			form.form('submit',{
				url:'InsertRoom',
				success:function(data){
					eval(data);
					grid.datagrid('reload');
					win.window('close');
				}
			});
		}
		
		function editRoom(){
			var row = grid.datagrid('getSelected');
			if (row==null)
				showUpdate();
			else{
				form.form('clear');
				$('#id').val(row.id);
			    $('#roomname').val(row.roomname);
				$('#memo').val(row.memo);
				win.window('open');
				form.url='UpdateRoom';
				$('#roomname').focus();			//人性化设计
			}
		}
		
		function deleteRoom(){
			var row = grid.datagrid('getSelected');
			if (row==null)
				showDelete();
			else{
				confirmDelete('DeleteRoom?id='+row.id);
			}			
		}
	
		function cancel1(){
			win.window('close');
		}
	</script>
</head>  
<body>
	<table id="roomgrid"></table>
	<div id="roomwin" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
			<form method="post">
				<input type="hidden" id="id" name="id"/>
				<table>					
					<tr>
						<td align="right"><div><sup>*</sup>操作间名称：</div></td>
						<td><input type="text" class="easyui-validatebox" required="true" id="roomname" name="roomname" style="width:200px;" maxLength=100/>
						</td>
					</tr>
					<tr>
						<td align="right"><div>备注：</div></td>
						<td><textarea id="memo" name="memo" style="width:200px;height:100px;"></textarea>
						</td>
					</tr>
				</table>
			</form>	
				<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel1();">取消</a>
				</p>
		</div>
	</div>	
	
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>