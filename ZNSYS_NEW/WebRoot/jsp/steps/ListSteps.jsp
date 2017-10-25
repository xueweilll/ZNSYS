<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>

<html>
  <head>
    <title>步骤表</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var querywin,queryform;
		var win,winform;
		$(function(){
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
			queryform=querywin.find('form');//查询窗口

			win=$('#win').window({
				title:"新增步骤",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			winform=win.find('form');//新增窗口
			
			
			grid=$('#grid').datagrid
			({
				title:'步骤表',
				url:'',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[				    						
				    {title:'步骤ID',field:'stepid',width:80,align:'center'},
				    {title:'步骤号',field:'stepsnumber',width:100,align:'center'},
				    {title:'步骤名称',field:'stepsname',width:100,align:'center'},
				    {title:'流程ID',field:'processid',width:80,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[
						{text:'新增',iconCls:'icon-add',handler:add},'-',
						{text:'删除',iconCls:'icon-remove',handler:remove},'-',
						{text:'编辑',iconCls:'icon-edit',handler:edit},'-',
						{text:'查询',iconCls:'icon-search',handler:find},'-',
						{text:'刷新',iconCls:'icon-reload',handler:reload}
						],
				//显示加载错误
				onLoadError:function(){
					showLoadError();
				}
			 });
			 //调整自动适应表格
			adjuestTable(grid);
			$(window).resize(function(){
				adjuestTable(grid);
			});
		});
		
		function add(){
			winform.form('clear');
			win.window('open');
			}
		
		function remove(){
			var row = grid.datagrid('getSelected');
			if(row==null){
				$.messager.alert('提示','请您先选择要删除的信息','warning');
				}else{
				$.messager.confirm('警告','您确认要删除该信息吗？','warning');
					}
			}

		function edit(){
			var row = grid.datagrid('getSelected');
			if(row==null){
				$.messager.alert('警告','请您先选择要编辑的信息！','warning');
				}else{
					form.form('clear');
						$('#stepid').val(row.stepid);
						$('#stepsnumber').val(row.stepsnumber);
						$('#stepsname').val(row.stepsname);
						$('#processid').val(row.processid);
					win.window('open');
				}
			}

		function find()
		{
			queryform.form('clear');
			querywin.window('open');
		}

		function reload(){
			grid.datagrid('reload');
		}
		
		function query()
		{
			var stepid=$('#stepid').validatabox('getValue');
			var stepsnumber=$('#stepsnumber').validatabox('getValue');
			var stepsname=$('#stepsname').validatabox('getValue');
			var processid=$('#processid').validatabox('getValue');
			grid.datagrid({url:'JsonOperateLog?stepid='+stepid+'&stepsnumber='+stepsnumber+
	            '&stepsname='+stempsname+'processid='+processid});
			grid.datagrid('reload');
			querywin.window('close');
		}
		
		function save(){
			win.window('close');
			}
		
		function cancel1()
		{
			win.window('close');
		}
		
		function cancel2()
		{
			querywin.window('close');
		}
		
		
	</script>
</head>  
<body>
	<table id="grid"></table>
	<div id="win" style="width:auto;height:auto;" align="center">
		<div style="padding:10px 10px 10px 20px;">
			<form method="post">
		<table>
				<tr>
					<td align="right"><div align="right"><sup>*</sup>步骤ID：</div></td>
					<td><input id="stepid" style="width:200px;" class="easyui-validatabox" required="true"></td>
				</tr>					
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>步骤号：</div></td>
					<td><input id="stepsnumber" style="width:200px;" class="easyui-validatabox" required="true"></td>
				</tr>
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>步骤名称：</div></td>
					<td><input id="stepsname" style="width:200px;" class="easyui-validatabox" required="true">
					</td>
				</tr>
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>流程号：</div></td>
					<td><input id="processid" style="width:200px;" class="easyui-validatabox" required="true">
					</td>
				</tr>					
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel1();">取消</a>
			</p>
			</form>
		</div>
	</div>
	<div id="queryform" style="width:auto;height:auto;" align="center">
		<div  id="popup_content" style="padding:10px 10px 10px 20px;">
		<form method="post">
		<table>
				<tr>
					<td align="right"><div align="right"><sup>*</sup>步骤ID：</div></td>
					<td><select id="stepid" style="width:200px;"></select></td>
				</tr>					
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>步骤号：</div></td>
					<td><select id="stepsnumber" style="width:200px;"></select></td>
				</tr>
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>步骤名称：</div></td>
					<td><select id="stepsname" style="width:200px;"></select>
					</td>
				</tr>
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>流程号：</div></td>
					<td><select id="processid" style="width:200px;"></select>
					</td>
				</tr>						
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="query();">查询</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel2();">取消</a>
			</p>
			</form>
		</div>
	</div>
	
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>