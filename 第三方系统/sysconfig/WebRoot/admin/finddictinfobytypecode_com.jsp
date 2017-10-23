<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<%
	String path = request.getContextPath();
	String baseurl = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/";
			System.out.println("==="+baseurl);
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>


<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
				<link rel="stylesheet" href="<%= baseurl%>styles/ext-all.css" type="text/css"></link>
		<link rel="stylesheet" href="<%= baseurl%>styles/body.css" type="text/css" />
		<link rel="stylesheet" href="<%= baseurl%>styles/dtree.css" type="text/css" />
		<script type="text/javascript" src="<%= baseurl%>scripts/ext-base.js"></script>
		<script type="text/javascript" src="<%= baseurl%>scripts/ext-all.js"></script>
		<script type="text/javascript" src="<%= baseurl%>scripts/jquery-1.2.js"></script>
		<script type="text/javascript" src="<%= baseurl%>scripts/jquery.form.js"></script>
		<script type="text/javascript" src="<%= baseurl%>scripts/nms-jquery-form.js"></script>
		<script type="text/javascript" src="<%= baseurl%>scripts/dtree_checkbox.js"></script>
		<script type='text/javascript' src='/dwr/interface/dwrtransfer.js'></script>
		<script type='text/javascript' src='/dwr/engine.js'></script>
		<script type='text/javascript' src='/dwr/util.js'></script>
		<style type="text/css">
.button-tree-expand {
	background-image: url(<%= baseurl%>images/performance_baseinfo.gif ) !important;
	margin: 0px 2px 5px 0px;
}
</style>
<script type="text/javascript">

        
        
         function toadddictinfo(){
             var typecode = jqueryGetEleById("typecode").val();
             extModalWinShow('magdictclassinfo.action?method=add&typecode='+typecode,'字典类别信息添加',320,230,true);
            }
            
            
         function toupdatedictinfo(){
            var selectItems = [];
		    selectItems = getAllCheck('Check');
			if(selectItems.length==0){
				extAlert("系统提示","请先选择要修改的信息！");
			}else if(selectItems.length > 1){
				extAlert("系统提示","只能修改单个信息!");
			}else{
			 var typecode = jqueryGetEleById("typecode").val();
			  extModalWinShow('magdictclassinfo.action?method=update&typecode='+typecode+"&dictid="+selectItems,'修改类别',320,230,true);
			}
          
          }
			function tosetstate(flag){
				var statestr = "不启用";
				if(flag == "1"){
					statestr = "启用";
				}
				var selectItems = [];
				selectItems = getAllCheck('Check');
				if(selectItems.length==0){
					extAlertIcon("系统提示","请勾选您要设置的信息","warning");
				}else{
					Ext.Msg.show({
						title: "设置确定",
						msg: "确实要设置"+statestr+"吗?",
						buttons: Ext.Msg.YESNO,
						icon: Ext.MessageBox.QUESTION,
						minWidth: 190,
						fn: function(btn){
							if(btn == 'yes'){
								setAtateAction(flag,selectItems);
							}
						}
					});
				}
          	}
          	//设置状态执行
			function setAtateAction(flag,selectItems){
				jqueryAjax("setdicttypestate.action","dictids="+selectItems+"&state="+flag,delNeBack,null);
			}
          
         function todeletedictinfo(){
              var selectItems = [];
					selectItems = getAllCheck('Check');
					if(selectItems.length==0){
						extAlertIcon("系统提示","请勾选您要删除的信息","warning");
					}else{
						extAlertConfirm("删除确定","确实要删除吗?",true,delNeAction,selectItems);
					}
          
          }
          
          
             	//删除设备执行
				function delNeAction(selectItems){
					jqueryAjax("savedicttypeinfo.action","dictids="+selectItems+"&method=delete",delNeBack,null);
				}
				//删除设备执行回调函数
				function delNeBack(responseText){
					extAlertCall("系统提示",responseText,alertbackfn,responseText);
				}
			     
			     //alert回调函数
				function alertbackfn(responseText){
					if(responseText.indexOf("成功") != -1){
					var typecode = jqueryGetEleById("typecode").val();
					searchcenterSub(typecode);
				    extModalWinDestroy();
					}
				}
      
		       //字典类别管理
		       	// 修改
				function dictMagConfig(){
					extModalWinShow('dictclassmanager.action','字典类别管理',500,350,true);
				}
		        //查询提交
			function searchwestSub(){
				jquerySubByFId("finddictlisttomgt",succcallwestBack,null);
			}

        
         function succcallwestBack(responseText){
					jqueryGetEleById("west").html(responseText);
				}
				
				
		function searchcenterSub(typecode){
		        jqueryGetEleById("typecode").val(typecode);
				jquerySubByFId("finddictinfobytypecode",succcallcenterBack,null);
			}

        
         function succcallcenterBack(responseText){
					//jqueryGetEleById("center").html(responseText);
					document.finddictinfobytypecode.submit();
				}	

