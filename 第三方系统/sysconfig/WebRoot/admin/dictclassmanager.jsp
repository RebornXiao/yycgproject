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
	background-image: url(<%= baseurl%>images/model_fault.gif ) !important;
	margin: 0px 2px 5px 0px;
}
</style>
		<script type="text/javascript">
    Ext.onReady(function() {   
  
            new Ext.Viewport({   
                        enableTabScroll : true,   
                        layout : "border",
                           
                        items : [ {   
                                    xtype : 'panel',   
                                    region : 'center',
                                    contentEl: 'center',
                            	    tbar:[
                            				{text:'添加类别',iconCls:'add',handler:toaddType},'-',
                            				{text:'修改类别',iconCls:'edit',handler:toediteType},'-',
                            				{text:'删除类别',iconCls:'delete',handler:todeleteType}
                            				
                            				]
                                     
                                }]   

                                       })   
                                       
                                       
            function toaddType(){
            extModalWinShow('magdictclass.action?method=add','字典类别添加',320,200,true);
            
            }                           
			 function toediteType(){
			 
			 var selectItems = [];
		    selectItems = getAllCheck('Check');
			if(selectItems.length==0){
				extAlert("系统提示","请勾选您要修改的类别");
			}else if(selectItems.length > 1){
				extAlert("系统提示","只能修改单个类别信息");
			}else{
			  extModalWinShow('magdictclass.action?method=update&typecode='+selectItems,'修改类别',320,200,true);
			}
			            
			            
			}   
            
            
         
         function todeleteType(){
           
			        var selectItems = [];
					selectItems = getAllCheck('Check');
					if(selectItems.length==0){
						extAlertIcon("系统提示","请勾选您要删除的类别","warning");
					}else{
						extAlertConfirm("删除确定","确实要删除吗?",true,delNeAction,selectItems);
					}
				}
				
				
				//删除设备执行
				function delNeAction(selectItems){
					jqueryAjax("savedicttype.action","typecodes="+selectItems+"&method=delete",delNeBack,null);
				}
				//删除设备执行回调函数
				function delNeBack(responseText){
					extAlertCall("系统提示",responseText,alertbackfn,responseText);
				}
			     
			     //alert回调函数
				function alertbackfn(responseText){
					if(responseText.indexOf("成功") != -1){
					
					refreshSelf();
					
					}
				}
           
  
        })  ;
		
				function refreshSelf(){
				jquerySubByFId("finddictlist",succcallBack,null);
				parent.searchwestSub();
				}
		
               function succcallBack(responseText){
					jqueryGetEleById("center").html(responseText);
				}
		
	</script>
	</head>
	<body onload="refreshSelf()">
	<s:form id="finddictlist" name="finddictlist" action="finddictlist"
				method="post" theme="simple"></s:form>
		<div id="center">
			
		</div>
		<div id="active" /></div>
	</body>
</html>
