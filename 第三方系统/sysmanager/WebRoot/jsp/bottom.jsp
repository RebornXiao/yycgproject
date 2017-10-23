<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>    
 <META http-equiv=Content-Type content="text/html; charset=utf-8">
 <title>bottom</title> 
<style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
-->
</style></head>
<body>
<table width="100%" height="26" border="0" align="center" cellpadding="0" cellspacing="0" background="../image/down_bg.gif">
  <tr>
    <td width="18%" align="center" valign="bottom">
    <table width="98%" height="24" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="15%"><img src="../image/icons/user.png" width="16" height="16"> 当前用户：${sessionScope.user.username}</td>
        <td width="15%"><img src="../image/icons/group_key.png" width="16" height="16">所属角色：${sessionScope.role.actorname}</td>
        <td width="20%" align="right">
        <script language="javascript">
			tmpDate = new Date();
			date = tmpDate.getDate();
			month= tmpDate.getMonth() + 1 ;
			year= tmpDate.getYear();
			//FireFox计算时间兼容
			year = (year<1900?(1900+year):year);
			document.write(year);
			document.write("年");
			document.write(month);
			document.write("月");
			document.write(date);
			document.write("日 ");
			
			myArray=new Array(6);
			myArray[0]="星期日"
			myArray[1]="星期一"
			myArray[2]="星期二"
			myArray[3]="星期三"
			myArray[4]="星期四"
			myArray[5]="星期五"
			myArray[6]="星期六"
			weekday=tmpDate.getDay();
			if (weekday==0 | weekday==6)
			{
			document.write(myArray[weekday])
			}
			else
			{document.write(myArray[weekday])
			};

    </script>
      <img src="../image/icons/clock.png" width="16" height="16"></td>
      </tr>
    </table></td>
  </tr>
</table>



</body>
</html>
