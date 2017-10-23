<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/ajax/jquery.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript">
		  function saveCheck(){
		    if(!CheckNotNull(document.getElementById("oldpassword"),"原始密码")){return false};
		    if(!CheckNotNull(document.getElementById("password"),"新密码")){return false};
		    if(!CheckNotNull(document.getElementById("checkpassword"),"确认密码")){return false};
		    if(!checkPassword()) return false;
            save();
		  }

          function checkPassword(){
             var password=document.getElementById("password").value;
             var checkPassword=document.getElementById("checkpassword").value;
             if(password.length<6 || password.length>18){
             	ymPrompt.alert("密码长度要在6~18位!",null,null,'警告',null)
                document.getElementsByName("password").value="";
                document.getElementsByName("checkpassword").value="";
                return false;
             }
             if(password!=checkPassword){
                ymPrompt.alert("两次输入密码不一致!",null,null,'警告',null)
                document.getElementsByName("password").value="";
                document.getElementsByName("checkpassword").value="";
                return false;
             }else
                return true;
          }
           
		  function save(){
		        userForm.action = "<%=path%>/admin/editPwd.action";
				userForm.submit();
		  }
        </script>
       <base target="_self">
	</head>
	<body scroll="auto">
	 <s:form name="userForm" namespace="/admin" theme="simple">
		
			<table width="100%">
				<TR>
					<td>
						<table align="center" width="100%" class="TableForm"
							cellpadding="2" cellspacing="0">
							<caption>
								修改密码
							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="35%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									原始密码
								</td>
								<td>
									<s:password id="oldpassword" name="oldpassword"  maxLength="50"/>
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									新密码
								</td>
								<td>
									<s:password id="password" name="user.password"  maxLength="50"/>
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									新密码确认
								</td>
								<td>
									<s:password id="checkpassword" name="checkpassword"  maxLength="50"/>
								</td>

							</tr>
							
							<tr>
								<td colspan="2" align="center">
									<input type="button" class="button" value="[提 交]"
										onclick="saveCheck();">
								    <input type=button class="button" value="[关 闭]"
										onclick="closeWin();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<s:hidden name="user.id"/>
	</s:form>
	</body>
	<script type="text/javascript">
	   <%
	       String message=(String)request.getAttribute("message");
	       Boolean isSuccess=(Boolean)request.getAttribute("isSuccess");
	       if(null!=isSuccess){
	          if(isSuccess)
	              out.println("ymPrompt.succeedInfo('"+message+"',200,150,'提示',closeWin)");
	          else
	              out.println("ymPrompt.errorInfo('"+message+"',200,150,'提示',null)");
	       }
	     //  if(null==message){
	       //}else if(message=="true"){
			 //   out.println("ymPrompt.succeedInfo('修改密码成功！',200,150,'提示',closeWin)");
	    //   }else{
			//    out.println("ymPrompt.errorInfo('修改密码失败！',200,150,'提示',null)");
	      // }
	   %>
	   function closeWin(tp){
	   		window.close();
	   }

	</script>
</html>

