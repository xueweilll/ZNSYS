<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.jfsl.pojo.*" %>
<html>
  <head>
    <title>生产数据表</title>
	<%@ include file="../../inc/easyui.inc"%>
	<script>
		var grid;
		var datawin;
		var producegrid;
		var querywin,queryform;
		var addwin,addform;
		var editwin,editform;

		//样品批号和检测项目
		var ypph = "";
		var jcxm = "";
		var csname = "";
		var scgc = "";

		//解析的数据
		//var JsonObject = eval(jsonstr);
		var JsonObject = "";			//存放回执结构Json数据
		var ContentStr = "";			//存放结构
		
		$(function(){
			datawin=$('#datadiv').window({
				title:"生产数据",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:true,
				maximizable:true,
				collapsible:false
			});
			
			//datagrid
			producegrid=$('#producedata').datagrid({
				title:' ',
				url:'',			//地址待定
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				singleSelect:true,
				rownumbers:true,
				fitColumns:false,
				onLoadError:function(){
					//showLoadError();
				}
			 });
		});
		
		function LoadFlow(){
			$.ajax({
				type: "POST",
				url: "ZGC?ypph="+ypph+"&jcxm="+jcxm+"&csname="+csname,
				dateType: "json",			
				success: function(data,status){
					//alert(data);
					var str = data.split("~");
					var zgcjson = eval(str[0]);
					//alert(str[1]);
					var flowjson = JSON.parse(str[1]);

					try{
						//区分各个节点数据
						var Title = flowjson["title"];
						var LineData = JSON.parse(flowjson["linedata"]);
						var LineCount = flowjson["linecount"];
						var NodeData = JSON.parse(flowjson["nodedata"]);
						var NodeCount = flowjson["nodecount"];
						var AreaData = JSON.parse(flowjson["areadata"]);
						var AreaCount = flowjson["areacount"];
					}catch(e){
						alert("没有此流程图！请在实验流程定义中建立。");
					}
						
					for(o in NodeData){
						if(zgcjson != null){
							for(var i=0;i<zgcjson.length;i++){
								if(zgcjson[i] == NodeData[o].name){
									//alert(NodeData[o].type);
									NodeData[o].type = "node";		//task节点转化为node节点
								}
							}
						}
					}
					document.getElementById('ListFlow').contentWindow.flush();
					document.getElementById('ListFlow').contentWindow.ReloadData(Title,LineData,LineCount,NodeData,NodeCount,AreaData,AreaCount);
				}//end of success
			});//end of ajax
			
		    
		    //初始化iframe高度
			var WindowHeight = document.body.clientHeight - 35;
			//alert(WindowHeight);
			$("#ListFlow").height(WindowHeight);
		}

		//获取流程图中的生产过程
		function getProduce(SCGC){
			//alert("点击的生产过程："+SCGC);
			scgc = SCGC;
			$.ajax({
				type: "POST",
				url: "JsonDatagridBySCGC?ypph="+ypph+"&jcxm="+jcxm+"&scgc="+scgc,
				dateType: "json",			
				success: function(data,status){ 
					//alert(data);
					var str = data.split("~");
					try{
						var Structure = JSON.parse(str[1]);
						//alert(str[1]);
						var ProduceData = JSON.parse(str[0]);
						//alert(str[0])
					}catch(e){
						alert("非标准JSON格式数据！");
						return;
					}
					try{
						//解析structure的结构
						ContentStr = "";
						for(var index in Structure){
							//alert(index);
							if(index == 0){
								ContentStr += "[[{title:'"+Structure[index].memo+"',field:'"+Structure[index].zdm+"',width:100,align:'center'}";
							}else{
								ContentStr += ",{title:'"+Structure[index].memo+"',field:'"+Structure[index].zdm+"',width:120,align:'center'}";
							}
						}
						ContentStr += "]]";
						//alert(ContentStr);
						LoadProduceData(ProduceData);
					}catch(e){
						alert("数据解析失败，请检查数据字典结构！");
					}
						
					/*for(o in NodeData){
						if(zgcjson != null){
							for(var i=0;i<zgcjson.length;i++){
								if(zgcjson[i] == NodeData[o].name){
									//alert(NodeData[o].type);
									NodeData[o].type = "node";		//task节点转化为node节点
								}
							}
						}
					}
					document.getElementById('ListFlow').contentWindow.flush();
					document.getElementById('ListFlow').contentWindow.ReloadData(Title,LineData,LineCount,NodeData,NodeCount,AreaData,AreaCount);
					*/}//end of success
			});
		}

		/*加载表格中的数据*/
		function LoadProduceData(ProduceData){
			//需要将structure数据先导入到ContentStr中
			var options = $('#producedata').datagrid("options");                   //取出当前datagrid的配置     
			//alert(JSON.stringify(options.columns));
			try{
		    	options.columns = eval(ContentStr);
			}catch(e){
				alert(e.message);
			}
		    $('#producedata').datagrid(options);
		    $('#producedata').datagrid("reload");
		    $('#body').layout('collapse','west');

		    $('#producedata').datagrid('loadData',ProduceData);
		}
		
		//载入基本参数
		function Loading(y,j){
			ypph = y;
			jcxm = j;
			$("#ListFlow").attr("src","../flow/ListFlow.html");
		}
		
		//刷新
		function Reflush(){
			window.location.reload();
		}
	</script>
<script type="text/javascript" src="../../js/layout.js"></script>
</head>  
<body class="easyui-layout" id="body">
	<div region="west" title="生产流程图" style="width:600px;padding:0px 5px 5px 0px;">
		<iframe id="ListFlow" name="ListFlow" style="width:100%;" frameborder="0" scrolling="no" src="#">
		
		</iframe>
	</div>
	<div region="center" title="节点数据">
		<table id="producedata"></table>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>
