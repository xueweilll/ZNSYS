 <%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ page import="com.jfsl.pojo.*"%>
<%@ page import="java.util.List" %>
<html>
  <head>
    <title>用户管理</title>
	<%@ include file="../../inc/easyui.inc"%>
	<%@ include file="../../inc/jqplot.inc"%>
	<style>
		#charTitle{font-weight: 500;color: black; }
	</style>
	<script type="text/javascript" src="../../js/jqplot.js"></script>
	</head>
<script>
var i=0;
var sz;
var hzb;
$(document).ready(function(){
	 
	 $.get('jsonph',function(data){
		 var fg=data.split("@@@");
		 hzb=eval(fg[0]);
		 sz=[eval(fg[1])];
		 zhu('chart1',sz,'批号统计','批号','数量',hzb,'数量');
	    });
}); 
 
function phtj(){
	i=0;
	 $.get('jsonph',function(data){
		 var fg=data.split("@@@");
		 hzb=eval(fg[0]);
		 sz=[eval(fg[1])];
		 zhu('chart1',sz,'批号统计','批号','数量',hzb,'数量');
	    });
}
function toggle(){
	$("#chart1").empty();
	if(i%2==0)
		zhe('chart1',sz,'批号统计','批号','数量',hzb);
	else
		zhu('chart1' ,sz,'批号统计','批号','数量',hzb,'产量');
	i++;
	
}
 
function wcqktj(){
	//alert("fdasf");
	i=0;
	$("#chart1").empty();
	 $.get('jsonwcqk',function(data){
		 var fg=data.split("@@@");
		 hzb=eval(fg[0]);
		 var sz1=eval(fg[1]);
		 var sz2=eval(fg[2]);
		 sz=[sz1,sz2];
		 zhu2('chart1',sz,'批号统计','批号','数量',hzb,'数量');
	    });
	 
}
</script>
<body class="easyui-layout">
	<div region="west" title="筛选" border="true" style="width:200px">
		<table align="center" style="line-height: 30px;padding: 50px">
			<tr>
				<td><a href="#" onclick="phtj();"><img style="width: 50px;height:50px" src="jsp/jqplot/0.png" /></a></td>
			</tr>
			</tr>	
				<td><a href="#" onclick="phtj();" style="text-decoration: none;color: black;font-weight: bold;">批号统计</a></td>
			</tr>
			<tr height="80px">
				<td>&nbsp;</td>
			<tr>
				<td><a href="#" onclick="wcqktj();"><img style="width: 50px;height:50px" src="jsp/jqplot/0.png" /></a></td>
			</tr>
			</tr>	
				<td><a href="#" onclick="wcqktj();" style="text-decoration: none;color: black;font-weight: bold;">完成统计</a></td>
			</tr>
		</table>
	</div>
	<div region="center" border="false">
		<div align="right">
			<a href="#" id="change" class="easyui-linkbutton" onclick="toggle();" style="text-decoration: none;">柱状图|折线图</a>
		</div>
		<div align="center">
		<div  id="chart1" style="height:500px;width:600px"></div>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>