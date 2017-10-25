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

//����URL����
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

//��grid����Excel�ļ�
function exportExcel(grid)
{
	var url=encodeParameter(grid.datagrid('options').url);
	var title=arguments[1]?arguments[1]:grid.datagrid('options').title;
	if ((title==null)||(title=='')) title='Excel�ļ�';
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
	ymPrompt.alert(content,w,h,'��ʾ',func);
	//$.messager.alert('��ʾ',content,'info',func);
}

function showSuccess(content)
{
	var func=arguments[1]?arguments[1]:null;
	var w=null,h=Math.round(content.length/tiplength)*20+180;
	ymPrompt.succeedInfo(content,w,h,'�ɹ�',func);
	//$.messager.alert('�ɹ�',content,'info',func);
}

function showWarning(content)
{
	var func=arguments[1]?arguments[1]:null;
	var w=null,h=Math.round(content.length/tiplength)*20+180;
	ymPrompt.alert(content,w,h,'����',func);
	//$.messager.alert('����',content,'warning',func);
}

function showError(content)
{
	var func=arguments[1]?arguments[1]:null;
	var w=null,h=Math.round(content.length/tiplength)*20+180;
	ymPrompt.errorInfo(content,w,h,'����',func);
	//$.messager.alert('����',content,'error',func);
}

function showConfirm(content,func)
{
	var w=null,h=Math.round(content.length/tiplength)*20+180;
	ymPrompt.confirmInfo(content,w,h,'ȷ��',function(tp){
		if (tp=='ok') func.call(this);
	})
	/*
	$.messager.confirm('ȷ��',content,function(data){
		if (data) func.call(this);
	})*/
}

//ȷ��ɾ���������ڶ�����Ϊ��Ҫˢ�µı��Ĭ�ϱ������Ϊgrid
function confirmDelete(url)
{
	var vgrid=arguments[1]?arguments[1]:grid;
	showConfirm('��ȷ��Ҫɾ����������?',function(){
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
	showError('�������ݷ�������');
}

function showUpdate()
{
	showWarning('����ѡ��Ҫ�޸ĵ����ݣ�');
}

function showDelete()
{
	showWarning('����ѡ��Ҫɾ�������ݣ�');
}

function showBrowse()
{
	showWarning('����ѡ��Ҫ�鿴�����ݣ�');
}

//ͨ�����ύ���󣬵ڶ�����Ϊ��Ҫ�����֣�Ĭ�ϱ�����Ϊpostform
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

//�ַ���תJson����
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

//�����ݿ�����ֵ���Ϣ
function getDicts(tname,fname)
{
	var dicts=$.ajax({
	  url: 'JsonDictCaption?tablename='+tname+'&fieldname='+fname,
	  async: false
	 }).responseText;
	dicts=strToJson(dicts);
	return dicts;
}

//�ӱ��ؼ��غ��ֵ���Ϣ �ж�ȡ�ֵ�ֵ
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

//����Ƿ񳬾���ˮλ
function checkWarn(dis)
{
	if (typeof(dis)=='undefined') return false;
	else if (dis==null) return false;
	else if (isNaN(dis)) return false;
	else if (parseFloat(dis)<=0) return false;
	else return true;
}

//����Ƿ�Ϊ��Ч����
function checkValue(dis)
{
	if (typeof(dis)=='undefined') return false;
	else if (dis==null) return false;
	else if (isNaN(dis)) return false;
	else return true;
}

//��ȡ����
function getData(data)
{
	if ((typeof(data)=='undefined')||(data==null)||(isNaN(data))) return 0;
	else return parseFloat(data);
}

function format(d)
{
	return (d>9)?''+d:'0'+d;
}

//��ȡʱ��
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