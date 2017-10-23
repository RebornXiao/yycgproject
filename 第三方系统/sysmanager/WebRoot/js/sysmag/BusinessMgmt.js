function init() {
	Ext.BLANK_IMAGE_URL = 'image/s.gif';
	Ext.QuickTips.init();
	// /////////////////////////////////////////////////////////////////////////
	// 业务列表
	// /////////////////////////////////////////////////////////////////////////
	var bussGridDs = new Ext.data.JsonStore({
				root : 'rows',
				totalProperty : 'total',
				fields : ['businessId', 'businessCode', 'businessName',
						'showOrder', 'remark'],
				proxy : new Ext.data.HttpProxy({
							url : 'business/list.do',
							method : 'POST'
						})
			});
	bussGridDs.load({
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
				header : '业务名称',
				dataIndex : 'businessName',
				width : 120
			}, {
				header : '业务代码',
				dataIndex : 'businessCode',
				width : 70
			}, {
				header : '备注',
				dataIndex : 'remark',
				width : 150
			}]);
	var gridToolBar = eval("("+Ext.get('toolbar').getValue()+")");
	
	//gridToolBar[gridToolBar.length]={text:'帮助',cls:'x-btn-text-icon', icon:'image/icons/whole_main_help.gif', handler:function(){window.open("/help/help.html?anchor=yewuguanli","帮助文档");}};

	//手工同步按钮
/*	var gridToolBar=
        [{
			id : 'bt_syn',
			text : '手工同步业务信息',
			icon : 'image/icons/package_add.png',
			cls : 'x-btn-text-icon',
			handler : AjaxSunBusData
		}];*/
	
	
	function AjaxSunBusData(){
		 Ext.MessageBox.confirm('提示信息', '确定要执行业务信息同步操作吗？', function(bt) {
	  			if (bt == 'no') {
	  				return;
	  			}
	    Ext.getBody().mask("业务信息同步中.请稍等...","x-mask-loading");   
		Ext.Ajax.request({
			url : '../uinsyndata.action',
			method : 'POST',
			params : {
				method : 'bussyn'
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
								icon : Ext.MessageBox.INFO
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
				   Ext.Msg.alert('业务信息同步', '业务信息同步失败,未知错误！');   
				      }
		});  
		 }); 
       }
  
	
	/*[{
				id : 'bt_add',
				text : '添加',
				icon : 'ext/examples/shared/icons/fam/add.gif',
				cls : 'x-btn-text-icon bmenu',
				handler : addBusiness
			}, '-', {
				id : 'bt_edit',
				text : '编辑',
				icon : 'ext/examples/shared/icons/fam/plugin.gif',
				cls : 'x-btn-text-icon bmenu',
				handler : editBusiness
			}, '-', {
				id : 'bt_delete',
				text : '删除',
				icon : 'ext/examples/shared/icons/fam/delete.gif',
				cls : 'x-btn-text-icon bmenu',
				handler : deleteBusiness
			}];*/
	var gridBottomBar = new Ext.PagingToolbar({
				pageSize : 30,
				store : bussGridDs,
				displayInfo : true,
				displayMsg : '共 {2} 条记录，显示第 {0} 到 {1} 条',
				emptyMsg : '无记录'
			});
	var bussGrid = new Ext.grid.GridPanel({
				title : '业务列表',
				frame : true,
				stripeRows : true,
				layout : 'fit',
				tbar : gridToolBar,
				bbar : gridBottomBar,
				viewConfig : {
					forceFit : true
				},
				loadMask : {
                    msg : '正在加载...'
                },
				height : 400,
				ds : bussGridDs,
				cm : cm,
				sm : sm
			});
	// /////////////////////////////////////////////////////////////////////////
	// 面板
	// /////////////////////////////////////////////////////////////////////////
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [bussGrid]
			});

	function addBusiness() {
		openBussWindow();
	}

	function editBusiness() {
		var selections = bussGrid.getSelectionModel().getSelections();
		if (selections.length == 0) {
			Ext.Msg.show({
						title : '提示信息',
						msg : '请选择一行数据',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING

					});
			return;
		}
		var editForm = openBussWindow().fp.getForm().loadRecord(selections[0]);
		editForm.findField('businessCode').getEl().dom.readOnly = true;

	}

	function deleteBusiness() {
		var selections = bussGrid.getSelectionModel().getSelections();
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
					var bussId = selections[0].get('businessId');
					Ext.Ajax.request({
								url : 'business/delete.do',
								method : 'POST',
								params : {
									businessId : bussId
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
														bussGridDs.reload();
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

	function openBussWindow() {
		var bussFormPanel = new Ext.FormPanel({
			id : 'business',
			frame : true,
			labelAlign : "right",
			labelWidth : 100,
			items : [{
						xtype : "hidden",
						id : 'businessId',
						name : "businessId",
						width : 180
					}, {
						xtype : "textfield",
						fieldLabel : "业务名称",
						id : 'businessName',
						name : "businessName",
						width : 180,
						allowBlank : false,
						maxLength:32,
						validator:checkValidInput
					}, {
						xtype : "textfield",
						fieldLabel : "业务代码",
						name : "businessCode",
						width : 180,
						allowBlank : false,
						maxLength:16,
						validator:checkValidInput
					}, {
						xtype : "textfield",
						fieldLabel : "备注",
						name : "remark",
						width : 180,
						maxLength:100
					}, {
						xtype : "numberfield",
							allowBlank : false,
						fieldLabel : "显示顺序",
						name : "showOrder",
						width : 80,
						validator : validateShowOrder
					}],
			buttons : [{
				text : '提交',
				handler : function(form, action) {
					var fp = bussFormPanel.getForm();
					if (!fp.isValid()) {
						return;
					}
					fp.submit({
								url : 'business/save.do',
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
														bussWin.close();
														bussGridDs.reload();
													}
												});
									}
								}
							});
				}
			}, {
				text : '重置',
				handler : function() {
					bussFormPanel.getForm().reset();
				}
			}]
		});
		// ////////////////////////////////////////////////////////////////////
		// 业务表单
		// ////////////////////////////////////////////////////////////////////
		var bussWin = new Ext.Window({
					title : '业务表单',
					width : 350,
					items : [bussFormPanel],
					modal : true
				});
		bussWin.show();
		return {
			fp : bussFormPanel,
			win : bussWin
		};
	}
	function validateShowOrder(order) {
		if (order < 1 || order > 1000) {
			return false;
		}
		return true;
	}

}