<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ page import="com.jfsl.pojo.*"%>
<%@ page import="java.util.List" %>
<html>
	<head>
		<title></title>
		<script type="text/javascript" src="statistic/Jquery/jquery.js"></script>
		<script type="text/javascript" src="statistic/Highcharts-4.1.5/js/highcharts.js"></script>
		<script type="text/javascript" src="statistic/Highcharts-4.1.5/js/modules/exporting.js"></script>
		<link href="statistic/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
		<script src="statistic/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
		<link href="statistic/jquery-easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
		<script src="statistic/jquery-easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<script src="statistic/js/Common.js"></script>
		<script>
			var ip='createSql?sql=';
			$(function(){	    			    		
	    		$('#container').datagrid({
	    			//fit: true,
	    			title:'工作量统计',
	                autoRowHeight: false,
	                showFooter: true,
	                rownumbers: true,
	                fitColumns: true,
	                singleSelect: true,
	                //pagination: true,
	                //pageSize: 20,
	                columns: [[
	                { field: 'jcxm', title: '检测项目', sortable: true },
	                
	                { field: 'jcr', title: '检测人', sortable: true },
	                
	                { field: 'ypsl', title: '样品数量', sortable: true}
	                ]]
	    		})
	    		Search();
	    	})
	    	function Search(){
	    		var start=$('#start').datebox('getText');
	    		var end=$('#end').datebox('getText');
	    		
	    		var sql="select jcxm,case when jcr is null then '空' else jcr end as jcr,case when sum(ypsl) is null then 0 else sum(ypsl) end as ypsl from ajhf03 where 1=1";
	    		if(start !=""){
	    			sql+=" and to_char(fxrq,'YYYY-MM-DD')>='"+start+"'";
	    		}
	    		if(end !=""){
	    			sql+=" and to_char(fxrq,'YYYY-MM-DD')<='"+end+"'";
	    		}
	    		sql+=" group by jcxm,jcr";
	    		
	    		$.ajax({
	    			async:false,
	    			dataType:'jsonp',
	    			url:encodeURI(ip+sql),
	    			jsonp: 'jsonpCallBack',
	    			success:function(data){	    
	    				
	    				var arr=new Array("jcxm","jcr","ypsl");
	    				var data2=ComJson2(data,arr);
	    				$('#container').datagrid('loadData',data2);
	    			}
	    		});
	    	}
	    	
		</script>
	</head>
	<body class="easyui-layout">
		<div class="easyui-panel" data-options="region:'north'" style="height: 30px;" border="false">
			<table>
				<tr>
					<td>分析日期</td>
					<td>
						<input id="start" type="text" class="easyui-datebox" style="width: 110px;" />&nbsp;-
						<input id="end" type="text" class="easyui-datebox" style="width: 110px;" />
					</td>
					
					<td>
						<input type="submit" value="查看" onclick="Search()" />
					</td>
				</tr>
			</table>
		</div>
		<div class="easyui-panel" data-options="region:'center'" border="false">
			<div id="container"></div>
		</div>
	</body>

</html>
<%@ include file="../../inc/loaded.inc"%>
