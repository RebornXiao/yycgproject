<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/jsp/common/base.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>业务管理</title>
		<link rel="stylesheet" type="text/css" href="js/ext/css/ext-all.css" />
		<script defer="defer" type="text/javascript" src="js/ext/ext-base.js"></script>
		<script defer="defer" type="text/javascript" src="js/ext/ext-all.js"></script>
		<script defer="defer" type="text/javascript" src="js/ext/ext-lang-zh_CN.js"></script>
		<script defer="defer" type="text/javascript" src="js/sysmag/Validation.js"></script>
		<script defer="defer" type="text/javascript" src="js/sysmag/BusinessMgmt.js"></script>
	<body onLoad="init()">
	<input id="toolbar" type="hidden" value="${toolbar}">
	</body>
</html>
