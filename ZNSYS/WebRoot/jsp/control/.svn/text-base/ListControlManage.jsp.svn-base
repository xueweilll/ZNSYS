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

			addwin=$('#addform').window({
				title:"新增通知",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			addform=addwin.find('form');

			editwin=$('#editform').window({
				title:"修改通知",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			editform=editwin.find('form');
			
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
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addControl
					},'-',{
					text:'修改',
					iconCls:'icon-edit',
					handler:editControl
					},'-',{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteControl
					}],
				onLoadError:function(){
					showLoadError();
				}
			 });
		});

		/**按钮功能*/
		function addControl(){
			addform.form('clear');
			addwin.window('open');
		}

		function editControl(){
			var row = $('#grid').datagrid('getSelected');
			if(row!=null){
				editform.form('load',row);
				editform.form('validate');
				editwin.window('open');
			}else{
				showUpdate();
			}
		}

		/**注意删除的公用函数*/
		function deleteControl(){
			var row = grid.datagrid('getSelected');
			if (row==null)
				showDelete();
			else{
				confirmDelete('DeleteControl?id='+row.id);
			}		
		}
		
		function find(){
			queryform.form('clear');
			querywin.window('open');
		}

		function deleteAll(){
			confirmDelete('DeleteAll');
		}
		
		/*实际操作*/
		function add(){
			var name=$('#name').val();
			var ip=$('#ip').val();

			if(name=="")
			{
				showWarning("请输入设备名称！");
				return;
			}
			if(ip=="")
			{
				showWarning("请输入IP地址！");
				return;
			}
			addform.form('submit',{
				url:"InsertControl",
				success:function(data){	
					try{				
						eval(data);
					}
					catch(e){
						alert(data);
					}
					addwin.window('close');
					grid.datagrid('reload');
				}
			});
		}

		function edit(){
			var name=$('#editname').val();
			var ip=$('#editip').val();

			if(name=="")
			{
				showWarning("请输入设备名称！");
				return;
			}
			if(ip=="")
			{
				showWarning("请输入IP地址！");
				return;
			}
			editform.form('submit',{
				url:"UpdateControl",
				success:function(data){					
					eval(data);
					editwin.window('close');
					grid.datagrid('reload');
				}
			});
		}
		
		function query(){
			grid.datagrid('reload');
			querywin.window('close');
		}

		/**取消按钮*/
		function cancel(){
			querywin.window('close');
		}

		function editcancel(){
			editwin.window('close');
		}
		
		function addcancel(){
			addwin.window('close');
		}
	</script>
</head>  
<body>
	<table id="grid"></table>
	
	<div id="addform" style="width:400px;height:350px;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
		<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right"><sup>*</sup>设备编号:</div></td>
					<td>
					<input type="text" id="id" name="id" style="display:none;"/>
					<input type="text" id="number" name="number" style="width:200px"/></td>
				</tr>					
				<tr>
					<td align="right" valign="top"><div align="right"><sup>*</sup>设备名称：</div></td>
					<td><input type="text" id="name" name="name" style="width:200px;"/></td>
				</tr>
				<tr>
					<td align="right"><div align="right">所在房间：</div></td>
					<td>
						<input type="text" id="address" name="address"  style="width:200px"/>
					</td>
				</tr>
				<tr>
					<td align="right"><div align="right"><sup>*</sup>IP地址：</div></td>
					<td>
						<input type="text" id="ip" name="ip"  style="width:200px"/>
					</td>
				</tr>		
				<tr>
					<td align="right"><div align="right">备注：</div></td>
					<td>
						<textarea id="memo" name="memo"  style="width:200px;height:8em;"></textarea>
					</td>
				</tr>					
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="add();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="addcancel();">取消</a>
			</p>
	  	</form>
		</div>
	</div>
	
	<div id="editform" style="width:400px;height:350px;">
		<div style="padding:10px 10px 10px 20px;">
		<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right"><sup>*</sup>设备编号:</div></td>
					<td>
					<input type="text" id="editid" name="id" style="display:none;"/>
					<input type="text" id="editnumber" name="number" style="width:200px"/></td>
				</tr>					
				<tr>
					<td align="right" valign="top"><div align="right"><sup>*</sup>设备名称：</div></td>
					<td><input type="text" id="editname" name="name" style="width:200px;"/></td>
				</tr>
				<tr>
					<td align="right"><div align="right">所在房间：</div></td>
					<td>
						<input type="text" id="editaddress" name="address"  style="width:200px"/>
					</td>
				</tr>
				<tr>
					<td align="right"><div align="right"><sup>*</sup>IP地址：</div></td>
					<td>
						<input type="text" id="editip" name="ip"  style="width:200px"/>
					</td>
				</tr>		
				<tr>
					<td align="right"><div align="right">备注：</div></td>
					<td>
						<textarea id="editmemo" name="memo"  style="width:200px;height:8em;"></textarea>
					</td>
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