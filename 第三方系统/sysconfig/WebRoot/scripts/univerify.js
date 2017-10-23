  /******************************************************/
/* 文件名：univerify.js                               */
/* 功  能：基于自定义属性的统一检测用Javascript函数库 */
/* 作  者：苗润土  */
/******************************************************/
/** 
 *	判断是否是数字
 *	是数字:返回true 否则:返回false
 */
function isDigit(dstr)
{
	return ((dstr>='0') && (dstr<='9'));
}


/**
 *	判断是否是字母(A--Z a--z)
 *	是字母:返回true 否则:返回false
 */
function isAlpha(astr)
{
	return (((astr>='A') && (astr<='Z')) || ((astr>='a') && (astr<='z')));
}

/**
 *	校验字符串是否包含非数字字
 *  包含非数字:返回 false 否则:返回true
 *
 */
function isnumber(checkStr)
{
  var i = 0;
  var result = true;
  var cstr = "";
  var count = 0;
  for (i = 0; i < checkStr.length; i++)
  {
  	cstr = checkStr.charAt(i);
  	if(i==0&&!isDigit(cstr)){
  	  result = false;
  	  break;
  	}
  	if(i==1&&checkStr.charAt(0)=='0'&&cstr!='.'){
  	  result = false;
  	  break;
  	}
  	if(cstr==".") {
  	  count++;
  	}
  	if(!isDigit(cstr) && (cstr!='.'))
  	{
  		result = false;
  		break;
  	}
  }
  if(count>1) {
    return false;
  }

  return result;
}


/**
 *	校验字符串是否纯数字
 *  包含非数字:返回 false 否则:返回true
 *
 */
function isnumber_all(checkStr)
{
  var i = 0;
  var result = true;
  var cstr = "";
  for (i = 0; i < checkStr.length; i++)
  {
  	cstr = checkStr.charAt(i);
  	if(!isDigit(cstr))
  	{
  		result = false;
  		break;
  	}
  }

  return result;
}
/**
	* 功能：校验合法日期 格式如(2007-02-27、2007/02/28、2007.02.28)
	*
	* 参数str：输入值
	* 返回值：true：合法 false：非法
	*/
function isDate(str) {
	//if (!/\d{4}(\.|\/|\-)\d{1,2}(\.|\/|\-)\d{1,2}/.test(str)) {
	if (!/\d{4}(\-)\d{1,2}(\-)\d{1,2}/.test(str)) {
		return false;
	}
	var r = str.match(/\d{1,4}/g);
	if (r == null) {
		return false;
	}
	var d = new Date(r[0], r[1] - 1, r[2]);
	return (d.getFullYear() == r[0] && (d.getMonth() + 1) == r[1] && d.getDate() == r[2]);
}
/**
	* 功能：校验合法长时间 格式如(2007-02-27 13:04:06)
	*
	* 参数str：输入值
	* 返回值：true：合法 false：非法
	*/
function isDateTimeNew(str) {
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
	var r = str.match(reg);
	if (r == null) {
		return false;
	}
	var d = new Date(r[1], r[3] - 1, r[4], r[5], r[6], r[7]);
	return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4] && d.getHours() == r[5] && d.getMinutes() == r[6] && d.getSeconds() == r[7]);
}
/**
	* 功能：校验合法长时间 格式如(13:04:06)
	*
	* 参数str：输入值
	* 返回值：true：合法 false：非法
	*/
