/*
* 依赖Ext JS Library 2.1,windows.js
* add：刘志强<liuzhiqiang@zzvcom.com>
* 支持的编码utf-8
*
* 功能点有： 
* 一、页面表单校验出错样式和提示 
* 二、objField为对象的id或者是name,strText为出错提示信息
*	 <input type="text" name="title" id="title1"> 
*		则objField可以为
*			document.all.title、
*			document.all.title1、
*			document.getElementById("title1")、
*			document.getElementsByName("title")
* 三、exemple
*		if(!CheckNotNull(myform.title1,"根据你的需求，标题不能为空，修改吧老大!")){return false;};
*/
//对js中startWith和endWith的扩展 
String.prototype.endWith=function(str){
    
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str)
	  return true;
	else
	  return false;
	return true;
}

String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;
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
function CheckCheckBoxSelect(objField){
	var idvalue=0;
	if(typeof(objField)!="undefined"){
		if(typeof(objField.length)=="undefined"){
			if(objField.checked==true)idvalue=1;
		}else{
			for(var i=0;i<objField.length;i++){
				if(objField[i].checked==true){
						idvalue=idvalue++;
				}
			}
		}
	}
	return idvalue;
}
//检查必填项,建议提示信息的长度大于15个汉字。
function CheckNotNull(objField, strText) {
  if (Trim(objField.value) == "") {
	    errstyle(objField);
	    qtip(objField, strText,0);
	   	//err(strText,objField);
	    return false;
  }else{
  	    cancelerrstyle(objField)
  	    qtip(objField, strText,1);
  }
  return true;
}

