<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
  <head>
    <title>远程监控</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script type="text/javascript">
		var grid;
		var m_bDVRControl; 
		$(function(){
			grid=$('#grid').datagrid({
				title:'摄像头',
				url:'JsonVideo',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[
				    {title:'ID',field:'id',align:'left',hidden:'true'},
				    {title:'摄像头编号',field:'code',width:100,align:'center'},
				    {title:'摄像头名称',field:'caption',width:100,align:'center'},
				    {title:'视频URL地址',field:'url',width:120,align:'center'}
				    
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				onClickRow:function(rowIndex,rowData){
					// alert(rowData.url);
					 show(rowData.url);}
			 });
			 
		});
		
		function show(url){
		 	var szIP=url,szPort='80',szUsername='admin',szPassword='12345';
			if (""== szIP||null==szIP) {
		    	return;
		    }

		    var iRet = WebVideoCtrl.I_Login(szIP,1,szPort,szUsername,szPassword,{
		    	success:function(xmlDoc){
		        	setTimeout(function () {
		        		getChannelInfo();		//获取通道
		        	}, 10);
		        	clickStartRealPlay(url);	//开始预览
		        },
		        error:function () {
		        	alert(szIP + " 摄像头无法连接，请检查设备！");
		        }
		    });
			if (-1 == iRet) {
		        clickStartRealPlay(url);	//开始预览
		    	//alert(szIP + " 已登录过！");
		    }          
		}
	</script>
</head>  
<body class="easyui-layout">
	<div region="west" style="width:500px" >
		 <table id="grid"></table>
	</div>
	<div region="center"  >
		<div id="divPlugin" style="width:100%;height:100%"></div>
	</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/video/webVideoCtrl.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/video/video.js"></script>
</html>
<%@ include file="../../inc/loaded.inc"%>