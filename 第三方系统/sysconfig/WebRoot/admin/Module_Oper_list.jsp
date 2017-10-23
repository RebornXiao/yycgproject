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
<%@ taglib prefix="t" uri="/WEB-INF/tld/button.tld"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>


		<link rel="stylesheet" id='skin' type="text/css"
			href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>

	</head>
	<body scroll="auto">
		<form name="zzvcom" onsubmit="search();" method="post" action="<%=path%>/admin/getOperationListByModuleid.action">
			<!-- 权限按钮所用id -->
			<input type="hidden" name="mid"
				value="<%=request.getParameter("mid")%>">
			<input type="hidden" name="parentid"
				value="<s:property value="parentid"/>">
			<input type="hidden" name="depth" value="<s:property value="depth"/>">
			<table width="100%">

				<TR>
					<td>
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								操作查询
							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="55%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									操作名称
								</td>
								<td>
									<input type="text" name="name" maxlength="25"
										value="<s:property value="name" />" id="opername" />
								</td>
								<td align="center">
									<input type="button" class="button" value="[检 索]"
										onclick="search();">
									<input class="button" id="resetNote" type="button"
										value="[重 置]" onclick="resetfrom()">
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<table class="Table" width="70%" cellpadding="3" cellspacing="0"
							align="center">
							<!-- 表头 -->
							<caption>
								操作列表
							</caption>
							<tr>
								<td class="freeBar" colspan="6">
									<t:button buttiontype="1" />
								</td>
							</tr>
							<!-- 每列所占的比例 -->
							<colgroup>
								<col width="4%"></col>
								<col width="5%"></col>

								<col width="25%"></col>
								<col width="10%"></col>
								<col width="26%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="head">
									&nbsp;
								</td>
								<td class="head">
									<input type="checkbox" id="all" name="all" class="checkbox"
										onclick="checkall(this.checked,this.form)" />
								</td>
								<td class="head">
									操作名称
								</td>
								<td class="head">
									排序号
								</td>
								<td class="head">
									方法
								</td>
								<td class="head">
									备注
								</td>
							</tr>



							<s:if test="null!=operList">
								<s:iterator value="operList" id="operObj" status="st">
									<tr onmouseover="changecolor(this,1);"
										onmouseout="changecolor(this,2);"
										<s:if test="!#st.odd">class="custom"</s:if>>
										<td class="changecol">
											<s:property value="#st.index+1" />

										</td>
										<td class="changecol">
											<input name="checkboxId" type="checkbox" id="checkboxId"
												class="checkbox" value="<s:property value="id" />" onclick="uncheck(this,this.form)">
										</td>
										<td class="changecol">
											<s:property value="opername" />
											&nbsp;
										</td>
										<td class="changecol">

											<s:property value="sort" />
											&nbsp;
										</td>
										<td class="changecol">
											<s:property value="method" />
											&nbsp;
										</td>


										<td class="changecol">
											<s:property value="remark" />
											&nbsp;
										</td>

									</tr>
								</s:iterator>
							</s:if>


							<tr>
								<td colspan="8" align="right" class="head">
									<s:property value="pageBar" escape="false" />

								</td>
							</tr>
						</table>

					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
         function resetfrom(){
			    document.getElementById("opername").value="";
         }
         function search(){
			    document.forms(0).pageIndex.value=1;//设置为第一页
				zzvcom.action="<%=path%>/admin/getOperationListByModuleid.action";
				zzvcom.submit();
	    }
	    var actionname="";
	    var window_parameter="dialogWidth:650px;dialogHeight:380px;help:no;scroll:auto;status:no";
			//添加 
			function toAddPage(){
			    //alert(window.dialogArguments);
			    actionname="<%=path%>/admin/toSaveOrUpdateOperPage.action?";
			    actionname+="moduleid=<s:property value="parentid" />&operFlag=0";
				var handle = showModalDialog(actionname,window,window_parameter);
				if(handle){
				    document.forms(0).pageIndex.value=1;//设置为第一页
				    zzvcom.action="<%=path%>/admin/getOperationListByModuleid.action";
					zzvcom.submit();
					//parent.moduletree.location="<%=path%>/admin/getModuleTreeUI.action?mid=<%=request.getParameter("mid")%>&menutype="+parent.menutype; 
				}
			}
			
			//修改
			function toEditPage(){
			    var num=getCheckBoxSelectNumber(zzvcom.checkboxId);
			    //alert(getCheckBoxvalue(contest.checkboxId));
			    if(num>1){
			          ymPrompt.alert('每次只能修改一条记录！',null,null,'提示',null);
					  return false;
				}else if(num==0){
				     ymPrompt.alert('请首先选择要修改的记录！',null,null,'提示',null);
					return false;
				}else{
				   actionname="<%=path%>/admin/toSaveOrUpdateOperPage.action?";
			       actionname+="moduleid=<s:property value="parentid" />&operFlag=1";
				   actionname+="&id="+getCheckBoxvalue(zzvcom.checkboxId);
				   var handle = showModalDialog(actionname,window,window_parameter);
				   if(handle){
				     
				     zzvcom.action="<%=path%>/admin/getOperationListByModuleid.action";
					 zzvcom.submit();
					 //parent.moduletree.location="<%=path%>/admin/getModuleTreeUI.action?mid=<%=request.getParameter("mid")%>&menutype="+parent.menutype; 
				   }
				}
			}//toEditPage
			
			function del(){
			     var num=getCheckBoxSelectNumber(zzvcom.checkboxId);
			     
			    if(0==num){
			        ymPrompt.alert('请选择要删除的记录！',null,null,'提示',null);
					 
					return false;
				}else {
					ymPrompt.confirmInfo('确定要删除该记录吗？',null,null,null,handler);
					return false;
				}
			
			}//del
			
			function handler(tp){
			   if(tp=='ok'){
			       //alert("&&");
			       zzvcom.action="<%=path%>/admin/deleteOper.action";
				   zzvcom.submit();
				   //parent.moduletree.location="<%=path%>/admin/getModuleTreeUI.action?mid=<%=request.getParameter("mid")%>&menutype="+parent.menutype; 
				//okFn();
			   }else
			     return false;
		}
		 <%
			Boolean isSucc=(Boolean)request.getAttribute("isSucc");
			String message=(String)request.getAttribute("message");
			
			 
			 
			if(null==isSucc) {
			    
			}else if(isSucc){
				//out.println("parent.moduletree.location=\""+request.getContextPath()+"/admin/getModuleTreeUI.action?mid="+request.getParameter("mid")+"&menutype=\"+parent.menutype;");
			}else {
			    out.println("ymPrompt.errorInfo('"+message+"',null,null,'提示',null)");
			   
				//out.println("alert(\""+message+"\")");
			}
	     %>	
</script>
</html>
