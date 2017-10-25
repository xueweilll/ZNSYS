<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.jfsl.pojo.*" %>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<%
User user=(User)session.getAttribute("user");
String today=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()); 
//System.out.println(today);
String username="";
if (user!=null)
{
	username=user.getUsername();
	//System.out.println(today+"***"+username);
}
%>
<html>
  <head>
    <title>通知管理</title>
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
				url:'JsonAllMessage',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[
				    {title:'ID',field:'messageid',align:'left',hidden:'true'},
				    {title:'标题',field:'title',width:120,align:'left'},
				    {title:'发布者',field:'author',width:80,align:'center'},
				    {title:'内容',field:'content',width:450,align:'left'},
				    {title:'发布时间',field:'publishtime',width:80,align:'center'},
				    {title:'有效截止时间',field:'dateline',width:80,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addMessage
					},'-',{
					text:'修改',
					iconCls:'icon-edit',
					handler:editMessage
					},'-',{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteMessage
					},'-',{
					text:'查询',
					iconCls:'icon-search',
					handler:find					
					}],
				onLoadError:function(){
					showLoadError();
				}
			 });
		});

		/**按钮功能*/
		function addMessage(){
			addform.form('clear');
			addwin.window('open');
		}

		function editMessage(){
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
		function deleteMessage(){
			var row = grid.datagrid('getSelected');
			if (row==null)
				showDelete();
			else{
				confirmDelete('DeleteMessage?messageid='+row.messageid);
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
			var title=$('#title').val();
			var content=$('#content').val();

			/**注意时间类型的获取方法*/
			var dateline=$('#dateline').datebox('getValue');
			$('#author').val('<%=username%>');
			$('#publishtime').val('<%=today%>');
			if(title=="")
			{
				showWarning("请输入标题！");
				return;
			}
			if(content=="")
			{
				showWarning("请输入消息内容！");
				return;
			}
			if(dateline=="")
			{
				showWarning("请输入截止日期！");
				return;
			}
			addform.form('submit',{
				url:"InsertMessage",
				success:function(data){					
					eval(data);
					addwin.window('close');
					grid.datagrid('reload');
				}
			});
		}

		function edit(){
			var title=$('#edittitle').val();
			var content=$('#editcontent').val();

			/**注意时间类型的获取方法*/
			var dateline=$('#editdateline').datebox('getValue');
			$('#editauthor').val('<%=username%>');
			$('#editpublishtime').val('<%=today%>');
			if(title=="")
			{
				showWarning("请输入标题！");
				return;
			}
			if(content=="")
			{
				showWarning("请输入消息内容！");
				return;
			}
			if(dateline=="")
			{
				showWarning("请输入截止日期！");
				return;
			}
			editform.form('submit',{
				url:"UpdateMessage",
				success:function(data){					
					eval(data);
					editwin.window('close');
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
	
	<div id="queryform" style="width:400px;height:220px;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
		<form>
			<table>
				<tr>
					<td colspan="2">（以下内容选一即可）</td>
				</tr>					
				<tr>
					<td align="right"><div align="right"><sup>*</sup>发布者：</div></td>
					<td><select id="selectauthor" style="width:200px;"></select></td>
				</tr>
				<tr>
					<td align="right"><div align="right"><sup>*</sup>时间范围：</div></td>
					<td>
					<input type="text" readonly="readonly" class="easyui-datetimebox" name="begintime" id="begindate" style="width:85px;"></input>
					至 
					<input type="text" readonly="readonly" class="easyui-datetimebox" name="endtime" id="enddate" style="width:85px;"></input>
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