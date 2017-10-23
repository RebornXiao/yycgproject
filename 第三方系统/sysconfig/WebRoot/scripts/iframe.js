/*================================================================================
								系统框架页面js
							author:张磊2008.06.20
================================================================================*/
	
//顶部panel
var topPanel;
//创建标签Panel对象
var tabs;
//底部panel
var bottomPanel;
//首页层框架
var viewport;

//首页页面框架加载
function windowInit(){
	//Ext预读方法
	Ext.onReady(function(){

		tabs = new Ext.TabPanel({
			region:'center',
			contentEl:'center',
			deferredRender:false,
			activeTab:0,
			resizeTabs:true,
			minTabWidth: 110,
			tabWidth:110,
			enableTabScroll:true,
			defaults: {autoScroll:true},
			plugins: new Ext.ux.TabCloseMenu(),
			items:[{
				id:'m0',
				title: '功能菜单',
				//iconCls: 'tabs',
				contentEl:'tablayerm0'
				//disabled:true
			}]
		});
		topPanel = new Ext.Panel({
			region:'north',
			contentEl: "north",
			height: 60
		});
		bottomPanel = new Ext.Panel({
			region:'south',
			contentEl: "south",
			//height: 140,
			height: 25,
			minSize:30,
			maxSize:900,
			split:true,
			collapsible:true
			//collapseMode:"mini"
		});
		viewport = new Ext.Viewport({
			layout:'border',
			items:[topPanel,bottomPanel,tabs]
		});
	});
}


//创建标签时间数组对象
var tabCreateTimeList = [];

//创建标签时间对象;
function tabCreateTime(id){
	this.id = id;
	this.time = new Date();
}

//更新标签创建时间
function updateTabTime(tabID){
	for(var i = 0; i < tabCreateTimeList.length; i++)
	{
		var curTabCreateTimeObj = tabCreateTimeList[i];
		if(curTabCreateTimeObj.id == tabID){
			tabCreateTimeList[i] = new tabCreateTime(tabID);
		}
	}
}

/*
*标签页url解析创建form元素字符串
*
*url 标签页url
*/
function getTabIframeFormEleStr(url){
	var urlArray = url.split("?");
	if(urlArray.length == 1){
		return "";
	}
	var parm = urlArray[1];
	var parmArray = parm.split("&");
	var parmStr = "";
	for(var m=0;m<parmArray.length;m++){
		var curArray = parmArray[m].split("=");
		var curName = curArray[0];
		var curValue = curArray[1];
		parmStr += "<input type='hidden' name='"+curName+"' value='"+curValue+"'/>";
	}
	return parmStr;
}

/*
*标签页url解析得到action
*
*url 标签页url
*/
function getTabIframeFormAction(url){
	var urlArray = url.split("?");
	return urlArray[0];
}

//声明标签div各参数字符串
var tabLayerDivStr1 = "<div id='";
var tabLayerDivStr2 = "'><div id='";
var tabLayerDivStr3 = "' style='position:absolute;z-index:1;width:100%;height:100%;background-color:#E9F2FC;'><center><br><br><br><br><br><br><br><br><br><br><div style='width:100px;background-color:#C7D6E9;'><table cellpadding='5' cellspacing='5' style='margin:3px;width:100px;border:1px solid #A3BAE9;'><tr><td>&nbsp;&nbsp;<img align='absmiddle' src='images/whole_wait.gif'></img><font size='5'><b>Loading...</b></font>&nbsp;&nbsp;</td></tr></table></div></center></div><iframe style='overflow-x:hidden;' name='";
var tabLayerDivStr4 = "' id='";
var tabLayerDivStr5 = "' src='' frameborder='0'  width='100%' height='100%'></iframe><form id='";
var tabLayerDivStr6 = "' name='";
var tabLayerDivStr7 = "' action='";
var tabLayerDivStr8 = "' method='post' target='";
var tabLayerDivStr9 = "'>";
var tabLayerDivStr10 = "</form></div>";

