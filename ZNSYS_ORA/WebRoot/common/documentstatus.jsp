<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="UTF-8"%>
<%@ include file="../inc/nocache.inc"%>
<%int status=(Integer)request.getAttribute("status");
switch(status)
{
case 1:%>
showSuccess('公文已提交审核！');
<%break;
case 2:%>
showSuccess('该公文审核通过！');
<%break;
case 3:%>
showInfo('该公文审核未通过！');
<%break;
case 4:%>
showSuccess('该公文已发布！');
<%}%>