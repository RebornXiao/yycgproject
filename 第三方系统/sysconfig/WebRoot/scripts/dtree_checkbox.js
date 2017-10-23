/*--------------------------------------------------|

| dTree 2.05 | www.destroydrop.com/javascript/tree/ |

|---------------------------------------------------|

| Copyright (c) 2002-2003 Geir Landr?              |

|                                                   |

| This script can be used freely as long as all     |

| copyright messages are intact.                    |

|                                                   |

| Updated: 17.04.2003                               |

|--------------------------------------------------*/



// Node object

function Node(id, pid, name, url, title, checkName,target, icon, iconOpen, open,isCheckBox,isSelect,_value) {

	this.id = id;
	
	this.pid = pid;

	this.name = name;
	
	this._value = _value;

	this.checkName = checkName;

	this.url = url;

	this.title = title;

	this.target = target;

	this.icon = icon;

	this.iconOpen = iconOpen;

	this._io = open || false;

	this._is = false;

	this._ls = false;

	this._hc = false;

	this._ai = 0;

	this._p;
	
	this.isCheckBox=isCheckBox; //新添加I(wangzhiguo)
	
	this.isSelect=isSelect;//是否选中

};



// Tree object

function treeCheckBox(objName,contextPath,check) {

	this.path = contextPath; //当前应用的路径
	this.config = {

		target					: null,

		folderLinks			: true,

		useSelection		: true,

		useCookies			: true,

		useLines				: true,

		useIcons				: true,

		useStatusText		: false,

		closeSameLevel	: false,

		inOrder					: false,
		
		check                :check   //**新加的checkbox   

	};

	this.icon = {

		root				: 'images/tree_base.gif',

		folder			:'images/tree_folder.gif',

		folderOpen	: 'images/tree_folderopen.gif',

		node				: 'images/tree_file.gif',

		empty				: 'images/tree_empty.gif',

		line				: 'images/tree_line.gif',

		join				: 'images/tree_join.gif',

		joinBottom	: 'images/tree_joinbottom.gif',

		plus				: 'images/tree_plus.gif',

		plusBottom	: 'images/tree_plusbottom.gif',

		minus				: 'images/tree_minus.gif',

		minusBottom	: 'images/tree_minusbottom.gif',

		nlPlus			: 'images/tree_nolines_plus.gif',

		nlMinus			: 'images/tree_nolines_minus.gif'

	};

	this.obj = objName;

	this.aNodes = [];

	this.aIndent = [];

	this.root = new Node(-1);

	this.selectedNode = null;

	this.selectedFound = false;

	this.completed = false;
	


};



// Adds a new node to the node array
//在原有的node对象，添加一个新参数为isCheckBox，作为标识,true有checkBox，false 为无
treeCheckBox.prototype.add = function(id, pid, name, url, title, checkName, target, icon, iconOpen, open,isCheckBox,isSelect,_value) {

	this.aNodes[this.aNodes.length] = new Node(id, pid, name, url, title, checkName, target, icon, iconOpen, open,isCheckBox,isSelect,_value);

};



// Open/close all nodes

treeCheckBox.prototype.openAll = function() {

	this.oAll(true);

};

treeCheckBox.prototype.closeAll = function() {

	this.oAll(false);

};



// Outputs the tree to the page

treeCheckBox.prototype.toString = function() {

	var str = '<div class="dTree">\n';
	
	if (document.getElementById) {

		if (this.config.useCookies) this.selectedNode = this.getSelected();

		str += this.addNode(this.root);

	} else str += 'Browser not supported.';

	str += '</div>';

	if (!this.selectedFound) this.selectedNode = null;

	this.completed = true;

	return str;

};

treeCheckBox.prototype.setDiv = function(divid){
	jqueryGetEleById(divid).html(this.toString());
};

// Creates the tree structure

