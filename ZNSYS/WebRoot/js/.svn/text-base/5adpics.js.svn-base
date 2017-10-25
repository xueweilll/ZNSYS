window.onload = function ()
{
	var oBox = document.getElementById("box");
	var aUl = document.getElementsByTagName("ul");
	var aImg = aUl[0].getElementsByTagName("li");
	var aNum = aUl[1].getElementsByTagName("li");
	var timer = play = null;
	var i = index = 0;	
	var bOrder = true;
	//切换按钮
	for (i = 0; i < aNum.length; i++)
	{
		aNum[i].index = i;
		aNum[i].onmouseover = function ()
		{
			show(this.index)
		}
	}
	
	//鼠标划过关闭定时器
	oBox.onmouseover = function ()
	{
		clearInterval(play)	
	};
	
	//鼠标离开启动自动播放
	oBox.onmouseout = function ()
	{
		autoPlay()
	};	
	
	//自动播放函数
	function autoPlay ()
	{
		play = setInterval(function () {
			//判断播放顺序
			bOrder ? index++ : index--;			
			
			//正序
			index >= aImg.length && (index = aImg.length - 2, bOrder = false);
			
			//倒序
			index <= 0 && (index = 0, bOrder = true);
			
			//调用函数
			show(index)
		},3000);	
	}
	autoPlay();//应用
	
	//图片切换, 淡入淡出效果
	function show (a)
	{
		index = a;
		var alpha = 0;
		for (i = 0; i < aNum.length; i++)aNum[i].className = "";
		aNum[index].className = "current";
		clearInterval(timer);			
		
		for (i = 0; i < aImg.length; i++)
		{
			aImg[i].style.opacity = 0;
			aImg[i].style.filter = "alpha(opacity=0)";	
		}
		
		timer = setInterval(function () {
			alpha += 2;
			alpha > 100 && (alpha =100);
			aImg[index].style.opacity = alpha / 100;
			aImg[index].style.filter = "alpha(opacity = " + alpha + ")";
			alpha == 100 && clearInterval(timer)
		},20);
	}
};