//添加标签
function addTabToIframe(tabID,title,loadUrl){
	var tabLayerDivId = "tablayer" + tabID;
	var tabShadowLayerDivId = "tabshadowlayer" + tabID;
	var tabIframeId = "tabiframe" + tabID;
	var tabFormId = "tabform"+ tabID;
	var tabIframeFormAction = getTabIframeFormAction(loadUrl);
	var tabIframeFormEleStr = getTabIframeFormEleStr(loadUrl);
	var tabLayerDivStr = tabLayerDivStr1 
						+ tabLayerDivId 
						+ tabLayerDivStr2 
						+ tabShadowLayerDivId 
						+ tabLayerDivStr3 
						+ tabIframeId 
						+ tabLayerDivStr4 
						+ tabIframeId 
						+ tabLayerDivStr5 
						+ tabFormId 
						+ tabLayerDivStr6 
						+ tabFormId 
						+ tabLayerDivStr7 
						+ tabIframeFormAction 
						+ tabLayerDivStr8 
						+ tabIframeId 
						+ tabLayerDivStr9 
						+ tabIframeFormEleStr 
						+ tabLayerDivStr10;
	var dh = Ext.DomHelper;
	dh.insertHtml('beforeEnd',center,tabLayerDivStr);
	
	tabs.add({
		id:tabID,
		title: '<div title='+title+'>'+title+'</div>',
		//iconCls: 'tabs',
		contentEl:tabLayerDivId,
		listeners: {activate: handleActivate},
		closable:true
	}).show();
	document.getElementById(tabFormId).submit();
}
//声明标签div各参数字符串(定制)
var tabLayerDivStr5_ = "' src='";
var tabLayerDivStr6_ = "' frameborder='0'  width='100%' height='100%'></iframe></div>";
//添加标签(定制)
function addTabToIframeCustomize(tabID,title,loadUrl){
	var tabLayerDivId = "tablayer" + tabID;
	var tabShadowLayerDivId = "tabshadowlayer" + tabID;
	var tabIframeId = "tabiframe" + tabID;
	var tabLayerDivStr = tabLayerDivStr1 
						+ tabLayerDivId 
						+ tabLayerDivStr2 
						+ tabShadowLayerDivId 
						+ tabLayerDivStr3 
						+ tabIframeId 
						+ tabLayerDivStr4 
						+ tabIframeId 
						+ tabLayerDivStr5_ 
						+ loadUrl 
						+ tabLayerDivStr6_;
	var dh = Ext.DomHelper;
	dh.insertHtml('beforeEnd',center,tabLayerDivStr);
	tabs.add({
		id:tabID,
		title: '<div title='+title+'>'+title+'</div>',
		//iconCls: 'tabs',
		contentEl:tabLayerDivId,
		listeners: {activate: handleActivate},
		closable:true
	}).show();
}

//得到最旧标签项
function getOldestTabItem(){
	var oldestTabItemTime = null;
	var oldestTabItemId = null;
	var oldestTabItem = null;
	for(var i = 0; i < tabCreateTimeList.length; i++){
		var curTabCreateTimeObj = tabCreateTimeList[i];
		var curObjTime = curTabCreateTimeObj.time;
		var curObjId = curTabCreateTimeObj.id;
		if(i === 0){
			oldestTabItemTime = curObjTime;
			oldestTabItemId = curObjId;
		}else{
		if(curObjTime < oldestTabItemTime){
			oldestTabItemTime = curObjTime;
			oldestTabItemId = curObjId;
		}
		}
	}
	if(oldestTabItemId !== null){
		oldestTabItem = tabs.getItem(oldestTabItemId);
	}
	return oldestTabItem;
}

//删除最旧标签项
function removeOldestTabItem(){
	var item = getOldestTabItem();
	if(item !== null){
		tabs.remove(item);
	}
}

