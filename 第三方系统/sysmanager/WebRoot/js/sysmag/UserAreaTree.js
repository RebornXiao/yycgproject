UserAreaTree = function(config, roleId) {
	Ext.apply(this, config);
	this.root = new Ext.tree.AsyncTreeNode({
				id : 'root',
				text : '区域列表'
			});
	this.loader = new Ext.tree.TreeLoader({
				dataUrl : 'user/loadRoleAreaTree.do',
				baseParams : {
					roleId : roleId
				}
			});
	UserAreaTree.superclass.constructor.call(this, {
				useArrow : true,
				animate : true,
				border:false,
				autoScroll : true,
				containerScroll : true,
				rootVisible : false,
				height : 200
			});
};
Ext.extend(UserAreaTree, Ext.tree.TreePanel, {});