treeCheckBox.prototype.addNode = function(pNode) {

	var str = '';

	var n=0;

	if (this.config.inOrder) n = pNode._ai;

	for (n; n<this.aNodes.length; n++) {

		if (this.aNodes[n].pid == pNode.id) {

			var cn = this.aNodes[n];

			cn._p = pNode;

			cn._ai = n;

			this.setCS(cn);

			if (!cn.target && this.config.target) cn.target = this.config.target;

			if (cn._hc && !cn._io && this.config.useCookies) cn._io = this.isOpen(cn.id);

			if (!this.config.folderLinks && cn._hc) cn.url = null;

			if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {

					cn._is = true;

					this.selectedNode = n;

					this.selectedFound = true;

			}

			str += this.node(cn, n);

			if (cn._ls) break;

		}

	}

	return str;

};



// Creates the node icon, url and text

treeCheckBox.prototype.node = function(node, nodeId) {

	var str = '<div class="dTreeNode">' + this.indent(node, nodeId);

	// 生成checkbox按钮
	if(this.config.check){
		
		// checkbox的名字
		var nameStr = '';
		var selectStr = '';
		// 生成名字串
		if(node.checkName){

			nameStr = 'name="' + node.checkName+'"';
		}
		if(node.isSelect){
			selectStr = 'checked="checked"';
		}
		var curforid="c"+this.obj + nodeId;
		if(node.pid==-1){
          if(node.isCheckBox){
			   str+= '<label for="'+ curforid+'" style="padding:0px;"><input ' + selectStr + ' type="checkbox"  ' + nameStr + ' id="c'+ this.obj + nodeId + '"  value ="' + (null!=node._value&&''!=node._value?node._value:node.id)+ '" onclick="javascript:'+this.obj+'.cc0('+nodeId+')"/>';
			 }else{
			   str+= '<input ' + selectStr + ' type="checkbox" style="display:none" ' + nameStr + ' id="c'+ this.obj + nodeId + '"  value ="' + (null!=node._value&&''!=node._value?node._value:node.id)+ '" onclick="javascript:'+this.obj+'.cc0('+nodeId+')"/>';
		  }
		    

		} else {
// 新添加(wangzhiguo)
			if(node.isCheckBox){
			   str+= '<label for="'+ curforid+'" style="padding:0px;"><input ' + selectStr + ' type="checkbox" ' + nameStr + ' id="c'+ this.obj + nodeId + '"  value ="' + (null!=node._value&&''!=node._value?node._value:node.id) + '" onclick="javascript:'+this.obj+'.cc0('+nodeId+')"/>';	
			}else{
			   str+= '<input ' + selectStr + ' type="checkbox"  style="display:none"' + nameStr + ' id="c'+ this.obj + nodeId + '"  value ="' + (null!=node._value&&''!=node._value?node._value:node.id) + '" onclick="javascript:'+this.obj+'.cc0('+nodeId+')"/>';
			}
//======//			
		}
	}  
	if (this.config.useIcons) {

		if (!node.icon) node.icon = (this.root.id == node.pid) ? this.icon.root : ((node._hc) ? this.icon.folder : this.icon.node);

		if (!node.iconOpen) node.iconOpen = (node._hc) ? this.icon.folderOpen : this.icon.node;

		if (this.root.id == node.pid) {

			node.icon = this.icon.root;

			node.iconOpen = this.icon.root;

		}
		if(node.pid==-1){
			 if(!node.isCheckBox){
        	   str += '<img id="i' + this.obj + nodeId + '" src="' + ((node._io) ? node.iconOpen : node.icon) + '" alt="" />';
             }
		}else{
			   str += '<img id="i' + this.obj + nodeId + '" src="' + ((node._io) ? node.iconOpen : node.icon) + '" alt="" />'
		}
		
       
		

	}
	
	

	if (node.url) {

		str += '<a id="s' + this.obj + nodeId + '" class="' + ((this.config.useSelection) ? ((node._is ? 'nodeSel' : 'node')) : 'node') + '" href="' + node.url + '"';

		if (node.title) str += ' title="' + node.title + '"';

		if (node.target) str += ' target="' + node.target + '"';

		if (this.config.useStatusText) str += ' onmouseover="window.status=\'' + node.name + '\';return true;" onmouseout="window.status=\'\';return true;" ';
  
		if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc))

			str += ' onclick="javascript: ' + this.obj + '.s(' + nodeId + ');"';

		str += '>';

	}

	else if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id)

		str += '<a href="javascript: ' + this.obj + '.o(' + nodeId + ');" class="node">';

	    str +=node.name;
	if(node.isCheckBox)str += '</label>';

	if (node.url || ((!this.config.folderLinks || !node.url) && node._hc)) str += '</a>';

	str += '</div>';

	if (node._hc) {

		str += '<div id="d' + this.obj + nodeId + '" class="clip" style="display:' + ((this.root.id == node.pid || node._io) ? 'block' : 'none') + ';">';

		str += this.addNode(node);

		str += '</div>';

	}

	this.aIndent.pop();

	return str;

};



