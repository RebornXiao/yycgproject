<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/base.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="<%=basePath%>">
        <title>操作超时</title>
        <link rel="stylesheet" type="text/css" href="css/custom.css" />
        <link rel="stylesheet" type="text/css" href="js/ext/css/ext-all.css" />
        <script defer="defer" type="text/javascript" src="js/ext/ext-base.js"></script>
        <script defer="defer" type="text/javascript" src="js/ext/ext-all.js"></script>
        <script defer="defer" type="text/javascript" src="js/ext/ext-lang-zh_CN.js"></script>
        <script defer="defer" type="text/javascript">
            function init() {
                Ext.BLANK_IMAGE_URL = 'image/s.gif';
                var panel = new Ext.Panel({
                    title:'提示信息',
                    frame:true,
                    width:200,
                    height:100,
                    html:'&nbsp;&nbsp;&nbsp;<img src="js/ext/images/default/window/icon-warning.gif">&nbsp;操作超时, 请重新登录',
                    renderTo:'error'
                });
                panel.el.center();
            }
        </script>
    <body onLoad="init()">
    <div id="error"></div>
    </body>
</html>
