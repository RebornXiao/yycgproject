
/*
* 依赖Ext JS Library 2.1
* add：刘志强<liuzhiqiang@zzvcom.com>
* 支持的编码utf-8
*
*
* 功能点有： 
* 一、模拟windows打开窗口效果 
* 二、参数说明
*	@layoutid容器id(必选)
*		contenid内容id(必选)
*		title标题(必选)
*		width宽度(必选)数字或者null
*		heigth高度(必选)数字或者null
*		confirmfunction确定按钮调用的方法(必选)方法或则null
*		cancelfunction取消按钮调用的方法(必选)方法或则null
*		showbutton是否显示底部按钮(必选)true、false、null
*		buttons自定义按钮(可选)var button=[{text:'确定',handler:null},{text:'取消',handler:null}]
* 三、exemple
*		var openwindows=new openwindow('hello-win','content','添加文章信息',700,300,toSaveOrUpdateArticle,null,true);
*		function toshow(){//按钮上添加该东西显示该窗口
*    		openwindows.show();
*    	}
*    	function tohide(){//按钮上添加该东西隐藏该窗口
*    		openwindows.hide();
*    	}
*/

function openwindow(layoutid,contenid,title,width,heigth,confirmfunction,cancelfunction,showbutton,buttons,type){
	this.win=null;
	this.buttons=buttons;
	this.contenid=contenid;
	this.ttt=null;
	this.type=type
	this.width=width;
	this.heigth=heigth;
	var _win=this;
	Ext.onReady(function(){
    //var button = Ext.get(buttionid);
    //button.on('click', function(){
        if(!_win.win){
            _win.win = new Ext.Window({
                el:layoutid,
                layout:'fit',
                width:_win.width||500,
                height:_win.heigth||300,
                title:title,
                draggable:true,
                stateful:false,
				stateId:null,
                modal:true,
                hideParent:true,
                plain: true,
                items: new Ext.BoxComponent({
				region:contenid,
				el: contenid
			}),

                buttons: !showbutton||_win.buttons||[{
                    text:vcomframe.openwindow_yes,
                    handler: function(){
                    	try{
                    		confirmfunction();
                    	}catch(e){
                    		err("当前功能不可用");
                    	}
                    }
                },{
                    text: vcomframe.openwindow_no,
                    handler: function(){
                        if(typeof(cancelfunction)=="function")cancelfunction();
                        else {
                        	_win.win.hide();
                        	if(!_win.type){
	                        	var iframe=document.getElementById(_win.contenid).getElementsByTagName('iframe');
	                        	if(iframe[0]){
	                        		iframe[0].src=""
	                        		iframe[0].contentWindow.document.write('');
	                        		};
	                        }
                        }
                    }
                }]
            });
	         _win.win.on('beforeclose',function(){
	             if(typeof(cancelfunction)=="function")cancelfunction();
	             else {
	                   _win.win.hide();
	                   if(!_win.type){
		                  var iframe=document.getElementById(_win.contenid).getElementsByTagName('iframe');
		                  if(iframe[0]){
		                  	iframe[0].contentWindow.document.write('');
		                  	iframe[0].src="";
		                  }
		               }
	              }
	       });
        }
        //_win.win.show(event.srcElement);
   // });
    
});
//隐藏当前层
openwindow.prototype.hide=function(){
	this.win.hide(this.ttt);
	if(!this.type){
	       var iframe=document.getElementById(this.contenid).getElementsByTagName('iframe');
	       if(iframe[0]){
	           	iframe[0].contentWindow.document.write('');
	           	iframe[0].src="";
           }
	}
}
//显示层
openwindow.prototype.show=function(obj,url){
	var _this=this;
	if(Ext.MessageBox.isVisible()){
		window.setTimeout(test,500);
	}else {
		test();
	}
	function test(){
		if(obj){_this.win.show(obj);_this.ttt=obj}
		else _this.win.show(event.srcElement);
		if(url!=null){
			try{
				var iframe=document.getElementById(_this.contenid).getElementsByTagName('iframe');
				if(iframe[0]){
		         	var ifwindow=iframe[0].contentWindow;
						ifwindow.document.open();
						ifwindow.document.write("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"vcomframe/css/main.css\"/></head><div id=\"loading-mask\"></div><div id=\"loading\"><div class=\"loading-indicator\"><img src=\"vcomframe/images/extanim32.gif\" width=\"32\" height=\"32\"style=\"margin-right:8px;\" align=\"absmiddle\" />页面加载中，请稍后...</div></div>");
						ifwindow.document.close();
		         }
			}catch(e){}
			if(_this.win.isVisible()){
				locations();
			}else{
				setTimeout(locations,500);
			}
		}
		
	}
	function locations(){
		var iframe=document.getElementById(_this.contenid).getElementsByTagName('iframe');
         if(iframe[0]){
         	iframe[0].contentWindow.location=url;
         }
	}
}
//设置标题
openwindow.prototype.setTitle=function(title){
	this.win.setTitle(title);
}
}