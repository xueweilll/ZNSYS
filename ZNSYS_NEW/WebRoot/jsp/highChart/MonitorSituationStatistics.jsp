 <%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ page import="com.jfsl.pojo.*"%>
<%@ page import="java.util.List" %>
<html>

	<head>
		<meta charset="GBK">
		<title></title>
		<script type="text/javascript" src="statistic/Jquery/jquery.js"></script>
		<!--<script type="text/javascript" src="Highcharts-4.1.5/js/highcharts.js"></script>
		<script type="text/javascript" src="statistic/Highcharts-4.1.5/js/modules/exporting.js"></script>-->
		<link href="statistic/jquery-easyui/themes/icon.css" rel="stylesheet" type="text/css" />
		<script src="statistic/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
		<link href="statistic/jquery-easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
		<script src="statistic/jquery-easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<script src="statistic/Highstock-2.1.5/js/highstock.js" type="text/javascript"></script>
		<script src="statistic/js/Common.js"></script>
		<script>
		var ip='createSql?sql=';
			$(function(){								
	    		var year="select distinct substr(to_char(fxrq,'yyyy-mm-dd'),1,4)as fxrq from AJHF03 where fxrq is not null order by fxrq";
	    		$('#fxrq').combobox({
	    			width:100,
	    			editable:false,
	    			onSelect:function(record){
	    				switch(record.value){
	    					case "year":
	    					$('#year').combobox('setValue','');
	    					$('#year').combobox({
	    						disabled:true
	    					});
	    					$('#start').datebox('setText','');
	    					$('#start').datebox({
	    						disabled:true
	    					});
	    					$('#end').datebox('setText','');
	    					$('#end').datebox({
	    						disabled:true
	    					});
	    					break;
	    					case "quarter":
	    					$('#year').combobox({
	    						disabled:false
	    					});
	    					$('#start').datebox('setText','');
	    					$('#start').datebox({
	    						disabled:true
	    					});
	    					$('#end').datebox('setText','');
	    					$('#end').datebox({
	    						disabled:true
	    					});
	    					break;
	    					case "month":
	    					$('#year').combobox({
	    						disabled:false
	    					});
	    					$('#start').datebox('setText','');
	    					$('#start').datebox({
	    						disabled:true
	    					});
	    					$('#end').datebox('setText','');
	    					$('#end').datebox({
	    						disabled:true
	    					});
	    					break;
	    					case "day":
	    					$('#year').combobox('setValue','');
	    					$('#year').combobox({
	    						disabled:true
	    					});	    					
	    					$('#start').datebox({
	    						disabled:false
	    					});	    					
	    					$('#end').datebox({
	    						disabled:false
	    					});
	    					break;
	    				}
	    			}
	    		})
	    		$('#year').combobox({
	    			editable:false,
	    			textField:'fxrq',
	    			valueField:'fxrq',
	    			disabled:true
	    		})
	    		$.ajax({
	    			async:false,
	    			dataType:'jsonp',
	    			url:encodeURI(ip+year),
	    			jsonp: 'jsonpCallBack',
	    			success:function(data){
	    				var data2=ComJson(data,"fxrq");
	    				$('#year').combobox('loadData',data2);
	    			}
	    		})
	    		Search();
	    	})
			function GetSql(time1,time2){
	    		var sql="select ypph,sum(bgzypsl) as bgzypsl,sum(ypsl) as ypsl from ajhf03 where 1=1";
	    		if(time1!=""){
    				sql+=" and to_char(fxrq,'YYYY-MM-DD')>='"+time1+"'";
    			}
    			if(time2!=""){
					sql+=" and to_char(fxrq,'YYYY-MM-DD')<='"+time2+"'";
				}
    			sql+=" group by ypph order by ypph";
    			return sql;
	    	}
	    	function Search(){	    		
	    		var fxrq=$('#fxrq').combobox('getValue');
	    		var year=$('#year').combobox('getText');
	    		var time1=$('#start').datebox('getText');
				var time2=$('#end').datebox('getText');	
				var sql1;
				var xtext1;
				var ytext1="样品数量";
				var sql2;
				var ytext2="批数";
	    		switch(fxrq){
	    			case "year":	    			
		    			sql1="select fxrq,sum(ypsl) as ypsl, sum(bgzypsl) as bgzypsl from (select substr(to_char(fxrq,'yyyy-mm-dd'),0,4)as fxrq,ypsl,bgzypsl from ajhf03";
		    			sql1+=" where fxrq is not null) group by fxrq order by fxrq";
		    			sql2="select fxrq,count(ypph)as ps from(select distinct ypph, substr(to_char(fxrq,'yyyy-mm-dd'),0,4)as fxrq ";
		    			sql2+=" from ajhf03 where fxrq is not null) group by fxrq order by fxrq";
		    			xtext1="年份";		    			
	    			break;
	    			case "quarter":
		    			if(year==""){
		    				alert("请选择年份");
		    				return;
		    			}
		    			sql1="select jd,sum(ypsl)as ypsl,sum(bgzypsl)as bgzypsl from(select sum(ypsl)as ";
		    			sql1+=" ypsl,sum(bgzypsl) as bgzypsl,(case when month='01' then '一季度' when month='02' then '一季度'";
		    			sql1+=" when month='03' then '一季度' when month='04' then '二季度' when month='05' then '二季度' when month='06' then";
		    			sql1+=" '二季度' when month='07' then '三季度' when month='08' then '三季度' when month='09' then '三季度'";
		    			sql1+=" when month='10' then '四季度' when month='11' then '四季度' when month='12' then '四季度' end) as jd from(select";
		    			sql1+=" ypsl,bgzypsl,substr(to_char(fxrq,'yyyy-mm-dd'),6,2) as month from ajhf03 where fxrq is not null and";
		    			sql1+=" substr(to_char(fxrq,'yyyy-mm-dd'),0,4)='"+year+"') group by month) group by jd order by nlssort(jd,'NLS_SORT = SCHINESE_STROKE_M')";
		    			sql1=myEncoder(sql1);
		    			
		    			sql2="select jd,sum(ps) as ps from (select count(ypph) as ps,";
		    			sql2+="(case when month='01' then '一季度' when month='02' then '一季度' when month='03' then '一季度'";
		    			sql2+=" when month='04' then '二季度' when month='05' then '二季度' when month='06' then '二季度'";
		    			sql2+=" when month='07' then '三季度' when month='08' then '三季度' when month='09' then '三季度'";
		    			sql2+=" when month='10' then '四季度' when month='11' then '四季度' when month='12' then '四季度' end)as jd from";
		    			sql2+=" (select distinct ypph,substr(to_char(fxrq,'yyyy-mm-dd'),6,2)as month from ajhf03";
		    			sql2+=" where fxrq is not null and substr(to_char(fxrq,'yyyy-mm-dd'),1,4)='"+year+"') group by month) group by jd ";
		    			sql2+=" order by nlssort(jd,'NLS_SORT = SCHINESE_STROKE_M')";		    			
		    			xtext1="季度";	
		    				    			
	    			break;
	    			case "month":
		    			if(year==""){
		    				alert("请选择年份");
		    				return;
		    			}
		    			sql1="select month,sum(ypsl)as ypsl,sum(bgzypsl) as bgzypsl from(select ypsl,bgzypsl,substr(to_char(fxrq,'yyyy-mm-dd'),0,4)as fxrq,";
		    			sql1+=" substr(to_char(fxrq,'yyyy-mm-dd'),6,2) as month from ajhf03";
		    			sql1+=" where fxrq is not null and substr(to_char(fxrq,'yyyy-mm-dd'),0,4)='2014') group by month order by month";
		    			sql2="select month,count(ypph)as ps from (select distinct ypph,substr(to_char(fxrq,'yyyy-mm-dd'),0,4)as fxrq ,";
		    			sql2+="substr(to_char(fxrq,'yyyy-mm-dd'),6,2) as month from ajhf03 where fxrq is not null and substr";
		    			sql2+=" (to_char(fxrq,'yyyy-mm-dd'),0,4)='"+year+"') group by month order by month";
		    			xtext1="月份";		    					
	    			break;
	    			case "day":
	    				if(time1=="" && time2==""){
	    					alert("请选择自定义日期");
	    					return;
	    				}
		    			sql1="select to_char(fxrq,'yyyy-mm-dd') as fxrq,sum(ypsl) as ypsl,sum(bgzypsl) as bgzypsl from ajhf03"
						sql1+=" where fxrq is not null ";
						if(time1!=""){
							sql1+=" and to_char(fxrq,'yyyy-mm-dd')>='"+time1+"'";
						}
						if(time2!=""){
							sql1+=" and to_char(fxrq,'yyyy-mm-dd')<='"+time2+"'";
						}
						sql1+=" group by fxrq order by fxrq";
						sql2="select to_char(fxrq,'yyyy-mm-dd') as fxrq,count(distinct ypph) as ps from ajhf03 where fxrq is not null  ";
						if(time1!=""){
							sql2+=" and to_char(fxrq,'yyyy-mm-dd')>='"+time1+"'";
						}
						if(time2!=""){
							sql2+=" and to_char(fxrq,'yyyy-mm-dd')<='"+time2+"'";
						}
						sql2+=" group by fxrq order by fxrq ";
		    			xtext1="天";
	    			break;
	    		}
	    		$.ajax({
	    			//async:false,
	    			dataType:'jsonp',
	    			url:encodeURI(ip+sql1),
	    			jsonp: 'jsonpCallBack',
	    			success:function(data){	    				
	    				var data1=new Array();
	    				var data2=new Array();
	    				var data3=new Array();
	    				var y1=0;
	    				var y2=0;
	    				for(var i=0;i<data.length;i++){
	    					data[i][1]=data[i][1]==null?"0":data[i][1];
	    					data[i][2]=data[i][2]==null?"0":data[i][2];
	    					data1[i]=data[i][0];
	    					data2[i]=data[i][1];
	    					data3[i]=data[i][2];
	    					y1+=parseInt(data[i][2]);
	    					y2+=parseInt(data[i][1]);
	    				}
	    				$('#yp').highcharts({
							chart: {
								type: 'column'
							},
							title: {
								text: ''
							},
							//横坐标标题
							xAxis: {
								categories: data1,
								title: {
									text: xtext1
								},
								 //min: 0,
								 max:data.length>11?11:data.length-1
							},
							scrollbar: {
						        enabled: data.length>12?true:false
						    },
							yAxis: [{
								min: 0,
								title: {
									text: ytext1								
								},								
							}, {
								title: {
									text: ''
								},
								opposite: true
							}],
							legend: {
								shadow: false
							},
							tooltip: {
								shared: true
							},
							plotOptions: {
								column: {
									grouping: false,
									shadow: false,
									borderWidth: 0
								}
							},
							series: [{
								name: '样品数量',
								color: Highcharts.getOptions().colors[0],
								data: data2,
								pointPadding: 0.3,
								//pointPlacement: -0.2
							}, {
								name: '报告总样品数量',
								color: Highcharts.getOptions().colors[1],
								data: data3,
								pointPadding: 0.4,
								//pointPlacement: -0.2
							}							
							]
						});		    			
	    			}
	    		});	    		
	    	}
	    	
		</script>
	</head>
	<body class="easyui-layout">
		<div class="easyui-panel" data-options="region:'north'" style="height: 90px;" border="false">
		<!-- 	<h1 style="text-align: center;">检测情况统计</h1>  -->
			<table>
				<tr>
					<td>分析日期</td>
					<td>						
						<select id="fxrq">
							<option value="year">年度</option>
							<option value="quarter">季度</option>
							<option value="month">月份</option>
							<option value="day">天数</option>
						</select>
					</td>
					<td>
						年份
					</td>
					<td>
						<input id="year" type="text" class="easyui-combobox" style="width: 110px;" />						
					</td>
					<td>
						<input id="start" type="text" class="easyui-datebox" style="width: 110px;" data-options="disabled:true"/>&nbsp;-
						<input id="end" type="text" class="easyui-datebox" style="width: 110px;" data-options="disabled:true"	 />
					</td>										
					<td>
						<input type="submit" value="查看" onclick="Search()" />
					</td>
				</tr>				
			</table>
		</div>
		<div class="easyui-panel" data-options="region:'center'"  border="false"> 
			<div id="yp" style="min-width:400px;height:400px"></div>
		</div>
		
	</body>
</html>
<%@ include file="../../inc/loaded.inc"%>