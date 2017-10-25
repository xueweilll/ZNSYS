function clearChart(chart)
{
	$('#'+chart).empty();
}

function initChart(chart,dateformat,date1,date2)
{
	var lines=[[date1,-1]];

	clearChart(chart);
	$.jqplot(chart, [lines], {
		stackSeries: false,
	    axes: {
	        xaxis: {
	            renderer: $.jqplot.DateAxisRenderer,
	            min:date1,
	            max:date2,
				tickOptions:{
					showGridline:true,
					formatString : dateformat
				},
	            numberTicks: 10
	        },
	        yaxis: {  
		        max:10,
		        min:0,
	            tickOptions:{
	        		showGridline:true,
	            	formatString : '%.2f'
		        } 
	        }
	    }
	});
}

function getStations(stationid)
{
	var stations;
	if ($('#'+stationid).combobox('getValue')=='') 
		stations=$('#stationid').combobox('getData');
	else 
		stations=[{stcd:'',stnm:''},{stcd:$('#'+stationid).combobox('getValue'),stnm:$('#'+stationid).combobox('getText')}];
	
	return stations;
}

function adjustY(y1,y2)
{
	return {'y1':Math.floor(y1*10)/10-0.02,'y2':Math.floor(y2*10)/10+0.1};
}

//得到画线需要的数据格式
function getLineData(sdata,gdata)
{
	var lines=[],series=[],yaxis={'y1':0,'y2':10};
	if (gdata.length>0)
	{
		y1=gdata[0].data;
		y2=gdata[0].data;
		for(var i=1;i<sdata.length;i++)
		{
			var linedata=[];
			for(var j=0;j<gdata.length;j++)
			{
				if (gdata[j].stcd==sdata[i].stcd)
				{
					var pointdata=[];
					pointdata.push(gdata[j].idtm);
					pointdata.push(gdata[j].data);
	
					linedata.push(pointdata);
					
					if (gdata[j].data<y1) y1=gdata[j].data;
					if (gdata[j].data>y2) y2=gdata[j].data;
				}
			}
			if (linedata.length>0) 
			{
				lines.push(linedata);
				series.push({label:sdata[i].stnm,lineWidth:1});
				yaxis=adjustY(y1,y2);
			}
		}
	}
	return {lines:lines,series:series,yaxis:yaxis};
}

//画曲线
function drawLine(chart,dateformat,date1,date2,y1,y2,lines,series)
{
	clearChart(chart);
	$.jqplot(chart, lines, {
	    axes: {
	        xaxis: {
	            renderer: $.jqplot.DateAxisRenderer,
	            min:date1,
	            max:date2,
				tickOptions:{
					showGridline:true,
					formatString:dateformat 
				},
	            numberTicks:10
	        },
	        yaxis: {  
		        min:y1,
		        max:y2,
	            tickOptions:{
	        		showGridline:true,
	            	formatString:'%.2f'
		        } 
	        }
	    },
	    series:series,
	    legend:{
			show:true,
			location:'ne'
		},
		highlighter: {
			show: true,
			sizeAdjust:7.5
		},
		cursor: {
			show: false
		}
	});
}

//得到画直方图需要的数据格式
function getBarData(sdata,gdata,staticstype,period)
{
	var bars=[],dates=[],series=[];
	if(staticstype==1)
	{
		for(var i=0;i<10;i++) dates.push('0'+i+':00');
		for(var i=10;i<24;i++) dates.push(i+':00');
	}
	else if(staticstype==4){
		if(period==0){
			for(var i=1;i<10;i++) dates.push('0'+i);
			for(var i=10;i<=31;i++) dates.push(i);
		}
		else if(period==1){
			for(var i=1;i<10;i++) dates.push('0'+i);
			dates.push('10');
		}
		else
			for(var i=(period-1)*10+1;i<=(period<3?period*10:period*10+1);i++) dates.push(i);
	}
	else if(staticstype==5){
		for(var i=1;i<10;i++) dates.push('0'+i);
		for(var i=10;i<=31;i++) dates.push(i);
	}
	else {
		for(var i=1;i<10;i++) dates.push('0'+i);
		for(var i=11;i<=12;i++) dates.push(i);
	}
	for(var i=1;i<sdata.length;i++)
	{
		var bardata=[];
		for(var j=0;j<dates.length;j++) bardata.push('');
		for(var j=0;j<gdata.length;j++)
		{
			if (gdata[j].stcd==sdata[i].stcd)
			{
				for (var l=0;l<dates.length;l++)
				{
					var tm;
					if (staticstype==1) tm=gdata[j].distm;
					else if(staticstype==4) tm=gdata[j].distm.substr(8,2);
					else if(staticstype==5) tm=gdata[j].distm.substr(8,2);
					else tm=gdata[j].distm.substr(5,2);
					if(tm==dates[l])
					{
						bardata[l]=gdata[j].data;
						/*
						if (getData(gdata[j].data)>0) bardata[l]=gdata[j].data;
						else bardata[l]='';
						*/
					}
				}
			}
		}
		bars.push(bardata);
		series.push({label:sdata[i].stnm,lineWidth:2});
	}
	return {bars:bars,dates:dates,series:series};
}

