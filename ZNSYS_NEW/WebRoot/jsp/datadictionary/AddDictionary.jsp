<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>添加数据字典</title>
<%@ include file="../../inc/easyui.inc"%>
</head>
<body>
<script type="text/javascript">
var gridInfo;
var dataname = "",sjbhy="";
var editIndex = undefined;
var combo =[{"typeid":"VARCHAR2","typename":"字符型"},{"typeid":"NUMBER","typename":"数字型"},{"typeid":"DATE","typename":"时间日期"}];

//加载json2
if(typeof JSON == 'undefined'){
	$('head').append($("<script type='text/javascript' src='js/json2.js'>"));
}

$(function(){
	gridInfo=$('#tgrid').datagrid({
        //fit:true,
        title:dataname,
        iconCls:'icon-edit',
		nowrap: false,		//不换行
	    striped: true,		//有条纹
	    fitColumns:true,	//自动宽度
	    autoRowHeight:true,
		columns:[[
			{title:'列名称(缩写)',field:'pydm',width:100,align:'center',editor:{type:'validatebox',options:{required:true}}},
			{title:'序号',field:'xh',width:60,align:'center',editor:{type:'numberbox',options:{required:true}}},
			{title:'数据类型',field:'lx',width:100,align:'center',editor:{
				type:'combobox',
				options:{
					valueField:'typeid',
					textField:'typename',
					data:combo,
					required:true,
					editable:false,
					onSelect:function(record){
						var ed = $('#tgrid').datagrid('getEditor', {index:editIndex,field:'kd'});
						if(record.typeid=="DATE"){
							$(ed.target).numberbox({required:false});
						}else{
							$(ed.target).numberbox({required:true});
						}
					}
				}}},
			{title:'数据项名称',field:'sjxmc',width:100,align:'center',editor:{type:'validatebox',options:{required:true}}},
			{title:'数据长度',field:'kd',width:80,align:'center',editor:{type:'numberbox',options:{required:false}}},
			{title:'精确位数(浮点数选填)',field:'xsws',width:150,align:'center',editor:'numberbox'},
			{title:'计量单位(选填)',field:'jldw',width:100,align:'center',editor:'text'},
			{title:'是主键',field:'pk',width:60,align:'center',editor:{type:'checkbox',options:{on:'是',off:'否'}}},
			{title:'可否为空',field:'kfwk',width:60,align:'center',editor:{type:'checkbox',options:{on:'是',off:'否'}}},
			{title:'是附键',field:'ak',width:60,align:'center',editor:{type:'checkbox',options:{on:'是',off:'否'}}},
			{title:'关联表',field:'czb',width:120,align:'center',editor:'text'},
			{title:'是否外键',field:'fkz',width:60,align:'center',editor:{type:'checkbox',options:{on:'是',off:'否'}}},
			{title:'外键表',field:'fkb',width:120,align:'center',editor:{
				type:'combobox',
				options:{
					valueField:'sjbmc',
					textField:'sjbmc',
					url:'findTablesNames',
					required:true,
					editable:false,
					onSelect:function(record){
						var fkzd = $('#tgrid').datagrid('getEditor', {index:editIndex,field:'fkzd'});
						$(fkzd.target).combobox("clear");
						$(fkzd.target).combobox("reload");
					}
				}}},
			{title:'外键字段',field:'fkzd',width:120,align:'center',editor:{
				type:'combobox',
				options:{
					valueField:'pydm',
					textField:'pydm',
					url:'findTablesColumns',
					required:true,
					editable:false,
					onBeforeLoad:function(param){
						var fkb = $('#tgrid').datagrid('getEditor', {index:editIndex,field:'fkb'});
						if(fkb!=null){
							var value=$(fkb.target).combobox("getValue");
							if(value==""){
								return false;
							}else{
								param.sjbmc=value;
							}
						}
						
					}
			}}},
			{title:'值约束',field:'zys',width:120,align:'center',editor:'text'},
			{title:'提示信息',field:'txgd',width:120,align:'center',editor:'text'}
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
			handler:findTableNameExist
		},'-',{
			text:'全部清空',
			iconCls:'icon-undo',
			handler:reject
		}],
		onLoadError:function(){
			showLoadError();
		},
		onClickRow:function(rowIndex, rowData){
			onClickRow(rowIndex);
		}
	});
	var strs = '${sjbmc}';
	if(strs !='ADDS'){
		$("#dictionaryname").val(strs);
		var json=${json};
		$('#tgrid').datagrid('loadData',json);
	}
	if($("#datasnamess").val()!="undefined"){
		$("#dictionaryhy").val($("#datasnamess").val());
	}
});
/*表格操作*/
function endEditing(){
	if (editIndex == undefined){
		return true
	}
	if ($('#tgrid').datagrid('validateRow', editIndex)){		//验证指定的行，当验证有效的时候返回true
		$('#tgrid').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (endEditing()){
		$('#tgrid').datagrid('selectRow', index).datagrid('beginEdit', index);
		editIndex = index;
		var ed = $('#tgrid').datagrid('getEditor', {index:index,field:'fkz'});
		var fkb = $('#tgrid').datagrid('getEditor', {index:index,field:'fkb'});
		var fkzd = $('#tgrid').datagrid('getEditor', {index:index,field:'fkzd'});
		if(!$(ed.target).is(":checked")){//初始化，判断checkbox是否呗勾选
			$(fkb.target).combobox("disable");
			$(fkzd.target).combobox("disable");
		}
		$(ed.target).bind('click',function(){//绑定checkbox 点击事件
			if($(ed.target).is(":checked")){
				$(fkb.target).combobox("enable");
				$(fkzd.target).combobox("enable");
			}else{
				$(fkb.target).combobox("disable");
				$(fkzd.target).combobox("disable");
				$(fkb.target).combobox("clear");
				$(fkzd.target).combobox("clear");
			}
	    });
	} else {
		$('#tgrid').datagrid('selectRow', editIndex);
	}
}

function append(){
	if (endEditing()){
		$('#tgrid').datagrid('appendRow',{status:'P'});		//追加一行
		editIndex = $('#tgrid').datagrid('getRows').length-1;		//赋值editIndex
		$('#tgrid').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);		//修改最后一列
		var ed = $('#tgrid').datagrid('getEditor', {index:editIndex,field:'fkz'});
		var fkb = $('#tgrid').datagrid('getEditor', {index:editIndex,field:'fkb'});
		var fkzd = $('#tgrid').datagrid('getEditor', {index:editIndex,field:'fkzd'});
		if(!$(ed.target).is(":checked")){//初始化，判断checkbox是否呗勾选
			$(fkb.target).combobox("disable");
			$(fkzd.target).combobox("disable");
		}
		$(ed.target).bind('click',function(){//绑定checkbox 点击事件
			if($(ed.target).is(":checked")){
				$(fkb.target).combobox("enable");
				$(fkzd.target).combobox("enable");
			}else{
				$(fkb.target).combobox("disable");
				$(fkzd.target).combobox("disable");
				$(fkb.target).combobox("clear");
				$(fkzd.target).combobox("clear");
			}
	    });
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#tgrid').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function findTableNameExist(){
	var dataname=$("#dictionaryname").val();
	$.getJSON("findTablesNames",function(json){
		$json=$(json);
		var result=true;
		$json.each(function(){
			if($("#datasjbhy").val()=="ADDS"){
				if(this.sjbmc==dataname){
					$.messager.alert('消息提示','英文表明重复！','info');
					result= false;
				}
			}else{
				if(this.sjbmc==dataname&&$("#datasjbhy").val()!=dataname){
					$.messager.alert('消息提示','英文表明重复！','info');
					result= false;
				}
			}
		});
		if(result){
			accept(); 
		}
	});
}
function accept(){
	var dataname=$("#dictionaryname").val();
	var datasjbhy=$("#dictionaryhy").val();
	if (endEditing()){
		var rows = $('#tgrid').datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('消息提示','列属性不能为空！','info');
			return false;
		}
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			for (var j = 0; j < rows.length; j++) {
				var row1 = rows[j];
				if(row.pydm==row1.pydm&&row1!=row){
					$.messager.alert('消息提示','列名称重复！','info');
					return false;
				}
				/* if(row.sjxmc==row1.sjxmc&&row1!=row){
					$.messager.alert('消息提示','数据列名称重复！','info');
					return false;
				} */
			}
		}
		var DataObject = JSON.stringify($('#tgrid').datagrid('getData'));
		if($('#datasjbhy').val()=="ADDS"){
			$.post("CreateTable",{
					table:encodeURI(DataObject),
					sjbmc:encodeURI(dataname),
					sjbhy:encodeURI(datasjbhy)
				},
				function(result){
					eval(result);
			});
		}else{
			$.post("UpdateTable",{
					table:encodeURI(DataObject),
					sjbmc:encodeURI(dataname),
					sjbhy:encodeURI(datasjbhy)
				},
				function(result){
					eval(result);
			});
		}
		
	}else{
		$.messager.alert("提示","完成需要填写的字段或行，方可提交。");
	}
}
function reject(){
	$('#tgrid').datagrid('rejectChanges');
	editIndex = undefined;
}
</script>
	<input type="hidden" id="datasjbhy" value="${sjbmc}" >
	<input type="hidden" id="datasnamess" value="${sjbhy}" >
	<div id="winss" >
		英文表名：<input type="text" id="dictionaryname" value="" class="easyui-validatebox" 
		data-options="required:true" 
		style="border-radius:5px;box-shadow:1px 1px 5px #ccc;width:200px;margin-top:8px"/>
		中文含义：<input type="text" id="dictionaryhy" value=""  class="easyui-validatebox" 
		data-options="required:false" 
		style="border-radius:5px;box-shadow:1px 1px 5px #ccc;width:200px;margin-top:8px"/>
	</div>
	<table id="tgrid"></table>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>