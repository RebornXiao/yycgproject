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
			<s:form id="finddictlist" name="finddictlist" action="finddictlist"
				method="post" theme="simple">
				<table class="bodytable">
					<tr>
						<td class="center">
							<div class="centerdiv">
								<table class="table_data">
									<tr>
										<th width="10%">
											<input id="Check" type="checkbox"
												onClick="checkAll(this, 'Check')" />
										</th>
										<th width="20%">
											类别编码
										</th>
										<th width="20%">
											类别名称
										</th>
										<th width="30%">
											备注信息
										</th>
										<th width="20%">
											显示顺序
										</th>

									</tr>
									<s:iterator value="reslist" id="reslist" status="res">
										<tr <s:if test="(#res.index+1)%2==0">class="two"</s:if>>
											<td align="center">
												<s:checkbox name="Check" fieldValue="%{#reslist.typecode}"
													onclick="chkRow(this)" />
											</td>
											<td>
												<s:property value="#reslist.typecode" />
											</td>
											<td>
												<s:property value="#reslist.typename" />
											</td>
											<td>
												<s:property value="#reslist.remark" />
											</td>
											<td>
												<s:property value="#reslist.typesort" />
											</td>
										</tr>
									</s:iterator>
								</table>
							</div>
						</td>
				</table>
			</s:form>
	</body>
</html>
