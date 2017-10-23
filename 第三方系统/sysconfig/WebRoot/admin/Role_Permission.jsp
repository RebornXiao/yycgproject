<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="t" uri="/WEB-INF/tld/button.tld"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
  <head>
  <title>角色权限管理</title>
  </head>
     <link rel="stylesheet" type="text/css"href="<%=path%>/vcomframe/css/common.css" />
     <link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
     <script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
     <script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
  <body>
 	<form name="form1" action="<%=path %>/admin/role/saveOrUpdatePermissions.action" target="subtarget">
 	<table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0">
		<caption>
			角色权限分配
		</caption>
		<colgroup>
			<col width="15%"></col>
			<col width="35%"></col>
			<col width="15%"></col>
			<col width="35%"></col>
		</colgroup>
		<tr>
			<td class="labelTd">
				角色名称<span class="star">*</span>
			</td>
			<td>
				<select name="id" onchange="changepro(this.value)">
					<option value="">---请选择--- </option>
					<s:iterator id="roleList" value="roleList" status="st">
						<option value="<s:property value="#roleList.id" />"><s:property value="#roleList.rolename" /></option>
					</s:iterator>
				</select>
				<input type="hidden" name="leftTree">
				<input type="hidden" name="rightTree">
			</td>
			<td class="labelTd">
				创建时间
			</td>
			<td>
				<input name="updatetime" value="<s:date name ="roleobj.updatetime" format="yyyy-MM-dd" />" readonly="readonly">
			</td>
		</tr>
	</table>
	
 	<table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0" style="margin-top: 5px;">
		
		<colgroup>
            <col width="25%"></col>
            <col width="20%"></col>
            <col width="10%"></col>
            <col width="25%"></col>
            <col width="20%"></col>
	
		</colgroup>
		
		<tr>
			<td colspan="2" align="right">
	
				<iframe name="left" scrolling="yes" src="<%=path %>/admin/role/getAllPermissionTree.action" width="70%" height="330px" frameborder="1" marginwidth="0"></iframe>
			
			</td>
			<td>
				<center>
					<input type="button" class="button" onclick="javascript:right.leftToRight()" name="pviadd" value="==》">
					<br>
					<br>
					<br>
					<input type="button" class="button" onclick="javascript:left.rightToLeft()" name="pvidelete" value="《==">
				</center>
			</td>
			<td colspan="2">
			
				<iframe name="right" scrolling="yes" src="<%=path %>/admin/role/getPermissionTree.action" width="70%" height="330px" frameborder="1" marginwidth="0"></iframe>
			
			</td>
		</tr>
		<tr>
			
			<td colspan="5" align="center">
				<t:button buttiontype="2"/>
				<input type="button" class="button" value="[重 置]" onclick="changepro(form1.id.value);">
			</td>
		</tr>
	</table>
	</form>
	<iframe name="subtarget" scrolling="yes" src="" marginwidth="0" style="display: none;"></iframe>
	<script>
		function getpro(){
			if(form1.id.value==""){
				ymPrompt.alert('请选择需要添加权限的角色',null,null,'警告',function(){
					return false;
				});
				return ;
			}
			form1.leftTree.value=left.rootTree.getAllOther();
			form1.rightTree.value=right.rootTree.getAllOther();
			form1.submit();
		}
		function changepro(obj){
			left.location="<%=path %>/admin/role/getAllPermissionTree.action?id="+obj;
			right.location="<%=path %>/admin/role/getPermissionTree.action?id="+obj;
		}
	</script>

  </body>
</html>
