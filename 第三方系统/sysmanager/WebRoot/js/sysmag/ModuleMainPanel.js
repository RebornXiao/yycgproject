ModuleMainPanel = function() {
	ModuleMainPanel.superclass.constructor.call(this, {
				id : 'main-panel',
				border : false,
				iconCls : 'node-module',
				authHeight:true,
				activeTab : 0,
				tabWidth : 150,
				minTabWidth : 120,
				enableTabScroll : true

			});
};

Ext.extend(ModuleMainPanel, Ext.TabPanel, {
			load : function(nodes, toolbar) {
				this.remove(this.getActiveTab());
				var _node = nodes[nodes.length - 1];
				var nodeType = _node.id.substr(0, 1);
				var grid;
				if (nodeType == 's') {
					grid = new ModuleGridPanel({
								id : _node.id
							}, toolbar, nodes[0], null);
				}
				if (nodeType == 't') {
					grid = new ModuleGridPanel({
								id : _node.id
							}, toolbar, nodes[0], nodes[1]);
				}
				/*if (nodeType == 'm') {
					grid = new ModuleGridPanel({
								id : _node.id
							}, toolbar, nodes[0], nodes[2]);
				}*/
				if (nodeType == 'b') {
					grid = new OperationGridPanel({
								id : _node.id
							}, toolbar, _node);
				}
				grid.load(_node.id);
				this.add({
							title : _node.text,
							layout:'fit',
							items : [grid]
							//closable : true
						}).show();
				this.doLayout();
				// }

			}
		});

