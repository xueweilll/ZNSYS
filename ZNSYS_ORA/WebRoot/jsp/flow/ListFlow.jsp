<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.jfsl.pojo.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>流程图process</title>
<%@ include file="../../inc/easyui.inc"%>
<script>
		var addwin,addform;
		var nodegrid;
		var confirm;
		
		//下拉框
		var xmzmcCombo;
		var jcmxCombo;
		
		$(function(){
			/* 下拉框 */
			xmzmcCombo = $("#xmzmc").combobox({
				url:'CombXMZMC',   
				valueField:'id',   
				textField:'text',
				onSelect: function (rec) {
					var url='JsonSCGCbyXMZMC?xmzmc='+rec.text;  
         			nodegrid.datagrid({url:url});
         			nodegrid.datagrid("reload");
         		}  
			});
			
			jcmxCombo = $("#jcxm").combobox({
				url:'CombJCXM',   
				valueField:'id',   
				textField:'text',
         		onSelect: function (rec){
         			//alert(JSON.stringify(rec));
         			var url='JsonSCGCbyJCXM?jcxm='+rec.text;  
         			nodegrid.datagrid({url:url});
         			nodegrid.datagrid("reload");
         		}   
			});
				
			addwin=$('#addform').window({
				title:"请输入新建流程名称",				
				closed:false,
				modal:true,
				closable:false,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			addform=addwin.find('form');
			
			confirm=$('#confirm').window({
				title:"系统提示",
				closed:true,
				modal:true,
				iconCls:'icon-tip',
				closable:false,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			
			nodegrid=$('#NodeGrid').datagrid({
				url:'JsonSCGC',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[
				    {title:'参数名',field:'id.csname',width:250,align:'center'},
				    {title:'过程顺序',field:'gcsx',width:100,align:'center'},
				    {title:'生产过程',field:'id.scgc',width:200,align:'center'},
				    {title:'表名',field:'name',width:350,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pageSize:20,
				pagination:false,
				fitColumns:true,
				toolbar:[{
					text:'添加节点(可双击快速添加)',
					iconCls:'icon-add',
					handler:addNode
					},"-",{
					text:'新增数据字典',
					iconCls:'icon-add',
					handler:addDictionary
					}],
				onLoadError:function(){
					showLoadError();
				},
				onDblClickRow:addNode
			 });
			 
			//iframe高度
			var ParentHeight = window.parent.document.body.clientHeight - 135;
			//alert(ParentHeight);
			$("#flow").height(ParentHeight);
			
			//右布局宽度
			var Width = document.body.clientWidth - 600;
			//alert(Width);
			$('#LayoutBody').layout('panel', 'east').panel('resize',{width:Width});
	        $('#LayoutBody').layout('resize');
	        
	        xmzmcCombo.combobox("select",1);
		});

		/*实际操作*/
		function add(){
			var title = $("#title").val();
			if(title == ''){
				alert("流程名称不能为空！");
				$("#title").focus();
			}else{
				document.getElementById('flow').contentWindow.setTitle(title);
				document.getElementById('flow').contentWindow.flush();
				addwin.window('close');
			}
		}
		
		function addNode(){
			var row = nodegrid.datagrid('getSelected');
			if (row == null)
				showWarning('请先选择要添加的数据！');
			else{
				//alert(row.id.scgc);
				document.getElementById('flow').contentWindow.addNode(row.id.scgc);
			}
		}
		
		function addDictionary(){
			alert("请在新弹出的页面中添加数据字典后，点击上方的标签页切换回流程定义！");
			window.parent.addTab("添加数据字典","ListAddDictionary","icon-002",true,false);
			confirm.window('open');
		}
		
		function open(){
			addwin.window('open');
		}
		
		function list(){
			grid=$('#grid').datagrid({
				title:'流程图管理(双击查看数据)',
				url:'JsonFlow',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[
				    {title:'ID',field:'id',align:'left',hidden:'true'},
				    {title:'流程名称',field:'title',width:120,align:'center'},
				    {title:'发布者',field:'author',width:120,align:'center'},
					{title:'连接线数据',field:'linedata',align:'left',hidden:'true'},
					{title:'连接线条数',field:'linecount',align:'left',hidden:'true'},
					{title:'节点数据',field:'nodedata',align:'left',hidden:'true'},
					{title:'节点个数',field:'nodecount',align:'left',hidden:'true'},
					{title:'区域数据',field:'areadata',align:'left',hidden:'true'},
					{title:'区域个数',field:'areacount',align:'left',hidden:'true'},
				    {title:'发布时间',field:'publishtime',width:120,align:'center'}
				]],
				toolbar:[{
					text:'查看',
					iconCls:'icon-add',
					handler:LookFlow
					},'-',{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteFlow
					}],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				onLoadError:function(){
					showLoadError();
				},
				onDblClickRow:function(rowIndex, rowData){
					var Title = "";
					var LineData = "";
					var LineCount = "";
					var NodeData = "";
					var NodeCount = "";
					var AreaData = "";
					var AreaCount = "";
					
					try{
						Title = rowData.title;
						LineData = rowData.linedata;
						LineCount = rowData.linecount;
						NodeData = rowData.nodedata;
						NodeCount = rowData.nodecount;
						AreaData = rowData.areadata;
						AreaCount = rowData.areacount;
						document.getElementById('flow').contentWindow.flush();
						document.getElementById('flow').contentWindow.ReloadData(Title,LineData,LineCount,NodeData,NodeCount,AreaData,AreaCount);
					}catch(e){
						alert("数据提取出错！");
					}
					$("#datatable").window('close');
				}
			 });
			$("#datatable").window('open');
		}
		
		/*实际功能*/
		function LookFlow(){
			
		}
		
		/* 完成新增数据字典后刷新 */
		function NodeFlush(){
			nodegrid.datagrid('reload');
			confirm.window('close');
		}
		
		function deleteFlow(){
			var row = grid.datagrid('getSelected');
			if (row == null)
				showDelete();
			else{
				confirmDelete('DeleteFlow?id='+row.id);
			}
		}
	</script>
<style>
*{
	margin:0px;
}
</style>
</head>
<body class="easyui-layout" id="LayoutBody">
	<!-- 主要框架 -->
	<div region="center" style="width:600px;padding:0px 5px 5px 0px;overflow:hidden;">
		<iframe width="100%" height="100%" src="jsp/flow/Flow.jsp" id="flow" name="flow" scrolling="no" frameborder="0" style="margin-top:2px;margin-left:2px;">
		</iframe>
	</div>
	<div region="east" title="流程节点" style="width:200px;" id="EastRegion">
		<table style="width:100%;height:auto;padding:0px;font-size:small;font-weight:bolder" align="center"  >
			<tr>
				<td>
			 		项目名称：<input id="xmzmc"/>
			 		检测项目：<input id="jcxm"  />
			 	</td>
			 </tr>
		</table>
		<table id="NodeGrid"></table>
	</div>
	
	<!-- 弹出框 -->
	<div id="addform" style="width:400px;height:150px;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
		<br/>
		<form>
			<table>
				<tr>
					<td align="right"  width="100"><div align="right"><sup>*</sup>新流程名称:</div></td>
					<td>
						<input type="text" id="title" name="title" style="width:200px;box-shadow:0px 0px 3px rgba(0,0,0,0.3); "/>
					</td>
				</tr>						
			</table>
			<br/>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="add();" style="margin-right:15px;">确定</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="$('#addform').window('close')">取消新建</a>
			</p>
	  	</form>
		</div>
	</div>
	
	<!-- 刷新页面确认框 -->
	<div id="confirm" style="width:200px;">
		<div style="padding:10px 10px 10px 20px;">
			<p style="font-size:1.2em;line-height:1.4em">请添加完成新数据字典后，点击刷新！</p><br>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-reload" onClick="NodeFlush();" style="margin-right:15px;">刷新</a>
			</p>
		</div>
	</div>
	
	<div class="easyui-window" closed="true" id="datatable" style="width:800px;height:500px;" title="打开流程">
		<table id="grid"></table>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>