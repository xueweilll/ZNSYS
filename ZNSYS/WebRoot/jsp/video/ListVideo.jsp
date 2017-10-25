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
			querywin=$('#querywin').window({
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
			win=$('#videowin').window({
				title:"视频参数设置",
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			form=win.find('form');
			
			grid=$('#videogrid').datagrid({
				title:'视频参数',
				fit:true,
				url:'JsonVideo',
				nowrap: false,
				striped: true,
				columns:[[
			        {title:'摄像头编号',field:'code',width:80,align:'center'},
			        {title:'摄像头名称',field:'caption',width:160,align:'center'},
					{title:'视频URL地址',field:'url',width:300,align:'center'}, 
					{title:'备注',field:'memo',width:400,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addVideo
					
				},{
					text:'编辑',
					iconCls:'icon-edit',
					handler:editVideo
					
				},{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteVideo
				}]
			});
		})
		
		function queryVideo(){
			queryform.form('clear');
			querywin.window('open');
		}
		
		function addVideo(){
			form.form('clear');
			win.window('open');
			form.url='InsertVideo';
			$('#code').focus();
		}
		
		function save(){
			/*
			if ($('#stationid').combobox('getValue').length==0)
			{
				showWarning('请选择测站！');
				return;
			}
			if ($.trim($('#code').val()).length==0)
			{
				showWarning('请输入摄像头编号！');
				$('#code').focus();
				return;
			}
			if ($.trim($('#caption').val()).length==0)
			{
				showWarning('请输入摄像头名称！');
				$('#caption').focus();
				return;
			}
			if ($.trim($('#url').val()).length==0)
			{
				showWarning('请输入视频URL地址！');
				$('#url').focus();
				return;
			}		
			*/
			if (!$('#code').validatebox('isValid')) return;
			if (!$('#caption').validatebox('isValid')) return;
			if (!$('#url').validatebox('isValid')) return;
			form.form('submit',{
				url:form.url,
				success:function(data){
					eval(data);
					grid.datagrid('reload');
					win.window('close');
				}
			});
		}
		
		function editVideo(){
			var row = grid.datagrid('getSelected');
			if (row==null)
				showUpdate();
			else{
				form.form('clear');
				$('#id').val(row.id);
			    $('#code').val(row.code);
				$('#caption').val(row.caption);
				$('#url').val(row.url);
				$('#memo').val(row.memo);
				win.window('open');
				form.url='UpdateVideo';
				$('#code').focus();
			}
		}
		
		function deleteVideo(){
			var row = grid.datagrid('getSelected');
			if (row==null)
				showDelete();
			else{
				confirmDelete('DeleteVideo?id='+row.id);
			}			
		}
	
		function cancel1(){
			win.window('close');
		}
	</script>
</head>  
<body>
	<table id="videogrid"></table>
	<div id="videowin" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
			<form method="post">
				<input type="hidden" id="id" name="id"/>
				<table>					
					<tr>
						<td align="right"><div><sup>*</sup>摄像头编号：</div></td>
						<td><input type="text" class="easyui-validatebox" required="true" id="code" name="code" style="width:200px;" maxLength=100/>
						</td>
					</tr>
					<tr>
						<td align="right"><div><sup>*</sup>摄像头名称：</div></td>
						<td><input type="text" class="easyui-validatebox" required="true" id="caption" name="caption" style="width:200px;" maxLength=100/>
						</td>
					</tr>
					<tr>
						<td align="right"><div><sup>*</sup>视频URL地址：</div></td>
						<td><textarea class="easyui-validatebox" required="true" id="url" name="url" style="width:200px;height:200px;"></textarea>
						</td>
					</tr>
					<tr>
						<td align="right"><div>备注：</div></td>
						<td colspan="3"><textarea id="memo" name="memo" style="width:200px;height:100px;"></textarea>
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