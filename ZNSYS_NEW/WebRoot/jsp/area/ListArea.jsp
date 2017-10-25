<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>

<html>
<head>
	<title>地区管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<%@ include file="../../inc/easyui.inc"%>
	<script type="text/javascript">
	var depid;
	var win;
	var form;
	var tree;
	var mynode;
	var flag;
	var belongins=[{id:'1',name:'省级'},{id:'2',name:'市级'},{id:'3',name:'县/区级'}];
	$(function(){
		$('#belongin').combobox({
			editable:false,
			data:belongins,	
			valueField: 'id',
			textField: 'name',
			panelHeight:'auto'
		});
		
		win=$('#dep').window({
			title:"地区管理",
			
			closed:true,
			modal:true,
			closable:true,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			resizable:false
		});
		form=win.find('form');
		
		tree=$('#tree1').tree({
			checkbox: false,
			animate:true,
			url: 'JsonDepartment',
			onClick:function(node){
				depid=node.id;
			},
			onLoadError:function(){
				showLoadError();
			}
		});

		$(document).bind('contextmenu',function(e){
			$('#popmenu1').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
			return false;
		});
	});

	function addDepartment(type)
	{
		if (depid==null)
		{
			$('#popmenu1').menu('hide');
			showWarning('请先选择一个地区！');
		}
		else
		{
			mynode=$('#tree1').tree('getSelected');
			var pid;
			flag=type;
			if(type==1)
			{
				var pnode=$('#tree1').tree('getParent',mynode.target);
				if(pnode==null) pid="";
				else pid=pnode.id;
				
			}
			else if(type==2)
				pid=mynode.id;

			form.form('clear');
			
			form.form('load','AddDepartment?parentid='+pid);
			
			$('#channelids').val("");
			win.window('open');
			form.url='InsertDepartment';
		}
		
	}
	
	function editDepartment()
	{
		flag=3;
		if (depid==null)
		{
			$('#popmenu1').menu('hide');
			showUpdate();
		}
		else
		{
			mynode=$('#tree1').tree('getSelected');
			
			form.form('clear');
			
			$('#id').val(mynode.id);
			$('#name').val(mynode.text);
			$('#showorder').val(mynode.attributes.showorder);
			$('#belongin').combobox('setValue',mynode.attributes.belongin);
			win.window('open');
			form.url='UpdateDepartment';
		}
	}
	
	function deleteDepartment()
	{
		if (depid==null)
		{
			$('#popmenu1').menu('hide');
			showDelete();
		}
		else
			showConfirm('您确认要删除该地区吗?',function(){
				$('#popmenu1').menu('hide');
				$.ajax({
					url: 'DeleteDepartment?id='+depid,
					type: 'GET',
					timeout: 1000,
					success:function(data){
						eval(data);
						mynode=$('#tree1').tree('getSelected');
						tree.tree('remove',mynode.target);
					}
				});
			});
	}
	
	function reload()
	{
		depid=null;
		$("#tree1").tree('reload');
	}

	
	function save()
	{
		if ($.trim($('#name').val()).length==0)
		{
			showWarning('请输入地区名称！');
			$('#name').focus();
			return;
		}
		if ($('#belongin').combobox('getValue')=='')
		{
			showWarning('请选择地区性质！');
			return;
		}
		if ($('#showorder').val().length==0)
		{
			showWarning('请先输入该地区的显示顺序号！');
			$('#showorder').focus();
			return;
		}
		
		form.form('submit',{
			url:form.url,
			success:function(data){
				eval(data);
				win.window('close');
				reload();	
			}
		});
	}
	
	function cancel(){
		win.window('close');
	}
	
	</script>
</head>  
<body>
	<div class="easyui-panel" fit="true" title="地区管理">
	<ul id="tree1" ></ul>
	</div>
	<div id="popmenu1" class="easyui-menu" style="width:120px;">
	
	<div onclick="javascript:addDepartment(1);"> 添加同级 </div>
	<div onclick="javascript:addDepartment(2);"> 添加下级</div>
	<div onclick="javascript:editDepartment(); "> 修改 </div>
	<div onclick="javascript:deleteDepartment();"> 删除 </div>
	<div class="menu-sep"></div>
	<div onclick="javascript:reload();"> 刷新 </div>
	</div>
	
	<div id="dep" style="width:360px;height:220px;">
		<div id="popup_content" style="padding:10px 10px 10px 40px;">
			<form id="form1" method="post">
			    <input id="id" name="id" type="hidden"/>	
				<table>	
					<tr>
					<td><div align="right"><sup>*</sup>地区名称：</div></td>
					<td><input type="text" id="name" name="name" style="width:180px;"/></td>					
					</tr>	
					<tr>
					<td><div align="right"><sup>*</sup>地区性质：</div></td>
					<td><input type="text" id="belongin" name="belongin" style="width:180px;"/></td>					
					</tr>
					<tr>
					<td><div align="right"><sup>*</sup>显示顺序：</div></td>
					<td><input type="text" class="easyui-numberbox" id="showorder" name="showorder" style="width:180px"/></td>					
					</tr>
				</table>
				<input type="hidden" id="channelids" name="channelids"/>
			</form>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel();">取消</a>
			</p>
		</div>
	</div>
	
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>