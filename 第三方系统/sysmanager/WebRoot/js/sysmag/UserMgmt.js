function init() {
	Ext.BLANK_IMAGE_URL = 'image/s.gif';
	Ext.QuickTips.init();
	var userStore = new Ext.data.JsonStore({
				id : 'user-store',
				root : 'rows',
				totalProperty : 'total',
				fields : ['userName', 'trueName', 'contact', 'address',
						'email', 'userState', 'remark', {
							name : 'createTime',
							type : 'date',
							dateFormat : 'YmdHis'
						}, 'sex', 'phone', 'movePhone', 'fax', 'areaList',
						'role', 'businessList'],
				proxy : new Ext.data.HttpProxy({
							url : 'user/list.do',
							method : 'POST'
						})
			});
	userStore.load({
				params : {
					start : 0,
					limit : 30
				}
			});
	var rn = new Ext.grid.RowNumberer();
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				header : ''
			});
	var cm = new Ext.grid.ColumnModel([rn, sm, {
				header : '用户名',
				dataIndex : 'userName',
				width : 50,
				renderer : renderUserArea.createDelegate(userGrid)
			}, {
				header : '用户角色',
				dataIndex : 'role',
				width : 50,
				renderer : function (value) {
					return value.roleName;
				}
			}, {
				header : '管理区域',
				dataIndex : 'areaList',
				width : 100,
				renderer : function (value) {
					var areas = "";
					for(i=0; i<value.length; i++) {
						areas += value[i].areaName + ","
					}
					return areas == ""? "" : areas.substring(0, areas.length-1);
				}
			},{
				header : '用户姓名',
				dataIndex : 'trueName',
				width : 60
			}, {
				header : '性别',
				dataIndex : 'sex',
				width : 20,
				renderer : renderSex
			}, {
				header : '地址',
				dataIndex : 'address',
				width : 200
			}, {
				header : '创建时间',
				dataIndex : 'createTime',
				width : 100,
				type : 'date',
				renderer : Ext.util.Format.dateRenderer('Y年m月d日')
			}/*, {
				header : '用户状态',
				dataIndex : 'userState',
				width : 30,
				renderer : renderUserState
			}*/]);
	var gridToolBar = eval("(" + Ext.get('toolbar').getValue() + ")");
	
	
