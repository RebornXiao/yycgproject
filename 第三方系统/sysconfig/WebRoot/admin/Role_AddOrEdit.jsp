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
		<title>customtree.html</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<%=path %>/vcomframe/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="<%=path%>/vcomframe/css/common.css" />
		<link rel="stylesheet" id='skin' type="text/css" href="<%=path%>/vcomframe/list/message/skin/qq/ymPrompt.css" />
		<script type="text/javascript" src="<%=path%>/vcomframe/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/message/ymPrompt.js"></script>
		<script type="text/javascript" src="<%=path%>/vcomframe/list/check/formInvalidCheck.js"></script>
		<link rel="stylesheet" id="ext-all-css" type="text/css" href="<%=path %>/vcomframe/ext/resources/css/ext-all.css" />
		<style>
		.delete-icon {
		    background-image:url(<%=path %>/vcomframe/images/rss_delete.gif) !important;
		}
		.load-icon {
		    background-image:url(<%=path %>/vcomframe/images/rss_load.gif) !important;
		}
		.add-feed {
		    background-image: url(<%=path %>/vcomframe/images/rss_add.gif) !important;
		}
		</style>
		<base target="_self">
	</head>

	<body>
		<div id="loading-mask"></div>
		<div id="loading">
			<div class="loading-indicator">
				<img src="<%=path %>/vcomframe/images/extanim32.gif" width="32" height="32"
					style="margin-right:8px;" align="absmiddle" />
				页面加载中，请稍后...
			</div>
		</div>
		<script type="text/javascript" src="<%=path %>/vcomframe/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=path %>/vcomframe/ext/ext-all.js"></script>
	    <script type="text/javascript" src="<%=path %>/vcomframe/ext/windows.js"></script>
	    <script type="text/javascript" src="<%=path %>/vcomframe/ext/tree.js"></script>
	    <script type="text/javascript">
			var id=100;
			Ext.onReady(function(){
					var west=new Ext.Panel({
			        region:"east",
			        width:250,
			        minSize:0,
			        maxSize:250,
			        split:true,        
			        collapsible: true,
			        title:"功能权限",
			        autoScroll:true,
			        items:new Ext.BoxComponent({
							el: 'east',
							height:'100%'
						}),
			        collapsed:false
			    });
				var center=new Ext.Panel({
						region:'center',
			            title: '<s:if test="0==operFlag">角色新增</s:if><s:else>角色修改</s:else>',
			            collapsible: true,
			            split:true,
			            width: 225,
			            minSize: 175,
			            maxSize: 300,
			            layout:'fit',
			            margins:'0 0 0 0',
			            items:new Ext.BoxComponent({
							el: 'center',
							height:'100%'
						})    
				});
				var viewport = new Ext.Viewport({
					layout:"border",
					items:[center,west]
				});
			});
			function changeplat(id){
				iframes.location="<%=path %>/admin/role/getPlatModule.action?platid="+id;
			}
		</script>
		<div id="center">
			<form name="zzvcom" onSubmit="return checkinput();" method="post"
			onSubmit="return checkinput();">


			<input type="hidden" name="roleobj.id"
				value="<s:property value="roleobj.id"/>">
			<input type="hidden" name="roleobj.permissions" id="permissions"
				value="<s:property value="roleobj.permissions"/>">

			<input type="hidden" name="roleobj.vchar1"
				value="<s:property value="roleobj.vchar1"/>">
