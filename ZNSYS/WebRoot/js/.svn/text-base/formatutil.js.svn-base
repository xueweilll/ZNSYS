/** 
* 格式化整数 
* @param number:number 要格式化的整数 
* @param fmt:string 整数格式 
*/ 
function formatNumber(number, fmt) { 
number = number + ''; 
if (fmt.length > number.length) { 
return fmt.substring(number.length) + number; 
} 
return number; 
} 

/** 
* 格式化日期为字符串表示 
* @param datetime:Date 要格式化的日期对象 
* @param format:String 日期格式 
*/ 
function formatDate(datetime, format) { 
var cfg = { 
MMM : ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'], 
MMMM : ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'] 
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
/*用正则表达式拆分日期格式各个元素*/ 
var elems = format.match(/y+|M+|d+|H+|m+|s+|S+|[^yMdHmsS]/g); 
//将日期元素替换为实际的值 
for (var i = 0; i < elems.length; i++) { 
if (cfg[elems[i]]) { 
elems[i] = cfg[elems[i]][values[elems[i].charAt(0)]]; 
} else if (!isNaN(values[elems[i].charAt(0)])) { 
	elems[i] = formatNumber(values[elems[i].charAt(0)], elems[i].replace(/./g, '0'));
} 
} 

return elems.join(''); 
}