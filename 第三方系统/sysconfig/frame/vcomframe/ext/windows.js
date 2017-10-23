/*
 * Copyright(c) 2008, Vcom
 * add：刘志强<liuzhiqiang@zzvcom.com>
 * 说明：
 *	1、本函数依赖Ext JS Library 2.1
 *  2、支持的编码为utf-8
 */
  //取得元素
var $ = function(id) {
    return document.getElementById(id);
}
var vcomframe={
		path:top.path,
		allpath:'',
		sigpath:'',
		timeout:30000,
		//combobox
		manange_alert:'警告',
		manange_message:'打开的页面超过了限制，请关闭不需要的页面!',
		combobox_emptyText:'\u8bf7\u9009\u62e9',
		activecombobox_emptyText:'请输入',
		treecombobox_emptyText:'\u8bf7\u9009\u62e9',
		//openwindow
		openwindow_yes:'确定',
		openwindow_no:'取消'
}
var ____object=[];
window.onresize =function(){
	for(var i=0;i<____object.length;i++){
		if(____object[i].types=="ComboBoxs"){
			____object[i].setWidth(document.getElementById(____object[i].hiddenName).parentElement.offsetWidth-30);
		}else if(____object[i].grids.types=="grids"){
			____object[i].grids.setWidth(document.body.offsetWidth-4);
		}
	}
}
/*
* 功能点有： 
* 一、添加出错样式js调用 
* 二、obj为对象的id或者是name
*    <input type="text" name="title" id="title1"> 
*		则obj可以为
*			document.all.title、
*			document.all.title1、
*			document.getElementById("title1")、
*			document.getElementsByName("title")
* 三、exemple
		errstyle(objField);
*/
function errstyle(obj){
	Ext.onReady(function(){
		Ext.get(obj).addClass("form-invalid");
		var index=0;
		var select=0;
		var childnote=Ext.get(obj).dom.parentElement;
		while(childnote.tagName!="TD"){
			childnote=childnote.parentElement;
		}
		childnote.className="errstyle";
		//Ext.get(obj).dom.parentElement.childNodes[1]="<img id='__errpic' src='"+vcomframe.path+"/etc/images/drop-no.gif' class='tdpic'>"
		//Ext.get(obj).dom.outerHTML=Ext.get(obj).dom.outerHTML+"<img id='__errpic' src='"+vcomframe.path+"/etc/images/drop-no.gif' class='tdpic'>";
	});
	
}
function qtip(obj,text,type){
	if(type==0){
		Ext.onReady(function(){
			Ext.get(obj).dom.qtip=text;
			Ext.get(obj).dom.qclass="x-form-invalid-tip";
		});
	}else{
		Ext.onReady(function(){
			Ext.get(obj).dom.qtip="";
			Ext.get(obj).dom.qclass="";
		});
	}
}
/*
* 功能点有： 
* 一、取消出错样式js调用 
* 二、obj为对象的id或者是name
*	<input type="text" name="title" id="title1"> 
*		则obj可以为
*			document.all.title、
*			document.all.title1、
*			document.getElementById("title1")、
*			document.getElementsByName("title")
* 三、exemple
		cancelerrstyle(objField);
*/
function cancelerrstyle(obj){
	Ext.onReady(function(){
		Ext.get(obj).removeClass("form-invalid");
		var childnote=Ext.get(obj).dom.parentElement;
		while(childnote.tagName!="TD"){
			childnote=childnote.parentElement;
		}
		childnote.className="successstyle";
		//if(Ext.get(obj).dom.parentElement.getElementsByTagName("img")[0])
		//Ext.get(obj).dom.parentElement.getElementsByTagName("img")[0].outerHTML="<img id='__errpic' src='"+vcomframe.path+"/etc/images/drop-yes.gif' class='tdpic'>";
	});
}
/*
* 功能点有： 
* 一、重新设定表单值，还原到原始状态 
* 二、obj为对象的id或者是name
*	<form name="myform">
*		则obj可以为
*			myform
* 三、exemple
		reset(myform);
*/
function reset(obj){
	obj.reset();
	var img=document.all.__errpic;
	if(img){
		if(!img.length){
			img.outerHTML="";
		}else{
			for(var i=0;i<img.length;i++){
				img[i].src="vcomframe/images/s.gif";
			}
		}
	}
}
/*
* 功能点有： 
* 一、模拟确认对话框 
* 二、value为弹出的确认框 提示信息。example:"请确认是否要删除选中的元素,此操纵不可以恢复!"
* 三、functions为点击弹出的确认框中yes【确定】执行的方法
* 四、example
*		confirm("你确定要删除选择的信息吗？此操作不可恢复。",conformback);
*    	function conformback(btn){
*    		if(btn=="yes"){
*    			//code here
*    		}
*    	)
* 
*/
function confirm(value,functions){
	if(typeof(functions)!="function"){functions=function(btn){};}
	Ext.onReady(function(){
		if(Ext.MessageBox.isVisible()){
			window.setTimeout(test,500);
		}else {
			test();
		}
		function test(){
			Ext.MessageBox.confirm('确定', value,functions);
		}
	});
}
function iframelocation(iframe,url)
	{window.setTimeout(A,1000);function A(){iframe.location=url}
}
/*
* 功能点有： 
* 一、模拟出错对话框 
* 二、value为弹出的确认框 提示信息。example:"根据你的需求，标题不能为空，修改吧老大!"
* 三、functions为点击弹出的框中yes【确定】执行的方法
* 四、example
*		err("你确定要删除选择的信息吗？此操作不可恢复。",conformback);
*    	function conformback(){
*    			//code here
*    	)
* 
*/
function err(value,filed,functions){
	Ext.onReady(function(){
		if(Ext.MessageBox.isVisible()){
			window.setTimeout(test,500);
		}else {
			test();
		}
		function test(){
			if(typeof(functions)!="function"){functions=function(btn){if(filed)Ext.get(filed).focus();};}
			Ext.MessageBox.show({
	           title: '出错了',
	           msg: value,
	           buttons: Ext.MessageBox.OK,
	           animEl: 'mb9',
	           fn: functions,
	           icon: Ext.MessageBox.WARNING
	       });
		}
	});
}
/*
* 功能点有： 
* 一、模拟成功对话框 
* 二、value为弹出的确认框 提示信息。example:"保存文章成功，恭喜你!"
* 三、functions为点击弹出的框中yes【确定】执行的方法
* 四、example
*		success("保存文章成功，恭喜你!",conformback);
*    	function conformback(){
*    			//code here
*    	)
* 
*/
function success(value,functions){
	if(typeof(functions)!="function"){functions=function(btn){};}
	Ext.onReady(function(){
		 if(Ext.MessageBox.isVisible()){
				window.setTimeout(test,500);
		 }else {
				test();
		 }
		 function test(){
			 	Ext.MessageBox.show({
				title: '成功信息',
				msg: value,
				buttons: Ext.MessageBox.OK,
				fn: functions,
				icon: Ext.MessageBox.INFO
			 });
		 }
	});
}
function tolocation(url,v){
	if(url.indexOf("?")>=0){
		url=url+"&mid="+v.mid;
	}else{
		url=url+"?mid="+v.mid;
	}
	window.location.href=url;
}
/*
* 功能点有： 
* 一、级联菜单实现 
* 二、obj为级联的this对象，name为被级联的name名称,json被级联的集合。
* 三、json格式如下，如果需要对级联的对象进行分组可以设定'-#分组一'，分组一为分组的名称
*	var json=[	['-#分组一',{value:'漯河',text:'漯河'},{value:'南阳',text:'南阳'},'-#分组二',{value:'信阳',text:'信阳'},{value:'漯河',text:'漯河'},'-#分组三',{value:'南阳',text:'南阳'}],
*				[{value:'齐齐哈尔',text:'齐齐哈尔'},{value:'牡丹江',text:'牡丹江'},{value:'大庆',text:'大庆'}],
*				[{value:'延边',text:'延边'},{value:'吉林',text:'吉林'},{value:'白山',text:'白山'}],
*				[{value:'大连',text:'大连'},{value:'葫芦岛',text:'葫芦岛'},{value:'盘锦',text:'盘锦'}],
*				[{value:'唐山',text:'唐山'},{value:'张家口',text:'张家口'},{value:'廊坊',text:'廊坊'}]
*				];
* 四、example
*	var json=[	['-#分组一',{value:'漯河',text:'漯河'},{value:'南阳',text:'南阳'},'-#分组二',{value:'信阳',text:'信阳'},{value:'漯河',text:'漯河'},'-#分组三',{value:'南阳',text:'南阳'}],
*				[{value:'齐齐哈尔',text:'齐齐哈尔'},{value:'牡丹江',text:'牡丹江'},{value:'大庆',text:'大庆'}],
*				[{value:'延边',text:'延边'},{value:'吉林',text:'吉林'},{value:'白山',text:'白山'}],
*				[{value:'大连',text:'大连'},{value:'葫芦岛',text:'葫芦岛'},{value:'盘锦',text:'盘锦'}],
*				[{value:'唐山',text:'唐山'},{value:'张家口',text:'张家口'},{value:'廊坊',text:'廊坊'}]
*				];
*		changeselect(this,json,"city");
* 五 、obj第一级option需要设定为<option value="">---请选择---</option>，
*/
function changeselect(obj,json,name){
		var names=eval("document.all."+name);
		var index=obj.selectedIndex;
			names.options.length=0;
		var lengths=names.children.length;
			while(lengths>0){
				names.removeChild(names.children(lengths-1));
				lengths--;
			}
		if(index==0){names.options[0]=new Option("---请选择---","");return;};
		if(json.length>index){
			var j=0;
			for(var i=0;i<json[index-1].length;i++){
				if(json[index-1][i].value)
					names.options[j++]=new Option(json[index-1][i].text,json[index-1][i].value);
				else {
					var arr=json[index-1][i].split("#");
					var nies = document.createElement("OPTGROUP");
						nies.style.background="#808080";
 						nies.label = arr[1];
 						names.appendChild(nies);
				}
			}
		}
	}
