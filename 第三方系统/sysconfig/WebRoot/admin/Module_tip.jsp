<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/css/common.css" />

		<link rel="stylesheet" id='skin' type="text/css"
			href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />

		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>

		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<base target="_self">
	</head>
	<body scroll="auto">

		<table width="100%">
			<TR>
				<td>
					<table align="center" width="100%" class="TableForm"
						cellpadding="3" cellspacing="0">
						<caption>
							注意
						</caption>
						<colgroup>
							<col width="15%"></col>
							<col width="40%"></col>
							<col width="15%"></col>
							<col width="30%"></col>
						</colgroup>
						<tr>
							 
							<td colspan="4">
								<span class="star">请选择叶子结点。</span>
							</td>

						</tr>



					</table>
				</td>
			</tr>
		</table>

	</body>
	<script type="text/javascript">
         
</script>
</html>
