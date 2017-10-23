<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
            
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
        <base href="<%=basePath %>">
		<title>系统管理</title>
		<link rel="stylesheet" type="text/css" href="js/ext/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="css/custom.css" />
		<script type="text/javascript" src="js/ext/ext-base.js"></script>
		<script type="text/javascript" src="js/ext/ext-all.js"></script>
		<script type="text/javascript" src="js/ext/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="js/sysmag/index.js"></script>
		<script>
 			Ext.onReady(function (){acdLayout();})
 		</script>
	</head>
	<body>
	</body>
</html>
