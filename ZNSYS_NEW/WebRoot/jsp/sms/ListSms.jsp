<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
<head>
<title>短信内容表</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<%@ include file="../../inc/easyui.inc"%>
<script>
var addlistgrid,addlist,smsgroup,smsgrid;
var smswin,addlistwin,smsgroupwin,querywin;
var smsform,queryform;		
var opflag=0;
$(function(){	
	smsgrid=$('#smsgrid').datagrid({
		title:'发送短信',
		url:'JsonSms',
		nowrap: false,
	    striped: true,
	    fit:true,
		columns:[[
			{field:'ck',width:40,align:'center',checkbox:'true'},
			{title:'短信内容',field:'content',width:360,align:'center'},
			{title:'创建时间',field:'createtime',width:140,align:'center'},
			{title:'发送时间',field:'sendreqtime',width:140,align:'center'}	
		]],
		rownumbers:true,
		pagination:true,
		loadMsg:'数据加载中.....',
		toolbar:[{
				text:'新建',
				iconCls:'icon-add',
				handler:addSms
			},{
				text:'编辑',
				iconCls:'icon-edit',
				handler:editSms
			},{
				text:'删除',
				iconCls:'icon-remove',
				handler:deleteSms
			},{
				text:'查询',
				iconCls:'icon-search',
			    handler:querySms
			},{
				text:'发送',
				iconCls:'icon-mail',
			    handler:sendSms
			}],
		onLoadError:function(){
			showLoadError();
		}
	});
	var p = $("#smsgrid").datagrid('getPager');
	$(p).pagination({
		pageList : [ 5, 10, 15, 20 ],
		beforePageText : '第',
		afterPageText : '页        共{pages}页',
		displayMsg : '当前显示{from}-{to}条记录      共{total}条记录'
	});
		
	addlistgrid=$('#addlistgrid').datagrid({
		nowrap: false,
	    striped: true,
	    fit:true,
		columns:[[
			{title:'姓名',field:'addlist.membername',width:80,align:'center'},
			{title:'电话',field:'tel',width:80,align:'center'}
		]],
		rownumbers:true,
		fitColumns:true,
		toolbar:[{
			text:'从通讯录添加',
			iconCls:'icon-add',
			handler:addFromAddList
		},{
			text:'从分组添加',
			iconCls:'icon-add',
			handler:addFromSmsGroup
		},{
			text:'删除',
			iconCls:'icon-remove',
			handler:deleteAddList
		}],
		onLoadError:function(){
			showLoadError();
		}
	});

	addlist=$('#addlist').datagrid({
		nowrap: false,
	    striped: true,
	    fitColumns:true,
	    url:'JsonAddList',
		columns:[[
			{field:'ck',checkbox:true},
	        {title:'姓名',field:'membername',width:60,align:'center'},
	        {title:'电话',field:'tel',width:100,align:'center'}
		]],
		rownumbers:true,
		onLoadError:function(){
			showLoadError();
		}
	});

	smsgroup=$('#smsgroup').datagrid({
		nowrap: false,
	    striped: true,
	    fitColumns:true,
	    url:'JsonSmsGroupGrid',
		columns:[[
			{field:'ck',checkbox:true},
	        {title:'组名',field:'caption',width:100,align:'center'}
		]],
		rownumbers:true,
		onLoadError:function(){
			showLoadError();
		}
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
	
	smswin=$('#smswin').window({
		title:"编辑短信",
		closed:true,
		modal:true,
		closable:true,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		resizable:false
	});
	smsform=smswin.find('form');
	
	addlistwin=$('#addlistwin').window({
		title:"从通讯录添加",
		closed:true,
		modal:true,
		closable:true,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		resizable:false
	});
	
	smsgroupwin=$('#smsgroupwin').window({
		title:"从分组添加",
		closed:true,
		modal:true,
		closable:true,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		resizable:false
	});
});

function query()
{
	var content=$('#content1').val();
	var begintime=$('#begintime').datebox('getValue');
	var endtime=$('#endtime').datebox('getValue');
	smsgrid.datagrid({url:'QuerySms?content='+content+'&begintime='+begintime+'&endtime='+endtime});
    smsgrid.datagrid('reload');
	querywin.window('close');
}

function querySms()
{
	queryform.form('clear');
	querywin.window('open');
}

function addFromAddList()
{
	addlist.datagrid('unselectAll');
	addlistwin.window('open');
}

function addFromSmsGroup()
{
	smsgroup.datagrid('unselectAll');
	smsgroupwin.window('open');
}

function deleteAddList()
{
	var row=addlistgrid.datagrid('getSelected');
	if (row) addlistgrid.datagrid('deleteRow',addlistgrid.datagrid('getRowIndex',row));
	else showDelete();
}
	
function addSms(){
	smsform.form('clear');
	addlistgrid.datagrid('loadData',[]);
	opflag=1;
	smswin.window('open');
}

function editSms(){
	var row = smsgrid.datagrid('getSelected');
	if (row){
		if((row.sendreqtime!=null)&&(row.sendreqtime!=''))
			showWarning('该消息已发送，不能再编辑！');
		else
		{
			smsform.form('clear');	
			$('#content').val(row.content);
			addlistgrid.datagrid({url:'JsonSmsLog?smsid='+row.id});
			addlistgrid.datagrid('reload');
			opflag=2;
			smswin.window('open');
		}
	}else{
		showUpdate();
	}
}

function deleteSms()
{
	var rows = smsgrid.datagrid('getSelections');
	if (rows){
		var ids=[];
		for (var i=0;i<rows.length;i++) ids.push(rows[i].id);
		showConfirm('确定要删除这些消息吗？',function(){
			$.ajax({
				url: 'DeleteSms?ids='+ids,
				type: 'GET',
				timeout: 1000,
				success:function(data){
					showInfo(data);
					smsgrid.datagrid('reload');
				}
			});
		});
	}else{
		showDelete();
	}			
}

function sendSms(){		
	var rows=smsgrid.datagrid('getSelections');
	var ids=[];
	if (rows){
		var ids=[];
		for (var i=0;i<rows.length;i++) 
			if((rows[i].sendreqtime==null)||(rows[i].sendreqtime=='')) ids.push(rows[i].id);
		if (ids.length==0)
			showWarning('请选择未发送过的消息！');
		else
			showConfirm('确定要发送这些消息吗？',function(){
				$.ajax({
					url: 'SendSms?ids='+ids,
					type: 'GET',
					timeout: 1000,
					success:function(data){
						showInfo(data);
						smsgrid.datagrid('reload');
					}
				});
			});
	}else{
		showWarning('请选择要发送的消息！');
	}
}		

function cancel1(){
	smswin.window('close');
}	
	
function cancel2(){
	addlistwin.window('close');
}	

function cancel3(){
	smsgroupwin.window('close');
}	

function cancel4(){
	querywin.window('close');
}	

function save1()
{
	if (!$('#content').validatebox('isValid')) return;
	var rows=addlistgrid.datagrid('getRows');
	var ids=[];
	for(var i=0;i<rows.length;i++) ids.push(rows[i].addlist.id);
	$('#addlistids').val(ids.join(','));
	var url='';
	if (opflag==1) url='InsertSms';
	else url='UpdateSms?id='+smsgrid.datagrid('getSelected').id;
	smsform.form('submit',{
		url:url,
		success:function(data){
			showInfo(data);
			smsgrid.datagrid('reload');
			smswin.window('close');
		}
	});
}

function save2()
{
	if (!$('#content').validatebox('isValid')) return;
	var rows=addlistgrid.datagrid('getRows');
	if(rows.length>0)
	{
		var ids=[];
		for(var i=0;i<rows.length;i++) ids.push(rows[i].addlist.id);
		$('#addlistids').val(ids.join(','));
		var url='';
		if (opflag==1) url='InsertAndSendSms';
		else url='UpdateAndSendSms?id='+smsgrid.datagrid('getSelected').id;
		$.messager.progress();
		//$.messager.alert('提示','短信发送过程需要硬件驱动的初始化和调用，大约需要10~100秒时间。这取决于服务器的性能，在此期间，还请您稍作等待…');  
		smsform.form('submit',{
			url:url,
			success:function(data){
				showInfo(data);
				smsgrid.datagrid('reload');
				$.messager.progress('close');
				smswin.window('close');
			}
		});
	}
	else
		showWarning('请选择要收信人！');
}

function save3()
{
	var selrows=addlist.datagrid('getSelections');
	if(selrows.length==0) showWarning('请选择收信人！');
	else
	{
		var rows=addlistgrid.datagrid('getRows');
		for(var i=0;i<selrows.length;i++)
		{
			var b=true;
			for(var j=0;j<rows.length;j++)
				if (selrows[i].tel==rows[j].tel) b=b && false;
			if (b) addlistgrid.datagrid('appendRow',{tel:selrows[i].tel,addlist:selrows[i]});
		}
		addlistwin.window('close');
	}
}

function save4()
{
	var rows=smsgroup.datagrid('getSelections');
	if(rows.length==0) showWarning('请选择收信人组！');
	else
	{
		var ids=[];
		for(var i=0;i<rows.length;i++) ids.push(rows[i].id);
		var selrows=getRemoteData('JsonAddListBySmsGroup?groupids='+ids);
		var rows=addlistgrid.datagrid('getRows');
		for(var i=0;i<selrows.length;i++)
		{
			var b=true;
			for(var j=0;j<rows.length;j++)
				if (selrows[i].tel==rows[j].tel) b=b && false;
			if (b) addlistgrid.datagrid('appendRow',{tel:selrows[i].tel,addlist:selrows[i]});
		}
		smsgroupwin.window('close');
	}
}
</script>
</head>  
<body>
	<table id="smsgrid"></table>
	
	<div id="smswin" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:0px 0px 0px 0px;">
		<form method="post">
			<input type="hidden" id="addlistids" name="addlistids"/>
			<div class="easyui-panel" style="width:400px;height:130px" title="短信内容" border=false>
				<textarea class="easyui-validatebox" required="true" validType="length[1,400]" name="content" id="content" style="width:100%;height:100px;"></textarea>
			</div>
			<div class="easyui-panel" style="width:400px;height:200px" title="收信人" border=false>
				<table id="addlistgrid"></table>
			</div>
			<br>
			<div align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save1();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save2();">保存并发送</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel1();">取消</a>
			</div>
		</form>
		</div>
	</div>
	
	<div id="addlistwin" style="width:auto;height:auto;" >
		<div id="popup_content" style="padding:0px 0px 0px 0px;">
			<table id="addlist" style="width:240px;height:360px;"></table>
			<div align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-ok" onClick="save3();">确定</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel2();">取消</a>
			</div>
		</div>
	</div>
	
	<div id="smsgroupwin" style="width:auto;height:auto;" >
		<div id="popup_content" style="padding:0px 0px 0px 0px;">
			<table id="smsgroup" style="width:180px;height:240px;"></table>
			<div align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-ok" onClick="save4();">确定</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel3();">取消</a>
			</div>
		</div>
	</div>
	
	<div id="querywin" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
			<form>
			<table>			
				<tr>
				    <td><div align="right">短信内容：</div></td>
				    <td><input type="text" id="content1" style="width:180px;" /></td>
			    </tr>
			    <tr>
				    <td><div align="right">创建时间：</div></td>
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
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel4();">取消</a>
			</p>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>