//针对模拟select检查必填项,建议提示信息的长度大于15个汉字。
function CheckSelectNotNull(objField, strText) {
  if ((objField.getValue()+"") == "") {
	    errstyle(objField.getComboBox().id);
	   	qtip(objField, strText,0);
	    return false;
  }else{
  	    cancelerrstyle(objField.getComboBox().id);
  	    qtip(objField, strText,1);
  }
  return true;
}
//检查EMail格式
function CheckEmail(objField, strText){
  var objValue = Trim(objField.value);
  if(objValue=="") return true;
  var format=/^(\S)+[@]{1}(\S)+\.{1}[^\.](\w)+$/;
  if(!format.test(objValue)){
	  	errstyle(objField);
	   qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  return true;
}

//检查输入内容是否为电话号码
function CheckPhone(objField, strText) {
  if (objField.value == "") return true;
  var RE = new RegExp("[^0-9-]");
  if (objField.value.search(RE) != -1) {
	    errstyle(objField);
	   	qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  return true;
}

//检查字符串长度
function CheckStringLength(objField, strText, numMinLen, numMaxLen){
  if(objField.value == "") return true;
  if(numMinLen != null){
    if(objField.value.length < numMinLen){
	      errstyle(objField);
	   	  qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  }
  if(numMaxLen != null){
    if(objField.value.length > numMaxLen){
	      errstyle(objField);
	   	  qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  }
  return true;
}

//检查输入内容是否为字母、数字

function CheckAlpNum(objField, strText) {
  if (objField.value == "") return true;
  var RE = new RegExp("[^a-zA-Z0-9]");
  if (objField.value.search(RE) != -1) {
	    errstyle(objField);
		qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  return true;
}

//检查输入内容是否为字母、数字和下划线

function CheckAlphaNumber(objField, strText) {
  if (objField.value == "") return true;
  var RE = new RegExp("[^a-zA-Z0-9_]");
  if (objField.value.search(RE) != -1) {
    	errstyle(objField);
		qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  return true;
}

//去除字符串左端空格

function LTrim(str) {
  return str.replace(/^\s*/, '');
}

//去除字符串右端空格

function RTrim(str) {
  return str.replace(/\s*$/, '');
}

//去除字符串两端空格

function Trim(str) {
  return LTrim(RTrim(str));
}

//检查输入内容是否为数字（不允许小数点）
function CheckNum(objField, strText) {
  if (objField.value == "") return true;
  var RE = new RegExp("[^0-9]");
  if (objField.value.search(RE) != -1) {
    	errstyle(objField);
		qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  return true;
}
//检查输入内容是否为数字（不允许小数点,允许是数字）
function CheckNum2(objField, strText) {
  if (objField.value == "") return true;
  var RE = new RegExp("^(?:\-?[1-9]\d*|0)$");
  if (!RE.test(objField.value)) {
    	errstyle(objField);
		qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  return true;
}
//检查输入内容是否为正整数
function CheckPositiveNum(objField, strText) {
  if (objField.value == "") return true;
  var RE = new RegExp("[^0-9]");
  if (objField.value.search(RE) != -1 || objField.value==0) {
    	errstyle(objField);
		qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  return true;
}
//检查输入内容是否为数字（允许小数点）
function CheckNumPoint(objField, strText) {
  if (objField.value == "") return true;
  if (isNaN(objField.value)) {
    	errstyle(objField);
		qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  return true;
}
//检查字符串是否出现中文
function CheckNoChinese(objField, strText) {
  if(objField.value == "") return true;
  var RE = new RegExp("[^\x01-\x7F]");
  if (objField.value.search(RE) != -1) {
    	errstyle(objField);
		qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
  return true;
}
//判断文件后缀
//CheckEndWith(form.title,"文件后缀必选是css格式，请检查后重新提交。",".css")
function CheckEndWith(objField, strText,endwith){
	var tvcss=objField.value.match(/\.[a-zA-Z]+$/)+"";
	if(tvcss.toUpperCase()!=endwith.toUpperCase()){
		errstyle(objField);
		qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
	return true;
}
//不能包含单引、双引、反斜线等非法字符！
function CheckSpecicalChar(objField, strText){
	var Re = /[\x22\x27\x5C]+/ig;
	if ( Re.test(objField.value)){
		errstyle(objField);
		qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
	return true;
}
//检查串不能为空，字符串长度和特殊字符
function CheckNnAndSlAndSc(objField,strText,numMinLen, numMaxLen){
	if(!CheckNotNull(objField,strText+"不能为空!"))return false;
	if(numMinLen)if(!CheckStringLength(objField,strText+"不能少于"+numMinLen+"个字符!"))return false;
	if(numMaxLen)if(!CheckStringLength(objField,strText+"不能超过"+numMaxLen+"个字符!",null,numMaxLen))return false;
	if(!CheckSpecicalChar(objField,strText+"不能包含特殊字符!"))return false;
	return true;
}
//检查内容是否为IP地址！
function CheckIP(objField, strText){
    var Re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
    var passedTest = false;
    
    if (Re.test(objField.value)) {
        objField.value.match(Re);
        if (RegExp.$1 <= 255 && RegExp.$1 >= 0 
        && RegExp.$2 <= 255 && RegExp.$2 >= 0 
        && RegExp.$3 <= 255 && RegExp.$3 >= 0 
        && RegExp.$4 <= 255 && RegExp.$4 >= 0) {
            passedTest = true;
        }
    }
    
    if ( !passedTest){
        errstyle(objField);
        err(strText,objField);
        return false;
    }else{
        cancelerrstyle(objField)
    }
    return true;
}
/*检查存放目录*/
function CheckEchannelName(objField, strText){
     
	//if( objField.value.endWith("/")||objField.value.startWith("/")){
	 //   errstyle(objField);
	 //  	err(strText,objField);
	 //   return false;
	//}else{
  	//    cancelerrstyle(objField)
  	//    return true;
  // }
   
   var patrn = new RegExp("^[a-z0-9_/]+$");
	if (!patrn.exec(objField.value)){
	    errstyle(objField);
	   	qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
}
//不能包含用户指定的字符！
function CheckChar(objField, strText,specialChar){
	
	if ( objField.value.indexOf(specialChar)>0){
		errstyle(objField);
		qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
	return true;
}
/*检查发布目录*/
function CheckPubPath(objField, strText){
	var patrn = new RegExp("^[a-z0-9_/]+$");
	if (!patrn.exec(objField.value)){
	    errstyle(objField);
	   	qtip(objField, strText,0);
	   	return false;
  }else{
  	    cancelerrstyle(objField);
  	    qtip(objField, strText,1);
  }
}

//图文策划不能包含单引、双引、反斜线等非法字符！
function CheckTwchSpecicalChar(objField){
//alert(objField.value.indexOf(","));
	var Re = /[\x22\x27\x5C]+/ig;
	if ( Re.test(objField.value||0<=objField.value.indexOf(","))){
		errstyle(objField);
		qtip(objField, '不能包含单引、双引、反斜线、逗号等特殊字符！',0);
		return false;
	}else if ( 0<=objField.value.indexOf(",")){
		errstyle(objField);
		qtip(objField, '不能包含单引、双引、反斜线、逗号等特殊字符！',0);
		return false;
	}else{
  	    cancelerrstyle(objField);
  	    qtip(objField, '不能包含单引、双引、反斜线、逗号等特殊字符！',1);
  	}
	return true;
}