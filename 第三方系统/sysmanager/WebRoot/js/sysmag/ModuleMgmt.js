function init() {
	Ext.BLANK_IMAGE_URL = 'image/s.gif';
	new Ext.ToolTip({
                target : 'gd',
                html : 'A very simple tooltip'
            });
	Ext.QuickTips.init();
	var Tree = Ext.tree;
	var tree = new Tree.TreePanel({
				id : 'tree',
				border : false,
				bodyStyle : 'padding:1px 0px 0px 0px',
				useArrows : true,
				animate : true,
				autoScroll : true,
				containerScroll : true,
				rootVisible : false,
				loader : new Tree.TreeLoader({
							dataUrl : 'module/loadTree.do'
						}),
				tbar : ['->', {
							text : '重新加载',
							icon : 'image/icons/arrow_refresh.png',
							cls : 'x-btn-text-icon',
							handler : function() {
								tree.getRootNode().reload();
							}

						}]
			});

	var root = new Tree.AsyncTreeNode({
				text : '节点列表',
				id : 'root'
			});
	tree.setRootNode(root);
	root.expand();
	var gridToolBar=
        [/*{
			id : 'add_module', 
			text : '添加',
			icon : 'image/icons/package_add.png',
			cls : 'x-btn-text-icon',
			handler : add_module
		},{
			id : 'del_module',
			text : '删除',
			icon : 'image/icons/package_delete.png',
			cls : 'x-btn-text-icon',
			handler : deleteArea
		},{
			id : 'edit_module',
			text : '修改',
			icon : 'image/icons/package_green.png',
			cls : 'x-btn-text-icon',
			handler : editArea
		}*/];
	tree.on('click', function(node) {
				var ids = node.getPath().split("/");
				var nodes = new Array();
				for (var i = 2; i < ids.length; i++) {
					var j = i - 2;
					nodes[j] = tree.getNodeById(ids[i]);
				}
				mainPanel.load(nodes, gridToolBar);
				viewport.doLayout();
			});
	//var gridToolBar = Ext.get('toolbar').getValue();
	
	var mainPanel = new ModuleMainPanel();

	var viewport = new Ext.Viewport({
				layout : "border",
				items : [{
							region : "center",
							layout : 'fit',
							items : [mainPanel]
						}, {
							region : "west",
							title : "系统功能操作列表",
							layout : 'fit',
							width : 200,
							collapsible : true,
							items : [tree]
						}]
			});

}