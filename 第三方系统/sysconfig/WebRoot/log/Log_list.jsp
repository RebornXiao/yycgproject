<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/list/DatePicker/WdatePicker.js">
</script>
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js">
</script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js">
</script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/check/formInvalidCheck.js">
</script>

		<script type="text/javascript">
function search(flag) {
	if (flag != null) {
		document.forms(0).pageIndex.value = 1;
	}
	vcomlogcontent.submit();
}
function toDeleteLogRecordPage() {

}
function resetfrom() {
	//		        document.getElementById("searchIp").value="";
	vcomlogcontent.searchIp.value = "";
	//		        vcomlogcontent.logType.value="";
	vcomlogcontent.startTime.value = "";
	vcomlogcontent.endTime.value = "";
}
</script>
	</head>
	<body scroll="auto">
		<form name="vcomlogcontent" onsubmit="search();" action="<%=path%>/log/getLogPageUI.action" method="post">
			<table width="100%">
				<TR>
					<td>
						<table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0">

							<caption>
								日志查询
							</caption>
							<colgroup>
								<col width="15%">
								<col width="35%">
								<col width="15%">
								<col width="35%">
							</colgroup>
							<tr>
								<td class="labelTd">
									IP地址
								</td>
								<td>
									<input name="searchIp" maxlength="15" value="<s:property value="searchIp"/>" id="searchIp" type="text">
								</td>
								<td class="labelTd">
									日志类型
								</td>
								<td>
									<select name="logType">
										<option value="-1" <s:if test="-1==logType">selected="selected"</s:if>>
											所有日志
										</option>
										<option value="1" <s:if test="1==logType">selected="selected"</s:if>>
											操作日志
										</option>
										<option value="2" <s:if test="2==logType">selected="selected"</s:if>>
											异常日志
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									创建时间
								</td>
								<td>
									<input id="d4311" class="Wdate" name="startTime" readonly="readonly" value="<s:property value="startTime"/>" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2040-10-01\'}'})" type="text">
								</td>
								<td class="labelTd">
									至
								</td>
								<td>
									<input id="d4312" class="Wdate" name="endTime" readonly="readonly" value="<s:property value="endTime"/>" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2040-10-01'})" type="text">
								</td>
							</tr>
							<tr>
								<td colspan="4" align="center">
									<input class="button" value="[检 索]" onclick="search('');" type="button">
									&nbsp;
									<input class="button" id="resetNote" value="[重 置]" onclick="resetfrom();" type="button">

								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table class="Table" width="70%" cellpadding="3" cellspacing="0" align="center">
							<!-- 表头 -->
							<caption>
								列表
							</caption>
							<tr>
								<td class="freeBar" colspan="5">
									<!-- <t:button buttiontype="1"/> -->
									<style>
										.toBatchDelLogPage {
											background-image: url(/sysmanager/vcomframe/images/delete.gif)
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
																		<EM unselectable="on"><BUTTON id=ext-gen22 class="x-btn-text toBatchDelLogPage" onclick="toBatchDelLogPage();">
																				批量删除
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
							<!-- 每列所占的比例 -->
							<colgroup>
								<!--  <col width="2%"></col>-->
								<col width="12%">
								<col width="10%">
								<!-- 日志信息 -->

								<col width="45%">
								<col width="15%">
								<col width="18%">
							</colgroup>
							<tr>
								<!--  
								<td class="head">
									<input type="checkbox" id="all" name="all" class="checkbox"
										onclick="checkall(this.checked,this.form)" />
								</td>-->
								<td class="head" nowrap="nowrap">
									管理员名称
								</td>
								<td class="head" nowrap="nowrap">
									日志类型
								</td>
								<td class="head">
									日志信息
								</td>
								<td class="head">
									IP地址
								</td>
								<td class="head" nowrap="nowrap">
									创建时间
								</td>
							</tr>
							<s:iterator value="logList" id="log" status="st">
								<tr onmouseover="changecolor(this,1);" onmouseout="changecolor(this,2);">
									<!-- 
										<td class="changecol">
											<input name="checkboxId" type="checkbox" id="checkboxId"
												class="checkbox" value="2347">
										</td>-->
									<td class="changecol">
										<s:property value='userName' />
									</td>
									<td class="changecol">
										<s:if test='source==1'>操作日志</s:if>
										<s:if test='source==2'>异常日志</s:if>
									</td>
									<td class="changecol">
										<s:property value='messages' />
									</td>
									<td class="changecol">
										<s:property value='userIp' />
									</td>
									<td class="changecol">
										<s:date name="operateDate" format="yyyy-MM-dd HH:mm:ss" />

									</td>
								</tr>
							</s:iterator>
							<tr>
								<td colspan="7" align="right" class="head">
									<s:property value="#request.pageBar" escape="false" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
var actionname = "";
var window_parameter = "dialogWidth:450px;dialogHeight:380px;help:no;scroll:auto;status:no";

function toBatchDelLogPage() {

	//alert(window.dialogArguments);
	actionname = "<%=path%>/log/toBatchDelLogPage.action";

	var handle = showModalDialog(actionname, window, window_parameter);

	if (handle) {
		document.forms(0).pageIndex.value = 1;//设置为第一页
		vcomlogcontent.action = "<%=path%>/log/getLogPageUI.action";
		vcomlogcontent.submit();

	}
}
</script>
</html>
