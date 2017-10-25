<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.jfsl.pojo.*" %>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
  <head>
    <title>远程监控</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var querywin,queryform;
		var addwin,addform;
		var editwin,editform;
				
		$(function(){		
			grid=$('#grid').datagrid({
				title:'通知管理',
				url:'JsonControl',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[
				    {title:'ID',field:'id',align:'left',hidden:'true'},
				    {title:'设备编号',field:'number',width:100,align:'center'},
				    {title:'设备名称',field:'name',width:100,align:'center'},
				    {title:'所在房间',field:'address',width:120,align:'center'},
				    {title:'IP地址',field:'ip',width:120,align:'center'},
				    {title:'备注',field:'dateline',width:140,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				onClickRow:function(rowIndex,rowData){
					var controlip = "http://"+$.trim(rowData.ip)+":2000";
					 
					window.open(controlip,'newwindow','height=900,width=1400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')   //这句要写成一行 
				},
				onLoadError:function(){
					showLoadError();
				}
			 });
		});
	</script>
</head>  
<body>
	<table id="grid"></table>
	
	<div id="addform" style="width:400px;height:280px;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
		<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right"><sup>*</sup>标题:</div></td>
					<td>
					<input type="text" id="messageid" name="messageid" style="display:none;"/>
					<input type="text" id="title" name="title" style="width:200px"/>
					<input type="text" id="author" name="author" style="display:none;"/></td>
				</tr>					
				<tr>
					<td  align="right" valign="top"><div align="right"><sup>*</sup>内容：</div></td>
					<td><textarea id="content" name="content" style="width:200px;height:8em;"></textarea></td>
				</tr>
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>截止日期：</div></td>
					<td><input type="text" id="publishtime" name="publishtime"  style="display:none;"/>
					<input type="text" class="easyui-datebox" name="dateline" id="dateline" style="width:200px;"/></td>
				</tr>						
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="add();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="addcancel();">取消</a>
			</p>
	  	</form>
		</div>
	</div>
	
	<div id="editform" style="width:400px;height:280px;">
		<div style="padding:10px 10px 10px 20px;">
		<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right"><sup>*</sup>标题:</div></td>
					<td>
					<input type="text" id="editmessageid" name="messageid" style="display:none;"/>
					<input type="text" id="edittitle" name="title" style="width:200px"/>
					<input type="text" id="editauthor" name="author" style="display:none;"/></td>
				</tr>					
				<tr>
					<td  align="right" valign="top"><div align="right"><sup>*</sup>内容：</div></td>
					<td><textarea id="editcontent" name="content" style="width:200px;height:8em;"></textarea></td>
				</tr>
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>截止日期：</div></td>
					<td><input type="text" id="editpublishtime" name="publishtime"  style="display:none;"/>
					<input type="text" class="easyui-datebox" name="dateline" id="editdateline" style="width:200px;"/></td>
				</tr>						
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="edit();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="editcancel();">取消</a>
			</p>
	  	</form>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>