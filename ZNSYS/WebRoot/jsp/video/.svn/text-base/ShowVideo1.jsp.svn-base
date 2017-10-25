<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../../inc/nocache.inc"%>
<%@ include file="../../inc/validatewindow.inc"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>常州市防汛信息集成平台</title>
<%@ include file="../../inc/easyui.inc"%>
<script>
	var m_bDVRControl;
	$(function(){
		$(window).unload(function(){
			try
			{
			 var myDate = new Date();
            var iYear = myDate.getFullYear();
            if (iYear < 1971 || iYear > 2037) {
                alert("请将系统时间设置成1971-2037！");
                //parent.location.href = "../login.php";
            }
            if (document.getElementById("HIKOBJECT1").object == null) {
                alert("请安装控件！");
                m_bDVRControl = null;
            }
            else {
                m_bDVRControl = document.getElementById("HIKOBJECT1");
            }
            var szDevIp = '10.72.21.104';
            var szDevPort = '8000';
            var szDevUser = 'admin';
            var szDevPwd = '12345';
            m_iLoginUserId = m_bDVRControl.Login(szDevIp, szDevPort, szDevUser, szDevPwd);
            m_iNowChanNo = 3;
            if (m_iNowChanNo > -1) {
                if (m_iPlay == 1) {
                    m_bDVRControl.StopRealPlay();
                }
 
                var bRet = m_bDVRControl.StartRealPlay(m_iNowChanNo, m_iProtocolType, m_iStreamType);
                if (bRet) {
                    LogMessage("预览通道" + (m_iNowChanNo + 1) + "成功！");
                    m_iPlay = 1;
                }
                else {
                    LogMessage("预览通道" + (m_iNowChanNo + 1) + "失败！");
                }
            }	 				 
			}
			catch(error){}
		});

		 
</script>
</head>  
<body class="easyui-layout">
	 
</body>
</html>
<%@ include file="../../inc/loaded.inc"%>