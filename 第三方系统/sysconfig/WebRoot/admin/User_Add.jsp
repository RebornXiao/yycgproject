<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/common/tag.jsp"%>
<%
	String path = request.getContextPath();

String jwpath = request.getScheme() + "://"+ request.getServerName() + ":" + (request.getServerPort()==80?"":request.getServerPort())+ "/";
jwpath = jwpath+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="stylesheet" type="text/css"
			href="<%= path%>/vcomframe/css/common.css" />
		<script type="text/javascript" src="<%= path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" src="<%= path%>/vcomframe/list/ajax/jquery.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
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
		
			function usercodecheck(str){
			  	var result = true;
			   	var reg = /^[\a-zA-Z0-9\_]*$/;
			   	var r = str.match(reg);
			   	if(r==null){
			    	result = false;
			    	return result;
			   	}
			   	return result;
			}
			
		  function saveCheck(){
		  	var usercode = document.getElementById("usercode").value;
		    if(!CheckNotNull(document.getElementById("usercode"),"登陆账号")){return false};
		    if(!usercodecheck(usercode)){
		    	ymPrompt.alert("登陆账号只能是英文、数字、下划线!",null,null,'警告',null);
		    	return false;
		    }
		    if(usercode.length>20){
		    	ymPrompt.alert("用户账号最多20位!",null,null,'警告',null);
		    	return false;
		    }
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
		    if(!CheckNotNull(document.getElementById("username"),"用户名称")){return false};
		    if(!CheckNotNull(document.getElementById("password"),"密码")){return false};
		    if(!CheckNotNull(document.getElementById("checkpassword"),"确认密码")){return false};
		    if(!CheckNotNull(document.getElementById("sex"),"性别")){return false};
		    if(!checkPassword()) return false;
		    //if(!CheckNotNull(document.getElementById("dltTarget"),"用户角色")){return false};
		    //if(document.getElementById("user.sysid")&& !CheckNotNull(document.getElementById("user.sysid"),sys_name)){return false};
		    if(!CheckNotNull(document.getElementById("usertype"),"系统平台")){return false};
		    
		    if(document.getElementById("user.sysid")){
		      //校验必须输入
		      if(!CheckNotNull(document.getElementById("user.sysid"),sys_name)){
		         return false;
		      }
		      //校验是否正确
		      sysidCheck(document.getElementById("user.sysid").value);
		    }else{
			   codeCheck();
			}
		    
		    /*
		    var roles=document.all.dltTarget;
            if(roles.length<1){
                ymPrompt.alert("请选择用户角色!",null,null,'警告',null )
                return false;
             }
             selectAll(roles);
             */
		     
		  }
		  //校验业务代码
		  function sysidCheck(id){
            //alert(id+"  "+groupid+"   ="+"<%=jwpath%>checksysid.action");
            var newdate = new Date(); 
			$.get("<%=jwpath%>checksysid.action?newdate="+newdate,{sysid:id,groupid:groupid},sysidrend); 
		  }
		  function sysidrend(xml){ 
		       //alert(xml);
　　			   if(xml=="true"){
                  ymPrompt.alert(sys_name+"不正确，请联系相关业务人员!",null,null,'警告',null)
                  return false;
                }
                codeCheck();
　　			} 
		 function codeCheck(){
		    var usercode=document.getElementById("usercode").value;
			$.get("<%=path%>/admin/codeCheck.action",{usercode:usercode},rend); 
			
		 }
		 function rend(xml){ 
　　			   if(xml=="true"){
                  ymPrompt.alert("登陆账号重复!",null,null,'警告',null)
                  return false;
                }
                save();
　　			} 
		  function save(){
		        userAddForm.action = "<%=path%>/admin/saveUser.action";
				userAddForm.submit();
		  }
		  function resetForm(){
		       
		  }
          function checkPassword(){
             var password=document.getElementById("password").value;
             var checkPassword=document.getElementById("checkpassword").value;
             if(password.length < 6 || password.length > 18){
             	ymPrompt.alert("用户密码应为6到18个字符!",null,null,'警告',null);
		    	return false;
             }
             if(password.indexOf('#')!= -1){
             	ymPrompt.alert("用户密码不能存在 # 字符!",null,null,'警告',null);
		    	return false;
             }
             if(password!=checkPassword){
                ymPrompt.alert("密码不一致!",null,null,'警告',function(){document.getElementById("checkpassword").focus(); })
                document.getElementsByName("password").value="";
                document.getElementsByName("checkpassword").value="";
                return false;
             }else 
                return true;
             
          }
        </script>
        <script   language="javascript">   
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

  </script>
	</head>
	<body scroll="auto">
	 <s:form name="userAddForm" namespace="/admin" theme="simple">
		
			<table width="100%">
				<TR>
					<td>
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
									<s:textfield id="usercode" name="usercode"  maxLength="50"/>
								</td>
								<td class="labelTd" colspan="2">
									用户密码<span class="star">*</span>
								</td>
								<td>
									<s:password id="password" name="password"  maxLength="50"/>
								</td>
							</tr>
							<tr>
								<td class="labelTd">
									姓名<span class="star">*</span>
								</td>
								<td>
									<s:textfield id="username" name="username"  maxLength="50"/>
								</td>
								<td class="labelTd" colspan="2">
									用户密码确认<span class="star">*</span>
								</td>
								<td>
									<s:password  id="checkpassword" name="checkpassword"   maxLength="50" />
								</td>
							</tr>
							<tr>
							   <td class="labelTd">
									性别<span class="star">*</span>
								</td>
								<td>
								   <input type="radio" name="sex" class="radio" value="Y"  checked id="sex"/>男
								   <input type="radio" name="sex" class="radio" value="N" id="sex"/>女
								</td>
								<td class="labelTd" colspan="2">
									联系方式
								</td>
								<td>
									<s:textfield id="phone" name="phone"  maxLength="20"/>
								</td>
							</tr>
							
                            <tr>
                              <td class="labelTd">
									系统平台<span class="star">*</span>
							  </td>
                              <td align="left">
                              	<s:if test="#request.currgroupid == 0 || #request.currgroupid == 1">
                              		<s:select 
		                              	list="#{'1':'省平台','2':'学习中心平台','0':'系统管理平台'}"
		                              	id="usertype"
		                               	name="user.groupid" 
		                               	headerKey="" 
		                               	headerValue="=====请选择系统平台=====" 
		                               	onchange="groupchange(this)"
		                               	style="width:250px;">
	                               	</s:select>
                              	</s:if>
                              	<s:elseif test="#request.currgroupid == 2">
                              		<s:select 
		                              	list="#{'2':'学习中心平台'}"
		                              	id="usertype"
		                               	name="user.groupid" 
		                               	onchange="groupchange(this)"
		                               	style="width:250px;">
	                               	</s:select>
	                               	<input type="hidden" id="user.sysid" name="user.sysid" value="<s:property value="#request.usersysid"/>"/>
                              	</s:elseif>
                              	
                              </td>
                              <td class="labelTd" id=systext colspan="2">
                              
                              	</td>
                               	<td  id=sysinput>
                               
                               	</td>
                              	
                            </tr>
                            <tr>
								<td class="labelTd">
									备注
								</td>
								<td colspan="4">
								    <s:textarea id="remark" name="remark" ></s:textarea>
								</td>
							</tr>
							<tr>
								<td colspan="5" align="center">
									<input type="button" class="button" value="[提 交]"
										onclick="saveCheck();">
									&nbsp;
									<input class="button" id="resetNote" type="button"
										value="[返 回]" onclick="history.back();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				
	
			</table>
	</s:form>
	<script type="text/javascript">
			var sys_='<s:textfield id="user.sysid" name="user.sysid" maxLength="50"/>';
			var sys_name="";
			var groupid="";
			<s:if test="#request.currgroupid == 2">
groupid='2';
sys_name="学习中心代码";
</s:if>
			function changeplat(id){
			    id=rolelist[id];
			    groupid = id;
			    var systext_obj=document.getElementById('systext');
			    var sysinput_obj=document.getElementById('sysinput');
			    sys_name="";
			    sysinput_obj.innerHTML="";
			    systext_obj.innerHTML="";
				if(id=='2'){
				   sysinput_obj.innerHTML=sys_;
				   sys_name="学习中心代码";
				   systext_obj.innerHTML=sys_name+'<span class="star">*</span>';
				}else if(id=='3'){
				   sysinput_obj.innerHTML=sys_;
				   sys_name="学号";
				   systext_obj.innerHTML=sys_name+'<span class="star">*</span>';
				}else if(id=='4'){
				   sysinput_obj.innerHTML=sys_;
				   sys_name="教师职工号";
				   systext_obj.innerHTML=sys_name+'<span class="star">*</span>';
				}
			}
			var rolelist=[];
			<s:iterator value="#request.roles" id="role" status="st">
			   rolelist['<s:property value="#role.id" />']='<s:property value="#role.type" />';
			</s:iterator>
		</script>
	</body>
</html>