var _______shows;
var _______timer;
function ProgressBegin(){
	Ext.onReady(function(){
		_______shows=Ext.MessageBox.show({
				           title: '请等待',
				           msg: 'Loading ...',
				           progressText: 'Initializing...',
				           width:300,
				           maxWidth:300,
				           progress:true,
				           closable:false,
				           animEl: '__mb6'
					       });
					       _______shows.getDialog().stateful=false
							var v=1;
					       // this hideous block creates the bogus progress
					       function fffff(){
					       		v++;
					            var i = v/11;
					            var progress=Math.round(100*i);
					               if(i<1){
					                Ext.MessageBox.updateProgress(i, Math.round(100*i)+'% completed');
					                }else{
										window.clearInterval(_______timer);				                
					                }
					       };
					       fffff();
					       _______timer = window.setInterval(fffff, 500);
	})
}
function ProgressEnd(){
	Ext.onReady(function(){
	Ext.MessageBox.updateProgress(10, 100+'% completed');
		    		window.clearInterval(_______timer);
		    		window.setTimeout(____tohide,500);
		    		function ____tohide(){
		    			_______shows.hide();
		    		}
	});
}
/*
* 功能点有： 
* 一、ext ajax组件，
* 二、扩展的功能点实现请求的时间加载百分比滚动条
* 三、pars为设定的参数格式如下
*	{
*		url : 'addarticle.action',
*		success : function(response) {
*		        	//code here
*		        },
*		failure : function() {
*		           //code here
*		        },
*		headers: {
*			       'my-header': 'foo'
*			    },
*		method:'post',
*		timeout : 30000,
*		params : {
*				   title:myform.title1.value,
*				   comfrom:myform.comfroms.value
*				 }
*	}
* 四、example
*		ajax({
*			url : 'addarticle.action',
*			success : function(response) {
*		        	//code here
*		        },
*			failure : function() {
*		           //code here
*		        },
*			headers: {
*			       'my-header': 'foo'
*			    },
*			method:'post',
*			timeout : 30000,
*			params : {
*				   title:myform.title1.value,
*				   comfrom:myform.comfroms.value
*				 }
*		})
* 
*/
function ajax(pars,Progress){
	var timer;
	var shows;
	if(Progress==null)Progress=true;
	Ext.onReady(function(){
			if(Progress){
				shows=Ext.MessageBox.show({
				           title: '请等待',
				           msg: 'Loading ...',
				           progressText: 'Initializing...',
				           width:300,
				           maxWidth:300,
				           progress:true,
				           closable:false,
				           animEl: '_mb6'
					       });
		      shows.getDialog().stateful=false;
							var v=1;
					       // this hideous block creates the bogus progress
					       function fffff(){
					       		v++;
					            var i = v/11;
					            var progress=Math.round(100*i);
					               if(i<1){
					                Ext.MessageBox.updateProgress(i, Math.round(100*i)+'% completed');
					                }else{
										window.clearInterval(timer);				                
					                }
					       };
					       fffff();
					       timer = window.setInterval(fffff, 500);
			}
		    Ext.Ajax.request(pars);
		    if(Progress){
		    	Ext.Ajax.on('requestcomplete', function(){
		    		Ext.MessageBox.updateProgress(10, 100+'% completed');
		    		window.clearInterval(timer);
		    		window.setTimeout(tohide,500);
		    		function tohide(){
		    			shows.hide();
		    		}
		    		
		    	});
		    }
	    })
}
/*
* 功能点有： 
* 一、页面加载信息条 
* 二、example 在页面body内部加入如下代码即可
*		<div id="loading-mask"></div>
		<div id="loading">
			<div class="loading-indicator">
				<img src="etc/images/extanim32.gif" width="32" height="32"
					style="margin-right:8px;" align="absmiddle" />
				页面加载中，请稍后...
			</div>
		</div>
* 
*/
document.onreadystatechange=function (){
	var cap=document.getElementsByTagName("caption");
	for(var i=0;i<cap.length;i++){
		cap[i].innerHTML="<div style=\"float:left\">"+cap[i].innerHTML+"</div><div class=\"cap\"><img src=\"vcomframe/images/panel-1.gif\" onclick=\"___test(this)\"></div>"
	}
	if(document.getElementById("loading")){
		setTimeout(function(){
	        Ext.get('loading').remove();
	        Ext.get('loading-mask').remove();
	    }, 250);
	}
	top.____type=0;
}

