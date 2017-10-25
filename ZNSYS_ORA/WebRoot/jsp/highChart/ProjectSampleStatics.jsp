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
		<script>
		var ip="http://10.72.21.233:8080/ZNSYS/createSql?sql=";
			$(function(){
				$('#year').combobox({
	    			textField:'bcrq',
	    			valueField:'bcrq',
	    			width:80,
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
			function Search(){
				var year=$('#year').combobox('getText') ;
				year=parseInt(year);
				var month=$('#month').combobox('getText') ;
				var month2=$('#month2').combobox('getText') ;
				//项目组名称数组
				var xmzmc=new Array();
				//sql语句数组
				var arrsql=new Array();
				var sql1="select distinct xmzmc from syzx_ah124 where (to_char(bcrq,'yyyy')='"+year+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"') ";
				sql1+=" or (to_char(bcrq,'yyyy')='"+(year-1)+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"')" ;	
				sql1+=" or (to_char(bcrq,'yyyy')='"+(year-2)+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"')" ;
				$.ajax({
					async:false,
	    			dataType:'jsonp',
	    			url:ip+sql1,
	    			jsonp: 'jsonpCallBack',
	    			success:function(data){
	    				arrsql=GetArrsql(data,year,month,month2);
	    					CreateDiv(data.length);	    				
				    		for(var i=0;i<data.length;i++){
				    			//alert(100);
		    					$('#container'+i).datagrid({
					    			title:data[i],
					                autoRowHeight: false,
					                showFooter: true,
					                rownumbers: true,
					                fitColumns: true,
					                singleSelect: true,
					                columns: [[
					                { field: 'jcxm', title: '检测项目', sortable: true,width:200 },
					                { field: 'zxm', title: '参数名称', sortable: true,width:200 },
					                { field: 'ypsl1', title: (year-2)+"年("+month+"~"+month2+")月", sortable: true,width:50 },
					                { field: 'ypsl2', title: (year-1)+"年("+month+"~"+month2+")月", sortable: true,width:50 },
					                { field: 'ypsl3', title: year+"年("+month+"~"+month2+")月", sortable: true,width:50 }
					                ]]
					    		})
				
					    		//进入下面的AJAX的时候，Success不识别变量i。。。
					    		var n=i;
		    					$.ajax({
					    			async:false,
					    			dataType:'jsonp',
					    			url:ip+myEncoder(arrsql[i]),
					    			jsonp: 'jsonpCallBack',
					    			success:function(data2){	
					    				    				
					    				var arr=new Array("jcxm","zxm","ypsl1","ypsl2","ypsl3");
					    				var data3=ComJson2(data2,arr);
					    				var total1=0;
										var total2=0;
										var total3=0;
										for(var j=0;j<data2.length;j++){
											total1+=data2[j][2];	
											total2+=data2[j][3];
											total3+=data2[j][4];
										}
										var dd="{\"total\":"+data2.length+",\"rows\":[";
										for(var i=0;i<data3.length;i++){							
											dd+="{\""+arr[0]+"\":\""+data3[i]["jcxm"]+"\","+"\""+arr[1]+"\":\""+data3[i]["zxm"]+"\","+"\""+arr[2]+"\":\""+data3[i]["ypsl1"]+"\","+"\""+arr[3]+"\":\""+data3[i]["ypsl2"]+"\","+"\""+arr[4]+"\":\""+data3[i]["ypsl3"]+"\"},";
										}											
										dd=dd.substring(0,dd.length-1);
										dd+="],\"footer\":[{\"zxm\":\"小计\",\"ypsl1\":"+total1+",\"ypsl2\":"+total2+",\"ypsl3\":"+total3+"}]}";		
										dd=eval('('+dd+')');	
					    				$('#container'+n).datagrid('loadData',dd);
					    			}
					    		});
		    				}
	    			}
				});													
			}
			function GetArrsql(data,year,month,month2){
				var arr=new Array();
				for(var i=0;i<data.length;i++){
					arr[i]=" select t1.jcxm,t1.zxm,case when t2.ypsl1 is null then 0 else t2.ypsl1 end as ypsl1, ";
					arr[i]+=" case when t3.ypsl2 is null then 0 else t3.ypsl2 end as ypsl2, case when t4.ypsl3 is null then 0 else t4.ypsl3 end as ypsl3 ";
					arr[i]+=" from (select distinct jcxm,zxm from syzx_ah124 where xmzmc='"+data[i]+"' and (to_char(bcrq,'yyyy')='"+year+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"'";
					arr[i]+=" or to_char(bcrq,'yyyy')='"+(year-1)+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"' or to_char(bcrq,'yyyy')='"+(year-2)+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"')) t1 left join ";
					arr[i]+=" (select jcxm,zxm,sum(ypsl) as ypsl1 from syzx_ah124 where xmzmc='"+data[i]+"' and to_char(bcrq,'yyyy')='"+(year-2)+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"' group by jcxm,zxm) t2 ";
					arr[i]+=" on t1.jcxm=t2.jcxm and t1.zxm=t2.zxm left join ";
					arr[i]+=" (select jcxm,zxm,sum(ypsl) as ypsl2 from syzx_ah124 where xmzmc='"+data[i]+"' and to_char(bcrq,'yyyy')='"+(year-1)+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"' group by jcxm,zxm) t3 ";
					arr[i]+=" on t1.jcxm=t3.jcxm and t1.zxm=t3.zxm left join ";
					arr[i]+=" (select jcxm,zxm,sum(ypsl) as ypsl3 from syzx_ah124 where xmzmc='"+data[i]+"' and to_char(bcrq,'yyyy')='"+year+"' and to_char(bcrq,'mm')>='"+month+"' and to_char(bcrq,'mm')<='"+month2+"' group by jcxm,zxm) t4 ";
					arr[i]+=" on t1.jcxm=t4.jcxm and t1.zxm=t4.zxm";
				}
				return arr;
			}
			function CreateDiv(length){
			   for(var i=0;i<length;i++){
				   var objdiv = document.createElement("div");

				   objdiv.id = "container"+i;
//				   objdiv.style.top = 100 * i + 100;
//				   objdiv.style.left = 100 * i + 100;
//				   objdiv.style.background = '#FFFF00';
//				   objdiv.style.visibility = 'visible';
//				   objdiv.style.width = 100;
//				   objdiv.style.height = 80;
//				   objdiv.style.border = "5 groove black";
//				   objdiv.innerHTML="aaa" + i;
				   document.getElementById("container").appendChild(objdiv);				   
				}
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
		<div class="easyui-panel" data-options="region:'center'"  border="false" id="container"> 
			
		</div>
</html>
<%@ include file="../../inc/loaded.inc"%>