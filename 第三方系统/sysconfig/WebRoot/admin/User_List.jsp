<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String message = (String) request.getAttribute("message");
	if (message == null)
		message = "";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js">
</script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js">
</script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/ajax/jquery.js">
</script>
		<script type="text/javascript" src="<%=path%>/scripts/jquery-1.2.js">
</script>
		<script type="text/javascript">

function search() {
	contest.action = "<%=path%>/admin/getAllUserList.action";
	contest.submit();
}
function resetForm() {
	document.getElementById("searchName").value = "";
	document.getElementById("usercode").value = "";
	document.getElementById("dltTarget").value = "";
	document.getElementById("sysroof").value = "";
}
function topermission() {
	var objField = document.getElementsByName("checkboxId");
	var num = getCheckBoxSelectNumber(objField);
	if (num == 0) {
		ymPrompt.alert('请选择一条记录！', null, null, '提示', null);
		return false;
	} else if (num > 1) {
		ymPrompt.alert('每次只能修改一条记录！', null, null, '提示', null);
		return false;
	}
	var v = getCheckBoxvalue(objField);
	$.get("<%=path%>/admin/delCheck.action", {
		ids : v
	}, permissionsdo);
}
function permissionsdo(xml) {
	if (xml == "false") {
		ymPrompt.alert("系统初始用户和自身用户不允许维护!", null, null, '警告', null)
		return false;
	}
	var objField = document.getElementsByName("checkboxId");
	var v = getCheckBoxvalue(objField);
	var window_parameter = "dialogWidth:650px;dialogHeight:600px;help:no;scroll:auto;status:no";
	var actionname = "<%=path%>/admin/getUserPermissions.action?id=" + v;
	var handle = showModalDialog(actionname, window, window_parameter);

}
function toAddPage() {
	contest.action = "<%=path%>/admin/toAddUserPage.action";
	contest.submit();
}
function toEditPage() {
	var objField = document.getElementsByName("checkboxId");
	var num = getCheckBoxSelectNumber(objField);
	if (num == 0) {
		ymPrompt.alert('请选择一条记录！', null, null, '提示', null);
		return false;
	} else if (num > 1) {
		ymPrompt.alert('每次只能修改一条记录！', null, null, '提示', null);
		return false;
	}
	var v = getCheckBoxvalue(objField);
	$.get("<%=path%>/admin/delCheck.action", {
		ids : v
	}, updatedo);

}
function updatedo(xml) {
	if (xml == "false") {
		ymPrompt.alert("系统初始用户和自身用户不允许修改!", null, null, '警告', null)
		return false;
	}
	var objField = document.getElementsByName("checkboxId");
	var v = getCheckBoxvalue(objField);
	contest.action = "<%=path%>/admin/getUserObj.action?id=" + v;
	contest.submit();
}
function delCheck() {
	var objField = document.getElementsByName("checkboxId");
	var num = getCheckBoxSelectNumber(objField);
	if (num == 0) {
		ymPrompt.alert('请选择一条记录！', null, null, '提示', null);
		return false;
	}
	var v = getCheckBoxvalue(objField);
	$.get("<%=path%>/admin/delCheck.action", {
		ids : v
	}, del);
}
function del(xml) {
	if (xml == "false") {
		ymPrompt.alert("系统初始用户和自身用户不允许删除!", null, null, '警告', null)
		return false;
	}
	ymPrompt.confirmInfo('确定要删除吗？', null, null, "提示", handler);
}
function handler(tp) {
	if (tp == 'ok') {
		var v = getCheckBoxvalue(document.getElementsByName("checkboxId"));
		contest.action = "<%=path%>/admin/delUser.action?ids=" + v;
		contest.submit();
	}

}
function initpwd() {
	var initpwd = '<s:property value="#request.initpwd"/>';
	var objField = document.getElementsByName("checkboxId");
	var num = getCheckBoxSelectNumber(objField);
	if (num == 0) {
		ymPrompt.alert('请选择一条记录！', null, null, '提示', null);
		return false;
	}
	ymPrompt.confirmInfo('通过此功能该用户的登陆密码将初始化为' + initpwd + '，确定要继续吗？', null,
			null, "提示", inithandler);
}
function inithandler(tp) {
	if (tp == 'ok') {
		var v = getCheckBoxvalue(document.getElementsByName("checkboxId"));
		contest.action = "<%=path%>/admin/initPWDByUser.action?ids=" + v;
		contest.submit();
	}
}
function jqueryGetEleByName(eleName) {
	var curElement = document.getElementsByName(eleName)[0];
	if (curElement === undefined) {
		return null;
	}
	var curEleType = curElement.type;
	var obj = null;
	if (curEleType == "text" || curEleType == "password"
			|| curEleType == "hidden" || curEleType == "button"
			|| curEleType == "submit" || curEleType == "radio"
			|| curEleType == "checkbox") {
		obj = jQuery("input[@name=" + eleName + "]");
	} else if (curEleType == "select-one" || curEleType == "select-multiple") {
		obj = jQuery("select[@name=" + eleName + "]");
	}
	return obj;
}
function chkRowOnly(_this, itemName) {
	var item = jqueryGetEleByName(itemName);

	if (null != item) {
		for ( var i = 0; i < item.length; i++) {
			var r = item[i].parentNode.parentNode
			item[i].checked = false;
			jQuery(r).removeClass("over");
		}
	}
	_this.checked = true;
	var r = _this.parentNode.parentNode;
	if (_this.checked) {
		jQuery(r).addClass("over");
	} else {
		jQuery(r).removeClass("over");
	}
}
</script>
	</head>
	<body scroll="auto">
		<form name="contest" action="<%=path%>/admin/getAllUserList.action" method="post">
			<table width="100%">
				<TR>
					<td>
						<table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0">
							<caption>
								用户查询
							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="55%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									登陆账号
								</td>
								<td>
									<s:textfield name="usercode" id="usercode" style="width:300px;" />
								</td>
								<td class="labelTd">
									姓名
								</td>
								<td>
									<s:textfield name="searchName" id="searchName" style="width:300px;" />
								</td>
							</tr>
							<s:if test="user.groupid!=2">

								<tr>
									<td class="labelTd">
										系统平台
									</td>
									<td>
										<s:select id="sysroof" name="sysroof" list="#{'1':'省平台','2':'学习中心','0':'系统管理平台'}"  headerKey="" headerValue="=====全部=====" style="width:300px;   "/>
									</td>
									<td class="labelTd">
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
							</s:if>
							<tr>
								<td align="center" colspan="5">
									<input type="button" class="button" value=" [查询用户] " onclick="search();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table class="Table" width="70%" cellpadding="3" cellspacing="0" align="center">
							<tr>
								<td class="freeBar" colspan="8">
									<!-- <t:button buttiontype="1"/> -->
									<style>
										.toAddPage {
											background-image: url(/sysmanager/vcomframe/images/add.gif) !important;
										}
										
										.toEditPage {
											background-image: url(/sysmanager/vcomframe/images/plugin.gif)
												!important;
										}
										
										.delCheck {
											background-image: url(/sysmanager/vcomframe/images/delete.gif)
												!important;
										}
										
										.initpwd {
											background-image: url(/sysmanager/vcomframe/images/plugin.gif)
												!important;
										}
									</style>
									<DIV class="x-toolbar x-small-editor">
										<TABLE cellSpacing=0>
											<TBODY>
												<TR>
													<TD>
														<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" border=0 cellSpacing=0 cellPadding=0 onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'">
															<TBODY>
																<TR>
																	<TD class=x-btn-left>
																		<I>&nbsp;</I>
																	</TD>
																	<TD class=x-btn-center>
																		<EM unselectable="on"><BUTTON id=ext-gen22 class="x-btn-text toAddPage" onclick="toAddPage();">
																				添加
																			</BUTTON>
																		</EM>
																	</TD>
																	<TD class=x-btn-right>
																		<I>&nbsp;</I>
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
													<TD>
														<SPAN id=ext-gen28 class=ytb-sep></SPAN>
													</TD>
													<TD>
														<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" border=0 cellSpacing=0 cellPadding=0 onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'">
															<TBODY>
																<TR>
																	<TD class=x-btn-left>
																		<I>&nbsp;</I>
																	</TD>
																	<TD class=x-btn-center>
																		<EM unselectable="on"><BUTTON id=ext-gen22 class="x-btn-text toEditPage" onclick="toEditPage();">
																				修改
																			</BUTTON>
																		</EM>
																	</TD>
																	<TD class=x-btn-right>
																		<I>&nbsp;</I>
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
													<TD>
														<SPAN id=ext-gen28 class=ytb-sep></SPAN>
													</TD>
													<TD>
														<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" border=0 cellSpacing=0 cellPadding=0 onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'">
															<TBODY>
																<TR>
																	<TD class=x-btn-left>
																		<I>&nbsp;</I>
																	</TD>
																	<TD class=x-btn-center>
																		<EM unselectable="on"><BUTTON id=ext-gen22 class="x-btn-text delCheck" onclick="delCheck();">
																				删除
																			</BUTTON>
																		</EM>
																	</TD>
																	<TD class=x-btn-right>
																		<I>&nbsp;</I>
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
													<TD>
														<SPAN id=ext-gen28 class=ytb-sep></SPAN>
													</TD>
													<TD>
														<TABLE style="WIDTH: auto" class="x-btn-wrap x-btn x-btn-text-icon" border=0 cellSpacing=0 cellPadding=0 onmouseout="this.className='x-btn-wrap x-btn x-btn-text-icon'" onmousemove="this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'">
															<TBODY>
																<TR>
																	<TD class=x-btn-left>
																		<I>&nbsp;</I>
																	</TD>
																	<TD class=x-btn-center>
																		<EM unselectable="on"><BUTTON id=ext-gen22 class="x-btn-text initpwd" onclick="initpwd();">
																				初始化密码
																			</BUTTON>
																		</EM>
																	</TD>
																	<TD class=x-btn-right>
																		<I>&nbsp;</I>
																	</TD>
																</TR>
															</TBODY>
														</TABLE>
													</TD>
													<TD>
														<SPAN id=ext-gen28 class=ytb-sep></SPAN>
													</TD>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									</DIV>
								</td>
							</tr>
							<!-- 表头 -->
							<caption>
								用户列表
							</caption>
							<!-- 每列所占的比例 -->
							<colgroup>
								<col width="6%"></col>
								<col width="6%"></col>
								<col width="15%"></col>
								<col width="15%"></col>
								<col width="15%"></col>
								<col width="20%"></col>
								<col width="11%"></col>
								<col width="10%"></col>

							</colgroup>
							<tr>
								<td class="head">
									序号
								</td>
								<td class="head">
									<input type="checkbox" id="all" name="all" class="checkbox" onclick="checkall(this.checked,this.form)" />
								</td>
								<td class="head">
									登陆账号
								</td>
								<td class="head">
									姓名
								</td>
								<td class="head">
									系统平台
								</td>
								<td class="head">
									业务代码(限学习中心使用)
								</td>
								<td class="head">
									联系方式
								</td>
								<td class="head">
									备注
								</td>

							</tr>

							<s:iterator value="userList" id="user" status="st">
								<tr onmouseover="changecolor(this,1);" onmouseout="changecolor(this,2);">
									<td class="changecol">
										<s:property value="#st.index+1" />
									</td>
									<td class="changecol">
										<input name="checkboxId" type="checkbox" id="checkboxId" class="checkbox" value="<s:property value="id" />" onclick="uncheck(this,this.form)">
										<!--  <input name="checkboxId" type="checkbox" id="checkboxId"
											onclick="chkRowOnly(this, 'checkboxId')" class="checkbox" value="<s:property value="id" />"  >-->
									</td>
									<td class="changecol">
										<s:property value='usercode' />
									</td>
									<td class="changecol">
										<s:property value='username' />
									</td>
									<td class="changecol">
										<s:if test="#user.groupid==1">省平台</s:if>
										<s:elseif test="#user.groupid==2">学习中心</s:elseif>
										<s:elseif test="#user.groupid==0">系统管理平台</s:elseif>
										<s:else>&nbsp;</s:else>
									</td>
									<td class="changecol">
										<s:property value='sysid' />
										&nbsp;
									</td>
									<td class="changecol">
										<s:property value='phone' />
										&nbsp;
									</td>
									<td class="changecol">
										<s:property value='remark' />
										&nbsp;
									</td>

								</tr>
							</s:iterator>
							<tr>
								<td colspan="8" align="right" class="head">
									<s:property value="#request.pageBar" escape="false" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		<script>
var message = '<%=message%>';
if ("error" == message) {
	ymPrompt.errorInfo('操作失败！', 200, 200, '提示', null);
}
if ('${newuserid}' != null && '${newuserid}' != '') {
	var window_parameter = "dialogWidth:650px;dialogHeight:600px;help:no;scroll:auto;status:no";
	var actionname = "<%=path%>/admin/getUserPermissions.action?id=${newuserid}";
	var handle = showModalDialog(actionname, window, window_parameter);
}
</script>
	</body>
</html>

