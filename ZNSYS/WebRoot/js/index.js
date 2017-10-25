$(function(){
	var bodyHeight = document.body.clientHeight;
	var bodyWidth = document.body.clientWidth;
	
	$(".title,.titile_div").width(bodyWidth*3/6);
	//alert($(".title").width());
	
	if (window!=$(window).attr('top')){
		$($(window).attr('top')).attr('location','login.jsp');
		return;
	}
	$("#username").focus();//一进来自动获取焦点
	
	
});

//添加cookie
function AddCookie(name,value,days,path){
	var name = escape(name);
	var value = escape(value);
	var expires = new Date();
	expires.setTime(expires.getTime() + days * 3600000 * 24);
	path = path == ""?"":";path=" + path;
	
	var _expires = (typeof days) =="string"?"":";expires="+expires.toUTCString();
	document.cookie = name + "=" +value + _expires + path;
}

//获取cookie
function GetCookie(name){
	var name = escape(name);
	var allcookies = document.cookie;
	name += "=";
	var pos = allcookies.indexOf(name);
	if(pos != -1){
		var start = pos + name.length;
		var end = allcookies.indexOf(";",start);
		if(end == -1) end = allcookies.length;
		var value = allcookies.substring(start,end);
		return value;
	}else return "";
}

//删除cookie
function DeleteCookie(name,path){
	var name = escape(name);
	var expires = new Date(0);
	
	path = path ==""?"":";path=" + path;
	document.cookie = name + "=" + ";expires="+expires.toUTCString()+path;
}

//载入cookie
window.onload = function(){
	var username = GetCookie("username");
	var password = GetCookie("password");
	var auto = GetCookie("autologin");
	$("#username").val(username);
	$("#userpsw").val(password);
	if(username!=""&&password!="") document.getElementById("remenber").checked = true;
	if(auto!=""){
		document.getElementById("auto").checked = true;
		checkValid();
	}
}


function checkValid() {
	if ($.trim($("#username").val()).length == 0) {
		alert('请输入用户名！');
		$("#username").focus();
		return;
	}
	if ($("#userpsw").val().length == 0) {
		alert('请输入密码！');
		$("#userpsw").focus();
		return;
	}
	var remenber = document.getElementById("remenber");
	if(remenber.checked){
		//alert("remenber");
		AddCookie("username",$("#username").val(),30,"/");
		AddCookie("password",$("#userpsw").val(),30,"/");
		if(document.getElementById("auto").checked){
			AddCookie("autologin","true",30,"/");
		}
	}else{
		DeleteCookie("username","/");
		DeleteCookie("password","/");
		if(!document.getElementById("auto").checked){
			DeleteCookie("autologin","/");
		}
	}
	$("#loginForm").submit();
}

