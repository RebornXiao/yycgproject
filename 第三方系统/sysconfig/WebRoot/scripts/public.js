/*==================================================
                     公用JS方法
				author:张磊2008.06.27
==================================================*/
//loging层
document.writeln("<div id='logingdiv' style='position:absolute;z-index:1;width:100%;height:100%;background-color:#E9F2FC;'><center><br><br><br><br><br><br><br><br><br><br><div style='width:100px;background-color:#C7D6E9;'><table cellpadding='5' cellspacing='5' style='margin:3px;width:100px;border:1px solid #A3BAE9;'><tr><td>&nbsp;&nbsp;<img align='absmiddle' src='images/whole_wait.gif'></img><font size='5'><b>Loading...</b></font>&nbsp;&nbsp;</td></tr></table></div></center></div>");
//loging层消失
function loadingShow(){
	if(!window.ActiveXObject){
		document.getElementById("logingdiv").style.display="none";
	}else{
	    document.onreadystatechange=function (){
			if(document.getElementById("logingdiv")){
				var state = this.readyState;
				if (state == "complete") {
					setTimeout(function(){
						document.getElementById("logingdiv").outerHTML="";
					}, 250);
				}
			}
		}
	}
}
loadingShow();