 <%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ page import="com.jfsl.pojo.*"%>
<%@ page import="java.util.List" %>
<html>
	<head>
		
		<title></title>
		<script type="text/javascript" src="statistic/Jquery/jquery.js"></script>
		<link href="statistic/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
		<script src="statistic/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
		<link href="statistic/jquery-easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
		<script src="statistic/jquery-easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<script src="statistic/js/Common.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script>
			var ip="createSql?sql=";
			var grid;
			$(function(){
				$('#year').combobox({
	    			textField:'bcrq',
	    			valueField:'bcrq',
	    			width:100,
	    			editable:false
	    		});
	    		var sql="select distinct to_char(bcrq,'yyyy') as bcrq from ah124 order by bcrq";
	    		$.ajax({
	    			async:false,
	    			dataType:'jsonp',
	    			url:encodeURI(ip+sql),
	    			jsonp: 'jsonpCallBack',
	    			success:function(data){
	    				var data2=ComJson(data,"bcrq");
	    				$('#year').combobox('loadData',data2);
	    			}
	    		});
	    		var year=GetYear();
	    		$('#year').combobox('setText',year);
	    		$('#year').combobox('setValue',year);
	    		var month=GetMonth();
	    		if(month<10){
	    			month="0"+month;
	    		}
	    		$('#month').combobox('setText',month);
	    		$('#month').combobox('setValue',month);
	    		 grid=$('#container').datagrid({
	    			//fit: true,
	    			title:'',
	                autoRowHeight: false,
	                showFooter: true,
	                rownumbers: true,
	                fitColumns: true,
	                singleSelect: true,
	                //pagination: true,
	                //pageSize: 20,
	                columns: [[
	                { field: 'ejdw', title: '单位', sortable: true,width:300 },
	                { field: 'xmzmc', title: '实验室名称', sortable: true,width:100 },
	                { field: 'ypsl2', title: '本月数量（块次）', sortable: true,width:100 },
	                { field: 'ypsl1', title: '累计数量（块次）', sortable: true,width:100 }
	                ]]
	    		})
	    		Search();
			})
			function Search(){
				var year=$('#year').combobox('getText');
				var month=$('#month').combobox('getText');
				var sql=GetSql(year,month);		
				$.ajax({
					async:false,
	    			dataType:'jsonp',
	    			url:encodeURI(ip+sql),
	    			jsonp: 'jsonpCallBack',
	    			success:function(data){
	    				
	    				var arr=new Array("ejdw","xmzmc","ypsl2","ypsl1");
	    				var data2=ComJson2(data,arr);
	    				var total1=0;
						var total2=0;
						for(var i=0;i<data.length;i++){
							
							total1+=data[i][2];
							if(data[i][3] != "无"){
								total2+=data[i][3];
							}
							
						}
						var dd="{\"total\":"+data.length+",\"rows\":[";
						for(var i=0;i<data2.length;i++){	
						
							dd+="{\""+arr[0]+"\":\""+data2[i]["ejdw"]+"\","+"\""+arr[1]+"\":\""+data2[i]["xmzmc"]+"\","+"\""+arr[2]+"\":\""+data2[i]["ypsl2"]+"\","+"\""+arr[3]+"\":\""+data2[i]["ypsl1"]+"\"},";
						}
						
						dd=dd.substring(0,dd.length-1);
						dd+="],\"footer\":[{\"xmzmc\":\"合计\",\"ypsl2\":"+total1+",\"ypsl1\":"+total2+"}]}";		
						//
						dd=eval('('+dd+')');
	    				$('#container').datagrid('loadData',dd);
	    			}
				});
			}
						
			function GetSql(year,month){
				var sql="select f1.ejdw,f1.xmzmc,case when f3.ypsl2 is null then 0 else f3.ypsl2 end  as ypls2,f1.ypsl1 from(select ejdw,xmzmc,sum(ypsl)as ypsl1 ";
				sql+="from ah124 where to_char(bcrq,'yyyy')='"+year+"' and to_char(bcrq,'mm')<='"+month+"' group by ejdw, xmzmc order by ejdw) f1 full outer join (select ejdw,xmzmc,ypsl2 from (select ejdw, ";
				sql+="xmzmc,sum(ypsl)as ypsl2 from ah124 where to_char(bcrq,'yyyy')='"+year+"' and to_char(bcrq,'mm')='"+month+"' group by ejdw, xmzmc order by ejdw) ";
				sql+="f2) f3 on f1.ejdw=f3.ejdw  and f1.xmzmc=f3.xmzmc";
				return sql;
			}
			

		</script>
	</head>
	<body class="easyui-layout">
		<div class="easyui-panel" data-options="region:'north'" style="height: 30px;" border="false">
			<table>
				<tr>
					<td>报出日期</td>
					<td><input id="year" class="easyui-combobox" /></td>
					<td>年</td>
					<td>
					<select id="month" class="easyui-combobox" style="width:50px;">
					    <option value="1">01</option>
					    <option value="2">02</option>
					    <option value="3">03</option>
					    <option value="4">04</option>
					    <option value="5">05</option>
					    <option value="6">06</option>
					    <option value="7">07</option>
					    <option value="8">08</option>
					    <option value="9">09</option>
					    <option value="10">10</option>
					    <option value="11">11</option>
					    <option value="12">12</option>
					</select>
					</td>
					<td>月</td>
					<td><input type="submit" value="查看" onclick="Search()" /></td>		
						<!--  	<input type="button" value="导出Excel" onclick="exportExcel(grid)"/>   --> 
				</tr>
			</table>
		</div>
		<div class="easyui-panel" data-options="region:'center'"  border="false"> 
			<div id="container"></div>
		</div>
		
</html>
<%@ include file="../../inc/loaded.inc"%>