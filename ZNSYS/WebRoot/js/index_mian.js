$(function(){
	var bodyHeight = document.documentElement.clientHeight;
	var bodyWidth = document.body.clientWidth;
	
	//让标题居中Middle
	var titleHeight = $(".title").height();
	var imgHeight = $(".img").height();
	var margin = (bodyHeight-80-titleHeight)/2;
	$(".title").attr("style","margin:"+(margin-20)+"px 0px "+(margin+20)+"px 0px");
	$(".img").attr("style","margin:"+(margin-20)+"px 0px "+(margin+20)+"px 0px");
	
});