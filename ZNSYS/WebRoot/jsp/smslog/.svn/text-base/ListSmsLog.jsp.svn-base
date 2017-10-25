<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
<head>
<title>短信发送记录表</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<%@ include file="../../inc/easyui.inc"%>
<script>
var querywin;
var queryform;		

$(function(){	
	$('#smstype').combobox({
		data:[{key:'自定义短信',value:'自定义短信'},{key:'会议通知',value:'会议通知'},{key:'告警短信',value:'告警短信'}],
		valueField:'value',
	    textField:'key',
	    panelHeight:'auto'
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
	queryform=querywin.find('form');
								
	$('#smsloggrid').datagrid({
		title:'短信发送记录表',
		fit:true,
		url:'QuerySmsLog',
		nowrap: false,
	    striped: true,
		columns:[[
			{title:'短信类型',field:'sms.smstype',width:80,align:'center'},
			{title:'收信人',field:'addlist.membername',width:80,align:'center'},
			{title:'电话号码',field:'tel',width:80,align:'center'},
			{title:'短信内容',field:'sms.content',width:280,align:'center'},
			{title:'发送时间',field:'sms.sendreqtime',width:120,align:'center'},
			{title:'发送状态',field:'sendstate',width:80,align:'center'}
		]],
		singleSelect:true,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		toolbar:[{
			text:'查询',
			iconCls:'icon-search',
		    handler:querySmsLog
		}],
		onLoadError:function(){
			showLoadError();
		}
	});
})

function query()
{
	var content=$('#content').val();
	var tel=$('#tel').val();
	var smstype=$('#smstype').combobox('getValue');
	var begintime=$('#begintime').datebox('getValue');
	var endtime=$('#endtime').datebox('getValue');
	$('#smsloggrid').datagrid({url:'QuerySmsLog?smstype='+smstype+'&content='+content+'&tel='+tel+'&begintime='+begintime+'&endtime='+endtime});
	$('#smsloggrid').datagrid('reload');
	querywin.window('close');
}

function querySmsLog(){
	queryform.form('clear');
	querywin.window('open');
}

function cancel(){
	querywin.window('close');
}		
</script>
</head>  
<body>
	<table id="smsloggrid"></table>
	
	<div id="querywin" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:10px 10px 10px 10px;">
			<form>
			<table>					
				<tr>
				    <td><div align="right">短信类别：</div></td>
				    <td><input type="text" id="smstype" style="width:180px;" /></td>
			    </tr>
			    <tr>
				    <td><div align="right">短信内容：</div></td>
				    <td><input type="text" id="content" style="width:180px;" /></td>
			    </tr>
			    <tr>
				    <td><div align="right">手机号：</div></td>
				    <td><input type="text" id="tel" style="width:180px;" /></td>
			    </tr>
			    <tr>
				    <td><div align="right">发送时间：</div></td>
				    <td><input class="easyui-datebox" type="text" id="begintime" style="width:180px;" /></td>
			    </tr>
			    <tr>
				    <td><div align="right">至</div></td>
				    <td><input class="easyui-datebox" type="text" id="endtime" style="width:180px;" /></td>
			    </tr>
			</table>
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-search" onClick="query();">查询</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">取消</a>
			</p>
		</div>
	</div>
	
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>