function acdLayout() {
	iframeTemplate = new Ext.Template();
	iframeTemplate
			.set(
					'<iframe id="{id}" scrolling="{scroll}"  marginheight="0" marginwidth="0" width="100%" height="100%" src="{src}" frameborder="0"></iframe>',
					true);
	Ext.BLANK_IMAGE_URL = 'image/s.gif';
	Ext.QuickTips.init();
	function nodeClick(node, event) {
		event.stopEvent();
		if (node.attributes.displayType) {
			addtab(node.id, node.attributes.href, node.text,
					node.attributes.displayType);
		} else {
			addtab(node.id, node.attributes.href, node.text, 1);// 默认用iframe打开
		}

	}

	var tree1 = new Ext.tree.TreePanel({
				border : false,
				autoScroll : true,
				root : new Ext.tree.AsyncTreeNode({
							id : '-1',
							text : 'root'
						}),
				rootVisible : false,
				useArrows : true,
				bodyStyle : 'padding:1px 0px 0px 0px',
				loader : new Ext.tree.TreeLoader({
							dataUrl : 'login/loadMenu.do'
						})
			});

	// tree1.root.appendChild(modulelist);

	tree1.on("click", nodeClick);

	var item1 = new Ext.Panel({
				title : '功能菜单',
				border : false,
				layout : 'fit',
				cls : 'empty',
				layout : 'fit',
				items : [tree1]
			});

	var accordion = new Ext.Panel({
				region : 'west',
				margins : '0 1 0 1',
				collapsible : true,
				title : '统一门户管理',
				width : 190,
				layout : 'accordion',
				layoutConfig : {
					animate : true
				},
				items : [item1]
			});

	var tabs = new Ext.TabPanel({
				region : 'center',
				deferredRender : false,
				activeTab : 0,
				margins : '0 1 0 1',
				enableTabScroll : true,
				// resizeTabs : true,
				minTabWidth : 80,
				tabWidth : 100,
				items : [{

							title : '**欢迎页**',
							closable : false,
							html : iframeTemplate.applyTemplate({
										id : "homePage",
										src : "jsp/home.jsp",
										scroll : "no"
									})
						}]

			});
	tabs.on("beforeremove", function(ct, cp) {
				try {
					var iFrame = cp.getEl().dom.firstChild.firstChild.firstChild; // iframe
					if (iFrame.src) {
						iFrame.src = "javascript:false";
					} // 关闭标签页时清空iframe占用的内存
				} catch (e) {
				}
			});
	function addtab(nodeId, url, title, tabType)// /openType:0(div)/1(iframe)
	{

		var tabId = "tabs_" + nodeId;
		var atab;
		if (atab = tabs.findById(tabId)) {
			tabs.setActiveTab(atab);
			return;
		}

		var newTab = {
			id : tabId,
			title : title,
			closable : true,
			autoScroll : true

		};
		if (tabType == '0') {
			newTab.autoLoad = {
				url : url,
				text : "页面加载中，请稍后...",
				scripts : true
			};
		} else if (tabType == '1') {
			newTab.html = iframeTemplate.applyTemplate({
						id : id || "",
						src : url,
						scroll : "no"
					});
		} else {
			return;
		}
		tabs.setActiveTab(tabs.add(newTab));

	}

	var indexTop = new Ext.Panel({
				region : 'north',
				height : 60,
				cls : 'empty',
				border : false,
				html : iframeTemplate.applyTemplate({
							id : "indexTop",
							src : "jsp/top.jsp",
							scroll : "no"
						})
			})

	var indexBottom = new Ext.Panel({
				region : 'south',
				height : 25,
				cls : 'empty',
				border : false,
				html : iframeTemplate.applyTemplate({
							id : "indexTop",
							src : "jsp/bottom.jsp",
							scroll : "no"
						})
			})
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [accordion, indexTop, tabs, indexBottom]
			});
}

function changePwd() {
	Ext.BLANK_IMAGE_URL = 'image/s.gif';
	Ext.QuickTips.init();
	var changePwdFp = new Ext.FormPanel({
		frame : true,
		labelAlign : "right",
		labelWidth : 100,
		items : [{
                    xtype : 'textfield',
                    inputType : 'password',
                    name : "pwd_old",
                    fieldLabel : "请输入旧密码",
                    minLength: 6,
                    maxLength : 12,
                    allowBlank : false,
                    anchor : '95%'
                },{
					xtype : 'textfield',
					id : 'password',
					inputType : 'password',
					name : "pwd_new",
					fieldLabel : "请输入新密码",
					minLength: 6,
					maxLength : 12,
					allowBlank : false,
					anchor : '95%'
				}, {
					xtype : 'textfield',
					id : 'password2',
					inputType : 'password',
					name : "password2",
					fieldLabel : "密码确认",
					vtype : 'password',
					initialPassField : 'password',
					minLength: 6,
					maxLength : 12,
					allowBlank : false,
					anchor : '95%'
				}],
		buttonAlign : 'right',
		buttons : [{
					text : '提交',
					handler : function(form, action) {
						var fp = changePwdFp.getForm();
						if (!fp.isValid()) {
							return;
						}
						fp.submit({
									url : 'user/changePassword.do',
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
															pwdWin.close();
														}
													});
										}
									}
								});
					}
				}, {
					text : '取消',
					handler : function() {
						pwdWin.close();
					}
				}]
	});

	var pwdWin = new Ext.Window({
				title : '修改密码',
				iconCls : 'x-form-pwd-iconCls',
				layout : 'fit',
				items : [changePwdFp],
				width : 300,
				height : 180,
				modal : true,
				resizable : false
			});
	pwdWin.show();
}

function comeToOtherDeployNodes() {
	Ext.QuickTips.init();

	var reader = new Ext.data.JsonReader({
				root : 'rows'
			}, [{
						name : 'nodeid'
					}, {
						name : 'name'
					}, {
						name : 'url'
					}, {
						name : 'sysid'
					}, {
						name : 'sysname'
					}]);

	var grid = new Ext.grid.GridPanel({
		store : new Ext.data.GroupingStore({
					reader : reader,
					autoLoad : true,
					proxy : new Ext.data.HttpProxy({
								url : 'navigation/loadDeployNode.do',
								method : 'POST'
							}),
					sortInfo : {
						field : 'sysid',
						direction : "ASC"
					},

					groupField : 'sysname'
				}),

		columns : [{
			id : 'nodeid',
			header : "节点名称",
			width : 200,
			sortable : true,
			dataIndex : 'name',
			renderer : function(value, p, record) {
				var url = record.data.url + "?nodeid=" + record.data.nodeid; //传递部署节点ID
				return String.format('<img src="image/icons/server.png"></img><a href="{1}" target="_parent">{0}<a>',value, url);
			}
		}, {
			header : "子系统",
			width : 20,
			hidden : true,
			sortable : false,
			dataIndex : 'sysname'
		}],

		view : new Ext.grid.GroupingView({
					forceFit : true,
					groupTextTpl : '{text} ({[values.rs.length]} {["个节点"]})'
				}),
		animCollapse : false
	});

	var dnWin = new Ext.Window({
				title : '进入其他节点',
				layout : 'fit',
				items : [grid],
				iconCls:'node-deploynode-go',
				width : 300,
				height : 350,
				modal : true,
				resizable : false
			});
	dnWin.show();
}

Ext.apply(Ext.form.VTypes, {
			password : function(val, field) {
				if (field.initialPassField) {
					var pwd = Ext.getCmp(field.initialPassField);
					return (val == pwd.getValue());
				}
				return true;
			},
			passwordText : '两次输入的密码不一致'
		});
