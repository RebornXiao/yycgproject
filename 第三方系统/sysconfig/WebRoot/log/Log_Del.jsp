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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/css/common.css" />

		<link rel="stylesheet" id='skin' type="text/css"
			href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />

		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>


		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/DatePicker/WdatePicker.js"></script>
		<base target="_self">
	</head>
	<body scroll="auto">
		<form action="<%=path%>/log/deleteLogBatch.action"
			onsubmit="return checkinput();">
			<table width="100%">
				<TR>
					<td>
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								删除日志
							</caption>
							<colgroup>
								<col width="20%"></col>
								<col width="20%"></col>
								<col width="25%"></col>
								<col width="35%"></col>
							</colgroup>
							<tr>
							<tr>
								<td class="labelTd" colspan="2">
									请填写日期
									<span class="star">*</span>
								</td>
								<td colspan="2">
									<input
										onclick="WdatePicker({maxDate:'<s:property value="startTime"/>'})"
										class="Wdate" readonly="readonly" type="text" name="endTime"
										value="" id="endTime" />

								</td>

							</tr>
							<tr>

								<td colspan="4">
									<span class="star">注意：</span>
									<br>
									1.该日期之前（包括该日期）的所有日志将都会被删除！
									<br>
									2.只能删除距今一个月前的日志；

								</td>

							</tr>

							<tr>
								<td colspan="4" align="center">
									<input type="submit" class="button" value="[删 除]">
									<input type="button" class="button" value="[重 置]"
										onclick="resetform();">
									<input type="button" class="button" value="[关 闭]"
										onclick="window.close();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</form>
	</body>
	<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
	<script type="text/javascript"
		src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
	<script type="text/javascript"
		src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
	<script type="text/javascript">
            
			function checkinput(){
			
			 // alert();
			    if(!CheckNotNull(document.getElementById("endTime"),"日期"))
			       return false;
			   
			    
			     return true;
			}
			
			function resetform(){
			  // alert();
			   document.getElementById("endTime").value="";
			 
		 
 
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
</html>
