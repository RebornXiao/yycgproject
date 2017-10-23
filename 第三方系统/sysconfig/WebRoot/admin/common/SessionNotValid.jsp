<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String contextPath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登录超时</title>
     
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=contextPath%>/vcomframe/list/message/ymPrompt.js"></script>
	<link rel="stylesheet" id='skin' type="text/css" href="<%=contextPath%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
	<link rel="stylesheet" href="<%=contextPath%>/styles/body.css" type="text/css" />

  </head>
  
  <body>
    <script type="text/javascript">
    function relogin(){
		var parent_proxy;
	   if(parent){
	     parent_proxy = parent;
	   }
	   if(parent_proxy.parent){
	     parent_proxy = parent_proxy.parent;
	   }
	   if(parent_proxy.parent){
	     parent_proxy = parent_proxy.parent;
	   }
	   if(parent_proxy.parent){
	     parent_proxy = parent_proxy.parent;
	   }
	   if(parent_proxy.parent){
	     parent_proxy = parent_proxy.parent;
	   }
	   parent_proxy.window.location='<%=contextPath%>/admin/loginshow.action';
	}
    </script>
    <table cellspacing="0" cellpadding="0" width="100%" class="table_data">
			<tr align="center">
				<td align="center"><font color="red">您的登陆已经超时!请点击下面链接重新登录！</font></td>
			</tr>
			<tr align="center">
				<td align="center"><a href="javascript:relogin()">[重新登录]</a></td>
			</tr>
	</table>
  </body>
</html>
