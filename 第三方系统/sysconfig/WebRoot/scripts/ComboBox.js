
/*
* 依赖Ext JS Library 2.1
* add：刘志强<liuzhiqiang@zzvcom.com>
* 支持的编码utf-8
*
*
* 功能点有： 
* 一、模拟select效果
* 二、参数说明
*	@id为combobox容器对应的div的id(必选)
*		text为显示信息(必选)
*		values下拉列表对应的选项，json格式[{value:"25%", text:"25%"}, {value:"25%", text:"25%"}]
*		selfName为从为定义的名称(必选)
*		width为设置宽度(必选)
*		heigth为设置高度(必选)
*		pagesize每页显示条数(),如果为null则表示不分页
*		editable是否允许可编辑
*		defalutvalue设定默认值
*		text1自定义json数据value字段
*		text2自定义json数据text字段
*		jilianobject被级联的对象或者是选中某项值级联的方法
*		jilianvalue被级联的数据（如果上面参数设置的是级联的方法，则可保持为null）
* 三、扩展功能
*		1、添加了分页动作，自定义实现了分页传递，目前不支持异步分页。
*		2、支持当手工输入信息过滤后的下拉列表的分页
*		3、支持通过配置是否显示分页
*		5、支持通过设定value自动选择
*		6、支持通过设定值自动定位设定值所在的页
*		
* 四、注意事项
		1、如果用级联select，注意分页参数请设置为null，本级联不支持分页级联
		2、如果调用出错，请对照设置的参数，是否完整。
* 五、exemple
*		普通select
*		var value=[{value:"25%", text:"25%"}, {value:"25%", text:"25%"}, {value:"25%", text:"25%"},{value:"25%", text:"25%"}, {value:"50%", text:"50%"}, {value:"50%", text:"50%"}, {value:"50%", text:"50%"},{value:"50%", text:"50%"},{value:"53%", text:"53%"}];
*		var combobox=new combobox("percent","完成状态",value,"combobox",270,200,4);
*			combobox.getValue();//取选中的值的value
*			combobox.getText();//取选中值的text
*			combobox.setValues("9");//设定默认值
*		级联select
*		var value5=[{value:"1", text:"河南"}, 
*				{value:"2", text:"郑州"},
*				{value:"3", text:"南阳"},
*				{value:"4", text:"洛阳"}];
*		var value4=[[{value:'1',text:'南阳'}],
*				[{value:'1',text:'新政'}],
*				[{value:'1',text:'淅川'}],
*				[{value:'1',text:'牡丹'}]
*		];
*		//被级联select 注意：这两个位置不能放反了
*		var jiliancombobox2=new combobox("jilianpagepercent2","完成状态2",value4[0],"jiliancombobox2",270,200,null,true,null);
*		//级联select
*		var jiliancombobox1=new combobox("jilianpagepercent1","完成状态2",value5,"jiliancombobox1",270,200,null,true,null,null,null,jiliancombobox2,value4);
*	
*/
function combobox(ids,text,values,selfName,width,heigth,pagesize,editable,defalutvalue,text1,text2,object,jilianvalue,type,disable) {
	//分页实现
	this.pagesize=pagesize;
	this.data=values;
	this.width=width;
	this.heigth=heigth;
	this.values=values;
	this.disable=disable;
	this.defalutvalue=defalutvalue;
	this.id=ids;
	this.text=text;
	this.type=type;
	this.jilianobject=object;
	this.jilianvalue=jilianvalue;
	this.text1=text1;
	this.text2=text2;
	if(this.text1&&this.text2){
		var valuearray=new Array();
		for(var i=0;i<this.values.length;i++){
			if(this.values[i].pinyin){
				var _value={value:eval("this.values["+i+"]."+this.text1),text:eval("this.values["+i+"]."+this.text2),pinyin:this.values[i].pinyin};
				valuearray[i]=_value;
			}else{
				var _value={value:eval("this.values["+i+"]."+this.text1),text:eval("this.values["+i+"]."+this.text2)};
				valuearray[i]=_value;
			}
		}
		this.values=valuearray;
	}
	
	var comboboxindex=ids;
	var $ = function(id) {
    	return document.getElementById(id);
	}
	var store,comwithpa,percent;
	this.store=null;
	this.comwithpa=null;
	//返回comboBox对象本身
	combobox.prototype.combobox=function(){
		return this.comwithpa;
	}
	combobox.prototype.markInvalid=function(text){
		return this.comwithpa.markInvalid(text);
	}
	//返回comboBox选中的值value
	combobox.prototype.getValue=function(){
		return this.comwithpa.getValue();
	}
	//返回comboBox选中的值text
	combobox.prototype.getText=function(){
		return this.comwithpa.getRawValue();
	}
	combobox.prototype.setDisabled=function(obj){
		this.comwithpa.setDisabled(obj);
	}
		//重新渲染数据
	combobox.prototype.loadData=function(val){
		this.comwithpa.clearValue();
		var valuearray=new Array();
		for(var i=0;i<val.length;i++){
			if(this.text1&&this.text2){
				var _value=[eval("val["+i+"]."+this.text1),eval("val["+i+"]."+this.text2)];
				valuearray[i]=_value;
			}else{
				var _value=[val[i].value,val[i].text];
				valuearray[i]=_value;
			}
		}
		this.store.loadData(valuearray);
	}
	//设定默认选中值
	combobox.prototype.setValues=function(value){
		if(this.pagesize){
			var index=0;
			for(var i=0;i<this.values.length;i++){
				if(this.values[i].value==value){index=i;break;}
			}
			var sp=Math.ceil((index+1)/this.pagesize);
			this.comwithpa.onFocus();
			this.comwithpa.expand();
			
			this.turnPage(sp);
			this.comwithpa.collapse();
			this.comwithpa.setValue(value);
			//this.comwithpa.reset();
		}else{
			this.comwithpa.setValue(value);
		}
	}
	//获取当前id
	  combobox.prototype.getComboBox=function(){
	  	return this.comwithpa;
	  }
	var _this=this;
	this.splitpage={
			currentPage:0,
			pageSize:15,
			pageCount:1,
			recordCount:1,
			nextPage:1,
			previewPage:1,
			startRecord:1
	}
	this._splitpage_tools = {
	        CURlement: "comboboxPage_CUR_" + comboboxindex,
	        Alllement: "comboboxPage_All_" + comboboxindex,
	        ext_comp_1003 : "combobox-ext-comp-1003_" + comboboxindex,
	        ext_comp_1004 : "combobox-ext-comp-1004_" + comboboxindex,
	        ext_comp_1005 : "combobox-ext-comp-1005_" + comboboxindex,
	        ext_comp_1006 : "combobox-ext-comp-1006_" + comboboxindex,
	        ext_gen14:"combobox-ext-gen14_" + comboboxindex,
	        ext_gen22:"combobox-ext-gen22_" + comboboxindex,
	        ext_gen34:"combobox-ext-gen37_" + comboboxindex,
	        ext_gen45:"combobox-ext-gen45_" + comboboxindex
	    }
	combobox.prototype.expand=function(){this.comwithpa.expand();}
	//级联select
	combobox.prototype.jilian=function(value){
		this.jilianobject.values=value;
		var valuearray=new Array();
		if(this.jilianobject.type==1){
			valuearray.push(["", (this.texts || "...")]);
		}
		if(this.jilianobject.text1&&this.jilianobject.text2){
			for(var i=0;i<this.jilianobject.values.length;i++){
				var _value=[eval("this.jilianobject.values["+i+"]."+this.jilianobject.text1),eval("this.jilianobject.values["+i+"]."+this.jilianobject.text2)];
				valuearray.push(_value);
			}
		}else{
			for(var i=0;i<this.jilianobject.values.length;i++){
				var _value=[this.jilianobject.values[i].value,this.jilianobject.values[i].text];
				valuearray.push(_value);
			}
		}
		this.jilianobject.combobox().clearValue();
		this.jilianobject.store.loadData(valuearray);
	}
	//抛出分页接口
	combobox.prototype.turnPage = function(f) {
		//alert(pagesize)
	    this.initpage(this.data,this.pagesize,f);
	    percent.length=0;
	    if(_this.type==1){
			percent.push(["", (this.texts || "...")]);
		}
	    for(var i=this.splitpage.startRecord;i<this.splitpage.startRecord+((this.splitpage.recordCount-this.splitpage.startRecord)>=this.splitpage.pageSize?this.splitpage.pageSize:(this.splitpage.recordCount-this.splitpage.startRecord));i++){
	    	percent.push([this.data[i].value,this.data[i].text]);
	    }
	    if(percent.length!=0){
	    	this.store.loadData(percent);
	    }
	    //$(this._splitpage_tools.CURlement).value=this.splitpage.currentPage;
	    $(this._splitpage_tools.Alllement).innerHTML=this.splitpage.currentPage;
	    if(this.splitpage.currentPage==1){
    		$(this._splitpage_tools.ext_comp_1003).className="x-btn-wrap x-btn x-btn-icon x-item-disabled";
    		$(this._splitpage_tools.ext_gen14).disabled=true;
    		$(this._splitpage_tools.ext_comp_1004).className="x-btn-wrap x-btn x-btn-icon x-item-disabled";
    		$(this._splitpage_tools.ext_gen22).disabled=true;
    	}else{
    		$(this._splitpage_tools.ext_comp_1003).className="x-btn-wrap x-btn x-btn-icon";
    		$(this._splitpage_tools.ext_gen14).disabled=false;
    		$(this._splitpage_tools.ext_comp_1004).className="x-btn-wrap x-btn x-btn-icon";
    		$(this._splitpage_tools.ext_gen22).disabled=false;
    	}
    	if(this.splitpage.currentPage==this.splitpage.pageCount){
    		$(this._splitpage_tools.ext_comp_1005).className="x-btn-wrap x-btn x-btn-icon x-item-disabled";
    		$(this._splitpage_tools.ext_gen34).disabled=true;
    		$(this._splitpage_tools.ext_comp_1006).className="x-btn-wrap x-btn x-btn-icon x-item-disabled";
    		$(this._splitpage_tools.ext_gen45).disabled=true;
    	}else{
    		$(this._splitpage_tools.ext_comp_1005).className="x-btn-wrap x-btn x-btn-icon";
    		$(this._splitpage_tools.ext_gen34).disabled=false;
    		$(this._splitpage_tools.ext_comp_1006).className="x-btn-wrap x-btn x-btn-icon";
    		$(this._splitpage_tools.ext_gen45).disabled=false;
    	}
	}
	//初始化分页信息(第一个加载使用)
	combobox.prototype.initpage =function(values,pagesize,cupage){
		this.splitpage.pageSize=pagesize;
		this.splitpage.recordCount=values.length;
		this.splitpage.currentPage=cupage;
		this.splitpage.pageCount = Math.ceil(this.splitpage.recordCount/ this.splitpage.pageSize);
        if(this.splitpage.currentPage>this.splitpage.pageCount)this.splitpage.currentPage=this.splitpage.pageCount;
        this.splitpage.nextPage = (this.splitpage.currentPage < this.splitpage.pageCount ? this.splitpage.currentPage + 1 : this.splitpage.pageCount);
        this.splitpage.previewPage = (this.splitpage.currentPage - 1 > 1 ? this.splitpage.currentPage - 1 : 1);
        this.splitpage.startRecord = (this.splitpage.currentPage - 1) * this.splitpage.pageSize;
        if (this.splitpage.currentPage > this.splitpage.pageCount) this.splitpage.currentPage = this.splitpage.pageCount;
	}
	if(this.pagesize)
	this.initpage(this.values,this.pagesize,1);
	//自定义分页实现
	function showpage() {//自定义分页标记模块
				var pagehtml="<div id=\"splitPage_" + comboboxindex + "\"><DIV class=\"x-toolbar x-small-editor\" id=ext-comp-1002><TABLE cellSpacing=0>"+
				"<TBODY>"+
				"<TR>"+
				"<TD>"+
				"<TABLE class=\"x-btn-wrap x-btn x-btn-icon "+(_this.splitpage.currentPage==1?"x-item-disabled":"")+"\" id=combobox-ext-comp-1003_"+comboboxindex+" style=\"WIDTH: auto\" cellSpacing=0 cellPadding=0 border=0>"+
				"<TBODY>"+
				"<TR>"+
				"<TD class=x-btn-left><I>&nbsp;</I></TD>"+                      
				"<TD class=x-btn-center><EM unselectable=\"on\"><BUTTON "+(_this.splitpage.currentPage==1?"disabled":"")+" class=\"x-btn-text x-tbar-page-first\" id=combobox-ext-gen14_"+comboboxindex+" qtip=\"第一页\" onclick=\"" + selfName + ".turnPage(1)\">　</BUTTON></EM></TD>"+
				"<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>"+
				"<TD>"+
				"<TABLE class=\"x-btn-wrap x-btn x-btn-icon "+(_this.splitpage.currentPage==1?"x-item-disabled":"")+"\" id=combobox-ext-comp-1004_"+comboboxindex+" style=\"WIDTH: auto\"cellSpacing=0 cellPadding=0 border=0>"+
				"<TBODY>"+
				"<TR>"+
				"<TD class=x-btn-left><I>&nbsp;</I></TD>"+
				"<TD class=x-btn-center><EM unselectable=\"on\"><BUTTON "+(_this.splitpage.currentPage==1?"disabled":"")+" class=\"x-btn-text x-tbar-page-prev\" id=combobox-ext-gen22_"+comboboxindex+" qtip=\"上一页\" onclick=\"" + selfName + ".turnPage(" + selfName + ".splitpage.previewPage)\">　</BUTTON></EM></TD>"+
				"<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>"+
				"<TD><SPAN class=ytb-sep ></SPAN></TD>"+
				"<TD><SPAN class=ytb-text><span id=\"comboboxPage_All_" + comboboxindex + "\">"+eval(selfName+".splitpage.currentPage")+"</span></SPAN></TD>"+
				//"<TD><INPUT class=x-tbar-page-number id=\"comboboxPage_CUR_" + comboboxindex + "\" style=\"HEIGHT: 18px\" size=3 value=" + _this.splitpage.currentPage + "></TD>"+
				"<TD><SPAN class=ytb-text>/ <span id=\"comboboxPage_All\">"+eval(selfName+".splitpage.pageCount")+"</span></SPAN></TD>"+
				"<TD><SPAN class=ytb-sep></SPAN></TD>"+
				"<TD id=ext-gen35>"+
				"<TABLE class=\"x-btn-wrap x-btn x-btn-icon "+(_this.splitpage.currentPage==_this.splitpage.pageCount?"x-item-disabled":"")+"\" id=combobox-ext-comp-1005_"+comboboxindex+" style=\"WIDTH: auto\"cellSpacing=0 cellPadding=0 border=0>"+
				"<TBODY>"+
				"<TR>"+
				"<TD class=x-btn-left><I>&nbsp;</I></TD>"+
				"<TD class=x-btn-center><EM unselectable=\"on\"><BUTTON "+(_this.splitpage.currentPage==_this.splitpage.pageCount?"disabled":"")+" class=\"x-btn-text x-tbar-page-next\" id=combobox-ext-gen37_"+comboboxindex+" qtip=\"下一页\" onclick=\"" + selfName + ".turnPage(" + selfName + ".splitpage.nextPage)\">　</BUTTON></EM></TD>"+
				"<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>"+
				"<TD id=ext-gen43>"+
				"<TABLE class=\"x-btn-wrap x-btn x-btn-icon "+(_this.splitpage.currentPage==_this.splitpage.pageCount?"x-item-disabled":"")+"\" id=combobox-ext-comp-1006_"+comboboxindex+" style=\"WIDTH: auto\"cellSpacing=0 cellPadding=0 border=0>"+
				"<TBODY>"+
				"<TR>"+
				"<TD class=x-btn-left><I>&nbsp;</I></TD>"+
				"<TD class=x-btn-center><EM unselectable=\"on\"><BUTTON "+(_this.splitpage.currentPage==_this.splitpage.pageCount?"disabled":"")+" class=\"x-btn-text x-tbar-page-last\" id=combobox-ext-gen45_"+comboboxindex+" qtip=\"最后一页\" onclick=\"" + selfName + ".turnPage(" + selfName + ".splitpage.pageCount)\">　</BUTTON></EM></TD>"+
				"<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>"+
				"<TD><SPAN class=ytb-sep></SPAN></TD>"+
				"</TR></TBODY></TABLE></div></div>";
				//初始化各tools
		return pagehtml;
	}
	//加载select
	combobox.prototype.initCheckBox=function (id, texts, width,heigth,values,pagesize){
		Ext.onReady(function () {
		Ext.namespace("Ext.comboData");
		percent=Ext.comboData.percent = [];//完成状态
		if(_this.type==1){
			percent.push(["",(this.texts || "...")]);
		}
		if(_this.pagesize&&!_this.defalutvalue){
			for(var i=0;i<(values.length>_this.pagesize?_this.pagesize:values.length);i++){
				percent.push([values[i].value,values[i].text]);
			}
		}else if(_this.pagesize&&_this.defalutvalue){
			var index=0;
			for(var j=0;j<values.length;j++){
				if(values[j].value==_this.defalutvalue){index=j;break;}
			}
			var sp=Math.ceil((index+1)/pagesize);
			_this.initpage(_this.values,_this.pagesize,sp);
			for(var i=_this.splitpage.startRecord;i<_this.splitpage.startRecord+((_this.splitpage.recordCount-_this.splitpage.startRecord)>=_this.splitpage.pageSize?_this.splitpage.pageSize:(_this.splitpage.recordCount-_this.splitpage.startRecord));i++){
	    		percent.push([values[i].value,values[i].text]);
	    	}
		}else{
			for(var i=0;i<values.length;i++){
				percent.push([values[i].value,values[i].text]);
			}
		}
		_this.store = new Ext.data.SimpleStore({fields:["value", "text"], data:percent || [["", ""]]});
		_this.comwithpa = new Ext.form.ComboBox({
				store:_this.store, 
				editable:editable||false, 
				displayField:"text", 
				valueField:"value", 
				listClass:"alignLeft", 
				hiddenName:id, 
				id:id+"_1",
				maxHeight:_this.heigth || 300, 
				width:(Ext.get(id).getComputedWidth()==0?(width||150):(Ext.get(id).getComputedWidth())),
				heigth:_this.heigth || 300, 
				typeAhead:true, 
				mode:"local", 
				types:'ComboBoxs',
				stateful:false,
				stateId:null,
				disabled :_this.disable||false,
				resizable:false,
				triggerAction:"all", 
				forceSelection:false,
				emptyText: (texts || "..."), 
				tpl:_this.pagesize==null?null:"<tpl for=\".\"><div class=\"x-combo-list-item\">{text}</div></tpl><div >"+showpage()+"</div>", 
				renderTo:ids
			});
		_this.comwithpa.on('expand',function(){this.restrictHeight()});
		_this.comwithpa.on("beforequery",function(query){
			_this.comwithpa.setValue(query.query);
		});
		
	   	if(_this.defalutvalue)_this.comwithpa.setValue(_this.defalutvalue);
	   	if(_this.jilianobject){
	   			_this.comwithpa.on("select",function(o,record,index){
	   			if(typeof(_this.jilianobject)=="object"){
	   				if(_this.type==1){
	   					if(index==0){
	   						_this.jilian([])
	   					}else
	   						_this.jilian(_this.jilianvalue[index-1]);
	   				}else{
	   					_this.jilian(_this.jilianvalue[index]);
	   				}
	   			}
	   	 		else if(typeof(_this.jilianobject)=="function"){
	   	 			_this.jilianobject(o,record,index);
	   	 		}
	   		});
	   	}
	    //comwithpa.onFocus();
		//comwithpa.expand();
		//comwithpa.el.focus();
	});
	}
	this.initCheckBox(this.id,this.text,this.width,this.heigth,this.values,this.pagesize); 
}
/*
* 功能点有： 
* 一、模拟google信息提示
* 二、参数说明
*	@id为combobox容器对应的div的id(必选)
*		text为为空的时间显示提示信息信息(必选)
*		width为设置宽度(必选)
*		heigth为设置高度(必选)
*		pagesize每页显示条数(当启用ajax的时间有效),如果为null则表示不分页。
*		url启用ajax需要的url地址。
*		value本地模拟拼音检索数据，不加载分页，如果启用ajax则此部分需要设置为null
*		defalutvalue设置默认值格式为{value:'2',text:'信阳'}
*		text1自定义json数据value字段
*		text2自定义json数据text字段
* 三、扩展功能
*		1、支持异步分页。
*		2、支持当手工输入信息过滤后的下拉列表的分页
*		3、支持通过配置是否显示分页
*		6、支持输入拼音查询
*		
* 三、exemple
*			var value3=[{value:'1',text:'河南',pinyin:'henan'},
*							{value:'2',text:'南阳',pinyin:'nanyang'},
*							{value:'3',text:'河北',pinyin:'hebei'},
*							{value:'4',text:'信阳',pinyin:'xinyang'},
*							{value:'5',text:'商丘',pinyin:'shangqiu'}
*							];
*			var activebox=new activecombobox("pagepercent1","省份","100%",150,5,"ComboBox.action",value3);
*			activebox.getValue();//取选中的值的value
*			activebox.getText();//取选中值的text
*/
function activecombobox(id,texts,width,heigth,pagesize,url,value,defalutvalue,text1,text2,pinyin){
	this.id=id;
	this.width=width;
	this.heigth=heigth;
	this.pagesize=pagesize;
	this.text1=text1;
	this.text2=text2;
	this.pinyin=pinyin;
	this.value=value;
	if(this.text1&&this.text2){
		var valuearray=new Array();
		for(var i=0;i<this.value.length;i++){
			var _value={value:eval("this.value["+i+"]."+this.text1),text:eval("this.value["+i+"]."+this.text2),pinyin:eval("this.value["+i+"]."+this.pinyin)||''};
			valuearray[i]=_value;
		}
		this.value=valuearray;
	}
	this.url=url;
	var _this=this;
	this.defalutvalue=defalutvalue;
	this.dsSupplier=null;
	this.supplierCmb=null;
	//返回comboBox选中的值value
	activecombobox.prototype.getValue=function(){
		return this.supplierCmb.getValue();
	}
	//返回comboBox选中的值text
	activecombobox.prototype.getText=function(){
		return this.supplierCmb.getRawValue();
	}
	//设定默认选中值
	activecombobox.prototype.setValues=function(value){
			this.supplierCmb.setValue(value);
	}
	//获取当前id
	  activecombobox.prototype.getComboBox=function(){
	  	return this.supplierCmb;
	  }
	Ext.onReady(function () {
		var urlstore=new Ext.data.HttpProxy({
                               url:_this.url
                          });
		_this.dsSupplier = new Ext.data.Store({
                    proxy:urlstore,
                    reader: new Ext.data.JsonReader({
                                root: 'gridRows',
                               totalProperty: 'totalCount'
                            }, [
                                  {name: 'value', mapping: 'value'},
                                {name: 'text', mapping: 'text'}
                              ])
                        });
           _this.supplierCmb = new Ext.form.ComboBox({
                    store: _this.dsSupplier,
                    displayField:'text',
                    valueField: 'value',
                    typeAhead: true,
                    loadingText: 'loading...',
                    maxHeight:_this.heigth || 300, 
                    width: (Ext.get(_this.id).getComputedWidth()==0?(_this.width||150):(Ext.get(_this.id).getComputedWidth())),
                    hiddenName:_this.id, 
                    id:_this.id+"_1", 
                    //hideTrigger:true,
                    minChars:1,
                    types:'ComboBoxs',
                    resizable:false,
                    stateful:false,
					stateId:null,
                    pageSize:_this.value!=null?null:_this.pagesize,
                    forceSelection:true,
                    triggerAction: 'all',
                    lazyInit:false,
                    emptyText: (texts || "..."),
                    renderTo:_this.id
                });
                if(_this.defalutvalue){
                	_this.supplierCmb.setValue(_this.defalutvalue.value);
                	Ext.get(_this.id+"_1").dom.value=_this.defalutvalue.text; 
                };
                _this.supplierCmb.on("beforequery",function(query){
                		if(_this.value){
                			var value={gridRows:[],totalCount:'1'};
	                		var values=new Array();
	                		var index=0;
	                		for(var i=0;i<_this.value.length;i++){
	                			if(_this.value[i].pinyin.indexOf(query.query)==0||_this.value[i].text.indexOf(query.query)==0){
				        			values[index++]=_this.value[i]
				        		}
	                		}
	                		value.gridRows=values;
	                		_this.dsSupplier.proxy=new Ext.data.MemoryProxy(value);
                		}
                		
                		//alert(query.query);
                		
                })
	})

}
var ____node="";
function treecombobox(id,texts,width,heigth,value,treeurl,defalutvalue,type,rootvis,onlyLeafCheckable,functions){
	this.comboxWithTree=null;
	this.id=id;
	this.defalutvalue=defalutvalue;
	this.texts=texts;
	this.width=width;
	this.rootvis=rootvis;
	this.onlyLeafCheckable=onlyLeafCheckable;
	this.heigth=heigth;
	this.value=value;
	this.functions=functions;
	var _this=this;
	Ext.onReady(function () {
		_this.comboxWithTree = new Ext.form.ComboBox({
		    store:new Ext.data.SimpleStore({fields:[],data:[[]]}),
		    editable:false,
		    shadow:false,
		    mode: 'local',
		    displayField:'text',
            valueField: 'value',
		    hiddenName:_this.id, 
            id:_this.id+"_1",
		    width:(Ext.get(_this.id).getComputedWidth()==0?(_this.width||150):(Ext.get(_this.id).getComputedWidth())),
		    triggerAction:'all',
		    types:'ComboBoxs',
		    resizable:false,
		    stateful:false,
			stateId:null,
		    maxHeight: _this.heigth||200,
		    emptyText:(texts || "..."), 
		    tpl: '<tpl for="."><div style="height:'+(_this.heigth-10)+'px"><div id="'+_this.id+'__tree1"></div></div></tpl>',
		    selectedClass:'',
		    onSelect:Ext.emptyFn,
		    renderTo:_this.id
		  });
		if(_this.defalutvalue){
                	 _this.comboxWithTree.setValue(_this.defalutvalue.value);
		     		Ext.get(_this.id+"_1").dom.value=_this.defalutvalue.text; 
		     		
        };
		var root =new Ext.tree.AsyncTreeNode({
								id:'root',
								text:"根节点",
								children:value
							});
		var load1=new Ext.tree.TreeLoader();
		var load2=new Ext.tree.TreeLoader({
					            dataUrl:treeurl
					        })
		var tree=new Ext.tree.TreePanel({
			        root:root,//定位到根节点
			        animate:true,//开启动画效果
			        enableDD:false,//不允许子节点拖动
			        border:false,//没有边框
			        rootVisible:_this.rootvis||false,//设为false将隐藏根节点，很多情况下，我们选择隐藏根节点增加美观性
			        loader:treeurl==null?load1:load2
		     });
		tree.on('click',function(node,e){
		     e.stopEvent();
		     if(_this.onlyLeafCheckable)
		     if(!node.leaf){node.expand();return}
		     if(node.disabled)return;
		     ____node=node;
		     if(type){
		     	_this.comboxWithTree.setValue(node.id);
		     	Ext.get(_this.id+"_1").dom.value=node.text; 
		     }else{
		     	var _node=node;
		     	var _value="";
		     	var _text="";
		     	while(_node.id!="root"){
		     		_text=_node.text+"/"+_text;
		     		_value=_node.id+"/"+_value;
		     		_node=_node.parentNode;
		     	}
		     	_this.comboxWithTree.setValue(_value);
		     	Ext.get(_this.id+"_1").dom.value=_text; 
		     }
		     
		     _this.comboxWithTree.collapse();
		     if(_this.functions){
		     	_this.functions(node,e);
		     }
		   });
		_this.comboxWithTree.on('expand',function(){
		   tree.render(_this.id+'__tree1');
		 });
	})
	//返回comboBox选中的值value
	treecombobox.prototype.getValue=function(){
		return this.comboxWithTree.getValue();
	}
	//返回comboBox选中的值text
	treecombobox.prototype.getText=function(){
		return this.comboxWithTree.getRawValue();
	}
	//设定默认选中值
	treecombobox.prototype.setValues=function(node){
		this.comboxWithTree.setValue(node.value);
		Ext.get(this.id+"_1").dom.value=node.text; 
	}
	//返回comboBox对象本身
	treecombobox.prototype.getComboBox=function(){
		return this.comboxWithTree;
	}
	//获取当前id
	  treecombobox.prototype.getId=function(){
	  	return this.id;
	  }
}
function treecheckpanel(id,texts,width,heigth,valuefield,treejson,type,treeurl,defalutvalue,checkModel,onlyLeafCheckable,panelName,checkfunction){
	this.comboxWithTree=null;
	this.tree=null;
	this.id=id;
	this.defalutvalue=defalutvalue;
	this.texts=texts;
	this.treejson=treejson;
	this.checkfunction=checkfunction;
	this.type=type;
	this.treeurl=treeurl;
	this.checkModel=checkModel;
	var _this=this;

	Ext.onReady(function () {
		   _this.comboxWithTree = new Ext.form.ComboBox({
		    store:new Ext.data.SimpleStore({fields:[],data:[[]]}),
		    editable:false,
		    shadow:false,
		    mode: 'local',
		    displayField:'text',
            valueField: 'value',
            inputType : 'text',
		    hiddenName:_this.id, 
            id:_this.id+"_1",
            types:'ComboBoxs',
		    width:(Ext.get(_this.id).getComputedWidth()==0?(_this.width||150):(Ext.get(_this.id).getComputedWidth())),
		    triggerAction:'all',
		    resizable:false,
		    maxHeight: heigth||200,
		    emptyText: (texts || "..."), 
			//tpl: '<tpl for="."><div style="height:200px"><div id="'+_this.id+'__tree1"></div></div></tpl>',
		    tpl: '<tpl for="."><div style="height:150px;overflow:auto;"><div id="'+_this.id+'_tree1"></div></div></tpl><div align=center><input onclick=\"'+panelName+'.setvalue();\"  class=\"button\" type=\"button\" value=\"确定\">&nbsp;&nbsp;<input  class=\"button\" type=\"button\" value=\"取消\" onclick=\"'+panelName+'.comboxWithTree.collapse();\"></div>',
		    selectedClass:'',
		    onSelect:Ext.emptyFn,
		    renderTo:_this.id
		  });
			

		

          
		    if(_this.defalutvalue){
                	 _this.comboxWithTree.setValue(_this.defalutvalue.value);
		     		Ext.get(_this.id+"_1").dom.value=_this.defalutvalue.text; 
		     		
            };
       
		    _this.root =new Ext.tree.AsyncTreeNode({
								id:'root',
								text:"根节点",
								children:_this.treejson
		     });

			  var load3=new Ext.tree.TreeLoader({baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI }});
			  var load4=new Ext.tree.TreeLoader({
					            dataUrl:treeurl,
					            baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI }
			   });

			
		       _this.tree=new Ext.tree.TreePanel({
				    root:_this.root,//定位到根节点
			        animate:false,//开启动画效果
			        enableDD:false,//不允许子节点拖动
			        border:false,//没有边框
			        checkModel: _this.checkModel||'cascade', //对树的级联多选 
					onlyLeafCheckable: onlyLeafCheckable||false,//对树所有结点都可选 
			        rootVisible:false,//设为false将隐藏根节点，很多情况下，我们选择隐藏根节点增加美观性
			        loader: _this.type==1?load3:load4
				});
		    	
			  
			
		        _this.comboxWithTree.on('expand',function(){
				         _this.tree.render(_this.id+'_tree1');
		       	});
				if(_this.checkfunction){
			    	_this.tree.on("check",function(v,m){
						_this.checkfunction(v,m);
					 });
				 }

		});

		
		//返回定义的tree对象，抛出对外接口getChecked
		treecheckpanel.prototype.trees=function(){
			return this.tree;
		}

        treecheckpanel.prototype.collapse=function(){
			return this.tree;
		}

		treecheckpanel.prototype.Checked=function(value){		
			this.comboxWithTree.onFocus();
			this.comboxWithTree.expand();
			//this.comboxWithTree.el.focus();
			this.tree.expandAll();
			var checkedbox=this.tree.getChecked();
			for(var i=0;i<checkedbox.length;i++){
				checkedbox[i].attributes.checked=false;
			}
			var valueid=value.split(",");			
			for(var j=0;j < valueid.length; j++){			
				if(valueid[j]!=""){
					var node=this.tree.getNodeById(valueid[j]);			
					node.attributes.checked=true;
				}
			}
			//alert();
			this.root.reload();
		    this.tree.collapseAll();
		    this.comboxWithTree.collapse();
		    
		}
		treecheckpanel.prototype.UnChecked=function(){		
			this.comboxWithTree.onFocus();
			this.comboxWithTree.expand();
			var checkedbox=this.tree.getChecked();
			for(var i=0;i<checkedbox.length;i++){
				checkedbox[i].attributes.checked=false;
			}
			
			this.root.reload();
		    this.comboxWithTree.collapse();
		    //赋值
			 this.comboxWithTree.setValue("");
		     Ext.get(this.id+"_1").dom.value= (this.texts || "...");
		}
		//获取选中的checkbox值
		treecheckpanel.prototype.getCheckedValue=function(){
			//this.tree.expandAll();
			var checkedbox=this.tree.getChecked();
			var value="";
			for(var i=0;i<checkedbox.length;i++){

				value=value+checkedbox[i].id+",";
			}
			return value.replace(/,$/,"");
		}
		//获取选中的checkbox值
		treecheckpanel.prototype.getCheckedLeaf=function(){
			//this.tree.expandAll();
			var checkedbox=this.tree.getChecked();
			var value="";
			for(var i=0;i<checkedbox.length;i++){

				value=value+checkedbox[i].leaf+",";
			}
			return value.replace(/,$/,"");
		}

		//获取选中的checkbox内容
		treecheckpanel.prototype.getCheckedText=function(){
			//this.tree.expandAll();
			var checkedbox=this.tree.getChecked();
			var value="";
			for(var i=0;i<checkedbox.length;i++){
				   value=value+checkedbox[i].text+",";
			}
			return value.replace(/,$/,"");
	  }
	  
	  //获取选中的checkbox内容
	  treecheckpanel.prototype.setvalue=function(){
			//获值
	         var text=this.getCheckedText();
	         var value=this.getCheckedValue();
             //赋值
			 this.comboxWithTree.setValue(value);
		     Ext.get(this.id+"_1").dom.value=text;
		     this.comboxWithTree.collapse(); 
		    
	   }
	   //获取当前id
	  treecheckpanel.prototype.getComboBox=function(){
	  	return this.comboxWithTree;
	  }
	treecheckpanel.prototype.getValue=function(){
	  	return this.comboxWithTree.getValue();
	  }
	treecheckpanel.prototype.getText=function(){
	  	return this.comboxWithTree.getRawValue();
	  }
}
