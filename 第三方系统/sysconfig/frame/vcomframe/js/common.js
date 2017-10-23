//checkBox,全选或全不选
function checkall(ischeck,form) {
	if (form.checkboxId != null) {
	    var count = form.checkboxId.length;
	    if (count == null) {
	    	if(!form.checkboxId.disabled)
	        	form.checkboxId.checked = ischeck;
	    } else {
	        for (i = 0; i < count; i++) {
	            if (form.checkboxId[i].disabled == true) {
	               form.checkboxId[i].checked = false;
	            } else {
	               form.checkboxId[i].checked = ischeck;
	            }
	        }//end of for
	    }
	}
        
}
//当全选后，如果取消列表中其中一个，咋取消全选选中。
function uncheck(obj,form){
	if(!obj.checked)form.all.checked=false;
	else{
		var count = form.checkboxId.length;
	    if (count == null) {
	        form.all.checked = true;
	    } else {
	    	var type=0;
	        for (i = 0; i < count; i++) {
	            if (form.checkboxId[i].disabled == true) {
	            } else {
	               if(!form.checkboxId[i].checked)type=1;
	            }
	        }//end of for
	        if(type==0)form.all.checked = true;
	    }
	}
}
/*得到选中的radio值*/
function getradiovalue(objField){
	var idvalue="";
	if(typeof(objField)!="undefined"){
		if(typeof(objField.length)=="undefined"){
			if(radio.checked==true)idvalue=radio.value;
		}else{
			for(var i=0;i<objField.length;i++){
				if(objField[i].checked==true){
					idvalue=objField[i].value;
					break;
				}
			}
		}
	}
	return idvalue;
}

/*得到选中的CheckBox值,返回的数据以,分割*/
function getCheckBoxvalue(objField){
	var idvalue="";
	if(typeof(objField)!="undefined"){
		if(typeof(objField.length)=="undefined"){
			if(objField.checked==true)idvalue=objField.value;
		}else{
			for(var i=0;i<objField.length;i++){
				if(objField[i].checked==true){
					if(idvalue=="")
						idvalue=objField[i].value;
					else
						idvalue=idvalue+","+objField[i].value;
				}
			}
		}
	}
	return idvalue;
}
/*得到CheckBox是否选中了多个，返回选中的checkbox的个数*/
function getCheckBoxSelectNumber(objField){
	var idvalue=0;
	if(typeof(objField)!="undefined"){
		if(typeof(objField.length)=="undefined"){
			if(objField.checked==true)idvalue=1;
		}else{
			for(var i=0;i<objField.length;i++){
				if(objField[i].checked==true){
						idvalue=idvalue+1;
				}
			}
		}
	}
	return idvalue;
}
//格式化数字大小
function formatsize(size){
	if(size/(1024*1024*1024)>=1){
		var num=size/(1024*1024*1024)+"";
		if(num.lastIndexOf(".")>=0&&(num.lastIndexOf(".")+4)<num.length)
			return num.substring(0,num.lastIndexOf(".")+4)+"G"
		else
			return num+"G";
	}else if(size/(1024*1024)>=1){
		var num=size/(1024*1024)+"";
		if(num.lastIndexOf(".")>=0&&(num.lastIndexOf(".")+4)<num.length)
			return num.substring(0,num.lastIndexOf(".")+4)+" M"
		else
			return num+"M";
	}else{
		var num=size/(1024)+"";
		if(num.lastIndexOf(".")>=0&&(num.lastIndexOf(".")+4)<num.length)
			return num.substring(0,num.lastIndexOf(".")+4)+" K"
		else
			return num+"K";
	}
}
//格式化时间
function formattime(time){
	var back="";
	if(time/(60*60)>=1){
		var t=parseInt(time/(60*60));
		if(t<10)
			back="0"+t+":";
		else
			back=t+":";
	}else{
		back="00:";
	}
	if(time%(60*60)>=1){
		var t=parseInt((time%(60*60))/60);
		if(t<10)
			back=back+"0"+t+":";
		else
			back=back+t+":";
	}else{
		back=back+"00:";
	}

	if(time%(60*60)%60>=1){
		var t=parseInt((time%(60*60))%60);
		if(t<10)
			back=back+"0"+t;
		else
			back=back+t;
	}else{
		back=back+"00";
	}
	return back;
}
function changecolor(obj,type){
			if(type==1)
				obj.style.backgroundColor ="#F0F0F0" 
			else{
				if(obj.rowIndex%2==0)
					obj.style.backgroundColor ="#FAFAFA" ;
				else
					obj.style.backgroundColor ="#ffffff" 
			}
				
}
document.onreadystatechange=function (){
		top.____type=0;
		var inputs=document.getElementsByTagName("input");
		for(var i=0;i<inputs.length;i++){
			if(inputs[i].type=="text"){
				inputs[i].attachEvent("onblur",testsss,true);
			}
		}
	}
	function testsss(obj){
		var ele = obj.srcElement;
        if ((ele.type == 'text' || ele.type == 'textarea')&&((ele.name != 'beginTime') && (ele.name != 'endTime'))) {
            var eleValue = ele.value;
            var array = "[&|<|>|%|#|~ ,']";
            eleValue = eleValue.replace(new RegExp(array, "gm"),"");
            ele.value = eleValue;
        }
	}
	if(top.oncontextmenu=="1")
		document.oncontextmenu =function(){event.cancelBubble = true; event.returnValue = false; return false;} ;