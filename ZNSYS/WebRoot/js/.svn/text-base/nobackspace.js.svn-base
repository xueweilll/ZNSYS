document.onkeydown = function() 
{ 
	if(window.event.keyCode==116)   
	{   
		window.event.keyCode=0;   
		window.event.cancelBubble=true;   
		return false;   
	}
}

document.documentElement.onkeydown = function(evt){ 
	var b = !!evt, oEvent = evt || window.event; 
	if (oEvent.keyCode == 8) { 
		var node = b ? oEvent.target : oEvent.srcElement; 
		var reg = /^(input|textarea)$/i, regType = /^(text|password|textarea)$/i; 
		if (!reg.test(node.nodeName) || !regType.test(node.type) || node.readOnly || node.disabled) { 
			if (b) 
			{ 
				oEvent.stopPropagation(); 
			} 
			else 
			{ 
				oEvent.cancelBubble = true; 
				oEvent.keyCode = 0; 
				oEvent.returnValue = false; 
			} 
		} 
	} 
}
