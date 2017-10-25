<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.jfsl.pojo.*" %>

<html>
  <head>
    <title>设备保养</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid,grid2;
		var querywin,queryform;
		var addwin,addform;
		var editwin,editform;
				
		$(function(){
			$("#equipmentid").combobox({
				url:'ComboEquipment',
				valueField:'id',   
				textField:'equipmentnumber',
				formatter:function(row){
					return row.equipmentnumber + "(" + row.equipmentname +")";
				}
			});
			
			$("#eequipmentid").combobox({
				url:'ComboEquipment',
				valueField:'id',   
				textField:'equipmentnumber',
				formatter:function(row){
					return row.equipmentnumber + "(" + row.equipmentname +")";
				}
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

			addwin=$('#addform').window({
				title:"新增保养",				
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
				title:"修改保养",				
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
				title:'设备保养信息',
				url:'JsonEquipmentMaintain',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				pageSize:20,
				columns:[[						
				    {title:'ID',field:'id',align:'left',hidden:'true'},
				    {title:'设备名称',field:'equipment.equipmentname',width:100,align:'left'},
				    {title:'设备编号',field:'equipment.equipmentnumber',width:80,align:'center',sortable:true},
				    {title:'保养类型',field:'equipmentmaintainname',width:120,align:'center'},
				    {title:'保养内容',field:'equipmentmaintaincontent',width:150,align:'center'},
				    {title:'保养负责人',field:'maintainpeople',width:80,align:'center'},
				    {title:'保养时间',field:'maintaintime',width:100,align:'center'},
				    {title:'置购时间',field:'equipment.buytime',width:100,align:'center',sortable:true},
				    {title:'生产厂商',field:'equipment.brand',width:120,align:'center'},
				    {title:'已经维护',field:'memo',width:150,align:'center'}
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
					}],
				onLoadError:function(){
					showLoadError();
				},
				onDblClickRow:editEquipment,
				enableHeaderClickMenu: true,        //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
            	enableHeaderContextMenu: true      //此属性开启表头列名称右键点击菜单
			 });//datagrid
		
			grid2=$('#grid2').datagrid({
				title:'需保养记录',
				url:'JsonNeedMaintain',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[						
				    {title:'ID',field:'id',align:'left',hidden:'true'},
				    {title:'设备名称',field:'equipment.equipmentname',width:100,align:'left'},
				    {title:'设备编号',field:'equipment.equipmentnumber',width:80,align:'center',sortable:true},
				    {title:'保养类型',field:'equipmentmaintainname',width:120,align:'center'},
				    {title:'保养内容',field:'equipmentmaintaincontent',width:150,align:'center'},
				    {title:'保养负责人',field:'maintainpeople',width:80,align:'center'},
				    {title:'保养时间',field:'maintaintime',width:100,align:'center'},
				    {title:'置购时间',field:'equipment.buytime',width:100,align:'center',sortable:true},
				    {title:'生产厂商',field:'equipment.brand',width:120,align:'center'},
				    {title:'已经维护',field:'memo',width:150,align:'center',formatter:function(value,row,index){
				    	return "<a href='#' onclick='addMemo()' class='easyui-linkbutton'>保养登记</a>";
				    }}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[{
					text:'处理维护',
					iconCls:'icon-add',
					handler:addMemo
					}],
				onLoadError:function(){
					showLoadError();
				}
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
				//不能load的额外载入
				$('#eequipmentid').combobox('setValue',row.equipment.id);
				editwin.window('open');
			}else{
				showUpdate();
			}
		}

		/**注意删除的公用函数*/
		function deleteEquipment(){
			var row = grid.datagrid('getSelected');
			if (row==null)
				show();
			else{
				confirmDelete('DeleteEquipmentMaintain?id='+row.id);
			}
		}
		
		function addMemo(){
			var row = grid2.datagrid('getSelected');
			
		if (row == null)
				showUpdate();
			else {
				$.messager.confirm('确认', '您确认想要处理这条保养记录吗？', function(r) {
					if (r) {
						$.ajax({
							url : 'AddMemo?id=' + row.id,
							success : function(data) {
								eval(data);
								grid2.datagrid('reload');
								grid.datagrid('reload');
							}
						});
					} else
						return;
				});
			}
		}

		function find() {
			queryform.form('clear');
			querywin.window('open');
		}

		function deleteAll() {
			confirmDelete('DeleteAll');
		}

		/*实际操作*/
		function add() {
			//注意获取下拉框值的方法不一样
			var equipmentid = $('#equipmentid').combobox('getValue');
			//alert(equipmentid);
			var maintaintime = $('#maintaintime').datebox('getValue');

			if (equipmentid == "") {
				showWarning("请输入保养设备！");
				return;
			}
			if (maintaintime == "") {
				showWarning("请输入保养时间！");
				return;
			}
			addform.form('submit', {
				url : "InsertEquipmentMaintain",
				success : function(data) {
					eval(data);
					addwin.window('close');
					grid.datagrid('reload');
				}
			});
		}

		function edit() {
			//注意获取下拉框值的方法不一样
			var equipmentid = $('#eequipmentid').combobox('getValue');
			//alert(equipmentid);
			var maintaintime = $('#emaintaintime').datebox('getValue');

			if (equipmentid == "") {
				showWarning("请输入保养设备！");
				return;
			}
			if (maintaintime == "") {
				showWarning("请输入保养时间！");
				return;
			}
			editform.form('submit', {
				url : "UpdateEquipmentMaintain",
				success : function(data) {
					eval(data);
					editwin.window('close');
					grid.datagrid('reload');
				}
			});
		}

		function query() {
			var author = $('#selectauthor').val();
			var begintime = $('#begindate').datetimebox('getValue');
			var endtime = $('#enddate').datetimebox('getValue');
			grid.datagrid({
				url : 'JsonQuery?author=' + author + '&begintime=' + begintime
						+ '&endtime=' + endtime
			});
			grid.datagrid('reload');
			querywin.window('close');
		}

		/**取消按钮*/
		function cancel() {
			querywin.window('close');
		}

		function editcancel() {
			editwin.window('close');
		}

		function addcancel() {
			addwin.window('close');
		}
	</script>
</head>  
<body class="easyui-layou">
	<div data-option="region:'center'" style="height:50%">
		<table id="grid"></table>
	</div>
	<div data-option="region:'south',collapsible:'true'" style="height:50%">
		<table id="grid2"></table>
	</div> 
	
	<div id="addform" style="height:290px;width:400px;">
		<div id="popup_content" style="padding:10px 10px 10px 10px;">
		<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right">保养设备：</div></td>
					<td>
					<input type="text" id="id" name="id" style="display:none;"/>
					<input type="text" id="equipmentid" name="equipmentid" style="width:200px" required="true"/>
					</td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">保养类型：</div></td>
					<td><input type="text" id="equipmentmaintainname" name="equipmentmaintainname" style="width:200px;" required="true"/></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">保养内容：</div></td>
					<td><textarea type="text" id="equipmentmaintaincontent" name="equipmentmaintaincontent" style="width:200px;height:4em"></textarea></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">保养负责人：</div></td>
					<td><input type="text" id="maintainpeople" name="maintainpeople" style="width:200px;" required="true"/></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">保养时间：</div></td>
					<td><input type="text" class="easyui-datebox" id="maintaintime" name="maintaintime" style="width:200px;" required="true"/></td>
				</tr>
				<tr>
					<td  align="right" valign="top"></td>
					<td><textarea id="memo" name="memo" style="width:200px;height:4em;display:none"></textarea></td>
				</tr>					
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="add();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="addcancel();">取消</a>
			</p>
	  	</form>
		</div>
	</div>
	
	<div id="editform" style="height:340px;width:400px;">
		<div id="popup_content" style="padding:10px 10px 10px 10px;">
		<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right">保养设备：</div></td>
					<td>
					<input type="text" id="eid" name="id" style="display:none;"/>
					<input type="text" id="eequipmentid" name="equipmentid" style="width:200px" required="true"/>
					</td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">保养类型：</div></td>
					<td><input type="text" id="eequipmentmaintainname" name="equipmentmaintainname" style="width:200px;" required="true"/></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">保养内容：</div></td>
					<td><textarea type="text" id="eequipmentmaintaincontent" name="equipmentmaintaincontent" style="width:200px;height:4em"></textarea></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">保养负责人：</div></td>
					<td><input type="text" id="emaintainpeople" name="maintainpeople" style="width:200px;" required="true"/></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">保养时间：</div></td>
					<td><input type="text" class="easyui-datebox" id="emaintaintime" name="maintaintime" style="width:200px;" required="true"/></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">是否已保养处理：<br/>（用于修复保养）</div></td>
					<td><textarea id="ememo" name="memo" style="width:200px;height:4em;"></textarea></td>
				</tr>					
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="edit();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="editcancel();">取消</a>
			</p>
	  	</form>
		</div>
	</div>
	
	<div id="queryform" style="width:400px;height:220px;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
		<form>
			<table>
				<tr>
					<td colspan="2">（以下内容选一即可）</td>
				</tr>					
				<tr>
					<td align="right"><div align="right">设备名称：</div></td>
					<td><select id="equipmentname" style="width:200px;"></select></td>
				</tr>
				<tr>
					<td align="right"><div align="right"><sup>*</sup>购买时间：</div></td>
					<td>
					<input type="text" readonly="readonly" class="easyui-datetimebox" name="buytime" id="buytime" style="width:85px;"></input>				
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
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>