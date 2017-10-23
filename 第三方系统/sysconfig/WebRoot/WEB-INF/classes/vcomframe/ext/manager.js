/*
* 依赖Ext JS Library 2.1
* add：刘志强<liuzhiqiang@zzvcom.com>
* 支持的编码utf-8
*
*
* 功能点有： 
* 一、首页界面布局和权限树的生成 
* 二、参数说明
*	@type为显示树的类型(必选)1为延迟加载树（第一级为qq面板）、2为异步加载树（第一级为qq面板）、3为延迟加载树，不带qq面板、4为异步加载树，不带qq面板
*		readerline为从json中所取的制(必选)
*		tabcount分页面板最大设定的个数(必选)
*		treeurl如果启用异步加载，需要的url地址(必选)
*		nexttreejson如果启用第一级为qq面板且异步加载树，需要的第二级的树信息。
* 三、exemple
*		var treejson=<s:property value="listjson" escape="false"/>;
*		var type=<s:property value="type" escape="false"/>;//值为1表明按照延迟加载的方式显示树结构（第一级为qq面板）、值为2表明为异步方式加载树结构（第一级为qq面板）、3、4
*		var tabcount=10;
*		var url="tree.action";
*		var nexttreejson=<s:property value="nexttreejson" escape="false"/>;
*		var managelayout=new managelayout(type,treejson,tabcount,url,nexttreejson);
*/
function managelayout(type,treejson,tabcount,treeurl,nexttreejson){
var iframe_tpl,tab;
//定义对外引用添加分页面板功能
managelayout.prototype.addpage=function(title,url,id){
	addPage(title,url,id);
}
//定义对外引用删除分页面板功能
managelayout.prototype.removepage=function(id){
	var tabIDs=(id?("tabPage-"+id):("tabPage-"+replaceC(title+"-"+url)));
	if(p=tab.getComponent(tabID)){
        tab.remove(p);
        return;
    }
}
function showmessage(obj){
	Ext.onReady(function(){
		Ext.MessageBox.show({
           title:vcomframe.manange_alert,
           msg: obj,
           buttons: Ext.MessageBox.OK,
           animEl: 'mb9',
           fn: null,
           icon: Ext.MessageBox.WARNING
       });
	});
}
//添加分页面板功能
function addPage(title,url,id,tp){
    var p;
    if(!tabcount)tabcount=10;
    var tabID=(id?("tabPage-"+id):("tabPage-"+replaceC(title+"-"+url)));
    if(p=tab.getComponent(tabID)){
         tab.remove(p);
        //tab.setActiveTab(p);
        //return;
    }
    if(tab.items.length>=tabcount){
    	showmessage(vcomframe.manange_message);
    	return;
    }
    var newTab={
        id:tabID, 
        title:title,
        closable: true,
        autoScroll:true
    };
    if(!tp){
        newTab.html=iframe_tpl.applyTemplate({id:id||"",src:url,scroll:"no"});
    }else{
        newTab.autoLoad={url:url,text:"",scripts: true};
    }
    ____type=1;
    tab.setActiveTab(tab.add(newTab));
}
Ext.onReady(panel=function(){
	var treepanel;
   	if(treejson){//���Panel���
   		treepanel=[];
   		for(var i=0;i<treejson.length;i++){
   			var d={
					title:'',
					border:false,
					html:'',
					id:0
					//iconCls:'nav'
			    };
			 d.title=treejson[i].text;
			 d.html="<div id=\"tree"+i+"\" style=\"overflow:auto;width:100%;height:100%\"></div>";
			 d.id=treejson[i].id;
			 treepanel[i]=d;
   		}
   	}
    iframe_tpl = new Ext.Template();
    iframe_tpl.set('<iframe id="{id}" scrolling="{scroll}"  marginheight="0" marginwidth="0" width="100%" height="100%" src="{src}" frameborder="0"></iframe>',true);
    tab = new Ext.TabPanel({
			region:'center',
			deferredRender:false,
			activeTab:0,
			resizeTabs:true, // turn on tab resizing
			minTabWidth: 115,
			tabWidth:135,
			enableTabScroll:true,
			items:[{
                title:'\u7CFB\u7EDF\u6B22\u8FCE\u9875',
                html:iframe_tpl.applyTemplate({id:"tabPage-initPage",scroll:"auto",src:welcomepage}),
                autoScroll:true
        }]
	});
	tab.on("beforeremove",function(a,b){
		try{
			var iFrame=document.getElementById(b.id).getElementsByTagName("iframe");
	    	iFrame[0].src="about:blank";
	    	var farems=iFrame[0].contentWindow.document.getElementsByTagName("iframe");
	    	for(var i=0;i<farems.length;i++){
	    		farems[0].src="about:blank";
	    		farems[0].contentWindow.document.write('');
	    	}
	    	iFrame[0].contentWindow.document.write('');
	    	//iFrame[0].parentNode.removeChild(iFrame[0]);
			CollectGarbage();
		}catch(e){}
    	return true;
    });

	var east=new Ext.Panel({
			region:'east',
            title: 'East Side',
            collapsible: true,
            split:true,
            width: 225,
            minSize: 175,
            maxSize: 300,
            layout:'fit',
            margins:'0 5 0 0',
            items:
            new Ext.TabPanel({
                border:false,
                activeTab:0,
                tabPosition:'bottom',
                items:[{
                   html:'<p>A TabPanel component can be a region.</p>',
                   title: 'A Tab',
                   autoScroll:true
                   }]
             })          
	});
	var north=new Ext.Panel({
			region:'north',
	        contentEl: 'north',
	        split:true,
	        height: 100,
	        minSize: 100,
	        maxSize: 200,
	        collapsible: true,
	        title:'顶部',
	        margins:'0 0 0 0'
	});
	
	var west;
	if(type==1||type==2){
		west=new Ext.Panel({
				region:'west',
				id:'west-panel',
				split:true,
				width: 200,
				minSize: 175,
				maxSize: 400,
				margins:'0 0 0 0',
				layout:'accordion',
				title:'\u7CFB\u7EDF\u83DC\u5355',
				collapsible :true,
				layoutConfig:{
					animate:true
					},
			    items: treepanel
		});
	}else if(type==3||type==4){
		west=new Ext.Panel({
				region:'west',
				id:'west-panel',
				split:true,
				width: 200,
				minSize: 175,
				maxSize: 400,
				draggable:false,
				margins:'0 0 0 0',
				layout:'accordion',
				title:'\u7CFB\u7EDF\u83DC\u5355',
				collapsible :true,
				html:'<div id="menuTree" style="padding:5px 3px;overflow:auto;width:100%;height:100%"></div>'
		});
	}
	
 	var north1=new Ext.BoxComponent({
				region:'north',
				el: 'north',
				height:60
	});
	var south=new Ext.BoxComponent({
				region:'south',
				el: 'south',
				height:25
	})
   var viewport = new Ext.Viewport({
		layout:'border',
		items:[north1,south,west,tab]
	});

	//������ṹ
	if(type==1){//延迟加载树（第一级为qq面板）����
		if(treejson){
	   		for(var i=0;i<treejson.length;i++){
	   			var tree2=new Ext.tree.TreePanel({
					renderTo:"tree"+i,
					root:new Ext.tree.AsyncTreeNode({
							    id:treejson[i].id,
							    text:"\u6839\u76EE\u5F55",
							    children:treejson[i].children
							}),
					animate:true,
					enableDD:false,
					border:false,
					rootVisible:false,
					containerScroll: true,
					loader: new Ext.tree.TreeLoader()
				});
				tree2.on("click",function(node,e){
					e.stopEvent();//阻止打开动作
			        if(!node.leaf){
			        	node.expand();
			        	return; //如果不是叶子节点，则不执行打开动作 
			        }
			        if(____type==1){
			        	showmessage("请不要频繁打开窗口!");
			        	return ;
			        }
			        addPage(node.attributes.text,node.attributes.href,node.id,node.attributes.loadType||0)
			    });
			    try{
				    tree2.expandAll();
				 }catch(e){}
	   		}
	   	}
	}else if(type==2){//�异步加载树（第一级为qq面板）
		if(nexttreejson){
	   		for(var i=0;i<treejson.length;i++){
	   			var tree2=new Ext.tree.TreePanel({
					renderTo:"tree"+i,
					root:new Ext.tree.AsyncTreeNode({
							    id:treejson[i].id,
							    text:"\u6839\u76EE\u5F55",
							    children:nexttreejson[i]
							}),
					animate:true,
					enableDD:false,
					border:false,
					rootVisible:false,
					containerScroll: true,
					loader: new Ext.tree.TreeLoader({
				            dataUrl:treeurl
				        })
				});
				tree2.on("click",function(node,e){
					e.stopEvent();
					if(!node.leaf){
			        	node.expand();
			        	return; //如果不是叶子节点，则不执行打开动作 
			        }
			        if(e.getTarget('span', 2))
			        addPage(e.getTarget('span', 2).innerHTML,node.attributes.href,node.id,node.attributes.loadType||0)
			    });
	   		}
	   	}
	}else if(type==3){//延迟加载树,不带qq面板
		var tree2=new Ext.tree.TreePanel({
					renderTo:"menuTree",
					root:new Ext.tree.AsyncTreeNode({
							    id:'root',
							    text:"\u6839\u76EE\u5F55",
							    children:treejson
							}),
					animate:true,
					enableDD:false,
					border:false,
					rootVisible:false,
					containerScroll: true,
					loader: new Ext.tree.TreeLoader()
				});
				tree2.on("click",function(node,e){
					
			        e.stopEvent();
			        if(!node.leaf){
			        	node.expand();
			        	return; //如果不是叶子节点，则不执行打开动作 
			        }
			        if(e.getTarget('span', 2))
			        addPage(e.getTarget('span', 2).innerHTML,node.attributes.href,node.id,node.attributes.loadType||0)
			    });
			    try{
				    tree2.expandAll();
				  }catch(e){}
	}else if(type==4){//�异步加载树,不带qq面板
		var tree2=new Ext.tree.TreePanel({
					renderTo:"menuTree",
					root:new Ext.tree.AsyncTreeNode({
							    id:'root',
							    text:"\u6839\u76EE\u5F55",
							    children:treejson
							}),
					animate:true,
					enableDD:false,
					border:false,
					rootVisible:false,
					containerScroll: true,
					loader: new Ext.tree.TreeLoader({
				            dataUrl:treeurl
				        })
				});
				tree2.on("click",function(node,e){
					e.stopEvent();
			        if(!node.leaf){
			        	node.expand();
			        	return; //如果不是叶子节点，则不执行打开动作 
			        }
			        if(e.getTarget('span', 2))
			        addPage(e.getTarget('span', 2).innerHTML,node.attributes.href,node.id,node.attributes.loadType||0)
			    });
	}
	
	
    //tree2
	//east.setVisible(false);
	//east.collapse();
	//end loding
	setTimeout(
				function() {
					Ext.get('loading').remove();
					Ext.get('loading-mask').fadeOut({remove:true});
				}, 250
			  );
});
}
