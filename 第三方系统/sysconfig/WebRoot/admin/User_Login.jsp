<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String message=(String)request.getAttribute("message");
	if(message==null) message="";
	
	String jwpath = request.getScheme() + "://"+ request.getServerName() + ":" + (request.getServerPort()==80?"":request.getServerPort())+ "/";
	jwpath = jwpath+"/loginshow.action";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0035)http://192.168.151.240/login.action -->
<HTML>
	<HEAD>
		<TITLE><s:property value="projecttitle"/></TITLE>
		<META http-equiv=Content-Type content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<script type="text/javascript"  src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<STYLE type=text/css>
BODY {
	MARGIN-TOP: 80px;
	BACKGROUND-COLOR: #b9cae8
}

TD {
	FONT-SIZE: 12px;
	COLOR: #ffffff
}

INPUT {
	BORDER-RIGHT: #8aacec 1px solid;
	BORDER-TOP: #8aacec 1px solid;
	FONT-SIZE: 9pt;
	BORDER-LEFT: #8aacec 1px solid;
	COLOR: #0c2a5c;
	BORDER-BOTTOM: #8aacec 1px solid;
	FONT-FAMILY: Arial, Helvetica, sans-serif;
	HEIGHT: 20px;
	BACKGROUND-COLOR: #ffffff
}

BODY {
	COLOR: #000000
}

TD {
	COLOR: #000000
}

TH {
	COLOR: #000000
}
</STYLE>

		<META content="MSHTML 6.00.6000.16735" name=GENERATOR>
	</HEAD>
	<BODY onload=gotojw();>
		 <script type="text/javascript">
            
    		window.location="<%=jwpath%>";
    		
    		function gotojw(){
    		   window.location="<%=jwpath%>";
    		}
 
    </script>
	</BODY>
</HTML>
