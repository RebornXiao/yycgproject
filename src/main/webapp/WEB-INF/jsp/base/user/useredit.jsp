<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>用户修改</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">

<LINK rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">

$(function (){
    /******表单校验*************/
	$.formValidator.initConfig({
		formID : "sysusereditform",
		theme : "Default",
		onError : function(msg, obj, errorlist) {
			//alert(msg);
		}
	});
	//用户账号
	$("#sysuser_userid").formValidator({
		onShow : "",
		onCorrect:"&nbsp;"
	}).inputValidator({
		min : 1,
		max : 20,
		onError : "请输入用户账号(最长10个字符)"
	});
});

//提交表单
function sysusersave(){ 
	if($.formValidator.pageIsValid()){//校验表单输入信息是否合法
		//使用jquery的ajax from提交，指定from的id和回调方法，提交的url使用提from中的action
		jquerySubByFId('sysusereditform',sysusersave_callback,null,"json");
	}
	
	
}
//from提交的回调方法,data是提交的返回的数据
function sysusersave_callback(data){
	message_alert(data);
}


</script>

</head>
<body>
<form id="sysusereditform" name="sysusereditform" action="${baseurl}user/usereditsubmit.action" method="post">
<input type="hidden" name="sysuser.id" value="${sysuser.id}" />
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;系统用户信息</TD>
								<TD align=right>&nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
			<TR>
				<TD>
					<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
						align=center>
						<TBODY>
							
							<TR>
								<TD height=30 width="15%" align=right >用户账号：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="sysuser_userid" name="sysuser.userid" value="${sysuser.userid}" />
								</div>
								<!-- sysuser_useridTip用于显示提示信息，提示div的id等于校验input的id+Tip -->
								<div id="sysuser_useridTip"></div>
								</TD>
								<TD height=30 width="15%" align=right >用户名称：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="sysuser_username" name="sysuser.username"  value="${sysuser.username}" />
								</div>
								<div id="sysuser_usernameTip"></div>
								</TD>
							</TR>
							<TR>
								<TD height=30 width="15%" align=right >用户密码：</TD>
								<TD class=category width="35%">
								<div>
									<input type="password" id="sysuser_password" name="sysuser.pwd" />
								</div>
								<div id="sysuser_passwordTip"></div>
								</TD>
								<TD height=30 width="15%" align=right >用户类型：</TD>
								<TD class=category width="35%">
								<div>
								
								<select name="sysuser.groupid" id="sysuser_groupid">
									
								<option value="">请选择</option>
								<option value="1" <c:if test="${sysuser.groupid=='1'}">selected</c:if>>卫生局</option>
								<option value="2" <c:if test="${sysuser.groupid=='2'}">selected</c:if>>卫生院</option>
								<option value="3" <c:if test="${sysuser.groupid=='3'}">selected</c:if>>卫生室</option>
								<option value="4" <c:if test="${sysuser.groupid=='4'}">selected</c:if>>供货商</option>
								<option value="0" <c:if test="${sysuser.groupid=='0'}">selected</c:if>>系统管理员</option>
	
								</select>
								</div>
								<div id="sysuser_groupidTip"></div>
								</TD>
								
								
							</TR>
							<TR>
							    <TD height=30 width="15%" align=right >用户单位名称：</TD><!-- 用处：根据名称获取单位id -->
								<TD class=category width="35%">
								<input type="text" name="sysuser.sysid" value="${sysmc}"/>
								</TD>
								<TD height=30 width="15%" align=right>用户状态：</TD>
								<TD class=category width="35%">
								<input type="radio" name="sysuser.userstate" value="1" <c:if test="${sysuser.userstate=='1'}">checked</c:if>/>正常
								<input type="radio" name="sysuser.userstate" value="0" <c:if test="${sysuser.userstate=='0'}">checked</c:if>/>暂停
								</TD>
								
							</TR>
							<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn"  class="easyui-linkbutton"   iconCls="icon-ok" href="#" onclick="sysusersave()">提交</a>
								<a id="closebtn"  class="easyui-linkbutton" iconCls="icon-cancel" href="#" onclick="parent.closemodalwindow()">关闭</a>
							  </td>
							</tr>
						
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
			</TABLE>
			


</form>


</body>

</html>
