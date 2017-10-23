/*================================================================================
								jquery Form提交封装
								author:张磊2008.06.11
================================================================================*/

//loading框对象
var extLoadingModalWin = null;

jQuery.noConflict();

/*
*jquery预读方法
*/
jQuery(document).ready(function(){
	loadingReady();
}); 

/*
*Ext预读方法
*/
Ext.onReady(function() {
	
});


/*
*询问提示框
*
*title 标题
*msg 内容
*isicon 是否有询问图标
*callbackfn 成功回调函数名(要求函数必须不能超过一个的参数)
*param 回调函数参数
*/
function extAlertConfirm(title,msg,isicon,callbackfn,param){
	var iconObj = null;
	if(isicon !== null && isicon === true){
		iconObj = Ext.MessageBox.QUESTION;
	}
	Ext.Msg.show({
		title: title,
		msg: msg,
		buttons: Ext.Msg.YESNO,
		icon: iconObj,
		minWidth: 190,
		fn: function(btn){
			if(btn == 'yes'){
				if(param === null){
					callbackfn();
				}else{
					callbackfn(param);
				}
			}
		}
	});
}

/*
*确认提示框
*
*title 标题
*msg 内容
*/
function extAlert(title,msg){
	Ext.Msg.show({
		title: title,
		msg: msg,
		buttons: Ext.Msg.OK,
		minWidth: 110
	});
}

/*
*确认提示框
*
*title 标题
*msg 内容
*icon 图标('info'/'question'/'warning'/'error')
*/
function extAlertIcon(title,msg,icon){
	var iconObj = null;
	if(icon !== null){
		icon = icon.toLowerCase();
		if(icon == 'info'){
			iconObj = Ext.MessageBox.INFO;
		}else if(icon == 'question'){
			iconObj = Ext.MessageBox.QUESTION;
		}else if(icon == 'warning'){
			iconObj = Ext.MessageBox.WARNING;
		}else if(icon == 'error'){
			iconObj = Ext.MessageBox.ERROR;
		}
	}
	Ext.Msg.show({
		   title: title,
		   msg: msg,
		   buttons: Ext.Msg.OK,
		   minWidth: 110,
		   icon: iconObj
	});
}

var SANMIAOHOUGUANBI="您的操作成功！<br>3秒后自动关闭提示框";
/*
*确认提示框
*
*title 标题
*msg 内容
*callbackfn 成功回调函数名(要求函数必须不能超过一个的参数)
*param 回调函数参数
*isno3laterclose 是否3秒后不自动关闭提示框
*/
function extAlertCall(title,msg,callbackfn,param,isno3laterclose){
	var isExecute = false;
	Ext.Msg.show({
		title: title,
		msg: msg,
		buttons: Ext.Msg.OK,
		minWidth: 110,
		fn: function(btn){
			isExecute = true;
			if(param === null){
				if(callbackfn)callbackfn();
			}else{
				if(callbackfn)callbackfn(param);
			}
		}
	});
	if(!isno3laterclose && msg.indexOf("您的操作成功") != -1){
		setTimeout(function(){
			if(!isExecute){
				Ext.Msg.hide(); 
				if(param === null){
					if(callbackfn)callbackfn();
				}else{
					if(callbackfn)callbackfn(param);
				}
			}
		},1000*3);
	}
}

/*
*确认提示框
*
*title 标题
*msg 内容
*icon 图标('info'/'question'/'warning'/'error')
*callbackfn 成功回调函数名(要求函数必须不能超过一个的参数)
*param 回调函数参数
*/
function extAlertIconCall(title,msg,icon,callbackfn,param){
	var iconObj = null;
	if(icon !== null){
		icon = icon.toLowerCase();
		if(icon == 'info'){
			iconObj = Ext.MessageBox.INFO;
		}else if(icon == 'question'){
			iconObj = Ext.MessageBox.QUESTION;
		}else if(icon == 'warning'){
			iconObj = Ext.MessageBox.WARNING;
		}else if(icon == 'error'){
			iconObj = Ext.MessageBox.ERROR;
		}
	}
	Ext.Msg.show({
		title: title,
		msg: msg,
		buttons: Ext.Msg.OK,
		minWidth: 110,
		icon: iconObj,
		fn: function(btn){
			if(param === null){
				callbackfn();
			}else{
				callbackfn(param);
			}
		}
	});
}

