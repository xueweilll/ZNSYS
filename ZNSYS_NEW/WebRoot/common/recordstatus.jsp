<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../inc/nocache.inc"%>
<%int status=(Integer)request.getAttribute("status");
switch(status)
{
case 1:%>
showSuccess('记录已提交工程管理科！');
<%break;
case 2:%>
showSuccess('记录已提交处长审核！');
<%break;
case 3:%>
showSuccess('记录已审核，提交养护大队！');
<%break;
case 4:%>
showSuccess('记录已申请验收！');
<%break;
case 5:
case 6:%>
showSuccess('记录已验收！');
<%}%>