// Adds the empty and line icons

treeCheckBox.prototype.indent = function(node, nodeId) {

	var str = '';

	if (this.root.id != node.pid) {

		for (var n=0; n<this.aIndent.length; n++)

			str += '<img src="' + ( (this.aIndent[n] == 1 && this.config.useLines) ? this.icon.line : this.icon.empty ) + '" alt="" />';

		(node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);

		if (node._hc) {

			str += '<a href="javascript: ' + this.obj + '.o(' + nodeId + ');"><img id="j' + this.obj + nodeId + '" src="';

			if (!this.config.useLines) str += (node._io) ? this.icon.nlMinus : this.icon.nlPlus;

			else str += ( (node._io) ? ((node._ls && this.config.useLines) ? this.icon.minusBottom : this.icon.minus) : ((node._ls && this.config.useLines) ? this.icon.plusBottom : this.icon.plus ) );

			str += '" alt="" /></a>';

		} else str += '<img src="' + ( (this.config.useLines) ? ((node._ls) ? this.icon.joinBottom : this.icon.join ) : this.icon.empty) + '" alt="" />';

	}

	return str;

};



// Checks if a node has any children and if it is the last sibling

treeCheckBox.prototype.setCS = function(node) {

	var lastId;

	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n].pid == node.id) node._hc = true;

		if (this.aNodes[n].pid == node.pid) lastId = this.aNodes[n].id;

	}

	if (lastId==node.id) node._ls = true;

};



// Returns the selected node

treeCheckBox.prototype.getSelected = function() {

	var sn = this.getCookie('cs' + this.obj);

	return (sn) ? sn : null;

};



// Highlights the selected node

treeCheckBox.prototype.s = function(id) {

	if (!this.config.useSelection) return;

	var cn = this.aNodes[id];

	if (cn._hc && !this.config.folderLinks) return;

	if (this.selectedNode != id) {

		if (this.selectedNode || this.selectedNode==0) {

			eOld = document.getElementById("s" + this.obj + this.selectedNode);

			eOld.className = "node";

		}

		eNew = document.getElementById("s" + this.obj + id);

		eNew.className = "nodeSel";

		this.selectedNode = id;

		if (this.config.useCookies) this.setCookie('cs' + this.obj, cn.id);

	}

};



// Toggle Open or close

treeCheckBox.prototype.o = function(id) {

	var cn = this.aNodes[id];

	this.nodeStatus(!cn._io, id, cn._ls);

	cn._io = !cn._io;

	if (this.config.closeSameLevel) this.closeLevel(cn);

	if (this.config.useCookies) this.updateCookie();

};



// Open or close all nodes

treeCheckBox.prototype.oAll = function(status) {

	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n]._hc && this.aNodes[n].pid != this.root.id) {

			this.nodeStatus(status, n, this.aNodes[n]._ls)

			this.aNodes[n]._io = status;

		}

	}

	if (this.config.useCookies) this.updateCookie();

};



// Opens the tree to a specific node

