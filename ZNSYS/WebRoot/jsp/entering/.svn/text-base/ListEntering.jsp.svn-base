<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%--<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validateuser.inc"%>
--%>
<!doctype html>
<html>
    <head>
        
        <title>jsPlumb 1.6.2 demo - yui</title>
        <meta http-equiv="content-type" content="text/html;charset=utf-8" />
        <link rel="stylesheet" href="jsPlumb/jsplumb.css">     
        <link rel="stylesheet" href="jsPlumb/demo.css">
		<link rel="stylesheet" href="jquery-easyui-1.3.2/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.2/themes/icon.css">
		<script type="text/javascript" src="jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="jquery-easyui-1.3.2/jquery.easyui.min.js"></script>
		<script>
			function adddiv(){
				$("#end").before("<div class='window' id='"+$("#addname").val()+"'><strong>"+$("#addname").val()+"</strong><br/><br/></div>");
			}
		</script>
    </head>
    <body data-demo-id="flowchart" data-library="yui">
        
        <div id="main">
            <!-- demo -->
			<input type="text" style="border-radius:5px;box-shadow:1px 1px 1px rgba(0,0,0,0.3);" id="addname">&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" onclick="adddiv();"/>添加一个生产过程</a>
            <div class="demo flowchart-demo" id="flowchart">
                <div class="window" id="flowchartWindow1"><strong>调度送样</strong><br/><br/></div>
                <div class="window" id="flowchartWindow2"><strong>岗位接样</strong><br/><br/></div>
                <div class="window" id="flowchartWindow3"><strong>岗位分析</strong><br/><br/></div>
                <div class="window" id="flowchartWindow4"><strong>报告审批</strong><br/><br/></div>
				<div id="end" style="display:none"></div>
            </div>
            <!-- /demo -->
        </div>
        <script src="jsPlumb/yui-min.js"></script>
                
        <!-- JS -->
        <!-- support lib for bezier stuff -->
        <script src="jsPlumb/jsBezier-0.6.js"></script>     
        <!-- geom functions -->   
        <script src="jsPlumb/biltong-0.2.js"></script>
        <!-- jsplumb util -->
        <script src="jsPlumb/util.js"></script>
        <!-- base DOM adapter -->
        <script src="jsPlumb/dom-adapter.js"></script>        
        <!-- main jsplumb engine -->
        <script src="jsPlumb/jsPlumb.js"></script>
        <!-- endpoint -->
        <script src="jsPlumb/endpoint.js"></script>                
        <!-- connection -->
        <script src="jsPlumb/connection.js"></script>
        <!-- anchors -->
        <script src="jsPlumb/anchors.js"></script>        
        <!-- connectors, endpoint and overlays  -->
        <script src="jsPlumb/defaults.js"></script>
        <!-- bezier connectors -->
        <script src="jsPlumb/connectors-bezier.js"></script>
        <!-- state machine connectors -->
        <script src="jsPlumb/connectors-statemachine.js"></script>
        <!-- flowchart connectors -->
        <script src="jsPlumb/connectors-flowchart.js"></script>
        <script src="jsPlumb/connector-editors.js"></script>
        <!-- SVG renderer -->
        <script src="jsPlumb/renderers-svg.js"></script>
        
        
        <!-- vml renderer -->
        <script src="jsPlumb/renderers-vml.js"></script>
        
        <!-- yui jsPlumb adapter -->
        <script src="jsPlumb/yui.jsPlumb.js"></script>
        <!-- /JS -->
        
        <!--  demo code -->
        <script src="jsPlumb/demo.js"></script>    
        
    </body>
</html>
<%@ include file="../../inc/loaded.inc"%>