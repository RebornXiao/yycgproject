config.ButtonDir = "blue";
config.InitMode = "EDIT";
config.AutoDetectPasteFromWord = "1";
config.ShowBorder = "0";
config.StateFlag = "1";
config.CssDir = "coolblue";

function showToolbar(){

	document.write ("<table border=0 cellpadding=0 cellspacing=0 width='100%' class='Toolbar' id='eWebEditor_Toolbar'><tr><td>"+
						"<div class=yToolbar>"+
	"<DIV CLASS=Btn TITLE='"+lang["SelectAll"]+"' onclick=\"format('SelectAll')\"><IMG CLASS=Ico SRC='buttonimage/blue/selectall.gif'></DIV>"+
	"<DIV CLASS=Btn TITLE='"+lang["UnSelect"]+"' onclick=\"format('Unselect')\"><IMG CLASS=Ico SRC='buttonimage/blue/unselect.gif'></DIV>");


	document.write ("</div></td></tr></table>");

}
