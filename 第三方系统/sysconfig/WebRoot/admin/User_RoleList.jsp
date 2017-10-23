<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'User_RoleList.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <link rel="stylesheet" type="text/css"href="<%=path%>/vcomframe/css/common.css" />
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
  
  <body>
    <table align="center" width="100%" class="TableForm" cellpadding="3" cellspacing="0" style="margin-top: 5px;">
		
		<colgroup>
			<col width="10%"></col>
			<col width="35%"></col>
			<col width="10%"></col>
			<col width="35%"></col>
			<col width="10%"></col>
		</colgroup>
		<tr>
			<td colspan="2" align="right">
				<select name="dltSource" size="10" id="toAddUserPage_dltSource" multiple="multiple" style="width:70%   ">
				    <option value="12">节目管理员</option>
				    <option value="10">日志管理员</option>
				    <option value="9">超级管理员</option>
				</select>
			</td>
			<td>
				<center>
					<input type="button" class="button" onclick="add(document.all.dltSource,document.all.dltTarget)" name="pviadd" value="==》">
					<br>
					<br>
					<br>
					<input type="button" class="button" onclick="add(document.all.dltTarget,document.all.dltSource)" name="pvidelete" value="《==">
				</center>
			</td>
			<td colspan="2">
				 <select   name="dltTarget"   size="10"   multiple="true"   style="width:70%   "></select>   
			</td>
		</tr>
	</table>
  </body>
</html>
