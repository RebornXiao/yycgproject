<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.zzvcom.sysmag.util.MyUtil"%>
<%@page import="org.logicalcobwebs.proxool.Vcom_3DES"%>
<%@page import="java.util.HashMap"%>
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
String deskey = MyUtil.getSysConfigValue("deskey");
org.logicalcobwebs.proxool.Vcom_3DES desDeObj= new org.logicalcobwebs.proxool.Vcom_3DES(0, aid, deskey);
String desDeStr = desDeObj.Vcom3DESChiper();
if((System.currentTimeMillis() - Long.parseLong(desDeStr))/1000 > 30) {
	response.sendRedirect("error.jsp");
}
String timestamp = request.getParameter("timestamp");
if(!timestamp.equals(desDeStr)) {
	response.sendRedirect("error.jsp");
}

session.removeAttribute("role");
session.removeAttribute("user");
String userid = request.getParameter("username");
String truename = request.getParameter("truename");
HashMap usermap = new HashMap();
usermap.put("username", userid);
usermap.put("truename", truename);
//System.out.println("============userid=" + userid + "============");
if("admin".equals(userid)) {
	HashMap roleMap = new HashMap();
	roleMap.put("roleid", "0");
	session.setAttribute("role", roleMap);
}
else {
	String roleid = request.getParameter("roleid");
	System.out.println("============roleid=" + roleid + "============");
	HashMap roleMap = new HashMap();
	roleMap.put("roleid", roleid);
	session.setAttribute("role", roleMap);
}
session.setAttribute("user",usermap);
 %>
</body>
</html>