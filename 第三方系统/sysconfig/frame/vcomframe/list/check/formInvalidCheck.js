/*
*js 常用方法
*$(id) 获得页面对象
*String.trim();过滤两端空格
*CheckNotNull(检查对象, str);检查对象value为空或空格 则 alert(请填写str) return false 否则return true;
*CheckEmail(检查对象, str) 检查对象value不是邮箱 则 alert(str格式错误) return false 否则return true;(以下雷同...)
*function CheckPhone(objField, strText, numMinLen, numMaxLen) 检查输入内容是否为电话号码 numMinLen<最小长度>, numMaxLen<最大长度>
*CheckStringLength(objField, strText, numMinLen<最小长度>, numMaxLen<最大长度>)  检查字符串长度 允许空 alert(str 最大长度不能。。。) 
*CheckAlpNum(objField, strText, numMinLen, numMaxLen) 检查输入内容是否为字母、数字 numMinLen<最小长度>, numMaxLen<最大长度>
*CheckAlphaNumber(objField, strText, numMinLen, numMaxLen) 检查输入内容是否为字母、数字和下划线 numMinLen<最小长度>, numMaxLen<最大长度>
*LTrim(str) 左滤空格
*RTrim(str) 右滤空格
*CheckNum(objField, strText, numMinLen, numMaxLen) 检查输入内容是否为数字（不允许小数点）numMinLen<最小长度>, numMaxLen<最大长度>
*CheckNoChinese(objField, strText, numMinLen, numMaxLen)不允许中文 如果包含中文返回 false 否则true numMinLen<最小长度>, numMaxLen<最大长度>
*CheckNumber(objField, strText, numMinValue, numMaxValue) 检查是否为数字类型 numMinValue<最小长度>, numMaxValue<最大长度>
*changeSelectValue(selectobj<选择框对象>,selvalue<要选择的值>) 按照value 修改select对象 选择
*getOptionText(selectobj<选择框对象>,optionvalue) 得到select对象value值所对应的显示内容
*getSelectOption(selectobj,optionvalue) 得到select对象value值所对应的option对象
*
*
*/

//检查必填项
function CheckNotNull(objField, strText) {
  if (Trim(objField.value) == "") {
    //alert("请填写“" + strText + "”！");
    ymPrompt.alert("请填写“" + strText + "”！",null,null,'警告',function(){
    	  objField.focus();
    })
    return false;
  }
  return true;
}

//检查EMail格式
function CheckEmail(objField, strText){
  var objValue = Trim(objField.value);
  if(objValue=="") return true;

  var strErr = "“" + strText + "”格式错误！";
  var RE=/[^A-Za-z0-9_-]/;
  var parts=objValue.split("@");

  if (parts.length!=2)		//	not format as ***@***
  {
      ymPrompt.alert(strErr,null,null,'警告',function(){
    	  objField.focus();
    })
    return false;
  }

  var oneparts;

  oneparts=parts[0].split(".");		//	first-parts
  
  for(i=0;i<oneparts.length;i++)
  {
    if (oneparts[i].length==0)	//连续两点..
    {
      ymPrompt.alert(strErr,null,null,'警告',function(){
    	  objField.focus();
    })
      return false;
    }
    if (RE.exec(oneparts[i])!=null)
    {
      ymPrompt.alert(strErr,null,null,'警告',function(){
    	  objField.focus();
    })
      return false;
    }
  }
  
  oneparts=parts[1].split(".");		//	second-parts

  if (oneparts.length<2)	//连续两点..
  {
    ymPrompt.alert(strErr,null,null,'警告',function(){
    	  objField.focus();
    })
    return false;
  }

  for(i=0;i<oneparts.length;i++)
  {
    if (oneparts[i].length==0)	//连续两点..
    {
      ymPrompt.alert(strErr,null,null,'警告',function(){
    	  objField.focus();
    })
      return false;
    }
    if (RE.exec(oneparts[i])!=null)
    {
     ymPrompt.alert(strErr,null,null,'警告',function(){
    	  objField.focus();
    })
      return false;
    }
  }
  return true;
}

//检查输入内容是否为电话号码
function CheckPhone(objField, strText, numMinLen, numMaxLen) {
  if (objField.value == "") return true;
  var RE = new RegExp("[^0-9-]");
  if (objField.value.search(RE) != -1) {
    ymPrompt.alert("“" + strText + "”中只能填写数字及-！",null,null,'警告',function(){
    	  objField.focus();
    })
    return false;
  }
  return CheckStringLength(objField, strText, numMinLen, numMaxLen);
}

