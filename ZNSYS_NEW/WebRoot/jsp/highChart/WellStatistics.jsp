 <%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ page import="com.jfsl.pojo.*"%>
<%@ page import="java.util.List" %>
<html>

	<head>
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
	ip='createSql?sql=';
			$(function(){	    		
	    		$('#dq').combobox({
	    			textField:'dq',
	    			valueField:'dq',
	    			width:200,
	    			editable:false
	    		});	
	    		$.ajax({
	    			async:false,
	    			dataType:'jsonp',
	    			url:encodeURI(ip+"select distinct dq from ajhf03 where dq is not null"),
	    			jsonp: 'jsonpCallBack',
	    			success:function(data){
	    				if(data.length>0){
		    				var data2=ComJson(data,"dq");
		    				$('#dq').combobox('loadData',data2);
	    					$('#dq').combobox('select',data[0]);
	    					Search();	    					
	    				}
	    			}
	    		});
	    		$('#start').datebox({
	    			onSelect:function(date){	
	    				$('#dq').combobox('setValue','');
	    				var time1=GetTime(date);
	    				var time2=$('#end').datebox('getText');
	    				var ypph="select distinct dq from ajhf03 where dq is not null";
	    				if(time1!=""){
	    					ypph+=" and to_char(fxrq,'yyyy-mm-dd')>='"+time1+"'";
	    				}
	    				if(time2!=""){
	    					ypph+=" and to_char(fxrq,'yyyy-mm-dd')<='"+time2+"'";
	    				}
	    				$.ajax({
			    			async:false,
			    			dataType:'jsonp',
			    			url:encodeURI(ip+ypph),
			    			jsonp: 'jsonpCallBack',
			    			success:function(data){
			    				var data2=ComJson(data,"dq");
			    				$('#dq').combobox('loadData',data2);
			    			}
			    		});	
	    			}
	    		});
	    		$('#end').datebox({
	    			onSelect:function(date){
	    				$('#dq').combobox('setValue','');
	    				var time2=GetTime(date);
	    				var time1=$('#start').datebox('getText');
	    				var ypph="select distinct dq from ajhf03 where dq is not null";
	    				if(time1!=""){
	    					ypph+=" and to_char(fxrq,'yyyy-mm-dd')>='"+time1+"'";
	    				}
	    				if(time2!=""){
	    					ypph+=" and to_char(fxrq,'yyyy-mm-dd')<='"+time2+"'";
	    				}
	    				$.ajax({
			    			async:false,
			    			dataType:'jsonp',
			    			url:encodeURI(ip+ypph),
			    			jsonp: 'jsonpCallBack',
			    			success:function(data){
			    				var data2=ComJson(data,"dq");
			    				$('#dq').combobox('loadData',data2);
			    			}
			    		});	 				
	    			}
	    		});		    			    		
	    	})
			function GetSql1(dq,time1,time2){
	    		var sql="select jh,sum(ypsl) as ypsl,sum(bgzypsl) as bgzypsl from ajhf03 where dq='"+dq+"'";	    		
    			sql+=" group by jh";
    			
    			return sql;
	    	}
//			function GetSql2(dq,time1,time2){
//				var sql="select jh,count(distinct ypph)as ps from ajhf03 where dq='"+dq+"' and dq is not null ";				
//  			sql+=" group by jh";
//  			return sql;
//			}
	    	function Search(){
	    		var dq=$('#dq').combobox('getText');
	    		if(dq==""){
	    			alert("请选择地区");
	    			return;
	    		}
	    		var time1=$('#start').datebox('getText');
				var time2=$('#end').datebox('getText');				
	    		var sql1=GetSql1(dq,time1,time2);	    		
	    		$.ajax({
	    			async:false,
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
	    					data1[i]=data[i][0];
	    					data2[i]=data[i][1];
	    					data3[i]=data[i][2];
	    					y1+=parseInt(data[i][1]);
	    					y2+=parseInt(data[i][2]);
	    				}
	    				$('#container').highcharts({
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
									text: '井号'									
								},							
								 max:data.length>5?5:data.length-1
							},
							scrollbar: {
						        enabled: data.length>6?true:false
						    },										
							yAxis: [{
								min: 0,
								title: {
									text: '样品数量'									
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
							}]
						});
	    			}
	    		});
	    		
	    	}
	    	
		</script>
	</head>
	<body class="easyui-layout">
		<div class="easyui-panel" data-options="region:'north'" style="height: 90px;" border="false">
		
			<table>
				<tr>
					<td>分析日期</td>
					<td>
						<input id="start" type="text" class="easyui-datebox" style="width: 110px;" />&nbsp;-
						<input id="end" type="text" class="easyui-datebox" style="width: 110px;" />
					</td>
					<td>地区</td>
					<td>
						<input class="easyui-combobox" id="dq" />
					</td>
					<td>
						<input type="submit" value="查看" onclick="Search()" />
					</td>
				</tr>
			</table>
		</div>
		<div class="easyui-panel" data-options="region:'center'"  border="false"> 
			<div id="container" style="min-width:100px;height:400px"></div>
		</div>
		<div class="easyui-panel" data-options="region:'east'"  border="false" style="width: 50%;" > 
			<p></p>
		</div>
	</body>
</html>
<%@ include file="../../inc/loaded.inc"%>