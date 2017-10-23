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
				header : '是否分配操作权限',
				dataIndex : 'isAssigned',
				renderer : function(value) {
					if(value == "0") {
						return "未分配";
					}
					else {
						return "已分配";
					}
				}
			}

	]);

	var roleDS = new Ext.data.JsonStore({
				root : 'rows',
				totalProperty : 'total',
				fields : ['roleId', 'roleName', 'roleDes', 'isAssigned'],
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
			
	//var gridToolBar = eval("(" + Ext.get('toolbar').getValue() + ")");
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
	//gridToolBar[gridToolBar.length]={text:'帮助',cls:'x-btn-text-icon', icon:'image/icons/whole_main_help.gif', handler:function(){window.open("/help/help.html?anchor=jiaoseguanli","帮助文档");}};
	
	var roleGrid = new Ext.grid.GridPanel({
		title : '角色列表',
		frame : true,
		ds : roleDS,
		cm : roleCM,
		sm : roleSM,
		border : true,
		margins : '5 5 5 5',
		tbar :gridToolBar,
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
					allowBlank : false,
					validator:checkValidInput
				});
		/*var areaId = new Ext.form.Hidden({
					name : 'areaId',
					id : 'areaId'
				});
		  var areaName = new Ext.form.ComboBox({
					fieldLabel : '所属区域',
					// emptyText : '选择区域',
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
					allowBlank : false
				});*/
		var roleDes = new Ext.form.TextArea({
					fieldLabel : '角色描述',
					name : 'roleDes',
					anchor : '90%',
					maxLength : 200,
					height : 70,
					maxLength : 100
				});
		/*var areaTree = new Ext.tree.TreePanel({
					autoScroll : true,
					useArrows : true,
					height : 200,
					rootVisible : false,
					loader : new Ext.tree.TreeLoader({
								dataUrl : 'area/loadRoleAreaTree.do'
							})
				});
		var areaRoot = new Ext.tree.AsyncTreeNode({
					id : 'root',
					text : '区域列表'
				});
		areaTree.setRootNode(areaRoot);
		areaTree.on('click', function(node) {
			        if (node.parentNode.id == 'root' ) {
			        	if(node.isExpanded()){
			        	    node.collapse();
			        	    return;
			        	} else {
			        	    node.expand();
			        	    return;
			        	}
			        }
					areaId.setValue(node.id);
					areaName.setValue(node.text);
					areaName.collapse();
				});
		areaName.on('expand', function() {
					areaTree.render('treedelete');
				});*/
		var panel = new Ext.FormPanel({
					id : 'role',
					frame : true,
					border : false,
					labelAlign : 'right',
					labelWidth : 100,
					width : 380,
					height : 200,
					waitMsgTarget : true,
					items : [roleId, roleName, /*areaId, areaName,*/ roleDes],
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
		/*Ext.getCmp('areaId').setValue(selected.get('area').areaId);
		Ext.getCmp('areaName').setValue(selected.get('area').areaName);*/

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
		var delWaitMask = new Ext.LoadMask(Ext.getBody(),{msg:'正在删除角色...'});
		if (bt == "no") {
			return;
		}
		delWaitMask.show();
		var currPage = roleGrid.getBottomToolbar().getPageData().activePage;
		var selection = roleGrid.getSelectionModel().getSelections();
		Ext.Ajax.request({
					method : 'POST',
					url : 'role/delete.do',
					params : {
						roleId : selection[0].get("roleId")
					},
					success : function(response, options) {
						delWaitMask.hide();
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
		var sysid = '';
		var allCheckedStatus = false;
		var leftTreeExpandStatus = false;
		var rightTreeExpandStatus = true;
		var sysTreeLoadMask = new Ext.LoadMask(Ext.getBody(), {
					msg : "正在加载子系统树..."
				});
		var tree2 = new Ext.tree.TreePanel({
			margins : '1 1 1 1',
			id : 'tree2',
			title : '选择系统',
			useArrows : true,
			animate : true,
			collapsible : true,
			bodyStyle : 'padding:1px 0px 0px 0px',
			region : 'west',
			width : 220,
			autoScroll : true,
			rootVisible : false,
			root : new Ext.tree.AsyncTreeNode({
						id : '-1',
						text : 'root'
					}),
			loader : new Ext.tree.TreeLoader({
						dataUrl : 'auth/loadSystemTree.do',
						baseParams : {
							'roleId' : roleId
							// 'systemId' : sysid
						},
						listeners : {
							beforeload : function() {
								sysTreeLoadMask.show();
							}
						}
					}),
			tbar : [{
				text : '全部展开',
				iconCls : 'button-tree-expand',
				handler : function() {
					if (leftTreeExpandStatus) {
						Ext.getCmp('tree2').collapseAll();
						leftTreeExpandStatus = false;
					} else {
						Ext.getCmp('tree2').expandAll();
						leftTreeExpandStatus = true;
					}
					this.setText(leftTreeExpandStatus ? '全部收起' : '全部展开');
					this.setIconClass(leftTreeExpandStatus
							? 'button-tree-collapse'
							: 'button-tree-expand');

				}
			}, {
				text : '刷新',
				cls : 'x-btn-text-icon bmenu',
				icon : 'image/icons/arrow_rotate_clockwise.png',
				tooltip:'点击查看最新数据及状态',
				handler : function() {
					Ext.getCmp('tree2').getRootNode().reload();
				}
			}],
			bbar : ['<img src="image/icons/award_star_gold_2.png">全部分配&nbsp;&nbsp;'
					+ '<img src="image/icons/award_star_silver_3.png">部分分配&nbsp;&nbsp;'
					+ '<img src="image/icons/award_star_bronze_1.png">没有分配'],
			listeners : {
				contextmenu : function(node, e) {
				//	if (node.isLeaf()) {
						node.ui.addClass('x-node-ctx');
						var menu = new Ext.menu.Menu({
							id : 'feeds-ctx',
							items : [{
										id : 'assign-all',
										icon : 'image/icons/key.png',
										text : '分配全部权限',
										scope : this,
										handler : function() {
											node.ui.removeClass('x-node-ctx');
											treeSaveMask.show();
											var sysId = "";
											var dnId = "";
											if (node.isLeaf()) {
												sysId = node.parentNode.id;
												dnId = node.id;
												dnId = dnId.substr(1, dnId.length- 1);
											} else {
											   sysId = node.id;
											}
											sysId = sysId.substr(1,sysId.length - 1);
											assignAllAuth(sysId, dnId);
										}
									}, {
										text : '清除全部权限',
										icon : 'image/icons/lock.png',
										scope : this,
										handler : function() {
											node.ui.removeClass('x-node-ctx');
											treeSaveMask.show();
											var sysId = "";
                                            var dnId = "";
                                            if (node.isLeaf()) {
                                                sysId = node.parentNode.id;
                                                dnId = node.id;
                                                dnId = dnId.substr(1, dnId.length- 1);
                                            } else {
                                               sysId = node.id;
                                            }
                                            sysId = sysId.substr(1,sysId.length - 1);
											Ext.Ajax.request({
												url : 'auth/save.do',
												method : 'POST',
												params : {
													roleId : roleId,
													systemId : sysId,
													deployNodeId : dnId,
													nodes : ''
												},
												success : function(response,options) {
													treeSaveMask.hide();
													var result = Ext.util.JSON.decode(response.responseText);
													Ext.Msg.show({
														title : '提示信息',
														msg : result.msg + ', 请刷新左侧系统树查看状态',
														buttons : Ext.MessageBox.OK,
														icon : Ext.MessageBox.INFO
													});
												}
											});
										}
									}],
							 listeners:{
							      hide : function(){
							         node.ui.removeClass('x-node-ctx');
							      }
							 
							 }		
						});
						menu.showAt(e.getXY());
					//}
				}
			}

		});

		function assignAllAuth(sysId, dnId) {
			Ext.Ajax.request({
						url : 'auth/saveAll.do',
						method : 'POST',
						params : {
							roleId : roleId,
							systemId : sysId,
							deployNodeId : dnId
						},
						success : function(response, options) {
							treeSaveMask.hide();
							var result = Ext.util.JSON
									.decode(response.responseText);
							Ext.Msg.show({
										title : '提示信息',
										msg : result.msg + ', 请刷新左侧系统树查看状态',
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO
									});
						},
						failure : function(response, options) {
							treeSaveMask.hide();
							Ext.Msg.show({
										title : '提示信息',
										msg : "连接失败",
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						}
					});

		}

		var tree1 = new Ext.tree.TreePanel({
			margins : '1 1 1 1',
			id : 'tree1',
			//useArrows : true,
			animate : false,
			checkModel : 'cascade',
			bodyStyle : 'padding:1px 0px 0px 0px',
			region : 'center',
			autoScroll : true,
			autoLoad : false,
			root : new Ext.tree.AsyncTreeNode({
						id : '-1',
						text : 'root'
					}),
			loader : new Ext.tree.TreeLoader({
						dataUrl : 'auth/loadModuleTree.do',
						baseAttrs : {
							uiProvider : Ext.ux.TreeCheckNodeUI
						}
					}),
			title : '选择操作权限',
			rootVisible : false,
			tbar : [{
				id : 'select-btn',
				text : '全部选择',
				iconCls : 'button-tree-allcheck',
				handler : function() {
					if (allCheckedStatus) {
						var leftTreeList = Ext.getCmp('tree1');
						var childrens = leftTreeList.root.childNodes;
						for (var i = 0; i < childrens.length; i++) {
							childrens[i].getUI().check(false);
						}
						allCheckedStatus = false;
					} else {
						var leftTreeList = Ext.getCmp('tree1');
						var childrens = leftTreeList.root.childNodes;
						for (var i = 0; i < childrens.length; i++) {
							childrens[i].getUI().check(true);
						}
						allCheckedStatus = true;
					}
					this.setText(allCheckedStatus ? '全部取消' : '全部选择');
					this.setIconClass(allCheckedStatus
							? 'button-tree-allcancel'
							: 'button-tree-allcheck');

				}
			}, '-', {
				id : 'expand-btn',
				text : '全部收起',
				iconCls : 'button-tree-collapse',
				handler : function() {
					if (rightTreeExpandStatus) {
						Ext.getCmp('tree1').collapseAll();
						rightTreeExpandStatus = false;
					} else {
						Ext.getCmp('tree1').expandAll();
						rightTreeExpandStatus = true;
					}
					this.setText(rightTreeExpandStatus ? '全部收起' : '全部展开');
					this.setIconClass(rightTreeExpandStatus
							? 'button-tree-collapse'
							: 'button-tree-expand');

				}
			}, '->', {
				text : '提交',
				icon : 'image/icons/disk_multiple.png',
				cls : 'x-btn-text-icon',
				handler : function() {
					treeSaveMask.show();
					var checkedNodeList = tree1.getChecked();
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
							systemId : tree1.getLoader().baseParams.systemId,
							deployNodeId : tree1.getLoader().baseParams.deployNodeId,
							nodes : nodes
						},
						success : function(response, options) {
							treeSaveMask.hide();
							var result = Ext.util.JSON
									.decode(response.responseText);
							Ext.Msg.show({
										title : '提示信息',
										msg : result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO,
										fn : function() {
											roleDS.reload();
										}
									});
						}
					});
				}
			}]

		});
		tree2.on("click", nodeClick);
		
		function nodeClick(node, event) {
			Ext.getCmp('select-btn').setText('全部选择');
			Ext.getCmp('select-btn').setIconClass('button-tree-allcheck');
			allCheckedStatus = false;

			Ext.getCmp('expand-btn').setText('全部收起');
			Ext.getCmp('expand-btn').setIconClass('button-tree-collapse');
			rightTreeExpandStatus = true;

			if (node.isLeaf()) {
				moduleTreeLoadMask.show();
				var path = node.getPath();
				var pathArray = path.split('/');
				var systemId = pathArray[2].substr(1, pathArray[2].length);
				var deployNodeId = pathArray[3].substr(1, pathArray[3].length);
				var rightTree = Ext.getCmp('tree1');
				var loader = rightTree.getLoader();
				Ext.apply(loader.baseParams, {
							roleId : roleId,
							systemId : systemId,
							deployNodeId : deployNodeId
						});
				rightTree.root.reload(function() {
							rightTree.expandAll();
						});
			} else {
				if (node.isExpanded()) {
					node.collapse();
				} else {
					node.expand();
				}
			}
		}

		var authWin = new Ext.Window({
					id : 'authWin',
					title : '权限分配',
					width : 600,
					height : 300,
					layout : 'border',
					resizable : false,
					modal : true,
					items : [tree2, tree1]
				});
		authWin.show();

		var moduleTreeLoadMask = new Ext.LoadMask(tree1.getEl(), {
					msg : "正在加载模块树..."
				});
		var treeSaveMask = new Ext.LoadMask(authWin.getEl(), {
					msg : '正在保存权限信息...'
				});
		tree1.on('load', function() {
					moduleTreeLoadMask.hide();
				});
		tree2.on('load', function() {
					sysTreeLoadMask.hide();
				});
		tree2.getLoader().on('loadexception', function() {
					Ext.Msg.alert('提示信息', '加载树节点失败!');
				});
	}
}

function init() {
	initRolePage();
}
