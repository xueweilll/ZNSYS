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
		#searchdiv{font-size:small;font-family:inherit;}
		#charTitle{font-weight: 500;color: black; }
	</style>
	<script type="text/javascript" src="../../js/jqplot.js"></script>
	</head>
<script>
var i=0;
var hzb;
var sz;
var  h;
var z;
$(document).ready(function(){
   
    // Can specify a custom tick Array.
    // Ticks should match up one for each y value (category) in the series.
   
  //  var plot1 = zhu('chart1',[s1, s2, s3],'mytitle','季度','产量',ticks,'产量');
});
 
function toggle(){
	$("#chart1").empty();
	if(i%2==0)
		zhe('chart1',[sz],'自定义统计',h,z,hzb);
	else
		zhu('chart1' ,[sz],'自定义统计',h,z,hzb,'数量');
	i++;
	
}
function check(){
	i=0;
	$("#chart1").empty();
	var sql=$("#sql").val().toLocaleLowerCase();
	//alert(sql);
	h=$("#h").val();
	z=$("#z").val();
	//alert(sql);
	if(sql.indexOf('update')!=-1||sql.indexOf('update')!=-1||sql.indexOf('delete')!=-1||sql.indexOf('insert')!=-1||sql.indexOf('create')!=-1){
			alert("违规查询！");
	}
	else{
		
		$.get(encodeURI('jsonsql?sql='+sql),function(data){
			//alert(data);
			if(data=='error'){
				showError("查询错误");
				}
			else{var dd=data.split("@@@");
		     hzb=eval(dd[0]);
			 sz=eval(dd[1]);
			// alert(hzb);
			// alert(sz);
			 zhu('chart1',[sz],'自定义统计',h,z,hzb);}
			
		    });
	}
}
</script>
<body class="easyui-layout">
	<div region="west" title="自定义统计" border="true" style="width:200px">
		 <table id="searchdiv">
		 	<tr>
		 		<td style="font-size: medium;padding-top: 20px">
		 			<font color="red">注意：只允许输入查询语句</font> <br/><br/>
		 			例子：<br />
		 			<font  style="font-size: small;color: blue; font-weight: bold;">select 横坐标字段，纵坐标字段 from 表名称</font>
		            <font  style="font-size: small; font-weight: bold;">   <br/>（ 第一个是横坐标字段）</font>
		 		
		 		</td>
		 	</tr> 
		 	<tr>
		 		<td><br /><br />横坐标含义：<input type="text" id="h"/><br/></td>
		 	</tr>
		 	<tr>
		 		<td>纵坐标含义：<input type="text" id="z"/><br/></td>
		 	</tr>
		 	<tr>
		 		<td>请输入sql语句：<br/></td>
		 	</tr>
		 	<tr>
		 		<td><textarea name="sql" id="sql"></textarea></td>
		 	</tr>
		 	<tr>
		 		<td align="right"><a style="width: 50px" href="#" class="easyui-linkbutton" onclick="check();">查 询</a>&nbsp;&nbsp;&nbsp;</td>
		 	</tr>
		 </table>
	</div>
	<div region="center" border="false">
		<div align="right">
			<a href="#" id="change" onclick="toggle();" style="text-decoration: none;">柱状图|折线图</a>
		</div>
		<div align="center">
		<div  id="chart1" style="height:500px;width:600px"></div>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>