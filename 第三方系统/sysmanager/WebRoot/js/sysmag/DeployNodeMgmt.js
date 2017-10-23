function test() {

	Ext.BLANK_IMAGE_URL = 'image/s.gif';
	Ext.QuickTips.init();
	// //////////////////////////////////////////////////////////////
	// 节点管理列表 //
	// /////////////////////////////////////////////////////////////
	function testGrid() {
		var sysId = '';
		var rn = new Ext.grid.RowNumberer();
		var sm = new Ext.grid.CheckboxSelectionModel({
					singleSelect : true,
					header : ''
				});

		var cm = new Ext.grid.ColumnModel([rn, sm,	{
					header : '节点名称',
					dataIndex : 'nodeName',
					width : 80
				}, {
					header : '所属系统ID ',
					dataIndex : 'sysId',
					hidden : true,
					width : 120
				}, {
					header : '所属系统',
					dataIndex : 'sysName',
					width : 100
				}, {
					header : '节点URL',
					dataIndex : 'nodeUrl',
					width : 120
				}, {
					header : '节点图标',
					dataIndex : 'nodeIcon',
					width : 120
				}]);

		var ds = new Ext.data.JsonStore({
					root : 'rows',
					totalProperty : 'total',
					fields : ['nodeId', 'nodeName', 'sysId', 'sysName',
							'nodeUrl', 'nodeIcon', 'showOrder'],
					proxy : new Ext.data.HttpProxy({
								url : 'deployNode/list.do',
								method : 'POST'
							})
				});
		var grid = new Ext.grid.GridPanel({
					id : 'grid',
					title : '节点列表',
					iconCls:'node-deploynode',
					stripeRows : true,
					region : 'center',
					margins : '1 1 1 1',
					//split : true,
					frame : true,
					ds : ds,
					cm : cm,
					sm : sm,
					border : true,
					tbar :/* eval("("+Ext.get('toolbar').getValue()+")"),*/[{
								id : 'bt_add',
								text : '添加',
								icon : 'ext/examples/shared/icons/fam/add.gif',
								cls : 'x-btn-text-icon bmenu',
								handler : toadd
							}, '-', {
								id : 'bt_edit',
								text : '编辑',
								icon : 'ext/examples/shared/icons/fam/plugin.gif',
								cls : 'x-btn-text-icon bmenu',
								handler : toedit
							}, '-', {
								id : 'bt_delete',
								text : '删除',
								icon : 'ext/examples/shared/icons/fam/delete.gif',
								cls : 'x-btn-text-icon bmenu',
								handler : todelete
							}],
					bbar : new Ext.PagingToolbar({
								pageSize : 30,
								store : ds,
								displayInfo : true,
								displayMsg : '共 {2} 条记录，显示第 {0} 到 {1} 条',
								emptyMsg : '无记录'
							}),
					viewConfig : {
						forceFit : true
					}
				});
		// //////////////////////////////////////////////////////////
		// /添加页面 //
		// ///////////////////////////////////////////////////////////
		function toadd() {
			var nodepareid = document.getElementById("sysId").value;
			var nodeparename = document.getElementById("sysName").value;
			if (nodepareid == "") {
				Ext.MessageBox.show({
							title : '提示信息',
							msg : '请首先选择子系统!',
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.WARNING
						});
				return false;

			}

			var win = showWindow();
			win.fs.getForm().findField("sysId").setValue(nodepareid);
			win.fs.getForm().findField("sysName").setValue(nodeparename);

		}

		function showWindow(target) {

			var fs = new Ext.FormPanel({
						frame : true,
						border : false,
						labelAlign : 'right',
						labelWidth : 100,
						width : 300,
						autoHeight : true,
						waitMsgTarget : true,
						items : [new Ext.form.TextField({
											fieldLabel : '节点名称',
											name : 'nodeName',
											anchor : '90%',
											allowBlank : false,
											blankText : '节点名称不能为空',
											maxLength:100,
											validator:checkValidInput
										}),

								new Ext.form.TextField({
											fieldLabel : '节点URL',
											name : 'nodeUrl',
											anchor : '90%',
											allowBlank : false,
											blankText : '节点URL不能为空',
											maxLength:100,
											vtype:'url'

										}), new Ext.form.Hidden({
											id : 'sysId',
											fieldLabel : '父类id',
											name : 'sysId',
											anchor : '90%'

										}), new Ext.form.TextField({
											fieldLabel : '所属子系统',
											name : 'sysName',
											readOnly : true,
											anchor : '90%'
										}), new Ext.form.TextField({
											fieldLabel : '节点图标',
											name : 'nodeIcon',
											anchor : '90%',
											maxLength:250

										}), new Ext.form.NumberField({
											fieldLabel : '显示顺序',
											name : 'showOrder',
											anchor : '90%',
											allowBlank:false,
											maxLength:4

										})

						]
					});
			var submit = fs.addButton({
				text : '提交',
				disabled : false,
				handler : function() {
					var bform = fs.getForm();
					if (bform.isValid()) {
						bform.submit({
									url : 'deployNode/save.do',
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
															win.close();
															ds.load({
																		params : {
																			start : 0,
																			limit : 30
																		}
																	});
														}
													});
										}

									}
								});
					}
				}
			});

			// // 弹出窗口
			win = new Ext.Window({
						title : '节点表单',
						closable : true,
						width : 350,
						autoHeight : true,
						resizable : true,
						modal : true,
						// border:false,
						plain : true,
						layout : 'fit',
						items : [fs]
					});
			win.show();

			return {
				win : win,
				fs : fs
			};
		}

		// 选择
		function isSelected(grid) {
			var selection = grid.getSelectionModel().getSelections();
			if (selection.length == 0) {
				return false;
			}
			// alert(selection[0].get("paramid"));

			return true;
		}
		// ///////////////////////////////////////////////
		// 修改面板 //
		// //////////////////////////////////////////////
		function toedit() {
			if (!isSelected(grid)) {
				Ext.MessageBox.show({
							title : '提示信息',
							msg : '请选择一行！',
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.WARNING
						})
				return;
			}
			var selection = grid.getSelectionModel().getSelections();

			editWindow().fs.getForm().loadRecord(selection[0]);
			// editWindow();

		}

		// 弹出修改页面
		function editWindow() {
			var fse = new Ext.FormPanel({
						frame : true,
						border : false,
						labelAlign : 'right',
						labelWidth : 100,
						width : 380,
						autoHeight : true,
						waitMsgTarget : true,
						items : [new Ext.form.TextField({
											fieldLabel : '节点Id',
											name : 'nodeId',
											anchor : '70%',
											readOnly : true,
											allowBlank : false
										}), new Ext.form.TextField({
											fieldLabel : '节点名称',
											name : 'nodeName',
											anchor : '70%',
											allowBlank : false,
											blankText : '节点名称不能为空'
										}),

								new Ext.form.TextField({
											fieldLabel : '节点URL',
											name : 'nodeUrl',
											anchor : '70%',
											allowBlank : false,
											blankText : '节点URL不能为空'

										}), new Ext.form.Hidden({
											id : 'sysId',
											fieldLabel : '父类id',
											name : 'sysId',
											anchor : '70%',
											allowBlank : false
										}), new Ext.form.TextField({
											fieldLabel : '所属系统',
											name : 'sysName',
											anchor : '70%',
											readOnly : true,
											allowBlank : false
										}), new Ext.form.TextField({
											fieldLabel : '节点图标',
											name : 'nodeIcon',
											anchor : '70%'

										}), new Ext.form.NumberField({
											fieldLabel : '排序号',
											name : 'showOrder',
											anchor : '70%'

										})

						]
					});

			var submit = fse.addButton({
				text : '提交',
				disabled : false,
				handler : function() {
					var bform = fse.getForm();
					if (bform.isValid()) {
						bform.submit({
									url : 'deployNode/save.do',
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
															editWin.close();
															ds.load({
																		params : {
																			start : 0,
																			limit : 12
																		}
																	});
														}
													});
										}

									}
								});
					}
				}
			});

			// // 弹出区域编辑窗口
			editWin = new Ext.Window({
						title : '修改节点表单',
						closable : true,
						width : 400,
						autoHeight : true,
						resizable : true,
						modal : true,
						plain : true,
						layout : 'fit',
						items : [fse]
					});
			editWin.show();
			return {
				win : editWin,
				fs : fse
			};

		}

		// //////////////////////////////////////////////
		// 删除 //
		// //////////////////////////////////////////////
		// 删除
		function todelete() {
			if (!isSelected(grid)) {
				Ext.MessageBox.show({
							title : '提示信息',
							msg : '请选择一行！',
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.WARNING
						})
				return;
			}
			Ext.MessageBox.confirm('提示信息', '您确定要执行删除操作吗？', todel);
		}

		function todel(bt) {
			if (bt == "no") {
				return;
			}

			var selection = grid.getSelectionModel().getSelections();
			var nodeid = selection[0].get("nodeId");
			Ext.Ajax.request({
						url : 'deployNode/delete.do',
						method : 'POST',
						params : {
							nodeId : nodeid
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
												ds.load({
															params : {
																start : 0,
																limit : 10,
																sysId : sysId
															}
														});
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

		}

		return grid;

	}
	var paramGrid = new testGrid();

	tree = new Ext.tree.TreePanel({
				margins : '1 1 1 1',
				id : 'tree',
				useArrows : true,
                animate : true,
				collapsible : true,
				bodyStyle:'padding:1px 0px 0px 0px',
				region : 'west',
				layout : 'accordion',
				loadMask : {
                   msg : '正在加载...'
                },
				width : 150,
				contentEl : 'west-div',
				autoScroll : true,
				root : new Ext.tree.AsyncTreeNode({
							id : '-1',
							text : 'root'
						}),
				loader : new Ext.tree.TreeLoader({
							dataUrl : 'deployNode/tree.do'
						}), //
				title : '子系统列表',
				iconCls:'node-system-normal',
				rootVisible : false,
				anchor : '100%',
				lines : true
			});

	tree.on("click", nodeClick);

	function nodeClick(node, event) {
		event.stopEvent();
		var attr = node.attributes;
		if (node.attributes.displayType) {
			alert('af');
		} else {
			var sysId = node.id;
			var typename = node.text;
			document.getElementById("sysId").value = sysId;
			// alert(document.getElementById("sysId").value);
			document.getElementById("sysName").value = typename;

			paramGrid = Ext.getCmp('grid');
			var store = paramGrid.getStore();
			Ext.apply(store.baseParams, {
						sysId : sysId
					});
			store.reload({
						params : {
							start : 0,
							limit : 10
						}
					});
		}

	}

	var viewport = new Ext.Viewport({
				layout : 'border',
				enableTabScroll : true,
				items : [tree, paramGrid]
			});

}