</script>
	</head>
	<body >
			<form id="finddictinfobytypecode" name="finddictinfobytypecode" action="finddictinfobytypecode_com.action"
				method="post" theme="simple">
				<input type="hidden" id="typecode" name="typecode" value="<s:property value='typecode'/>"/>
				<table class="bodytable">
				<tr><td class="top">
						<div id="tabBtnContainer">
							<ul id="tabBtnUi">
								<li><a href="javascript:toadddictinfo();"><img id="actionBtnImg" src="<%= baseurl%>images/cfg_add_tableobj.gif" />添加信息</a></li>
								<li><a href="javascript:toupdatedictinfo();"><img id="actionBtnImg" src="<%= baseurl%>images/cfg_edit_managerobj.gif" />修改信息</a></li>
								<li><a href="javascript:tosetstate('1');"><img id="actionBtnImg" src="<%= baseurl%>images/cfg_edit_managerobj.gif" />设置可用</a></li>
								<li><a href="javascript:tosetstate('0');"><img id="actionBtnImg" src="<%= baseurl%>images/cfg_edit_managerobj.gif" />设置不可用</a></li> 
							   
							</ul>
						</div>
					</td></tr>
					<tr>
						<td class="center">
							<div class="centerdiv">
								<table class="table_data">
									<tr>
										<th width="10" > 
											<input id="Check" type="checkbox"
												onClick="checkAll(this, 'Check')" />
										</th>
<!-- 
										<th width="20%">
											类别
										</th> -->
										<th width="30%">
											<s:property value='typename'/>
										</th>
										<s:if test="isshow == 'yes'">
											<th width="10%">
												代码
											</th>
										</s:if>
										<th width="10%">
											显示顺序
										</th>
										<th width="10%">
											状态
										</th>
										<th width="30%">
											备注
										</th>

									</tr>
									<s:iterator value="reslist" id="reslist" status="res">
										<tr <s:if test="(#res.index+1)%2==0">class="two"</s:if>>
											<td >
											<s:if test='valuetype=="1"'>
											<s:checkbox name="Check" fieldValue="%{#reslist.id}"
													onclick="chkRow(this)" 
													disabled="true"
													/>
											</s:if>
											<s:else>
											<s:checkbox name="Check" fieldValue="%{#reslist.id}"
													onclick="chkRow(this)" 	/>
											</s:else>
											</td>
											<!-- 
											
											<td>
												<s:property value="#reslist.typename" />
											</td>
											-->
											<td>
												<s:property value="#reslist.info" />
											</td>
											<s:if test="isshow=='yes'">
											<td>
												<s:property value="#reslist.dictcode" />
											</td>
											</s:if>
											<td>
												<s:property value="#reslist.dictsort" />
											</td>
											<td>
												<s:if test="#reslist.isenable == 1">启用</s:if>
												<s:elseif test="#reslist.isenable == 0">停用</s:elseif>
											</td>
											<td>
												<s:property value="#reslist.remark" />
												<s:if test='valuetype=="1"'>
												该项信息为系统初始信息，不允许修改。
												</s:if>
											</td>
											<td>&nbsp;</td>

											
										</tr>
									</s:iterator>
								</table>
							</div>
						</td>
				</table>
			</form>
			 <s:form id="finddictlisttomgt" name="finddictlisttomgt" action="finddictlisttomgt"
				method="post" theme="simple"></s:form>
				

			<div id="active"/></div>
	</body>
</html>
