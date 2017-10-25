
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ page import="com.jfsl.pojo.*"%>
<%@ page import="java.util.List"%>
<html>
<head>
<title>用户管理</title>
<%@ include file="../../inc/easyui.inc"%>
<%-- <%@ include file="../../inc/jqplot.inc"%> --%>
<style>
#charTitle {
	font-weight: 500;
	color: black;
}
</style>
<!-- <script type="text/javascript" src="../../js/jqplot.js"></script> -->
<!-- <script src="js/jquery.min.js"></script> -->
<script src="js/echarts.js" charset="UTF-8"></script>
<script src="statistic/js/Common.js"></script>
</head>

<body class="easyui-layout">
	<div region="west" title="筛选" border="true" style="width:200px">
		<table align="center" style="line-height: 30px;padding: 50px">
			<tr>
				<td><a href="#" onclick="phtj();"><img
						style="width: 50px;height:50px" src="jsp/jqplot/0.png" /></a></td>
			</tr>
			</tr>
			<td><a href="#" onclick="phtj();"
				style="text-decoration: none;color: black;font-weight: bold;">批号统计</a></td>
			</tr>
			<tr height="80px">
				<td>&nbsp;</td>
			<tr>
				<td><a href="#" onclick="wcqktj();"><img
						style="width: 50px;height:50px" src="jsp/jqplot/0.png" /></a></td>
			</tr>
			</tr>
			<td><a href="#" onclick="wcqktj();"
				style="text-decoration: none;color: black;font-weight: bold;">完成统计</a></td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<div align="center">
			<input id="year" class="easyui-combobox" />
		</div>
		<div align="center">
			<div id="echarts" style=" height:95% ; width:95%;  color:#fff; padding-left: 0px;">
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var i = 0;
		var type ="0";
		var sz;
		var hzb;
		var ip = "createSql?sql=";
		var myChart;
		$(function(){
			myChart = echarts.init(document.getElementById('echarts'));
			var myDate = new Date();
			var value = myDate.getFullYear();  
			setData(value);
		});
		$(document).ready(function() {
			$('#year').combobox({
				textField : 'bcrq',
				valueField : 'bcrq',
				width : 80,
				editable : false,
				onSelect : function() {
					var value = $('#year').combobox('getValue');
					setData(value);
				}
			});

			var sql = "select distinct substr(ypph,1,4) as y from AJHD01 where ypph != '空' 	order by y";
			$.ajax({
				async : false,
				dataType : 'jsonp',
				url : encodeURI(ip + sql),
				jsonp : 'jsonpCallBack',
				success : function(data) {
					var data2 = ComJson(data, "bcrq");
					$('#year').combobox('loadData', data2);
				}
			});
		});
		
		function setData(value){
			$.get(encodeURI('jsonph?year='+value), 
				function(data) {
					var fg = data.split("@@@");
					hzb = eval(fg[0]);
					sz = eval(fg[1]);
					echart(hzb, sz);
					/* zhu('chart1', sz, '批号统计', '批号', '数量',	hzb, '数量'); */
			});
		}

		function echart(hzb, sz, wsz) {
			if(type == "0"){
				var option = {
			        tooltip : {
			            trigger: 'item'
			        },
			        toolbox: {
			            show : true,
			            feature : {
			                mark : {show: true},
			                dataView : {show: true, readOnly: false},
			                magicType: {show: true, type: ['line', 'bar']},
			                restore : {show: true},
			                saveAsImage : {show: true}
			            }
			        },
			        calculable : true,
			        legend: {
			            data:[ 'Growth','批号统计'],
			            itemGap: 5
			        },
			        grid: {
			            top: '12%',
			            left: '1%',
			            right: '10%',
			            containLabel: true
			        },
			        xAxis: [
			            {
			               /*  type : 'category', */
			                name : '批号',
							data : hzb
			            }
			        ],
			        yAxis: [
			            {
			                type : 'value',
			                name : '次数'
			            }
			        ],
			        dataZoom: [
			            {
			                show: true,
			                start: 94,
			                end: 100
			            },
			            {
			                type: 'inside',
			                start: 94,
			                end: 100
			            },
			            {
			                show: true,
			                yAxisIndex: 0,
			                filterMode: 'empty',
			                width: 30,
			                height: '80%',
			                showDataShadow: false,
			                left: '93%'
			            }
			        ],
			        series : [
			            {
			                name: '批号统计',
			                type: 'bar',
							data:sz
			            }
			        ]
			    };

			    myChart.setOption(option);
			}
			else if(type =="1"){
				var option = {
				        tooltip : {
				            trigger: 'item'
				        },
				        toolbox: {
				            show : true,
				            feature : {
				                mark : {show: true},
				                dataView : {show: true, readOnly: false},
				                magicType: {show: true, type: ['line', 'bar']},
				                restore : {show: true},
				                saveAsImage : {show: true}
				            }
				        },
				        calculable : true,
				        legend: {
				            data:['批号统计','完成数量'],
				            itemGap: 5
				        },
				        grid: {
				            top: '12%',
				            left: '1%',
				            right: '10%',
				            containLabel: true
				        },
				        xAxis: [
				            {
				               /*  type : 'category', */
				                name : '批号',
								data : hzb
				            }
				        ],
				        yAxis: [
				            {
				                type : 'value',
				                name : '次数'
				            }
				        ],
				        dataZoom: [
				            {
				                show: true,
				                start: 94,
				                end: 100
				            },
				            {
				                type: 'inside',
				                start: 94,
				                end: 100
				            },
				            {
				                show: true,
				                yAxisIndex: 0,
				                filterMode: 'empty',
				                width: 30,
				                height: '80%',
				                showDataShadow: false,
				                left: '93%'
				            }
				        ],
				        series : [
				            {
				                name: '批号统计',
				                type: 'bar',
								data:sz
				            },{
				                name: '完成数量',
				                type: 'bar',
								data:wsz
				            }
				        ]
				    };

				    myChart.setOption(option);
			}
		}

		function phtj() {
			type ="0";
			i = 0;
			var value = $('#year').combobox('getValue');
			if(value == ""){
				var myDate = new Date();
				value = myDate.getFullYear();  
			}
			setData(value);
		}

		function wcqktj() {
			type ="1";
			i = 0;
			$.get(encodeURI('jsonwcqk?year=2016'), function(data) {
				var fg = data.split("@@@");
				hzb = eval(fg[0]);
				var sz1 = eval(fg[1]);
				//var sz2 = eval(fg[2]);
				if(hzb.length == 0){
					var option = {};
				   myChart.setOption(option);
				}else {
					echart(hzb, sz1);
				}
				//sz = [ sz1, sz2 ];
				//zhu2('chart1', sz, '批号统计', '批号', '数量', hzb, '数量');
			});

		}
	</script>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>