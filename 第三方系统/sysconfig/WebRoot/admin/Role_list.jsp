<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="t" uri="/WEB-INF/tld/button.tld"%>
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
			<script type="text/javascript" src="<%=path%>/vcomframe/list/ajax/jquery.js"></script>
	</head>
	<body scroll="auto">
		<form name="form1" action="<%=path%>/admin/role/getRoleSearch.action"
			method="post">
			<table width="100%">

				<TR>
					<td>
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								角色查询
							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="55%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									角色名称
								</td>
								<td>
									<s:textfield name="roleobj.rolename" maxlength="50"
										id="rolename"></s:textfield>
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
								角色列表
							</caption>
							<tr>
								<td class="freeBar" colspan="8">
									<t:button buttiontype="1"/>
								</td>
							</tr>
							<!-- 每列所占的比例 -->
							<colgroup>
								<col width="4%"></col>
								<col width="5%"></col>

								<col width="21%"></col>
								<col width="20%"></col>
								<!-- <col width="6%"></col>-->
								<col width="10%"></col>
								<col width="10%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="head">
									&nbsp;
								</td>
								<td class="head">
									
								</td>
								<td class="head">
									角色名称
								</td>
								<td class="head">
									所属平台
								</td>
								<!-- 
								<td class="head">
									排序号
								</td>
								 -->
								<td class="head">
									创建时间
								</td>
								<td class="head">
									修改时间
								</td>
								<td class="head">
									备注
								</td>
							</tr>
							<s:iterator value="roleList" id="roleList" status="st">
								<tr onmouseover="changecolor(this,1);"
									onmouseout="changecolor(this,2);"
									<s:if test="!#st.odd">class="custom"</s:if>>
									<td class="changecol">
										<s:property value="#st.index+1" />
									</td>
									<td class="changecol">
										<input name="checkboxId" type="checkbox" id="checkboxId"
											onclick="uncheck(this,this.form)" class="checkbox" value="<s:property value="id" />">
									</td>
									<td class="changecol">
										<s:property value="rolename" />
									</td>
									<td class="changecol">
										<s:if test="type==1">
											省平台
										</s:if>
										<s:elseif test="type==2">
										    学习中心平台
										</s:elseif>
										<s:elseif test="type==3">
										    学生平台 
										</s:elseif>
										<s:elseif test="type==0">
										    系统管理平台 
										</s:elseif>
										<s:else>
										&nbsp;
										</s:else>

									</td>
									<!-- 
									<td class="changecol">
										<s:property value="sort" />
									</td> -->
									<td class="changecol">
										<s:date name="createtime" format="yyyy-MM-dd" />
										&nbsp;
									</td>
									<td class="changecol">
										<s:date name="updatetime" format="yyyy-MM-dd" />
										&nbsp;
									</td>
									<td class="changecol">
										<s:property value="remark" />
										&nbsp;
									</td>
								</tr>
							</s:iterator>
							<tr>
								<td colspan="7" align="right" class="head">
									<s:property value="#request.pageBar" escape="false" />
								</td>
							</tr>
						</table>

					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
		//用户检索
		function search(){
			form1.submit();
		}
		//重置检索表单
		function resetfrom(){
			form1.rolename.value="";
		}
		
		 var actionname="";
	     var window_parameter="dialogWidth:650px;dialogHeight:380px;help:no;scroll:auto;status:no";
			//添加 
			function toAddPage(){
			 
			    //alert(window.dialogArguments);
			    actionname="<%=path%>/admin/role/toSaveOrUpdateRolePage.action";
			 
			    
				var handle = showModalDialog(actionname,window,window_parameter);
				
				
				if(handle){
				    document.forms(0).pageIndex.value=1;//设置为第一页
				    form1.action="<%=path%>/admin/role/getRoleSearch.action";
					form1.submit();
				}
			}
			//修改
			function toEditPage(){
			    var num=getCheckBoxSelectNumber(form1.checkboxId);
			    //alert(getCheckBoxvalue(contest.checkboxId));
			    if(num>1){
			          ymPrompt.alert('每次只能修改一条记录！',null,null,'提示',null);
					  return false;
				}else if(num==0){
				     ymPrompt.alert('请首先选择要修改的记录！',null,null,'提示',null);
					return false;
				}else{
				  $.get("<%=path%>/admin/role/roleEditCheck.action",{roleid:getCheckBoxvalue(form1.checkboxId)},updatedo); 
				  
				  
				   
				}
				
				 
			}
			function updatedo(xml){
		        if(xml=="false"){
                  ymPrompt.alert("系统初始角色不允许修改!",null,null,'警告',null)
                  return false;
                }
                actionname="<%=path%>/admin/role/toSaveOrUpdateRolePage.action?";
			       actionname+="operFlag=1";
				   actionname+="&roleid="+getCheckBoxvalue(form1.checkboxId);
				   var handle = showModalDialog(actionname,window,window_parameter);
				   if(handle){
				     
				     form1.action="<%=path%>/admin/role/getRoleSearch.action";
					 form1.submit();
					  
				   }
		  }
				function del(){
			    
			    var num=getCheckBoxSelectNumber(form1.checkboxId);
			     
			    if(0==num || num>1){
			        ymPrompt.alert('请选择一条记录进行删除！',null,null,'提示',null);
					 
					return false;
				}else {
					
					$.get("<%=path%>/admin/role/roleDeleteCheck.action",{roleid:getCheckBoxvalue(form1.checkboxId)},deldo); 
					
				}
			
			}//del
			 function deldo(xml){
		        if(xml=="false"){
                  ymPrompt.alert("系统初始角色或已分配角色不允许删除!",null,null,'警告',null)
                  return false;
                }
                
	            ymPrompt.confirmInfo('确定要删除该记录吗？',null,null,null,handler);
		  }
			function handler(tp){
			   if(tp=='ok'){
			       //alert("&&");
			       form1.action="<%=path%>/admin/role/deleteRole.action";
				   form1.submit();
				  
			   }else
			     return false;
		}
	</script>
</html>