<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/jsp/common/base.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>模块管理</title>
		<link rel="stylesheet" type="text/css" href="js/ext/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="css/custom.css" />
		<script defer="defer" type="text/javascript" src="js/ext/ext-base.js"></script>
		<script defer="defer" type="text/javascript" src="js/ext/ext-all-debug.js"></script>
		<script defer="defer" type="text/javascript" src="js/ext/ext-lang-zh_CN.js"></script>
		<script defer="defer" type="text/javascript" src="js/sysmag/Validation.js"></script>
		<script defer="defer" type="text/javascript" src="js/sysmag/ModuleMgmt.js"></script>
		<script defer="defer" type="text/javascript" src="js/sysmag/SystemGrid.js"></script>
		<script defer="defer" type="text/javascript" src="js/sysmag/ModuleGridPanel.js"></script>
		<script defer="defer" type="text/javascript" src="js/sysmag/OperationGridPanel.js"></script>
		<script defer="defer" type="text/javascript" src="js/sysmag/ModuleMainPanel.js"></script>
	<body onLoad="init()">
	<input type="hidden" id="toolbar" value="${toolbar}" />
	<div id="gd" ></div>
	</body>
</html>