//画直方图
function drawBar(chart,bars,dates,series)
{
	clearChart(chart);
	$.jqplot(chart, bars, {
		seriesDefaults: {
			renderer:$.jqplot.BarRenderer,
			rendererOptions:{barWidth:10},
			pointLabels:{
				show:true,
				location:'n',
				edgeTolerance:-15
			}            
		},            
	    axes: {
	        xaxis: {
				renderer: $.jqplot.CategoryAxisRenderer,
				tickOptions:{
					showGridline:true
				},
				ticks: dates 
	        },
	        yaxis: {  
		        min:0,
	            tickOptions:{
	        		showGridline:true,
	            	formatString:'%.2f'
		        } 
	        }
	    },
	    series:series,
	    legend:{
			show:true,
			location:'ne'
		}
	});
}

function drawChart(chart,stationid,rows,bdate,edate,dateformat,charttype,staticstype,period)
{
	var stations=getStations(stationid);
	if (rows.length==0)
		initChart(chart,dateformat,bdate,edate);
	else
	{
		var chartdata;
		if (charttype==1)
		{
			chartdata=getLineData(stations,rows);
			if (chartdata.lines.length==0) initChart(chart,dateformat,bdate,edate);
			else drawLine(chart,dateformat,bdate,edate,chartdata.yaxis.y1,chartdata.yaxis.y2,chartdata.lines,chartdata.series);
		}
		else
		{
			chartdata=getBarData(stations,rows,staticstype,period);
			if (chartdata.bars.length==0) initChart(chart,dateformat,bdate,edate);
			else drawBar(chart,chartdata.bars,chartdata.dates,chartdata.series);
		}	
	}
}

