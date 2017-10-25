window.onload = function ()
{
	var oBox = document.getElementById("box");
	var aUl = document.getElementsByTagName("ul");
	var aImg = aUl[0].getElementsByTagName("li");
	var aNum = aUl[1].getElementsByTagName("li");
	var timer = play = null;
	var i = index = 0;	
	var bOrder = true;
	//�л���ť
	for (i = 0; i < aNum.length; i++)
	{
		aNum[i].index = i;
		aNum[i].onmouseover = function ()
		{
			show(this.index)
		}
	}
	
	//��껮���رն�ʱ��
	oBox.onmouseover = function ()
	{
		clearInterval(play)	
	};
	
	//����뿪�����Զ�����
	oBox.onmouseout = function ()
	{
		autoPlay()
	};	
	
	//�Զ����ź���
	function autoPlay ()
	{
		play = setInterval(function () {
			//�жϲ���˳��
			bOrder ? index++ : index--;			
			
			//����
			index >= aImg.length && (index = aImg.length - 2, bOrder = false);
			
			//����
			index <= 0 && (index = 0, bOrder = true);
			
			//���ú���
			show(index)
		},3000);	
	}
	autoPlay();//Ӧ��
	
	//ͼƬ�л�, ���뵭��Ч��
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