function ___test(obj){
		if(obj.src.indexOf("panel-1.gif")>0){
			obj.src="vcomframe/images/panel-2.gif"
		}else
			obj.src="vcomframe/images/panel-1.gif";
		var trel=obj.parentElement.parentElement.parentElement.getElementsByTagName("tr");
		for(var i=0;i<trel.length;i++){
			if(trel[i].style.display=="none"){
				trel[i].style.display=""
				var divs=trel[i].getElementsByTagName("div");
				for(var j=0;j<divs.length;j++){
					divs[j].style.display="";
				}
			}else {
				trel[i].style.display="none";
				var divs=trel[i].getElementsByTagName("div");
		
				for(var j=0;j<divs.length;j++){
					divs[j].style.display="none";
				}
			};
		}
		for(var i=0;i<____object.length;i++){
			try{
				if(____object[i].grids.types=="grids"){
					//____object[i].grids.setWidth(document.body.offsetWidth-4);
					____object[i].grids.setHeight(document.body.offsetHeight-Ext.get(____object[i].id).getTop()-2);
				}
			}catch(e){
			}
		}
	}
	/*清除style*/
	function cleantdstyle(){
    	var tdlist=document.getElementsByTagName("td");
    	for(var i=0;i<tdlist.length;i++){
    		if(tdlist[i].className=="successstyle"||tdlist[i].className=="errstyle")tdlist[i].className="";
    	}
    }
	Ext.BLANK_IMAGE_URL ='vcomframe/images/s.gif';
	Ext.onReady(function(){
		Ext.QuickTips.init();   
	});