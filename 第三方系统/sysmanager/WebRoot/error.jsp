<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录出错了。。。。</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <style type="text/css">
		body { background-color: #fff; color: #666; font-family:  宋体; }
		div.dialog {

			padding: 0 4em;
			align:center;
			border: 1px solid #ccc;
			border-right-color: #999;
			border-bottom-color: #999;
		}
		h1 { font-size: 100%; color: #f00; line-height: 1.5em; }
	</style>
	<%String type=request.getParameter("type").toString(); %>
  <body>
     <div  class="dialog" align="center">
       <h1>登录超时或者Session过期，请重试</h1>
  </div>
  </body>
</html>
