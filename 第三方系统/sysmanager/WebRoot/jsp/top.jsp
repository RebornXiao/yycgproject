<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/base.jsp"%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>top</title>
		<link rel="stylesheet" type="text/css" href="css/top.css" />
		<link rel="stylesheet" type="text/css" href="js/ext/css/ext-all.css" />
		<script defer="defer" type="text/javascript" src="js/ext/ext-base.js"></script>
		<script defer="defer" type="text/javascript" src="js/ext/ext-all.js"></script>
		<script defer="defer" type="text/javascript" src="js/ext/ext-lang-zh_CN.js"></script>
		<script defer="defer" type="text/javascript" src="js/sysmag/PwdChange.js"></script>
	</head>
	<body topmargin="0" leftmargin="0">
		<form id="form1" action="http://testvcom:8080/login">
		  <input type="hidden" name="syslist" value="${sessionScope.syslist}">
		</form>
		<table width="100%" height="60" cellpadding="0" cellspacing="0"
			background="image/head_line.gif">
			<tr>
				<td>
					<img src="image/logo_1.gif" height="60">
				</td>
				<td>
					<table width="100%" height="60" border="0" cellpadding="0"
						cellspacing="0">
						<tr align="right">
							<td align="right">
								&nbsp;
								<img src="image/icons/lock_edit.png" width="16" height="16">
								<a href="javascript:parent.changePwd();" target="_self">修改密码</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<img src="image/icons/key_delete.png" width="16" height="16">
								<a href="logout.do" target="_parent">注销</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<img src="image/icons/door_in.png" width="16" height="16">
								<a href="javascript:parent.comeToOtherDeployNodes();">进入其它子系统</a>&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
