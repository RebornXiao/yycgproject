

/*
* 依赖Ext JS Library 2.1
* add：刘志强<liuzhiqiang@zzvcom.com>
* 支持的编码utf-8
*
*
* 功能点有： 
* 一、带checkbox的树
* 二、依赖tree-expend-checkbox.js
* 三、参数说明
*	@id为显示树的id(必选)
*		type为加载树的类型(必选)1为延迟加载树、2为异步加载树
*		treejson初始化的树的节点(必选)
*		treeurl当type为2是调用的url地址(当type为2必选，type为1是可设置为null)
*		functions点击树的节点是执行的动作
*		checkModel为选择类型single为单选，cascade为级联选择
*		onlyLeafCheckable为是否只允许选择叶子		
* 四、exemple
*			var treejson= [{text:"信息中心",icon:"..\/etc\/images\/im2.gif",depth:1,parentid:0,leaf:false,draggable:false,id:1,children:[{text:"右键菜单",href:"rightmenu.html",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:1,leaf:true,draggable:false,id:25,children:[]},{text:"grid数据显示方式二",href:"articleGridManage1.action",icon:"..\/etc\/images\/im2.gif",depth:2,parentid:1,leaf:true,draggable:false,id:26,children:[]},{text:"样式文件",href:"css.jsp",icon:"..\/etc\/images\/im2.gif",depth:2,parentid:1,leaf:true,draggable:false,id:6,children:[]},{text:"ext数据表格",href:"articleGridManage.action",icon:"..\/etc\/images\/im2.gif",depth:2,parentid:1,leaf:true,draggable:false,id:5,children:[]},{text:"信息征集",icon:"..\/etc\/images\/im2.gif",depth:2,parentid:1,leaf:false,draggable:false,id:4,children:[{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:23,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:22,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:21,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:20,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:19,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:17,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:16,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:15,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:14,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:13,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:12,children:[]},{text:"异步加载测试",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:11,children:[]},{text:"信息文章列表",href:"articleManage.action",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:8,children:[]},{text:"发布信息文章",href:"addarticle.html",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:4,leaf:true,draggable:false,id:7,children:[]}]}]},{text:"系统应用",icon:"..\/etc\/images\/im2.gif",depth:1,parentid:0,leaf:false,draggable:false,id:2,children:[{text:"系统帮助",href:"http:\/\/www.baidu.com",icon:"..\/etc\/images\/im2.gif",depth:2,parentid:2,leaf:false,draggable:false,id:9,children:[{text:"系统帮助",icon:"..\/etc\/images\/im2.gif",depth:3,parentid:9,leaf:true,draggable:false,id:24,children:[]}]}]},{text:"系统设置",icon:"..\/etc\/images\/im2.gif",depth:1,parentid:0,leaf:false,draggable:false,id:3,children:[{text:"设置帮助",href:"http:\/\/www.baidu.com",icon:"..\/etc\/images\/im2.gif",depth:2,parentid:3,leaf:true,draggable:false,id:10,children:[]}]}]
*			//普通树，不支持右键添加删除，支持异步加载和延迟加载
*			var checkboxtree=new checkboxtree('menuTree',1,treejson,null,toclick,'cascade',false);
*/
function checkboxtree(id,type,treejson,treeurl,functions,checkModel,onlyLeafCheckable,checkfunction){
		this.tree=null;
		this.type=type;
		this.treejson=treejson;
		this.treeurl=treeurl;
		this.checkModel=checkModel;
		this.root=null;
		var _this=this;
		Ext.onReady(function(){
		     _this.root =new Ext.tree.AsyncTreeNode({
								id:'root',
								text:"根节点",
								children:_this.treejson
							});
			  var load3=new Ext.tree.TreeLoader({baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI }});
			  var load4=new Ext.tree.TreeLoader({
					            dataUrl:_this.treeurl,
					            baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI }
					        })
		      _this.tree=new Ext.tree.TreePanel({
			        renderTo:id,
			        root:_this.root,//定位到根节点
			        animate:true,//开启动画效果
			        enableDD:false,//不允许子节点拖动
			        border:false,//没有边框
			        checkModel: _this.checkModel||'cascade', //对树的级联多选 
					onlyLeafCheckable: onlyLeafCheckable||false,//对树所有结点都可选 
			        rootVisible:false,//设为false将隐藏根节点，很多情况下，我们选择隐藏根节点增加美观性
			        loader: _this.type==1?load3:load4
		     });
		     //_this.tree.expandAll();
		     //_this.tree.collapseAll();
		     if(functions)
		     _this.tree.on("click",functions);
		     if(checkfunction){
		     	_this.tree.on("check",function(v){
					checkfunction(v);
				 });
		     }
		     
		});
		//返回定义的tree对象，抛出对外接口getChecked
		checkboxtree.prototype.trees=function(){
			return this.tree;
		}
		checkboxtree.prototype.Checked=function(value){
			var checkedbox=this.tree.getChecked();
			for(var i=0;i<checkedbox.length;i++){
				checkedbox[i].attributes.checked=false;
			}
			var valueid=value.split(",");
			for(var j=0;j<valueid.length;j++){
				if(valueid[j]!=""){
					var node=this.tree.getNodeById(valueid[j]);
					node.attributes.checked=true;
				}
			}
			this.root.reload();
			this.tree.expandAll();
		    this.tree.collapseAll();
		}
		checkboxtree.prototype.Reload=function(value){
		     _this.treejson=value;
		    _this.root.attributes.children=value;
			this.root.reload();
		}
		//获取选中的checkbox值
		checkboxtree.prototype.getCheckedValue=function(){
			//this.tree.expandAll();
			//this.tree.collapseAll();
			var checkedbox=this.tree.getChecked();
			var value="";
			for(var i=0;i<checkedbox.length;i++){
				value=value+checkedbox[i].id+",";
			}
			return value.replace(/,$/,"");
		}
		//获取选中的checkbox所有非叶子的节点值		
      	checkboxtree.prototype.getCheckedIsLeafValue=function(){
			//this.tree.expandAll();
			//this.tree.collapseAll();
			var checkedbox=this.tree.getChecked();
			var value="";
			for(var i=0;i<checkedbox.length;i++){
			if(!checkedbox[i].attributes.leaf){
				value=value+checkedbox[i].id+",";
				}
			}
			return value.replace(/,$/,"");
		}
}
/*
*	定义可动态添加节点的树
*	@id为显示树的id(必选)
*		treejson初始化的树的节点(必选)
*		functions点击树的节点是执行的动作
*		rightfunction点击右键菜单执行的动作
*/
function activetree(id,treejson,functions,rightfunction){
		this.root=null;
		this.tree=null;
		var _this=this;
		this.treejson=treejson;
		Ext.onReady(function(){
			this.root=new Ext.tree.TreeNode({
		            id:"root",//根节点id
		            text:"权限控制"
		      });
			for (var i=0;i<this.treejson.length;i++){
				var tree=new Ext.tree.TreeNode({
			            id:this.treejson[i].id,
			            text:this.treejson[i].text,
			            icon:this.treejson[i].icon,
			            href:this.treejson[i].href,
			            leaf:this.treejson[i].leaf
			      });
			      this.root.appendChild(tree);
			       //增加右键点击事件
				  if(typeof(rightfunction)!="function")rightfunction=function(){};
				  tree.on('contextmenu',rightfunction); //加载右键菜单动作
			      if(this.treejson[i].children)toaddtree(tree,this.treejson[i].children);
			}
			function toaddtree(roots,json){
				for(var i=0;i<json.length;i++){
					var tree=new Ext.tree.TreeNode({
			            id:json[i].id,
			            text:json[i].text,
			            icon:json[i].icon,
			            href:json[i].href,
			            leaf:json[i].leaf
			      });
			      roots.appendChild(tree);
			      if(typeof(rightfunction)!="function")rightfunction=function(){};
			      tree.on('contextmenu',rightfunction); //加载右键菜单动作 
			      if(json[i].children)toaddtree(tree,json[i].children);
				}
			}
			//生成树形面板
	      	_this.tree=new Ext.tree.TreePanel({
		        renderTo:id,
		        root:root,//定位到根节点
		        animate:true,//开启动画效果
		        enableDD:false,//不允许子节点拖动
		        border:false,//没有边框
		        rootVisible:false//设为false将隐藏根节点，很多情况下，我们选择隐藏根节点增加美观性
	     	});
	     	if(typeof(functions)!="function")functions=function(){};
	     		_this.tree.on("click",functions);
		 	});
		 //返回定义的tree对象，抛出对外接口
		 activetree.prototype.tree=function(){
			return this.tree;
		 }
		 //添加一个新的节点
		 activetree.prototype.addnode=function(node,json){
		 	node.leaf=false;
		 	var tree=new Ext.tree.TreeNode({
			            id:json.id,
			            text:json.text,
			            icon:json.icon,
			            href:json.href,
			            leaf:json.leaf
			});
			node.appendChild(tree);
			if(typeof(rightfunction)!="function")rightfunction=function(){};
			tree.on('contextmenu',rightfunction); //加载右键菜单动作 
		 }
		 //删除一个节点
		 activetree.prototype.delnode=function(node){
		 	node.remove();
		 }
}

