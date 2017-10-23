<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<%
	String path = request.getContextPath();

%>
<html>
	<head>
		<title>操作成功</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
	    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="<%= path%>/styles/body.css" type="text/css"/>
		<script type="text/javascript">
		//确定
		var resultUrl = "<s:property value="resulturl" />";
		var nextUrl = "<s:property value="nexturl" />";
		function resultUrl_method(){
			if(resultUrl != ""){
				window.location = resultUrl;
			}else{
				window.history.go(-1);
			}
		}
		function nextUrl_method(){
			if(nextUrl != ""){
				window.location = nextUrl;
			}else{
				window.history.go(-1);
			}
		}
	</script>
		<style>
		body{
			font-family:"宋体";
			font-size:12px;
			margin:0px;
			color:#445588;
			background-color:#ECF3FB;
		}
		
		table.box{
			border: 1px solid #2275B9;
			width: 400px;
			vertical-align:middle;
			background-color: #E4EEE8;
		}
		</style>
	</head>
	<body>
		<table width="100%">
			<tr height="50"><td>&nbsp;</td></tr>
			<tr align="center">
				<td align="center">
					<table class="box">
						<tr>
							<div id="tabBtnContainer">
								<ul id="tabBtnUi">
									<li><a href="#"></a>成功提示信息</li>
								</ul>
							</div>
						</tr>
						<tr>
							<td width="100" align="right">
								<img alt="" src="<%= path%>/images/yes.png" align="middle" height="50" width="50"/>
							</td>
							<td style="font-size:20px;">
								<font color="green"><s:actionmessage/></font>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<s:if test="#request.nexturl!=null">
								<input type="button" align="middle" class="button" value="确定" onclick="nextUrl_method()" />
								</s:if>
								<s:if test="#request.resulturl!=null">
								<input type="button" align="middle" class="button" value="返回" onclick="resultUrl_method()" />
								</s:if>
							</td>
						</tr>
					</table>
				<td>
			</tr>
		</table>
	</body>
</html>