//得到标签数量(并过滤当前标签时间)
function getTabsItemCount(){
	var i=0;
	var temp = [];
	tabs.items.each(function(){
	for(var j = 0; j < tabCreateTimeList.length; j++){
		var curTabCreateTimeObj = tabCreateTimeList[j];
		if(curTabCreateTimeObj.id == this.id){
			temp[temp.length] = curTabCreateTimeObj;
		}
	}
	i++;
	});
	tabCreateTimeList = temp;
	return i;
}
	
//标签活动事件
function handleActivate(tab){
	refurbishCurTabPanel();
}

//得到当前活动标签ID
function getCurActiveTabId(){
	return tabs.getActiveTab().getId();
}

//更新标签Iframe
function updateTabIframeSrc(tabID,loadUrl){
	var tabFormId = "tabform" + tabID;
	var inputArray = document.getElementById(tabFormId).getElementsByTagName("input");
	for(var i=inputArray.length-1;i>=0;i--){
		inputArray[i].parentElement.removeChild(inputArray[i]);
	}
	var urlArray = loadUrl.split("?");
	if(urlArray.length > 1){
		var parm = urlArray[1];
		var parmArray = parm.split("&");
		for(var m=0;m<parmArray.length;m++){
			var curArray = parmArray[m].split("=");
			var curName = curArray[0];
			var curValue = curArray[1];
			//var tempEle = document.createElement("<input type='hidden' name='"+curName+"' value='"+curValue+"'>");
			var tempEle = document.createElement("input");  
			tempEle.type="hidden";  
			tempEle.name=curName;  
			tempEle.value=curValue;
			document.getElementById(tabFormId).appendChild(tempEle);
		}
	}
	document.getElementById(tabFormId).submit();
}
//更新标签Iframe地址(定制)
function updateTabIframeSrcCustomize(tabID,loadUrl){
	jqueryGetEleById("tabiframe" + tabID).attr('src',loadUrl);
}

//刷新当前标签
function refurbishCurTabPanel(){
	var curTabId = getCurActiveTabId();
	updateTabTime(curTabId);
}

//显示标签
/*
function showTabPanel(tabID,title,loadUrl){
	var tabobj = tabs.getItem(tabID);
	if(tabobj === undefined){
		if(getTabsItemCount() >= 9){
			removeOldestTabItem();
		}
		addTabToIframe(tabID,title,loadUrl);
		tabCreateTimeList[tabCreateTimeList.length] = new tabCreateTime(tabID);
	}else{
		updateTabIframeSrc(tabID,loadUrl);
		tabobj.show();
	}
	loadingShow(tabID);
}
*/
function showTabPanel(tabID,title,loadUrl){
	var tabobj = tabs.getItem(tabID);
	if(tabobj === undefined){
		if(getTabsItemCount() >= 9){
			removeOldestTabItem();
		}
		addTabToIframe(tabID,title,loadUrl);
		tabCreateTimeList[tabCreateTimeList.length] = new tabCreateTime(tabID);
	}else{
	updateTabIframeSrc(tabID,loadUrl);
	tabobj.show();
    }
	loadingShow(tabID);
}
//显示标签(定制)
function showTabPanelCustomize(tabID,title,loadUrl){
	var tabobj = tabs.getItem(tabID);
	if(tabobj === undefined){
		if(getTabsItemCount() >= 9){
			removeOldestTabItem();
		}
		addTabToIframeCustomize(tabID,title,loadUrl);
		tabCreateTimeList[tabCreateTimeList.length] = new tabCreateTime(tabID);
	}else{
		
		updateTabIframeSrcCustomize(tabID,loadUrl);
		tabobj.show();
	}
	loadingShow(tabID);
}

/*loading加载*/
function loadingShow(tabID){
	if(!window.ActiveXObject){
        jqueryGetEleById("tabshadowlayer" + tabID).hide();
    }else{
    	jqueryGetEleById("tabshadowlayer" + tabID).show();
		var curIframe = document.getElementById("tabiframe" + tabID);
		curIframe.onreadystatechange = function(){
			var state = this.readyState;
            if (state == "complete") {
				jqueryGetEleById("tabshadowlayer" + tabID).hide();
            	this.onreadystatechange = null; 
			} 
		};
    }
}