treeCheckBox.prototype.openTo = function(nId, bSelect, bFirst) {

	if (!bFirst) {

		for (var n=0; n<this.aNodes.length; n++) {

			if (this.aNodes[n].id == nId) {

				nId=n;

				break;
			}

		}

	}

	var cn=this.aNodes[nId];

	if (cn.pid==this.root.id || !cn._p) return;

	cn._io = true;

	cn._is = bSelect;

	if (this.completed && cn._hc) this.nodeStatus(true, cn._ai, cn._ls);

	if (this.completed && bSelect) this.s(cn._ai);

	else if (bSelect) this._sn=cn._ai;

	this.openTo(cn._p._ai, false, true);

};



// Closes all nodes on the same level as certain node

treeCheckBox.prototype.closeLevel = function(node) {

	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._hc) {

			this.nodeStatus(false, n, this.aNodes[n]._ls);

			this.aNodes[n]._io = false;

			this.closeAllChildren(this.aNodes[n]);

		}

	}

}



// Closes all children of a node

treeCheckBox.prototype.closeAllChildren = function(node) {

	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n].pid == node.id && this.aNodes[n]._hc) {

			if (this.aNodes[n]._io) this.nodeStatus(false, n, this.aNodes[n]._ls);

			this.aNodes[n]._io = false;

			this.closeAllChildren(this.aNodes[n]);		

		}

	}

}



// Change the status of a node(open or closed)

treeCheckBox.prototype.nodeStatus = function(status, id, bottom) {

	eDiv	= document.getElementById('d' + this.obj + id);

	eJoin	= document.getElementById('j' + this.obj + id);

	if (this.config.useIcons) {

		eIcon	= document.getElementById('i' + this.obj + id);

		eIcon.src = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;

	}

	eJoin.src = (this.config.useLines)?

	((status)?((bottom)?this.icon.minusBottom:this.icon.minus):((bottom)?this.icon.plusBottom:this.icon.plus)):

	((status)?this.icon.nlMinus:this.icon.nlPlus);

	eDiv.style.display = (status) ? 'block': 'none';

};





// [Cookie] Clears a cookie

treeCheckBox.prototype.clearCookie = function() {

	var now = new Date();

	var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);

	this.setCookie('co'+this.obj, 'cookieValue', yesterday);

	this.setCookie('cs'+this.obj, 'cookieValue', yesterday);

};



// [Cookie] Sets value in a cookie

treeCheckBox.prototype.setCookie = function(cookieName, cookieValue, expires, path, domain, secure) {

	document.cookie =

		escape(cookieName) + '=' + escape(cookieValue)

		+ (expires ? '; expires=' + expires.toGMTString() : '')

		+ (path ? '; path=' + path : '')

		+ (domain ? '; domain=' + domain : '')

		+ (secure ? '; secure' : '');

};



// [Cookie] Gets a value from a cookie

treeCheckBox.prototype.getCookie = function(cookieName) {

	var cookieValue = '';

	var posName = document.cookie.indexOf(escape(cookieName) + '=');

	if (posName != -1) {

		var posValue = posName + (escape(cookieName) + '=').length;

		var endPos = document.cookie.indexOf(';', posValue);

		if (endPos != -1) cookieValue = unescape(document.cookie.substring(posValue, endPos));

		else cookieValue = unescape(document.cookie.substring(posValue));

	}

	return (cookieValue);

};



// [Cookie] Returns ids of open nodes as a string

treeCheckBox.prototype.updateCookie = function() {

	var str = '';

	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n]._io && this.aNodes[n].pid != this.root.id) {

			if (str) str += '.';

			str += this.aNodes[n].id;

		}

	}

	this.setCookie('co' + this.obj, str);

};



// [Cookie] Checks if a node id is in a cookie

treeCheckBox.prototype.isOpen = function(id) {

	var aOpen = this.getCookie('co' + this.obj).split('.');

	for (var n=0; n<aOpen.length; n++)

		if (aOpen[n] == id) return true;

	return false;

};



// If Push and pop is not implemented by the browser

if (!Array.prototype.push) {

	Array.prototype.push = function array_push() {

		for(var i=0;i<arguments.length;i++)

			this[this.length]=arguments[i];

		return this.length;

	}

};

