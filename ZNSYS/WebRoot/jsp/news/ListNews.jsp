<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<%
String begintime=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()); 
%>
<html>
  <head>
    <title>�������</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var querywin,queryform;
		$(function(){
			$('#department').combotree({
				url:'JsonDepartment',
				onSelect:function(node)
				{
					$('#user').combobox({
						url:'JsonUsername?departmentid='+node.id
					});
				}
			});
			$('#user').combobox({
				valueField:'id',
				textField:'username',
				editable:false
			});
			
			querywin=$('#queryform').window({
				title:"��ѯ����",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			queryform=querywin.find('form');
			
			grid=$('#grid').datagrid
			({
				title:'�������',
				url:'JsonOperateLog?begintime=<%=begintime%>',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[				    						
				    {title:'��ĿID',field:'projectid',width:100,align:'center'},
				    {title:'�������ID',field:'areaid',width:100,align:'center'},
				    {title:'����ID',field:'wellid',width:100,align:'center'},
				    {title:'ί�е�λID',field:'consignorid',width:100,align:'center'},
				    {title:'�����Ŀ',field:'testitem',width:180,align:'center'},
				    {title:'�ͱ�ʱ��',field:'submittime',width:80,align:'center'},
				    {title:'��������',field:'sendsample',width:80,align:'center'},
				    {title:'��λ����',field:'jobacceptsample',width:80,align:'center'},
				    {title:'��λ����',field:'jobanalysis',width:100,align:'center'},
				    {title:'��λУ��',field:'jobcheck',width:100,align:'center'},
				    {title:'��������',field:'reportapproval',width:100,align:'center'},
				    {title:'�������',field:'reportaccept',width:100,align:'center'},
				    {title:'�����ϴ�',field:'dataupload',width:100,align:'center'},
				    {title:'��ɽ���',field:'completeproject',width:100,align:'center'},

				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[{
					text:'��ѯ',
					iconCls:'icon-search',
					handler:find					
					},{
					text:'ˢ��',
					iconCls:'icon-reload',
					handler:function(){
						grid.datagrid('reload');
					}
				},
				{
					text:'����',
					iconCls:'icon-excel',
				    handler:function(){
				    	exportExcel(grid);//�˴���grid����Ҫ�������ݵı��
					}
				}],
				onLoadError:function(){
					showLoadError();
				}
			 });
		});

		function find()
		{
			queryform.form('clear');
			querywin.window('open');
		}

		function query()
		{
			var departmentid=$('#department').combotree('getValue');
			var username=$('#user').combobox('getValue');
			var begintime=$('#begindate').datebox('getValue');
			var endtime=$('#enddate').datebox('getValue');
			grid.datagrid({url:'JsonOperateLog?departmentid='+departmentid+'&username='+username+
				               '&begintime='+begintime+'&endtime='+endtime});
			grid.datagrid('reload');
			querywin.window('close');
		}

		function cancel()
		{
			querywin.window('close');
		}
	</script>
</head>  
<body>
	<table id="grid"></table>
	<div id="queryform" style="width:400px;height:220px;">
		<div  id="popup_content" style="padding:10px 10px 10px 20px;">
		<form>
		<table>
				<tr>
					<td align="right"  width="100"><div align="right"><sup>*</sup>���ţ�</div></td>
					<td><select id="department" style="width:200px;"></select></td>
				</tr>					
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>������</div></td>
					<td><select id="user" style="width:200px;"></select></td>
				</tr>
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>ʱ�䷶Χ��</div></td>
					<td><input type="text" readonly="readonly" class="easyui-datebox" name="begintime" id="begindate" style="width:85px;"></input>
					�� <input type="text" readonly="readonly" class="easyui-datebox" name="endtime" id="enddate" style="width:85px;"></input>
					</td>
				</tr>						
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="query();">��ѯ</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">ȡ��</a>
			</p>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>