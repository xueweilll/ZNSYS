function DayAdd(d,n)
{
	var t1,t2;
	t1=d.getTime();
	t2=n*1000*3600*24;
	t1+=t2;
	return (new Date(t1));
}

function StrToDate(str)
{
	var arys= new Array();
	arys=str.split('-');
	var newDate=new Date(arys[0],(arys[1]-1),arys[2]);  
	return newDate;
}

function StrToDateTime(str)
{
	var arys=str.split(' ');
	var arys1=arys[0].split('-');
	var arys2=arys[1].split(':');
	var newDate=new Date(arys1[0],(arys1[1]-1),arys1[2],arys2[0],arys2[1],arys2[2]);  
	return newDate;
}