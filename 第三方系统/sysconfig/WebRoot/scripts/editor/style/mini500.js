config.ButtonDir = "blue";
config.InitMode = "EDIT";
config.AutoDetectPasteFromWord = "1";
config.ShowBorder = "0";
config.StateFlag = "1";
config.CssDir = "coolblue";

function showToolbar(){

	document.write ("<table border=0 cellpadding=0 cellspacing=0 width='100%' class='Toolbar' id='eWebEditor_Toolbar'><tr><td>"+
						"<div class=yToolbar><DIV CLASS=TBHandle></DIV>"+
	"<SELECT CLASS=TBGen onchange=\"formatFont('fontname',this[this.selectedIndex].value);this.selectedIndex=0\">"+lang["FontName"]+"</SELECT>"+
	"<SELECT CLASS=TBGen onchange=\"formatFont('fontsize',this[this.selectedIndex].value);this.selectedIndex=0\">"+lang["FontSize"]+"</SELECT>"+
	"<DIV CLASS=Btn TITLE='"+lang["Cut"]+"' onclick=\"format('cut')\"><IMG CLASS=Ico SRC='buttonimage/blue/cut.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["Copy"]+"' onclick=\"format('copy')\"><IMG CLASS=Ico SRC='buttonimage/blue/copy.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["Paste"]+"' onclick=\"format('paste')\"><IMG CLASS=Ico SRC='buttonimage/blue/paste.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["FindReplace"]+"' onclick=\"findReplace()\"><IMG CLASS=Ico SRC='buttonimage/blue/findreplace.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["Delete"]+"' onclick=\"format('delete')\"><IMG CLASS=Ico SRC='buttonimage/blue/delete.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["RemoveFormat"]+"' onclick=\"format('RemoveFormat')\"><IMG CLASS=Ico SRC='buttonimage/blue/removeformat.gif'></DIV>"+
	"<DIV CLASS=TBSep></DIV><DIV CLASS=Btn TITLE='"+lang["UnDo"]+"' onclick=\"goHistory(-1)\"><IMG CLASS=Ico SRC='buttonimage/blue/undo.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["ReDo"]+"' onclick=\"goHistory(1)\"><IMG CLASS=Ico SRC='buttonimage/blue/redo.gif'></DIV>"+
	"<DIV CLASS=TBSep></DIV><DIV CLASS=Btn TITLE='"+lang["TableMenu"]+"' onclick=\"showToolMenu('table')\"><IMG CLASS=Ico SRC='buttonimage/blue/tablemenu.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["SelectAll"]+"' onclick=\"format('SelectAll')\"><IMG CLASS=Ico SRC='buttonimage/blue/selectall.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["UnSelect"]+"' onclick=\"format('Unselect')\"><IMG CLASS=Ico SRC='buttonimage/blue/unselect.gif'></DIV>"+
	"<DIV CLASS=TBSep></DIV><DIV CLASS=Btn TITLE='"+lang["OrderedList"]+"' onclick=\"format('insertorderedlist')\"><IMG CLASS=Ico SRC='buttonimage/blue/insertorderedlist.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["UnOrderedList"]+"' onclick=\"format('insertunorderedlist')\"><IMG CLASS=Ico SRC='buttonimage/blue/insertunorderedlist.gif'></DIV>"+
	"<DIV CLASS=TBSep></DIV><DIV CLASS=Btn TITLE='"+lang["ForeColor"]+"' onclick=\"showDialog('selcolor.htm?action=forecolor', true)\"><IMG CLASS=Ico SRC='buttonimage/blue/forecolor.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["InsertImage"]+"' onclick=\"parent.uploadPic()\"><IMG CLASS=Ico SRC='buttonimage/blue/img.gif'></DIV>");

	document.write ("</div></td></tr></table>");

}