function isTimeNew(str) {
	var reg = /^(\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
	var r = str.match(reg);
	if (r == null) {
		return false;
	}
	var d= new Date();
	
	var e=new Date(d.getFullYear(),d.getMonth(), d.getDate(),r[1],r[2],r[3]);
	return (e.getHours() == r[1] && e.getMinutes() == r[2] && e.getSeconds() == r[3]);
}
/**
 *	校验字符串是否正整数
 *  是正整数:返回 true 否则:返回true
 */
function isint(checkStr) {
  var i = 0;
  var result = true;
  var cstr = "";
  for (i = 0; i < checkStr.length; i++)
  {
  	cstr = checkStr.charAt(i);
  	if(!isDigit(cstr))
  	{
  		result = false;
  		break;
  	}
  	if(i==0){
  	if(cstr=='0'){
  	result = false;
  	break;
  	}
  	}
  	
  }
  return result;
}
/**
	* 功能：IP地址格式校
	*
	* 参数str：输入值
	* 返回值：true：合法 false：非法
	*/
function isIP(str) {
	return (new RegExp(/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/).test(str));
}
/*
 *清除前面指定字符
 */
 function clearCharinfront(oldstring,clear_c){
   var new_string;
   var end_index;
   for(var i=0;i<oldstring.length;i++){
       if(oldstring.charAt(i)!=clear_c||i==oldstring.length-1){
         end_index = i;
         break;
       }      
   } 
   new_string = oldstring.substring(end_index,oldstring.length);
   return new_string;
 }
 /*
  *格式化字符串
  */
function formatstring(oldstring,count_){
    var result =oldstring;
    var len = count_ - oldstring.length;
    for (var i = 0; i < len; i++) {
      result = "0" + result;
    }
    return result;
}
function runCode(url) {
	var winname = window.open('', "_blank", '');
	winname.document.open('text/html', 'replace');
	winname.document.write('正在载入...');
	if(this.url!=null){
	  winname.location.href=this.url;
	}else if(url!=null){
	  winname.location.href=url;
	}else{
	  return false;
	}
	winname.document.close();
}
/* 取得字符串的字节长度 */
function strlen(str)
{var i;
var len;
len = 0;
for (i=0;i<str.length;i++)
{
if (str.charCodeAt(i)>255) len+=2; else len++;
}
return len;
}

/* 检测字符串是否为空 */
function isnull(str)
{
var i;
 for (i=0;i<str.length;i++)
{
  if (str.charAt(i)!=' ') return false;
}
 return true;
}
/*email的判断。*/
function ismail (s)
{
        if (s.length > 100)
        {
                window.alert("email地址长度不能超过100位!");
                return false;
        }

         var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$"
         var re = new RegExp(regu);
         if (s.search(re) != -1) {
               return true;
         } else {
               //window.alert ("请输入有效合法的E-mail地址 ！")
               return false;
         }
}
/*输入网址验证*/
function check_http(check_obj){
    if(check_obj.search(/^https?:\/\/((\w|-)+\.)+\w+\/?$/i)>=0){
        return true;
        }else{
        return false;
        }
}

/*电话号码的判断。*/
 function isphone(telephone)
{
  if(isnull(telephone)==false){
    var checkOK = "0123456789-";
      var checkStr = telephone;
      var allValid = true;
      var decPoints = 0;
      var allNum = "";
    
      for (i = 0;  i < checkStr.length;  i++)
      {
        ch = checkStr.charAt(i);
        for (j = 0;  j < checkOK.length;  j++)
          if (ch == checkOK.charAt(j))
            break;
        if (j == checkOK.length)
        {
          allValid = false;
          break;
        }
        if (ch != ".")
          allNum += ch;
      }
      if (!allValid)
      {
        alert("对不起，您的输入的联系电话有错误，请重新输入！");
        return false;
    }
  }
  
return true;
}  
/*日期的判断。*/

function isDate(sDate)
{    var iaMonthDays = [31,28,31,30,31,30,31,31,30,31,30,31];
    var iaDate = new Array(3);
    var year, month, day;

    if (arguments.length != 1) return false;
    iaDate = sDate.toString().split("-");
    if (iaDate.length != 3) return false;
    if (iaDate[1].length > 2 || iaDate[2].length > 2) return false;

    year = parseFloat(iaDate[0]);
    month = parseFloat(iaDate[1]);
    day=parseFloat(iaDate[2]);

    if (year < 1900 || year > 2100) return false;
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1]=29;
    if (month < 1 || month > 12) return false;
    if (day < 1 || day > iaMonthDays[month - 1]) return false;
    return true;
}
function isDate_new(sDate){
  try{
  var iaMonthDays = [31,28,31,30,31,30,31,31,30,31,30,31];
  if (arguments.length != 1) return false;
  iaDate = sDate.toString().split("-");
  if (iaDate.length != 3) return false;
  if (iaDate[1].length > 2 || iaDate[2].length > 2) return false;
  year = parseFloat(iaDate[0]);
  month = parseFloat(iaDate[1]);
  day=parseFloat(iaDate[2]);
  if(isNaN(year)||isNaN(month)||isNaN(day)){return false;}
  if(year<=0||month<=0||day<=0){return false;}
  if(year>9999){return false;}
  if (year < 1900) return false;
  if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1]=29;
  if (month < 1 || month > 12) return false;
  if (day < 1 || day > iaMonthDays[month - 1]) return false;
  new Date(year,month-1,day);
  }catch(e){return false;}
  return true;
}
//手机校验
function ismobile_new(mobile){   
    var regu =/(^[1][3][0-9]{9}$)|(^[1][5][0-9]{9}$)/;  
    var regu_l =/(^[1][0][6][0][1-9]{1}[0-9]{1,2}[1-9]{1}\d{6,7}$)/;  
     var re = new RegExp(regu);  
     var re_l = new RegExp(regu_l);  
     if (re.test( mobile.value )||re_l.test( mobile.value )) {  
       return true;  
     }  
     if(mobile.chname!=null){
         alert(mobile.chname+"输入格式不正确！");
     }else{
         alert("您输入的手机号码不正确！");
     }
     return false;
} 

