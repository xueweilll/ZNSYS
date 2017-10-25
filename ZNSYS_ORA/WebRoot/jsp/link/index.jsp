<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@page import="com.jfsl.pojo.LinkUrl"%>
<%
	LinkUrl linkurl = (LinkUrl)request.getAttribute("linkurl");
%>

<html>
  <head>
    <title>自动跳转</title>
    <%@ include file="../../inc/easyui.inc"%>
	<script>
		<%--
		var urladdress = '<%=linkurl.getUrl()%>';
		var username = '<%=linkurl.getUsername()%>';
		var password = '<%=linkurl.getPassword()%>';
		var value_1 = '<%=linkurl.getValue_1()%>';
		var value_2 = '<%=linkurl.getValue_2()%>';
		
		var DataString = "{\""+value_1+"\":\""+username+"\",\""+value_2+"\":\""+password+"\"}";
		var postData = JSON.parse(DataString);
		
		alert(postData);
		$.post({urladdress,postData,function(data,textStatus){
				alert(data);
			}
		});--%>
		$(function(){
			//alert(frm.action+","+frm.method);
			var password = "<%=linkurl.getPassword()%>";
			
			frm.action = "<%=linkurl.getUrl()%>";
			//alert(frm.action+","+frm.method);
			frm.submit();
		});
	</script>
</head>  
<body>
	<form name="frm" id ="frm" action="" method="post">
		<input type="text" name="<%=linkurl.getValue_1()%>"/ value="<%=linkurl.getUsername()%>@petrochina.com.cn" style="display:none">
		<input type="password" name="<%=linkurl.getValue_2()%>" value="<%=linkurl.getPassword()%>" style="display:none"/>
	</form>
</body>
</html>
