<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="com.jfsl.pojo.*" %>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<%
User user=(User)session.getAttribute("user");
String username="";
if (user!=null)
{
	username=user.getUsername();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>流程图</title>
<!--[if lt IE 9]>
<?import namespace="v" implementation="#default#VML" ?>
<![endif]-->
<link rel="stylesheet" type="text/css" href="codebase/GooFlow.css"/>
<script type="text/javascript" src="codebase/jquery.min.js"></script>
<script type="text/javascript" src="codebase/GooFunc.js"></script>
<link rel="stylesheet" type="text/css" href="codebase/default.css"/>
<script type="text/javascript" src="codebase/GooFlow.js"></script>
<style>
*{
	margin:0px;
}
</style>
<script type="text/javascript">
var UserName = "<%=username %>";

var temp = 5,temp2 = 5;

var property={
	height:700,
	haveHead:true,														//上边栏
	headBtns:["new","open","save","undo","redo","reload"],//如果haveHead=true，则定义HEAD区的按钮
	haveTool:true,														//左边栏
	haveGroup:true,														//分组和连线编辑
	useOperStack:true													//操作undo的功能开启
};

var remark={
	cursor:"选择指针",
	direct:"转换连线",
	task:"流程步骤",
	node:"完成步骤",
	group:"组织划分框编辑开关"
};

var process;
$(document).ready(function(){

	/**初始化*/
	process=$.createGooFlow($("#process"),property);
	process.setNodeRemarks(remark);
	
	/**自定义事件编写*/
	process.onItemDel=function(id,type){
		//return confirm("确定要删除该单元吗?");
		return true;
	}
	
	/**保存按钮事件*/
	process.onBtnSaveClick=function(){
		/**获取编辑后的参数*/
		var Title = process.$title;
		var LineData = JSON.stringify(process.$lineData);
		var LineCount = JSON.stringify(process.$lineCount);
		var NodeData = JSON.stringify(process.$nodeData);
		var NodeCount  = JSON.stringify(process.$nodeCount);
		var AreaData = JSON.stringify(process.$areaData);
		var AreaCount = JSON.stringify(process.$areaCount);
		
		/*alert("title:"+Title);
		alert("linedata:"+LineData);
		alert("linecount:"+LineCount);
		alert("nodedata:"+NodeData);
		alert("nodecount:"+NodeCount);
		alert("areadata:"+AreaData);
		alert("areacount:"+AreaCount);*/
		
		/**数据转码*/
		
		
		/**数据的传递与处理*/
		$.post("InsertFlow", {
                title : encodeURI(Title),
                linedata : encodeURI(LineData),
                linecount : LineCount,
                nodedata : encodeURI(NodeData),
                nodecount : NodeCount,
                areadata : encodeURI(AreaData),
                areacount : AreaCount,
                author : encodeURI(UserName)
            }, function (data) {
                window.parent.eval(data);
                //alert("保存成功！");
        });
	}
	
	/**打开按钮事件*/
	process.onBtnOpenClick=function(){
		window.parent.list();
	}
	
	/**新增按钮事件*/
	process.onBtnNewClick=function(){
		window.parent.open();
	}
	
	/**刷新按钮事件*/
	process.onFreshClick=function(){
		process.clearData();
	}
	
	/**数据加载*/
	/*jsondata={
		title:"生产流程图",
		nodes:{
			node_1:{name:"接样",left:67,top:49,type:"node",width:86,height:24},
			node_2:{name:"测粘度",left:219,top:49,type:"task",width:86,height:24},
			node_3:{name:"敏感性测试",left:380,top:49,type:"node",width:86,height:24}
		},
		lines:{
			process_line_3:{type:"sl",from:"node_1",to:"node_2",name:"",marked:false},
			process_line_6:{type:"sl",from:"node_2",to:"node_3",name:"",marked:false}
		},
		areas:{
			process_area_8: {name:"化学分析",left:35,top:10,color:"blue",width:472,height:114}
		}
	};*/
	//process.$max=9;
	//process.loadData(jsondata);
	
	var bodywidth = document.documentElement.clientWidth;
	var bodyheight = document.documentElement.clientHeight;
	process.reinitSize(bodywidth,bodyheight);
	getMax();
});

function setTitle(title){
	process.setTitle(title);
}

function flush(){
	process.clearData();
}

function ReloadData(Title,LineData,LineCount,NodeData,NodeCount,AreaData,AreaCount){
	try{
		jsondata={
			title:Title,
			nodes:NodeData,
			lines:LineData,
			areas:AreaData
		}
		process.loadData(jsondata);
		getMax();
	}
	catch(e){
		alert("载入失败！");
	}
}

/**添加节点*/
function addNode(sjbhy,csname){
	//alert(process.$max);
	//alert(process.$nodeCount);
	if(sjbhy != ""){
		var id = "node_"+(process.$max + process.$nodeCount - 1);
		alert(csname);
		var nodeJson = {name:sjbhy,left:temp2,top:temp,type:"task",width:90,height:24,marked:false,pid:csname};
		process.addNode(id,nodeJson);
		temp = temp + 12;
		temp2 = temp2 + 1;
	}else alert("该节点无数据表含义，请修改后添加！");
}

/**遍历所有的node节点，然后计算最大的*/
function getMax(){
	var temp;		//缓存处理节点
	var temp_max;
	var max = 1;
	var  NodeContent = process.$nodeData;
	//alert(JSON.stringify(obj));
	for(o in NodeContent){
		temp = o;
		temp = temp.replace(/process_node_/,"");
		temp_max = parseInt(temp);
		if(temp_max > max){
			//alert("max = "+temp_max);
			max = temp_max;
		}
	}
	//alert(max);
	process.$max = max;
}
</script>
</head>
<body>
<div id="process"></div><br /><br /></body>
</html>