//手机校验
function checkMobile(s) { 
	
	var flg=false; 
	
	var field13=/^13\d{9}$/g; 
	
	var field15=/^15[0,1,3,5,6,7,8,9]\d{8}$/g; 
	
	var field18=/^18[6,8,9]\d{8}$/g; 
	
	if((field13.exec(s))||(field15.exec(s))||(field18.exec(s))) 
	
	{ flg=true; } 
	
	else { 
	flg=false; 
	} 
		
	return flg; 
	} 

/*email的判断。*/
function checkemail (s)
{
        if (s.length > 50)
        {
               // window.alert("email地址长度不能超过50个字符!");
                return false;
        }

         var regu = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"
         var re = new RegExp(regu);
         if (s.search(re) != -1) {
               return true;
         } else {
               //window.alert ("请输入有效合法的E-mail地址 ！")
               return false;
         }
}
//电话校验
function isphone_new(phone){
  var regu =/(^([0][1-9]{1}\d{1,2}[-]?)?[1-9]{1}\d{6,7}$)/;   
     var re = new RegExp(regu);  
     if (re.test( phone.value )) {  
       return true;  
     }  
     if(phone.chname!=null){
         alert(phone.chname+"输入格式不正确！");
     }else{
         alert("您输入的电话号码不正确！");
     }  
     return false; 
}
//电话区号
function isphoneone_new(phone){
  var regu =/(^[0][1-9]{1}\d{1,2}$)/;   
     var re = new RegExp(regu);  
     if (re.test( phone.value )) {  
       return true;  
     }  
     if(phone.chname!=null){
         alert(phone.chname+"输入格式不正确！");
     }else{
         alert("您输入的电话区号不正确！");
     }  
     return false; 
}
function isphonetwo_new(phone){
  var regu =/(^[1-9]{1}\d{6,7}$)/;   
     var re = new RegExp(regu);  
     if (re.test( phone.value )) {  
       return true;  
     }  
     if(phone.chname!=null){
         alert(phone.chname+"输入格式不正确！");
     }else{
         alert("您输入的电话号码不正确！");
     }  
     return false; 
}
//判断是否包括中文、数字、英文、句点、斜杠
function istextcum(str){
  var result = true;
   var reg = /^[\a-zA-Z0-9\.\/\u0391-\uFFE5]*$/;
   var r = str.match(reg);
   if(r==null){
      result = false;
      //alert("只能输入中文、英文、数字、斜杠和句点");
      return result;
   }
   return result;
}
//判断是否包括中文、数字、英文、句点、横杠、下划杠
function istextcum2(str){
  var result = true;
   var reg = /^[\a-zA-Z0-9\.\-\_\u0391-\uFFE5]*$/;
   var r = str.match(reg);
   if(r==null){
      result = false;
      //alert("只能输入中文、英文、数字、横杠和句点");
      return result;
   }
   return result;
}
//判断是否包括中文、数字、英文、句点、横杠
function istextcum1(str){
  var result = true;
   var reg = /^[\a-zA-Z0-9\.\-\u0391-\uFFE5]*$/;
   var r = str.match(reg);
   if(r==null){
      result = false;
      //alert("只能输入中文、英文、数字、横杠和句点");
      return result;
   }
   return result;
}
//判断是否包括中文、数字、英文
function iscnnumen(str){
  var result = true;
   var reg = /^[\a-zA-Z0-9\u4e00-\u9fa5]*$/;
   var r = str.match(reg);
   if(r==null){
      result = false;
      alert("只能输入中文、英文、数字");
      return result;
   }
   return result;
}
//判断是否包括数字、英文、"."、"-"
function iscumenglish(str){
  var result = true;
   var reg = /^[\a-zA-Z0-9\.\-]*$/;
   var r = str.match(reg);
   if(r==null){
      result = false;
      return result;
   }
   return result;
}

