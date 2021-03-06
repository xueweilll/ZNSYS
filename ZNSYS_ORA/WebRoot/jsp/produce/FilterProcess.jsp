<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>

<html>
  <head>
    <title>流程筛选</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var FlowContent;
		$(function(){
			FlowContent = $("#FlowContent").window({
				title:"项目进度",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:true,
				collapsible:false,
				resizable:true,
				width:fixWidth(0.9),
				height:fixHeight(0.9),
				onClose:function(){
					//alert("reload......");
					document.getElementById('ListProduce').contentWindow.Reflush();		//关闭时刷新子框架
				}
			});
			
			$('#grid').datagrid({
				title:'样品列表',
				url:'JsonProcess',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[
							{title:'井号',field:'DQ',width:100,align:'center'},
							{title:'地区',field:'JH',width:100,align:'center'},
							{title:'批号',field:'YPPH',width:120,align:'center'},
							{title:'检测项目',field:'JCXM',width:300,align:'center'},
							{title:'样品数量',field:'YPSL',width:100,align:'center'},
							{title:'调度送样',field:'DDSY',width:120,align:'center',
								styler: function (value, row, index) {
							    if(value=='y')
									return 'background-color:#BE684F;color:white';
					           },
					           formatter:function(value,row,index){
									return "";
								}
								},
							{title:'岗位接样',field:'JYR',width:120,align:'center',
								styler: function (value, row, index) {
									  if(value=='y')
											return 'background-color:#BF7C51;color:white';
							           },
							           formatter:function(value,row,index){
											return "";
										}
					           },
							{title:'岗位分析',field:'JCR',width:120,align:'center',
								styler: function (value, row, index) {
					        	   if(value=='y')
										return 'background-color:#BE9052;color:white';
						           }
					           ,
					           formatter:function(value,row,index){
									return "";
								}
					          },
							{title:'岗位校核',field:'JHR',width:120,align:'center',
								styler: function (value, row, index) {
					        	   if(value=='y')
										return 'background-color:#BEA553;color:white';
						           },
						           formatter:function(value,row,index){
										return "";
									}
					           },
							{title:'报告审批',field:'SPR',width:120,align:'center',
								styler: function (value, row, index) {
					        	   if(value=='y')
										return 'background-color:#BCB855;color:white';
						           },
						           formatter:function(value,row,index){
										return "";
									}
					         },
							{title:'报告接受',field:'BGJSF',width:120,align:'center',
								styler: function (value, row, index) {
					        	   if(value=='y')
										return 'background-color:#ADBC57;color:white';
						           },
						           formatter:function(value,row,index){
										return "";
									}
					         },
							{title:'数据上传',field:'SCF',width:120,align:'center',
								styler: function (value, row, index) {
					        	   if(value=='y')
										return 'background-color:#9BBB58;color:white';
						           },
						           formatter:function(value,row,index){
										return "";
									}
					        },
							{title:'完成',field:'WC',width:120,align:'center'}
						]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[{
					text:'查看',
					iconCls:'icon-search',
					handler:search
					}],
			onDblClickRow: function(rowindex,rowdata){
					var YPPH=rowdata.YPPH;
					var JCXM=rowdata.JCXM;
					FlowOpen(YPPH,JCXM);
  					FlowContent.window('open');
			},		
				onLoadError:function(){
					showLoadError();
				}
			 });
			$("#area").combobox({
				    url:'searchArea?searchArea= ',   
				    valueField:'area',   
				    textField:'text',
				    onChange: function (newValue, oldValue) {
					                if (newValue != null) {
					                    var thisKey =$.trim($("#area").combobox('getText')); //搜索词
					                    var urlStr="searchArea?searchArea="+thisKey;
					                    $("#area").combobox("reload", urlStr);
					                }
					                else
					                {  
						                var thisKey="";
					                	var urlStr="searchArea?searchArea="+thisKey;
					                	$("#area").combobox("reload", urlStr);
						             }
         						  },
         			 onSelect: function(rec){ 
         		         			 var url='JsonProcessByArea?area='+myEncoder(rec.area);  
         							 $('#grid').datagrid({url:url});
         							 $('#grid').datagrid("reload");
         					         var url='searchWellByArea?searchWell='+myEncoder(rec.area); 
         					         $('#well').combobox('reload', url); 
         					         }   
				});
			$("#well").combobox({
			    url:'searchWell?searchWell= ',   
			    valueField:'well',   
			    textField:'text',
			    onChange: function (newValue, oldValue) {
				                if (newValue != null) {
				                    var thisKey =$.trim($("#well").combobox('getText')); //搜索词
				                    var urlStr="searchWell?searchWell="+thisKey;
				                    $("#well").combobox("reload", urlStr);
				                }
				                else
				                {  
					                var thisKey="";
				                	var urlStr="searchWell?searchWell="+thisKey;
				                	$("#well").combobox("reload", urlStr);
					                }
     						  },
     			 onSelect: function(rec){   
     							 var url='JsonProcessByWell?well='+myEncoder(rec.well);  
     							 $('#grid').datagrid({url:url});
     							 $('#grid').datagrid("reload");
     							 var url='searchPhByWell?searchPh='+myEncoder(rec.well); 
     					         $('#ph').combobox('reload', url);
     					         }   
			});
			$("#ph").combobox({
			    url:'searchPh?searchPh= ',   
			    valueField:'ph',   
			    textField:'text',
			    onChange: function (newValue, oldValue) {
				                if (newValue != null) {
				                    var thisKey =$.trim($("#ph").combobox('getText')); //搜索词
				                    var urlStr="searchPh?searchPh="+thisKey;
				                    $("#ph").combobox("reload", urlStr);
				                }
				                else
				                {  
					                var thisKey="";
				                	var urlStr="searchPh?searchPh= "+thisKey;
				                	$("#ph").combobox("reload", urlStr);
					                }
     						  },
     			 onSelect: function(rec){ 
     							 var url='JsonProcessByPh?ph='+rec.ph;  
     							 $('#grid').datagrid({url:url});
     							 $('#grid').datagrid("reload");  
     							 var url='searchProByPh?searchProgram='+rec.ph; 
     					         $('#select').combobox('reload', url); 
     					         }   
			});
			$("#select").combobox({
				 url:'searchProgram?searchProgram= ',   
				    valueField:'jcxm',   
				    textField:'text',
				    onChange: function (newValue, oldValue) {
					                if (newValue != null) {
					                    var thisKey =myEncoder($.trim($("#select").combobox('getText'))); //搜索词
					                    var urlStr="searchProgram?searchProgram="+thisKey;
					                    $("#select").combobox("reload", urlStr);
					                }
					                else
					                {  
						                var thisKey="";
					                	var urlStr="searchProgram?searchProgram="+thisKey;
					                	$("#select").combobox("reload", urlStr);
						                }
         						},
								onDblClickRow: function(rowindex,rowdata){
  									var YPPH=rowdata.YPPH;
  									var JCXM=rowdata.JCXM;
  									FlowOpen(YPPH,JCXM);
  									FlowContent.window('open');
  								},		
         			 			onSelect: function(rec){ 
          							 var url='JsonProcessByJcxm?jcxm='+myEncoder(rec.jcxm);  
          							 $('#grid').datagrid({url:url});
          							 $('#grid').datagrid("reload");  
          					    }   
				});
		});
		function myEncoder(ch) {
			var val = ch != '' ? encodeURI(encodeURI(ch)) : '';
			//var path = "test.jsp?title="+title;
			return val;
		}
		function search(){
			var row =$('#grid').datagrid('getSelected');
			if(row==null){
				$.messager.alert('提示','请您先选择要查看的数据','warning');
				}else
				{
					var YPPH=row.YPPH;
					var JCXM=row.JCXM;
					FlowOpen(YPPH,JCXM);
					FlowContent.window('open');
				}
		}
		function mhcx(){
			/*var area=$("#area").combobox("getValue");
			var well=$("#well").combobox("getValue");
			var ph=$("#ph").combobox("getValue");
			var pro=$("#pro").combobox("getValue");
			var js='{"total":1,"rows":[{"YPBH":"2013-03777","YPPH":'+ph+',"DQ":'+area+',"JH":'+well+',"YSDM":'+pro+',"YPSD":"3068.50"},{"YPBH":"2013-03776","YPPH":'+ph+',"DQ":'+area+',"JH":'+well+',"YSDM":'+pro+',"YPSD":"3067.50"},]}';			
			var j=eval("("+js+")");
			$("#grid").datagrid("loadData",j);
		   */
		window.location.href="ListProduce";
		}
		
		function jqcx(){
			var a=$("#jqbh").val();
			if(a==""||a==null){
				alert("请填写具体编号！");
				return false;
			}
			else{
				confirm("确认查询？");
				window.location.href="ListProduce";
			}
		}
		
		//向子框架传递参数
		function FlowOpen(ypph,jcxm){
			document.getElementById('ListProduce').contentWindow.Loading(ypph,jcxm);
		}
		
		//计算百分比宽度
		function fixWidth(percent){
			return document.body.clientWidth * percent;
		}
		
		//计算百分比高度
		function fixHeight(percent){
			return document.body.clientHeight * percent;
		}
	</script>
</head>  
<body width="100%">
	<div id="panel" class="easyui-panel" data-options="fit:true,border:false">
		 <table style="width:100%;height:auto;padding:0px;font-size:small;font-weight:bolder" align="center"  >
			 	 <style>
			 	 	.input{
			 	 		margin-right:20px;
			 	 	}
			 	 </style>
			 	<tr>
			 		<td>
			 			项目：<input id="select" class="input"/>
			 			参数：<input id="cs" class="input"/>
			 			地区：<input id="area" class="input"/>
			 			井号：<input id="well" class="input"/>
			 			批号：<input id="ph" class="input"/>
			 		 </td>
			 	</tr>
			 	 
			 </table>
			<table id="grid"  style="width:600px;"></table>
			<table>
				<tr height="10px">
					<td>&nbsp;</td>
				</tr>
			</table>
	
	</div>
	<div id="FlowContent">
		<iframe width="100%" height="100%" src="jsp/produce/ListProduce.jsp" id="ListProduce" name="ListProduce" scrolling="no" frameborder="0"></iframe>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>