/*
	
*	@id为显示树的id(必选)
*		type为加载树的类型(必选)1为延迟加载树、2为异步加载树
*		treejson初始化的树的节点(必选)
*		treeurl当type为2是调用的url地址(当type为2必选，type为1是可设置为null)
*		functions点击树的节点是执行的动作
*/
function customtree(id,type,treejson,treeurl,functions,expend){
		this.tree=null;
		this.treejson=treejson;
		this.treeurl=treeurl;
		this.expend=expend;
		this.root=null;
		this.type=type;
		var _this=this;
		Ext.onReady(function(){
		     _this.root =new Ext.tree.AsyncTreeNode({
								id:'root',
								text:"根节点",
								children:_this.treejson
							});
			  var load1=new Ext.tree.TreeLoader();
			  var load2=new Ext.tree.TreeLoader({
					            dataUrl:_this.treeurl
					        })
		      _this.tree=new Ext.tree.TreePanel({
			        renderTo:id,
			        root:_this.root,//定位到根节点
			        animate:true,//开启动画效果
			        enableDD:false,//不允许子节点拖动
			        border:false,//没有边框
			        rootVisible:false,//设为false将隐藏根节点，很多情况下，我们选择隐藏根节点增加美观性
			        loader: _this.type==1?load1:load2
		     });
		     if(_this.expend){
		     	_this.tree.expandAll();
		     }
		     if(typeof(functions)!="function")functions=function(){};
		     _this.tree.on("click",functions);
		});
		//返回定义的tree对象，抛出对外接口
		customtree.prototype.tree=function(){
			return this.tree;
		 }
		 //添加一个新的节点
         customtree.prototype.addnode=function(node,json){
            node.leaf=false;
            var trees=new Ext.tree.TreeNode({
                        id:json.id,
                        text:json.text,
                        icon:json.icon,
                        leaf:json.leaf
            });
            node.appendChild(trees);
         }
         //删除一个节点
         customtree.prototype.delnode=function(node){
            node.remove();
         }
}