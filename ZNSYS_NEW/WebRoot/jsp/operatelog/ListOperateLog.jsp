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
    <title>用户操作日志</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var querywin,queryform;
		$(function(){
			$('#department').combotree({
				url:'JsonDepartment',
				onSelect:function(node)
				{
					$('#user').combobox({
						url:'JsonUsername?departmentid='+node.id
					});
				}
			});
			$('#user').combobox({
				valueField:'id',
				textField:'username',
				editable:false
			});
			
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
			
			grid=$('#grid').datagrid
			({
				title:'用户操作日志',
				url:'JsonOperateLog?begintime=<%=begintime%>',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[				    						
				    {title:'用户名',field:'username',width:100,align:'center'},
				    {title:'姓名',field:'name',width:100,align:'center'},
				    {title:'部门',field:'departmentname',width:120,align:'center'},
				    {title:'IP地址',field:'ipaddress',width:120,align:'center'},
				    {title:'操作时间',field:'operatetime',width:150,align:'center'},
				    {title:'操作内容',field:'operateobject',width:180,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				pageSize:30,
				toolbar:[{
					text:'查询',
					iconCls:'icon-search',
					handler:find					
					},{
					text:'刷新',
					iconCls:'icon-reload',
					handler:function(){
						grid.datagrid('reload');
					}
				},
				{
					text:'导出',
					iconCls:'icon-excel',
				    handler:function(){
				    	exportExcel(grid);//此处的grid就是要导出数据的表格
					}
				}],
				onLoadError:function(){
					showLoadError();
				}
			 });
		});

		function find()
		{
			queryform.form('clear');
			querywin.window('open');
		}

		function query()
		{
			var departmentid=$('#department').combotree('getValue');
			var username=$('#user').combobox('getValue');
			var begintime=$('#begindate').datebox('getValue');
			var endtime=$('#enddate').datebox('getValue');
			grid.datagrid({url:'JsonOperateLog?departmentid='+departmentid+'&username='+username+
				               '&begintime='+begintime+'&endtime='+endtime});
			grid.datagrid('reload');
			querywin.window('close');
		}

		function cancel()
		{
			querywin.window('close');
		}
	</script>
</head>  
<body>
	<table id="grid"></table>
	<div id="queryform" style="width:400px;height:220px;">
		<div  id="popup_content" style="padding:10px 10px 10px 20px;">
		<form>
		<table>
				<tr>
					<td align="right"  width="100"><div align="right"><sup>*</sup>部门：</div></td>
					<td><select id="department" style="width:200px;"></select></td>
				</tr>					
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>姓名：</div></td>
					<td><select id="user" style="width:200px;"></select></td>
				</tr>
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>时间范围：</div></td>
					<td><input type="text" readonly="readonly" class="easyui-datebox" name="begintime" id="begindate" style="width:85px;"></input>
					至 <input type="text" readonly="readonly" class="easyui-datebox" name="endtime" id="enddate" style="width:85px;"></input>
					</td>
				</tr>						
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="query();">查询</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">取消</a>
			</p>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>