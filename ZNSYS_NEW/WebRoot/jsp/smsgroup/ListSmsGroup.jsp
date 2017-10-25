<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
<head>
<title>分组表</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<%@ include file="../../inc/easyui.inc"%>
<script type="text/javascript">
    window.onload = function ()
    {
        var table = document.getElementById ('sms');
        table.oncontextmenu = function ()
        {
            return false;
        }
    }
</script>
<script type="text/javascript">
var addlistgrid,addlist;
var addlistwin,smsgroupwin;
var smsgroupform;
var smstree;	
var smsgid=null;
$(function(){	
	 smstree=$('#sms').tree({
		checkbox: false,
		animate:true,
		url: 'JsonSmsGroup',
		onSelect:function(node){
			smsgid=node.id;
			$('#addlistgrid').datagrid({'url':'JsonSmsGroupAddList?groupid='+smsgid});
			$('#addlistgrid').datagrid('reload');
		},
		onContextMenu:function(e,node){
			$('#popmenu1').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
		},
		onLoadError:function(){
			showError('加载数据发生错误！');
		}
	});
		
	smsgroupwin=$('#smsgroupwin').window({
		title:"分组",
		closed:true,
		modal:true,
		closable:true,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		resizable:false
	});
	smsgroupform=smsgroupwin.find('form');

	addlist=$('#addlist').datagrid({
		nowrap: false,
	    striped: true,
	    url:'JsonAddList',
		columns:[[
			{field:'ck',checkbox:true},
	        {title:'姓名',field:'membername',width:100,align:'center'}
		]],
		rownumbers:true,
		onLoadError:function(){
			showLoadError();
		}
	});
	addlistwin=$('#addlistwin').window({
		title:"通讯录",
		closed:true,
		modal:true,
		closable:true,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		resizable:false
	});
								
	addlistgrid=$('#addlistgrid').datagrid({
        fit:true,
		nowrap: false,
	    striped: true,
		columns:[[
	        {title:'姓名',field:'addlist.membername',width:100,align:'center'},
			{title:'电话',field:'addlist.tel',width:100,align:'center'},
			{title:'单位部门',field:'addlist.memberdept',width:150,align:'center'},
			{title:'备注',field:'addlist.memo',width:250,align:'center'}
		]],
		singleSelect:true,
		rownumbers:true,
		pagination:true,
		toolbar:[{
			text:'新增',
			iconCls:'icon-add',
			handler:addSmsGroupAddlist
		},{
			text:'删除',
			iconCls:'icon-remove',
			handler:deleteSmsGroupAddlist
		}],
		onLoadError:function(){
			showLoadError();
		}
	});
	
	$(document).bind('contextmenu',function(e){
		return false;
	});
})

function addSmsGroupAddlist(){
	if ((smsgid==null)||(smsgid==''))
	{
		showWarning('请先选择一个分组！');
		return;
	}
	addlist.datagrid('unselectAll');
	addlistwin.window('open');
}

function deleteSmsGroupAddlist(){
	var row = addlistgrid.datagrid('getSelected');
	if (row)
		confirmDelete('DeleteSmsGroupAddList?id='+row.id,addlistgrid);
	else
		showDelete();		
}

function addGroup()
{
	smsgroupform.form('clear');
	smsgroupwin.window('open');
	smsgroupform.url='InsertSmsGroup';
}

function editGroup()
{
	if ((smsgid==null)||(smsgid==''))
	{
		$('#popmenu1').menu('hide');
		showUpdate();
	}
	else
	{
		mynode=$('#sms').tree('getSelected');
		smsgroupform.form('clear');
		$('#caption').val(mynode.text);
		smsgroupwin.window('open');
		smsgroupform.url='UpdateSmsGroup?id='+smsgid;
	}
}

function deleteGroup()
{
	if ((smsgid==null)||(smsgid==''))
	{
		$('#popmenu1').menu('hide');
		showDelete();
	}
	else
		showConfirm('您确认要删除该分组吗?',function(){
			$('#popmenu1').menu('hide');
			$.ajax({
				url: 'DeleteSmsGroup?id='+smsgid,
				type: 'GET',
				timeout: 1000,
				success:function(data){
					eval(data);
					mynode=$('#sms').tree('getSelected');
					$('#sms').tree('remove',mynode.target);
					addlistgrid.datagrid('loadData',[]);
				}
			});
		});
}

function reloadGroup()
{
	$('#sms').tree('reload');
}

function save(){	
	if (!$('#caption').validatebox('isValid')) return;
	smsgroupform.form('submit',{
		url:smsgroupform.url,
		success:function(data){
			eval(data);
			smsgroupwin.window('close');
			reloadGroup();
			if (smsgroupform.url.indexOf('Insert')>=0) 
				addlistgrid.datagrid('loadData',[]);
		}
	});
}	

	
function gsave(){	
	var groupid=$('#sms').tree('getSelected').id;
	var selrows=addlist.datagrid('getSelections');
	var rows=addlistgrid.datagrid('getRows');
	var ids=[];
	var b;
	for (var i=0;i<selrows.length;i++)
	{
		b=true;
		for(var j=0;j<rows.length;j++)
		{
			if(selrows[i].id==rows[j].addlist.id) b=b && false;
		}
		if (b) ids.push(selrows[i].id);
	}
	if (ids.length>0)
	{
		ids=ids.join(',');
		$.ajax({
			url:'InsertSmsGroupAddList?addlistids='+ids+'&groupid='+groupid,
			type:'GET',
			success:function(data){
				eval(data);
				addlistwin.window('close');
				addlistgrid.datagrid('reload');
			}
		});	
	}
	else
		showWarning('请选择要添加到该分组的人员！');
}		


function cancel1(){
	addlistwin.window('close');
}		

function cancel2(){
	smsgroupwin.window('close');
}		
</script>
</head>
<body class="easyui-layout">
	<div region="west" style="width:160px;" border=false>
		<div class="easyui-panel" fit=true title="通讯录分组">
			<ul id="sms"></ul>
		</div>
	</div>
	
	<div region="center" title="组员名单" border=false>
		<table id="addlistgrid"></table>
	</div>
	
	<div id="popmenu1" class="easyui-menu" style="width:120px;">
		<div onclick="javascript:addGroup();"> 添加</div>
		<div onclick="javascript:editGroup(); "> 修改 </div>
		<div onclick="javascript:deleteGroup();"> 删除 </div>
		<div class="menu-sep"></div>
		<div onclick="javascript:reloadGroup();"> 刷新 </div>
	</div>
	
	<div id="addlistwin" style="width:auto;height:auto;" >
		<div id="popup_content" style="padding:0px 0px 0px 0px;">
			<table id="addlist" style="width:180px;height:240px;"></table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="gsave();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel1();">取消</a>
			</p>
		</div>
	</div>
	
	<div id="smsgroupwin" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
			<form method="post">
			<table>		
				<tr>
					<td align="right"><div align="right"><sup>*</sup>分组名称：</div></td>
					<td><input type="text" name="caption" id="caption" style="width:160px;" class="easyui-validatebox" required="true" validType="length[1,100]" /></td>
				</tr>
			</table>
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel2();">取消</a>
			</p>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>