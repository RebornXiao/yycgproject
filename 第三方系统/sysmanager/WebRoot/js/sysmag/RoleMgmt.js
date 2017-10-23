function initRolePage() {
	Ext.BLANK_IMAGE_URL = 'image/s.gif';
	Ext.QuickTips.init();
	var rn = new Ext.grid.RowNumberer();
	var roleSM = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				header : ''
			});
	var roleCM = new Ext.grid.ColumnModel([rn, roleSM, {
				header : '角色名称',
				dataIndex : 'roleName',
				width : 150,
				sortable : true
			}, {
				header : '角色描述',
				dataIndex : 'roleDes',
				width : 150
			}, {
				header : '所属区域',
				dataIndex : 'area',
				renderer : function(value) {
					return value.areaName;
				}
			}

	]);

	var roleDS = new Ext.data.JsonStore({
				root : 'rows',
				totalProperty : 'total',
				fields : ['roleId', 'roleName', 'roleDes', 'area'],
				proxy : new Ext.data.HttpProxy({
							url : 'role/list.do',
							method : 'POST'
						})
			});
	roleDS.load({
				params : {
					start : 0,
					limit : 30
				}
			});

	var sysDS = new Ext.data.JsonStore({
				root : 'rows',
				fields : ['systemId', 'systemName'],
				proxy : new Ext.data.HttpProxy({
							url : 'module/loadSystemList.do',
							method : 'POST'
						})
			});

	var sysSM = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				header : ''
			});
	var gridToolBar=
        [{
			id : 'add_role', 
			text : '添加',
			icon : 'image/icons/package_add.png',
			cls : 'x-btn-text-icon',
			handler : toadd
		},{
			id : 'del_role',
			text : '删除',
			icon : 'image/icons/package_delete.png',
			cls : 'x-btn-text-icon',
			handler : todelete
		},{
			id : 'edit_role',
			text : '修改',
			icon : 'image/icons/package_green.png',
			cls : 'x-btn-text-icon',
			handler : toedit
		},{
			id : 'assign_role',
			text : '分配权限',
			icon : 'image/icons/package_green.png',
			cls : 'x-btn-text-icon',
			handler : toassign
		}];
	var roleGrid = new Ext.grid.GridPanel({
		title : '角色列表',
		frame : true,
		ds : roleDS,
		cm : roleCM,
		sm : roleSM,
		border : true,
		margins : '5 5 5 5',
		tbar : gridToolBar,
		bbar : new Ext.PagingToolbar({
					pageSize : 30,
					store : roleDS,
					displayInfo : true,
					displayMsg : '共 {2} 条记录，显示第 {0} 到 {1} 条 ',
					emptyMsg : "没有记录"
				}),
		anchor : '100%',
		viewConfig : {
			forceFit : true
		}// ,
			// autoHeight : true
		});

	function showWin() {
		var roleId = new Ext.form.Hidden({
					name : 'roleId'
				});

		var roleName = new Ext.form.TextField({
					fieldLabel : '角色名称',
					name : 'roleName',
					anchor : '80%',
					maxLength : 64,
					allowBlank:false
				});
		var areaId = new Ext.form.Hidden({
					name : 'areaId',
					id : 'areaId'
				});
		var areaName = new Ext.form.ComboBox({
					fieldLabel : '所属区域',
					name : 'areaName',
					id : 'areaName',
					store : new Ext.data.SimpleStore({
								fields : [],
								data : [[]]
							}),
					anchor : '80%',
					editable : false,
					mode : 'local',
					triggerAction : 'all',
					tpl : "<div id='treedelete' style='height:200;'></div>",
					allowBlank:false
				});
		var roleDes = new Ext.form.TextArea({
					fieldLabel : '角色描述',
					name : 'roleDes',
					anchor : '90%',
					maxLength : 200,
					height : 70,
					maxLength : 100
				});
		var areaTree = new Ext.tree.TreePanel({
					autoScroll : true,
					useArrows : true,
					height : 200,
					rootVisible : false,
					loader : new Ext.tree.TreeLoader({
								dataUrl : 'area/loadAreaTree.do'
							})
				});
		var areaRoot = new Ext.tree.AsyncTreeNode({
					id : '0',
					text : '区域列表'
				});
		areaTree.setRootNode(areaRoot);
		areaTree.on('click', function(node) {
					areaId.setValue(node.id);
					areaName.setValue(node.text);
					areaName.collapse();
				});
		areaName.on('expand', function() {
					areaTree.render('treedelete');
				});
		var panel = new Ext.FormPanel({
					id : 'role',
					frame : true,
					border : false,
					labelAlign : 'right',
					labelWidth : 100,
					width : 380,
					height : 200,
					waitMsgTarget : true,
					items : [roleId, roleName, areaId, areaName, roleDes],
					buttons : [{
								text : '提交',
								handler : addRole
							}, {
								text : '重置',
								handler : function() {
									panel.getForm().reset()
								}
							}]
				});
		var addWin = new Ext.Window({
					id : 'addWin',
					title : '角色表单',
					width : 400,
					// height : 250,
					layout : 'fit',
					modal : true,
					items : [panel]
				});
		addWin.show();
		return {
			fp : panel,
			win : addWin
		};
	}

	/*
	 * addWin.on('hide', function() { var a = panel.getForm().reset(); });
	 */

	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [roleGrid]
			});

	function isSelected(sm) {
		var selection = sm.getSelections();
		if (selection.length == 0) {
			return false;
		}
		return true;
	}

	function toadd() {
		showWin();
	}

	/* 此处为添加或者编辑角色用的函数 */
	function addRole(form, action) {
		if (!Ext.getCmp('role').getForm().isValid()) {
            return;
        }
		Ext.getCmp('role').getForm().submit({
					url : 'role/save.do',
					method : 'POST',
					waitMsg : '正在保存数据...',
					success : function(form, action) {
						if (action.result.status == false) {
							Ext.Msg.show({
										title : '提示信息',
										msg : action.result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						}
						if (action.result.status == true) {
							Ext.Msg.show({
										title : '提示信息',
										msg : action.result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO,
										fn : function() {
											Ext.getCmp('addWin').close();
											roleDS.reload();
										}
									});
						}
					}
				});

	}
	function toedit() {
		if (!isSelected(roleGrid)) {
			Ext.MessageBox.show({
						title : '提示信息',
						msg : '请选择一行！',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING
					});
			return;
		}
		showWin();
		var panel = Ext.getCmp('role');
		var selected = roleSM.getSelected();
		panel.getForm().loadRecord(selected);
		Ext.getCmp('areaId').setValue(selected.get('area').areaId);
		Ext.getCmp('areaName').setValue(selected.get('area').areaName);

	}

	function toassign() {
		if (!isSelected(roleGrid)) {
			Ext.MessageBox.show({
						title : '提示信息',
						msg : '请选择一行！',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING
					});
			return;
		}
		var slc = roleSM.getSelected().get('roleId');
		assignPower(slc);
	}

	function todelete() {
		if (!isSelected(roleGrid)) {
			Ext.MessageBox.show({
						title : '提示信息',
						msg : '请选择一行！',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING
					});
			return;
		}
		Ext.MessageBox
				.confirm('提示信息', '该角色及相关权限将被删除<br><br> 确定执行删除操作吗?', todel);
	}

	function todel(bt) {
		if (bt == "no") {
			return;
		}
		var currPage = roleGrid.getBottomToolbar().getPageData().activePage;
		var selection = roleGrid.getSelectionModel().getSelections();
		Ext.Ajax.request({
					method : 'POST',
					url : 'role/delete.do',
					params : {
						roleId : selection[0].get("roleId")
					},
					success : function(response, options) {
						var result = Ext.util.JSON
								.decode(response.responseText);
						if (result.success == true) {
							Ext.MessageBox.show({
										title : '提示信息',
										msg : result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO,
										fn : function() {
											roleDS.reload();
										}
									});
						} else {
							Ext.MessageBox.show({
										title : '提示信息',
										msg : result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						}

					}
				});

	}

	function assignPower(roleId) {
		var sysDS = new Ext.data.JsonStore({
					root : 'rows',
					fields : ['systemId', 'systemName', 'assignStatus'],
					proxy : new Ext.data.HttpProxy({
								url : 'auth/loadSystemList.do?roleId='
										+ roleId,
								method : 'POST'
							}),
					autoLoad : true
				});
		var sysCM = new Ext.grid.ColumnModel([{
					header : '子系统列表',
					dataIndex : 'systemName'
				}]);
		var sysGrid = new Ext.grid.GridPanel({
					ds : sysDS,
					cm : sysCM,
					bbar:['红色: 没有分配任何权限<br>黄色: 已分配部分权限<br>绿色: 已分配全部权限'],
					loadMask : {
                      msg : '正在加载...'
                    },
					viewConfig : {
						forceFit : true,
						getRowClass : function(record, rowIndex, rowParams,
								store) {
							if (record.data.assignStatus == 'NONE') {
								return 'x-grid-back-red';
							} else if (record.data.assignStatus == 'SOME') {
								return 'x-grid-back-yellow';
							} else if (record.data.assignStatus == 'ALL') {
								return 'x-grid-back-green';
							} else {
								return '';
							}
						}
					}
				});
		sysGrid.on('rowclick', function() {
			var sysid = sysGrid.getSelectionModel().getSelections()[0].data.systemId;
			var sysname = sysGrid.getSelectionModel().getSelections()[0].data.systemName;
			var treePanel = new Ext.tree.TreePanel({
						autoScroll : true,
						loadMask : {
                           msg : '正在加载...'
                        },
						//height : 330,
						rootVisible : false,
						singleExpand : true,
						animate :false,
						checkModel : 'cascade',
						loader : new Ext.tree.TreeLoader({
									dataUrl : 'auth/loadAuthTree.do',
									baseAttrs : {
										uiProvider : Ext.ux.TreeCheckNodeUI
									},
									baseParams : {
										'roleId' : roleId,
										'systemId' : sysid
									},
									preloadChildren:true
								})
					});
			var root = new Ext.tree.AsyncTreeNode({
						id : sysid,
						text : '子系统'
					});
			treePanel.setRootNode(root);
			treePanel.expandAll();
			
			treePanel.on('click', function(node) {
					})
			var treeWin = new Ext.Window({
				title : sysname + ' 权限分配',
				buttonAlign : 'right',
				width : 300,
				height : 400,
				layout:'fit',
				//x : sysWin.getPosition()[0] + 50,
				//y : sysWin.getPosition()[1] + 50,
				modal : true,
				items : [treePanel],
				buttons : [{
					text : '提交',
					handler : function(btn) {
						btn.disable();
						var checkedNodeList = treePanel.getChecked();
						var nodes = "";
						for (var i = 0; i < checkedNodeList.length; i++) {
							if (!checkedNodeList[i].hasChildNodes()) {
								nodes += checkedNodeList[i].getPath() + ",";
							}
						}
						
						Ext.Ajax.request({
									url : 'auth/save.do',
									method : 'POST',
									params : {
										roleId : roleId,
										systemId : sysid,
										nodes : nodes
									},
									success : function(response, options) {
										var result = Ext.util.JSON.decode(response.responseText);
										Ext.Msg.show({
													title : '提示信息',
													msg : result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO,
													fn : function() {
														treeWin.close();
														sysDS.reload();
													}
												});
									}
								});
					}
				}]
			});
			treeWin.show();
		});

		var sysWin = new Ext.Window({
					title : '子系统列表',
					width : 250,
					height : 300,
					layout : 'fit',
					modal : true,
					items : [sysGrid]
				});
		sysWin.show();
	}
}

function init() {
	initRolePage();
}
