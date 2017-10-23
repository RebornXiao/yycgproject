<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>datagrid测试</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">

<LINK rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>


</head>

<SCRIPT type="text/javascript">

var toolbar_v=[{
	id:'btnadd',
	text:'添加',
	iconCls:'icon-add',
	handler:function(){
		alert("add");
	}
}];

var columns_v=[[
				{
				 field:'code',//此名称对应于json的数据
				 title:'用户账号',
				 width:120
				 },
				 {
					 field:'name',
					 title:'用户名称',
					 width:120
				 },
				 {
					 field:'groupid',
					 title:'用户类型',
					 width:120,
					//作用是对单元格中的数据内容进行格式化，value是单元格的数据,index是行的序号从0开始，row就是一行数据为json格式
					 formatter: function(value,row,index){
							if(value=='2'){
								return '卫生院';
							}else if(value=='3'){
								return '卫生室';
								
							}
						}

				}
			]];

$(function(){
	
	$('#userlist').datagrid({
		title:'用户列表',
		//iconCls:'icon-save',
		width:600,
		height:350,
		striped:true,//是否显示条纹效果
		url:'datagrid_data.json',//请求获取json格式的数据，里边存放了列表需要的数据
		idField:'code',//数据列表的主键，如果定义错误影响datagrid的操作
		pagination:true,//是否显示分页区域
		rownumbers:true,//是否显示行的序号
		columns:columns_v,//在外边定义列表的列，传给datagrid的columns属性
		toolbar:toolbar_v//在外边定义工具栏toolbar_v，传datagrid的toolbar属性
		
	});
	
});
   
</script>

<body>
<!-- 用户信息列表 -->
<table id="userlist"></table>

</body>

</html>