 <%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
 
<%@ page import="com.jfsl.pojo.*"%>
<%@ page import="java.util.List" %>
<html>
  <head>
    <title>用户管理</title>
	<%@ include file="../../inc/easyui.inc"%>
	<%@ include file="../../inc/jqplot.inc"%>
	<style>
		#charTitle{font-weight: 500;color: black; }
	</style>
	</head>
<script>
var i=0;
$(document).ready(function(){
 
    var s1 = [200, 600, 700, 1000];
    var s2 = [460, 500, 690, 820];
    var s3 = [300, 400, 600, 700];
    // Can specify a custom tick Array.
    // Ticks should match up one for each y value (category) in the series.
    var ticks = ['第一季度', '第二季度', '第三季度', '第四季度'];
    
    var plot1 = $.jqplot('chart1', [s1, s2, s3], {
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
		//title:'zz',设置标题
		title:{text:'<span id="charTitle">季度产量</span>',
				show:true,
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
            show: true 
        }, 
        },
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
       series:[        // 具体数据序列属性  
                        {  
                            color:'#FF6666',  
                            label:'一区'  
                        },{  
                            color:'#0066CC',  
                            label:'二区'  
                        },{  
                            color:'#99CC66',  
                            label:'三区'  
                        }  
                    ],  
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
                tickOptions: {formatString: '$%d'},
                label:'<font style="color:blue">产量</font>'
            }
        }
    });
    $('#chart1').on('jqplotDataClick', function(ev, gridpos, datapos, neighbor, plot){  
    	 alert('d');
        // 获取横坐标的下标
        var tickIndex = neighbor[0]; 
        alert('横坐标：'+tickIndex+"第"+tickIndex+"季度");
        //获取纵坐标的值  
        var date = neighbor[1];  
       alert('产值：'+date);
       var xvalue = chart.axes.xaxis.ticks[tickIndex][1]; 
       alert(xvalue);
    }); 
    
});
function zhu(){
	 var s1 = [200, 600, 700, 1000];
	    var s2 = [460, 500, 690, 820];
	    var s3 = [300, 400, 600, 700];
	    // Can specify a custom tick Array.
	    // Ticks should match up one for each y value (category) in the series.
	    var ticks = ['第一季度', '第二季度', '第三季度', '第四季度'];
	    
	    var plot1 = $.jqplot('chart1', [s1, s2, s3], {
	        // The "seriesDefaults" option is an options object that will
	        // be applied to all series in the chart.
			//title:'zz',设置标题
			title:{text:'<span id="charTitle">季度产量</span>',
					show:true,
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
	            shadow: true,
	        }, 
	        },
	        // Custom labels for the series are specified with the "label"
	        // option on the series option.  Here a series option object
	        // is specified for each series.
	       series:[        // 具体数据序列属性  
	                        {  
	                            color:'#FF6666',  
	                            label:'一区'  
	                        },{  
	                            color:'#0066CC',  
	                            label:'二区'  
	                        },{  
	                            color:'#99CC66',  
	                            label:'三区'  
	                        }  
	                    ],  
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
                tooltipFormatString:'产量：%d'  
            } , 
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
	                tickOptions: {formatString: '$%d'},
	                label:'产量'
	            }
	        }
	    });
}
function zhe(){
	var s1 = [200, 600, 700, 1000];
    var s2 = [460, 500, 690, 820];
    var s3 = [300, 400, 600, 700];
    // Can specify a custom tick Array.
    // Ticks should match up one for each y value (category) in the series.
    var ticks = ['第一季度', '第二季度', '第三季度', '第四季度'];
    
    var plot1 = $.jqplot('chart1', [s1, s2, s3], {
        // The "seriesDefaults" option is an options object that will
        // be applied to all series in the chart.
		//title:'zz',设置标题
		title:{text:'<span id="charTitle">季度产量</span>',
				show:true,
				},
				animate: true,
		        // Will animate plot on calls to plot1.replot({resetAxes:true})
		        animateReplot: true,
        seriesDefaults:{  //设置坐标轴
			show: true, //是否自动显示坐标轴。 
			min: 2, //横(纵)轴最小刻度值 
			max: 2, //横(纵)轴最大刻度值 
            rendererOptions: {fillToZero: true},
      		pointLabels: {   // 显示数据点，依赖于jqplot.pointLabels.min.js文件  
            show: true 
        }, 
        },
        // Custom labels for the series are specified with the "label"
        // option on the series option.  Here a series option object
        // is specified for each series.
       series:[        // 具体数据序列属性  
                        {  
                            color:'#FF6666',  
                            label:'一区'  
                        },{  
                            color:'#0066CC',  
                            label:'二区'  
                        },{  
                            color:'#99CC66',  
                            label:'三区'  
                        }  
                    ],  
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
                tickOptions: {formatString: '$%d'},
                label:'产量'
            }
        }
    });
}
function toggle(){
	$("#chart1").empty();
	if(i%2==0)
		zhe();
	else
		zhu();
	i++;
	
}

</script>
<body class="easyui-layout">
	<div region="west" title="筛选" border="true" style="width:200px">
		
	</div>
	<div region="center" border="false">
		<div align="right">
			<a href="#" id="change" onclick="toggle();" style="text-decoration: none;">柱状图|折线图</a>
		</div>
		<div align="center">
		<div  id="chart1" style="height:500px;width:600px"></div>
		</div>
	</div>
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>