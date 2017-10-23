function init() {
	Ext.BLANK_IMAGE_URL = 'image/s.gif';
	Ext.QuickTips.init();
	
	/*子系统数据源*/
	var store = new Ext.data.JsonStore({
			fields : ['systemId', 'systemName'],
			root : 'rows',
			proxy : new Ext.data.HttpProxy({
					url : 'syswebservicecfg/listSystem.do',
					method : 'POST'
			})
	});
	
	/* 服务类型列表数据源 */
	var webserviceTypeGridDs = new Ext.data.JsonStore({
				totalProperty : 'total',
				root : 'rows',
				fields : ['typeId', 'typeName'],
				proxy : new Ext.data.HttpProxy({
							url : 'syswebservicecfg/listWebserviceType.do',
							method : 'POST'
						})
			});
	
	/* 配置列表数据源 */
	var sysWebserviceGridDs = new Ext.data.JsonStore({
				root : 'rows',
				totalProperty : 'total',
				fields : ['id', 'sysid', 'sysname', 'typeid', 'typename',
						'url', 'port', 'servicename','serviceUrn','namespace'],
				proxy : new Ext.data.HttpProxy({
							url : 'syswebservicecfg/list.do',
							method : 'POST'
						})
			});
	sysWebserviceGridDs.load({
				params : {
					sysname : '',
					start : 0,
					limit : 30
				}
			});
	var rn = new Ext.grid.RowNumberer();

	/* 配置列表中的复选框 */
	var cfgSM = new Ext.grid.CheckboxSelectionModel({
				singleSelect : false
			});
	var cm = new Ext.grid.ColumnModel([rn, cfgSM, {
				header : '子系统',
				dataIndex : 'sysname',
				width : 150
			}, {
				header : '服务类型',
				dataIndex : 'typename',
				width : 150
			}, {
				header : '服务URN',
				dataIndex : 'serviceUrn',
				width : 100
			}, {
				header : '端口',
				dataIndex : 'port',
				width : 100
			}, {
				header : '服务名',
				dataIndex : 'servicename',
				width : 200
			},	{
				header : '服务命名空间',
				dataIndex : 'namespace',
				width : 200
			}]);
	var gridToolBar = eval("("+Ext.get('toolbar').getValue()+")");
	/*var gridToolBar = [{
				id : 'bt_add',
				text : '添加',
				icon : 'image/icons/application_add.png',
				cls : 'x-btn-text-icon bmenu',
				handler : showAddPanel
			}, '-', {
				id : 'bt_edit',
				text : '修改',
				icon : 'image/icons/application_delete.png',
				cls : 'x-btn-text-icon bmenu',
				handler : edit
			}, '-', {
				id : 'bt_delete',
				text : '删除',
				icon : 'image/icons/application_delete.png',
				handler : deleteCfg
			}];*/

	var gridBottomBar = new Ext.PagingToolbar({
				pageSize : 30,
				store : sysWebserviceGridDs,
				displayInfo : true,
				displayMsg : '共 {2} 条记录，显示第 {0} 到 {1} 条',
				emptyMsg : '无记录'
			});
	var sysWebserviceGrid = new Ext.grid.GridPanel({
				frame : true,
				region : 'center',
				stripeRows : true,
				tbar : gridToolBar,
				bbar : gridBottomBar,
				viewConfig : {
					forceFit : true
				},
				ds : sysWebserviceGridDs,
				cm : cm,
				sm : cfgSM
			});

	var searchBar = new Ext.Toolbar({
				region : 'north',
				items : ["子系统名称", {
							xtype : 'textfield',
							id : 'sysname',
							width : 100
						}, {
							id : 'bt_search',
							text : '模糊查询',
							icon : 'image/icons/application_go.png',
							cls : 'x-btn-text-icon bmenu',
							handler : search
						}]
			});
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [searchBar, sysWebserviceGrid]
			});
	function search() {
		var name = Ext.getCmp("sysname").getValue().trim();
		Ext.getCmp("sysname").setValue(name);
		sysWebserviceGridDs.baseParams = {
			sysname : name
		};
		sysWebserviceGridDs.reload();
	}
	function deleteCfg() {
		var rows = cfgSM.getSelections();
		var leng = rows.length;
		if (leng == 0) {
			Ext.MessageBox.show({
						title : '提示信息',
						msg : '请选择一行！',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING
					});
			return;
		}
		var ids = "";
		for (i = 0; i < leng; i++) {
			ids += rows[i].get("id");
			ids += ",";
		}
		Ext.MessageBox.confirm('提示信息', '确定要执行删除操作吗?', function(bt) {
					if (bt === 'no') {
						return;
					}
					Ext.Ajax.request({
								url : 'syswebservicecfg/delete.do',
								method : 'POST',
								params : {
									id : ids
								},
								success : function(response, options) {
									var result = Ext.util.JSON
											.decode(response.responseText);
									if (result.success === true) {
										Ext.Msg.show({
													title : '提示信息',
													msg : result.msg,
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.INFO,
													fn : function() {
														sysWebserviceGridDs
																.reload();
													}
												});
									}
									if (result.success === false) {
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
	function showAddPanel(){
			store.load();
			webserviceTypeGridDs.load();
			var box = new Ext.form.ComboBox({
				fieldLabel : '子系统',
				store : store,
				displayField : 'systemName',
				valueField : 'systemId',
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				allowBlank : false,
				emptyText : '请选择子系统',
				editable : false,
				selectOnFocus : true
			});
			var port = new Ext.form.NumberField({
				fieldLabel : '端口',
				allowNegative : false,
				allowDecimals : false,
				allowBlank : false,
				maxLength:5
			});
			var urn = new Ext.form.TextField({
				fieldLabel:'服务URN',
				allowBlank : false,
                validator:function(value){
                	if(value.trim().length > 0){
                		return true;
                	}
                	else return "该输入项为必填项";
                }
			});
			var serviceName = new Ext.form.TextField({
				fieldLabel : '服务名',
				allowBlank : false,
				validator:function(value){
                	if(value.trim().length > 0){
                		return true;
                	}
                	else return "该输入项为必填项";
                }
			});
			var nameSpace = new Ext.form.TextField({
				fieldLabel : '服务命名空间',
				allowBlank : false,
				validator:function(value){
                	if(value.trim().length > 0){
                		return true;
                	}
                	else return "该输入项为必填项";
                }
			});
			var webserviceTypeSM = new Ext.grid.CheckboxSelectionModel({});
			var webserviceTypeCM = new Ext.grid.ColumnModel([webserviceTypeSM, {
				header : '可选择的服务类型',
				dataIndex : 'typeName',
				width : 300
			}]);
			var grid = new Ext.grid.GridPanel({
				frame : true,
				viewConfig : {
					forceFit : true
				},
				width : 270,
				height : 140,
				autoScroll : true,
				sm : webserviceTypeSM,
				cm : webserviceTypeCM,
				ds : webserviceTypeGridDs
			});
			var addPanel = new Ext.form.FormPanel({
				width : 300,
				height : 340,
				frame : true,
				labelWidth : 80,
				items:[box,port,urn,serviceName,nameSpace,grid],
				buttons : [{
							id : 'submit',
							text : '提交',
							handler : addCfg
						}, {
							id : 'reset',
							text : '重置',
							handler : function() {
								addPanel.getForm().reset();
								webserviceTypeSM.clearSelections();
							}
						}]
			});
			var win = new Ext.Window({
				title : '添加WebService子系统配置',
				width : 300,
				height : 360,
				layout : 'fit',  
				resizable : false,
				modal : true,
				items : [addPanel]
			});
			win.show();
			function addCfg() {
				var url = "syswebservicecfg/insert.do";
				var typeId = grid.getSelections();
				var length = typeId.length;
				if (addPanel.getForm().isValid()) {
					if (length === 0) {
						Ext.Msg.alert('提示信息', '至少选择一个服务类型');
						return;
					}
				} else {
					return;
				}
				var typeIds = "";
				var i = 0;
				for (i = 0; i < length; i++) {
					typeIds += typeId[i].get("typeId");
					typeIds += ",";
				}
				Ext.Ajax.request({
					url : url,
					method : 'POST',
					params : {
						sysid : box.getValue().trim(),
						typeIds : typeIds,
						port : port.getValue(),
						servicename : serviceName.getValue().trim(),
						serviceurn : urn.getValue().trim(),
						namespace : nameSpace.getValue().trim()
					},
					success : function(response, options) {
						var result = Ext.util.JSON
								.decode(response.responseText);
						if (result.status === true) {
							Ext.Msg.show({
										title : '提示信息',
										msg : result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO,
										fn : function() {
											sysWebserviceGridDs.reload();
										}
									});
						}
						if (result.status === false) {
							Ext.Msg.show({
										title : '提示信息',
										msg : result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						}
						win.close();
					}
				});
			}
			
	}
	function edit(){
			var cfgs = cfgSM.getSelections();
			if(cfgs.length == 0){
				Ext.Msg.alert("提示信息","请选择一个配置");
				return;
			}
			if(cfgs.length > 1){
				Ext.Msg.alert("提示信息","只能选择一个配置");
				return;
			}
			store.load();
			webserviceTypeGridDs.load();
			var cfg = cfgs[0];
			var id = new Ext.form.Hidden();
			var typeid = new Ext.form.Hidden();
			var sysid = new Ext.form.Hidden();
			var sysBox = new Ext.form.ComboBox({
				fieldLabel : '子系统',
				store : store,
				displayField : 'systemName',
				valueField : 'systemId',
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				allowBlank : false,
				emptyText : '请选择子系统',
				editable : false,
				selectOnFocus : true,
				listeners:{
					select:function(){
						sysid.setValue(this.getValue());
					}
				}
			});
			var serverBox = new Ext.form.ComboBox({
				fieldLabel : '服务类型',
				store : webserviceTypeGridDs,
				displayField : 'typeName',
				valueField : 'typeId',
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				allowBlank : false,
				emptyText : '请选择服务类型',
				editable : false,
				selectOnFocus : true,
				listeners:{
					select:function(){
						typeid.setValue(this.getValue());
					}
				}
			});
			var port = new Ext.form.NumberField({
				fieldLabel : '端口',
				allowNegative : false,
				allowDecimals : false,
				allowBlank : false,
				maxLength:5
			});
			var urn = new Ext.form.TextField({
				fieldLabel:'服务URN',
				allowBlank : false,
                validator:function(value){
                	if(value.trim().length > 0){
                		return true;
                	}
                	else return "该输入项为必填项";
                }
			});
			var serviceName = new Ext.form.TextField({
				fieldLabel : '服务名',
				allowBlank : false,
				validator:function(value){
                	if(value.trim().length > 0){
                		return true;
                	}
                	else return "该输入项为必填项";
                }
			});
			var nameSpace = new Ext.form.TextField({
				fieldLabel : '服务命名空间',
				allowBlank : false,
				validator:function(value){
                	if(value.trim().length > 0){
                		return true;
                	}
                	else return "该输入项为必填项";
                }
			});
			id.setValue(cfg.get("id"));
			sysid.setValue(cfg.get("sysid"));
			typeid.setValue(cfg.get("typeid"));
			port.setValue(cfg.get("port"));
			urn.setValue(cfg.get("serviceUrn").trim());
			serviceName.setValue(cfg.get("servicename").trim());
			nameSpace.setValue(cfg.get("namespace").trim());
			var addPanel = new Ext.form.FormPanel({
				width : 300,
				height : 220,
				frame : true,
				labelWidth : 80,
				items:[id,sysBox,port,urn,serviceName,nameSpace,serverBox],
				buttons : [{
							id : 'submit',
							text : '提交',
							handler : editCfg
						}, {
							id : 'reset',
							text : '重置',
							handler : function() {
								id.setValue("");
								sysid.setValue("");
								typeid.setValue("");
								sysBox.setValue("");
								sysBox.setRawValue("");
								port.setValue("");
								urn.setValue("");
								serviceName.setValue("");
								nameSpace.setValue("");
								serverBox.setValue("");
								serverBox.setRawValue("");
							}
						}]
			});
			var win = new Ext.Window({
				title : '修改WebService子系统配置',
				width : 300,
				height : 250,
				layout : 'fit',  
				resizable : false,
				modal : true,
				items : [addPanel],
				listeners:{
					show : function(){
						sysBox.setValue(cfg.get("sysname"));
						serverBox.setValue(cfg.get("typename"));
					}
				}
			});
			win.show();
			function editCfg() {
				var url = "syswebservicecfg/update.do";
				Ext.Ajax.request({
					url : url,
					method : 'POST',
					params : {
						id : id.getValue(),
						sysid : sysid.getValue(),
						typeIds : typeid.getValue(),
						port : port.getValue(),
						servicename : serviceName.getValue().trim(),
						serviceurn : urn.getValue().trim(),
						namespace : nameSpace.getValue().trim()
					},
					success : function(response, options) {
						var result = Ext.util.JSON
								.decode(response.responseText);
						if (result.status === true) {
							Ext.Msg.show({
										title : '提示信息',
										msg : result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.INFO,
										fn : function() {
											sysWebserviceGridDs.reload();
										}
									});
						}
						if (result.status === false) {
							Ext.Msg.show({
										title : '提示信息',
										msg : result.msg,
										buttons : Ext.MessageBox.OK,
										icon : Ext.MessageBox.ERROR
									});
						}
						win.close();
					}
				});
			}
			
	}

}