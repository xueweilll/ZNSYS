﻿function drow(chart,data,title){
	 var plot1 = $.jqplot(chart,data, {
	        // The "seriesDefaults" option is an options object that will
	        // be applied to all series in the chart.
			//title:'zz',设置标题
			title:{text:title,
					show:true,
					},
					animate: true,
			        // Will animate plot on calls to plot1.replot({resetAxes:true})
			        animateReplot: true,
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
	        axes: {
	            // Use a category axis on the x axis and use our custom ticks.
	            xaxis: {
	                renderer: $.jqplot.CategoryAxisRenderer,
	                ticks: ticks,
	                label:'季度'
	            },
	            // Pad the y axis just a little so bars can get close to, but
	            // not touch, the grid boundaries.  1.2 is the default padding.
	            yaxis: {
	                pad: 1.05,
	                tickOptions: {formatString: '%d'},
	                label:'产量'
	            }
	        }
	    });
}