if (!Array.prototype.pop) {

	Array.prototype.pop = function array_pop() {

		lastElement = this[this.length-1];

		this.length = Math.max(this.length-1,0);

		return lastElement;

	}

};


// 写 checkbox 页面元素的 onclick 事件处理函数

treeCheckBox.prototype.cc=function(nodeId){  

	var checkStr = '';
	
    var cs = document.getElementById("c"+this.obj+nodeId).checked;   
	
    var n,node = this.aNodes[nodeId];   
  
    var len =this.aNodes.length;   
  
    for (n=0; n<len; n++) {   
  
        if (this.aNodes[n].pid == node.id) {               
  
            document.getElementById("c"+this.obj+n).checked=cs;            

            this.cc(n);            
  
        }          
  
    }   
  
    if(cs==false){     
  
        var clicknode=node   
  
        do{   
  
            for(j=0;j<len;j++){             
  
                if(this.aNodes[j].pid==clicknode.pid&&document.getElementById("c"+this.obj+j).checked==true){                  
  
                    return;   
  
                    }   
  
            }   
  
            if(j==len){    
  
                for(k=0;k<len;k++){   
  
                    if(this.aNodes[k].id==clicknode.pid){   
  
                        document.getElementById("c"+this.obj+k).checked=false;   
  
                        clicknode=this.aNodes[k];   
  
                        break;   
                    }      
  
                }   
  
            }   
  
        }while(clicknode.pid!=-1);     
  
    }   
  
    if(cs==true){   
  
        var pid=node.pid;   
  
        var bSearch;   
  
        do{   
  
            bSearch=false;   
  
            for(n=0;n<len;n++){   
  
                if(this.aNodes[n].id==pid){   
  
                    document.getElementById("c"+this.obj+n).checked=true;   
  
                    pid=this.aNodes[n].pid;   
					
                    bSearch= true;       
  
                    break;   
  
                }   
  
            }   
  
        }while(bSearch==true);   
   }   
}

// 提交表单
treeCheckBox.prototype.divSearch=function(date){
	//alert(document.getElementsByName("areaName")[0].value+date);
}

// checkbox的onclick事件
treeCheckBox.prototype.cc0=function(nodeId){
	this.cc(nodeId);
	this.divSearch(new Date());
}

//添加radClick(),treeid为树的ID,labelid为td的ID
function radClick(param,treeid,labelid){	
	var isselect = jqueryGetEleById(param).attr("checked");
	if(isselect){
		if(treeid!=''){
		  jqueryGetEleById(treeid).show();
		}
		if(labelid!=''){
		  jqueryGetEleById(labelid).hide();
		}
		
		
	}else{
		if(treeid!=''){
		  jqueryGetEleById(treeid).hide();
		}
		if(labelid!=''){
			jqueryGetEleById(labelid).show();
		}
   }
}

//显示选项卡所对应的选项
function querytype(id){
	var selected = jqueryGetEleValById(id);
    var selection=document.getElementById(id);
    var num=selection.options.length;
    for(var i=0;i<num;i++){
        var t=i+1;
        var temp="div_q"+(i+1);
        if(selected==t){
       	   jqueryGetEleById(temp).show();
        }else{
           jqueryGetEleById(temp).hide();
        }
    }     	
}
//提交前，将选中的checkbox但没提交的置为未选中状态
function changeCheck(id,chkname){
   var selected = jqueryGetEleValById(id);
   var selection=document.getElementById(id);
   var num=selection.options.length;
  
   for(var i=0;i<num;i++){
   		var m=i+1;
   	   	if(selected!=m){
   	   	var temp="div_q"+m;
   	   	var div=document.getElementById(temp)
   	   	var input=div.getElementsByTagName("input");
   	   	var c=input.length;
   	   	for(var t=0;t<c;t++){
   	   	   if(input[t].type=="checkbox"){
   	   		
			if(input[t].name==chkname){
				if(input[t].checked){
					input[t].checked=false;
					input[t].onclick();
				}
			}
			input[t].checked=false;
   	   	   }
   	   	}   	    
   	   }
   }
}