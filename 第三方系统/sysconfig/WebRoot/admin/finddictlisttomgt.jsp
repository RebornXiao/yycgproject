<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<%
	String path = request.getContextPath();
	String baseurl = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/";
			System.out.println("==="+baseurl);
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	</head>
	<body>
				<table class="bodytable">
					<tr>
						<td class="center">
							<div class="centerdiv">
								<table class="table_data">
									<s:iterator value="reslist" id="reslist" status="res">
										<tr>
											<td style="padding-left:20px">
												<a href="javascript:void(0)" onclick="searchcenterSub('<s:property value="#reslist.typecode" />')"><s:property value="#reslist.typename" /></a>
											</td>
										</tr>
									</s:iterator>
								</table>
							</div>
						</td>
				</table>
	</body>
</html>
