UserForm = function(config) {
	var roleDs = new Ext.data.JsonStore({
				root : 'rows',
				fields : ['roleId', 'roleName', 'roleDes'],
				proxy : new Ext.data.HttpProxy({
							url : 'user/loadRoles.do?cmd=' + config.cmd+'&edituser=' + config.seluser,
							method : 'POST'
						}),
				autoLoad : true
			});

    var areaName = new Ext.form.ComboBox({
					fieldLabel : '管理区域',
					// emptyText : '选择区域',
					name : 'areaNames',
					id : 'areaField',
					store : new Ext.data.SimpleStore({
								fields : [],
								data : [[]]
							}),
					anchor : '100%',
					editable : false,
					mode : 'local',
					triggerAction : 'all',
					tpl : "<div id='treedelete' style='height:200;'></div>",
					allowBlank : false
				});
	var areaTree = new Ext.tree.TreePanel({
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
			        	
			        	//areaName.collapse();
/*			        	Ext.Msg.show({
                                        title : '提示信息',
                                        msg : "所属该区域的角色只能由其上级区域用户分配<br>请选择其下的区域进行分配",
                                        buttons : Ext.MessageBox.OK,
                                        icon : Ext.MessageBox.WARNING
                                    });
			        	return;*/
			        }
					Ext.getCmp('areaIds').setValue(node.id);
					Ext.getCmp('areaField').setValue(node.text);
					//areaId.setValue(node.id);
					//areaName.setValue(node.text);
					areaName.collapse();
				});
		areaName.on('expand', function() {
					areaTree.render('treedelete');
				});
	var enterPan = new Ext.TabPanel({
		id : 'cente',
	   autoHeight:true,
		activeTab : 0,

		items : [new Ext.Panel({
			layout : "column",
			frame:true,
			title : "基本信息",
			autoHeight : true,
			items : [{
				columnWidth : 0.5,
				layout : 'form',
				items : [{
					xtype : "fieldset",
					defaults : {
						allowBlank : false,
						anchor : '95%'
					},
					defaultType : 'textfield',
					height : 145,
					width : 250,
					title : "基本信息",
					items : [{
								id : 'userName',
								name : "userName",
								fieldLabel : "用户名",
								maxLength : 32,
								regex : /^(?!sso_|center_)\w+$/,
								regexText : '账号只能由A-Z,a-z,0-9,及下划线组成,且不能以sso_或center_开头'
							}, {
											xtype : "textfield",
											fieldLabel : "用户姓名",
											name : "trueName",
											allowBlank : false,
											maxLength : 32,
											validator : checkValidInput
										},{
								id : 'password',
								inputType : 'password',
								name : "password",
								fieldLabel : "密码",
								minLength : 6,
								maxLength : 30,
								allowBlank : config.pwdBlank,
								validator : checkValidInput
							}, {
								id : 'password2',
								inputType : 'password',
								name : "password2",
								fieldLabel : "密码确认",
								vtype : 'password',
								initialPassField : 'password',
								minLength : 6,
								maxLength : 30,
								allowBlank : config.pwdBlank,
								validator : checkValidInput
							}, {
								xtype : 'label',
								id : 'pwdLabel',
								html : '<br><br><font color="red">注: 若修改密码请在密码框中输入</font>',
								labelStyle : 'font-weight:bold;'
							}]
				}],
				border : false
			}, {
				columnWidth : 0.5,
				items : [{
							xtype : "fieldset",
							defaults : {
								allowBlank : false
							},
							height : 145,
							width : 250,
							title : "权限分配",
							items : [{
										xtype : "combo",
										id : 'roleCbx',
										fieldLabel : "用户角色",
										hiddenName : 'roleId',
										store : roleDs,
										mode : 'local',
										triggerAction : 'all',
										displayField : 'roleName',
										valueField : 'roleId',
										editable : false,
										listWidth: 180,
										anchor : '100%'
									}
									/*, {
										xtype : "trigger",
										id : 'areaField',
										name : "areaNames",
										fieldLabel : "管理区域",
										triggerClass : 'x-form-search-trigger',
										readOnly : true,
										anchor : '95%',
										onTriggerClick : openAreaWin
									}*/
									, areaName
										, {
										xtype : 'hidden',
										id : 'areaIds',
										name : 'areaIds'
										
									}, {
										xtype : 'hidden',
										id : 'bussIds',
										name : 'businessIds',
										value: '1'
									}/*, {
										xtype : 'radiogroup',
										fieldLabel : '用户状态',
										id : 'userState',
										width : 100,
										items : [{
													boxLabel : '正常',
													name : 'userState',
													inputValue : 1,
													checked : true
												}, {
													boxLabel : '暂停',
													name : 'userState',
													inputValue : 0
												}]
									}*/, {
										xtype : 'hidden',
										id : 'userState',
										name : 'userState',
										value: 1
									}]
						}],
				border : false
			}],
			border : false

		}), new Ext.Panel({
					layout : 'column',
					title : "详细信息",
					autoHeight : true,
					frame:true,
					border : false,
					items : [{
								columnWidth : 0.5,
								layout : "form",
								defaults : {
									width : 140
								},
								items : [ {
											xtype : 'radiogroup',
											fieldLabel : '性别',
											id : 'sex',
											width : 80,
											items : [{
														boxLabel : '男',
														name : 'sex',
														inputValue : 1,
														checked : true
													}, {
														boxLabel : '女',
														name : 'sex',
														inputValue : 0
													}]
										}, {
											xtype : "numberfield",
											fieldLabel : "手机号码",
											name : "movePhone",
											maxLength : 11,
											regex : /^[0-9]+$/,
											regexText : '请输入数字'
										}, {
											xtype : "textfield",
											name : "contact",
											fieldLabel : "固定电话",
											maxLength : 13,
											regex : /^[0-9]+$/,
											regexText : '请输入数字'
										}, {
											xtype : "textfield",
											name : "fax",
											fieldLabel : "传真",
											maxLength : 13,
											regex : /^[0-9]+$/,
											regexText : '请输入数字'
										}/*, {
											xtype : "textfield",
											name : "phone",
											fieldLabel : "小灵通",
											maxLength : 13,
											regex : /^[0-9]+$/,
											regexText : '请输入数字'
										}*/]

							}, {
								columnWidth : 0.5,
								layout : "form",
								defaults : {
									width : 160
								},
								items : [{
											xtype : "textfield",
											fieldLabel : "地址",
											name : "address",
											maxLength : 120
										}, {
											xtype : "textfield",
											name : "email",
											fieldLabel : "Email",
											vtype : 'email'
										}, {
											xtype : 'textarea',
											name : 'remark',
											fieldLabel : '备注',
											height : 105,
											maxLength : 120

										}]

							}]
				})],
					listeners : {
					render : function(pan) {
				
							pan.activate(0);
							pan.activate(1);
							//pan.activate(2);
							pan.activate(0);
						
						}
					}

	});
	UserForm.superclass.constructor.call(this, {
				frame : true,
				id : 'user-form',
				labelWidth : 70,
				//bodyStyle : 'padding:5px 5px 5px 5px',
				items : [enterPan],
				buttons : [{
					text : '提交',
					handler : function(form, action) {
						var fp = Ext.getCmp('user-form').getForm();
						if (!fp.isValid()) {
							return;
						}
						fp.submit({
									url : 'user/save.do',
									method : 'POST',
									params : {
										cmd : config.cmd
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
										}
										if (action.result.status == true) {
											Ext.Msg.show({
														title : '提示信息',
														msg : action.result.msg,
														buttons : Ext.MessageBox.OK,
														icon : Ext.MessageBox.INFO,
														fn : function() {
															Ext
																	.getCmp('user-win')
																	.close();
															config.gs.reload();
														}
													});
										}
									}
								});
					}
				}]
			});

	Ext.getCmp('roleCbx').on('select', function() {
				//Ext.getCmp('areaField').setValue("");
				//Ext.getCmp('areaIds').setValue("");
			});

	function getBussTreeCombo() {
		var tree = new Ext.tree.TreePanel({
					useArrows : true,
					autoScroll : true,
					animate : true,
					containerScroll : true,
					rootVisible : false,
					border : false,
					root : new Ext.tree.AsyncTreeNode({
								id : 'root',
								text : '业务列表'

							}),
					loader : new Ext.tree.TreeLoader()
				});
		tree.on('checkchange', function(node, checked) {
					var businessIds = "";
					var businessNames = "";
					for (var i = 0; i < tree.getChecked().length; i++) {
						businessIds += tree.getChecked()[i].id + ",";
						businessNames += tree.getChecked()[i].text + ",";
					}
					Ext.getCmp('bussIds').setValue(businessIds);
					Ext.getCmp('bussTreeCombo').setValue(businessNames);
				});
		var comboxWithTree = new Ext.form.ComboBox({
					store : new Ext.data.SimpleStore({
								fields : [],
								data : [[]]
							}),
					id : 'bussTreeCombo',
					fieldLabel : '管理业务',
					editable : false,
					mode : 'local',
					triggerAction : 'all',
					border : true,
					//maxHeight : 200,
					maxWidth : 130,
					tpl : "<div id='divtree' style='height:200;'></div>",
					selectedClass : '',
					onSelect : Ext.emptyFn,
					anchor : '95%'
				});
		comboxWithTree.on('expand', function() {
					var username = Ext.getCmp('userName').getValue();

					tree.getLoader().dataUrl = 'user/loadBussTree.do';
					tree.getLoader().baseParams = {
						userName : username
					};

					tree.getRootNode().reload();
					tree.render('divtree');
					tree.root.expand()
				});
		return comboxWithTree;
	}

	function openAreaWin() {
		var roleId = Ext.getCmp('roleCbx').getValue();
		if (roleId == '') {
			Ext.Msg.alert('提示信息', '请首先分配角色');
			return;
		}
		var oldAreaIds = Ext.getCmp('areaIds').getValue();
		if (oldAreaIds != '') {
			Ext.Msg.confirm('提示信息', '重新分配将清空之前的区域<br><br>确定要重新分配管理区域吗?',
					function(btn, text) {
						if (btn == 'yes') {
							open(roleId);
						}
					});
		} else {
			open(roleId);
		}

		function open(roleId) {//已于2012-2-9更新为单选，此方法暂时不用
			var areaTree = new UserAreaTree({}, roleId);
			var areaWin = new Ext.Window({
						title : '管理区域分配',
						items : [areaTree],
						width : 300,
						modal : true,
						resizable : false,
						buttons : [{
							text : '确定',
							handler : function() {
								Ext.getCmp('areaField').setValue("");
								Ext.getCmp('areaIds').setValue("");
								var checkedAreaNodes = areaTree.getChecked();
								
								if (checkedAreaNodes.length == 0) {
									Ext.Msg.alert('提示信息', '请选择一个管理区域');
									return;
								}
								
								if (checkedAreaNodes.length > 1) {
									Ext.Msg.alert('提示信息', '每个管理员只能管理一个区域');
									return;
								}
								var areas = concatArea(checkedAreaNodes);
								if (!isSelectSameLevelArea(areas.areaIds)) {
									Ext.Msg.alert('提示信息', '请选择相同级别的区域');
									return;
								}
								Ext.getCmp('areaField').setValue(areas.areaNames);
								Ext.getCmp('areaIds').setValue(areas.areaIds);
								areaWin.close();
								// alert(areas.areaIds);
								// alert(areas.areaNames);
							} 
						}]
					});
			areaWin.show();
		}

	}
};
Ext.extend(UserForm, Ext.FormPanel, {});

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

/* 以","连接区域Id和区域名称 */
function concatArea(areaNodes) {
	var areaIds = "";
	var areaNames = "";
	for (var i = 0; i < areaNodes.length; i++) {
		areaIds += areaNodes[i].id + ",";
		areaNames += areaNodes[i].text + ",";
	}
	return {
		areaIds : areaIds,
		areaNames : areaNames
	};
}

/* 判断选择的区域是否为同一级别 */
function isSelectSameLevelArea(areaIds) {
	var conn = Ext.lib.Ajax.getConnectionObject().conn;
	conn.open('POST', 'area/validAreaChoice.do?areaIds=' + areaIds, false);
	conn.send(null);
	return Ext.util.JSON.decode(conn.responseText).status;

}