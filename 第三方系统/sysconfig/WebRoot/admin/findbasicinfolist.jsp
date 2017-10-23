<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<%
	String path = request.getContextPath();
	String baseurl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	System.out.println("===" + baseurl);
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
	String message = (String) request.getAttribute("message");
	if (message == null)
		message = "";
%>
<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js">
</script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js">
</script>
		<script type="text/javascript">
function toEdit() {
	findbasicinfolist.action = "<%=path%>/admin/updateBasicinfo.action";
	findbasicinfolist.submit();

}
</script>
	</head>
	<body scroll="auto">
		<s:form id="findbasicinfolist" name="findbasicinfolist" action="findbasicinfolist" method="post" theme="simple">
			  <table class="Table" width="70%" cellpadding="3" cellspacing="0" align="center">
				<caption>
					系统参数配置
				</caption>
				<tr>
					<td class="freeBar" colspan="8">
						<style>
							.toEdit {
								background-image: url(/sysmanager/vcomframe/images/plugin.gif)
									!important;
							}
						</style>
						
					</td>
				</tr>
				<!-- 每列所占的比例 -->
				<colgroup>
					<col width="30%"></col>
					<col width="70%"></col>


				</colgroup>
				<tr>
					<td class="head">
						配置项名称
					</td>
					<td class="head">
						配置项值
					</td>
				</tr>
				<s:iterator value="basicinfoList" id="basicinfo" status="st">
					<tr onmouseover="changecolor(this,1);" onmouseout="changecolor(this,2);">
						<td class="changecol">
							<s:property value="name" />
							&nbsp;
						</td>
						<td class="changecol">
							<s:hidden name="id" />
							<s:textfield name="value" cssStyle="width:600px" />
							&nbsp;
						</td>
					</tr>
				</s:iterator>
					<tr>
					<td colspan=2 align="center">
						<input type="button" value="	提 交		" onclick="toEdit()" />
					</td>
				</tr>
			</table>
			
			
		</s:form>
		<script>
var message = '<%=message%>';
if ("error" == message) {
	ymPrompt.errorInfo('操作失败！', 200, 200, '提示', null);
}
</script>
	</body>
</html>
