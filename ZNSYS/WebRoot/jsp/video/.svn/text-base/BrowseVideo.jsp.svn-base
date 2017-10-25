<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>常州市防汛信息集成平台</title>
<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var win,querywin;
		var form,queryform;
		$(function(){
			$('#stationid1').combobox({
				url:'JsonStationCode',
				valueField:'stcd',
			    textField:'stnm'
			  });
			  
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
					{title:'所属测站',field:'station.stnm',width:160,align:'center'},
					{title:'备注',field:'memo',width:400,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[
				{
					text:'查询',
					iconCls:'icon-search',
				    handler:queryVideo
				}]
			});
		})
		
		function query()
		{   	
			grid.datagrid({url:'QueryVideo?stationid='+$('#stationid1').combobox('getValue')});
            grid.datagrid('reload');
			querywin.window('close');
		}
		
		function queryVideo(){
			querywin.window('open');
		}

		function cancel2(){
			querywin.window('close');
		}
	</script>
</head>  
<body>
	<table id="videogrid"></table>
	<div id="querywin" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
			<form>
				<table>					
					<tr>
						<td align="right"><div>所属测站:</div></td>
						<td><input type="text" id="stationid1" style="width:200px;"/>
						</td>
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