function getWaterLineData(rows,sttp,bsnm)
{
	var lines=[],series=[],yaxis={'y1':0,'y2':10};
	if (sttp=='RR')
	{
		if (rows.length>0)
		{
			var y1=rows[0].rz,y2=rows[0].rz;
			var linedata=[],pointdata;
			for(var j=0;j<rows.length;j++)
			{
				pointdata=[];
				pointdata.push(getTM(rows[j].tm));
				pointdata.push(rows[j].rz);
		
				linedata.push(pointdata);
				
				if ((rows[j].rz!=null)&&(rows[j].rz<y1)) y1=rows[j].rz;
				if ((rows[j].rz!=null)&&(rows[j].rz>y2)) y2=rows[j].rz;
			}
			if (linedata.length>0) 
			{
				lines.push(linedata);
				series.push({label:'库上水位',lineWidth:2,showMarker:false});
				yaxis=adjustY(y1,y2);
			}
		}
	}
	if (sttp=='ZZ')
	{
		if(rows.length>0)
		{
			var linedata=[],pointdata;
			var y1=rows[0].z,y2=rows[0].z;
			for(var j=0;j<rows.length;j++)
			{
				pointdata=[];
				pointdata.push(getTM(rows[j].tm));
				pointdata.push(rows[j].z);
		
				linedata.push(pointdata);
				
				if ((rows[j].z!=null)&&(rows[j].z<y1)) y1=rows[j].z;
				if ((rows[j].z!=null)&&(rows[j].z>y2)) y2=rows[j].z;
			}
			if (linedata.length>0) 
			{
				lines.push(linedata);
				series.push({label:'河道水位',lineWidth:2,showMarker:false});
				yaxis=adjustY(y1,y2);
			}
		}
	}
	if (sttp=='DD')
	{
		if (rows.length>0)
		{
			var linedata1=[],linedata2=[],pointdata;
			var y1=rows[0].upz,y2=rows[0].upz;
			for(var j=0;j<rows.length;j++)
			{
				pointdata=[];
				pointdata.push(getTM(rows[j].tm));
				pointdata.push(rows[j].upz);
				linedata1.push(pointdata);
				pointdata=[];
				pointdata.push(getTM(rows[j].tm));
				pointdata.push(rows[j].dwz);
				linedata2.push(pointdata);
				
				if ((rows[j].upz!=null)&&(rows[j].upz<y1)) y1=rows[j].upz;
				if ((rows[j].upz!=null)&&(rows[j].upz>y2)) y2=rows[j].upz;
				
				if ((rows[j].dwz!=null)&&(rows[j].dwz<y1)) y1=rows[j].dwz;
				if ((rows[j].dwz!=null)&&(rows[j].dwz>y2)) y2=rows[j].dwz;
			}
			if (linedata1.length>0) 
			{
				lines.push(linedata1);
				if (bsnm.indexOf('长江')>=0)
					series.push({label:'长江侧水位',lineWidth:2,showMarker:false});
				else if (bsnm.indexOf('太湖')>=0)
					series.push({label:'内河侧水位',lineWidth:2,showMarker:false});
				else
					series.push({label:'闸上水位',lineWidth:2,showMarker:false});
				yaxis=adjustY(y1,y2);
			}
			if (linedata2.length>0) 
			{
				lines.push(linedata2);
				if (bsnm.indexOf('长江')>=0)
					series.push({label:'内河侧水位',lineWidth:2,showMarker:false});
				else if (bsnm.indexOf('太湖')>=0)
					series.push({label:'太湖侧水位',lineWidth:2,showMarker:false});
				else
					series.push({label:'闸下水位',lineWidth:2,showMarker:false});
				yaxis=adjustY(y1,y2);
			}
		}
	}
	if (sttp=='DP')
	{
		if (rows.length>0)
		{
			var linedata1=[],linedata2=[],pointdata;
			var y1=rows[0].ppupz,y2=rows[0].ppupz;
			for(var j=0;j<rows.length;j++)
			{
				pointdata=[];
				pointdata.push(getTM(rows[j].tm));
				pointdata.push(rows[j].ppupz);
				linedata1.push(pointdata);
				pointdata=[];
				pointdata.push(getTM(rows[j].tm));
				pointdata.push(rows[j].ppdwz);
				linedata2.push(pointdata);
				
				if ((rows[j].ppupz!=null)&&(rows[j].ppupz<y1)) y1=rows[j].ppupz;
				if ((rows[j].ppupz!=null)&&(rows[j].ppupz>y2)) y2=rows[j].ppupz;
				
				if ((rows[j].ppdwz!=null)&&(rows[j].ppdwz<y1)) y1=rows[j].ppdwz;
				if ((rows[j].ppdwz!=null)&&(rows[j].ppdwz>y2)) y2=rows[j].ppdwz;
			}
			if (linedata1.length>0) 
			{
				lines.push(linedata1);
				if (bsnm.indexOf('长江')>=0)
					series.push({label:'长江侧水位',lineWidth:2,showMarker:false});
				else if (bsnm.indexOf('太湖')>=0)
					series.push({label:'内河侧水位',lineWidth:2,showMarker:false});
				else
					series.push({label:'闸上水位',lineWidth:2,showMarker:false});
				yaxis=adjustY(y1,y2);
			}
			if (linedata2.length>0) 
			{
				lines.push(linedata2);
				if (bsnm.indexOf('长江')>=0)
					series.push({label:'内河侧水位',lineWidth:2,showMarker:false});
				else if (bsnm.indexOf('太湖')>=0)
					series.push({label:'太湖侧水位',lineWidth:2,showMarker:false});
				else
					series.push({label:'闸下水位',lineWidth:2,showMarker:false});
				yaxis=adjustY(y1,y2);
			}
		}
	}
	
	return {lines:lines,series:series,yaxis:yaxis};
}

function getRainBarData(rows)
{
	var bars=[],dates=[],bardata=[],series=[];
	if (rows.length>0)
	{
		var d=rows[rows.length-1].tm;
		var t=new Date();
		t.setFullYear(d.year+1900);
		t.setMonth(d.month);
		t.setDate(d.date);
		t.setHours(d.hours);
		t.setMinutes(d.minutes);
		var l=0;
		for (var i=1;i<=24;i++)
		{
			var d=DayAdd(t,i/24-1);
			dates.push(d.getHours()+':'+format(d.getMinutes()));
			var b=false;
			for (var j=l;j<rows.length;j++)
			{	
				var tm=rows[j].tm;
				if (formatDate(d,'yyyy-M-d H:m')==((tm.year+1900)+'-'+(tm.month+1)+'-'+tm.date+' '+tm.hours+':'+tm.minutes))
				{
					b=true;
					l=j;
					break;
				}
			}
			if (b) bardata.push(rows[j].drp);
			else bardata.push('');
		}
	}
	if (bardata.length>0) 
	{
		bars.push(bardata);
		series.push({label:'降雨量',lineWidth:1,showMarker:false});
	}
	
	return {bars:bars,dates:dates,series:series};
}

//获取水势
function getWPTN(wptn)
{
	if((typeof(wptn)=='undefined')||(wptn==null)||($.trim(wptn)=='')) return '';
	else if(wptn=='4') return '↑';
	else if(wptn=='5') return '↓';
	else return '―';
}