function init() {
	Ext.BLANK_IMAGE_URL = 'image/s.gif';
	Ext.QuickTips.init();
	function addArea() {
		openAreaWindow(getAreaComboTree());
	}

	function editArea() {
		var selections = areaGrid.getSelectionModel().getSelections();
		if (selections.length == 0) {
			Ext.Msg.show({
						title : '提示信息',
						msg : '请选择一行数据',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING

					});
			return;
		}
		var readOnlyUpperAreaTextField = new Ext.form.TextField({
					fieldLabel : '所属上级区域',
					name : 'upperAreaName',
					readOnly : true
				});
		var form = openAreaWindow(readOnlyUpperAreaTextField).fp.getForm();
		form.loadRecord(selections[0]);
		//form.findField('isUnit').items.first().setValue(selections[0].get("isUnit"));
	}

	function deleteArea() {
		var selections = areaGrid.getSelectionModel().getSelections();
		if (selections.length == 0) {
			Ext.Msg.show({
						title : '提示信息',
						msg : '请选择一行数据',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.WARNING

					});
			return;
		}
		Ext.MessageBox.confirm('提示信息', '该区域及其所有下级区域都将被删除</br></br>确定要执行删除操作吗？',
				function(bt) {
					if (bt == 'no') {
						return;
					}
					var areaId = selections[0].get('areaId');
					Ext.Ajax.request({
								url : 'area/delete.do',
								method : 'POST',
								params : {
									areaId : areaId
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
														areaGridDs.reload();
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

	// /////////////////////////////////////////////////////////////////////////
	// 区域列表
	// /////////////////////////////////////////////////////////////////////////

	var areaGridDs = new Ext.data.JsonStore({
				root : 'rows',
				totalProperty : 'total',
				fields : ['areaId', 'areaName', 'areaLevel', 'fullName',
						'shortName', 'upperAreaId', 'upperAreaName', 'isUnit', 'yzCode', 'showOrder', 'businessLevel'],
				proxy : new Ext.data.HttpProxy({
							url : 'area/list.do',
							method : 'POST'
						})
			});
	areaGridDs.load({
				params : {
					start : 0,
					limit : 15
				}
			});
	var rn = new Ext.grid.RowNumberer();
	var sm = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				header : ''
			});
	var cm = new Ext.grid.ColumnModel([rn, sm, 
		 
	       /*{
				header : '区域编号',
				dataIndex : 'areaId',
				width : 80
			}
			 , {
				header : '外部编码',
				dataIndex : 'yzCode',
				width : 100
			}, {
				header : '区域层级',
				dataIndex : 'areaLevel',
				width : 80
			}
			,
			*/
			 {
				header : '区域名称',
				dataIndex : 'areaName',
				width : 100
			}
			/*, {
				header : '全称',
				dataIndex : 'fullName',
				width : 120
			}, {
				header : '简称',
				dataIndex : 'shortName',
				width : 120
			}, {
				header : '业务级别',
				dataIndex : 'businessLevel',
				width : 80
			}, {
				header : '排序号',
				dataIndex : 'showOrder',
				width : 80
			}
			*/
			, {
				header : '所属上级区域',
				dataIndex : 'upperAreaName',
				width : 80
			}
			
			/*, {
				header : '地图配置',
				dataIndex : 'indexmap',
				width : 100,
				renderer : renderAreaUnitValue
			}*/
			]);
	//var gridToolBar = eval("(" + Ext.get('toolbar').getValue() + ")");
	var provinceupload = Ext.get('provinceupload').getValue();
	var cityidentity = Ext.get('cityidentity').getValue();
	var identitylevel = Ext.get('identitylevel').getValue();

/*     gridToolBar[gridToolBar.length]={
    		    id : 'bt_syn',
				text : '手工同步区域信息',
				icon : 'image/icons/package_add.png',
				cls : 'x-btn-text-icon',
				handler : AjaxSunAreaData
     };*/
	
      var gridToolBar=
	        [{
				id : 'add_area',
				text : '添加区域',
				icon : 'image/icons/world_add.png',
				cls : 'x-btn-text-icon',
				handler : addArea
			},{
				id : 'delete_area',
				text : '删除区域',
				icon : 'image/icons/world_delete.png',
				cls : 'x-btn-text-icon',
				handler : deleteArea
			},{
				id : 'edit_area',
				text : '修改区域',
				icon : 'image/icons/world_edit.png',
				cls : 'x-btn-text-icon',
				handler : editArea
			}];
      
      
      function AjaxSunAreaData(){
    	  Ext.MessageBox.confirm('提示信息', '确定要执行区域信息同步操作吗？', function(bt) {
  			if (bt == 'no') {
  				return;
  			}
    	    Ext.getBody().mask("区域信息同步中.请稍等...","x-mask-loading");   
			Ext.Ajax.request({
				url : '../uinsyndata.action',
				method : 'POST',
				timeout : 30000000,
				params : {
					method : 'areasyn'
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
						areaGridDs.reload();
					}
					if (result.success == false) {
						Ext.Msg.show({
									title : '提示信息',
									msg : result.msg,
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
						areaGridDs.reload();
					}	
				},
				failure: function(response, options){   
					   //去除x-mask-loading效果   
					   Ext.getBody().unmask();   
					   var s = Ext.util.JSON.decode(response.responseText);  
					   Ext.Msg.alert('区域信息同步', '区域信息同步失败,未知错误！');   
					      }
			});  
    	  }); 
           }
      
      
      var gridBottomBar = new Ext.PagingToolbar({
				pageSize : 15,
				store : areaGridDs,
				displayInfo : true,
				displayMsg : '共 {2} 条记录，显示第 {0} 到 {1} 条',
				emptyMsg : '无记录'
			});
	var areaGrid = new Ext.grid.GridPanel({
				// title : '区域列表',
				region : 'center',
				frame : true,
				stripeRows : true,
				layout : 'fit',
				tbar : gridToolBar,
				bbar : gridBottomBar,
				viewConfig : {
					forceFit : true
				},
				ds : areaGridDs,
				cm : cm,
				sm : sm
			});
	var areaTree1 = Ext.tree;
	var rootNode = new areaTree1.AsyncTreeNode({
				id : 'root',
				text : '区域列表'

			});
	var treepanel1 = new areaTree1.TreePanel({
				useArrows : true,
				autoScroll : false,
				animate : true,
				containerScroll : true,
				rootVisible : false,
				border : false,
				loader : new areaTree1.TreeLoader({
							dataUrl : 'area/loadAreaTree.do'
						})
			});
	treepanel1.setRootNode(rootNode);
	treepanel1.on('click', function(node) {
				comboxWithTree1.setValue(node.text);
				Ext.getCmp('srchAreaId').setValue(node.id);
				comboxWithTree1.collapse();
			});

	var comboxWithTree1 = new Ext.form.ComboBox({
				store : new Ext.data.SimpleStore({
							fields : [],
							data : [[]]
						}),
				// fieldLabel : '所属上级区域',
				editable : false,
				mode : 'local',
				triggerAction : 'all',
				border : true,
				maxHeight : 200,
				maxWidth : 100,
				tpl : "<div id=divtree1 style='height:200;overflow:auto'></div>",
				selectedClass : '',
				onSelect : Ext.emptyFn
			});
	comboxWithTree1.on('expand', function() {
				treepanel1.render('divtree1');
				treepanel1.root.expand();
			});
	// /////////////////////////////////////////////////////////////////////////
	// 查询面板
	// /////////////////////////////////////////////////////////////////////////
	var searchBar = new Ext.Toolbar({
				region : 'north',
				// height:40,
				items : ['所属上级区域', '', comboxWithTree1, {
							xtype : 'hidden',
							id : 'srchAreaId',
							name : 'srchAreaId'
						}, '-', {
							text : '查询',
							cls : 'x-btn-text-icon',
							icon : 'image/icons/magnifier.png',
							handler : function() {
								var srchAreaId = Ext.getCmp('srchAreaId')
										.getValue();
								if(srchAreaId==''){
									srchAreaId = null;
								}
								Ext.apply(areaGridDs.baseParams, {
											srchAreaId : srchAreaId
										});
								areaGridDs.load({
											params : {
												start : 0,
												limit : 15
											}
										});
							}
						}, '-', {
							text : '重置',
							cls : 'x-btn-text-icon',
							icon : 'image/icons/arrow_refresh.png',
							handler : function() {
								comboxWithTree1.setValue("");
								Ext.getCmp('srchAreaId').setValue("");
								Ext.apply(areaGridDs.baseParams, {
											srchAreaId : null
										});
								areaGridDs.reload();
							}
						}]

			});

	var searchPnl = new Ext.Panel({
		// title : '查询条件',
		region : 'north',
		// layout : 'column',
		// height:70,
		tbar : [comboxWithTree1]

			// bodyStyle : 'padding:5px 5px 5px 5px',
			/*
			 * items : [{ columnWidth : .25, layout : 'form', border : false,
			 * items : [comboxWithTree1, { xtype : 'hidden', id : 'srchAreaId',
			 * name : 'srchAreaId' }] }, { columnWidth : .10, border : false,
			 * items : [{ xtype : 'button', text : '查询', handler : function() {
			 * var srchAreaId = Ext.getCmp('srchAreaId') .getValue();
			 * Ext.apply(areaGridDs.baseParams, { srchAreaId : srchAreaId });
			 * areaGridDs.reload(); } }] }, { columnWidth : .60, border : false,
			 * items : [{ xtype : 'button', text : '重置', handler : function() {
			 * Ext.apply(areaGridDs.baseParams, { srchAreaId : null });
			 * areaGridDs.reload(); } }] }]
			 */
		});

	// /////////////////////////////////////////////////////////////////////////
	// 面板
	// /////////////////////////////////////////////////////////////////////////
	var viewport = new Ext.Viewport({
				layout : 'border',
				border : false,
				items : [searchBar, areaGrid]
			});
	var levelStore = new Ext.data.SimpleStore({
		fields:['returnValue', 'textValue'],
		data:[['0','请选择'],['1','1'],['2','2'],['3','3']]
	});
	function openAreaWindow(upperAreaComponent) {
		var areaFormPanel = new Ext.FormPanel({
			id : 'area',
			frame : true,
			labelAlign : "right",
			labelWidth : 100,
			items : [{
						xtype : "hidden",
						id : 'areaId',
						name : "areaId",
						width : 180
					}, {
						xtype : "textfield",
						fieldLabel : "区域名称",
						id : 'areaName',
						name : "areaName",
						width : 180,
						allowBlank : false,
						maxLength : 64,
						validator:checkValidInput
					}/*, {
						xtype : "textfield",
						fieldLabel : "简称",
						name : "shortName",
						width : 180,
						maxLength : 16
					}, {
						xtype : "textfield",
						fieldLabel : "全称",
						name : "fullName",
						width : 180,
						maxLength : 100
					}, {
                        xtype : "textfield",
                        fieldLabel : "外部编码",
                        name : "yzCode",
                        width : 180,
                        maxLength : 32
                    }
                    
                    , 
                    	{            
						xtype:'combo', 
						fieldLabel : '业务级别',
						hiddenName:'businessLevel',
						store : levelStore,
						emptyText : '请选择',
						value : '0', 
						triggerAction : 'all',
						valueField : 'returnValue',
						displayField : 'textValue',
						typeAhead: true,
				        mode: 'local',
						readOnly : true,
						width : 180
                    }, 
                    	{
						xtype : "numberfield",
						fieldLabel : "排序号",
						name : "showOrder",
						width : 180,
						allowBlank : true,
						maxLength : 6
					}*/
					,upperAreaComponent, {
						xtype : 'hidden',
						id : 'upperAreaId'
					},{
						xtype : 'hidden',
						id : 'isUnit',
						value: '0'
					}],
			buttons : [{
				text : '提交',
				handler : function(form, action) {
					var fp = areaFormPanel.getForm();
					if (!fp.isValid()) {
						return;
					}
					fp.submit({
								url : 'area/save.do',
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
														areaWin.close();
														areaGridDs.reload();
													}
												});
									}
								}
							});
				}
			}, {
				text : '重置',
				handler : function() {
					areaFormPanel.getForm().reset();
				}
			}]
		});
		// ////////////////////////////////////////////////////////////////////
		// 业务表单
		// ////////////////////////////////////////////////////////////////////
		var areaWin = new Ext.Window({
					title : '区域表单',
					width : 350,
					items : [areaFormPanel],
					modal : true
				});
		areaWin.show();
		return {
			fp : areaFormPanel,
			win : areaWin
		};
	}

	function getAreaComboTree() {
		var areaTree = Ext.tree;
		var rootNode = new areaTree.AsyncTreeNode({
					id : 'root',
					text : '区域列表'

				});
		var treepanel = new areaTree.TreePanel({
					useArrows : true,
					autoScroll : false,
					animate : true,
					containerScroll : true,
					rootVisible : false,
					border : false,
					loader : new areaTree.TreeLoader({
								dataUrl : 'area/loadAreaTree.do'
							})
				});
		treepanel.setRootNode(rootNode);
		treepanel.on('click', function(node) {
					comboxWithTree.setValue(node.text);
					Ext.getCmp('upperAreaId').setValue(node.id);
					comboxWithTree.collapse();
				});

		var comboxWithTree = new Ext.form.ComboBox({
					store : new Ext.data.SimpleStore({
								fields : [],
								data : [[]]
							}),
					fieldLabel : '所属上级区域',
					editable : false,
					allowBlank : false,
					mode : 'local',
					triggerAction : 'all',
					border : true,
					maxHeight : 200,
					maxWidth : 100,
					tpl : "<div id=divtree style='height:200;overflow:auto'></div>",
					selectedClass : '',
					onSelect : Ext.emptyFn
				});
		comboxWithTree.on('expand', function() {
					treepanel.render('divtree');
					treepanel.root.expand();
				});
		return comboxWithTree;
	}
	function renderAreaUnitValue(value, cellmeta, record, rowIndex, columnIndex, store) {
		var str=""
		if(provinceupload == "1") {
			if(record.data["areaLevel"]=="1") {
				str="<a href='../siteuploadmap.action?areaid="+record.data["areaId"]+"' target='_blank'><font color='red'>上传省区地图</font></a>";
			}
		}
		if(cityidentity == "1") {
			if(record.data["areaLevel"]==identitylevel){
				str="<a href='../siteonflashmap.action?areaid="+record.data["areaId"]+"' target='_blank'>地图标注</a>";
			}
		}
			
		return str;

	}
	


	function validateShowOrder(order) {
		if (order < 1 || order > 1000) {
			return false;
		}
		return true;
	}

}