<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>

<html>
  <head>
    <title>数据字典管理</title>
	<%@ include file="../../inc/easyui.inc"%>
	 
	<script>
		var arraydata=new Array();
		var num=0;
	    var strid=0;
		var grid;
		var querywin,queryform,editwin;
		var win,winform;
		var detaildiv;
		var DN=0;
		//var data={"total":6,"rows":[{"DValue":"NO NULL","DType":"文本","DMemo":"id","DName":"MessageID","DTypeLength":null},{"DValue":"NO NULL","DType":"文本","DMemo":"标题","DName":"Title","DTypeLength":null},{"DValue":"NO NULL","DType":"文本","DMemo":"发布者","DName":"Author","DTypeLength":null},{"DValue":"NO NULL","DType":"文本","DMemo":"消息正文","DName":"Content","DTypeLength":null},{"DValue":"NO NULL","DType":"文本","DMemo":"发布日期","DName":"PublishTime","DTypeLength":null},{"DValue":"NO NULL","DType":"文本","DMemo":"截止日期","DName":"DateLine","DTypeLength":null}]};
		$(function(){
			$('#detailgrid').datagrid
			({
				title:'字段信息',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[				    						
				    {title:'字段名',field:'SJBMC',width:80,align:'center'},
				    {title:'含义',field:'PYDM',width:100,align:'center'},
				    {title:'类型',field:'XH',width:80,align:'center'},
				    {title:'长度',field:'SJXMC',width:80,align:'center'},
				    {title:'字段名',field:'LX',width:80,align:'center'},
				    {title:'含义',field:'KD',width:100,align:'center'},
				    {title:'类型',field:'XSWS',width:80,align:'center'},
				    {title:'长度',field:'JLDW',width:80,align:'center'},
				    {title:'字段名',field:'PK',width:80,align:'center'},
				    {title:'含义',field:'AK',width:100,align:'center'},
				    {title:'类型',field:'CZB',width:80,align:'center'},
				    {title:'长度',field:'FKZ',width:80,align:'center'},
				    {title:'字段名',field:'ZYS',width:80,align:'center'},
				    {title:'含义',field:'TXGD',width:100,align:'center'},
				    {title:'字段名',field:'SYBHY',width:80,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				onLoadError:function(){
					showLoadError();
				}
			 });
			
			querywin=$('#queryform').window({
				title:"查询条件",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});

			editwin=$('#editdiv').window({
				title:"编辑",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false,
				width:700,
				height:300
			});

			detaildiv=$('#detaildiv').window({
				title:"具体信息",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			
			queryform=querywin.find('form');//查询窗口

			win=$('#win').window({
				title:"新增流程",				
				closed:true,
				modal:true,
				closable:true,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				resizable:false
			});
			winform=win.find('form');//新增窗口

			
			grid=$('#grid').datagrid
			({
				title:'流程信息',
				url:'JsonDatadictionary',
				fit:true,
				nowrap: false,
				rownumbers:true,
				striped: true,
				columns:[[				    						
				    {title:'表名',field:'sjbmc',width:100,align:'center'},
				    {title:'具体字段',field:'jtck',align:'center',
						formatter:function(value,row,index){
						return "<a onClick=look('"+value+"') href='#' >点击查看</a>";
					}},
				    {title:'中文名',field:'sjbhy',width:100,align:'center'}
				]],
				singleSelect:true,
				rownumbers:true,
				pagination:true,
				fitColumns:true,
				toolbar:[
						{text:'新增',iconCls:'icon-add',handler:add},'-',
						{text:'删除',iconCls:'icon-remove',handler:remove},'-',
						{text:'编辑',iconCls:'icon-edit',handler:edit},'-',
						{text:'刷新',iconCls:'icon-reload',handler:reload}
						],
				//显示加载错误
				onLoadError:function(){
					showLoadError();
				}
			 });
			 //调整自动适应表格
			adjuestTable(grid);
			$(window).resize(function(){
				adjuestTable(grid);
				
			});
		});
		
		function add(){
			 window.location.href="jsp/datadictionary/AddDictionary.jsp";
			}
		
		function remove(){
			var row = grid.datagrid('getSelected');
			if(row==null){
				$.messager.alert('提示','请您先选择要删除的信息','warning');
				}else{
					confirmDelete('DeleteDatadictionary?sjbmc='+row.sjbmc);
					}
			}

		
		function find()
		{
			queryform.form('clear');
			querywin.window('open');
		}

		function reload(){
			grid.datagrid('reload');
		}
		
		function query()
		{
			var processid=$('#processid').validatabox('getValue');
			var processname=$('#processname').validatabox('getValue');
			grid.datagrid({url:'JsonOperateLog?processid='+processid+'&processname='+processname});
			grid.datagrid('reload');
			querywin.window('close');
		}
		
		function save(){
			win.window('close');
			}
		
		function cancel1()
		{
			win.window('close');
		}
		
		function cancel2()
		{
			querywin.window('close');
		}
	 function look(tablename){
		 $.getJSON("JsonDatadictionaryBysjbmc?sjbmc="+tablename, function(json){
			 $('#detailgrid').datagrid('loadData',json); 
			 detaildiv.window("open");
			});
		 
		 
	 }

	 function edit1(){
		 DN=0;
			var row = grid.datagrid('getSelected');
			if(row==null){
				$.messager.alert('警告','请您先选择要编辑的信息！','warning');
				}else{
					$("#id").val(row.id);
					var ja=row.structure;
					var length=ja.length;
				    var str="<tr><td>表名</td><td><input type='text' id='tableName' name='tableName' value='"+row.sjbmc+"'/></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
				    str+="<tr><td>中文名</td><td><input type='text' id='memo' name='memo' value='"+row.sjbhy+"'/></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
				    str+="<tr><td>字段名</td><td>含义</td><td>类型</td><td>长度</td><td>能否为空</td></tr>";
				    for(var i=0;i<length;i++){
						str+="<tr><td><input id='DN"+DN+"' type='text' value='"+ja[i].DName+"'/></td><td><input id='DM"+DN+"' type='text' value='"+ja[i].DMemo+"'/></td><td><select id='DT"+DN+"' type='text' value='"+ja[i].DType+"'/><option value='文本'>文本</option><option value='整数'>整数</option><option value='浮点数'>浮点数</option></select></td><td><input type='text' id='DTL"+DN+"' value='"+ja[i].DTypeLength+"'/></td><td><select type='text' id='DV"+DN+"' value='"+ja[i].DValue+"'><option>NOT NULL</option><option>NULL</option></select></td></tr>";
						DN++;
					}
					str+="<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td><a href='#' id='editbtn' onClick='e();'>更改</a></td><td><a href='#' id='stopbtn' onClick='c();'>取消</a></td></tr>";
				    $("#edittable").html(str);
				    $('#editbtn').linkbutton({   
				        iconCls: 'icon-save'  
				    });
				    $('#stopbtn').linkbutton({   
				        iconCls: 'icon-cancel'  
				    });  
					editwin.window('open');
				}
		}
 
		 function edit(){
			 DN=0;
			var row = grid.datagrid('getSelected');
			if(row==null){
				$.messager.alert('警告','请您先选择要编辑的信息！','warning');
				}else{
					 $.getJSON("JsonDatadictionaryBysjbmc?sjbmc="+row.sjbmc, function(json){
						  var j=json.rows;
						//  alert(row.sjbmc);
						//alert(row.sjbhy);
						  var str="<tr><td>表名</td><td colspan=6><input type='text' id='tableName' name='tableName' value='"+row.sjbmc+"'/></td> </tr>";
						    str+="<tr><td>中文名</td><td colspan=6><input type='text' id='memo' name='memo' value='"+row.sjbhy+"'/></td> </tr>";
						    str+="<tr><td width='100px'>字段名</td><td width='100px'>含义</td><td>类型</td><td>长度</td><td>能否为空</td><td>主键</td><td>外键</td></tr>";
						//	alert(j.length);
							    for(var i=0;i<j.length;i++){
								    if(j[i].PK=="否"){
								    str+="<tr><td><input type='text' id='pydm"+DN+"' value='"+j[i].PYDM+"' /></td><td><input type='text' id='pydm"+DN+"' value='"+j[i].SJXMC+"' /></td><td><input type='text' id='pydm"+DN+"' value='"+j[i].LX+" </td><td><input type='text' id='pydm"+DN+"' value='"+j[i].KD+"' /></td><td><input type='text' id='pydm"+DN+"' value='"+j[i].SJBMC+"' /></td><td><select id='cc'  name='pk' style='width:200px;'><option value='yes'>是</option><option value='no' selected='true'>否</option></select></td><td>"+j[i].SJBMC+"</td></tr>";}
else
	{
	  str+="<tr><td><input type='text' id='pydm"+DN+"' value='"+j[i].PYDM+"' ' /></td><td><input type='text' id='pydm"+DN+"' value='"+j[i].SJXMC+"' /></td><td><input type='text' id='pydm"+DN+"' value='"+j[i].LX+"' /></td><td><input type='text' id='pydm"+DN+"' value='"+j[i].KD+"' /></td><td><input type='text' id='pydm"+DN+"' value='"+j[i].SJBMC+"' /></td><td><select id='cc'  name='pk' style='width:200px;'><option value='yes' selected='true'>是</option><option value='no' >否</option></select></td><td>"+j[i].SJBMC+"</td></tr>";}
						    }
						    str+="<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td colspan=2><a href='#' id='editbtn' onClick='e();'>更改</a></td><td  colspan=2><a href='#' id='stopbtn' onClick='c();'>取消</a></td></tr>";
						    $("#edittable").html(str);
						    $('#editbtn').linkbutton({   
						        iconCls: 'icon-save'  
						    });
						    $('#stopbtn').linkbutton({   
						        iconCls: 'icon-cancel'  
						    });  
						    				    
							editwin.window('open');
	
							});
				  
					
				}
		}
		////
		function e(){
			var tableName=$("#tableName").val();
			var memo=$('#memo').val();
			var structureObj=[];
			for(var nu=0;nu<DN;nu++){
				var dn=$("#DN"+nu).val();
				var dm=$("#DM"+nu).val();
				var dt=$("#DT"+nu).val();
				var dtl=parseInt($("#DTL"+nu).val());
				var dv=$("#DV"+nu).val();
				//alert(dn+dm+dt+dtl+dv);
				//var jo="{\"DName\":\""+dn+"\",\"DMemo\":\""+dm+"\",\"DType\":\""+dt+"\",\"DTypeLength\":\""+dtl+"\",\"DValue\":\""+dv+"\"}";
				var jsonStr="{}";
				var jsonObj = JSON.parse(jsonStr);
				jsonObj["DName"]=dn;
				jsonObj["DMemo"]=dm;
				jsonObj["DType"]=dt;
				jsonObj["DTypeLength"]=dtl;
				jsonObj["DValue"]=dv;
				//var jsonstr=JSON.stringify(jsonObj);
				structureObj.push(jsonObj);
			}
			var structure=JSON.stringify(structureObj);
			var dicid=$("#id").val();
			$.post("UpdateTable", {
				Id: dicid,
                TableName: tableName,
                Structure: structure,
                Author: "sa",
                Memo: memo
            }, function (data) {
                eval(data);
                $("#editdiv").window('close');
                grid.datagrid("reload");
            });
		}
		function c(){
			editwin.window('close');
		}			
	</script>
</head>  
<body>
	<table id="grid"></table>
	<div id="win" style="width:auto;height:auto;" align="center">
		<div style="padding:10px 10px 10px 20px;">
			<form method="post">
		<table>
				<tr>
					<td align="right"><div align="right"><sup>*</sup>流程号：</div></td>
					<td><input id="processid" style="width:200px;" class="easyui-validatabox" required="true"></td>
				</tr>					
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>流程名称：</div></td>
					<td><input id="processname" style="width:200px;" class="easyui-validatabox" required="true"></td>
				</tr>			
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="save();">保存</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel1();">取消</a>
			</p>
			</form>
		</div>
	</div>
	<div id="queryform" style="width:auto;height:auto;" align="center">
		<div  id="popup_content" style="padding:10px 10px 10px 20px;">
		<form method="post">
		<table>
				<tr>
					<td align="right"><div align="right"><sup>*</sup>流程号：</div></td>
					<td><select id="processid" style="width:200px;"></select></td>
				</tr>					
				<tr>
					<td  align="right"><div align="right"><sup>*</sup>流程名称：</div></td>
					<td><select id="processname" style="width:200px;"></select></td>
				</tr>
			</table>
			<p align="center">
				<a href="#" class="easyui-linkbutton" icon="icon-save" onClick="query();">查询</a>
				<a href="#" class="easyui-linkbutton" icon="icon-cancel" onClick="cancel2();">取消</a>
			</p>
			</form>
		</div>
	</div>
	<div id="detaildiv" style="width:1000px;height:400px">
		<table id="detailgrid"></table>	
	</div>
	<div id="editdiv" style="width:800px;height:auto;padding:20 30 40 30;font-weight:bolder;font-size:medium;line-height:30px">
		<div  id="popup_content" style="padding:10px 10px 10px 20px;">
			<form method="post" id="editform">
				<input type="hidden" name="id" id="id"/>
				<table id="edittable" style="font-weight:bolder;font-size:medium;line-height:30px">
					 
				</table>
			</form>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>