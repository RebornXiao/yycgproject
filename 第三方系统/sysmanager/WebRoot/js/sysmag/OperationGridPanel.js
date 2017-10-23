OperationGridPanel = function(config, toolbar, bottomModuleNode) {
	Ext.apply(this, config);
	this.store = new Ext.data.JsonStore({
				root : 'rows',
				fields : ['operationId', 'operationName', 'operationCode',
						'moduleId', 'moduleName', 'method', 'icon', 'showOrder'],
				proxy : new Ext.data.HttpProxy({
							url : 'module/loadList.do',
							method : 'POST'
						})
			});
	var rn = new Ext.grid.RowNumberer({header:'序号', width:40});
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				header : ''
			});
	var gridToolBar=
        [{
			id : 'add_operation', 
			text : '添加',
			icon : 'image/icons/package_add.png',
			cls : 'x-btn-text-icon',
			handler : add
		},{
			id : 'del_operation',
			text : '删除',
			icon : 'image/icons/package_delete.png',
			cls : 'x-btn-text-icon',
			handler : del
		},{
			id : 'edit_operation',
			text : '修改',
			icon : 'image/icons/package_green.png',
			cls : 'x-btn-text-icon',
			handler : edit
		}];
	this.columns = [rn, sm, {
				header : '操作编号',
				dataIndex : 'operationId',
				width : 80
			}, {
				header : '操作名称',
				dataIndex : 'operationName',
				width : 100
			}, {
				header : '操作方法',
				dataIndex : 'method',
				width : 100
			}, {
				header : '图标',
				dataIndex : 'icon',
				width : 100
			}, {
				header : '所属上级模块',
				dataIndex : 'moduleName',
				width : 50
			}];
	/*
	 * this.tbar = [{ id : 'bt_add', text : '添加', icon :
	 * 'ext/examples/shared/icons/fam/add.gif', cls : 'x-btn-text-icon bmenu',
	 * handler : add }, '-', { id : 'bt_edit', text : '编辑', icon :
	 * 'ext/examples/shared/icons/fam/plugin.gif', cls : 'x-btn-text-icon
	 * bmenu', handler : edit }, '-', { id : 'bt_delete', text : '删除', icon :
	 * 'ext/examples/shared/icons/fam/delete.gif', cls : 'x-btn-text-icon
	 * bmenu', handler : del }];
	 */
	OperationGridPanel.superclass.constructor.call(this, {
				region : 'center',
				// title : '操作列表',
				frame : true,
				border : true,
				stripeRows : true,
				tbar :gridToolBar,
				height : 400,
				sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
				viewConfig : {
					forceFit : true
				},
				enableDragDrop : true,
				ddGroup : "GridDD",
				dropConfig : {
					appendOnly : true
				},
				renderTo : 'gd',
				buttonAlign : 'left',
				buttons : [{
							text : '保存排列顺序',
							handler : saveOrder,
							tooltip : '<font color="red">使用鼠标拖拽列表某行可改变排列顺序</font>'
						}]
			});
	var ddrow = new Ext.dd.DropTarget(this.getView().el.dom, {
				ddGroup : "GridDD",
				notifyDrop : function(dd, e, data) {
					var rows = dd.grid.getSelectionModel().getSelections();
					var count = rows.length;
					var cindex = dd.getDragData(e).rowIndex;
					var array = [];
					for (var i = 0; i < count; i++) {
						var index = cindex + i;
						array.push(index);
					}
					dd.grid.store.remove(dd.grid.store
							.getById(data.selections[0].id));
					dd.grid.store.insert(cindex, data.selections);

					dd.grid.getView().refresh();
					dd.grid.getSelectionModel().selectRows(array);
				}
			});
	var gdStore = this.store;
	function saveOrder() {
		if (gdStore.getCount() == 0) {
			Ext.Msg.alert('提示信息', '当前列表中无数据!');
			return;
		}
		var resultNode = "";
		var length = gdStore.getCount();
		for (var i = 0; i < length; i++) {
			resultNode += gdStore.getAt(i).data.operationId + ",";
		}

		Ext.Ajax.request({
					url : 'module/saveOrder.do',
					method : 'POST',
					params : {
						ids : resultNode,
						type : 'o'
					},
					success : function(response, options) {
						var result = Ext.util.JSON
								.decode(response.responseText);
						Ext.Msg.show({
									title : '提示信息',
									msg : result.msg,
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.INFO
								});

					},
					failure : function(response, options) {
						Ext.Msg.show({
									title : '提示信息',
									msg : '保存失败',
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.WARNING
								});
					}
				});
	}
	function add() {
		openOptWin('add');
		Ext.getCmp('moduleName').setValue(bottomModuleNode.text);
		Ext.getCmp('moduleId').setValue(bottomModuleNode.id.substr(1,
				bottomModuleNode.id.length));
		
		Ext.getCmp('operationId').disable();
		Ext.getCmp('moduleName').disable();
	}

	function edit() {
		var selections = Ext.getCmp(config.id).getSelectionModel()
				.getSelections();
		if (selections.length == 0) {
			Ext.Msg.show({
						title : '提示信息',
						msg : '请选择一行数据',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING

					});
			return;
		}
		var fp = openOptWin('edit').fp.getForm();

		Ext.getCmp('operationId').disable();
		Ext.getCmp('moduleName').disable();
		fp.loadRecord(selections[0]);
	}
	function del() {
		var selections = Ext.getCmp(config.id).getSelectionModel()
				.getSelections();
		if (selections.length == 0) {
			Ext.Msg.show({
						title : '提示信息',
						msg : '请选择一行数据',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING

					});
			return;
		}

		Ext.MessageBox.confirm('提示信息', '确定要执行删除操作吗？', function(bt) {
					if (bt == 'no') {
						return;
					}
					var operationId = selections[0].data.operationId;
					Ext.Ajax.request({
								url : 'operation/delete.do',
								method : 'POST',
								params : {
									operationId : operationId
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
														Ext.getCmp(config.id).store
																.reload();
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
	function openOptWin(cmd) {
		var optFormPanel = new Ext.FormPanel({
			id : 'operation',
			frame : true,
			labelAlign : "right",
			defaults : {
				width : 180
			},
			items : [{
						xtype : "textfield",
						fieldLabel : "操作ID",
						id : 'operationId',
						name : "operationId",
						allowBlank :true,
						maxLength : 32/*,
						validator:checkValidInput*/
					}, {
						xtype : "textfield",
						id : 'operationName',
						fieldLabel : "操作名称",
						name : "operationName",
						allowBlank : false,
						maxLength : 32,
						validator:checkValidInput
					}, {
						xtype : "textfield",
						id : 'method',
						fieldLabel : '操作方法',
						name : "method",
						maxLength : 100
					}, {
						xtype : "textfield",
						fieldLabel : "图标",
						name : "icon",
						maxLength : 250
					}, {
						xtype : "numberfield",
						fieldLabel : "显示顺序",
						name : "showOrder",
						allowBlank : false,
						maxLength : 4
					}, {
						xtype : "textfield",
						id : 'moduleName',
						fieldLabel : '所属模块',
						name : "moduleName",
						readOnly : true
					}, {
						xtype : 'hidden',
						id : 'moduleId',
						name : 'moduleId'
					}],
			buttons : [{
				text : '提交',
				handler : function(form, action) {
					var fp = optFormPanel.getForm();
					if (!fp.isValid()) {
                        return;
                    }
					Ext.getCmp('operationId').enable();
					Ext.getCmp('moduleName').enable();
					fp.submit({
								url : 'operation/save.do',
								method : 'POST',
								params : {
									cmd : cmd
								},
								waitMsg : '正在保存数据...',
								success : function(form, action) {
									if (action.result.status == false) {
										Ext.Msg.show({
													title : '提示信息',
													msg : action.result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
										Ext.getCmp('operationId').disable();
										Ext.getCmp('moduleName').disable();
									}
									if (action.result.status == true) {
										Ext.Msg.show({
													title : '提示信息',
													msg : action.result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO,
													fn : function() {
														optWin.close();
														Ext.getCmp(config.id).store
																.reload();
													}
												});
									}
								}
							});
				}
			}]
		});
		var optWin = new Ext.Window({
					title : '操作表单',
					width : 350,
					items : [optFormPanel],
					modal : true,
					resizable : false
				});
		optWin.show();
		return {
			fp : optFormPanel,
			win : optWin
		};
	}
};

Ext.extend(OperationGridPanel, Ext.grid.GridPanel, {
			load : function(node) {
				this.store.load({
							params : {
								node : node
							}
						});
			}
		});
