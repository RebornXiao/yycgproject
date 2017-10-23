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
			href="<%= path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/ajax/jquery.js"></script>
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<script type="text/javascript"
			src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<link rel="stylesheet" id='skin' type="text/css"
			href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript">
			
			function groupchange(this_){
				flag = this_.value;
				groupid = flag;
				var systext_obj=document.getElementById('systext');
			    var sysinput_obj=document.getElementById('sysinput');
			    sys_name="";
			    sysinput_obj.innerHTML="";
			    systext_obj.innerHTML="";
				if(flag == "2"){
					sysinput_obj.innerHTML='<s:textfield id="user.sysid" name="user.sysid" maxLength="50"/>';
					sys_name="学习中心代码";
				   	systext_obj.innerHTML=sys_name+'<span class="star">*</span>';
				}
			}
			
			function phonecheck(str){
			  	var result = true;
			   	var reg = /^[\0-9\-]*$/;
			   	var r = str.match(reg);
			   	if(r==null){
			    	result = false;
			    	return result;
			   	}
			   	return result;
			}
		  function updateCheck(){
		     //if(!CheckNotNull(document.getElementById("usercode"),"用户代码")){return false};
		     if(!CheckNotNull(document.getElementById("username"),"用户名称")){return false};
		     if(!CheckNotNull(document.getElementById("sex"),"性别不允许为空")){return false}; 
		     var phone = document.getElementById("phone").value;
		  	if(phone != ""){
		  		if(!phonecheck(phone)){
			    	ymPrompt.alert("联系方式只能是数字、横杠!",null,null,'警告',null);
			    	return false;
			    }
		  	}
		  	var remark = document.getElementById("remark").value;
		  	if(remark.length>300){
		    	ymPrompt.alert("备注不能超过300字符!",null,null,'警告',null);
		    	return false;
		    }
		      if(document.getElementById("user.sysid") && !CheckNotNull(document.getElementById("user.sysid"),"请输入学习中心代码")){return false};
		     
		     
		     $.get("<%=path%>/admin/delCheck.action",{ids:'<s:property value="user.id"/>'},updatedo); 
		     
		     
		    /*
		     var roles=document.all.dltTarget;
             if(roles.length<1){
                ymPrompt.alert("请分配角色!",null,null,'警告',function(){objField.focus(); })
                return false;
             }
             selectAll(roles);
             var oldvalue=document.getElementById("oldcode").value;
             var usercode=document.getElementById("usercode").value;
             if(oldvalue==usercode){
                update();
             }else{
                codeCheck();
             }*/
             
		     
		   }
		    function updatedo(xml){
		        if(xml=="false"){
                  ymPrompt.alert("系统初始用户不允许修改!",null,null,'警告',null)
                  return false;
                }
	            update();
		  }
		 function codeCheck(){
		    var usercode=document.getElementById("usercode").value;
			$.get("<%=path%>/admin/codeCheck.action",{usercode:usercode},rend); 
		 }
		 function rend(xml){ 
　　			   if(xml=="true"){
                  ymPrompt.alert("用户代码重复!",null,null,'警告',null)
                  return false;
                }
                update();
		  }
		  function update(){
		  var checkinput=iframes.checkboxtree.getCheckedValue();
               document.getElementById("permissions").value=checkinput;
		        userAddForm.action = "<%=path%>/admin/updateUser.action";
				userAddForm.submit();
		  }
		  function resetForm(){
		       
		  }

        </script>
		<script language="javascript">   
         function   add(ObjSource,ObjTarget){
				  for(var   i=ObjSource.length-1;i>=0;i--){   
				     if(ObjSource.options[i].selected){   
				        var   opt=document.createElement("OPTION");   
				        ObjTarget.add(opt);   
				        opt.value=ObjSource.options[i].value;   
				        opt.text=ObjSource.options[i].text;   
				        ObjSource.options.removeChild(ObjSource.options[i]);   
				        opt.selected=true;   
				     }   
				  }   
           }   
		  function   addAll(ObjSource,ObjTarget){   
				  selectAll(ObjSource);   
				  add(ObjSource,ObjTarget);   
			 }   
		  function   selectAll(ObjSource){   
		      for(var   i=0;i<ObjSource.length;i++){   
		         ObjSource.options[i].selected=true;   
		       }   
		  }
