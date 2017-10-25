<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
<head>
	<title>通讯录表</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@ include file="../../inc/easyui.inc"%>
	<script type="text/javascript">
		var grid;
		var win,querywin;
		var form,queryform;		
		
		$(function(){	
			  querywin=$('#querywin').window({
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
			  
				win=$('#addlistwin').window({
				title:"通讯录",
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			form=win.find('form');
										
			grid=$('#addlistgrid').datagrid({
			title:'通讯录',
			url:'JsonAddList',
	        height:420,
			nowrap: false,
		    striped: true,
			columns:[[
			        {title:'姓名',field:'membername',width:100,align:'center'},
					{title:'电话',field:'tel',width:100,align:'center'},
					{title:'单位部门',field:'memberdept',width:150,align:'center'},
					{title:'备注',field:'memo',width:250,align:'center'}
				]],
			singleSelect:true,
			rownumbers:true,
			pagination:true,
			fit:true,
			toolbar:[{
					text:'新增',
					iconCls:'icon-add',
					handler:addAddlist
				},{
					text:'编辑',
					iconCls:'icon-edit',
					handler:editAddlist
				},{
					text:'删除',
					iconCls:'icon-remove',
					handler:deleteAddlist
				},{
					text:'查询',
					iconCls:'icon-search',
				    handler:queryAddlist
				}],
				onLoadError:function(){
					showLoadError();
				}
			});
		})
		
		function query()
		{
			var querymembername1=$('#membername1').val();
			var querymemberdept1=$('#memberdept1').val();
			grid.datagrid({url:'QueryAddList?membername='+querymembername1+
								'&memberdept='+querymemberdept1
				            });
            grid.datagrid('reload');
			querywin.window('close');
		}
		
		function queryAddlist(){
				queryform.form('clear');
				querywin.window('open');
			}

			
		function addAddlist(){
			form.form('clear');
			win.window('open');
			form.url='InsertAddList';
		}
		
		function editAddlist(){
			var row = grid.datagrid('getSelected');
			if (row){
				form.form('clear');
				$('#membername').val(row.membername);
				$('#tel').val(row.tel);
				$('#memberdept').val(row.memberdept);
				$('#memo').val(row.memo);
				form.url='UpdateAddList?id='+row.id;
				win.window('open');
			}else{
				showUpdate();
			}
		}
		function deleteAddlist(){
			var row = grid.datagrid('getSelected');
			if (row){
				confirmDelete('DeleteAddList?id='+row.id);
			}else{
				showDelete();
			}			
		}
		
		
		function save(){		
			if (!$('#membername').validatebox('isValid')) return;
			if (!$('#tel').validatebox('isValid')) return;
			if (!isMobile($('#tel').val()))
			{
				showWarning('请输入正确的手机号码！',function(){$('#tel').focus();});
				return;
			}
			form.form('submit',{
				url:form.url,
				success:function(data){
					eval(data);
					grid.datagrid('reload');
					win.window('close');
				}
			});
		}		
		
		function cancel(){
			win.window('close');
		}		
		function cancel2(){
			querywin.window('close');
		}		
	</script>
</head>  
<body>
	<table id="addlistgrid"></table>
	<div id="addlistwin" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
			<form method="post">
				<table border="0">
				 <tr>
				    <td align="right" ><div align="right"><sup>*</sup>姓名：</div></td>
				    <td><input type="text" name="membername" id="membername" style="width:160px;" class="easyui-validatebox" required="true" validType="length[1,8]" /></td>
				  </tr>
				  <tr>
				  	<td align="right"><div align="right"><sup>*</sup>电话：</div></td>
				    <td><input type="text" name="tel" id="tel" style="width:160px;" class="easyui-validatebox" required="true" validType="length[1,12]"/></td>
				  </tr>
				  <tr>  
				    <td align="right"><div align="right">单位部门：</div></td>
				    <td><input type="text" name="memberdept" id="memberdept" style="width:160px;" /></td>
				  </tr>
				  <tr>
				    <td align="right"><div align="right">备注：</div></td>
				    <td><textarea name="memo" id="memo" style="width:160px;height:100px;" /></textarea></td>
				  </tr>	
				    
			</table>
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">取消</a>
			</p>
		</div>
	</div>
	
	<div id="querywin" style="width:auto;height:auto;">
		<div id="popup_content" style="padding:10px 10px 10px 20px;">
			<form>
			<table>					
					 <tr>
				    <td align="right"><div align="right">姓名：</div></td>
				    <td><input type="text" name="membername1" id="membername1" style="width:180px;" /></td>
				    </tr>
				    <tr>
				    <td align="right"><div align="right">单位部门：</div></td>
				    <td><input type="text" name="memberdept1" id="memberdept1" style="width:180px;" /></td>
				  </tr>

			</table>
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-search" onClick="query();">查询</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel2();">取消</a>
			</p>
		</div>
	</div>
	
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>