<!-- 显示顺序改为默认 -->
			<input type="hidden" name="roleobj.sort" value="1" id="sort">

		 
			<input type="hidden" name="operFlag"
				value="<s:property value="operFlag"/>">
			<table width="100%">
				<TR>
					<td>
						<s:if test="0==operFlag">
								<input type="hidden" name="moduleObj.parentid"
										value="<s:property value="parentid"/>">
									<input type="hidden" name="moduleObj.depth"
										value="<s:property value="depth"/>">
								</s:if>
								<s:else>
									<!-- 创建时间 -->
									<input type="hidden" name="roleobj.createtime"
										value="<s:property value="roleobj.createtime"/>">
								</s:else>
						<table align="center" width="100%" class="TableForm"
							cellpadding="3" cellspacing="0">
							<colgroup>
								<col width="15%"></col>
								<col width="40%"></col>
								<col width="15%"></col>
								<col width="30%"></col>
							</colgroup>
							<tr>
								<td class="labelTd">
									角色名称
									<span class="star">*</span>
								</td>
								<td colspan="3">
									<input type="text" name="roleobj.rolename" maxlength="25"
										value='<s:property value="roleobj.rolename"/>'
										id="rolename" />
								</td>
								
							</tr>
							<tr>
								<td class="labelTd">
									选择平台<span class="star">*</span>
								</td>
								<td colspan="3">
								<s:if test="1==isxuexicenter">
									学习中心平台
									<input type="hidden" name="roleobj.type" value="2">
								</s:if>
								<s:elseif test="0==operFlag">
								<select name="roleobj.type" id="roleobj.type" onchange="changeplat(this.value)">
								 <option value="" <s:if test="roleobj.type==null ">selected</s:if>>======请选择平台======</option>
								 <option value="1" <s:if test="roleobj.type==1 ">selected</s:if>>省平台</option>
								 <option value="2" <s:if test="roleobj.type==2 ">selected</s:if>>学习中心平台</option>
								<%--
								 <option value="3" <s:if test="roleobj.type==3 ">selected</s:if>>学生平台</option>
								 <option value="4" <s:if test="roleobj.type==4 ">selected</s:if>>教师平台</option>
								 --%>
								 <option value="0" <s:if test="roleobj.type==0 ">selected</s:if>>系统管理平台</option>
								</select>
								</s:elseif>
								<s:else>
								<input type="hidden" name="roleobj.type" value="<s:property value="roleobj.type"/>">
								  <s:if test="roleobj.type==1">
											省平台
										</s:if>
										<s:elseif test="roleobj.type==2">
										    学习中心平台
										</s:elseif>
										<s:elseif test="roleobj.type==3">
										    学生平台 
										</s:elseif>
										<s:elseif test="roleobj.type==0">
										    系统管理平台 
										</s:elseif>
										<s:elseif test="roleobj.type==4">
										    教师平台 
										</s:elseif>
										<s:else>
										&nbsp;
										</s:else>
								</s:else>
								
								
										    
								</td>

							</tr>
							<tr>
								<td class="labelTd">
									备注
								</td>
								<td colspan="3">
									<input type="text" name="roleobj.remark" maxlength="50"
										value="<s:property value="roleobj.remark"/>" />
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
		</div>
		<div id="east">
		<iframe src="<s:if test="roleobj.type!=''"><%=path %>/admin/role/getPlatModule.action?platid=<s:property value="roleobj.type"/>&roleobj.id=<s:property value="roleobj.id"/></s:if>" id="iframes" name="iframes" frameborder="0" height="100%" width="100%"></iframe>
		</div>
		<script type="text/javascript">
			function iscnnumen(str){
			  var result = true;
			   var reg = /^[\a-zA-Z0-9\u4e00-\u9fa5]*$/;
			   var r = str.match(reg);
			   if(r==null){
			      result = false;
			      return result;
			   }
			   return result;
			}

            var operFlag=<s:property value="operFlag"/>;
			function checkinput(){
				var rolename = document.getElementById("rolename").value;
			    if(!CheckNotNull(document.getElementById("rolename"),"角色名称")){return false};
			    if(!iscnnumen(rolename)){
			    	ymPrompt.alert("角色名称只能输入中文、英文、数字!",null,null,'警告',null);
			    	return false;
			    }
			    //if(!CheckNotNull(document.getElementById("sort"),"菜单显示顺序")){return false};
			    if(document.getElementById("roleobj.type")&&!CheckNotNull(document.getElementById("roleobj.type"),"选择平台")){return false};
			    if(!CheckNum(document.getElementById("sort"), "排序号", 0, 10000)) return false;
			    var checkinput=iframes.checkboxtree.getCheckedValue();
			    if(checkinput==""){
			    	alert("请选择角色权限!");
			    	return;
			    }
			    if('<s:property value="roleobj.id"/>'!=''){
			    Ext.Ajax.request({   
            url : '<%=path%>/admin/role/roleEditCheck.action' ,    
            params :'roleid=<s:property value="roleobj.id"/>',   
            method: 'GET',   
            text: "Updating...",   
            success:updatedo
            }) 
			    }else{
			      saverole();
			    }
			    
			    
			}
			function updatedo(xml){
			
		        if(xml.responseText=="false"){
                  ymPrompt.alert("系统初始角色不允许修改!",null,null,'警告',null)
                  return false;
                }
                saverole();
		  }
		  function saverole(){
		     var checkinput=iframes.checkboxtree.getCheckedValue();
               document.getElementById("permissions").value=checkinput;
			    //alert(checkinput);
			    var actionname="";
			    if(0==operFlag)
			       actionname="<%=path%>/admin/role/saveRole.action";
			    else
			       actionname="<%=path%>/admin/role/updateRole.action";
			       
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
	</body>
</html>

