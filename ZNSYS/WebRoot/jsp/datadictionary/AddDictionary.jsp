<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>添加数据字典</title>
<%@ include file="../../inc/easyui.inc"%>
<script type="text/javascript">
var win,grid;
var dataname = "",sjbhy="";
var editIndex = undefined;
var combo =[{"typeid":"VARCHAR2","typename":"字符型"},{"typeid":"NUMBER","typename":"数字型"},{"typeid":"DATE","typename":"时间日期"}];

//加载json2
if(typeof JSON == 'undefined'){
	$('head').append($("<script type='text/javascript' src='js/json2.js'>"));
}

$(function(){		
	win=$('#win').window({
		title:"新建数据字典",
		closed:false,
		modal:true,
		closable:true,
		minimizable:false,
		maximizable:false,
		collapsible:false,
		resizable:false
	});
								
	grid=$('#grid').datagrid({
        fit:true,
        title:dataname,
        iconCls:'icon-edit',
		nowrap: true,		//不换行
	    striped: true,		//有条纹
	    fitColumns:false,	//自动宽度
		columns:[[
			{title:'列名称(缩写)',field:'pydm',width:100,align:'center',editor:'text'},
			{title:'序号',field:'xh',width:60,align:'center',editor:'numberbox'},
			{title:'数据类型',field:'lx',width:100,align:'center',editor:{
				type:'combobox',
				options:{
					valueField:'typeid',
					textField:'typename',
					data:combo,
					required:true
				}}},
			{title:'数据项名称',field:'sjxmc',width:100,align:'center',editor:'text'},
			{title:'数据长度',field:'kd',width:80,align:'center',editor:'numberbox'},
			{title:'精确位数(浮点数选填)',field:'xsws',width:150,align:'center',editor:'numberbox'},
			{title:'计量单位(选填)',field:'jldw',width:100,align:'center',editor:'text'},
			{title:'是主键',field:'pk',width:60,align:'center',editor:{type:'checkbox',options:{on:'true',off:'false'}}},
			{title:'可否为空',field:'kfwk',width:60,align:'center',editor:{type:'checkbox',options:{on:'true',off:'false'}}},
			{title:'是附键',field:'ak',width:60,align:'center',editor:{type:'checkbox',options:{on:'true',off:'false'}}},
			{title:'关联表',field:'czb',width:120,align:'center',editor:'text'},
			{title:'是否外键',field:'fkz',width:60,align:'center',editor:{type:'checkbox',options:{on:'true',off:'false'}}},
			{title:'外键表',field:'fkb',width:120,align:'center',editor:'text'},
			{title:'外键字段',field:'fkzd',width:120,align:'center',editor:'text'},
			{title:'值约束',field:'zys',width:120,align:'center',editor:'text'},
			{title:'提示信息',field:'txgd',width:120,align:'center',editor:'text'},
		]],
		singleSelect:true,		//单选
		rownumbers:true,		//列序号
		toolbar:[{
			text:'新增属性',
			iconCls:'icon-add',
			handler:append
		},'-',{
			text:'删除属性',
			iconCls:'icon-remove',
			handler:removeit
		},'-',{
			text:'保存数据字典',
			iconCls:'icon-save',
			handler:accept
		},'-',{
			text:'全部清空',
			iconCls:'icon-undo',
			handler:reject
		}],
		onLoadError:function(){
			showLoadError();
		},
		onClickRow:onClickRow
	});
});

function Confirm(){
	dataname = $('#dictionaryname').val();
	sjbhy = $('#dictionaryhy').val();
	
	grid.datagrid({title:dataname+'('+sjbhy+')'});
	win.window("close");
}

/*表格操作*/
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#grid').datagrid('validateRow', editIndex)){		//验证指定的行，当验证有效的时候返回true
		$('#grid').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
		
function onClickRow(index){
	if (endEditing()){
		$('#grid').datagrid('selectRow', index).datagrid('beginEdit', index);
		editIndex = index;
	} else {
		$('#grid').datagrid('selectRow', editIndex);
	}
}

//此版本不支持该属性
/*function onClickCell(index, field){
	alert(index+"::::::"+field);
	if (endEditing()){
		$('#grid').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
		editIndex = index;
	}else {
		$('#grid').datagrid('selectRow', editIndex);
	}
}*/

function append(){
	if (endEditing()){
		$('#grid').datagrid('appendRow',{status:'P'});		//追加一行
		editIndex = $('#grid').datagrid('getRows').length-1;		//赋值editIndex
		$('#grid').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);		//修改最后一列
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#grid').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		//$('#grid').datagrid('acceptChanges');		//需考虑一下
		var DataObject = JSON.stringify($('#grid').datagrid('getData'));
		alert("table="+DataObject+"&sjbmc="+dataname+"&sjbhy="+sjbhy);
		$.post("CreateTable",{
				//"table="+encodeURI(DataObject)+"&sjbmc="+encodeURI(dataname)+"&sjbhy="+encodeURI(sjbhy),
				table:encodeURI(DataObject),
				sjbmc:encodeURI(dataname),
				sjbhy:encodeURI(sjbhy)
			},
			function(result){
				eval(result);
		});
	}else{
		$.messager.alert("提示","完成需要填写的字段或行，方可提交。");
	}
}
function reject(){
	$('#grid').datagrid('rejectChanges');
	editIndex = undefined;
}
</script>
</head>
<body>
	<%--<table id="dg" class="easyui-datagrid" title="添加数据字典" style="width:100%;height:100%" data-options="iconCls: 'icon-edit',singleSelect: true,toolbar: '#tb',url: 'datagrid_data1.json',method: 'get',onClickRow: onClickRow">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">Item ID</th>
				<th data-options="field:'productid',width:100,
						formatter:function(value,row){
							return row.productname;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'productid',
								textField:'productname',
								url:'products.json',
								required:true
							}
						}">Product</th>
				<th data-options="field:'listprice',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">List Price</th>
				<th data-options="field:'unitcost',width:80,align:'right',editor:'numberbox'">Unit Cost</th>
				<th data-options="field:'attr1',width:250,editor:'text'">Attribute</th>
				<th data-options="field:'status',width:60,align:'center',editor:{type:'checkbox',options:{on:'P',off:''}}">Status</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增属性</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">移除属性</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存数据字典</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">清空所有</a>
	</div>--%>
	<table id="grid"></table>

	<div id="win" style="width:350px;height:200px;" >
		<div id="popup_content" style="padding:5px;margin:10px;text-align:center">
			<div>
				<span style="font-family:'黑体';font-size:1.2em;font-weight:700;text-shadow:1px 1px 1px #ccc;margin-bottom:7px">
					请输入新建数据字典名称：</span><br/>
				&nbsp;英文表名：<input type="text" id="dictionaryname" style="border-radius:5px;box-shadow:1px 1px 5px #ccc;width:200px;margin-top:8px"/>
				<br/>中文含义：<input type="text" id="dictionaryhy" style="border-radius:5px;box-shadow:1px 1px 5px #ccc;width:200px;margin-top:8px"/>
			</div>
			<div align="center" style="margin-top:8px">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="Confirm();">确定</a>
			</div>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>