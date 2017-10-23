ModuleGridPanel = function(config, toolbar, sysNode, topModuleNode) {
	Ext.QuickTips.init();
	Ext.apply(this, config);
	this.store = new Ext.data.JsonStore({
				root : 'rows',
				fields : ['moduleId', 'moduleName', 'systemId', 'systemName',
						'upperModuleId', 'upperModuleName', 'url', 'icon',
						'showOrder', 'isUsed'],
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
			id : 'add_module', 
			text : '添加',
			icon : 'image/icons/package_add.png',
			cls : 'x-btn-text-icon',
			handler : add
		},{
			id : 'del_module',
			text : '删除',
			icon : 'image/icons/package_delete.png',
			cls : 'x-btn-text-icon',
			handler : del
		},{
			id : 'edit_module',
			text : '修改',
			icon : 'image/icons/package_green.png',
			cls : 'x-btn-text-icon',
			handler : edit
		}];
	/*
	 * this.tbar = eval("("+toolbar.toString()+")");[{ id : 'bt_add', text :
	 * '添加', icon : 'ext/examples/shared/icons/fam/add.gif', cls :
	 * 'x-btn-text-icon bmenu', handler : add }, '-', { id : 'bt_edit', text :
	 * '编辑', icon : 'ext/examples/shared/icons/fam/plugin.gif', cls :
	 * 'x-btn-text-icon bmenu', handler : edit }, '-', { id : 'bt_delete', text :
	 * '删除', icon : 'ext/examples/shared/icons/fam/delete.gif', cls :
	 * 'x-btn-text-icon bmenu', handler : del }];
	 */
	this.columns = [rn, sm, {
				header : '模块编号',
				dataIndex : 'moduleId',
				width : 50
			}, {
				header : '模块名称',
				dataIndex : 'moduleName',
				width : 70
			}, {
				header : '所属子系统',
				dataIndex : 'systemName',
				width : 70
			}, {
				header : '所属上级模块',
				dataIndex : 'upperModuleName',
				width : 70,
				renderer : function(value) {
					return value || '无';
				}
			}, {
				header : 'URL',
				dataIndex : 'url',
				width : 100
			}, {
				header : '图标',
				dataIndex : 'icon',
				width : 100
			}];
	ModuleGridPanel.superclass.constructor.call(this, {
				// title : '模块列表',
				region : 'center',
				border : true,
				stripeRows : true,
				frame : true,
				tbar : gridToolBar,
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
				ddGroup : "GridDD", // Data come from
				// copy:true,
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
			Ext.Msg.alert('提示信息','当前列表中无数据!');
			return ;
		}
		var resultNode = "";
		var length = gdStore.getCount();
		for (var i = 0; i < length; i++) {
			resultNode += gdStore.getAt(i).data.moduleId + ",";
		}
		
		Ext.Ajax.request({
					url : 'module/saveOrder.do',
					method : 'POST',
					params : {
						ids : resultNode,
						type:'m'
					},
					success : function(response, options) {
						var result = Ext.util.JSON.decode(response.responseText);
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
		openModuleWin('add');
		Ext.getCmp('systemName').setValue(sysNode.text);
		Ext.getCmp('systemId')
				.setValue(sysNode.id.substr(1, sysNode.id.length));
		if (topModuleNode == null) {
			Ext.getCmp('upperModuleName').setValue('无');
			Ext.getCmp('upperModuleId').setValue('0');
		} else {
			Ext.getCmp('upperModuleName').setValue(topModuleNode.text);
			Ext.getCmp('upperModuleId').setValue(topModuleNode.id.substr(1,
					topModuleNode.id.length));
		}
		Ext.getCmp('moduleId').disable();
		Ext.getCmp('systemName').disable();
		Ext.getCmp('upperModuleName').disable();

	}
	function edit() {
		var selections = Ext.getCmp(config.id).getSelectionModel()
				.getSelections();
		// var selections = this.getSelectionModel().getSelections();
		if (selections.length == 0) {
			Ext.Msg.show({
						title : '提示信息',
						msg : '请选择一行数据',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING

					});
			return;
		}
		var fp = openModuleWin('edit').fp.getForm();
		// document.getElementById('moduleId').readOnly = true;
		Ext.getCmp('moduleId').disable();
		Ext.getCmp('systemName').disable();
		Ext.getCmp('upperModuleName').disable();
		fp.loadRecord(selections[0]);
		fp.findField('isUsed').items.first().setValue(selections[0]
				.get("isUsed"));

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

		Ext.MessageBox.confirm('提示信息', '确定要执行删除操作吗?', function(bt) {
					if (bt == 'no') {
						return;
					}
					var moduleId = selections[0].data.moduleId;
					Ext.Ajax.request({
								url : 'module/delete.do',
								method : 'POST',
								params : {
									moduleId : moduleId
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

	function openModuleWin(cmd) {
		var moduleFormPanel = new Ext.FormPanel({
			id : 'module',
			frame : true,
			labelAlign : "right",
			defaults : {
				width : 180
			},
			items : [{
						xtype : "textfield",
						fieldLabel : "模块ID",
						id : 'moduleId',
						name : "moduleId",
						allowBlank : true,
						maxLength : 32/*,
						validator:checkValidInput*/
					}, {
						xtype : "textfield",
						id : 'moduleName',
						fieldLabel : "模块名称",
						name : "moduleName",
						allowBlank : false,
						maxLength : 32,
						validator:checkValidInput
					}, {
						xtype : "textfield",
						fieldLabel : "URL",
						name : "url",
						maxLength : 100
					}, {
						xtype : "textfield",
						fieldLabel : "图标",
						name : "icon",
						maxLength : 250
					}, {
						xtype : "hidden",
						id : 'systemId',
						name : "systemId"
					}, {
						xtype : "numberfield",
						fieldLabel : "显示顺序",
						name : "showOrder",
						allowBlank : false,
						maxLength : 4
					}, {
						xtype : "textfield",
						id : 'systemName',
						fieldLabel : '所属子系统',
						name : "systemName",
						readOnly : true
					}, {
						xtype : 'hidden',
						id : 'upperModuleId',
						name : 'upperModuleId'
					}, {
						xtype : "textfield",
						id : 'upperModuleName',
						fieldLabel : "所属上级模块",
						name : "upperModuleName",
						readOnly : true
					}, new Ext.form.RadioGroup({
								fieldLabel : '是否使用',
								id : 'isUsed',
								width : 100,
								items : [{
											boxLabel : '是',
											name : 'isUsed',
											inputValue : 1,
											checked : true
										}, {
											boxLabel : '否',
											name : 'isUsed',
											inputValue : 0

										}]
							})],
			buttons : [{
				text : '提交',
				handler : function(form, action) {
					var fp = moduleFormPanel.getForm();
					if (!fp.isValid()) {
                        return;
                    }
					Ext.getCmp('moduleId').enable();
					Ext.getCmp('systemName').enable();
					Ext.getCmp('upperModuleName').enable();
					fp.submit({
								url : 'module/save.do',
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
										Ext.getCmp('moduleId').disable();
										Ext.getCmp('systemName').disable();
										Ext.getCmp('upperModuleName').disable();
									}
									if (action.result.status == true) {
										Ext.Msg.show({
													title : '提示信息',
													msg : action.result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO,
													fn : function() {
														moduleWin.close();
														Ext.getCmp(config.id).store.reload();
													}
												});
									}
								}
							});
				}
			}]
		});
		var moduleWin = new Ext.Window({
					title : '模块表单',
					width : 350,
					items : [moduleFormPanel],
					modal : true,
					resizable : false
				});
		moduleWin.show();
		return {
			fp : moduleFormPanel,
			win : moduleWin
		};
	}

};

Ext.extend(ModuleGridPanel, Ext.grid.GridPanel, {
			load : function(node) {
				this.store.load({
							params : {
								node : node
							}
						});
			}
		});
