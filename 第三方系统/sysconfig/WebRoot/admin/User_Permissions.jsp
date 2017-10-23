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
		<title>用户分配权限</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=path %>/vcomframe/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<link rel="stylesheet" id="ext-all-css" type="text/css" href="<%=path %>/vcomframe/ext/resources/css/ext-all.css" />
		<style>
		.delete-icon {
		    background-image:url(<%=path %>/vcomframe/images/rss_delete.gif) !important;
		}
		.load-icon {
		    background-image:url(<%=path %>/vcomframe/images/rss_load.gif) !important;
		}
		.add-feed {
		    background-image: url(<%=path %>/vcomframe/images/rss_add.gif) !important;
		}
		</style>
		<base target="_self">
	</head>

	<body>
		<div id="loading-mask"></div>
		<div id="loading">
			<div class="loading-indicator">
				<img src="<%=path %>/vcomframe/images/extanim32.gif" width="32" height="32"
					style="margin-right:8px;" align="absmiddle" />
				页面加载中，请稍后...
			</div>
		</div>
		<script type="text/javascript" src="<%=path %>/vcomframe/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=path %>/vcomframe/ext/ext-all.js"></script>
	    <script type="text/javascript" src="<%=path %>/vcomframe/ext/windows.js"></script>
	    <script type="text/javascript" src="<%=path %>/vcomframe/ext/tree.js"></script>
	    <script type="text/javascript">
			var id=100;
			Ext.onReady(function(){
				var south=new Ext.Panel({
			        region:"south",
			        height: '300',
			        minSize:0,
			        maxSize:300,
			        split:true,        
			        //collapsible: true,
			        title:"",
			        autoScroll:true,
			        items:new Ext.BoxComponent({
							el: 'south',
							height:'100%'
						}),
			        collapsed:false
			    });
				var center=new Ext.Panel({
						region:'center',
			            title: '用户权限',
			            //collapsible: true,
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
					items:[center,south]
				});
			});
		</script>
		<div id="center">
		
		<iframe src="<%=path %>/admin/role/getPlatUserModule.action?userid=<s:property value="user.id"/>" id="iframes" name="iframes" frameborder="0" height="100%" width="100%"></iframe>
		
		
		</div>
		<div id="south">
			<form name="zzvcom" onSubmit="return checkinput();" method="post" onSubmit="return checkinput();">
			<input type="hidden" name="user.id" value="<s:property value="user.id"/>">
			<input type="hidden" name="user.permissions" id="permissions" value="<s:property value="user.permissions"/>">
			<center>
				<input type="button" class="button" value="[保 存]" onclick="checkinput();">
				&nbsp;
				<input class="button" id="resetNote" type="button" value="[关 闭]" onclick="window.close();">
			</center>
			</form>
		</div>
		<script type="text/javascript">
			

            var operFlag=<s:property value="operFlag"/>;
			function checkinput(){
				saverole();
			    
			}
		  function saverole(){
		     var checkinput=iframes.checkboxtree.getCheckedValue();
               document.getElementById("permissions").value=checkinput;
			    //alert(checkinput);
			       
			    zzvcom.action="<%=path%>/admin/role/updateUserPermissions.action";
			    zzvcom.submit();
				return true;
		  }
			
		 <%
			Boolean isSucc=(Boolean)request.getAttribute("isSucc");
			String message=(String)request.getAttribute("message");
			
			 
			 
			if(null==isSucc) {
			    
			}else if(isSucc){
			
				out.println("window.returnValue=true");
				out.println("window.close();");
			}else {
			    out.println("window.returnValue=false");
			    out.println("ymPrompt.errorInfo('"+message+"',null,null,'提示',null)");
			   
				//out.println("alert(\""+message+"\")");
			}
	     %>
</script>
	</body>
</html>

