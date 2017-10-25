<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<%
String begintime=new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime())+"-01"; 
%>
<html>
  <head>
    <title>用户登录统计</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid,detailgrid;
		var querywin,queryform,detailwin;
		var begintime='<%=begintime%>',endtime='';
		$(function(){
			$('#department').combotree({
				url:'JsonDepartment'
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
			
			grid=$('#grid').datagrid({
				title:'用户登录统计',
				url:'JsonLoginStatistics?begintime='+begintime,
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[				    						
				    {title:'用户名',field:'username',width:100,align:'center'},
				    {title:'姓名',field:'name',width:120,align:'center'},
				    {title:'部门',field:'departmentname',width:160,align:'center'},
				    {title:'登录次数',field:'logincount',width:160,align:'center'},
				    {title:'在线时长',field:'timelength',width:160,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
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
				},{
					text:'详细信息',
					iconCls:'icon-doc',
					handler:detail
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

			detailwin=$('#detailform').window({
				title:"详细登录信息",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			detailgrid=$('#detailgrid').datagrid({
				fit:true,
				nowrap: false,
				striped: true,
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				columns:[[				    						
				    {title:'用户名',field:'username',width:80,align:'center'},
				    {title:'姓名',field:'name',width:80,align:'center'},
				    {title:'部门',field:'departmentname',width:120,align:'center'},
				    {title:'IP地址',field:'ipaddress',width:100,align:'center'},
				    {title:'登录时间',field:'logintime',width:150,align:'center'},
				    {title:'注销时间',field:'logouttime',width:150,align:'center'}
				]],
				onLoadError:function(){
					showLoadError();
				}
			});
		});

		function detail()
		{
			var row=grid.datagrid('getSelected');
			if (row){
				detailgrid.datagrid('loadData',{"total":0,"rows":[]});
				detailgrid.datagrid({'pageNumber':1});
				detailwin.window('open');
				var username=row.username;
				detailgrid.datagrid({url:'JsonLoginHistory?username='+username+
                    '&begintime='+begintime+'&endtime='+endtime});
				detailgrid.datagrid('reload');		
			}else{
				showWarning('请先选择要查看的用户！');
			}
		}

		function find()
		{
			begintime='';
			endtime='';
			queryform.form('clear');
			querywin.window('open');
		}

		function query()
		{
			var departmentid=$('#department').combotree('getValue');
			begintime=$('#begindate').datebox('getValue');
			endtime=$('#enddate').datebox('getValue');
			grid.datagrid({url:'JsonLoginStatistics?departmentid='+departmentid+
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
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
		<form>
		<table>
				<tr>
					<td align="right"  width="100"><div align="right"><sup>*</sup>部门：</div></td>
					<td><select id="department" style="width:200px;"></select></td>
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
	
	<div id="detailform" style="width:800px;height:380px;">
		<table id="detailgrid"></table>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>