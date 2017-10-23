
//---------------------------------弹窗类-----------------------------------
function popup(popId,shadowid,bgid){//
    this.popWindow=document.getElementById(popId);
    this.bgcolor="#000";
    this.alpha=40;   
	this.mybg=null; 
	this.mypopwin=null; 
	this.shadowid= shadowid;
	this.bgid = bgid;
}

//创建弹窗
popup.prototype.create=function(){    
    //if(!this.mybg){	
	//创建遮盖层	
	var h = window.parent.document.body.clientHeight;
	var h1 = window.parent.document.documentElement.clientHeight;
	h > h1 ? h = h : h = h1;
	mybg = window.parent.document.createElement("div");	
	mypopwin = window.parent.document.createElement("div");	
	mypopwin.innerHTML = this.popWindow.innerHTML;
	mypopwin.style.zIndex = 2000;	
	mypopwin.style.position = "absolute";
	mypopwin.style.top = (screen.height-480)/2; ;
	mypopwin.style.left =(screen.width-480)/2;;
	mypopwin.id=this.shadowid;
	//mypopwin.style.marginTop = -(mypopwin.clientHeight/2)+"px";
	//mypopwin.style.marginLeft = -(h/2)+"px";	
	mypopwin.style.display = "block";
	
	
	mybg.style.background = this.bgcolor;
	mybg.style.width = "100%";
	mybg.style.height = h + "px";
	mybg.style.position = "absolute";
	mybg.style.top = "0";
	mybg.style.left = "0";
	mybg.style.zIndex = "500";
	mybg.style.opacity = (this.alpha/100);
	mybg.style.filter = "Alpha(opacity="+this.alpha+")";
	mybg.id=this.bgid;
	window.parent.document.body.appendChild(mybg);	
	window.parent.document.body.appendChild(mypopwin);	
	this.mybg=mybg;
	this.mypopwin=mypopwin;
	//document.body.style.overflow = "hidden";	
	//}
	
	this.mybg.style.display="block";
	this.mypopwin.style.display = "block";
	//针对ie6.0隐藏select
	if(navigator.userAgent.indexOf("MSIE 6.0")>0){
	window.parent.document.body.style.height="100%";//针对ie6.0不定义body高度遮盖层高度无效
	this.hideSelects();
	}	
	this.hideObject();
		
	ev.addEvent(window.parent,"resize",this.changSize);
}


//弹窗拖动
popup.prototype.drag=function(handId){//
    jQuery(handId).onselectstart=function(){return false};
    var s_left=0;
    var s_top=0;
	var popup=this.popWindow;
    ev.addEvent(jQuery(handId),"mousedown",mDown);
	//按下时
	function mDown(){
	    var evn=ev.getEvent();        
        //拖动修正值
        f_left=evn.clientX-popup.offsetLeft;
        f_top=evn.clientY-popup.offsetTop;       
        s_left=evn.clientX-f_left+"px";
        s_top=evn.clientY-f_top+"px";	
		ev.addEvent(document,"mousemove",mMove);
		ev.addEvent(document,"mouseup",mUp);
	}
	//拖动时
	function mMove(){
        var evn=ev.getEvent();        
        popup.style.left=evn.clientX-f_left+(popup.clientWidth/2)+"px";
        popup.style.top=evn.clientY-f_top+(popup.clientHeight/2)+"px";
   }
   //放下时
   function mUp(){
        ev.removeEvent(document,"mousemove",mMove);
        ev.removeEvent(document,"mouseup",mUp);
   }
}


//针对ie6.0显示select
popup.prototype.showSelects=function(){
   var elements = document.getElementsByTagName("select");
   for (i=0;i< elements.length;i++){
      elements[i].style.visibility='visible';
   }
}

//针对ie6.0隐藏select
popup.prototype.hideSelects=function(){
   var elements = document.getElementsByTagName("select");
   for (i=0;i< elements.length;i++){
   elements[i].style.visibility='hidden';
   }
}

//关闭弹窗
popup.prototype.closePopup=function(){    
    this.mypopwin.style.display="none";	
	this.mybg.style.display="none";			
	//针对ie6.0显示select
   	if(navigator.userAgent.indexOf("MSIE 6.0")>0){	
	this.showSelects();
	}
	this.showObject();
}

//显示弹窗
popup.prototype.openPopupAndBg=function(){    
    this.popWindow.style.display="";	
	this.mybg.style.display="";			
	//针对ie6.0显示select
   	if(navigator.userAgent.indexOf("MSIE 6.0")>0){	
	this.hideSelects();
	}
}

//显示弹窗
popup.prototype.openPopup=function(){    
    this.popWindow.style.display="";	
	this.mybg.style.display="none";			
	//针对ie6.0显示select
   	if(navigator.userAgent.indexOf("MSIE 6.0")>0){	
	this.hideSelects();
	}
	this.hideObject();
}
//隐藏OBJECT
popup.prototype.hideObject=function(){
   var elements = document.getElementsByTagName("object");
   for (i=0;i< elements.length;i++){
   elements[i].style.visibility='hidden';
   }
};

//显示OBJECT
popup.prototype.showObject=function(){
   var elements = document.getElementsByTagName("object");
   for (i=0;i< elements.length;i++){
   elements[i].style.visibility='visible';
   }
}
popup.prototype.changSize = function(){
	if(this.mybg.style.display != "none"){		
		var h = document.body.clientHeight;
		var h1 = document.documentElement.clientHeight;
		h > h1 ? h = h : h = h1;
		this.mybg.style.height = h + "px";
	}
};
//-----------------------event---------------------------
var ev={
          //添加事件监听
          addEvent:function(obj,evt,fun){
              if(obj.addEventListener){//for dom
                    obj.addEventListener(evt,fun,false)
              }
              else if(obj.attachEvent){//for ie
			         obj.attachEvent("on"+evt,fun)
                    //obj.attachEvent("on"+evt,function(){fun.call(obj)});//解决IE attachEvent this指向window的问题
			  }
              else{obj["on"+evt] = fun}//for other
          },
		  
          //删除事件监听
          removeEvent:function(obj,evt,fun){
              if(obj.removeEventListener){//for dom
                    obj.removeEventListener(evt,fun,false)
              }
              else if(obj.detachEvent){//for ie
                    obj.detachEvent("on"+evt,fun)
              }
              else{obj["on"+evt] = null;
			  } //for other
           },
	
          //捕获事件		
           getEvent:function(){
                    if(window.event){return window.event}
                    else{return ev.getEvent.caller.arguments[0];}	
           },
		   
		   formatEvent:function(evt){
                    evt.eTarget = evt.target ? evt.target:evt.srcElement;//事件目标对象
                    evt.eX = evt.pagex ? evt.pagex:evt.clientX + document.body.scrollLeft;//页面鼠标X坐标
                    evt.eY = evt.pagey ? evt.pagex:evt.clientY + document.body.scrollTop;//页面鼠标Y坐标
                    evt.eStopDefault = function(){this.preventDefault ? this.preventDefault():this.returnValue = false;}//取消默认动作
                    evt.eStopBubble = function(){this.stopPropagation ? this.stopPropagation():this.cancelBubble = true;}//取消冒泡
           }
}