//检查字符串长度
function CheckStringLength(objField, strText, numMinLen, numMaxLen){
  if(objField.value == "") return true;
  if(numMinLen != null){
    if(objField.value.length < numMinLen){
      ymPrompt.alert("“" + strText + "”的长度不能少于 " + numMinLen.toString() + " 个字符！",null,null,'警告',function(){
    	  objField.focus();
    })
      return false;
    }
  }
  if(numMaxLen != null){
    if(objField.value.length > numMaxLen){ 
      ymPrompt.alert("“" + strText + "”的长度不能多于 " + numMaxLen.toString() + " 个字符！",null,null,'警告',function(){
    	  objField.focus();
    })
      return false;
    }
  }
  return true;
}

//检查输入内容是否为字母、数字

function CheckAlpNum(objField, strText, numMinLen, numMaxLen) {
  if (objField.value == "") return true;
  var RE = new RegExp("[^a-zA-Z0-9]");
  if (objField.value.search(RE) != -1) {
    ymPrompt.alert("“" + strText + "”中只能填写字母、数字！",null,null,'警告',function(){
    	  objField.focus();
    })
    return false;
  }
  return CheckStringLength(objField, strText, numMinLen, numMaxLen);
}

//检查输入内容是否为字母、数字和下划线

function CheckAlphaNumber(objField, strText, numMinLen, numMaxLen) {
  if (objField.value == "") return true;
  var RE = new RegExp("[^a-zA-Z0-9_]");
  if (objField.value.search(RE) != -1) {
    ymPrompt.alert("“" + strText + "”中只能填写字母、数字或下划线！",null,null,'警告',function(){
    	  objField.focus();
    })
    return false;
  }
  return CheckStringLength(objField, strText, numMinLen, numMaxLen);
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


//字符串对象加入过滤空格方法
String.prototype.trim = function() 
{ 
    return this.replace(/(^\s*)|(\s*$)/g, ""); 
}


//检查输入内容是否为数字（不允许小数点）
function CheckNum(objField, strText, numMinLen, numMaxLen) {
  if (objField.value == "") return true;
  var RE = new RegExp("[^0-9]");
  if (objField.value.search(RE) != -1) {
    ymPrompt.alert("“" + strText + "”中只能填写数字！",null,null,'警告',function(){
    	  objField.focus();
    })
    return false;
  }
  return CheckStringLength(objField, strText, numMinLen, numMaxLen);
}
//检查字符串是否出现中文
function CheckNoChinese(objField, strText, numMinLen, numMaxLen) {
  if(objField.value == "") return true;
  var RE = new RegExp("[^\x01-\x7F]");
  if (objField.value.search(RE) != -1) {
      ymPrompt.alert("“" + strText + "”中不能出现中文！",null,null,'警告',function(){
    	  objField.focus();
    })
    return false;		
  }
  return CheckStringLength(objField, strText, numMinLen, numMaxLen);
}

//检查是否为数字类型
function CheckNumber(objField, strText, numMinValue, numMaxValue) {
  if(objField.value == "") return true;
  if(isNaN(objField.value)){
    ymPrompt.alert("“" + strText + "”中只能填写数字！",null,null,'警告',function(){
    	  objField.focus();
    })
    return false;
  }
  var numValue = parseFloat(objField.value);
  if(numMinValue != null){
    if(numValue < numMinValue){
      ymPrompt.alert("“" + strText + "”的值不能小于 " + numMinValue.toString() + " ！",null,null,'警告',function(){
    	  objField.focus();
    })
      return false;
    }
  }
  if(numMaxValue != null){
    if(numValue > numMaxValue){
      ymPrompt.alert("“" + strText + "”的值不能大于 " + numMaxValue.toString() + " ！",null,null,'警告',function(){
    	  objField.focus();
    })
      return false;
    }
  }
  return true;
}

//按照value 修改select 选择
function changeSelectValue(selectobj,selvalue){
	try{
		var optlist = selectobj.options;
		for(var i=0;i<optlist.length;i++){
			if(optlist[i].value=selvalue){
				optlist[i].selected=true;
			}
		}
	}catch(e){
		
	}
}

//通过value得到对应text的值
function getOptionText(selectobj,optionvalue){
	var ttext = null;
	try{
		var optlist = selectobj.options;
		for(var i=0;i<optlist.length;i++){
			if(optlist[i].value=optionvalue){
				ttext = optlist[i].text;
			}
		}
	}catch(e){
		
	}
}

//获得对应value的option对象
function getSelectOption(selectobj,optionvalue){
	var topt = null;
	try{
		var optlist = selectobj.options;
		for(var i=0;i<optlist.length;i++){
			if(optlist[i].value=optionvalue){
				topt = optlist[i];
			}
		}
	}catch(e){
		
	}
}
