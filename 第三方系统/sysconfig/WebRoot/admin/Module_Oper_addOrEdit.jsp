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
			src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<base target="_self">
	</head>
	<body scroll="auto">
		<form name="zzvcom" onSubmit="return checkinput();" method="post"
			onSubmit="return checkinput();">


			<input type="hidden" name="operObj.id"
				value="<s:property value="operObj.id"/>">
			<input type="hidden" name="operObj.sort" value="1" id="sort" >


			
			<input type="hidden" name="operFlag"
				value="<s:property value="operFlag"/>">
			<table width="100%">
				<TR>
					<td>
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								<s:if test="0==operFlag">操作新增
								
								<input type="hidden" name="operObj.moduleid"
										value="<s:property value="moduleid"/>">
									
								</s:if>
								<s:else>操作修改
								<input type="hidden" name="operObj.moduleid"
										value="<s:property value="operObj.moduleid"/>">
								<input type="hidden" name="operObj.createtime"
										value="<s:property value="operObj.createtime"/>">
								
								</s:else>

							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="40%"></col>
								<col width="15%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									操作名称
									<span class="star">*</span>
								</td>
								<td colspan="3">
									<input type="text" style="width:44%" name="operObj.opername" maxlength="25" id="opername" 
										value='<s:property value="operObj.opername"/>'
										id="opername" />
									<!-- 
									快速选择：<select style="width:43%" onchange="zzvcom.opername.value=this.value">
										<option value="">--请选择--</option>
										<option value="添加">添加</option>
										<option value="修改">修改</option>
										<option value="删除">删除</option>
									</select>
									 -->
								</td>
							</tr>
							<!-- 
							<tr>
								<td class="labelTd">
									排序号
									<span class="star">*</span>
								</td>
								<td colspan="3">
									<input type="text" name="operObj.sort" maxlength="50" onchange="this.value=this.value.replace(/\s/g,'')"
										value="<s:property value="operObj.sort"/>" id="sort" />
								</td>
							</tr>
							 -->
							<tr>
								<td class="labelTd">
									url<span class="star">*</span>
								</td>
								<td colspan="3">
									<input type="text" style="width:44%" name="operObj.method" id="method"
										value="<s:property value="operObj.method"/>" />
									<!-- 
									快速选择：<select style="width:43%" onchange="zzvcom.method.value=this.value">
										<option value="">--请选择--</option>
										<option value="todel">todel</option>
										<option value="toadd">toadd</option>
										<option value="toEdit">toEdit</option>
									</select>
									 -->
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									按钮图片
								</td>
								<td colspan="3">
									<input type="text" name="operObj.opercode" maxlength="50"
										value="<s:property value="operObj.opercode"/>" />
								</td>

							</tr>
							<tr>
								<td class="labelTd">
									备注
								</td>
								<td colspan="3">
									<input type="text" name="operObj.remark" maxlength="50"
										value="<s:property value="operObj.remark"/>" />
								</td>

							</tr>

							<tr>
								<td colspan="4" align="center">
									<input type="button" class="button" value="[保 存]"
										onclick="checkinput();">
									&nbsp;
									<input class="button" id="resetNote" type="button"
										value="[关 闭]" onclick="window.close();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
            var operFlag=<s:property value="operFlag"/>;
			function checkinput(){
			    if(!CheckNotNull(document.getElementById("opername"),"操作名")){return false};
			    if(!CheckNotNull(document.getElementById("method"),"url")){return false};
			    if(!CheckNotNull(document.getElementById("sort"),"排序号")){return false};
			    if(!CheckNum(document.getElementById("sort"), "排序号", 0, 10000)) return false;
					
					
					
			   // if(!CheckNotNull(document.getElementById("sort"),"排序号")){return false};
			    
			    var actionname="";
			    if(0==operFlag)
			       actionname="<%=path%>/admin/saveOper.action";
			    else
			       actionname="<%=path%>/admin/updateOper.action";
			       
			    zzvcom.action=actionname;
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
</html>
