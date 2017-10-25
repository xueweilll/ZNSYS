<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
	<title>授权管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		$(function(){
			$('#tree').tree({
				checkbox:true,
				url:'JsonGrantedFunction?roleid=<s:property value="#request.roleid"/>'
			});
		})
		function save()
		{
			var ids = [];
			var nodes = $('#tree').tree('getChecked');
			for(var i=0;i<nodes.length;i++)
			{
				if ($('#tree').tree('isLeaf',nodes[i].target))
				{
					ids.push(nodes[i].id);
				}
			}
			$('#function').attr('action','GrantFunction?roleid=<s:property value="#request.roleid"/>&functionids='+ids);
			$('#function').submit();
		}
		function cancel()
		{
			window.parent.ymPrompt.close();
		}
	</script>
</head>  
<body>
	<div style="border:1px solid black;width:270;height:230">
		<ul id="tree"></ul>
	</div>
	<form id="function" method="post">
		<a href="#" class="easyui-linkbutton" icon="icon-save" onclick="save();">保存</a>
		<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="cancel();">取消</a>
	</form>
</body>
</html>
