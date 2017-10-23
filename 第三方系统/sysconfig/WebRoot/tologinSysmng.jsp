<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="org.logicalcobwebs.proxool.Vcom_3DES"%>
<%@page import="zzvcom.sys.pojo.VcomSysUser"%>
<html> 
<head>
    <title></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
</head>
<body >
<%
	String aid = request.getParameter("aid");
	if(aid == null || aid.equals("")){
		response.sendRedirect("error.jsp"); 
	}
	String deskey = "123456789012345678901234";
	org.logicalcobwebs.proxool.Vcom_3DES desDeObj= new org.logicalcobwebs.proxool.Vcom_3DES(0, aid, deskey);
	String desDeStr = desDeObj.Vcom3DESChiper();
	if((System.currentTimeMillis() - Long.parseLong(desDeStr))/1000 > 30) {
		response.sendRedirect("error.jsp");
	}
	String timestamp = request.getParameter("timestamp");
	if(!timestamp.equals(desDeStr)) {
		response.sendRedirect("error.jsp");
	}
	String userid = request.getParameter("userid");
	String username = request.getParameter("username");
	String roleid = request.getParameter("roleid");
	String group = request.getParameter("group");
	
	System.out.println("===========================group=" + group);
	System.out.println("===========================userid=" + userid);
	if(group.equals("1") || group.equals("2")) {
		zzvcom.sys.pojo.VcomSysUser user = new zzvcom.sys.pojo.VcomSysUser();
		user.setUsercode(userid);
		user.setUsername(username);
		user.setRole(roleid);
		user.setGroupid(group);
		user.setSysid(userid);
		session.setAttribute("userInfo",user);
		session.setAttribute("userName",username);
		System.out.println("===========================session");
		System.out.println("===========================session");
		System.out.println("===========================session");
		System.out.println("===========================session");
		System.out.println("===========================session");
	}
 %>
</body>
</html>