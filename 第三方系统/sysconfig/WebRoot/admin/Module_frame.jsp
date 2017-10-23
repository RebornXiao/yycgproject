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
		<link rel="stylesheet" id="ext-all-css" type="text/css"
			href="<%=path%>/vcomframe/ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
	</head>

	<body>
		<script type="text/javascript" id="ext-base-js"
			src="<%=path%>/vcomframe/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" id="ext-all-js"
			src="<%=path%>/vcomframe/ext/ext-all.js"></script>
		<script type="text/javascript">
	    	Ext.BLANK_IMAGE_URL = 'vcomframe/images/s.gif';
			Ext.onReady(function(){
					var west=new Ext.Panel({
			        region:"west",
			        width:200,
			        minSize:0,
			        maxSize:200,
			        split:true,        
			        collapsible: true,
			        title:"所有菜单",
			        autoScroll:true,
			        items:new Ext.BoxComponent({
							el: 'east',
							height:'100%'
						}),
			        collapsed:false
			    });
				_center=new Ext.Panel({
						region:'center',
			            title: null,
			            collapsible: true,
			            split:true,
			            width: 225,
			            minSize: 175,
			            maxSize: 300,
			            layout:'fit',
			            margins:'0 0 0 0',
			            items:new Ext.BoxComponent({
							el: 'center',
							height:'100%'
						})    
				});
				var viewport = new Ext.Viewport({
					layout:"border",
					items:[_center,west]
				});
			});
			
				var menutype='<s:property value="menutype" />';
		</script>

		<div id="center">
			<iframe topmargin="0" leftmargin="0" id="centerframe" scrolling="no"
				marginheight="0" marginwidth="0" style="width: 100%; height: 100%"
				src="<s:if test="1==menutype"><%=path%>/admin/getModuleListByParentid.action?parentid=-1&depth=1&mid=<%=request.getParameter("mid")%></s:if><s:else><%=path%>/admin/Module_tip.jsp</s:else>" frameborder="0"></iframe>
		</div>
		<div id="east">
			<iframe topmargin="0" leftmargin="0" id="moduletree" scrolling="auto"
				marginheight="0" marginwidth="0" style="width: 100%; height: 100%"
				src="<%=path%>/admin/getModuleTreeUI.action?menutype=<s:property value="menutype" />&mid=<%=request.getParameter("mid")%>"
				frameborder="0"></iframe>


		</div>
	</body>
</html>
