SystemGrid = function(config) {
	this.title = "子系统面板";
	Ext.apply(this, config);
	this.store = new Ext.data.JsonStore({
				root : 'rows',
				fields : ['systemId', 'systemName', 'url', 'icon',
						'showOrder'],
				proxy : new Ext.data.HttpProxy({
							url : 'module/loadSystemList.do',
							method : 'POST'
						})
			});
	this.columns = [{
				header : '子系统名称',
				dataIndex : 'systemName',
				width : 100
			}, {
				header : 'URL',
				dataIndex : 'url',
				width : 300
			}, {
				header : '图标',
				dataIndex : 'icon',
				width : 200
			}];
	SystemGrid.superclass.constructor.call(this, {
				region : 'center',
				id : 'system-grid',
				loadMask : {
					msg : '正在加载...'
				},
				autoHeight:true,
				sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
				viewConfig : {
					forceFit : true
				}
			});
};

Ext.extend(SystemGrid, Ext.grid.GridPanel, {
			load : function() {
				this.store.load({
							params : {
								start : 0,
								limit : 30
							}
						});
			}
		});
