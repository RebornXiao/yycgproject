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
			<s:form id="finddictinfobytypecode_show" name="finddictinfobytypecode_show" action="finddictinfobytypecode"
				method="post" theme="simple">
				<table class="bodytable">
				<tr><td class="top">
						<div id="tabBtnContainer">
							<ul id="tabBtnUi">
								<li><a href="javascript:toadddictinfo();"><img id="actionBtnImg" src="<%= baseurl%>images/cfg_add_tableobj.gif" />添加信息</a></li>
								<li><a href="javascript:toupdatedictinfo();"><img id="actionBtnImg" src="<%= baseurl%>images/cfg_edit_managerobj.gif" />修改信息</a></li>
								<li><a href="javascript:tosetstate('1');"><img id="actionBtnImg" src="<%= baseurl%>images/cfg_edit_managerobj.gif" />设置可用</a></li>
								<li><a href="javascript:tosetstate('0');"><img id="actionBtnImg" src="<%= baseurl%>images/cfg_edit_managerobj.gif" />设置不可用</a></li>
							</ul>
						</div>
					</td></tr>
					<tr>
						<td class="center">
							<div class="centerdiv">
								<table class="table_data">
									<tr>
										<th width="20">
											<input id="Check" type="checkbox"
												onClick="checkAll(this, 'Check')" />
										</th>

										<th width="20%">
											类别
										</th>
										<th width="30%">
											名称/值
										</th>
										<s:if test="isshow == 'yes'">
											<th width="10%">
												代码
											</th>
										</s:if>
										<th width="10%">
											显示顺序
										</th>
										<th width="10%">
											状态
										</th>
										<th width="30%">
											备注
										</th>
										

									</tr>
									<s:iterator value="reslist" id="reslist" status="res">
										<tr <s:if test="(#res.index+1)%2==0">class="two"</s:if>>
											<td align="center">
												<s:checkbox name="Check" fieldValue="%{#reslist.id}"
													onclick="chkRow(this)" />
											</td>
											
											<td>
												<s:property value="#reslist.typename" />
											</td>
											<td>
												<s:property value="#reslist.info" />
											</td>
											<s:if test="isshow == 'yes'">
												<td>
													<s:property value="#reslist.dictcode" />
												</td>
											</s:if>
											<td>
												<s:property value="#reslist.dictsort" />
											</td>
											<td>
												<s:if test="#reslist.isenable == 1">启用</s:if>
												<s:elseif test="#reslist.isenable == 0">停用</s:elseif>
											</td>
											<td>
												<s:property value="#reslist.remark" />
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
