<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.jfsl.pojo.*" %>

<html>
  <head>
    <title>设备信息</title>
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
				title:"新增设备",				
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
				title:'设备信息',
				url:'JsonEquipment',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[						
				    {title:'ID',field:'id',align:'left',hidden:'true'},
				    {title:'设备名称',field:'equipmentname',width:120,align:'left'},
				    {title:'设备编号',field:'equipmentnumber',width:100,align:'center',sortable:true},
				    {title:'购买时间',field:'buytime',width:120,align:'center',sortable:true},
				    {title:'购买金额',field:'worth',width:80,align:'center',sortable:true},
				    {title:'生产厂商',field:'brand',width:120,align:'center'},
				    {title:'参数描述',field:'description',width:150,align:'center'},
				    {title:'设备负责人',field:'admin',width:120,align:'center'},
				    {title:'备注',field:'memo',width:150,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addEquipment
					},'-',{
					text:'修改',
					iconCls:'icon-edit',
					handler:editEquipment
					},'-',{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteEquipment
					},'-',{
						text:'需保养设备',
						iconCls:'icon-admin',
						handler:Maintain
					},'-',{
					text:'查询',
					iconCls:'icon-search',
					handler:queryEquipment
				}],
				onLoadError:function(){
					showLoadError();
				},
				onDblClickRow:editEquipment,
				enableHeaderClickMenu: true,        //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
            	enableHeaderContextMenu: true      //此属性开启表头列名称右键点击菜单
			 });//datagrid
		});

		/**按钮功能*/
		function addEquipment(){
			addform.form('clear');
			addwin.window('open');
		}

		function editEquipment(){
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
		function deleteEquipment(){
			var row = grid.datagrid('getSelected');
			if (row==null)
				showDelete();
			else{
				confirmDelete('DeleteEquipment?id='+row.id);
			}		
		}
		
		function queryEquipment(){
			queryform.form('clear');
			querywin.window('open');
		}

		function deleteAll(){
			confirmDelete('DeleteAll');
		}
		
		/*实际操作*/
		function add(){
			var equipmentname=$('#equipmentname').val();
			var equipmentunit=$('#equipmentnumber').val();

			if(equipmentname=="")
			{
				showWarning("请输入设备名称！");
				return;
			}
			if(equipmentunit=="")
			{
				showWarning("请输入设备编号！");
				return;
			}
			addform.form('submit',{
				url:"InsertEquipment",
				success:function(data){					
					eval(data);
					addwin.window('close');
					grid.datagrid('reload');
				}
			});
		}

		function edit(){
			var equipmentname=$('#editequipmentname').val();
			var equipmentunit=$('#editequipmentunit').val();

			if(equipmentname=="")
			{
				showWarning("请输入设备名称！");
				return;
			}
			if(equipmentunit=="")
			{
				showWarning("请输入设备规格型号！");
				return;
			}
			editform.form('submit',{
				url:"UpdateEquipment",
				success:function(data){					
					eval(data);
					editwin.window('close');
					grid.datagrid('reload');
				}
			});
		}
		
		function Maintain(){
			grid.datagrid({url:'QueryEquipmentMaintain'});
			grid.datagrid('reload');
		}
		
		function query(value,name){
			//alert(value+"#"+name);
			if(value!=null&&name!=null){
				grid.datagrid({url:'JsonEquipmentQuery?field='+name+'&value='+value});
				grid.datagrid('reload');
				querywin.window('close');
			}else return;
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
	
	<div id="addform" style="height:330px;width:750px;">
		<div id="popup_content" style="padding:10px 10px 10px 10px;">
		<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right">设备名称：</div></td>
					<td>
					<input type="text" id="id" name="id" style="display:none;"/>
					<input type="text" id="equipmentname" name="equipmentname" style="width:200px" required="true"/>
					</td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">设备编号：</div></td>
					<td><input type="text" id="equipmentnumber" name="equipmentnumber" style="width:200px;" required="true"/></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">购买时间：</div></td>
					<td><input type="text" id="buytime" class="easyui-datebox" name="buytime" style="width:200px;"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">设备价值：</div></td>
					<td><input type="text" id="worth" class="easyui-numberbox" name="worth" style="width:200px;" required="required" data-options="min:0,precision:2"></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">生产厂商：</div></td>
					<td><input type="text" id="brand" name="brand" style="width:200px;"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">负责人：</div></td>
					<td><input type="text" id="admin" name="admin" style="width:200px;"></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">参数描述：</div></td>
					<td><textarea id="description" name="description" style="width:200px;height:8em;"></textarea></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">设备备注：</div></td>
					<td><textarea id="memo" name="memo" style="width:200px;height:8em;"></textarea></td>
				</tr>					
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="add();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="addcancel();">取消</a>
			</p>
	  	</form>
		</div>
	</div>
	
	<div id="editform" style="height:330px;width:750px;">
		<div style="padding:10px 10px 10px 10px;">
			<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right">设备名称：</div></td>
					<td>
					<input type="text" id="editid" name="id" style="display:none;"/>
					<input type="text" id="editequipmentname" name="equipmentname" style="width:200px" required="true"/>
					</td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">设备编号：</div></td>
					<td><input type="text" id="editequipmentnumber" name="equipmentnumber" style="width:200px;" required="true"/></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">购买时间：</div></td>
					<td><input type="text" id="editbuytime" class="easyui-datebox" name="buytime" style="width:200px;"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">设备价值：</div></td>
					<td><input type="text" id="editworth" class="easyui-numberbox" name="worth" style="width:200px;" required="required" data-options="min:0,precision:2"></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">生产厂商：</div></td>
					<td><input type="text" id="editbrand" name="brand" style="width:200px;"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">负责人：</div></td>
					<td><input type="text" id="editadmin" name="admin" style="width:200px;"></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">参数描述：</div></td>
					<td><textarea id="editdescription" name="description" style="width:200px;height:8em;"></textarea></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">设备备注：</div></td>
					<td><textarea id="editmemo" name="memo" style="width:200px;height:8em;"></textarea></td>
				</tr>					
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="edit();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="editcancel();">取消</a>
			</p>
	  	</form>
		</div>
	</div>

	<div id="queryform" style="width:400px;height:150px;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
			<form>
				<table>
					<tr>
						<td>
							<style>
								.searchbox-text{width:200px !important;}
							</style>
							<input id="ss" class="easyui-searchbox" style="width:320px;height:30px;" data-options="searcher:query,prompt:'请选择查询条件并输入查询内容……',menu:'#mm'"></input>
							<div id="mm" style="width:140px">
								<div name="equipmentname" iconCls="icon-mointor">设备名称</div>
								<div name="equipmentnumber" iconCls="icon-notepad">设备编号</div>
							</div>
						</td>
					</tr>
				</table>
				<p align="center">
					<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">取消</a>
				</p>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>