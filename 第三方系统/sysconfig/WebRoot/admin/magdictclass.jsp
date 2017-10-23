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
    <title>添加字典类别</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%= baseurl%>styles/ext-all.css" type="text/css"></link>
	<link rel="stylesheet" href="<%= baseurl%>styles/body.css" type="text/css"/>
	<link rel="stylesheet" href="<%= baseurl%>styles/dtree.css" type="text/css"/>
	<link rel="stylesheet" href="<%= baseurl%>styles/ipInput.css" type="text/css"/>
	<link rel="stylesheet" href="<%= baseurl%>scripts/My97DatePicker/skin/WdatePicker.css" type="text/css"/>
	<script type="text/javascript" src="<%= baseurl%>scripts/ext-base.js"></script>
	<script type="text/javascript" src="<%= baseurl%>scripts/ext-all.js"></script>
	<script type="text/javascript" src="<%= baseurl%>scripts/jquery-1.2.js"></script>
	<script type="text/javascript" src="<%= baseurl%>scripts/jquery.form.js"></script>
	<script type="text/javascript" src="<%= baseurl%>scripts/nms-jquery-form.js"></script>
	<script type="text/javascript" src="<%= baseurl%>scripts/dtree_checkbox.js"></script>
	<script type="text/javascript" src="<%= baseurl%>scripts/univerify.js"></script>
	<script type="text/javascript">
		
		//验证
		function checkForm(){
			
			return true;
		}
		//提交
		function winSub(){
			if(checkForm()&&verifyAll(document.savedicttype)){
		      jquerySubByFId("savedicttype",winSubBack,null);
		    }
		}
		//提交回调函数
		function winSubBack(responseText){
			extAlertCall("系统提示",responseText,alertbackfn,responseText);
		}
		//alert回调函数
		function alertbackfn(responseText){
			if(responseText.indexOf("成功") != -1){
				parent.refreshSelf();
				winClose();
			}
		}
		// 模式窗口的关闭
		function winClose(){
			parent.extModalWinDestroy();
		}
		//页面初始化方法
		function initMethod(){
			
			
		}
		extFrameModelFull('center');
	</script>
 </HEAD>
 <BODY>
 <div id="center">
 <s:form id="savedicttype" name="savedicttype" action="savedicttype" method="post" theme="simple">
 <s:hidden name="method" value="%{method}"/>
 <s:hidden name="oldtypecode" value="%{typecode}"/>
<table class="table_edit_list">
	<tr>
		<td width="60" class="table_edit_list_left_box">
			类别编码：
		</td>
		<td class="table_edit_list_right_box">
		<s:textfield id="typecode" name="dicttype.typecode" value="%{dicttype.typecode}" chname="类别编码" nullable="no" maxlength="20" />
		<span class="redfont">*</span>
		</td>
	</tr>
	<tr>
		<td width="60" class="table_edit_list_left_box">
			类别名称：
		</td>
		<td class="table_edit_list_right_box">
			<s:textfield id="typename" name="dicttype.typename" value="%{dicttype.typename}" chname="类别名称" nullable="no" maxlength="20"/>
			<span class="redfont">*</span>
		</td>
	</tr>
	<tr>
		<td width="60" class="table_edit_list_left_box">
			备注信息：
		</td>
		<td class="table_edit_list_right_box">
			<s:textarea id="remark" name="dicttype.remark" value="%{dicttype.remark}" cols="17" rows="3"
			/>
			
		</td>
	</tr>
	<tr>
		<td width="60" class="table_edit_list_left_box">
			显示顺序：
		</td>
		<td class="table_edit_list_right_box">
			<s:textfield id="typesort" name="dicttype.typesort" value="%{dicttype.typesort}"  datatype="number"  maxlength="10"
			chname="显示顺序" 
			/>
		</td>
	</tr>

</table>
 <table class="table_action">
		<tr>
			<td align="center">
				<span id="btnspan" onClick="winSub()"><img id="btnimg" align="middle" alt="提交" src="<%= baseurl%>images/whole_submit.gif"/>提交</span>
				<span id="btnspan" onClick="winClose()"><img id="btnimg" align="middle" alt="关闭" src="<%= baseurl%>images/whole_cancel.gif"/>关闭</span>
			</td>
		</tr>
	</table>
	</s:form>
</div>
</BODY>
</HTML>

