<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/list/tree/tree.css" />
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/tree/xtree.js"></script>
	</head>
	<body>
		<script>
		// 1-菜单管理；2-操作管理
		var menutype=parent.menutype;
		    //用户点击站点菜单
		    function openModuleIframe(parentid,depth){
		     
		        if(1==menutype){//展开菜单列表
		            parent.document.getElementById("centerframe").src="<%=path%>/admin/getModuleListByParentid.action?parentid="+parentid+"&depth="+depth+"&mid=<%=request.getParameter("mid")%>";
		        }else if(2==menutype){//给出提示页面,要求用户选择栏目或站点；
		          
		            parent.document.getElementById("centerframe").src="<%=path%>/admin/getOperationListByModuleid.action?parentid="+parentid+"&mid=<%=request.getParameter("mid")%>";
		           // toTipPage();
		        }
		        
				 
			}
		 
		 
			
	 	<s:property value="moduleTree" escape="false"/>
		</script>

	</body>
</html>
