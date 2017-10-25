 <%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ page import="com.jfsl.pojo.*"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="GBK">
		<title></title>
		<script type="text/javascript" src="statistic/Jquery/jquery.js"></script>
		<link href="statistic/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
		<script src="statistic/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
		<link href="statistic/jquery-easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
		<script src="statistic/jquery-easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<script src="statistic/js/Common.js"></script>
		<script src="statistic/Highstock-2.1.5/js/highstock.js" type="text/javascript"></script>
		<script>
		var ip='http://10.72.21.233:8080/ZNSYS/createSql?sql=';
			$(function(){
				$('#year').combobox({
	    			textField:'bcrq',
	    			valueField:'bcrq',
	    			width:60,
	    			editable:false,
	    		});
	    		var sql="select distinct to_char(bcrq,'yyyy') as bcrq from syzx_ah124 order by bcrq";
	    		$.ajax({
	    			async:false,
	    			dataType:'jsonp',
	    			url:ip+sql,
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
	    		$('#month2').combobox('setText',month);
	    		$('#month2').combobox('setValue',month);    
	    		Search();		
			})
			function Search() {
				var year = $('#year').combobox('getText');
				var month = $('#month').combobox('getText');
				var month2= $('#month2').combobox('getText');
				var sql = GetSql(parseInt(year), month,month2);
				
				$.ajax({
					async: false,
					dataType: 'jsonp',
					url: ip+ sql,
					jsonp: 'jsonpCallBack',
					success: function(data) {
						var arr = new Array("jcxm", "ypsl1", "ypsl2", "ypsl3");
						var data2 = ComJson2(data, arr);
						$('#container').datagrid({
							//fit: true,
							title: '',
							autoRowHeight: false,
							showFooter: true,
							rownumbers: true,
							fitColumns: true,
							singleSelect: true,
							//pagination: true,
							//pageSize: 20,
							columns: [
								[{
									field: 'jcxm',
									title: '检测项目',
									sortable: true,
									width:200
								}, {
									field: 'ypsl1',
									title: (parseInt(year) - 2)+"年("+month+"~"+month2+")月",
									sortable: true,
									width:50
								}, {
									field: 'ypsl2',
									title: (parseInt(year) - 1)+"年("+month+"~"+month2+")月",
									sortable: true,
									width:50
								}, {
									field: 'ypsl3',
									title: year+"年("+month+"~"+month2+")月",
									sortable: true,
									width:50
								}]
							]
						})
						var total1=0;
						var total2=0;
						var total3=0;
						for(var i=0;i<data.length;i++){
							total1+=data[i][1];
							total2+=data[i][2];
							total3+=data[i][3];
						}
						var dd="{\"total\":"+data.length+",\"rows\":[";
						for(var i=0;i<data2.length;i++){							
							dd+="{\""+arr[0]+"\":\""+data2[i]["jcxm"]+"\","+"\""+arr[1]+"\":\""+data2[i]["ypsl1"]+"\","+"\""+arr[2]+"\":\""+data2[i]["ypsl2"]+"\","+"\""+arr[3]+"\":\""+data2[i]["ypsl3"]+"\"},";
						}											
						dd=dd.substring(0,dd.length-1);
						dd+="],\"footer\":[{\"jcxm\":\"合计\",\"ypsl1\":"+total1+",\"ypsl2\":"+total2+",\"ypsl3\":"+total3+"}]}";		
						dd=eval('('+dd+')');	
						$('#container').datagrid('loadData', dd);
						var arr1=new Array();
						var arr2=new Array();
						var arr3=new Array();
						var arr4=new Array();
						for(var i=0;i<data.length;i++){
							arr1[i]=data[i][0];
							arr2[i]=data[i][1];
							arr3[i]=data[i][2];
							arr4[i]=data[i][3];
						}
						$('#container2').highcharts({
							chart: {
								type: 'column'
							},
							title: {
								text: ''
							},							
							xAxis: {
								categories: arr1,
								title:{text:'检测项目'}
							},
							yAxis: {
								min: 0,
								title: {
									text: '样品数量'
								}
							},
							tooltip: {
								shared: true
							},
							plotOptions: {
								column: {
									pointPadding: 0.2,
									borderWidth: 0
								}
							},
							series: [{
								name: parseInt(year)-2,
								data: arr2
							}, {
								name: parseInt(year)-1,
								data: arr3
							}, {
								name: year,
								data: arr4
							}]
						});
					}
				});
			}
			function GetSql(year,month,month2){
				var sql="select t1.xmzmc,case when t2.ypsl1 is null then 0 else t2.ypsl1 end as ypsl1, ";
				sql+="case when t3.ypsl2 is null then 0 else t3.ypsl2 end as ypsl2 ,case when t4.ypsl3 is null then 0 else t4.ypsl3 end as ypsl3 ";
				sql+="from (select distinct xmzmc from syzx_ah124 where to_char(bcrq,'yyyy')='"+year+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"' or ";
				sql+="to_char(bcrq,'yyyy')='"+(year-1)+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"' or to_char(bcrq,'yyyy')='"+(year-2)+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"')t1 ";
				sql+="left join (select xmzmc,sum(ypsl) as ypsl1 from syzx_ah124 where to_char(bcrq,'yyyy')='"+(year-2)+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"' group by xmzmc)t2 on t1.xmzmc=t2.xmzmc ";
				sql+="left join (select xmzmc,sum(ypsl) as ypsl2 from syzx_ah124 where to_char(bcrq,'yyyy')='"+(year-1)+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"' group by xmzmc)t3 on t1.xmzmc=t3.xmzmc ";
				sql+="left join (select xmzmc,sum(ypsl) as ypsl3 from syzx_ah124 where to_char(bcrq,'yyyy')='"+year+"' and to_char(bcrq,'mm')>='"+month+"' group by xmzmc)t4 on t1.xmzmc=t4.xmzmc ";
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
										<td>
						<select id="month2" class="easyui-combobox" style="width:50px;">
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
				</tr>
			</table>
		</div>
		<div class="easyui-panel" data-options="region:'center'"  border="false"> 
			<div id="container"></div>
			<div id="container2" style="min-width:200px;height:280px"></div>
		</div>
		
</html>

<%@ include file="../../inc/loaded.inc"%>