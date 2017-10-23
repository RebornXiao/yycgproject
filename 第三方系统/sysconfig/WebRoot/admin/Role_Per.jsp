<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>customtree.html</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=path %>/vcomframe/css/main.css"/>
		<link rel="stylesheet" id="ext-all-css" type="text/css" href="<%=path %>/vcomframe/ext/resources/css/ext-all.css" />
	</head>

	<body>
		<div id="loading-mask"></div>
		<div id="loading">
			<div class="loading-indicator">
				<img src="<%=path %>/vcomframe/images/extanim32.gif" width="32" height="32"
					style="margin-right:8px;" align="absmiddle" />
				加载中.
			</div>
		</div>
		<script type="text/javascript" src="<%=path %>/vcomframe/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=path %>/vcomframe/ext/ext-all.js"></script>
	    <script type="text/javascript" src="<%=path %>/vcomframe/ext/windows.js"></script>
	    <script type="text/javascript" src="<%=path %>/vcomframe/ext/tree.js"></script>
	    <script type="text/javascript" src="<%=path %>/vcomframe/ext/tree-expend-checkbox.js"></script>
	    <script type="text/javascript" src="<%=path %>/vcomframe/ext/ComboBox.js"></script>
		<script>

			var treejson =<s:property value="permissionjson" escape="false"/>;
			//var treecheckpanel=new treecheckpanel('tree-panel',"地区",200,250,"cascade",treejson,1,null,null,'cascade',false,'treecheckpanel');
			//function getvalue(){
				//alert(treecheckpanel.getCheckedValue());
			//}
			var checkboxtree=new checkboxtree('tree-panel',1,treejson,null,null,'cascade',false,null);

			window.setTimeout(function(){
				checkboxtree.tree.expandAll();
		    //checkboxtree.tree.collapseAll();
			},1000)
			
		</script>
		<div id="tree-panel">
			
		</div>
	</body>
</html>