/*
*模式框url解析创建form元素字符串
*
*url 模式框url
*/
function getModalWinIframeFormEleStr(url){
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
*模式框url解析得到action
*
*url 模式框url
*/
function getModalWinIframeFormAction(url){
	var urlArray = url.split("?");
	return urlArray[0];
}

//模式框对象
var extModalWin = null;
var modalWinLayerDivStr1 = "<div id='";
var modalWinLayerDivStr2 = "'><div id='";
var modalWinLayerDivStr3 = "' style='position:absolute;z-index:1;width:100%;height:100%;background-color:#E9F2FC;'><center><br><br><br><div style='width:100px;background-color:#C7D6E9;'><table cellpadding='5' cellspacing='5' style='margin:3px;width:100px;border:1px solid #A3BAE9;'><tr><td>&nbsp;&nbsp;<img align='absmiddle' src='images/whole_wait.gif'></img><font size='5'>Loading...</font>&nbsp;&nbsp;</td></tr></table></div></center></div><iframe scrolling='yes' style='overflow-x:hidden;' name='";
var modalWinLayerDivStr4 = "' id='";
var modalWinLayerDivStr5 = "' src='' frameborder='0' width='100%' height='100%'></iframe><form id='iframeform' name='iframeform' action='";
var modalWinLayerDivStr6 = "' method='post' target='";
var modalWinLayerDivStr7 = "'>";
var modalWinLayerDivStr8 = "</form></div>";
/*
*模式框
*
*loadUrl 加载url
*title 标题
*width 宽度
*height 高度
*isDrag 是否可以拖动
*/
function extModalWinShow(loadUrl,title,width,height,isDrag){
	var milliSeconds = new Date().getMilliseconds();
	var modalWinLayerDivId = "layer" + milliSeconds;
	var modalWinIframeId = "iframe" + milliSeconds;
	var modalShadowLayerDivId = "shadowlayer" + milliSeconds;
	var modalIframeFormAction = getModalWinIframeFormAction(loadUrl);
	var modalIframeFormEleStr = getModalWinIframeFormEleStr(loadUrl);
	var modalWinLayerDivStr = modalWinLayerDivStr1 
							+ modalWinLayerDivId 
							+ modalWinLayerDivStr2 
							+ modalShadowLayerDivId 
							+ modalWinLayerDivStr3 
							+ modalWinIframeId 
							+ modalWinLayerDivStr4 
							+ modalWinIframeId 
							+ modalWinLayerDivStr5 
							+ modalIframeFormAction 
							+ modalWinLayerDivStr6 
							+ modalWinIframeId 
							+ modalWinLayerDivStr7 
							+ modalIframeFormEleStr 
							+ modalWinLayerDivStr8;
	var dh = Ext.DomHelper;
	dh.insertHtml('beforeEnd',active,modalWinLayerDivStr);
	modelLoadingShow(milliSeconds);
	var modalWinLayerObj = Ext.get(modalWinLayerDivId);
	extModalWin = new Ext.Window({
	    plain: true,
	    title: title,
	    width: width,
	    height: height,
        draggable: isDrag,
		modal: true,
		resizable: false,
		autoDestroy: true,
		//maximizable:true,
		//minimizable:true,
	    items: modalWinLayerObj
    });
   
    extModalWin.show();
    document.getElementById('iframeform').submit();
}

/*
*模式框销毁
*/
function extModalWinDestroy(){
	if(extModalWin !== null){
		extModalWin.close();
		extModalWin = null;
	}
}

/*
*非模式框(返回对象，.show() .hide() 两个方法来调用显示和隐藏)
*
*title 标题
*width 宽度
*height 高度
*x left距离
*y top距离
*renderTo 框架div id
*contentEl 内容div id
*isDrag 是否可以拖动
*/
function extDivWin(title,width,height,x,y,renderTo,contentEl,isDrag){
	var extDivWinObj = new Ext.Window({
	    plain: true,
	    title: title,
	    width: width,
	    height: height,
		modal: false,
		draggable: isDrag,
		resizable: false,
		closeAction:'hide',
		renderTo:renderTo,
		autoScroll:true,
		x:x,
		y:y,
	    contentEl: contentEl 
    });
    return extDivWinObj;
}


function extHandleDivWin(title,width,height,x,y,renderTo,contentEl,isDrag,buttons,closable){
	var extDivWinObj = new Ext.Window({
	    plain: true,
	    title: title,
	    width: width,
	    height: height,
		modal: false,
		draggable: isDrag,
		resizable: false,
		closable:closable,
		closeAction:'hide',
		renderTo:renderTo,
		autoScroll:true,
		x:x,
		y:y,
	    contentEl: contentEl,
	    buttonAlign:'center',
	    buttons:buttons
    });
    return extDivWinObj;
}

/*
*层区域块儿
*
*renderTo 框divid
*contentEl 内容divid
*width 宽
*height 高
*/
function extDivPanel(renderTo,contentEl,width,height){
	var p = new Ext.Panel({
		renderTo: renderTo,
	    width:width,
	    height:height,
	    autoScroll:true,
	    contentEl: contentEl
	});
}

/*模式框框架loading加载*/
function modelLoadingShow(param){
	if(!window.ActiveXObject){
        jqueryGetEleById("shadowlayer" + param).hide();
    }else{
    	jqueryGetEleById("shadowlayer" + param).show();
		var a = document.getElementById("iframe" + param);
		a.onreadystatechange = function(){
			var state = this.readyState;
            if (state == "complete") {
			jqueryGetEleById("shadowlayer" + param).hide();
            	this.onreadystatechange = null; 
			} 
		};
    }
}

/*
*ext框架结构左右模式
*
*leftLayerId 左层ID
*leftWidth 左层宽度
*leftTitle 左层Title
*rightLayerId 右层ID
*/
function extFrameModel(leftLayerId,leftWidth,leftMinWidth,leftMaxWidth,leftTitle,rightLayerId){
	Ext.onReady(function(){
	var viewport = new Ext.Viewport({
			layout:'border',
			items:[{
				region:'west',
				contentEl: leftLayerId,
				title:"<img src='/images/title_search.gif'/>&nbsp;"+leftTitle,
				split:true,
				width: leftWidth,
				minSize: leftMinWidth,	
				//maxSize: leftMaxWidth,先屏蔽掉
				collapsible: true,
				autoScroll:true
			},{
				region:'center',
				contentEl: rightLayerId,
				split:true,
				autoScroll:true
			}]
		});
	});
}

/*
*ext框架结构整页面模式
*
*id ID
*/
function extFrameModelFull(id){
	Ext.onReady(function(){
	var viewport = new Ext.Viewport({
			layout:'border',
			items:[{
				region:'center',
				contentEl: id,
				split:true,
				autoScroll:true
			}]
		});
	});
}

/*ext右键菜单对象*/
var extRightMenu = null;

/*
*ext右键菜单创建
*
*items 菜单项
*(items为数组对象,数组元素items[i]也为数组对象,
*其中items[i][0]为菜单文字,items[i][1]为事件函数指针,
*菜单文字为'-'时,为分割条,
*例如[['菜单项1',function],['菜单项2',function],['-',null],['菜单项3',null]])
*/
function createExtRightMenu(items){
	extRightMenu = null;
	var modelItems = [];
	for(var i=0;i<items.length;i++){
		if(items[i][0] == '-'){
			modelItems[i] = '-';	
		}else{
			modelItems[i] = {text:items[i][0],handler:items[i][1]};
		}
	}
	extRightMenu = new Ext.menu.Menu({
		id:'rightClickCont',
		items:modelItems
	});
}
 
/*
*ext右键菜单显示(oncontextmenu事件驱动,可以自定义js函数中先调用createExtRightMenu(items)来动态初始化，然后调用本函数显示)
*/
function extRightMenuShow(){
	Ext.EventObject.preventDefault();
	if(extRightMenu !== null){
		extRightMenu.showAt(Ext.EventObject.getXY());
	}
}

//loading框内html
var loadingModalWinLayerDivStr = "<div><center><img align='absmiddle' src='images/whole_wait.gif'></img><font size='2'>Loading...</font><br><a href='javascript:loadingHalt();'>取消</a></center></div>";
var loadingModalWinLayerDivStr_1 = "<div><center><img align='absmiddle' src='images/whole_wait.gif'></img><font size='2'>Loading...</font><br><a href=javascript:loadingHalt('";
var loadingModalWinLayerDivStr_2 = "');>取消</a></center></div>";

/*
*loading框创建并显示
*/
function extLoadingModalWinShow(fun_tag){
	var loadingModalWinLayerDivStr_l = loadingModalWinLayerDivStr_1+fun_tag+loadingModalWinLayerDivStr_2;
    if(extLoadingModalWin !== null){
      return ;
    }
	extLoadingModalWin = new Ext.Window({
	    plain: true,
	    width: 200,
	    height: 66,
        draggable: false,
		modal: true,
		closable:false,
		resizable: false,
		autoDestroy: true,
	    html:loadingModalWinLayerDivStr_l
    });
    extLoadingModalWin.show();
}

/*
*loading框销毁
*/
function extLoadingModalWinDestroy(){
	if(extLoadingModalWin !== null){
		extLoadingModalWin.close();
		extLoadingModalWin = null;
	}
}

/*
*loading准备
*/
var success_list = new Object();//临时数据对象
function loadingReady(){
	jQuery("body").ajaxSend(function(request,settings,uu){
		var success_tag = "fun_"+(new Date()).getTime();
		eval("success_list."+success_tag+"=uu;");
	    /*
		var result=-1;
	    if(uu&&uu.data){
	      result = uu.data.search("ajax_commit_display=0");
	    }
	    if(result<0){
	      extLoadingModalWinShow();
	    }
		*/
		extLoadingModalWinShow(success_tag);
	
	});
	jQuery("body").ajaxComplete(function(request, settings){
	       extLoadingModalWinDestroy();
	
	});
	jQuery("body").ajaxError(function(request, settings){
		extLoadingModalWinDestroy();
		extAlertIcon("系统提示","系统提交时出错!","error");
	});
}

/*
*loading中断
*/
function loadingHalt(fun_tag){
    eval("success_list."+fun_tag+".success=blank_fun;");
	extLoadingModalWinDestroy();
}

/*
*jQuery Dom元素选择器
*
*eleId 元素Id
*return 返回jQuery类型元素对象
*/
function jqueryGetEleById(eleId){
	return jQuery("#" + eleId);
}

/*
*jQuery Dom元素选择器(支持的元素类型'text'/'password'/'hidden'/'button'/'submit'/'checkbox'/'radio'/'select')
*
*eleName 元素Name
*return 返回jQuery类型元素对象(如果不支持类型或者无此元素则返回null)
*/
function jqueryGetEleByName(eleName){
	var curElement = document.getElementsByName(eleName)[0];
	if(curElement === undefined){
		return null;
	}
	var curEleType = curElement.type;
	var obj = null;
	if(curEleType == "text" || curEleType == "password" || curEleType == "hidden" || curEleType == "button" || curEleType == "submit" || curEleType == "radio" || curEleType == "checkbox"){
		obj = jQuery("input[@name=" + eleName + "]");
	}else if(curEleType == "select-one" || curEleType == "select-multiple"){
		obj = jQuery("select[@name=" + eleName + "]");
	}
	return obj;
}

/*
*jQuery 得到Form元素值(支持的元素类型'text'/'password'/'hidden'/'button'/'submit'/'checkbox'/'radio'/'select')
*
*eleId 元素Id
*return 返回元素的值或者用户选择的值,如果无此元素则返回null
*/
function jqueryGetEleValById(eleId){
	var objValue = jQuery("#" + eleId).val();
	objValue = (objValue === undefined) ? null : objValue;
	return objValue;
}

/*
*jQuery 得到Form元素值(支持的元素类型'text'/'password'/'hidden'/'button'/'submit'/'checkbox'/'radio'/'select')
*
*eleName 元素Name
*return 返回元素的值或者用户选择的值,如果无此元素或者此元素是checkbox,radio元素且用户未选择则返回null
*/
function jqueryGetEleValByName(eleName){
	var curElement = document.getElementsByName(eleName)[0];
	if(curElement === undefined){
		return null;
	}
	var curEleType = curElement.type;
	var objValue = null;
	if(curEleType == "text" || curEleType == "password" || curEleType == "hidden" || curEleType == "button" || curEleType == "submit"){
		objValue = jQuery("input[@name=" + eleName + "]").val();
	}else if(curEleType == "select-one" || curEleType == "select-multiple"){
		objValue = jQuery("select[@name=" + eleName + "]").val();
	}else if(curEleType == "radio"){
		objValue = jQuery("input[@name=" + eleName + "][@checked]").val();
	}else if(curEleType == "checkbox"){
		var a = document.getElementsByName(eleName);
	    var s = [];
	    for(var i = 0; i < a.length; i++)
	    {
	        if(a[i].checked)
	        {
	            s[s.length] = a[i].value;
	        }
	    }
	    objValue = (s === "") ? null : s;
	}
	objValue = (objValue === undefined) ? null : objValue;
	return objValue;
}
/*
*load div
*
*divid div id Name
*url url地址
*/
function jqueryLoad(divId,url){
	jQuery("#" + divId).load(url,{limit: new Date()}); 
}

/*
*load divJs 层装载执行过的js(在action或者页面接收参数divid)
*
*divid div id Name
*url url地址
*/
function jqueryLoadJs(divId,url){
	var data = "divid=" + divId;
    var callbackFn = function(responseText){
      eval(responseText);
    };
	jqueryAjax(url,data,callbackFn,null);
}
/*
*load divJs 层装载执行过的js(在action或者页面接收参数divid)
*
*formId form Id
*/
function jqueryLoadJsByFId(formId){
    var callbackFn = function(responseText){
      eval(responseText);
    };
    jquerySubByFId(formId,callbackFn,null);
}

/*
*form提交(post方式)
*
*formname form Name
*callbackfn 回调函数名(要求函数必须有参数且不能多与两个,一个参数时参数为响应文本,两个参数时第一个参数为响应文本)
*param 回调函数参数(如果为null,那么调用一个参数的回调函数,否则调用两个参数的回调函数)
*/
function jquerySubByFName(formName,callbackFn,param){
	var formObj = jQuery("form[@name=" + formName + "]");
	var options = {success: function(responseText) {
		if(param === null){
			callbackFn(responseText);
		}else{
			callbackFn(responseText,param);
		}
	}}; 
	formObj.ajaxSubmit(options); 
}
/*
*form提交(post方式)
*
*formId form Id
*callbackfn 回调函数名(要求函数必须有参数且不能多与两个,一个参数时参数为响应文本,两个参数时第一个参数为响应文本)
*param 回调函数参数(如果为null,那么调用一个参数的回调函数,否则调用两个参数的回调函数)
*/
function jquerySubByFId(formId,callbackFn,param){
	var formObj = jQuery("#" + formId);
	var options = {success: function(responseText) {
		if(param === null){
			callbackFn(responseText);
		}else{
			callbackFn(responseText,param);
		}
	}}; 
	formObj.ajaxSubmit(options); 
}


function blank_fun(p1,p2,p3){

}
/*
*多form提交(post方式)
*
*formIds form Ids(form1,form2,form3)
*action 提交地址
*callbackfn 回调函数名(要求函数必须有参数且不能多与两个,一个参数时参数为响应文本,两个参数时第一个参数为响应文本)
*param 回调函数参数(如果为null,那么调用一个参数的回调函数,否则调用两个参数的回调函数)
*/
function jqueryMulSubByFId(formIds,action,callbackFn,param){
	if(!window.ActiveXObject){
		var formName = "hiddenTempForm";
		jqueryGetEleById(formName).remove();
        var submitForm = document.createElement("FORM");
		submitForm.id = formName;
		submitForm.name = formName;
		submitForm.method = "POST";
		submitForm.action = action;
		var frms = formIds.split(",");
		for(var i = 0;i < frms.length;i++){
			var data = jqueryGetEleById(frms[i]).formSerialize().split("&");
			for(var j = 0;j < data.length;j++){
				var dataName = data[j].split("=")[0];
				var dataValue = jqueryGetEleValByName(dataName);//data[j].split("=")[1];
				var input = document.createElement('input');
				input.setAttribute('name', dataName);
    			input.setAttribute('type', 'hidden');
				input.setAttribute('value', dataValue);
				submitForm.appendChild(input);
			}
		}
		document.body.appendChild(submitForm);
		jquerySubByFId(submitForm.id,callbackFn,null);
    }else{
    	var frms = formIds.split(",");
		var frmHtml = "";
		for(var i = 0;i < frms.length;i++){
			frmHtml += jqueryGetEleById(frms[i]).html();
		}
		jqueryGetEleById("hiddenForm").attr("action",action);
		jqueryGetEleById("hiddenForm").html(frmHtml);
		jquerySubByFId("hiddenForm",callbackFn,null);
    }
}

/*
*ajax提交(get方式)
*
*url 提交地址
*data 提交数据
*callbackfn 回调函数名(要求函数必须有参数且不能多与两个，一个参数时参数为响应文本,两个参数时第一个参数为响应文本)
*param 回调函数参数(如果为null,那么调用一个参数的回调函数,否则调用两个参数的回调函数)
*/
function jqueryAjax(url,data,callbackFn,param){
	jQuery.ajax({
	    type: "POST",
	    url: url,
	    data: data,
		success: function(responseText){
			if(param == null){
				callbackFn(responseText);
			}else{
				callbackFn(responseText,param);
			}
		} 
  	}); 
}

/*==================================================
                     公用JS方法
				author:张磊2008.06.27
==================================================*/

/*总的复选框单击事件(变颜色)*/
function checkAll(_this, itemName) {
	var item = jqueryGetEleByName(itemName);
	if(null != item){
		for (var i = 0; i < item.length; i++) {
			var r=item[i].parentNode.parentNode
			item[i].checked = _this.checked;
			if(item[i].checked){
				jQuery(r).addClass("over");
			}else{
				jQuery(r).removeClass("over");
			}
		}
	}
}

/*总的复选框单击事件*/
function checkAllBase(_this, itemName) {
	var item = jqueryGetEleByName(itemName);
	if(null != item){
		for (var i = 0; i < item.length; i++) {
			var r=item[i].parentNode.parentNode
			item[i].checked = _this.checked;
		}
	}
}

/*唯一复选框单击事件*/
function chkRowOnly(_this, itemName) {
		var item = jqueryGetEleByName(itemName);
		if(null != item){
			for (var i = 0; i < item.length; i++) {
				var r=item[i].parentNode.parentNode
				item[i].checked = false;
				jQuery(r).removeClass("over");
			}
		}
		_this.checked = true;
		var r = _this.parentNode.parentNode;
		if(_this.checked){
			jQuery(r).addClass("over");
		}else{
			jQuery(r).removeClass("over");
		}
	}
	
	/*单选，而且可以取消单选*/
	function unchkRowOnly(_this, form,index) {
		if(_this.checked) {
			var r = _this.parentNode.parentNode;
			jQuery(r).addClass("over");
			for (i = 0; i < form.Check.length; i++) {
	            if (i == index) {
	            } else {
	               form.Check[i].checked = false;
	               var ri = form.Check[i].parentNode.parentNode;
	               jQuery(ri).removeClass("over");
	            }
	        }
		}
		else {
			var r = _this.parentNode.parentNode;
			for (i = 0; i < form.Check.length; i++) {	            
	            form.Check[i].checked = false;
	            var ri = form.Check[i].parentNode.parentNode;
	            jQuery(ri).removeClass("over");
	        }
		}
	}

/*复选框单击事件*/
function chkRow(obj){
	var r = obj.parentNode.parentNode;
	if(obj.checked){
		jQuery(r).addClass("over");
	}else{
		jQuery(r).removeClass("over");
	}
	return;
	var isAllHas = true;
	var item = jqueryGetEleByName(obj.name);
	for (var i = 0; i < item.length; i++) {
		if(!item[i].checked){
			isAllHas = false;
		}
	}
	var captain = jqueryGetEleById(obj.name);
	if(isAllHas){
		captain[0].checked = true;
	}else{
		captain[0].checked = false;
	}
}
/*得到所有选中的复选框value*/
function getAllCheck(itemName){
	var result = [];
	var item = jqueryGetEleByName(itemName);
	if(null != item){
		for (var i = 0; i < item.length; i++) {
			if(item[i].checked){
				result[result.length] = item[i].value;
			}
		}
	}
	return result;
}
/*得到所有选中的复选框value*/
function getAllCheckIE(itemName){
	var result = [];
	var item = document.getElementsByName(itemName);
	if(null != item){
		for (var i = 0; i < item.length; i++) {
			if(item[i].checked){
				result[result.length] = item[i].value;
			}
		}
	}
	return result;
}
/*得到所有选中的复选框对象*/
function getAllCheckObj(itemName){
	var result = [];
	var item = document.getElementsByName(itemName);
	if(null != item){
		for (var i = 0; i < item.length; i++) {
			if(item[i].checked){
				result[result.length] = item[i];
			}
		}
	}
	return result;
}
/*得到树选中相应文本*/
function getTreeText(treeName,chkName){
	var isReturn = false;
	eval("if(typeof("+treeName+")==\"undefined\"){isReturn=true;}");
	if(isReturn)return;
	var treeObj = eval(treeName);
	var treeTextArray = [];
	var allSelectObj=getAllCheckObj(chkName);
	for(var m=0;m<allSelectObj.length;m++){
		var curid = allSelectObj[m].id
		var curindex = curid.replace("c"+treeName,"");
		treeTextArray[treeTextArray.length] = treeObj.aNodes[curindex].title;
	}
	return treeTextArray;
}
/*清空树复选框和相应文本框*/
function clearTreeInput(inputId,checkName){
	var checkObjArray = getAllCheckObj(checkName);
	if(checkObjArray&&checkObjArray.length>0){
		for(var i=0;i<checkObjArray.length;i++){
			var checkObj = checkObjArray[i];
		    if(checkObj.checked){
		      checkObj.checked = false;
		      checkObj.onclick();
		    }
		}
	}
	jqueryGetEleById(inputId).val("");
}
/*得到所有复选框value*/
function getAllCheckVal(itemName){
	var result = [];
	var item = jqueryGetEleByName(itemName);
	if(null != item){
		for (var i = 0; i < item.length; i++) {
			result[result.length] = item[i].value;
		}
	}
	return result;
}
/*比较两个数组,第一个数组里的值是否在第二个基础数组里存在*/
function check2Array(arr,database){
    //alert(arr+"----"+database);
    var isGo = true;
    for(var i = 0;i < arr.length; i++){
    	var arrStr = arr[i];
    	var flag = false;
    	for(var j = 0;j < database.length; j++){
    		var databaseStr = database[j];
    		if(arrStr == databaseStr){
    			flag = true;
    		}
    	}
    	if(!flag){
    		return false;
    	}
    }
    return isGo;
}

/*比较两个数组,第一个数组里是否有值在第二个基础数组里存在*/
function checkTwoArray(arr,database){
    //alert(arr+"----"+database);
    var flag = false;
    for(var i = 0;i < arr.length; i++){
    	var arrStr = arr[i];
    	for(var j = 0;j < database.length; j++){
    		var databaseStr = database[j];
    		if(arrStr == databaseStr){
    			flag = true;
    			break;
    		}
    	}
    }
    return flag;
}

/*得到选中的单选框value*/
function getRadioCheckValue(itemName){
	var result;
	var item = jqueryGetEleByName(itemName);
	if(null != item){
		for (var i = 0; i < item.length; i++) {
			if(item[i].checked){
				result = item[i].value;
			}
		}
	}
	return result;
} 

/**
 * 功能：计算字符串长度
 *
 * 参数str：输入值
 * 返回值：字符串长度(英文(1位) + 汉字(2位))
 */
function strLen(str) {
	return str.replace(/[^\x00-\xff]/g, "**").length;
}
/**
 * 功能：得到parent的iframe对象
 *
 */
function getParentIframe(){
	var tabId = parent.getCurActiveTabId();
	var tabIframeId = "tabiframe" + tabId;
	return parent.frames[tabIframeId];
}
/**
 * 功能：退出重新登录
 *
 */
function relogin(){
    if(parent.logout){
       parent.logout();
    }else if(parent.parent.logout){
       parent.parent.logout();
    }else if(parent.parent.parent.logout){
       parent.parent.parent.logout();
    }else if(parent.parent.parent.parent.logout){
       parent.parent.parent.parent.logout();
    }
}


/**DWR loading 显示信息*/
var useLoadingMessage = function(message) {
  var loadingMessage;
  if (message) loadingMessage = message;
  else loadingMessage = "Loading";
  dwr.engine.setPreHook(function() {
    var disabledZone = dwr.util.byId('disabledZone');
    if (!disabledZone) {
      disabledZone = document.createElement('div');
      disabledZone.setAttribute('id', 'disabledZone');
      disabledZone.style.position = "absolute";
      disabledZone.style.zIndex = "1001";
      disabledZone.style.left = "-400px";
      disabledZone.style.top = "0px";
      disabledZone.style.width = "100%";
      disabledZone.style.height = "100%";
      document.body.appendChild(disabledZone);
      var messageZone = document.createElement('div');
      messageZone.setAttribute('id', 'messageZone');
      messageZone.style.position = "absolute";
      messageZone.style.top = "150px";
      messageZone.style.right = "0px";
      messageZone.style.background = "#CAD9EC";
      messageZone.style.color = "#445588";
      messageZone.innerHTML="<img src='/images/bulueloading.gif'/>  ";
      messageZone.style.fontSize = "14px";
      messageZone.style.border="3px solid #A3BAE9";
      messageZone.style.padding = "10px 10px 10px 10px";
      messageZone.style.overflow="hidden";
      messageZone.style.fontFamily = "Arial,Helvetica,sans-serif";
      mask.style.visibility='visible';
      disabledZone.appendChild(messageZone);
      var text = document.createTextNode(loadingMessage);
      messageZone.appendChild(text);
      dwr.util._disabledZoneUseCount = 1;
    }
    else {
      mask.style.visibility='visible';
      dwr.util.byId('messageZone').innerHTML = "<img src='/images/bulueloading.gif'/>  "+loadingMessage;
      disabledZone.style.visibility = 'visible';
      dwr.util._disabledZoneUseCount++;
    }
  });
  dwr.engine.setPostHook(function() {
    dwr.util._disabledZoneUseCount--;
    if (dwr.util._disabledZoneUseCount == 0) {
      mask.style.visibility='hidden';
      dwr.util.byId('disabledZone').style.visibility = 'hidden';
    }
  });

}
Ext.BLANK_IMAGE_URL ='/images/default/s.gif';

//查看帮助文档 anchor代表锚点
function shownmshelp(anchor){
	window.open("pages/help/help.jsp?anchor="+anchor,"帮助文档");
}
//分页函数开始
function setup_pageSize(){
var setup_pagesize_l = document.getElementById("query_page_size");
form_setup_pagesize(setup_pagesize_l.value);
}

function to_pageSub(sum_page){
var to_pagenumber = document.getElementById("to_pagenumber_obj");
if(to_pagenumber){
if(!isNaN(parseInt(to_pagenumber.value))){
var to_pagenumber_l = parseInt(to_pagenumber.value);
if(to_pagenumber_l>sum_page||to_pagenumber_l<1){
alert("请输入1到"+sum_page+"之间的数值!");
}else{
to_page(to_pagenumber_l);
}
}else{
alert("请输入1到"+sum_page+"之间的数值!");
}
}
}
//分页函数结束
/*加载层并执行js*/
function getHTMLJs(id,url,pars,meth){
	var updateajax =  new Ajax.Updater(
        {success: id},url,
        {asynchronous:true,method: meth, parameters: pars, onFailure: reportError,onSuccess:reportSuccess, evalScripts: true}
    );
}
function reportError(request){}
function reportSuccess(response){
	eval(response.responseText);
}

//层初始化方法
	//{divid:,w:,h:,x1:,y1:,x2:,y2:,isokbutton:,htmldivid:,loadurl:,divobj:,treename:,chkName:,inputid:}
	/*
	divid:外层id
	w:宽
	h:高
	isokbutton:是否有确定按钮
	htmldivid:内层id
	loadurl:内容加载url,如果不需要内容加载则可以不写
	divobj:填定null
	treename:树名称
	chkName:复选框名称
	inputid:输入框

	*/
	function initQueryDiv(divArray){
		this.divArray = divArray;
	   for(var i=0;i<divArray.length;i++){
		   var divIndex_l = divArray[i];
	     if(divIndex_l.isokbutton){
		   divIndex_l.okEvent = getDivOkEvent(divIndex_l.inputid,divIndex_l.treename,divIndex_l.chkName,divIndex_l);
		   divIndex_l.divobj = extHandleDivWin("",divIndex_l.w,divIndex_l.h,divIndex_l.x1,divIndex_l.y1,divIndex_l.divid,divIndex_l.htmldivid,false,[{text:'确定',handler:divIndex_l.okEvent}],false);
		 }else{
		   divIndex_l.divobj = extDivWin("",divIndex_l.w,divIndex_l.h,divIndex_l.x1,divIndex_l.y1,divIndex_l.divid,divIndex_l.htmldivid,false);
		 }
		 
	   }
	}
    //层显示
	initQueryDiv.prototype.show= function(index){
	  this.closeall();
	  var divIndex_l = this.divArray[index];
      if(divIndex_l&&divIndex_l.loadurl){
	     if(document.getElementById(divIndex_l.htmldivid).innerHTML==""){
			jqueryGetEleById(divIndex_l.htmldivid).html("<img src='/images/loading.gif'/>正在加载中...");
	  		getHTMLJs("",divIndex_l.loadurl,"","post");
		 }
	  }
      jqueryGetEleById(divIndex_l.htmldivid).show();
      divIndex_l.divobj.show();
	};
    //所有层隐藏
    initQueryDiv.prototype.closeall = function(){
		var divArray = this.divArray;
        for(var i=0;i<divArray.length;i++){
		  if(divArray[i]){
		    if(divArray[i].isokbutton){
			   divArray[i].divobj.hide();
               divArray[i].okEvent();
			}else{
			  divArray[i].divobj.hide();
			}
		  }
		}
	};
	//得到层对象
    initQueryDiv.prototype.getDivObj = function(index){
		var divArray = this.divArray;
		if(divArray[index]){
		  return divArray[index].divobj;
		}
        return null;
	};
	//清除内容
	initQueryDiv.prototype.clear = function(index){
		var divArray = this.divArray;
		if(divArray[index]){
		  clearTreeInput(divArray[index].inputid,divArray[index].chkName);
		}
	};
    //层事件公用方法
    function getDivOkEvent(inputid,treename,chkName,QueryDivIndex){
		var okEvent = function(){
		   jqueryGetEleById(inputid).val(getTreeText(treename,chkName));
		   QueryDivIndex.divobj.hide();
		};
		return okEvent;
	}
	//修改loadurl
	initQueryDiv.prototype.editLoadurl = function(index,loadurl){
		var divIndex_l = this.divArray[index];
		divIndex_l.loadurl = loadurl;
	};
	
	
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
									url : 'jfchildsys.action',
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
					return String.format('<img src="/images/server.gif"></img><a href="{1}" target="_parent">{0}<a>',value, url);
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
	
	
	