function resultUrl(){
				window.location = "getAllUserList.action";
		}
  </script>
	</head>
	<body scroll="auto">
		<s:form name="userAddForm" namespace="/admin" theme="simple">

			<input type="hidden" name="user.permissions" id="permissions" value="<s:property value="user.permissions"/>">
			<table width="100%" height="100%">
				<TR>
					<td height="40%">
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								用户信息
							</caption>
							<colgroup>
								<col width="15%"></col>
								<col width="30%"></col>
								<col width="10%"></col>
								<col width="5%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									登陆账号<span class="star">*</span>
								</td>
								<td>
								<s:property value="user.usercode"/>			
								
								</td>
								<td class="labelTd"  colspan="2">
									姓名<span class="star">*</span>
								</td>
								<td>
									<s:textfield id="username" name="user.username" maxLength="50" />
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									性别<span class="star">*</span>
								</td>
								<td>
									<s:if test="user.sex==\"Y\"">
										<input type="radio" name="user.sex" checked class="radio"
											value="Y" id="sex" />男
								        <input type="radio" name="user.sex" class="radio" value="N"
											id="sex" />女
								   </s:if>
									<s:elseif test="user.sex==\"N\"">
										<input type="radio" name="user.sex" class="radio" value="Y"
											id="sex" />男
								        <input type="radio" name="user.sex" checked class="radio"
											value="N" id="sex" />女
								   </s:elseif>
								</td>
								<td class="labelTd"  colspan="2">
									联系方式
								</td>
								<td>
									<s:textfield id="phone" name="user.phone" maxLength="20" />
								</td>
							</tr>
							<tr>
                              <td class="labelTd">
									系统平台<span class="star">*</span>
							  </td>
                              <td align="left">
                              	<s:if test="user.groupid == 1">省平台</s:if>
                              	<s:elseif test="user.groupid == 2">学习中心平台</s:elseif>
                              	<s:elseif test="user.groupid == 0">系统管理平台</s:elseif>
                              	<s:else>&nbsp;</s:else>
                              </td>
                              <td class="labelTd" id=systext colspan="2">
                              		<s:if test="user.groupid==2">
	                              		学习中心代码<span class="star">*</span>
	                              	</s:if>
                              	</td>
                               	<td  id=sysinput>
	                               <s:if test="user.groupid==2">
	                              		<!-- 
	                              		<s:textfield id="user.sysid" name="user.sysid" maxLength="50"/>
	                              		 -->
	                              		<s:property value="user.sysid"/>	
	                              		<s:hidden name="user.sysid" />
	                              	</s:if>
                               	</td>
                               
                            </tr>
							
                            <tr>
								<td class="labelTd">
									备注
								</td>
								<td colspan="4">
									<s:textarea id="remark" name="user.remark"></s:textarea>
								</td>
							</tr>
							
						</table>
					</td>
				</tr>
				<TR>
					<td height="50%">
						<table align="center" width="100%" height="260" class="TableForm"
							cellpadding="3" cellspacing="0">
							<caption>
								用户权限分配
							</caption>
							
							<tr>
								<td >
									<iframe src="<%=path %>/admin/role/getPlatUserModule.action?userid=<s:property value="user.id"/>" id="iframes" name="iframes" frameborder="0" height="260" width="100%"></iframe>
								</td>
								
							</tr>
							
						</table>
					</td>
				</tr>
				<tr>
								<td colspan="5" align="center" height="10%">
									<input type="button" class="button" value="[提 交]"
										onclick="updateCheck();">
									&nbsp;
									<input class="button" id="resetNote" type="button"
										value="[返 回]" onclick="resultUrl();">
								</td>
							</tr>
			</table>
			<s:hidden name="user.id" />
			<s:hidden name="pageIndex" />
			<s:hidden name="searchName" />
			<s:hidden name="user.password" />
			<s:hidden name="user.groupid" />
			

			<input type="hidden" id="oldcode" name="oldcode" value="<s:property value='user.usercode'/>" />
		</s:form>
		<script type="text/javascript">

			var sys_name="";
			 						<s:if test="user.groupid=='2'">
									 sys_name="学习中心代码";
									  </s:if>		
									  <s:elseif test="user.groupid=='3'">
									  sys_name="学号";
									  </s:elseif>
									   <s:elseif test="user.groupid=='4'">
									  sys_name="教师职工号";
									  </s:elseif>
			
		</script>
	</body>
</html>

