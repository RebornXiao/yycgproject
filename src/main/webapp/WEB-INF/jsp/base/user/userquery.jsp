<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>用户查询列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">

<LINK rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<SCRIPT type="text/javascript">

//用户添加
function useradd(){
	//第一个参数是窗口的title，依次是：宽、高、url链接
	createmodalwindow("添加用户信息", 800, 250, '${baseurl}user/useradd.action');
}
//用户修改
function useredit(id){
	//alert(id);
	//打开修改窗口
	createmodalwindow("修改用户信息", 800, 250, '${baseurl}user/useredit.action?id='+id);
}
//用户删除
function userdel(id){
	//提示用户是否确定删除
	_confirm('您确定要执行删除操作吗?', null, function() {
		$("#sysuserdelid").val(id);
		//通过form提交删除用户信息
		jquerySubByFId('sysuserdelForm', sysuserdel_callback, null, "json");
	});
}
//用户删除回调方法
function sysuserdel_callback(data){
	message_alert(data);
	//判断一下如果操作成功执行刷新
	if(data.resultInfo.type==TYPE_RESULT_SUCCESS){//操作成功
		sysuserquery();//重新执行查询，相当于刷新
	}
	
} 
var toolbar_v=[{
	id:'btnadd',
	text:'添加',
	iconCls:'icon-add',
	handler:useradd
}];

var columns_v=[[
				{
				 field:'userid',//此名称对应于json的数据
				 title:'用户账号',
				 width:120
				 },
				 {
					 field:'username',
					 title:'用户名称',
					 width:120
				 },
				 {
					 field:'groupname',
					 title:'用户类型',
					 width:120
					
				},
				 {
					 field:'sysmc',
					 title:'单位名称',
					 width:120
					
				},
				 {
					 field:'userstate',
					 title:'用户状态',
					 width:120,
					//作用是对单元格中的数据内容进行格式化，value是单元格的数据,index是行的序号从0开始，row就是一行数据为json格式
					 formatter: function(value,row,index){
							if(value=='1'){
								return '正常';
							}else {
								return '暂停';
								
							}
						}
				 },
				 {
					 field:'opt1',
					 title:'修改',
					 width:120,
					 formatter: function(value,row,index){
							return "<a href=javascript:useredit('"+row.id+"')>修改</a>";
						}
					
				},
				 {
					 field:'opt2',
					 title:'删除',
					 width:120,
					 formatter: function(value,row,index){
							return "<a href=javascript:userdel('"+row.id+"')>删除</a>";
						}
					
				}
			]];

$(function(){
	//datagrid的加载方法
	$('#sysuserlist').datagrid({
		title:'用户列表',
		striped:true,//是否显示条纹效果
		url:'${baseurl}user/userquery_result.action',//请求获取json格式的数据，里边存放了列表需要的数据
		idField:'code',//数据列表的主键，如果定义错误影响datagrid的操作
		pagination:true,//是否显示分页区域
		rownumbers:true,//是否显示行的序号
		columns:columns_v,//在外边定义列表的列，传给datagrid的columns属性
		toolbar:toolbar_v,//在外边定义工具栏toolbar_v，传datagrid的toolbar属性
		pageList:[15,30,50,100]//定义每页显示个数
		
	});
	
});
//查询按钮调用方法
function sysuserquery(){
	//获取from里边所有的对象及值，并组织成json格式
	var formdata = $("#sysuserqueryForm").serializeJson();
	 //根据查询重新加载datagrid，传入的参数是查询条件
	$('#sysuserlist').datagrid('load',formdata);

}

function test1(callback_fun){
	//调用回调
	alert('test1调用回调');
	callback_fun('data....');
}

function test(callback_fun){
	
	//.....
	
	//......
	alert('test...');
	//开始调用回调
	test1(callback_fun);
	
}
//回调方法
function test_callback(data){
	alert(data);
	
}
//test(test_callback);
   
</script>
</head>

<body>
<form id="sysuserqueryForm">
<TABLE class="table_search">
				<TBODY>
					<TR>
						<TD class="left">用户账号：</td>
						<td><INPUT type="text" name="sysuserCustom.userid" /></TD>
						<TD class="left">用户名称：</TD>
						<td><INPUT type="text" name="sysuserCustom.username" /></TD>
						
						<TD class="left">单位名称：</TD>
						<td><INPUT type="text" name="sysuserCustom.sysmc" /></TD>
						<TD class="left">用户类型：</TD>
						<td>
							<select name="sysuserCustom.groupid">
								<option value="">请选择</option>
								<c:forEach items="${grouplist}" var="dictinfo">
								     <option value="${dictinfo.dictcode}">${dictinfo.info}</option>
								</c:forEach>
							</select>
						</TD>
						<td ><a id="btn" href="#" onclick="sysuserquery()"
							class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>
					</TR>


				</TBODY>
			</TABLE>
			
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="sysuserlist"></table>
					</TD>
				</TR>
			</TBODY>
		   </TABLE>
</form>
<form id="sysuserdelForm" action="${baseurl}user/userdel.action" method="post">
    <input type="hidden" id="sysuserdelid" name="sysuserdelid" />
</form>
</body>
</html>
