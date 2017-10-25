//折线图
function zhe(chart,data,title,xtitle,ytitle,ticks){
	$.jqplot(chart,data, {
	        // The "seriesDefaults" option is an options object that will
	        // be applied to all series in the chart.
			//title:'zz',设置标题
			title:{text:title,
					show:true
					},
					animate: true,
			        // Will animate plot on calls to plot1.replot({resetAxes:true})
			        animateReplot: true,
	      
	        // Custom labels for the series are specified with the "label"
	        // option on the series option.  Here a series option object
	        // is specified for each series.
	      
	        // Show the legend and put it outside the grid, but inside the
	        // plot container, shrinking the grid to accomodate the legend.
	        // A value of "outside" would not shrink the grid and allow
	        // the legend to overflow the container.
	        legend: {
	            show: true,
	            placement: 'outsideGrid'
	        },
	        axes: {
	            // Use a category axis on the x axis and use our custom ticks.
	            xaxis: {
	                renderer:$.jqplot.CategoryAxisRenderer,
	                ticks: ticks,//横轴显示
	                numberTicks:3,
	                label:xtitle
	            },
	            // Pad the y axis just a little so bars can get close to, but
	            // not touch, the grid boundaries.  1.2 is the default padding.
	            yaxis: {
	                pad: 1.05,
	                tickOptions: {formatString: '%d'},
	                label:ytitle
	            }
	        }
	    });
}
//柱状图
function zhu(chart,data,title,xtitle,ytitle,ticks,hightitle){
	  
	   $.jqplot(chart, data, {
	        // The "seriesDefaults" option is an options object that will
	        // be applied to all series in the chart.
			//title:'zz',设置标题
			title:{text:title,
					show:true
					},
					animate: true,
			        // Will animate plot on calls to plot1.replot({resetAxes:true})
			        animateReplot: true,
	        seriesDefaults:{  //设置坐标轴
				show: true, //是否自动显示坐标轴。 
				min: 2, //横(纵)轴最小刻度值 
				max: 2, //横(纵)轴最大刻度值 
	            renderer:$.jqplot.BarRenderer,
	            rendererOptions: {fillToZero: true},
	      		pointLabels: {   // 显示数据点，依赖于jqplot.pointLabels.min.js文件  
	            show: true ,
	            shadow: true
	        }
	        },
	        // Custom labels for the series are specified with the "label"
	        // option on the series option.  Here a series option object
	        // is specified for each series.
	       
	        // Show the legend and put it outside the grid, but inside the
	        // plot container, shrinking the grid to accomodate the legend.
	        // A value of "outside" would not shrink the grid and allow
	        // the legend to overflow the container.
	        legend: {
	            show: true,
	            placement: 'outsideGrid'
	        },
	        highlighter:{  
               show:true,  
               tooltipAxes:'y',  
               useAxesFormatters:false,  
               tooltipFormatString:hightitle+'：%d'  
           } , 
           axesDefaults:{
        	   min:0
           },
	        axes: {
	            // Use a category axis on the x axis and use our custom ticks.
	            xaxis: {
	                renderer: $.jqplot.CategoryAxisRenderer,
	                ticks: ticks,
	                numberTicks:0.5,
	                label:xtitle
	            },
	            // Pad the y axis just a little so bars can get close to, but
	            // not touch, the grid boundaries.  1.2 is the default padding.
	            yaxis: {
	                pad: 1.05,
	                tickOptions: {formatString: '%d'},
	                label:ytitle
	            }
	        }
	    });
}
function zhu2(chart,data,title,xtitle,ytitle,ticks,hightitle){
	  
	   $.jqplot(chart, data, {
	        // The "seriesDefaults" option is an options object that will
	        // be applied to all series in the chart.
			//title:'zz',设置标题
			title:{text:title,
					show:true
					},
					animate: true,
			        // Will animate plot on calls to plot1.replot({resetAxes:true})
			        animateReplot: true,
	        seriesDefaults:{  //设置坐标轴
				show: true, //是否自动显示坐标轴。 
				min: 2, //横(纵)轴最小刻度值 
				max: 2, //横(纵)轴最大刻度值 
	            renderer:$.jqplot.BarRenderer,
	            rendererOptions: {fillToZero: true},
	      		pointLabels: {   // 显示数据点，依赖于jqplot.pointLabels.min.js文件  
	            show: true ,
	            shadow: true
	        }
	        },
	        // Custom labels for the series are specified with the "label"
	        // option on the series option.  Here a series option object
	        // is specified for each series.
	       
	        // Show the legend and put it outside the grid, but inside the
	        // plot container, shrinking the grid to accomodate the legend.
	        // A value of "outside" would not shrink the grid and allow
	        // the legend to overflow the container.
	        legend: {
	            show: true,
	            placement: 'outsideGrid'
	        },
	        series:[
	                {
	                label:'批数总量'
	                },{
	                	label:'已完成'
	                }
	         
	         ],
	        highlighter:{  
            show:true,  
            tooltipAxes:'y',  
            useAxesFormatters:false,  
            tooltipFormatString:hightitle+'：%d'  
        } , 
        axesDefaults:{
     	   min:0
        },
	        axes: {
	            // Use a category axis on the x axis and use our custom ticks.
	            xaxis: {
	                renderer: $.jqplot.CategoryAxisRenderer,
	                ticks: ticks,
	                pad: 2,
	                numberTicks:0.5,
	                label:xtitle
	            },
	            // Pad the y axis just a little so bars can get close to, but
	            // not touch, the grid boundaries.  1.2 is the default padding.
	            yaxis: {
	                pad: 1.05,
	                tickOptions: {formatString: '%d'},
	                label:ytitle
	            }
	        }
	    });
}