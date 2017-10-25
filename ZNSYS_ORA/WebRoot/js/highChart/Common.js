function ComJson(data,value){
	if(data.length==0){
		return [];
	}else{
		var data1="[";
		for(var i=0;i<data.length;i++){
			data1+="{\""+value+"\":\""+data[i].toString()+"\"},";
		}
		data1=data1.substring(0,data1.length-1);
		data1+="]";
		var data2=eval('(' + data1 + ')');
		return data2;
	}
}
function ComJson2(data,value){
	if(data.length==0){
		return[];
	}else{
		var data1="[";
		for(var i=0;i<data.length;i++){
			data1+="{";
			for(var j=0;j<value.length;j++){
				data[i][j]=data[i][j]==null?"нч":data[i][j];
				//alert(data[i][j]);
				data1+="\""+value[j]+"\":\""+data[i][j]+"\",";
			}
			data1+="},";
		}
		data1=data1.substring(0,data1.length-1);
		data1+="]";
		var data2=eval('(' + data1 + ')');
		return data2;
	}
}
function GetTime(date){
	var time=date.getFullYear();
	var month=date.getMonth()+1;
	var day=date.getDate();
	time+="-";
	if(month<10){
		time+="0";
	}
	time+=month+"-";
	if(day<10){
		time+="0";
	}
	time+=day;
	return time;
}