//判断是否只包括数字
function isonlynum(str){
  var result = true;
   var reg = /^[\0-9]*$/;
   var r = str.match(reg);
   if(r==null){
      result = false;
      return result;
   }
   return result;
}

//验证不允许输入"'"
function isSearchData(str){
	var result = true;
	var reg = /\'/;
	var r = str.match(reg);

	if(r == "'"){
		result = false;		
		return result;
	}
	return result;	
}
//  身份证的验证
function isIdCardNo(num)
      {
      
        var len = num.length, re; 
        if (len == 15){
			if(!isint(num)){
				alert("身份证号码必须是整数!")
				return false
			}
          re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
        }
		else if (len == 18){
			if(!isint(num.substring(0,17))){
			    alert("前17位应是整数!")
			    return flase
		   }
          re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/);
        }
		else {alert("身份证输入的数字位数不对！"); return false;}
        var a = num.match(re);
        if (a != null)
        {
          if (len==15)
          {
            var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);
            var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
          }
          else
          {
            var D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
            var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
          }
          if (!B) {alert("输入的身份证号 "+ a[0] +" 里出生日期不对！"); return false;}
        }
       return true;
      } 
     
 //判断单选框是否选择。     
 function CheckRadio(val,msg1,msg2)
{
    var is_radio=val.type;
    
    var tt = document.getElementsByName(val.name)
    var s_msg1=(msg1==null || msg1=="")? "请选择 radio!":"在"+msg1+"中请选择一个答案！";
    var s_msg2=(msg2==null || msg2=="")? "没有可选的 radio!":msg2;
    
    if(is_radio=="radio")
	{if (val.nullable=="no")
    {           
    		
            var check_length = tt.length;
            //alert(check_length);
            var i_count=0
            for(var i=0;i<check_length;i++)
            {
                if (tt(i).checked)
                {
                    i_count=i_count+1;
                    return true;
                }
            }
            if(i_count==0)
            {
                alert(s_msg1);
                return false;
            }
       
    }//
   }

}     
     
/******************************************/
/*功能：弹出窗体                          */
/*参数：URL 地址,TYPE 模式/全屏,SC 是否显示滚动条*/
/*      iW 宽度,iH 高度,TOP 头部位置,LEFT 左边位置,*/
/*      R 改变大小,S 状态栏显示,T,TB 帮助显示*/
/*返回：无                                */
/******************************************/ 
function OW(URL,TYPE,SC,iW,iH,TOP,LEFT,R,S,T,TB)
{
	var sF="dependent=yes,resizable=no,toolbar=no,status=yes,directories=no,menubar=no,";
	

	sF+="scrollbars="+(SC?SC:"NO")+",";
	
	if (TYPE=="full"){
		sF+=" Width=1010,";
		sF+=" Height=750,";
		sF+=" Top=0,";
		sF+=" Left=0,";
		window.open(URL, "_blank", sF, false);
		return;
	}
	
	
	if (TYPE=="modal"){
		sF ="resizable:no; status:no; scroll:yes;";
		sF+=" dialogWidth:800px;";
		sF+=" dialogHeight:570px;";
			if(parent.length<2){
				sF+="dialogTop:"+(parseInt(parent.dialogTop)+25)+"px;";					
				sF+="dialogLeft:"+(parseInt(parent.dialogLeft)+25)+"px;";
				}
		
		var alpha = '?';
		var _URL = URL;
		var unique = (new Date()).getTime();
		//如果没有参数		
		if (_URL.indexOf(alpha) == -1){
			_URL+= "?time="+unique;
		}
		else
		{
		//如果带有参数，含有‘?’
			_URL+= "&time="+unique;
		}
		//alert(_URL.indexOf(alpha));alert(_URL);
		return window.showModalDialog(_URL,window,sF);
	}
	else if (TYPE=="modeless"){
		
		sF ="resizable:no; status:no; scroll:yes;";
		sF+=" dialogWidth:800px;";
		sF+=" dialogHeight:570px;";
			if(parent.length<2){
				sF+="dialogTop:"+(parseInt(parent.dialogTop)+25)+"px;";					
				sF+="dialogLeft:"+(parseInt(parent.dialogLeft)+25)+"px;";
				}
		
		var alpha = '?';
		var _URL = URL;
		var unique = (new Date()).getTime();
		//如果没有参数		
		if (_URL.indexOf(alpha) == -1){
			_URL+= "?time="+unique;
		}
		else
		{
		//如果带有参数，含有??
			_URL+= "&time="+unique;
		}
		//alert(_URL.indexOf(alpha));alert(_URL);
		return window.showModelessDialog(_URL,window,sF);
	}	
	else
	{
		if(iW!=undefined && iH!=undefined){
			sF+=" Width="+iW+",";
			sF+=" Height="+iH+",";
		}
		else{
			sF+=" Width=800,";
			sF+=" Height=570,";
			iW = 800;
			iH = 570;
		}
		/*
		if(window.opener==null || window.opener==undefined){
			sF+=" Top=50px,";
			sF+=" Left=50px,";
		}else{
			sF+="Top="+(parseInt(window.screenTop)+20)+"px,";					
			sF+="Left="+(parseInt(window.screenLeft)+30)+"px,";
		}
		*/
		var l = ( screen.availWidth - iW ) / 2;
   		    var  t = ( screen.availHeight - iH ) / 2;
   		    sF+="left="+l+"px,";
   		    sF+="top="+t+"px,";
		sF+=" scrollbars=yes,"
		window.open(URL, "_blank", sF, false);
	
	}
}

