var tiplength=40;

function isMobile(obj) {
	reg = /^(\+\d{2,3}\-)?\d{11}$/;
	if (reg.test(obj)) 
		return true;
	else
		return false;
}

function isEmail(obj){    
    reg=/^\w{3,}@\w+(\.\w+)+$/;    
    if(reg.test(obj))         
    	return true;
    else    
    	return false;
}

function isUri(obj){    
    reg=/^http:\/\/[a-zA-Z0-9]+\.[a-zA-Z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;    
    if(reg.test(obj))
    	return true;
    else
    	return false;
}

function adjuestTable(grid)
{
	var h=$(window).height()-30;
	var w=$(window).width()-20;
	grid.datagrid('resize',{width:w,height:h});
	return {width:w,height:h};
}

function adjuestPanel(panel)
{
	var h=$(window).height()-30;
	var w=$(window).width()-20;
	panel.panel('resize',{width:w,height:h});	
	return {width:w,height:h};
}

//编码URL链接
function encodeParameter(str)
{
	var s='';
	for (var i=0;i<str.length;i++)
	{
		var t=str.substr(i,1);
		if ((t=='?')||(t=='&')||(t==' ')||(t=='=')||(t=='%')||(t=='+')) s=s+encodeURIComponent(t);
		else s=s+t;
	}
	return s;
}

//从grid导出Excel文件
function exportExcel(grid)
{
	var url=encodeParameter(grid.datagrid('options').url);
	var title=arguments[1]?arguments[1]:grid.datagrid('options').title;
	if ((title==null)||(title=='')) title='Excel文件';
	var columnfields=grid.datagrid('getColumnFields');
	var fields='';
	var titles='';
	for(var i=0;i<columnfields.length;i++)
	{
		var column=grid.datagrid('getColumnOption',columnfields[i]);
		fields=fields+column.field+',';
		titles=titles+column.title+',';
	}
	fields=encodeParameter(fields.substr(0,fields.length-1));
	titles=encodeParameter(titles.substr(0,titles.length-1));
	title=encodeParameter(title);
	if ($('#exportform').length<=0)
		$("body").append("<form id='exportform' method='post'></form>");
	//prompt('','ExportExcel?url='+url+'&title='+title+'&fields='+fields+'&titles='+titles);
	$('#exportform').form("submit",{
		url:'ExportExcel?url='+url+'&title='+title+'&fields='+fields+'&titles='+titles,
		success:function(data){
			eval(data);
		}
	});
	if ($('#exportform').length>0) $('#exportform').remove();
}

function showInfo(content)
{
	var func=arguments[1]?arguments[1]:null;
	var w=null,h=Math.round(content.length/tiplength)*20+180;
	ymPrompt.alert(content,w,h,'提示',func);
	//$.messager.alert('提示',content,'info',func);
}

function showSuccess(content)
{
	var func=arguments[1]?arguments[1]:null;
	var w=null,h=Math.round(content.length/tiplength)*20+180;
	ymPrompt.succeedInfo(content,w,h,'成功',func);
	//$.messager.alert('成功',content,'info',func);
}

function showWarning(content)
{
	var func=arguments[1]?arguments[1]:null;
	var w=null,h=Math.round(content.length/tiplength)*20+180;
	ymPrompt.alert(content,w,h,'警告',func);
	//$.messager.alert('警告',content,'warning',func);
}

function showError(content)
{
	var func=arguments[1]?arguments[1]:null;
	var w=null,h=Math.round(content.length/tiplength)*20+180;
	ymPrompt.errorInfo(content,w,h,'错误',func);
	//$.messager.alert('错误',content,'error',func);
}

function showConfirm(content,func)
{
	var w=null,h=Math.round(content.length/tiplength)*20+180;
	ymPrompt.confirmInfo(content,w,h,'确认',function(tp){
		if (tp=='ok') func.call(this);
	})
	/*
	$.messager.confirm('确认',content,function(data){
		if (data) func.call(this);
	})*/
}

//确认删除函数，第二参数为需要刷新的表格，默认表格名字为grid
function confirmDelete(url)
{
	var vgrid=arguments[1]?arguments[1]:grid;
	showConfirm('您确认要删除该数据吗?',function(){
		$.ajax({
			url: url,
			type: 'GET',
			timeout: 1000,
			success:function(data){
				eval(data);
				vgrid.datagrid('reload');
			}
		});
	});
}

function showLoadError()
{
	showError('加载数据发生错误！');
}

function showUpdate()
{
	showWarning('请先选择要修改的数据！');
}

function showDelete()
{
	showWarning('请先选择要删除的数据！');
}

function showBrowse()
{
	showWarning('请先选择要查看的数据！');
}

//通过表单提交请求，第二参数为需要表单名字，默认表单名字为postform
function postUrl(url)
{
	var func=arguments[1]?arguments[1]:null;
	var formname=arguments[2]?arguments[2]:'postform';
	if ($('#'+formname).length<=0)
		$("body").append("<form id='"+formname+"' method='post'></form>");
	$('#'+formname).form("submit",{
		url:url,
		success:func
	});
	if ($('#'+formname).length>0) $('#'+formname).remove();
}

//字符串转Json对象
function strToJson(str)
{   
	try
	{
		var json=eval('('+str+')');   
		return json;  
	}
	catch(error)
	{
		return {};
	}
}

//从数据库加载字典信息
function getDicts(tname,fname)
{
	var dicts=$.ajax({
	  url: 'JsonDictCaption?tablename='+tname+'&fieldname='+fname,
	  async: false
	 }).responseText;
	dicts=strToJson(dicts);
	return dicts;
}

//从本地加载后字典信息 中读取字典值
function readCaption(dicts,code)
{
	var rs='';
	for(var i=0;i<dicts.length;i++)
	{
		if(dicts[i].code==code)
		{
			rs=dicts[i].caption;
			break;
		}
	}
	return rs;
}

function downloadFile(filename)
{
	if ($('#downloadform').length<=0)
		$('body').append('<form id="downloadform" method="post"></form>');
	$('#downloadform').form("submit",{
		url:'DownloadFile?filename='+filename
	});
	if ($('#downloadform').length>0) $('#downloadform').remove();
}

//检查是否超警戒水位
function checkWarn(dis)
{
	if (typeof(dis)=='undefined') return false;
	else if (dis==null) return false;
	else if (isNaN(dis)) return false;
	else if (parseFloat(dis)<=0) return false;
	else return true;
}

//检查是否为有效数据
function checkValue(dis)
{
	if (typeof(dis)=='undefined') return false;
	else if (dis==null) return false;
	else if (isNaN(dis)) return false;
	else return true;
}

//获取数据
function getData(data)
{
	if ((typeof(data)=='undefined')||(data==null)||(isNaN(data))) return 0;
	else return parseFloat(data);
}

function format(d)
{
	return (d>9)?''+d:'0'+d;
}

//获取时间
function getTM(tm)
{
	return (tm.year+1900)+'-'+format(tm.month+1)+'-'+format(tm.date)+' '+format(tm.hours)+':'+format(tm.minutes)+':'+format(tm.seconds);
}

function getRemoteData(url)
{
	try
	{
		var data=$.ajax({url:url,async:false}).responseText;
		data=strToJson(data);
		return data;
	}
	catch(e)
	{
		return [];
	}
}