<%@ page contentType="text/html;charset=UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<jsp:useBean id="eWebEditor" class="com.ewebeditor.server.browse" scope="page"/>

<%
eWebEditor.Load(pageContext);
%>