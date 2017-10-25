<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.jfsl.pojo.*" %>

<html>
  <head>
    <title>生产过程管理</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var querywin,queryform;
		var addwin,addform;
		var editwin,editform;
				
		$(function(){
			 $("#cc").combobox({
				url:'ComboSJZD',   
				valueField:'sjbmc',   
				textField:'sjbhy',
				formatter: function(row){
					if(row.sjbhy == ""){
						return row.sjbmc;
					}else return row.sjbmc + "(" +  row.sjbhy + ")";
				},
         		onSelect:function(record){ 
         			//alert(JSON.stringify(record));
         			if($('#name').val() != ""){
         				var temp = $('#name').val();
         				$('#name').val(temp + "\\" + record.sjbmc);	
         			}else{
         				$('#name').val(record.sjbmc);
         			}
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
				title:"新增生产过程",				
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
				title:"修改生产过程",				
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
				title:'生产过程定义',
				url:'JsonSCGC',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[						
				    {title:'项目组名称',field:'id.xmzmc',width:100,align:'center'},
				    {title:'检测项目',field:'id.jcxm',width:120,align:'center'},
				    {title:'参数名称',field:'id.csname',width:130,align:'center'},
				    {title:'生产过程',field:'id.scgc',width:110,align:'center'},
				    {title:'检测项目过程',field:'id.jcxmgc',width:80,align:'center'},
				    {title:'过程顺序',field:'gcsx',width:100,align:'center',sortable:true},
				 //   {title:'表格视图资源',field:'gridviewsource',width:100,align:'center'},
				    {title:'链接地址',field:'url',width:60,align:'center'},
				    {title:'标记',field:'sign',width:60,align:'center',
				    	formatter:function(value,row,index){	//将0和1的数据进行处理
				    		if(row.sign){return "√"}else{return "×";}
				    	}
				    },
				    {title:'是否上传',field:'ysc',width:60,align:'center',
				    	formatter:function(value,row,index){	//将0和1的数据进行处理
				    		if(row.ysc){return "√"}else{return "×";}
				    	}
				    },
				//    {title:'表名',field:'name',width:180,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addScgc
					},'-',{
					text:'修改',
					iconCls:'icon-edit',
					handler:editScgc
					},'-',{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteScgc					
					}],
				onLoadError:function(){
					showLoadError();
				},
				pageSize:20,
				onDblClickRow:editScgc,
				enableHeaderClickMenu: true,        //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
            	enableHeaderContextMenu: true      //此属性开启表头列名称右键点击菜单
			 });//datagrid
		});

		/**按钮功能*/
		function addScgc(){
			addform.form('clear');
			addwin.window('open');
		}

		function editScgc(){
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
		function deleteScgc(){
			var row = grid.datagrid('getSelected');
			if (row==null)
				showDelete();
			else{
				confirmDelete('DeleteScgc?id='+row.id);
			}		
		}
		
		function find(){
			queryform.form('clear');
			querywin.window('open');
		}
		
		/*实际操作*/
		function add(){
			var Scgcname=$('#Scgcname').val();
			var Scgcunit=$('#Scgcnumber').val();

			if(Scgcname=="")
			{
				showWarning("请输入设备名称！");
				return;
			}
			if(Scgcunit=="")
			{
				showWarning("请输入设备编号！");
				return;
			}
			addform.form('submit',{
				url:"InsertScgc",
				success:function(data){					
					eval(data);
					addwin.window('close');
					grid.datagrid('reload');
				}
			});
		}

		function edit(){
			var Scgcname=$('#editScgcname').val();
			var Scgcunit=$('#editScgcunit').val();

			if(Scgcname=="")
			{
				showWarning("请输入设备名称！");
				return;
			}
			if(Scgcunit=="")
			{
				showWarning("请输入设备规格型号！");
				return;
			}
			addform.form('submit',{
				url:"InsertScgc",
				success:function(data){					
					eval(data);
					addwin.window('close');
					grid.datagrid('reload');
				}
			});
		}
		
		function query(){
			var author=$('#selectauthor').val();
			var begintime=$('#begindate').datetimebox('getValue');
			var endtime=$('#enddate').datetimebox('getValue');
			grid.datagrid({url:'JsonQuery?author='+author+'&begintime='+begintime+'&endtime='+endtime});
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
	
	<div id="addform" style="height:330px;width:750px;">
		<div id="popup_content" style="padding:5px;">
		<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right">项目组名称：</div></td>
					<td>
					<input type="text" id="xmzmc" name="xmzmc" style="width:200px" required="true"/>
					</td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">检测项目：</div></td>
					<td><input type="text" id="jcxm" name="jcxm" style="width:200px;" required="true"/></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">参数名称：</div></td>
					<td><input type="text" id="csname" name="csname" style="width:200px;"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">生产过程：</div></td>
					<td><input type="text" id="scgc" name="scgc" style="width:200px;" required="required"></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">检测项目过程：</div></td>
					<td><input type="text" id="jcxmgc" name="jcxmgc" style="width:200px;"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">过程顺序：</div></td>
					<td><input type="text" id="gcsx" name="gcsx" style="width:200px;"></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">表格视图资源：</div></td>
					<td><input type="text" id="gridviewsource" name="gridviewsource" style="width:200px;"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">链接地址：</div></td>
					<td><input type="text" id="url" name="url" style="width:200px;"></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">标记：</div></td>
					<td><input type="checkbox" id="sign" name="sign"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">是否已上传：</div></td>
					<td><input type="checkbox" id="ysc" name="ysc"></td>
				</tr>
				<tr>
					<td align="right" valign="top"><div align="right">关联表名：</div></td>
					<td  align="left" valign="top" colspan="3">
						<textarea id="name" name="name" style="width:90%;height:4em;"></textarea>
					</td>
					<td valign="top"><input id="cc" name="nameselect" style="width:200px;"><br>(请在此下拉选择表添加)</td>
				</tr>					
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="add();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="addcancel();">取消</a>
			</p>
	  	</form>
		</div>
	</div>
	
	<div id="editform" style="height:330px;width:700px;">
		<div style="padding:10px 10px 10px 10px;">
			<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right">项目组名称：</div></td>
					<td>
					<input type="text" id="xmzmc" name="xmzmc" style="width:200px" required="true"/>
					</td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">检测项目：</div></td>
					<td><input type="text" id="jcxm" name="jcxm" style="width:200px;" required="true"/></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">参数名称：</div></td>
					<td><input type="text" id="csname" name="csname" style="width:200px;"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">生产过程：</div></td>
					<td><input type="text" id="scgc" name="scgc" style="width:200px;" required="required"></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">检测项目过程：</div></td>
					<td><input type="text" id="jcxmgc" name="jcxmgc" style="width:200px;"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">过程顺序：</div></td>
					<td><input type="text" id="gcsx" name="gcsx" style="width:200px;"></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">表格视图资源：</div></td>
					<td><input type="text" id="gridviewsource" name="gridviewsource" style="width:200px;"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">链接地址：</div></td>
					<td><input type="text" id="url" name="url" style="width:200px;"></td>
				</tr>
				<tr>
					<td  align="right" valign="top"><div align="right">标记：</div></td>
					<td><input type="checkbox" id="sign" name="sign"></td>
					<td style="width:20px;"></td>
					<td  align="right" valign="top"><div align="right">是否已上传：</div></td>
					<td><input type="checkbox" id="ysc" name="ysc"></td>
				</tr>
				<tr>
					<td align="right" valign="top"><div align="right">关联表名：</div></td>
					<td  align="left" valign="top" colspan="3">
						<textarea id="name" name="name" style="width:90%;height:4em;"></textarea>
					</td>
					<td valign="top"><input id="cc" name="nameselect" style="width:200px;"><br>(请在此下拉选择表添加)</td>
				</tr>					
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="edit();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="editcancel();">取消</a>
			</p>
	  	</form>
		</div>
	</div>
	
	<div id="queryform" style="width:700px;height:330px;">
		<div id="popup_content" style="padding:5px;">
		<form>
			<table>
				<tr>
					<td colspan="2">（以下内容选一即可）</td>
				</tr>					
				<tr>
					<td align="right"><div align="right">设备名称：</div></td>
					<td><select id="Scgcname" style="width:200px;"></select></td>
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