/*	gridToolBar[gridToolBar.length]={
			id : 'bt_syn',
			text : '手工同步用户信息',
			icon : 'image/icons/package_add.png',
			cls : 'x-btn-text-icon',
			handler : AjaxSunUserData
	      };*/
	
	//gridToolBar[gridToolBar.length]={text:'帮助',cls:'x-btn-text-icon', icon:'image/icons/whole_main_help.gif', handler:function(){window.open("/help/help.html?anchor=yonghuguanli","帮助文档");}};
	
	function AjaxSunUserData(){
		Ext.MessageBox.confirm('提示信息', '确定要执行用户信息同步操作吗？', function(bt) {
			if (bt == 'no') {
				return;
			}
	    Ext.getBody().mask("用户信息同步中.请稍等...","x-mask-loading");   
		Ext.Ajax.request({
			url : '../uinsyndata.action',
			method : 'POST',
			params : {
				method : 'usersyn'
			},
			success : function(response, options) {
			    Ext.getBody().unmask(); 
				var result = Ext.util.JSON
						.decode(response.responseText);
				if (result.success == true) {
					Ext.Msg.show({
								title : '提示信息',
								msg : result.msg,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.INFO,
								fn : function() {
									userStore.reload();
								}
							});
				}
				if (result.success == false) {
					Ext.Msg.show({
								title : '提示信息',
								msg : result.msg,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
				}
			},
			failure: function(){   
				   //去除x-mask-loading效果   
				   Ext.getBody().unmask();   
				   Ext.Msg.alert('用户信息同步', '用户信息同步失败,未知错误！');   
				      }
		});  
		
		});
       }
  
	
	
	
	/*
	 * var gridToolBar1 = [{ id : 'bt_add', text : '添加', icon :
	 * 'ext/examples/shared/icons/fam/add.gif', cls : 'x-btn-text-icon bmenu',
	 * handler : addUser }, '-', { id : 'bt_edit', text : '编辑', icon :
	 * 'ext/examples/shared/icons/fam/plugin.gif', cls : 'x-btn-text-icon
	 * bmenu', handler : editUser }, '-', { id : 'bt_delete', text : '删除', icon :
	 * 'ext/examples/shared/icons/fam/delete.gif', cls : 'x-btn-text-icon
	 * bmenu', handler : deleteUser }];
	 */
	var gridBottomBar = new Ext.PagingToolbar({
				pageSize : 30,
				store : userStore,
				displayInfo : true,
				displayMsg : '共 {2} 条记录，显示第 {0} 到 {1} 条',
				emptyMsg : '无记录'
			});
	var userGrid = new Ext.grid.GridPanel({
				title : '用户信息列表',
				iconCls:'user',
				frame : true,
				stripeRows : true,
				tbar : gridToolBar,
				bbar : gridBottomBar,
				viewConfig : {
					forceFit : true
				},
				loadMask : {
					msg : '正在加载...'
				},
				ds : userStore,
				cm : cm,
				sm : sm
			});
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [userGrid]
			});
	userGrid.getSelectionModel().selectFirstRow();
	function addUser() {
		var addUserForm = new UserForm({
					gs : userStore,
					cmd : 'add',
					seluser: ''
				});
		var form = openWin(addUserForm).fp.getForm();
		Ext.getCmp('pwdLabel').setVisible(false);
	}

	function editUser() {
		var selections = userGrid.getSelectionModel().getSelections();
		if (selections.length == 0) {
			Ext.Msg.show({
						title : '提示信息',
						msg : '请选择一行数据',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING

					});
			return;
		}
		
		var username = selections[0].data.userName;
		if(isSSOHead(username)){
			Ext.Msg.show({
				title : '提示信息',
				msg : '不能编辑统一门户用户信息！',
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.WARNING

			});
	      return;
		}

		var editUserForm = new UserForm({
					gs : userStore,
					cmd : "edit",
					seluser: username,
					pwdBlank : true
				});
		var form = openWin(editUserForm).fp.getForm();
		form.loadRecord(selections[0]);

		Ext.getCmp('userName').getEl().dom.readOnly = true;
        Ext.getCmp('pwdLabel').setVisible(true);
		Ext.getCmp('roleCbx').setValue(selections[0].data.role.roleId);
		Ext.getCmp('roleCbx').setRawValue(selections[0].data.role.roleName);

		Ext.getCmp('areaField')
				.setValue(concatArea(selections[0].data.areaList).areaNames);
		Ext.getCmp('areaIds')
				.setValue(concatArea(selections[0].data.areaList).areaIds);

		Ext.getCmp('bussTreeCombo').setValue(concatBusiness(selections[0].data.businessList).bussNames);
		Ext.getCmp('bussIds').setValue(concatBusiness(selections[0].data.businessList).bussIds);

		Ext.getCmp('userState').items.first().setValue(selections[0].data.userState);
		Ext.getCmp('sex').items.first().setValue(selections[0].data.sex);
	}

	function openWin(form) {
		var userWin = new Ext.Window({
					id : 'user-win',
					title : '用户管理（注：具有下级管理区域方可创建新用户！）',
					items : [form],
					width : 565,
					modal : true,
					resizable : false
				});
		userWin.show();
		return {
			fp : form,
			win : userWin
		};
	}

	function deleteUser() {
		var selections = userGrid.getSelectionModel().getSelections();
		if (selections.length == 0) {
			Ext.Msg.show({
						title : '提示信息',
						msg : '请选择一行数据',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING

					});
			return;
		}
		
		var username = selections[0].data.userName;
		if(isSSOHead(username)){
			Ext.Msg.show({
				title : '提示信息',
				msg : '不能删除统一门户用户信息！',
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.WARNING

			});
	      return;
		}
		
		
		Ext.MessageBox.confirm('提示信息', '确定要执行删除操作吗？', function(bt) {
					if (bt == 'no') {
						return;
					}
					var username = selections[0].data.userName;
					Ext.Ajax.request({
								url : 'user/delete.do',
								method : 'POST',
								params : {
									userName : username
								},
								success : function(response, options) {
									var result = Ext.util.JSON
											.decode(response.responseText);
									if (result.success == true) {
										Ext.Msg.show({
													title : '提示信息',
													msg : result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO,
													fn : function() {
														userStore.reload();
													}
												});
									}
									if (result.success == false) {
										Ext.Msg.show({
													title : '提示信息',
													msg : result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
									}
								}
							});

				});

	}

	function renderSex(sexCode) {
		return sexCode == '1' ? '男' : '女';
	}

	function renderUserState(stateCode) {
		return stateCode == '1' ? '正常' : '<font color="red">暂停</font>';
	}

	function renderCreateTime(timestr) {
		var year = timestr.substr(0, 4);
		var month = timestr.substr(5, 6);
		var day = timestr.substr();
	}

	function concatArea(areaList) {
		var areaIds = "";
		var areaNames = "";
		for (var i = 0; i < areaList.length; i++) {
			areaIds += areaList[i].areaId + ",";
			areaNames += areaList[i].areaName + ",";
		}
		return {
			areaIds : areaIds,
			areaNames : areaNames
		};
	}

	function concatBusiness(bussList) {
		var bussIds = "";
		var bussNames = "";
		for (var i = 0; i < bussList.length; i++) {
			bussIds += bussList[i].businessId + ",";
			bussNames += bussList[i].businessName + ","
		}
		return {
			bussIds : bussIds,
			bussNames : bussNames
		};
	}
	function renderUserArea(value, cell, record) {
		var data = record.data;
		var info = {
			//roleName : data.role.roleName,
			areaNames : concatArea(data.areaList).areaNames,
			bussNames : concatBusiness(data.businessList).bussNames
		};
		var tipTpl = new Ext.XTemplate('<p><b><font color=red>用户详细信息</font></b></p>',
			    '<p>管理区域: {areaNames}</p>',''
				/*'<p>管理业务: {bussNames}</p>'*/);
		var tip = tipTpl.apply(info);
		return '<div qtip="' + tip + '">' + value + '</div>';
	}
	
	/**
	* 功能：判断是否以sso_开头的字符串
	*
	* 参数str：输入值
	* 返回值：true：合法 false：非法
	*/
function isSSOHead(str) {
	return (new RegExp(/^sso_\w+$/).test(str));
}

}
