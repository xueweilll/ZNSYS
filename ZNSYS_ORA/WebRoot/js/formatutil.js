/** 
* ��ʽ������ 
* @param number:number Ҫ��ʽ�������� 
* @param fmt:string ������ʽ 
*/ 
function formatNumber(number, fmt) { 
number = number + ''; 
if (fmt.length > number.length) { 
return fmt.substring(number.length) + number; 
} 
return number; 
} 

/** 
* ��ʽ������Ϊ�ַ�����ʾ 
* @param datetime:Date Ҫ��ʽ�������ڶ��� 
* @param format:String ���ڸ�ʽ 
*/ 
function formatDate(datetime, format) { 
var cfg = { 
MMM : ['һ', '��', '��', '��', '��', '��', '��', '��', '��', 'ʮ', 'ʮһ', 'ʮ��'], 
MMMM : ['һ', '��', '��', '��', '��', '��', '��', '��', '��', 'ʮ', 'ʮһ', 'ʮ��'] 
}, 
values = { 
y : datetime.getFullYear(), 
M : datetime.getMonth()+1, 
d : datetime.getDate(), 
H : datetime.getHours(), 
m : datetime.getMinutes(), 
s : datetime.getSeconds(), 
S : datetime.getMilliseconds() 
}; 
/*��������ʽ������ڸ�ʽ����Ԫ��*/ 
var elems = format.match(/y+|M+|d+|H+|m+|s+|S+|[^yMdHmsS]/g); 
//������Ԫ���滻Ϊʵ�ʵ�ֵ 
for (var i = 0; i < elems.length; i++) { 
if (cfg[elems[i]]) { 
elems[i] = cfg[elems[i]][values[elems[i].charAt(0)]]; 
} else if (!isNaN(values[elems[i].charAt(0)])) { 
	elems[i] = formatNumber(values[elems[i].charAt(0)], elems[i].replace(/./g, '0'));
} 
} 

return elems.join(''); 
}