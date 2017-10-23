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
		var codelength = '${codelength}';
		
		function codecheck(str){
		  var result = true;
		   var reg = /^[\a-zA-Z0-9\.]*$/;
		   var r = str.match(reg);
		   if(r==null){
		      result = false;
		      return result;
		   }
		   return result;
		}
		//验证
		function checkForm(){
			var dictcode = trim(jqueryGetEleValById('dictcode'));
			jqueryGetEleById('dictcode').val(dictcode);
			if(codelength != null && codelength != ''){
				if(!codecheck(dictcode)){
					alert("代码只能为数值和字母");
					return false;
				}
				if(dictcode.length != codelength){
					alert("代码须为"+codelength+"位");
					return false;
				}
			}
			return true;
		}
		//提交
		function winSub(){
			if(verifyAll(document.savedicttypeinfo)&&checkForm()){
		      jquerySubByFId("savedicttypeinfo",winSubBack,null);
		    }
		}
		//提交回调函数
		function winSubBack(responseText){
			extAlertCall("系统提示",responseText,alertbackfn,responseText);
		}
		//alert回调函数
		function alertbackfn(responseText){
		 var typecode = jqueryGetEleById("typecode").val();
			if(responseText.indexOf("成功") != -1){
				parent.searchcenterSub(typecode);
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
 <s:form id="savedicttypeinfo" name="savedicttypeinfo" action="savedicttypeinfo" method="post" theme="simple">
 <s:hidden name="method" value="%{method}"/>
 <s:hidden id="typecode" name="typecode" value="%{typecode}"/>
 <s:hidden name="dictinfo.id" value="%{dictinfo.id}"/>
 <s:hidden name="dictinfo.isenable" value="%{dictinfo.isenable}"/>

<table class="table_edit_list">
	<!-- 
	<tr>
		<td width="60" class="table_edit_list_left_box">
			类别：
		</td>
		<td class="table_edit_list_right_box">
		   <s:property value="%{dictinfo.typename}"/>
					</td>
	</tr>
	 -->
    <tr>
		<td width="60" class="table_edit_list_left_box">
			<s:property value="%{dictinfo.typename}"/>：
		</td>
		<td class="table_edit_list_right_box">
			<s:textfield id="info" name="dictinfo.info" value="%{dictinfo.info}" 
			chname="名称/值" nullable="no" maxlength="60"  maxsize="60"/>
		<span class="redfont">*</span>	
		</td>

	</tr>
	<s:if test="codelength != null && codelength != ''">
		<tr>
			<td width="60" class="table_edit_list_left_box">
				代码：
			</td>
			
			<td class="table_edit_list_right_box">
				<s:textfield id="dictcode" name="dictinfo.dictcode" value="%{dictinfo.dictcode}" chname="代码" nullable='no'/>
				<span class="redfont">*</span>
			</td>
		</tr>
	</s:if>
	<s:else>
		<s:hidden id="dictcode" name="dictinfo.dictcode" value="%{dictinfo.dictcode}"/>
	</s:else>

	

	<tr>
		<td width="60" class="table_edit_list_left_box">
			显示顺序：
		</td>
		<td class="table_edit_list_right_box">
			<s:textfield name="dictinfo.dictsort" chname="显示顺序" datatype="number" nullable='no' maxsize="20"/>
			<span class="redfont">*</span>
		</td>
	</tr>
	<tr>
		<td width="60" class="table_edit_list_left_box">
			备注：
		</td>
		<td class="table_edit_list_right_box">
			<s:textarea id="remark" name="dictinfo.remark" value="%{dictinfo.remark}" cols="17" rows="3" chname="备注信息"   maxsize="200"/>
		</td>
	</tr>
</table>
 <table class="table_action">
		<tr>
			<td align="center">
				<!--<span id="btnspan" onClick="winSub()"><img id="btnimg" align="middle" alt="提交" src="<%= baseurl%>images/whole_submit.gif"/>提交</span>
				<span id="btnspan" onClick="winClose()"><img id="btnimg" align="middle" alt="关闭" src="<%= baseurl%>images/whole_cancel.gif"/>关闭</span>  -->
			    <input type="button" class="button" value="提交" onclick="winSub();" />
				<input type="button" class="button" value="关闭" onclick="winClose();" />
			</td>
		</tr>
	</table>
	</s:form>
</div>
</BODY>
</HTML>