/* 检测指定文本框输入是否合法 */
function verifyInput(input)
{
var image;
var i;
var error = false;
//单选校验
  if ( CheckRadio(input,input.chname,"")==false)
   {
	error= true;
	}
//
/* 长度校验 */
if (strlen(input.value)<parseInt(input.smallsize)&&strlen(input.value)!=0)
{
alert(input.chname+"内容长度至少"+input.smallsize+"个字符!\n\n(一个汉字按两个字符算)");
error = true;
}

if (strlen(input.value)>parseInt(input.maxsize))
{
alert(input.chname+"内容长度最多"+input.maxsize+"个字符!\n\n(一个汉字按两个字符算)");
error = true;
}
else
/* 非空校验 */
if (input.nullable=="no"&&(isnull(input.value)||(input.value=="")))
{
alert(input.chname+"不能为空");
error = true;
}
if(isnull(input.value)){
  input.value = "";
}
if (!isnull(input.value))
{
/* 数据类型校验 */
switch(input.datatype)
{
case "number": if (isnumber(input.value)==false)
{
alert(input.chname+"值类型为数字型!");
error = true;
}
break;
case "int": if (isint(input.value)==false)
{
alert(input.chname+"值类型为整数型!");
error = true;
}
break;
case "onlynumber": if (isnumber_all(input.value)==false)
{
alert(input.chname+"值类型为数字类型!");
error = true;
}
break;
case "email": if (ismail(input.value)==false)
{
alert(input.chname+"格式输入不正确!");
error = true;
}
break;
case "phone": if (isphone_new(input)==false)
{
alert(input.chname+"格式输入不正确!");
error = true;
}
break;
case "phoneone": if (isphoneone_new(input)==false)
{

error = true;
}
break;
case "phonetwo": if (isphonetwo_new(input)==false)
{

error = true;
}
break;
case "mobile": if (ismobile_new(input)==false)
{
error = true;
}
break;
case "date": if (isDate_new(input.value)==false)
{
alert(input.chname+"值不是合法的日期格式!(如：2008-4-5)");
error = true;
}
break;
case "datetime": if (isDateTimeNew(input.value)==false)
{
alert(input.chname+"值不是合法的日期格式!(如：2008-4-5 12:3:15)");
error = true;
}
break;
case "onlytime": if (isTimeNew(input.value)==false)
{
alert(input.chname+"值不是合法的时间格式!(如：12:3:15)");
error = true;
}
break;
case "ip": if (isIP(input.value)==false)
{
alert(input.chname+"值不是合法的IP地址格式!(如：192.168.1.1)");
error = true;
}
break;
case "card": if (isIdCardNo(input.value)==false)
{
//alert(input.chname+"值不是合法的日期");
error = true;
}
break;
case "textcum": if (istextcum(input.value)==false)
{
alert(input.chname+"的值只能输入中文、英文、数字、斜杠和句点");
error = true;
}
break;
case "textcum1": if (istextcum1(input.value)==false)
{
alert(input.chname+"的值只能输入中文、英文、数字、横杠和句点");
error = true;
}
break;
case "cnnumen": if (iscnnumen(input.value)==false)
{
alert(input.chname+"的值只能输入中文、英文、数字");
error = true;
}
break;
case "cumenglish": if (iscumenglish(input.value)==false)
{
alert(input.chname+"的值只能是英文、数字、点和横杠");
error = true;
}
break;
/* 在这里可以添加多个自定义数据类型的校验判断 */
/*  case datatype1: ... ; break;        */
/*  case datatype2: ... ; break;        */
/*  ....................................*/

default		: break;
}
}
/* 根据有无错误设置或取消警示标志 */
if (error)
{
//image = document.getElementById("img_"+input.name);
//image.src="../jsp/supply/img/warning.gif";
return false;
}
else
{
//image = document.getElementById("img_"+input.name);
//image.src="../jsp/supply/img/space.gif";
return true;
}
}
/* 检测指定FORM表单所有应被检测的元素
（那些具有自定义属性的元素）是否合法，此函数用于表单的onsubmit事件 */
function verifyAll(myform)
{
var i;
for (i=0;i<myform.elements.length;i++)
{
 /* 非自定义属性的元素不予理睬 */
if (myform.elements[i].chname+""=="undefined") continue;
/* 校验当前元素 */

if (verifyInput(myform.elements[i])==false)
{
//myform.elements[i].focus();
return false;
}
}
return true;
}
function ltrim(s){ return s.replace( /^\s*/, ""); }
function rtrim(s){ return s.replace( /\s*$/, ""); }
function trim(s){ return rtrim(ltrim(s)); }
function deleteArrayByid(obj,id){
  if(obj&&id){
    for(var i=0,length=obj.length;i<length;i++){
      if(obj[i]==id){
        delete obj[i];
      }
    }
  }
}
 function geturl(form){
    var queryString = "";
      for (var i = 0; i < form.elements.length; i++) {
			var e = form.elements[i];
			if ((e.name != null) && (e.value != null)) {
			    if(e.type=="radio"||e.type=="checkbox"){
			      if(e.checked){
			        //queryString += e.name + "=" + encodeURIComponent(e.value) + "&";
			        queryString += e.name + "=" + e.value + "&";
			      }
			    }else{
			      //queryString += e.name + "=" + encodeURIComponent(e.value) + "&";
			      queryString += e.name + "=" + e.value + "&";
			      
			    }
				
			}
		}
		return queryString;
    }
    
    function EncodeUtf8(s1)
  {
      var s = escape(s1);
      var sa = s.split("%");
      var retV ="";
      if(sa[0] != "")
      {
         retV = sa[0];
      }
      for(var i = 1; i < sa.length; i ++)
      {
           if(sa[i].substring(0,1) == "u")
           {
               retV += Hex2Utf8(Str2Hex(sa[i].substring(1,5)));
               
           }
           else retV += "%" + sa[i];
      }
      
      return retV;
  }
  function Str2Hex(s)
  {
      var c = "";
      var n;
      var ss = "0123456789ABCDEF";
      var digS = "";
      for(var i = 0; i < s.length; i ++)
      {
         c = s.charAt(i);
         n = ss.indexOf(c);
         digS += Dec2Dig(eval(n));
           
      }
      //return value;
      return digS;
  }
  function Dec2Dig(n1)
  {
      var s = "";
      var n2 = 0;
      for(var i = 0; i < 4; i++)
      {
         n2 = Math.pow(2,3 - i);
         if(n1 >= n2)
         {
            s += '1';
            n1 = n1 - n2;
          }
         else
          s += '0';
          
      }
      return s;
      
  }
  function Dig2Dec(s)
  {
      var retV = 0;
      if(s.length == 4)
      {
          for(var i = 0; i < 4; i ++)
          {
              retV += eval(s.charAt(i)) * Math.pow(2, 3 - i);
          }
          return retV;
      }
      return -1;
  } 
  function Hex2Utf8(s)
  {
     var retS = "";
     var tempS = "";
     var ss = "";
     if(s.length == 16)
     {
         tempS = "1110" + s.substring(0, 4);
         tempS += "10" +  s.substring(4, 10); 
         tempS += "10" + s.substring(10,16); 
         var sss = "0123456789ABCDEF";
         for(var i = 0; i < 3; i ++)
         {
            retS += "%";
            ss = tempS.substring(i * 8, (eval(i)+1)*8);
            
            
            
            retS += sss.charAt(Dig2Dec(ss.substring(0,4)));
            retS += sss.charAt(Dig2Dec(ss.substring(4,8)));
         }
         return retS;
     }
     return "";
  } 
  
      Ext.onReady(function() {
		document.onmousedown=function(){parent.Ext.menu.MenuMgr.hideAll();};  
	});
  